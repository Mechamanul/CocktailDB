package com.mechamanul.cocktaildb.data.local.model

import androidx.room.Entity


@Entity(primaryKeys = ["cocktailId", "ingredientId"])
data class CocktailIngredientsCrossRef(
    val cocktailId: Long, val ingredientId: Long
)