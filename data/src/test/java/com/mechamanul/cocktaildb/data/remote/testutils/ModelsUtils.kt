package com.mechamanul.cocktaildb.data.remote.testutils

import com.mechamanul.cocktaildb.domain.Cocktail
import com.mechamanul.cocktaildb.domain.Ingredient

fun createTestCocktail() = Cocktail(
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
    )
)

fun provideJSONCocktailAPICallExample() = "{\n" +
        "  \"drinks\": [\n" +
        "    {\n" +
        "      \"idDrink\": \"16289\",\n" +
        "      \"strDrink\": \"Apple Grande\",\n" +
        "      \"strDrinkAlternate\": null,\n" +
        "      \"strTags\": null,\n" +
        "      \"strVideo\": null,\n" +
        "      \"strCategory\": \"Punch / Party Drink\",\n" +
        "      \"strIBA\": null,\n" +
        "      \"strAlcoholic\": \"Alcoholic\",\n" +
        "      \"strGlass\": \"Punch Bowl\",\n" +
        "      \"strInstructions\": \"Chill both ingredients!! Mix in a tumbler and enjoy!\",\n" +
        "      \"strInstructionsES\": \"¡Refrigera ambos ingredientes! ¡Mezcla en un vaso Tumbler y disfruta!\",\n" +
        "      \"strInstructionsDE\": \"Kühlen Sie beide Zutaten herunter. In einem Becher mischen und genießen!\",\n" +
        "      \"strInstructionsFR\": null,\n" +
        "      \"strInstructionsIT\": \"Chill both ingredients!! Mix in a tumbler and enjoy!\",\n" +
        "      \"strInstructionsZH-HANS\": null,\n" +
        "      \"strInstructionsZH-HANT\": null,\n" +
        "      \"strDrinkThumb\": \"https://www.thecocktaildb.com/images/media/drink/wqrptx1472668622.jpg\",\n" +
        "      \"strIngredient1\": \"Tequila\",\n" +
        "      \"strIngredient2\": \"Apple cider\",\n" +
        "      \"strIngredient3\": null,\n" +
        "      \"strIngredient4\": null,\n" +
        "      \"strIngredient5\": null,\n" +
        "      \"strIngredient6\": null,\n" +
        "      \"strIngredient7\": null,\n" +
        "      \"strIngredient8\": null,\n" +
        "      \"strIngredient9\": null,\n" +
        "      \"strIngredient10\": null,\n" +
        "      \"strIngredient11\": null,\n" +
        "      \"strIngredient12\": null,\n" +
        "      \"strIngredient13\": null,\n" +
        "      \"strIngredient14\": null,\n" +
        "      \"strIngredient15\": null,\n" +
        "      \"strMeasure1\": \"3 oz\",\n" +
        "      \"strMeasure2\": \"12 oz\",\n" +
        "      \"strMeasure3\": null,\n" +
        "      \"strMeasure4\": null,\n" +
        "      \"strMeasure5\": null,\n" +
        "      \"strMeasure6\": null,\n" +
        "      \"strMeasure7\": null,\n" +
        "      \"strMeasure8\": null,\n" +
        "      \"strMeasure9\": null,\n" +
        "      \"strMeasure10\": null,\n" +
        "      \"strMeasure11\": null,\n" +
        "      \"strMeasure12\": null,\n" +
        "      \"strMeasure13\": null,\n" +
        "      \"strMeasure14\": null,\n" +
        "      \"strMeasure15\": null,\n" +
        "      \"strImageSource\": null,\n" +
        "      \"strImageAttribution\": null,\n" +
        "      \"strCreativeCommonsConfirmed\": \"No\",\n" +
        "      \"dateModified\": \"2016-08-31 19:37:02\"\n" +
        "    }\n" +
        "  ]\n" +
        "}"
