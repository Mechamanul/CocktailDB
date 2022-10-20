package com.mechamanul.cocktaildb.di

import com.mechamanul.cocktaildb.data.local.LocalCocktailDataSourceImpl
import com.mechamanul.cocktaildb.data.remote.RemoteCocktailDataSourceImpl
import com.mechamanul.cocktaildb.data.repository.CocktailRepositoryImpl
import com.mechamanul.cocktaildb.data.repository.LocalCocktailDataSource
import com.mechamanul.cocktaildb.data.repository.RemoteCocktailDataSource
import com.mechamanul.cocktaildb.domain.repository.CocktailRepository
import dagger.Binds

import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


// try viewmodelcomponent later
@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {


    @Binds
    abstract fun provideLocalDataSourceImpl(localDataSourceImpl: LocalCocktailDataSourceImpl): LocalCocktailDataSource

    @Binds
    abstract fun provideRemoteDataSourceImpl(remoteCocktailDataSourceImpl: RemoteCocktailDataSourceImpl): RemoteCocktailDataSource

    @Binds
    abstract fun bindCocktailRepositoryImpl(cocktailRepositoryImpl: CocktailRepositoryImpl): CocktailRepository
}