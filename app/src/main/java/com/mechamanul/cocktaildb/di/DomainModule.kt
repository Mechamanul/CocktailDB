package com.mechamanul.cocktaildb.di

import com.mechamanul.cocktaildb.domain.*
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
    abstract fun bindSearchCocktailByNameImpl(searchCocktailByNameUseCaseImpl: searchCocktailByNameUseCaseImpl): searchCocktailByNameUseCase

    @Binds
    abstract fun bindSaveCocktailUseCaseImpl(saveCocktailUseCaseImpl: saveCocktailUseCaseImpl): saveCocktailUseCase

    @Binds
    abstract fun bindGetCocktailByIdUseCaseImpl(getCocktailByIdUseCaseImpl: getCocktailByIdUseCaseImpl): getCocktailByIdUseCase

    @Binds
    abstract fun bindChangeLikeStateImpl(useCaseImpl: changeLikeStateUseCaseImpl): changeLikeStateUseCase

    @Binds
    abstract fun bindGetFavouriteCocktailsImpl(getFavouriteCocktailsUseCaseImpl: getFavouriteCocktailsUseCaseImpl): getFavouriteCocktailsUseCase

    @Binds
    abstract fun prefetchCategoiresUseCaseImpl(prefetchCocktailCategoriesImpl: prefetchCocktailCategoriesImpl): prefetchCocktailCategories
}