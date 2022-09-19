package com.mechamanul.cocktaildb.domain

interface searchCocktailByNameUseCase {
    suspend fun invoke(name: String): Cocktail
}