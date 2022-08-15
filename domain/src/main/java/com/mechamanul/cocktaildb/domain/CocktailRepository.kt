package com.mechamanul.cocktaildb.domain

import com.mechamanul.cocktaildb.utils.Result
import kotlinx.coroutines.flow.Flow

interface CocktailRepository {
    suspend fun getRandomCocktail(): Flow<Result<Cocktail>>
}