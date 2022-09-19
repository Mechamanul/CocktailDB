package com.mechamanul.cocktaildb.ui.start_page

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.mechamanul.cocktaildb.databinding.FragmentStartPageBinding
import dagger.hilt.android.AndroidEntryPoint


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
        val adapter = VisitedCocktailsAdapter(this)
        val binding = FragmentStartPageBinding.bind(view)
        binding.apply {
            visitedCocktailsRv.adapter = adapter
        }
        super.onViewCreated(view, savedInstanceState)
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