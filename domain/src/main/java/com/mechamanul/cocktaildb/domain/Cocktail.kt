package com.mechamanul.cocktaildb.domain

data class Cocktail(
    val id: Int = 0,
    val name: String ,
    val category: String ,
    val type: String ,
    val glass: String ,
    val imageUrl: String ,
    val instruction:String,
    val listOfIngredients: List<Ingredient> = listOf()
)
