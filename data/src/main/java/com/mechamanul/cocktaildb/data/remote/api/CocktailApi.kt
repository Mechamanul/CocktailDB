package com.mechamanul.cocktaildb.data.remote.api

import com.mechamanul.cocktaildb.data.remote.responses.CategoryResponse
import com.mechamanul.cocktaildb.data.remote.responses.CocktailResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface CocktailApi {
    @GET("random.php")
    suspend fun getRandomCocktail(): CocktailResponse

    @GET("search.php")
    suspend fun searchCocktailByName(@Query("s") name: String): CocktailResponse

    @GET("list.php")
    suspend fun getListOfCategories(@Query("c") list: String = "list"): CategoryResponse

    @GET("filter.php")
    suspend fun getCocktailByCategoryName(@Query("c") categoryName: String): CocktailResponse

    @GET("lookup.php")
    suspend fun getCocktailById(@Query("i") id: Int): CocktailResponse
}
