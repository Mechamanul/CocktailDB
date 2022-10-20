package com.mechamanul.cocktaildb.ui.pages.favourites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
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
import com.mechamanul.cocktaildb.databinding.FragmentCocktailListBinding
import com.mechamanul.cocktaildb.domain.common.Result
import com.mechamanul.cocktaildb.domain.model.Cocktail
import com.mechamanul.cocktaildb.ui.BaseFragment
import com.mechamanul.cocktaildb.ui.elements.adapters.cocktail_list.CocktailsListAdapter
import com.mechamanul.cocktaildb.ui.elements.callbacks.ImageDrawerCallback
import com.mechamanul.cocktaildb.ui.elements.callbacks.NavigationCallback
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FragmentFavourites : BaseFragment(), ImageDrawerCallback, NavigationCallback {
    private val viewModel: FavouritesViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentCocktailListBinding.inflate(inflater).root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val binding = FragmentCocktailListBinding.bind(view)
        val adapter = CocktailsListAdapter(this, this)
        binding.apply {
            rv.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

            rv.adapter = adapter

        }
        super.onViewCreated(view, savedInstanceState)
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiFlow.collect { uiState ->
                    when (uiState) {
                        is Result.Error -> handleExceptions(
                            uiState.exception,
                            binding.root
                        ).show()
                        is Result.Success -> uiState.data.collect {
                            adapter.submitList(it)
                        }
                    }
                }
            }
        }
    }

    override fun drawImageCallback(imageView: ImageView, url: String) {
        Glide.with(requireContext()).asBitmap()
            .load(url).placeholder(R.drawable.cocktail_mojito_icon).centerCrop()
            .transform(CenterCrop(), RoundedCorners(58)).into(imageView)
    }

    override fun navigateToCocktailDetails(cocktail: Cocktail) =
        viewLifecycleOwner.lifecycleScope.launch {
            val action =
                FragmentFavouritesDirections.actionFragmentFavouritesToFragmentCocktailBase(cocktail.id)
            findNavController().navigate(action)
        }
}