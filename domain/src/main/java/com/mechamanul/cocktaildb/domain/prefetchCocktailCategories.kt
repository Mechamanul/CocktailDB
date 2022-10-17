package com.mechamanul.cocktaildb.domain

import com.mechamanul.cocktaildb.utils.Result

interface prefetchCocktailCategories {
    suspend fun invoke(): Result<Boolean>
}