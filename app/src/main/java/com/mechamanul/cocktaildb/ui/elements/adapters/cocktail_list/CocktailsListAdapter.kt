package com.mechamanul.cocktaildb.ui.elements.adapters.cocktail_list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.mechamanul.cocktaildb.databinding.VisitedCocktailsItemBinding
import com.mechamanul.cocktaildb.domain.model.Cocktail
import com.mechamanul.cocktaildb.ui.elements.callbacks.CocktailDiffCallback
import com.mechamanul.cocktaildb.ui.elements.adapters.cocktail_list.CocktailsListAdapter.VisitedCocktailViewHolder
import com.mechamanul.cocktaildb.ui.pages.start_page.ImageDrawerCallback
import com.mechamanul.cocktaildb.ui.pages.start_page.NavigationCallback


class CocktailsListAdapter(
    val drawImageAsyncCallback: ImageDrawerCallback,
    val navigationCallback: NavigationCallback
) :
    ListAdapter<Cocktail, VisitedCocktailViewHolder>(CocktailDiffCallback()) {
    inner class VisitedCocktailViewHolder(val binding: VisitedCocktailsItemBinding) :
        ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val cocktail = getItem(position)
                    navigationCallback.navigateToCocktailDetails(cocktail)
                }
            }
        }

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
