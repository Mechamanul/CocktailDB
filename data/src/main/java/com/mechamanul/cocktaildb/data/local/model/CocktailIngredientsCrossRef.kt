package com.mechamanul.cocktaildb.data.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity


@Entity(primaryKeys = ["cocktailId", "ingredientId"])
data class CocktailIngredientsCrossRef(
    val cocktailId: Long, @ColumnInfo(index = true) val ingredientId: Long, val measure: String
)