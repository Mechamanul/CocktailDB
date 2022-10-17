package com.mechamanul.cocktaildb.data.remote.testutils

import com.mechamanul.cocktaildb.domain.model.Cocktail
import com.mechamanul.cocktaildb.domain.model.Ingredient

fun createSimpleTestCocktail() = Cocktail(
    id = 16289,
    name = "Apple Grande",
    category = "Punch / Party Drink",
    type = "Alcoholic",
    glass = "Punch Bowl",
    imageUrl = "https://www.thecocktaildb.com/images/media/drink/wqrptx1472668622.jpg",
    instruction = "Chill both ingredients!! Mix in a tumbler and enjoy!",
    listOfIngredients = listOf(
        Ingredient("Tequila", "3 oz"),
        Ingredient(
            "Apple cider",
            "12 oz"
        )
    ),
    isFavourite = false
)

fun createTestCocktailNoMeasures() = Cocktail(
    id = 16289,
    name = "Apple Grande",
    category = "Punch / Party Drink",
    type = "Alcoholic",
    glass = "Punch Bowl",
    imageUrl = "https://www.thecocktaildb.com/images/media/drink/wqrptx1472668622.jpg",
    instruction = "Chill both ingredients!! Mix in a tumbler and enjoy!",
    listOfIngredients = listOf(
        Ingredient("Tequila", ""),
        Ingredient(
            "Apple cider",
            ""
        )
    ),
    isFavourite = false
)

