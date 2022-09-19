package com.mechamanul.cocktaildb.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import com.mechamanul.cocktaildb.domain.Cocktail

@Dao
interface CocktailDao {
    @Query("SELECT * FROM cocktails")
    suspend fun getVisitedCocktails(): List<Cocktail>
}