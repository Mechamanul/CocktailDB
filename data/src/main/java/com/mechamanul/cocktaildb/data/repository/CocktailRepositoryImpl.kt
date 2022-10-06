package com.mechamanul.cocktaildb.data.repository

import com.mechamanul.cocktaildb.domain.Cocktail
import com.mechamanul.cocktaildb.domain.CocktailRepository
import com.mechamanul.cocktaildb.utils.Result
import javax.inject.Inject


class CocktailRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteCocktailDataSource,
    private val localDataSource: LocalCocktailDataSource
) :
    CocktailRepository {
    override suspend fun getRandomCocktail(): Result<Cocktail> {
        return try {
            val cocktail = remoteDataSource.getRandomCocktail()
            Result.Success(cocktail)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override suspend fun searchCocktailByName(name: String): Result<List<Cocktail>> {
        return try {
            val cocktails = remoteDataSource.searchCocktailByName(name)
            Result.Success(cocktails)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override suspend fun getCocktailById(id: Int): Cocktail {
        return localDataSource.getCocktailById(id)
    }

    override suspend fun getVisitedCocktailsList(): Result<List<Cocktail>> {
        val cocktails =  localDataSource.getVisitedCocktails()
        return Result.Success(cocktails)
    }

}