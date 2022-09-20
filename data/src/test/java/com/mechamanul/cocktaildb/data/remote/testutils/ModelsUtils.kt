package com.mechamanul.cocktaildb.data.remote.testutils

import com.mechamanul.cocktaildb.data.remote.Drink
import com.mechamanul.cocktaildb.domain.Cocktail
import com.mechamanul.cocktaildb.domain.Ingredient

fun createTestCocktail() = Cocktail(
    id = 1,
    name = "TestCocktail",
    category = "TestCategory",
    type = "TestType",
    glass = "TestGlass",
    imageUrl = "TestUrl",
    instruction = "TestInstruction",
    listOfIngredients = listOf(
        Ingredient("TestIngredient1", "TestMeasure1"),
        Ingredient(
            "TestIngredient2",
            "TestMeasure2"
        )
    )
)

fun createTestDrink() = Drink(
    id = 1,
    name = "TestCocktail",
    category = "TestCategory",
    type = "TestType",
    glass = "TestGlass",
    imageUrl = "TestUrl",
    instruction = "TestInstruction",
    listOfIngredients = listOf(
        Ingredient("TestIngredient1", "TestMeasure1"),
        Ingredient(
            "TestIngredient2",
            "TestMeasure2"
        )
    )
)