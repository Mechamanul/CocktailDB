package com.mechamanul.cocktaildb.data.remote

import com.mechamanul.cocktaildb.data.repository.RemoteCocktailDataSource
import com.mechamanul.cocktaildb.domain.Cocktail
import com.mechamanul.cocktaildb.domain.Ingredient
import com.mechamanul.cocktaildb.utils.Result
import javax.inject.Inject

class RemoteCocktailDataSourceImpl @Inject constructor(private val cocktailService: CocktailService) :
    RemoteCocktailDataSource {
    override suspend fun getRandomCocktail(): Result<Cocktail> {
        val response = cocktailService.getRandomCocktail()
        if (response.isSuccessful) {
            val result = response.body()
            result?.let {
                return Result.Success(it.mapToDomain())
            } ?: Result.Error(throwable = Throwable("Error during api call"))
        }
        return Result.Error(Throwable("Error with internet connection"))
    }

    private fun CocktailResponse.mapToDomain(): Cocktail {
        return Cocktail(
            id = id.toInt(),
            name = name,
            category = category,
            type = type,
            glass = glass,
            imageUrl = imageUrl,
            listOfIngredients = listOf(Ingredient("Gin", "1/2oz"))
        )
    }
}