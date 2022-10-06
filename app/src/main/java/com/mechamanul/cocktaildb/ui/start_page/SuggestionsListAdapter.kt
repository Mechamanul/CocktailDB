package com.mechamanul.cocktaildb.ui.start_page

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mechamanul.cocktaildb.R
import com.mechamanul.cocktaildb.databinding.SuggestionItemBinding
import com.mechamanul.cocktaildb.domain.Cocktail
import com.mechamanul.cocktaildb.ui.start_page.SuggestionsListAdapter.SuggestionViewHolder

class SuggestionsListAdapter :
    ListAdapter<Cocktail, SuggestionViewHolder>(CocktailDiffCallback()) {
    inner class SuggestionViewHolder(val binding: SuggestionItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
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

