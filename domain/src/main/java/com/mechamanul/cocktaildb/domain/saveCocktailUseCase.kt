package com.mechamanul.cocktaildb.domain

import com.mechamanul.cocktaildb.domain.model.Cocktail

interface saveCocktailUseCase {
    suspend fun invoke(cocktail: Cocktail): Boolean
}