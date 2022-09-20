package com.mechamanul.cocktaildb.data.remote

import com.mechamanul.cocktaildb.data.remote.testutils.createTestCocktail
import com.mechamanul.cocktaildb.data.remote.testutils.createTestDrink
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.impl.annotations.MockK
import io.mockk.junit4.MockKRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Test

import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertSame
import org.junit.Before
import org.junit.Rule
import retrofit2.Response


@ExperimentalCoroutinesApi
class CocktailsServiceTest {
    @get:Rule
    val rule = MockKRule(this)

    @MockK
    lateinit var cocktailService: CocktailService

    @Test
    fun searchCocktailCorrectly() = runTest {
        val searchString = "T"
        val searchingCocktail = createTestCocktail()
        coEvery { cocktailService.searchCocktailByName(searchString) } returns Response.success(
            CocktailResponse(listOf(createTestDrink()))
        )

        val apiResult = cocktailService.searchCocktailByName("T")
        assertSame(searchingCocktail.id,apiResult.body()?.drinks?.get(0)?.id)
        coVerify(exactly = 1){
            cocktailService.searchCocktailByName(searchString)
        }
        confirmVerified(cocktailService)


    }
}