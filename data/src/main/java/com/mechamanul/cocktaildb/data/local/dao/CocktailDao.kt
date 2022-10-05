package com.mechamanul.cocktaildb.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.mechamanul.cocktaildb.data.local.model.CocktailEntity
import com.mechamanul.cocktaildb.data.local.model.CocktailIngredientsCrossRef
import com.mechamanul.cocktaildb.data.local.model.CocktailWithIngredients
import com.mechamanul.cocktaildb.data.local.model.IngredientEntity

@Dao
interface CocktailDao {
    @Transaction
    @Query("SELECT * FROM cocktails")
    suspend fun getVisitedCocktails(): List<CocktailWithIngredients>

    @Insert
    suspend fun insertIngredient(ingredient: IngredientEntity): Long

    @Insert
    suspend fun insertCocktail(cocktail: CocktailEntity): Long

    @Insert
    suspend fun insertCocktailIngredientCrossRef(cocktailIngredientsCrossRef: CocktailIngredientsCrossRef)

    @Query("SELECT * FROM cocktails WHERE cocktailId=:id")
    suspend fun getCocktailById(id: Int): CocktailWithIngredients


    @Transaction
    suspend fun insertCocktailWithIngredients(
        cocktail: CocktailEntity,
        ingredientsMeasure: Map<String, String>
    ) {
        val cocktailId = insertCocktail(cocktail)
        ingredientsMeasure.entries.forEach {
            val ingredientId = insertIngredient(IngredientEntity(name = it.key))
            insertCocktailIngredientCrossRef(
                CocktailIngredientsCrossRef(
                    cocktailId,
                    ingredientId,
                    it.value
                )
            )
        }

    }

}