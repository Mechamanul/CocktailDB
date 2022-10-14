package com.mechamanul.cocktaildb.ui.cocktail.page

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.mechamanul.cocktaildb.R
import com.mechamanul.cocktaildb.databinding.FragmentCocktailPageBinding
import com.mechamanul.cocktaildb.ui.cocktail.CocktailViewModel
import com.mechamanul.cocktaildb.ui.cocktail.CocktailViewModel.CocktailUiState.Success
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FragmentCocktailPage : Fragment() {
    private val viewModel by viewModels<CocktailViewModel>(ownerProducer = { requireParentFragment() })
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentCocktailPageBinding.inflate(layoutInflater)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentCocktailPageBinding.bind(view)
        var fabState: Boolean

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiFlow.collect { uiState ->
                    if (uiState is Success) {
                        val cocktail = uiState.cocktail
                        binding.apply {
                            Glide.with(requireContext()).asBitmap()
                                .load(cocktail.imageUrl).centerCrop()
                                .transform(CenterCrop(), RoundedCorners(96)).into(image)
                            drinkName.text = cocktail.name
                            drinkId.text = "ID: ${cocktail.id}"
                            category.text = cocktail.category
                            type.text = cocktail.type
                            glassType.text = cocktail.glass
                            fabState = cocktail.isFavourite
                            with(fab) {
                                changeFabIcon(fabState)
                            }

                            fab.setOnClickListener {
                                fabState = !fabState
                                (it as FloatingActionButton).changeFabIcon(fabState)

                                viewModel.changeLikeState(cocktailId = cocktail.id)
                            }
                        }

                    }

                }
            }
        }
    }

    private fun FloatingActionButton.changeFabIcon(state: Boolean) {
        if (state) {
            setImageResource(

                R.drawable.ic_baseline_favorite_56
            )
        } else {
            setImageResource(
                R.drawable.ic_baseline_favorite_border_56
            )
        }
    }

}

