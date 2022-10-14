package com.mechamanul.cocktaildb.ui.pages.cocktail_details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.viewpager2.widget.ViewPager2
import com.mechamanul.cocktaildb.databinding.FragmentCocktailBaseBinding
import com.mechamanul.cocktaildb.ui.BaseFragment
import com.mechamanul.cocktaildb.ui.pages.cocktail_details.CocktailViewModel.CocktailUiState.*
import com.mechamanul.cocktaildb.ui.pages.cocktail_details.ingredients.FragmentCocktailIngredients
import com.mechamanul.cocktaildb.ui.pages.cocktail_details.main_info.FragmentCocktailPage
import com.mechamanul.cocktaildb.ui.elements.adapters.ViewPagerAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FragmentCocktailBase : BaseFragment() {
    private val viewModel: CocktailViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val binding = FragmentCocktailBaseBinding.inflate(inflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val binding = FragmentCocktailBaseBinding.bind(view)
        val viewPagerAdapter = ViewPagerAdapter(
            childFragmentManager, viewLifecycleOwner.lifecycle,
            arrayListOf(FragmentCocktailPage(), FragmentCocktailIngredients())
        )

        binding.apply {
            viewPager.apply {
                visibility = View.GONE
                orientation = ViewPager2.ORIENTATION_VERTICAL
                adapter = viewPagerAdapter

            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiFlow.collect {
                    when (it) {
                        is Failure -> handleExceptions(it.exception, binding.root).show()
                        is InitialLoading -> binding.apply {
                            viewPager.visibility = View.GONE
                        }
                        is Success -> binding.apply {
                            layoutLoading.root.visibility = View.GONE
                            viewPager.visibility = View.VISIBLE
                        }
                    }
                }
            }
        }




        super.onViewCreated(view, savedInstanceState)
    }


}
