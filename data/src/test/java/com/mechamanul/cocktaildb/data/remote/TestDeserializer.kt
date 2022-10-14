package com.mechamanul.cocktaildb.data.remote

import com.google.gson.*
import com.google.gson.stream.JsonReader
import com.mechamanul.cocktaildb.data.remote.testutils.createSimpleTestCocktail
import com.mechamanul.cocktaildb.data.remote.testutils.createTestCocktailNoMeasures
import com.mechamanul.cocktaildb.data.remote.type_adapters.CocktailResponseDeserializer
import io.mockk.every
import io.mockk.mockk
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.io.File
import java.io.InputStreamReader


const val relativePathToTestJsons = "src/test/java/com/mechamanul/cocktaildb/data/remote/testutils"

class TestDeserializer {
    private fun getTestJsonByFilename(filename: String): JsonElement {
        val reader =
            JsonReader(
                InputStreamReader(
                    File(
                        "$relativePathToTestJsons/$filename.json"
                    ).inputStream(),
                    "UTF-8"
                )
            )
        return JsonParser.parseReader(reader)
    }

    @Test
    fun testDeserializeDirectly() {
        val mockedContext = mockk<JsonDeserializationContext>()
        every {
            mockedContext.deserialize(
                ofType(JsonElement::class),
                CocktailResponse::class.java
            ) as String
        } answers {
            val jsonElement: JsonElement = firstArg()
            jsonElement.asJsonPrimitive.asString
        }
        val deserializer = CocktailResponseDeserializer()
        val element = getTestJsonByFilename("simpleResponseTest")
        val deserializationResult =
            deserializer.deserialize(element, CocktailResponse::class.java, mockedContext)
        assertEquals(deserializationResult.cocktails!![0], createSimpleTestCocktail())
    }


    private lateinit var gson: Gson

    @Before
    fun initGson() {
        val deserializer = CocktailResponseDeserializer()
        gson =
            GsonBuilder().registerTypeAdapter(CocktailResponse::class.java, deserializer).create()
    }

    @Test
    fun testDeserializeIndirectlyViaAutomaticTypeAdapterBinding() {
        val deserializationResult =
            gson.fromJson(
                getTestJsonByFilename("simpleResponseTest"), CocktailResponse::class.java
            )
        assertEquals(deserializationResult.cocktails!![0], createSimpleTestCocktail())
    }

    @Test(expected = JsonSyntaxException::class)
    fun testThrowsErrorOnWrongJson() {
        val apiCallJsonAsString = getTestJsonByFilename("simpleResponseTest").asString
        //corrupt hierarchy
        val wrongJson = apiCallJsonAsString.removeRange(
            apiCallJsonAsString.length - 4,
            apiCallJsonAsString.length - 1
        )
        println(wrongJson)
        gson.fromJson(wrongJson, CocktailResponse::class.java)
    }

    @Test
    fun deserializeCorrectlyWhenDrinksNull() {
        val testListCocktails = CocktailResponse(listOf())
        val deserializationResult =
            gson.fromJson(getTestJsonByFilename("drinksNull"), CocktailResponse::class.java)
        assertEquals(testListCocktails, deserializationResult)
    }

    @Test
    fun deserializeCorrectlyWhenIngredientHaveNoMeasure() {
        val testCocktail = createTestCocktailNoMeasures()
        val deserializationResult = gson.fromJson(
            getTestJsonByFilename("noMeasureResponseTest"),
            CocktailResponse::class.java
        )
        assertEquals(testCocktail, deserializationResult.cocktails!![0])
    }


}