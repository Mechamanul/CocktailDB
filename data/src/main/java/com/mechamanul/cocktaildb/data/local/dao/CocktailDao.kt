package com.mechamanul.cocktaildb.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.mechamanul.cocktaildb.data.local.model.CocktailEntity
import com.mechamanul.cocktaildb.data.local.model.CocktailWithIngredients
import com.mechamanul.cocktaildb.data.local.model.IngredientEntity
import com.mechamanul.cocktaildb.domain.Cocktail
import com.mechamanul.cocktaildb.domain.Ingredient

@Dao
interface CocktailDao {
    @Transaction
    @Query("SELECT * FROM cocktails")
    suspend fun getVisitedCocktails(): List<CocktailWithIngredients>

    @Insert
    suspend fun insertIngredient(ingredient: IngredientEntity)

    @Insert
    suspend fun insertCocktail(cocktail: CocktailEntity)
}