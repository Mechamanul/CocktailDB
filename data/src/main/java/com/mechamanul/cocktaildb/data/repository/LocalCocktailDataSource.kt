package com.mechamanul.cocktaildb.data.repository

import com.mechamanul.cocktaildb.domain.model.Cocktail
import kotlinx.coroutines.flow.Flow

interface LocalCocktailDataSource {
    suspend fun getVisitedCocktails(): Flow<List<Cocktail>>
    suspend fun saveCocktailAndIngredientsToDatabase(cocktail: Cocktail)
    suspend fun getCocktailById(id: Int): Cocktail
    suspend fun changeLikeState(cocktailId: Int, favourite: Boolean)
    suspend fun getFavouriteCocktailsFlow(): Flow<List<Cocktail>>
    suspend fun insertListOfCategories(categories: List<String>)
}