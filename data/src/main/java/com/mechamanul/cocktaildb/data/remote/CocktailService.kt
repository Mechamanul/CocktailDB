package com.mechamanul.cocktaildb.data.remote

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CocktailService {
    @GET("random.php")
    suspend fun getRandomCocktail(): Response<CocktailResponse>

    @GET("search.php")
    suspend fun searchCocktailByName(@Query("s") name: String): Response<CocktailResponse>
}
