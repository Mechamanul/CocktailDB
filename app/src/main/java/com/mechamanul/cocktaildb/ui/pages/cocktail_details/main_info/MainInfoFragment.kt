package com.mechamanul.cocktaildb.ui.pages.cocktail_details.main_info

import android.os.Bundle
import android.util.Log
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
import com.mechamanul.cocktaildb.domain.common.Result
import com.mechamanul.cocktaildb.domain.common.Result.Success
import com.mechamanul.cocktaildb.ui.BaseFragment
import com.mechamanul.cocktaildb.ui.pages.cocktail_details.CocktailViewModel
import com.mechamanul.cocktaildb.ui.pages.cocktail_details.CocktailViewModel.UiState.CallCompleted
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainInfoFragment : BaseFragment() {
    private val likeViewModel: MainInfoViewModel by viewModels()
    private val cocktailViewModel by viewModels<CocktailViewModel>(ownerProducer = { requireParentFragment() })
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        val binding = FragmentCocktailPageBinding.inflate(layoutInflater)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentCocktailPageBinding.bind(view)
        binding.fab.setOnClickListener { likeViewModel.changeLikeState() }


        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {

                launch {
                    cocktailViewModel.uiFlow.collect { uiState ->
                        if (uiState is CallCompleted && uiState.result is Success) {

                            val cocktail = uiState.result.data
                            Log.d("MainInfoFragment", cocktail.toString())
                            binding.apply {
                                Glide.with(requireContext()).asBitmap()
                                    .load(cocktail.imageUrl).centerCrop()
                                    .placeholder(R.drawable.cocktail_mojito_icon)
                                    .transform(CenterCrop(), RoundedCorners(96)).into(image)
                                drinkName.text = cocktail.name
                                drinkId.text = "ID: ${cocktail.id}"
                                category.text = cocktail.category
                                type.text = cocktail.type
                                glassType.text = cocktail.glass


                            }

                        }

                    }
                }
                launch {

                    likeViewModel.likeState.collect {
                        when (it) {
                            is Result.Error -> handleExceptions(it.exception, binding.root).show()
                            is Success -> it.data.collect { state ->
                                binding.fab.changeFabIcon(state)
                            }
                        }
                    }
                }
            }

        }
    }


    private fun FloatingActionButton.changeFabIcon(like: Boolean) {
        if (like) {
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

