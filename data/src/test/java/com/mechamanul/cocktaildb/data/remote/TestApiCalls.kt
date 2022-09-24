package com.mechamanul.cocktaildb.data.remote

import com.mechamanul.cocktaildb.data.remote.testutils.createTestCocktail
import io.mockk.*
import io.mockk.impl.annotations.MockK
import io.mockk.junit4.MockKRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Test

import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertSame
import org.junit.Before
import org.junit.Rule
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.create


@ExperimentalCoroutinesApi
class CocktailsServiceTest {
    @get:Rule
    val rule = MockKRule(this)


    // добавить кастомные ошибки
    // протестировать GSON
    // потом уже тестировать retrofit
    @MockK
    lateinit var cocktailService: CocktailService

//    @Before
//    fun createRemoteDataSource() {
//        val retrofit = mockk<Retrofit>()
//        every { retrofit.create(cocktailService::class.java) } returns cocktailService
//        val remoteCocktailDataSource = RemoteCocktailDataSourceImpl()
//    }

//    @Test
//    fun searchCocktailCorrectly() = runTest {
//        val searchString = "T"
//        val searchingCocktail = createTestCocktail()
//        coEvery { cocktailService.searchCocktailByName(searchString) } returns Response.success(
//            CocktailResponse(listOf(createTestCocktail()))
//        )
//
//        val apiResult =
//            assertEquals(searchingCocktail.id, apiResult.body()?.cocktails?.get(0)?.id)
//        coVerify(exactly = 1) {
//            cocktailService.searchCocktailByName(searchString)
//        }
//        confirmVerified(cocktailService)
//
//
//    }
}