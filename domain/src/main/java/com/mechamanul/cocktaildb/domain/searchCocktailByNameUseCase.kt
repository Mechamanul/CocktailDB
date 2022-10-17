package com.mechamanul.cocktaildb.domain

import com.mechamanul.cocktaildb.domain.model.Cocktail
import com.mechamanul.cocktaildb.utils.Result

interface searchCocktailByNameUseCase {
    suspend fun invoke(name: String): Result<List<Cocktail>>
}