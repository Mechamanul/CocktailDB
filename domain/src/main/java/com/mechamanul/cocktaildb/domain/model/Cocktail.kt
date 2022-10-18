package com.mechamanul.cocktaildb.domain.model


data class Cocktail(
    val id: Int,
    val name: String,
    val category: String?,
    val type: String?,
    val glass: String?,
    val imageUrl: String,
    val instruction: String?,
    val listOfIngredients: List<Ingredient> = listOf(),
    val isFavourite: Boolean?
)
