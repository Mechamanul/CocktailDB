package com.mechamanul.cocktaildb.data.repository

import com.mechamanul.cocktaildb.domain.model.Cocktail
import com.mechamanul.cocktaildb.domain.repository.CocktailRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class CocktailRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteCocktailDataSource,
    private val localDataSource: LocalCocktailDataSource
) :
    CocktailRepository {
    override suspend fun getRandomCocktail(): Cocktail {
        return remoteDataSource.getRandomCocktail()
    }

    override suspend fun searchCocktailByName(name: String): List<Cocktail> {
        return remoteDataSource.searchCocktailByName(name)
    }

    override suspend fun getCocktailById(id: Int): Cocktail {

        return localDataSource.getCocktailById(id)?:remoteDataSource.getCocktailById(id)
    }

    override suspend fun getVisitedCocktailsList(): Flow<List<Cocktail>> {
        return localDataSource.getVisitedCocktails()
    }

    override suspend fun saveCocktail(cocktail: Cocktail): Boolean {
        return try {
            localDataSource.saveCocktailAndIngredientsToDatabase(cocktail)
            true
        } catch (e: Exception) {
            false
        }
    }

    override suspend fun changeLikeState(cocktailId: Int, favourite: Boolean) {
        return localDataSource.changeLikeState(cocktailId, favourite)
    }

    override suspend fun getFavouriteCocktailsFlow(): Flow<List<Cocktail>> {
        return localDataSource.getFavouriteCocktailsFlow()

    }


    override suspend fun getListOfCategories(): List<String> {

        var categories = localDataSource.getListOfCategories()
        if (categories.isEmpty()) {
            categories = remoteDataSource.getListOfCategories()
            localDataSource.insertListOfCategories(categories)
        }
        return categories
    }

    override suspend fun getCocktailsByCategoryName(categoryName: String): List<Cocktail> {
        return remoteDataSource.getCocktailsByCategoryName(categoryName)
    }


}