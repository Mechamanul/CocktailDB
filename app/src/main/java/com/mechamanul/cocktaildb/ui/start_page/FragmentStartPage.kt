package com.mechamanul.cocktaildb.ui.start_page

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.google.android.material.snackbar.Snackbar
import com.mechamanul.cocktaildb.R
import com.mechamanul.cocktaildb.databinding.FragmentStartPageBinding
import com.mechamanul.cocktaildb.ui.start_page.StartPageViewModel.StartPageEvents.CocktailSearchResult.Failure
import com.mechamanul.cocktaildb.ui.start_page.StartPageViewModel.StartPageEvents.CocktailSearchResult.Success
import com.mechamanul.cocktaildb.ui.start_page.StartPageViewModel.StartPageEvents.VisitedCocktailsLoaded
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


@AndroidEntryPoint
class FragmentStartPage : Fragment(), IImplementImageDrawerCallback {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val binding = FragmentStartPageBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val viewModel: StartPageViewModel by viewModels()
        val adapter = VisitedCocktailsAdapter(this)
        val binding = FragmentStartPageBinding.bind(view)
        binding.apply {
            visitedCocktailsRv.adapter = adapter
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiEvents.collect { event ->
                    when (event) {
                        is Failure -> Snackbar.make(
                            binding.root,
                            event.exception.message.toString(),
                            Snackbar.LENGTH_LONG
                        ).show()
                        is Success -> {
                            // TODO: fix navigation here(somehow argument constructor is wrong)
                            val action =
                                FragmentStartPageDirections.actionFragmentStartPageToFragmentCocktailBase(
                                    event.cocktail.id
                                )
                            findNavController().navigate(action)
                        }
                        is VisitedCocktailsLoaded -> adapter.submitList(event.cocktails)
                    }
                }
            }

            super.onViewCreated(view, savedInstanceState)
        }


    }

    override fun drawImageCallback(imageView: ImageView, url: String) {
        Glide.with(requireContext()).asBitmap()
            .load(url).centerCrop()
            .transform(CenterCrop(), RoundedCorners(37)).into(imageView)
    }
}

interface IImplementImageDrawerCallback {
    fun drawImageCallback(imageView: ImageView, url: String)
}