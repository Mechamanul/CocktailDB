package com.mechamanul.cocktaildb.ui.pages.search_category

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mechamanul.cocktaildb.databinding.FragmentSearchByCategoryBinding
import com.mechamanul.cocktaildb.ui.BaseFragment

class FragmentSearchByCategory : BaseFragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentSearchByCategoryBinding.inflate(inflater).root
    }
}