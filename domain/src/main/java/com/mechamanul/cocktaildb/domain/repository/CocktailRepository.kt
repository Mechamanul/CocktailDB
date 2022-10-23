package com.mechamanul.cocktaildb.domain.repository

import com.mechamanul.cocktaildb.domain.model.Cocktail
import kotlinx.coroutines.flow.Flow

interface CocktailRepository {
    suspend fun getRandomCocktail(): Cocktail
    suspend fun searchCocktailByName(name: String): List<Cocktail>
    suspend fun getCocktailById(id: Int): Cocktail
    suspend fun getVisitedCocktailsList(): Flow<List<Cocktail>>
    suspend fun saveCocktail(cocktail: Cocktail): Boolean
    suspend fun changeLikeState(cocktailId: Int, favourite: Boolean)
    suspend fun getFavouriteCocktailsFlow(): Flow<List<Cocktail>>
    suspend fun getListOfCategories(): List<String>
    suspend fun getCocktailsByCategoryName(categoryName: String): List<Cocktail>
    suspend fun getCocktailLikeState(id: Int): Flow<Boolean>
}