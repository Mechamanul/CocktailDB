package com.mechamanul.cocktaildb.data.remote

import com.mechamanul.cocktaildb.data.repository.RemoteCocktailDataSource
import com.mechamanul.cocktaildb.domain.Cocktail
import com.mechamanul.cocktaildb.domain.Ingredient
import java.net.SocketTimeoutException
import javax.inject.Inject
import kotlin.Exception

class RemoteCocktailDataSourceImpl @Inject constructor(private val cocktailService: CocktailService) :
    RemoteCocktailDataSource {
    override suspend fun getRandomCocktail(): Cocktail {
        try {
            val response = cocktailService.getRandomCocktail()
            if (response.isSuccessful) {
                val result = response.body()

                result?.let {
                    return it.drinks[0].mapToDomain()
                }
            }

        } catch (e: Exception) {
            throw e
        }
        throw Exception("Error during getting random cocktail")

    }

    override suspend fun searchCocktailByName(name: String): Cocktail? {
        try {
            val response = cocktailService.searchCocktailByName(name)
            if (response.isSuccessful) {
                val result = response.body()
                result?.let {
                    return it.drinks[0].mapToDomain()
                }
            }
        } catch (e: Exception) {
            throw e
        }
        throw Exception("Error during searching cocktail by name")
    }


    private fun Drink.mapToDomain(): Cocktail {
        return Cocktail(
            id = id,
            name = name,
            category = category,
            type = type,
            glass = glass,
            imageUrl = imageUrl,
            instruction = instruction,
            listOfIngredients = listOfIngredients
        )
    }
}