package com.mechamanul.cocktaildb.data.repository

import com.mechamanul.cocktaildb.domain.Cocktail
import com.mechamanul.cocktaildb.domain.CocktailRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.withContext
import javax.inject.Inject


class CocktailRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteCocktailDataSource,
    private val localDataSource: LocalCocktailDataSource
) :
    CocktailRepository {
    override suspend fun getRandomCocktail(): Cocktail {
        return remoteDataSource.getRandomCocktail()

    }

    override suspend fun searchCocktailByName(name: String): Cocktail {
        TODO("Not yet implemented")
    }

    override suspend fun getCocktailById(id: Int): Cocktail {
        TODO("Not yet implemented")
    }

    override suspend fun getVisitedCocktailsList(): List<Cocktail> {
        return localDataSource.getVisitedCocktails()
    }

}