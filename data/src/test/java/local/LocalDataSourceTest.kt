package local

import com.mechamanul.cocktaildb.data.local.model.CocktailEntity
import com.mechamanul.cocktaildb.data.local.model.CocktailIngredientsCrossRef
import com.mechamanul.cocktaildb.data.local.model.CocktailWithIngredients
import com.mechamanul.cocktaildb.data.local.model.IngredientEntity
import com.mechamanul.cocktaildb.domain.Cocktail
import com.mechamanul.cocktaildb.domain.Ingredient
import org.junit.Assert.assertEquals
import org.junit.Test
import java.io.IOException

class LocalDataSourceTest {
    private fun mapToDomain(entity: CocktailWithIngredients): Cocktail {

        return Cocktail(

            id = entity.cocktail.cocktailId.toInt(),
            name = entity.cocktail.name,
            category = entity.cocktail.category,
            type = entity.cocktail.type,
            glass = entity.cocktail.glass,
            imageUrl = entity.cocktail.imageUrl,
            instruction = entity.cocktail.instruction,
            listOfIngredients = mergeCrossRefWithIngredients(entity.crossRefs, entity.ingredients)
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

    @Test
    fun testMappingToDomain() {
        val testInstance = CocktailWithIngredients(
            CocktailEntity(
                1L,
                "name",
                "category",
                "type",
                "glass",
                "url",
                "instruction"
            ),
            listOf(IngredientEntity(1L, "name")),
            listOf(CocktailIngredientsCrossRef(1L, 1L, "measure"))
        )
        val expected = Cocktail(
            1,
            "name",
            "category",
            "type",
            "glass",
            "url",
            "instruction",
            listOf(Ingredient("name", "measure"))
        )
        assertEquals(expected, mapToDomain(testInstance))
    }
}