package com.mechamanul.cocktaildb.ui.cocktail

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

class CocktailViewPagerAdapter(
    fm: FragmentManager,
    lifecycle: Lifecycle,
    list: ArrayList<Fragment>
) : FragmentStateAdapter(fm, lifecycle) {
    private val fragmentsList = list
    override fun getItemCount(): Int {
        return fragmentsList.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragmentsList[position]
    }
}