package com.mechamanul.cocktaildb.data.repository

import com.google.gson.GsonBuilder
import com.mechamanul.cocktaildb.data.remote.CocktailResponse
import com.mechamanul.cocktaildb.data.type_adapters.CocktailResponseDeserializer
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CocktailRetrofitSource(retrofit: Retrofit) {
    init {
        val gson = GsonBuilder().registerTypeAdapter(
            CocktailResponse::class.java,
            CocktailResponseDeserializer()
        ).create()
        retrofit.newBuilder().addConverterFactory(GsonConverterFactory.create(gson))
            .baseUrl("https://www.thecocktaildb.com/api/json/v1/1/").build()
    }

}