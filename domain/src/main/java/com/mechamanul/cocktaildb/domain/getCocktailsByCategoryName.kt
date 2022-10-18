package com.mechamanul.cocktaildb.domain

import com.mechamanul.cocktaildb.domain.model.Cocktail
import com.mechamanul.cocktaildb.utils.Result

interface getCocktailsByCategoryName {
    suspend fun invoke(categoryName:String): Result<List<Cocktail>>
}