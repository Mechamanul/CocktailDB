package com.mechamanul.cocktaildb.domain

import com.mechamanul.cocktaildb.utils.Result
import kotlinx.coroutines.flow.Flow

interface getVisitedCocktailsUseCase {
    suspend fun invoke(): Result<Flow<List<Cocktail>>>
}