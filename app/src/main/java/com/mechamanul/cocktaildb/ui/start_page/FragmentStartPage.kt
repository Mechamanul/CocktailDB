package com.mechamanul.cocktaildb.ui.start_page

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.widget.SearchView.*
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
import com.mechamanul.cocktaildb.domain.Cocktail
import com.mechamanul.cocktaildb.ui.BaseFragment
import com.mechamanul.cocktaildb.ui.start_page.StartPageViewModel.UiEvent.*
import com.mechamanul.cocktaildb.utils.ConnectionException
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
        val visitedCocktailsAdapter = VisitedCocktailsAdapter(this)
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
            searchCocktail.setOnQueryTextListener(object : OnQueryTextListener {

                var queryTextChangedJob: Job? = null
                override fun onQueryTextSubmit(query: String?): Boolean {
                    query ?: return false
                    viewLifecycleOwner.lifecycleScope.launch {
                        viewModel.searchCocktailByName(query)
                    }
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    newText ?: return false
                    queryTextChangedJob?.cancel()
                    queryTextChangedJob = viewLifecycleOwner.lifecycleScope.launch {
                        delay(500)
                        viewModel.searchCocktailByName(newText)
                    }
                    return true
                }

            })
            searchCocktail.setOnCloseListener {
                suggestionsList.visibility = View.GONE
                false
            }

            viewLifecycleOwner.lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    viewModel.uiEvents.collect { event ->
                        when (event) {
                            is CocktailSearchResult -> when (event) {
                                is CocktailSearchResult.Failure -> {
                                    val snackbar = handleExceptions(event.exception, binding.root)
                                    if (event.exception is ConnectionException) {
                                        snackbar.setAction("Retry") {
                                            this.launch {
                                                viewModel.searchCocktailByName("name")
                                            }
                                        }
                                    }
                                    snackbar.show()
                                }
                                is CocktailSearchResult.Success -> {
                                    suggestionsList.visibility = View.VISIBLE
                                    suggestionsAdapter.submitList(
                                        event.cocktails
                                    )
                                }
                            }
                            is GetVisitedCocktailResult -> when (event) {
                                is GetVisitedCocktailResult.Failure -> handleExceptions(
                                    event.exception,
                                    binding.root
                                ).show()
                                is GetVisitedCocktailResult.Success -> visitedCocktailsAdapter.submitList(
                                    event.cocktails
                                )
                            }
                        }
                    }
                }

                super.onViewCreated(view, savedInstanceState)
            }


        }
    }


    override fun drawImageCallback(imageView: ImageView, url: String) {
        Glide.with(requireContext()).asBitmap()
            .load(url).centerCrop()
            .transform(CenterCrop(), RoundedCorners(58)).into(imageView)
    }

    override fun navigateToCocktailDetails(cocktail: Cocktail): Job =
        viewLifecycleOwner.lifecycleScope.launch {
            val job = async { viewModel.saveChosenCocktailToDatabase(cocktail) }
            job.await()
            val action =
                FragmentStartPageDirections.actionFragmentStartPageToFragmentCocktailBase(cocktail.id)
            findNavController().navigate(action)
        }
}

interface ImageDrawerCallback {
    fun drawImageCallback(imageView: ImageView, url: String)
}

interface NavigationCallback {
    fun navigateToCocktailDetails(cocktail: Cocktail): Job
}