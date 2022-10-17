package com.mechamanul.cocktaildb.domain

import com.mechamanul.cocktaildb.domain.model.Cocktail
import com.mechamanul.cocktaildb.utils.Result

interface getRandomCocktailUseCase {
    suspend fun invoke(): Result<Cocktail>
}