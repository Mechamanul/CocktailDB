package com.mechamanul.cocktaildb.data.repository

import com.mechamanul.cocktaildb.domain.model.Cocktail

interface RemoteCocktailDataSource {
    suspend fun getRandomCocktail(): Cocktail
    suspend fun searchCocktailByName(name: String): List<Cocktail>
    suspend fun getListOfCategories(): List<String>
    suspend fun getCocktailsByCategoryName(categoryName: String): List<Cocktail>
    suspend fun getCocktailById(id: Int): Cocktail
}
