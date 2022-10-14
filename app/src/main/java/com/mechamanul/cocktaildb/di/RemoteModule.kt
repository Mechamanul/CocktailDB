package com.mechamanul.cocktaildb.di

import com.google.gson.GsonBuilder
import com.mechamanul.cocktaildb.data.remote.CocktailResponse
import com.mechamanul.cocktaildb.data.remote.CocktailApi
import com.mechamanul.cocktaildb.data.remote.type_adapters.CocktailResponseDeserializer
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RemoteModule {

    // сделать интерцептор
    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit {
        val gson = GsonBuilder().registerTypeAdapter(
            CocktailResponse::class.java,
            CocktailResponseDeserializer()
        ).create()
        return Retrofit.Builder().addConverterFactory(GsonConverterFactory.create(gson))
            .baseUrl("https://www.thecocktaildb.com/api/json/v1/1/").build()
    }

    @Singleton
    @Provides
    fun provideCocktailService(retrofit: Retrofit) = retrofit.create(CocktailApi::class.java)

}