package com.mechamanul.cocktaildb.domain

import kotlinx.coroutines.flow.Flow

interface CocktailRepository {
    suspend fun getRandomCocktail(): Cocktail
    suspend fun searchCocktailByName(name: String): Cocktail
    suspend fun getCocktailById(id: Int): Cocktail
    suspend fun getVisitedCocktailsList(): List<Cocktail>
}