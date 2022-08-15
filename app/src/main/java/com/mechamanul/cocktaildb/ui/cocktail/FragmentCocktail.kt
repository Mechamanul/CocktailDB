package com.mechamanul.cocktaildb.ui.cocktail

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.mechamanul.cocktaildb.R
import com.mechamanul.cocktaildb.databinding.FragmentCocktailViewpagerBinding
import com.mechamanul.cocktaildb.ui.cocktail.screens.FragmentCocktailPage
import com.mechamanul.cocktaildb.ui.cocktail.screens.ingredients.FragmentCocktailIngredients

class FragmentCocktail : Fragment(R.layout.fragment_cocktail_viewpager) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val binding = FragmentCocktailViewpagerBinding.bind(view)
        val adapter = CocktailViewPagerAdapter(
            parentFragmentManager,
            lifecycle,
            arrayListOf(FragmentCocktailPage(), FragmentCocktailIngredients())
        )
        binding.apply {
            viewPager.adapter = adapter
        }
    }
}