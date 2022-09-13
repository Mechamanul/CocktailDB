package com.mechamanul.cocktaildb.domain

import kotlinx.coroutines.flow.Flow

interface CocktailRepository {
    suspend fun getRandomCocktail():Cocktail
}