package com.mechamanul.cocktaildb.domain

import com.mechamanul.cocktaildb.domain.model.Cocktail


interface getCocktailByIdUseCase {
    suspend fun invoke(id: Int): Cocktail
}