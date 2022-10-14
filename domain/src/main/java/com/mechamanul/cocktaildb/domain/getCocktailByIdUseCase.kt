package com.mechamanul.cocktaildb.domain


interface getCocktailByIdUseCase {
    suspend fun invoke(id: Int): Cocktail
}