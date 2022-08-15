package com.mechamanul.cocktaildb.di

import com.mechamanul.cocktaildb.data.remote.CocktailService
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

//    @Singleton
//    @Provides
//    fun provideOkHttp() = OkHttpClient.Builder().build()


    @Singleton
    @Provides
    fun provideRetrofit() = Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
        .baseUrl("https://www.thecocktaildb.com/api/json/v1/1").build()

    @Singleton
    @Provides
    fun provideCocktailService(retrofit: Retrofit) = retrofit.create(CocktailService::class.java)

}