package com.mechamanul.cocktaildb.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.mechamanul.cocktaildb.data.local.model.*
import kotlinx.coroutines.flow.Flow

@Dao
interface CocktailDao {
    @Transaction
    @Query("SELECT * FROM cocktails ORDER BY createdAt DESC")
    fun getVisitedCocktails(): Flow<List<CocktailWithIngredients>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertIngredient(ingredient: IngredientEntity): Long

    @Insert
    suspend fun insertCocktail(cocktail: CocktailEntity): Long

    @Insert
    suspend fun insertCocktailIngredientCrossRef(cocktailIngredientsCrossRef: CocktailIngredientsCrossRef)

    @Query("Select ingredientId from ingrediententity where name=:name")
    suspend fun searchIngredientByName(name: String): Long?

    @Transaction
    @Query("SELECT * FROM cocktails WHERE cocktailId=:id")
    suspend fun getCocktailById(id: Int): CocktailWithIngredients?

    @Query("UPDATE cocktails SET isFavourite = :isFavourite WHERE cocktailId=:id")
    suspend fun setIsFavourite(id: Int, isFavourite: Boolean)

    @Transaction
    suspend fun insertCocktailWithIngredients(
        cocktail: CocktailEntity,
        ingredientsMeasure: Map<String, String>,
    ) {
        val cocktailId = insertCocktail(cocktail)
        ingredientsMeasure.entries.forEach {
            val ingredientId =
                searchIngredientByName(it.key) ?: insertIngredient(IngredientEntity(name = it.key))
            insertCocktailIngredientCrossRef(
                CocktailIngredientsCrossRef(
                    cocktailId = cocktailId,
                    ingredientId = ingredientId,
                    measure = it.value
                )
            )
        }

    }

    @Query("SELECT * FROM categories")
    suspend fun getCategories(): List<CategoryEntity>

    @Insert
    suspend fun insertCategories(vararg categories: CategoryEntity)

    @Transaction
    @Query("SELECT * FROM cocktails WHERE isFavourite=1")
    fun getFavouriteCocktails(): Flow<List<CocktailWithIngredients>>

}