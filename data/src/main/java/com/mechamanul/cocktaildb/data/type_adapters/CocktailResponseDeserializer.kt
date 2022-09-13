package com.mechamanul.cocktaildb.data.type_adapters

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.mechamanul.cocktaildb.data.remote.CocktailResponse
import com.mechamanul.cocktaildb.data.remote.Drink
import com.mechamanul.cocktaildb.domain.Ingredient
import java.lang.reflect.Type
import javax.inject.Inject

class CocktailResponseDeserializer @Inject constructor() : JsonDeserializer<CocktailResponse> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): CocktailResponse {
        val jo = json?.asJsonObject
        val result: CocktailResponse = CocktailResponse(jo?.let { jo ->
            jo.get("drinks")?.let {
                deserializeListOfDrinks(it)
            } ?: throw Exception("Problem occured when tried to acces drink field")
        } ?: throw Exception("Problem occured at the start of deserialization"))
        return result
    }


    fun deserializeListOfDrinks(json: JsonElement?): List<Drink> {
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
                println(it.value)
                val ingredientName = it.value[0].value
                val measure = it.value[1].value
                if (!measure.isJsonNull) {
                    Ingredient(ingredientName.asString, measure.asString)
                } else {
                    Ingredient(ingredientName.asString, "")
                }
            } ?: listOf()
            Drink(
                drinkJsonObject["idDrink"].asInt,
                drinkJsonObject["strDrink"].asString,
                drinkJsonObject["strCategory"].asString,
                drinkJsonObject["strAlcoholic"].asString,
                drinkJsonObject["strGlass"].asString,
                drinkJsonObject["strInstructions"].asString,
                drinkJsonObject["strDrinkThumb"].asString,
                listOfIngredients = listOfIngredients
            )
        } ?: listOf()
    }
}