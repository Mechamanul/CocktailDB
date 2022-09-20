package com.mechamanul.cocktaildb.data.local

import com.mechamanul.cocktaildb.data.local.dao.CocktailDao
import com.mechamanul.cocktaildb.data.local.model.CocktailEntity
import com.mechamanul.cocktaildb.data.local.model.CocktailWithIngredients
import com.mechamanul.cocktaildb.data.local.model.IngredientEntity
import com.mechamanul.cocktaildb.data.repository.LocalCocktailDataSource
import com.mechamanul.cocktaildb.domain.Cocktail
import com.mechamanul.cocktaildb.domain.Ingredient
import javax.inject.Inject

class LocalCocktailDataSourceImpl @Inject constructor(private val cocktailDao: CocktailDao) :
    LocalCocktailDataSource {
    override suspend fun getVisitedCocktails(): List<Cocktail> {
        return cocktailDao.getVisitedCocktails()
            .map { cocktailWithIngredients -> cocktailWithIngredients.mapToDomain() }
    }

    override suspend fun saveCocktailAndIngredientsToDatabase(cocktail: Cocktail): Boolean {
        try {
            val cocktailEntity = cocktail.mapFromDomain()
            cocktailDao.insertCocktail(cocktailEntity)
            cocktail.listOfIngredients.forEach {
                cocktailDao.insertIngredient(IngredientEntity(name = it.name, measure = it.measure))
            }
            return true
        } catch (e: Exception) {
            throw e
        }

    }

    private fun CocktailWithIngredients.mapToDomain(): Cocktail {

        return Cocktail(
            id = cocktail.cocktailId.toInt(),
            name = cocktail.name,
            category = cocktail.category,
            type = cocktail.type,
            glass = cocktail.glass,
            imageUrl = cocktail.imageUrl,
            instruction = cocktail.instruction,
            listOfIngredients = ingredients.map { ingredientEntity -> ingredientEntity.mapToDomain() }
        )

    }


    private fun Cocktail.mapFromDomain(): CocktailEntity {
        return CocktailEntity(
            name = name,
            cocktailId = id.toLong(),
            category = category,
            type = type,
            glass = glass,
            imageUrl = imageUrl,
            instruction = instruction
        )
    }

    private fun IngredientEntity.mapToDomain(): Ingredient {
        return Ingredient(name = name, measure = measure)
    }
}