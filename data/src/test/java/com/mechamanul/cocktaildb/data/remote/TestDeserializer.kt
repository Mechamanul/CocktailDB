package com.mechamanul.cocktaildb.data.remote

import com.google.gson.*
import com.mechamanul.cocktaildb.data.remote.testutils.createTestCocktail
import com.mechamanul.cocktaildb.data.remote.testutils.provideJSONCocktailAPICallExample
import com.mechamanul.cocktaildb.data.type_adapters.CocktailResponseDeserializer
import io.mockk.every
import io.mockk.mockk
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.invocation.InvocationOnMock


class TestDeserializer {


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
        val element = JsonParser.parseString(provideJSONCocktailAPICallExample())
        val deserializationResult =
            deserializer.deserialize(element, CocktailResponse::class.java, mockedContext)
        assertEquals(deserializationResult.cocktails[0], createTestCocktail())
    }

    @Test
    fun testDeserializationIndirectly() {

    }

//    @Test
//    @Throws(IOException::class)
//    fun testDeserializeDirectlyWithMockedContext() {
//        val unit: JsonDeserializer<ZonedDateTime> = getZonedDateTimeJsonDeserializer()
//        val mockContext: JsonDeserializationContext = mock(JsonDeserializationContext::class.java)
//        `when`(
//            mockContext.deserialize(
//                any(JsonElement::class.java),
//                eq(String::class.java)
//            )
//        ).thenAnswer(
//            Answer<Any> { iom: InvocationOnMock ->
//                val jsonElement = iom.arguments[0] as JsonElement
//                jsonElement.asJsonPrimitive.asString
//            })
//        val parser = JsonParser(
//
//            getPackageResourceJsonReader(
//                ZonedDateTimeJsonDeserializerTest::class.java,
//                "zoned-date-time.json"
//            ).use { jsonReader ->
//                val jsonElement: JsonElement = parser.parse(jsonReader)
//                val actualZonedDateTime: ZonedDateTime = unit.deserialize(
//                    jsonElement,
//                    ZonedDateTime::class.java, mockContext
//                )
//                assertThat(actualZonedDateTime, `is`(expectedZonedDateTime))
//            }
//                    verify (mockContext).deserialize(
//                any(JsonPrimitive::class.java),
//                eq(String::class.java)
//            )
//                    verifyNoMoreInteractions (mockContext)
//    }
}