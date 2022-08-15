package com.mechamanul.cocktaildb.data.repository

import com.mechamanul.cocktaildb.domain.Cocktail
import com.mechamanul.cocktaildb.utils.Result

interface RemoteCocktailDataSource {
    suspend fun getRandomCocktail(): Result<Cocktail>
}
