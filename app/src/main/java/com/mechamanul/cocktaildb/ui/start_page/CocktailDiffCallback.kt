package com.mechamanul.cocktaildb.ui.start_page

import androidx.recyclerview.widget.DiffUtil
import com.mechamanul.cocktaildb.domain.Cocktail

class CocktailDiffCallback : DiffUtil.ItemCallback<Cocktail>() {
    override fun areItemsTheSame(oldItem: Cocktail, newItem: Cocktail): Boolean =
        oldItem.id == newItem.id


    override fun areContentsTheSame(oldItem: Cocktail, newItem: Cocktail): Boolean =
        oldItem == newItem

}