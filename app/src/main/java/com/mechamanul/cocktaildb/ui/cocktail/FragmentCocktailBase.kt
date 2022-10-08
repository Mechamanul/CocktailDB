package com.mechamanul.cocktaildb.ui.cocktail

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.snackbar.Snackbar
import com.mechamanul.cocktaildb.databinding.FragmentCocktailBaseBinding
import com.mechamanul.cocktaildb.ui.cocktail.CocktailViewModel.CocktailUiState.*
import com.mechamanul.cocktaildb.ui.cocktail.ingredients.FragmentCocktailIngredients
import com.mechamanul.cocktaildb.ui.cocktail.page.FragmentCocktailPage
import com.mechamanul.cocktaildb.ui.cocktail.viewpager.CocktailViewPagerAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.net.UnknownHostException

@AndroidEntryPoint
class FragmentCocktailBase : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val binding = FragmentCocktailBaseBinding.inflate(inflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val viewModel: CocktailViewModel by activityViewModels()
        val binding = FragmentCocktailBaseBinding.bind(view)
        val viewPagerAdapter = CocktailViewPagerAdapter(
            childFragmentManager, viewLifecycleOwner.lifecycle,
            arrayListOf(FragmentCocktailPage(), FragmentCocktailIngredients())
        )

        binding.apply {
            layoutLoading.root.visibility = View.GONE
            swipeRefreshLayout.visibility = View.GONE
            swipeRefreshLayout.setOnRefreshListener {
                Log.d("swipeTriggered", "True")
                viewModel.getRandomCocktail()
            }
            viewPager.apply {
                orientation = ViewPager2.ORIENTATION_VERTICAL
                adapter = viewPagerAdapter

            }

        }
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiFlow.collect { uiState ->
                    binding.swipeRefreshLayout.isRefreshing = false
                    when (uiState) {
                        is Failure -> {
                            when (uiState.exception) {
                                is UnknownHostException -> Snackbar.make(
                                    binding.root,
                                    "No internet connection",
                                    Snackbar.LENGTH_LONG
                                ).setAction("RETRY") {
                                    viewModel.getRandomCocktail()
                                }.show()
                                else -> Snackbar.make(
                                    binding.root,
                                    uiState.exception.message.toString(),
                                    Snackbar.LENGTH_LONG
                                ).show()
                            }
                        }

                        is Success -> binding.apply {
                            swipeRefreshLayout.visibility = View.VISIBLE
                            layoutLoading.root.visibility = View.GONE
//                            viewPager.apply {
//                                adapter = viewPagerAdapter
//                            }

                        }
                        is InitialLoading -> binding.layoutLoading.root.visibility =
                            View.VISIBLE
                    }
                }
            }
        }
        super.onViewCreated(view, savedInstanceState)
    }


}
