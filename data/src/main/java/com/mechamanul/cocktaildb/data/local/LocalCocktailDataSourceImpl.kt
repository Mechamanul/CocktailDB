package com.mechamanul.cocktaildb.data.local

import com.mechamanul.cocktaildb.data.local.dao.CocktailDao
import com.mechamanul.cocktaildb.data.local.model.*
import com.mechamanul.cocktaildb.data.repository.LocalCocktailDataSource
import com.mechamanul.cocktaildb.domain.common.AppException
import com.mechamanul.cocktaildb.domain.model.Cocktail
import com.mechamanul.cocktaildb.domain.model.Ingredient
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

class LocalCocktailDataSourceImpl @Inject constructor(private val cocktailDao: CocktailDao) :
    LocalCocktailDataSource {
    override suspend fun getVisitedCocktails(): Flow<List<Cocktail>> {
        return cocktailDao.getVisitedCocktails()
            .map { list -> list.map { cocktailEntity -> mapToDomain(cocktailEntity) } }
    }

    override suspend fun saveCocktailAndIngredientsToDatabase(cocktail: Cocktail) {
        cocktailDao.insertCocktailWithIngredients(
            cocktail.mapFromDomain(),
            cocktail.listOfIngredients.associate { it.name to it.measure }
        )
    }

    override suspend fun getCocktailById(id: Int): Cocktail? {
        return cocktailDao.getCocktailById(id)?.let { mapToDomain(it) }
    }

    override suspend fun changeLikeState(cocktailId: Int, favourite: Boolean) {
        return cocktailDao.setIsFavourite(cocktailId, favourite)
    }

    private fun mapToDomain(entity: CocktailWithIngredients): Cocktail {

        return Cocktail(

            id = entity.cocktail.cocktailId.toInt(),
            name = entity.cocktail.name,
            category = entity.cocktail.category,
            type = entity.cocktail.type,
            glass = entity.cocktail.glass,
            imageUrl = entity.cocktail.imageUrl,
            instruction = entity.cocktail.instruction,
            listOfIngredients = mergeCrossRefWithIngredients(entity.crossRefs, entity.ingredients),
            isFavourite = entity.cocktail.isFavourite
        )

    }

    private fun mergeCrossRefWithIngredients(
        crossRefs: List<CocktailIngredientsCrossRef>,
        ingredients: List<IngredientEntity>,
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
            category = category ?: throw Exception("category is null"),
            type = type ?: throw Exception("type is null"),
            glass = glass ?: throw Exception("glass is null"),
            imageUrl = imageUrl,
            instruction = instruction ?: throw Exception("instruction is null"),
        )
    }

    override suspend fun getFavouriteCocktailsFlow(): Flow<List<Cocktail>> {
        return cocktailDao.getFavouriteCocktails()
            .map { list -> list.map { cocktailWithIngredients -> mapToDomain(cocktailWithIngredients) } }
    }

    override suspend fun insertListOfCategories(categories: List<String>) {
        cocktailDao.insertCategories(*categories.map { CategoryEntity(name = it) }.toTypedArray())
    }

    override suspend fun getListOfCategories(): List<String> {
        return cocktailDao.getCategories().map { it.name }
    }

    override suspend fun getCocktailLikeFlow(id: Int): Flow<Boolean> {
        return cocktailDao.getCocktailLikeFlow(id)
            ?: throw AppException("Queried null value when tried to change like state")
    }
}