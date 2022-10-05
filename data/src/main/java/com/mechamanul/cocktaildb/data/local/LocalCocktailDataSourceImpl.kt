package com.mechamanul.cocktaildb.data.local

import com.mechamanul.cocktaildb.data.local.dao.CocktailDao
import com.mechamanul.cocktaildb.data.local.model.CocktailEntity
import com.mechamanul.cocktaildb.data.local.model.CocktailIngredientsCrossRef
import com.mechamanul.cocktaildb.data.local.model.CocktailWithIngredients
import com.mechamanul.cocktaildb.data.local.model.IngredientEntity
import com.mechamanul.cocktaildb.data.repository.LocalCocktailDataSource
import com.mechamanul.cocktaildb.domain.Cocktail
import com.mechamanul.cocktaildb.domain.Ingredient
import java.io.IOException
import javax.inject.Inject

class LocalCocktailDataSourceImpl @Inject constructor(private val cocktailDao: CocktailDao) :
    LocalCocktailDataSource {
    override suspend fun getVisitedCocktails(): List<Cocktail> {
        return cocktailDao.getVisitedCocktails()
            .map { cocktailWithIngredients -> cocktailWithIngredients.mapToDomain() }
    }

    override suspend fun saveCocktailAndIngredientsToDatabase(cocktail: Cocktail) {
        cocktailDao.insertCocktailWithIngredients(
            cocktail.mapFromDomain(),
            cocktail.listOfIngredients.associate { it.name to it.measure }
        )
    }

    override suspend fun getCocktailById(id: Int): Cocktail {
        return cocktailDao.getCocktailById(id).mapToDomain()
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
            listOfIngredients = mergeCrossRefWithIngredients(crossRefs, ingredients)
        )

    }

    private fun mergeCrossRefWithIngredients(
        crossRefs: List<CocktailIngredientsCrossRef>,
        ingredients: List<IngredientEntity>
    ): List<Ingredient> {
        val refsAssociated = crossRefs.associateBy { it.ingredientId }
        val ingredientsAssociated = ingredients.associateBy { it.ingredientId }
        return refsAssociated.map { refEntry ->
            ingredientsAssociated[refEntry.key]?.let { ingredientEntity ->
                Ingredient(
                    ingredientEntity.name, refEntry.value.measure
                )
            } ?: throw IOException("Amount of crossrefs is not equal to amount of ingredients")
        }

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

}