package com.mechamanul.cocktaildb.domain

import com.mechamanul.cocktaildb.utils.Result
import kotlinx.coroutines.flow.Flow

interface CocktailRepository {
    suspend fun getRandomCocktail(): Result<Cocktail>
    suspend fun searchCocktailByName(name: String): Result<List<Cocktail>>
    suspend fun getCocktailById(id: Int): Cocktail
    suspend fun getVisitedCocktailsList(): Result<Flow<List<Cocktail>>>
    suspend fun saveCocktail(cocktail: Cocktail):Boolean
}