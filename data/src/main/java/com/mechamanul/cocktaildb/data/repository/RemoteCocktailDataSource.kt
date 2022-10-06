package com.mechamanul.cocktaildb.data.repository

import com.mechamanul.cocktaildb.domain.Cocktail
import com.mechamanul.cocktaildb.domain.Ingredient

interface RemoteCocktailDataSource {
    suspend fun getRandomCocktail(): Cocktail
    suspend fun searchCocktailByName(name: String): List<Cocktail>
}
