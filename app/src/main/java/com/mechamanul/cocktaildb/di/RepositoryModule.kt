package com.mechamanul.cocktaildb.di

import com.mechamanul.cocktaildb.data.repository.CocktailRepositoryImpl
import com.mechamanul.cocktaildb.domain.CocktailRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


// try viewmodelcomponent later
@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {


    @Binds
    abstract fun bindCocktailRepositoryImpl(cocktailRepositoryImpl: CocktailRepositoryImpl): CocktailRepository
}