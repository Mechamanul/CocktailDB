package com.mechamanul.cocktaildb.ui.start_page

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.mechamanul.cocktaildb.domain.Cocktail
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.mechamanul.cocktaildb.ui.start_page.VisitedCocktailsRecyclerView.VisitedCocktailViewHolder


class DiffCallback : DiffUtil.ItemCallback<Cocktail>() {
    override fun areItemsTheSame(oldItem: Cocktail, newItem: Cocktail): Boolean =
        oldItem.id == newItem.id


    override fun areContentsTheSame(oldItem: Cocktail, newItem: Cocktail): Boolean =
        oldItem == newItem

}

class VisitedCocktailsRecyclerView() :
    ListAdapter<Cocktail, VisitedCocktailViewHolder>(DiffCallback()) {
    inner class VisitedCocktailViewHolder(itemView: View) : ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VisitedCocktailViewHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: VisitedCocktailViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

}
