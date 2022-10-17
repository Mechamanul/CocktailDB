package com.mechamanul.cocktaildb.domain

import com.mechamanul.cocktaildb.utils.Result

interface getCocktailCategoriesUseCase {
    suspend fun invoke(): Result<List<String>>
}