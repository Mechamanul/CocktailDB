package com.mechamanul.cocktaildb.ui.pages.cocktail_details.ingredients

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.mechamanul.cocktaildb.R
import com.mechamanul.cocktaildb.databinding.FragmentCocktailIngredientsBinding
import com.mechamanul.cocktaildb.domain.common.Result
import com.mechamanul.cocktaildb.ui.pages.cocktail_details.CocktailViewModel
import com.mechamanul.cocktaildb.ui.elements.adapters.IngredientsAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class IngredientsFragment : Fragment(R.layout.fragment_cocktail_ingredients) {
    val viewModel by viewModels<CocktailViewModel>(ownerProducer = { requireParentFragment() })
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentCocktailIngredientsBinding.bind(view)
        val ingredientsAdapter = IngredientsAdapter()
        binding.apply {
            rv.apply {
                layoutManager = LinearLayoutManager(requireContext())
                adapter = ingredientsAdapter
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiFlow.collect { uiState ->
                    if (uiState is CocktailViewModel.UiState.CallCompleted && uiState.result is Result.Success) {
                        val cocktail = uiState.result.data
                        ingredientsAdapter.submit(cocktail.listOfIngredients)
                        binding.howTo.text = cocktail.instruction
                    }
                }
            }
        }
    }
}
