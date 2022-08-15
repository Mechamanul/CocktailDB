package com.mechamanul.cocktaildb.domain

data class Cocktail(
    val id: Int,
    val name: String,
    val category: String,
    val type: String,
    val glass: String,
    val imageUrl: String,
    val listOfIngredients: List<Ingredient>
)
