package com.mechamanul.cocktaildb.di

import androidx.room.Room
import com.mechamanul.cocktaildb.data.local.database.CocktailDatabase
import dagger.Module
import dagger.Provides
import android.content.Context
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object LocalModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context) =
        Room.databaseBuilder(context, CocktailDatabase::class.java, "cocktail_database").build()

    @Singleton
    @Provides
    fun provideDao(database: CocktailDatabase) = database.cocktailDao()


}