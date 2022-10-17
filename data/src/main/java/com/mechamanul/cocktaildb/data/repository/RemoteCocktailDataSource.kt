package com.mechamanul.cocktaildb.data.repository

import com.mechamanul.cocktaildb.domain.model.Cocktail

interface RemoteCocktailDataSource {
    suspend fun getRandomCocktail(): Cocktail
    suspend fun searchCocktailByName(name: String): List<Cocktail>
    suspend fun getListOfCategories():List<String>
}
