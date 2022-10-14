package com.mechamanul.cocktaildb.data.local

import com.mechamanul.cocktaildb.data.local.dao.CocktailDao
import com.mechamanul.cocktaildb.data.local.model.CocktailEntity
import com.mechamanul.cocktaildb.data.local.model.CocktailIngredientsCrossRef
import com.mechamanul.cocktaildb.data.local.model.CocktailWithIngredients
import com.mechamanul.cocktaildb.data.local.model.IngredientEntity
import com.mechamanul.cocktaildb.data.repository.LocalCocktailDataSource
import com.mechamanul.cocktaildb.domain.Cocktail
import com.mechamanul.cocktaildb.domain.Ingredient
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

    override suspend fun getCocktailById(id: Int): Cocktail {
        return mapToDomain(cocktailDao.getCocktailById(id))
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
            instruction = instruction,
        )
    }

    override suspend fun getFavouriteCocktailsFlow(): Flow<List<Cocktail>> {
        return cocktailDao.getFavouriteCocktails()
            .map { list -> list.map { cocktailWithIngredients -> mapToDomain(cocktailWithIngredients) } }
    }
}