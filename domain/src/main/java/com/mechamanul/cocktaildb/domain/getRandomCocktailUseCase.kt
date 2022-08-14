package com.mechamanul.cocktaildb.domain

import com.mechamanul.cocktaildb.utils.Result
import kotlinx.coroutines.flow.Flow

interface getRandomCocktailUseCase {
    suspend fun invoke(): Flow<Result<Cocktail>>
}