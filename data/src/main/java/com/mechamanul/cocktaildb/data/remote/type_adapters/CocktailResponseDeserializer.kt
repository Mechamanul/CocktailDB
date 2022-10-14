package com.mechamanul.cocktaildb.data.remote.type_adapters

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.mechamanul.cocktaildb.data.remote.CocktailResponse
import com.mechamanul.cocktaildb.domain.Cocktail
import com.mechamanul.cocktaildb.domain.Ingredient
import com.mechamanul.cocktaildb.utils.JsonDeserializationException
import java.lang.reflect.Type
import javax.inject.Inject

class CocktailResponseDeserializer @Inject constructor() : JsonDeserializer<CocktailResponse> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): CocktailResponse {
        val jo = json?.asJsonObject
        val result = CocktailResponse(jo?.let { jo ->
            val drinksArray = jo.get("drinks")
            if (!drinksArray.isJsonNull) {
                deserializeListOfDrinks(drinksArray)
            } else {
                listOf()
            }
//            jo.get("drinks")?.let {
//                deserializeListOfDrinks(it)
//            } ?: listOf()
        } ?: throw JsonDeserializationException("Problem occurred at the start of deserialization"))
        return result
    }


    private fun deserializeListOfDrinks(json: JsonElement?): List<Cocktail> {
        val listJsonArray = json?.asJsonArray
        return listJsonArray?.map { drinkJsonElement ->
            val drinkJsonObject = drinkJsonElement.asJsonObject
            val numberToIngredientMeasure = drinkJsonObject.entrySet()?.filter {
                val field = it.key
                field[field.length - 1].isDigit()
            }?.groupBy { it.key.filter { it.isDigit() } }
            val listOfIngredients = numberToIngredientMeasure?.filter {
                val ingredientName = it.value[0].value
                !ingredientName.isJsonNull
            }?.map {
                val ingredientName = it.value[0].value
                val measure = it.value[1].value
                if (!measure.isJsonNull) {
                    Ingredient(ingredientName.asString, measure.asString)
                } else {
                    Ingredient(ingredientName.asString, "")
                }
            } ?: listOf()
            Cocktail(
                id = drinkJsonObject["idDrink"].asInt,
                name = drinkJsonObject["strDrink"].asString,
                category = drinkJsonObject["strCategory"].asString,
                type = drinkJsonObject["strAlcoholic"].asString,
                glass = drinkJsonObject["strGlass"].asString,
                instruction = drinkJsonObject["strInstructions"].asString,
                imageUrl = drinkJsonObject["strDrinkThumb"].asString,
                listOfIngredients = listOfIngredients,
                isFavourite = false
            )
        } ?: listOf()
    }
}