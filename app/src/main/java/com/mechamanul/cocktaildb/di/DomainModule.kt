package com.mechamanul.cocktaildb.di

import com.mechamanul.cocktaildb.domain.getRandomCocktailUseCase
import com.mechamanul.cocktaildb.domain.getRandomCocktailUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
abstract class DomainModule {
    @Binds
    abstract fun bindGetRandomCocktailUseCaseImpl(getRandomCocktailUseCaseImpl: getRandomCocktailUseCaseImpl): getRandomCocktailUseCase
}