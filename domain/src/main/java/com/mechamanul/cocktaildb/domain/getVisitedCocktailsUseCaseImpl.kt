package com.mechamanul.cocktaildb.domain

import javax.inject.Inject

class getVisitedCocktailsUseCaseImpl @Inject constructor(private val cocktailRepository: CocktailRepository) :
    getVisitedCocktailsUseCase {
    override suspend fun invoke(): List<Cocktail> {
        return cocktailRepository.getVisitedCocktailsList()
    }
}