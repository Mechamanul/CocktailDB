package com.mechamanul.cocktaildb.ui.start_page

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.mechamanul.cocktaildb.domain.Cocktail
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.mechamanul.cocktaildb.databinding.VisitedCocktailsItemBinding
import com.mechamanul.cocktaildb.ui.start_page.VisitedCocktailsAdapter.VisitedCocktailViewHolder


class DiffCallback : DiffUtil.ItemCallback<Cocktail>() {
    override fun areItemsTheSame(oldItem: Cocktail, newItem: Cocktail): Boolean =
        oldItem.id == newItem.id


    override fun areContentsTheSame(oldItem: Cocktail, newItem: Cocktail): Boolean =
        oldItem == newItem

}

class VisitedCocktailsAdapter(val drawImageAsyncCallback: IImplementImageDrawerCallback) :
    ListAdapter<Cocktail, VisitedCocktailViewHolder>(DiffCallback()) {
    inner class VisitedCocktailViewHolder(val binding: VisitedCocktailsItemBinding) :
        ViewHolder(binding.root) {
        fun bind(item: Cocktail) {
            binding.apply {
                cocktailName.text = item.name
                drawImageAsyncCallback.drawImageCallback(
                    url = item.imageUrl,
                    imageView = cocktailImage
                )
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VisitedCocktailViewHolder {
        val binding =
            VisitedCocktailsItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VisitedCocktailViewHolder(binding)
    }

    override fun onBindViewHolder(holder: VisitedCocktailViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem)
    }

}