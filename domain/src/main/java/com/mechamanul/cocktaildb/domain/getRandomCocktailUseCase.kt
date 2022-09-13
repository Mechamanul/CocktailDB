package com.mechamanul.cocktaildb.domain

import kotlinx.coroutines.flow.Flow

interface getRandomCocktailUseCase {
    suspend fun invoke(): Cocktail
}