package com.mechamanul.cocktaildb.ui.pages.start_page

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.mechamanul.cocktaildb.R
import com.mechamanul.cocktaildb.databinding.FragmentStartPageBinding
import com.mechamanul.cocktaildb.domain.model.Cocktail
import com.mechamanul.cocktaildb.ui.BaseFragment
import com.mechamanul.cocktaildb.ui.elements.adapters.cocktail_list.CocktailsListAdapter
import com.mechamanul.cocktaildb.ui.elements.adapters.search_suggestion_list.SuggestionsListAdapter
import com.mechamanul.cocktaildb.ui.elements.callbacks.ImageDrawerCallback
import com.mechamanul.cocktaildb.ui.elements.callbacks.NavigationCallback
import com.mechamanul.cocktaildb.domain.common.ConnectionException
import com.mechamanul.cocktaildb.domain.common.Result
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@AndroidEntryPoint
class FragmentStartPage : BaseFragment(), ImageDrawerCallback, NavigationCallback {
    private val viewModel: StartPageViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val binding = FragmentStartPageBinding.inflate(inflater)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val visitedCocktailsAdapter = CocktailsListAdapter(this, this)
        val suggestionsAdapter = SuggestionsListAdapter(this)
        val binding = FragmentStartPageBinding.bind(view)
        binding.apply {
            visitedCocktailsRv.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            visitedCocktailsRv.adapter = visitedCocktailsAdapter

            suggestionsList.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            suggestionsList.adapter = suggestionsAdapter
            suggestionsList.visibility = View.GONE

            searchCocktail.findViewById<View>(androidx.appcompat.R.id.search_close_btn)
                .setOnClickListener {
                    searchCocktail.setQuery("", false)
                }


            searchCocktail.setOnQueryTextListener(object : OnQueryTextListener {

                var queryTextChangedJob: Job? = null
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    queryTextChangedJob?.cancel()
                    if (newText != null) {
                        if (newText.isNotEmpty()) {
                            queryTextChangedJob = viewLifecycleOwner.lifecycleScope.launch {
                                delay(500)
                                viewModel.searchCocktailByName(newText)
                            }
                        } else {
                            suggestionsAdapter.submitList(listOf())
                            suggestionsList.visibility = View.GONE
                        }
                    }

                    return false
                }

            })
            searchCocktail.setOnCloseListener {
                suggestionsList.visibility = View.GONE
                false
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.cocktailSearch.collect { result ->
                        when (result) {
                            is Result.Error -> {
                                val snackbar = handleExceptions(result.exception, binding.root)
                                if (result.exception is ConnectionException) {
                                    snackbar.setAction("Retry") {
                                        this.launch {
                                            viewModel.searchCocktailByName("name")
                                        }
                                    }
                                }
                                snackbar.show()
                            }
                            is Result.Success -> {
                                binding.suggestionsList.visibility = View.VISIBLE
                                suggestionsAdapter.submitList(
                                    result.data
                                )
                            }
                        }
                    }
                }
                launch {

                    viewModel.visitedCocktails.collect { result ->
                        when (result) {
                            is Result.Error -> handleExceptions(
                                result.exception,
                                binding.root
                            ).show()
                            is Result.Success -> launch {
                                result.data.collect {
                                    visitedCocktailsAdapter.submitList(it)
                                }
                            }
                        }
                    }
                }

            }


        }

        super.onViewCreated(view, savedInstanceState)
    }


    override fun drawImageCallback(imageView: ImageView, url: String) {
        Glide.with(requireContext()).asBitmap()
            .load(url).placeholder(R.drawable.cocktail_mojito_icon).centerCrop()
            .transform(CenterCrop(), RoundedCorners(58)).into(imageView)
    }

    override fun navigateToCocktailDetails(cocktail: Cocktail): Job =
        viewLifecycleOwner.lifecycleScope.launch {
            val job = async { viewModel.saveChosenCocktailToDatabase(cocktail) }
            val action =
                FragmentStartPageDirections.actionFragmentStartPageToFragmentCocktailBase(
                    cocktail.id
                )
            job.await()
            findNavController().navigate(action)
        }
}

