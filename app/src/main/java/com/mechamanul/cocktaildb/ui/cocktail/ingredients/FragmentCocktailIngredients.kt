package com.mechamanul.cocktaildb.ui.cocktail.ingredients

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
import com.mechamanul.cocktaildb.ui.cocktail.CocktailViewModel
import com.mechamanul.cocktaildb.ui.cocktail.CocktailViewModel.CocktailUiState.Success
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FragmentCocktailIngredients : Fragment(R.layout.fragment_cocktail_ingredients) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewModel: CocktailViewModel by viewModels()

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
                    if (uiState is Success) {
                        ingredientsAdapter.submit(uiState.cocktail.listOfIngredients)
                        binding.howTo.text = uiState.cocktail.instruction
                        ingredientsAdapter.notifyDataSetChanged()
                    }
                }
            }
        }
    }
}
