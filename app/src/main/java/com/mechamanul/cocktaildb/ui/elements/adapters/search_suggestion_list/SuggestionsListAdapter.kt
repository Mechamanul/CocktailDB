package com.mechamanul.cocktaildb.ui.elements.adapters.search_suggestion_list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mechamanul.cocktaildb.databinding.SuggestionItemBinding
import com.mechamanul.cocktaildb.domain.model.Cocktail
import com.mechamanul.cocktaildb.ui.elements.callbacks.CocktailDiffCallback
import com.mechamanul.cocktaildb.ui.elements.adapters.search_suggestion_list.SuggestionsListAdapter.SuggestionViewHolder
import com.mechamanul.cocktaildb.ui.pages.start_page.NavigationCallback

class SuggestionsListAdapter(val navigationCallback: NavigationCallback) :
    ListAdapter<Cocktail, SuggestionViewHolder>(CocktailDiffCallback()) {
    inner class SuggestionViewHolder(val binding: SuggestionItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
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
            binding.name.text = item.name
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SuggestionViewHolder {
        val binding =
            SuggestionItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SuggestionViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SuggestionViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem)
    }
}

