package com.mechamanul.cocktaildb.data.remote

import com.mechamanul.cocktaildb.data.repository.RemoteCocktailDataSource
import com.mechamanul.cocktaildb.domain.Cocktail
import com.mechamanul.cocktaildb.domain.Ingredient
import java.lang.Exception
import javax.inject.Inject

class RemoteCocktailDataSourceImpl @Inject constructor(private val cocktailService: CocktailService) :
    RemoteCocktailDataSource {
    override suspend fun getRandomCocktail(): Cocktail {
        val response = cocktailService.getRandomCocktail()
        if (response.isSuccessful) {
            val result = response.body()

            result?.let {
                return it.drinks[0].mapToDomain()
            }
            throw Exception("Error during api call")
        }
        throw Exception("Error during getting random cocktail")

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