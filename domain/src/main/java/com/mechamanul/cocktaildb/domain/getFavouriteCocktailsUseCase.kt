package com.mechamanul.cocktaildb.domain

import com.mechamanul.cocktaildb.domain.model.Cocktail
import com.mechamanul.cocktaildb.utils.Result
import kotlinx.coroutines.flow.Flow

interface getFavouriteCocktailsUseCase {
    suspend fun invoke(): Result<Flow<List<Cocktail>>>
}