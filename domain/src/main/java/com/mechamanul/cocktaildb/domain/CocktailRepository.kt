package com.mechamanul.cocktaildb.domain

import com.mechamanul.cocktaildb.utils.Result

interface CocktailRepository {
    suspend fun getRandomCocktail(): Result<Cocktail>
    suspend fun searchCocktailByName(name: String): Result<Cocktail>
    suspend fun getCocktailById(id: Int): Cocktail
    suspend fun getVisitedCocktailsList(): List<Cocktail>
}