package com.mechamanul.cocktaildb.domain

import com.mechamanul.cocktaildb.utils.Result

interface searchCocktailByNameUseCase {
    suspend fun invoke(name: String): Result<Cocktail>
}