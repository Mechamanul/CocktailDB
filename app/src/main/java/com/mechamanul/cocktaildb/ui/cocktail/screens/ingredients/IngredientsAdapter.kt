package com.mechamanul.cocktaildb.ui.cocktail.screens.ingredients

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mechamanul.cocktaildb.R
import com.mechamanul.cocktaildb.databinding.CocktailIngredientBinding
import com.mechamanul.cocktaildb.domain.Ingredient

class IngredientsAdapter : RecyclerView.Adapter<IngredientsAdapter.IngredientsViewHolder>() {

    private var listOfIngredients: List<Ingredient> = listOf()

    inner class IngredientsViewHolder(private val binding: CocktailIngredientBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Ingredient) {
            binding.apply {
                ingredient.text = item.name
                measure.text = item.measure
            }
        }
    }

    fun submit(newList: List<Ingredient>) {
        listOfIngredients = newList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredientsViewHolder {
        val binding =
            CocktailIngredientBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return IngredientsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: IngredientsViewHolder, position: Int) {
        holder.bind(listOfIngredients[position])
    }

    override fun getItemCount(): Int {
        return listOfIngredients.size
    }
}