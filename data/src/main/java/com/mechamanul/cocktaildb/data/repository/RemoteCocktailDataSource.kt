package com.mechamanul.cocktaildb.data.repository

import com.mechamanul.cocktaildb.domain.Cocktail

interface RemoteCocktailDataSource {
    suspend fun getRandomCocktail(): Cocktail
}
