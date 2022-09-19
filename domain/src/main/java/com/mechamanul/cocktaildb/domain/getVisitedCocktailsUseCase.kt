package com.mechamanul.cocktaildb.domain

interface getVisitedCocktailsUseCase {
    suspend fun invoke(): List<Cocktail>
}