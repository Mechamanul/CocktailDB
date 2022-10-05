package com.mechamanul.cocktaildb.data.remote

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CocktailApi {
    @GET("random.php")
    suspend fun getRandomCocktail(): CocktailResponse

    @GET("search.php")
    suspend fun searchCocktailByName(@Query("s") name: String): CocktailResponse
}
