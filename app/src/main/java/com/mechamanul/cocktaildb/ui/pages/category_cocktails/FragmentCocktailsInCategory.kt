package com.mechamanul.cocktaildb.ui.pages.category_cocktails

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.mechamanul.cocktaildb.R
import com.mechamanul.cocktaildb.databinding.FragmentCocktailListBinding
import com.mechamanul.cocktaildb.domain.common.Result
import com.mechamanul.cocktaildb.domain.model.Cocktail
import com.mechamanul.cocktaildb.ui.BaseFragment
import com.mechamanul.cocktaildb.ui.elements.adapters.cocktail_list.CocktailsListAdapter
import com.mechamanul.cocktaildb.ui.elements.callbacks.ImageDrawerCallback
import com.mechamanul.cocktaildb.ui.elements.callbacks.NavigationCallback
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FragmentCocktailsInCategory : BaseFragment(), ImageDrawerCallback, NavigationCallback {

    val viewModel: CocktailsInCategoryViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentCocktailListBinding.inflate(layoutInflater).root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val binding = FragmentCocktailListBinding.bind(view)
        val adapter = CocktailsListAdapter(this, this)
        binding.apply {
            rv.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            rv.adapter = adapter
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect {
                    when (it) {
                        is Result.Error -> handleExceptions(
                            it.exception,
                            binding.root
                        )
                        is Result.Success -> {
                            adapter.submitList(
                                it.data
                            )
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
                FragmentCocktailsInCategoryDirections.actionFragmentCocktailsInCategoryToFragmentCocktailBase(
                    cocktail.id
                )
            job.await()
            findNavController().navigate(action)
        }

}