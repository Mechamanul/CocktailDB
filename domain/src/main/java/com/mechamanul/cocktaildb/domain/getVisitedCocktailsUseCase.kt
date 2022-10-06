package com.mechamanul.cocktaildb.domain

import com.mechamanul.cocktaildb.utils.Result

interface getVisitedCocktailsUseCase {
    suspend fun invoke(): Result<List<Cocktail>>
}