package com.mechamanul.cocktaildb.data.remote

import retrofit2.Response
import retrofit2.http.GET

interface CocktailService {
    @GET("random.php")
    suspend fun getRandomCocktail(): Response<CocktailResponse>
}
