package com.mechamanul.cocktaildb.data.repository

import com.mechamanul.cocktaildb.domain.Cocktail
import com.mechamanul.cocktaildb.domain.CocktailRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.withContext
import javax.inject.Inject


class CocktailRepositoryImpl @Inject constructor(private val remoteDataSource: RemoteCocktailDataSource) :
    CocktailRepository {
    override suspend fun getRandomCocktail(): Cocktail {
        return remoteDataSource.getRandomCocktail()

    }

}