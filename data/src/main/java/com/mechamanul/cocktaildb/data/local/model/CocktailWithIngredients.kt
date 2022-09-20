package com.mechamanul.cocktaildb.data.local.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Junction
import androidx.room.Relation

data class CocktailWithIngredients(
    @Embedded val cocktail: CocktailEntity,
    @Relation(
        parentColumn = "cocktailId",
        entityColumn = "ingredientId",
        associateBy = Junction(CocktailIngredientsCrossRef::class)
    )
    val ingredients: List<IngredientEntity>
)
