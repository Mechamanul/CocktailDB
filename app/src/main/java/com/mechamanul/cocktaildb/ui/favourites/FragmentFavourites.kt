package com.mechamanul.cocktaildb.ui.favourites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mechamanul.cocktaildb.databinding.FragmentFavouritesBinding
import com.mechamanul.cocktaildb.ui.BaseFragment

class FragmentFavourites : BaseFragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentFavouritesBinding.inflate(inflater).root

    }
}