package com.mechamanul.cocktaildb.domain

interface saveCocktailUseCase {
    suspend fun invoke(cocktail: Cocktail): Boolean
}