package com.mechamanul.cocktaildb.domain

import com.mechamanul.cocktaildb.utils.Result
import kotlinx.coroutines.flow.Flow

interface CocktailRepository {
    fun getRandomCocktail(): Flow<Result<Cocktail>>
}