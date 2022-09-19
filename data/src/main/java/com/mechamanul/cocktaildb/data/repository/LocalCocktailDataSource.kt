package com.mechamanul.cocktaildb.data.repository

import com.mechamanul.cocktaildb.domain.Cocktail

interface LocalCocktailDataSource {
    suspend fun getVisitedCocktails(): List<Cocktail>
}