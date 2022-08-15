package com.mechamanul.cocktaildb.data.repository

import com.mechamanul.cocktaildb.domain.Cocktail
import com.mechamanul.cocktaildb.domain.CocktailRepository
import com.mechamanul.cocktaildb.utils.Result
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.withContext
import javax.inject.Inject


class CocktailRepositoryImpl @Inject constructor(private val remoteDataSource: RemoteCocktailDataSource) :
    CocktailRepository {
    override suspend fun getRandomCocktail(): Flow<Result<Cocktail>> {
        return flow {
            emit(Result.Loading)
            val apiResponse = remoteDataSource.getRandomCocktail()
            emit(apiResponse)
        }.shareIn(CoroutineScope(Dispatchers.IO), SharingStarted.WhileSubscribed(), 1)
    }

}