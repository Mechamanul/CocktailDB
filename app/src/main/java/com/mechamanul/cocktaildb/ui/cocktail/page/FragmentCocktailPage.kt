package com.mechamanul.cocktaildb.ui.cocktail.page

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.RoundedCorner
import android.view.View
import android.view.View.MeasureSpec.EXACTLY
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.view.forEach
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.palette.graphics.Palette
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.CenterInside
import com.bumptech.glide.load.resource.bitmap.GranularRoundedCorners
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.mechamanul.cocktaildb.R
import com.mechamanul.cocktaildb.databinding.FragmentCocktailPageBinding
import com.mechamanul.cocktaildb.ui.cocktail.CocktailViewModel
import com.mechamanul.cocktaildb.ui.cocktail.CocktailViewModel.CocktailUiState.Success
import com.mechamanul.cocktaildb.utils.Result
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FragmentCocktailPage : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentCocktailPageBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onStart() {
        Log.d("cocktailPage", "OnStart()")
        super.onStart()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewModel: CocktailViewModel by activityViewModels()
        val binding = FragmentCocktailPageBinding.bind(view)
        binding.apply {
            fab.setOnClickListener {
                (it as FloatingActionButton).setImageResource(
                    R.drawable.ic_baseline_favorite_56
                )
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiFlow.collect { uiState ->
                    Log.d("uiFlow", "Collected in cocktailpage")
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
                        }
                    }

                }
            }
        }
    }

}

