package com.mechamanul.cocktaildb.di

import com.mechamanul.cocktaildb.domain.getRandomCocktailUseCase
import com.mechamanul.cocktaildb.domain.getRandomCocktailUseCaseImpl
import com.mechamanul.cocktaildb.domain.searchCocktailByNameUseCaseImpl
import com.mechamanul.cocktaildb.domain.searchCocktailByNameUseCase
import com.mechamanul.cocktaildb.domain.getVisitedCocktailsUseCaseImpl
import com.mechamanul.cocktaildb.domain.getVisitedCocktailsUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
abstract class DomainModule {
    @Binds
    abstract fun bindGetRandomCocktailUseCaseImpl(getRandomCocktailUseCaseImpl: getRandomCocktailUseCaseImpl): getRandomCocktailUseCase
    @Binds
    abstract fun bindGetVisitedCocktailUseCaseImpl(getVisitedCocktailsUseCaseImpl: getVisitedCocktailsUseCaseImpl): getVisitedCocktailsUseCase
    @Binds
    abstract fun bindSearchCocktailByNameImpl(searchCocktailByNameUseCaseImpl: searchCocktailByNameUseCaseImpl):searchCocktailByNameUseCase

}