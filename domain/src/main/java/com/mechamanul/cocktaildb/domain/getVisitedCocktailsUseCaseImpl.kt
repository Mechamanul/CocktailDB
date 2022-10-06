package com.mechamanul.cocktaildb.domain

import com.mechamanul.cocktaildb.utils.Result
import javax.inject.Inject

class getVisitedCocktailsUseCaseImpl @Inject constructor(private val cocktailRepository: CocktailRepository) :
    getVisitedCocktailsUseCase {
    override suspend fun invoke(): Result<List<Cocktail>> {
        return cocktailRepository.getVisitedCocktailsList()
    }
}