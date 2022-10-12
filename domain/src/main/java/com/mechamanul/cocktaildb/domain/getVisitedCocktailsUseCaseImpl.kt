package com.mechamanul.cocktaildb.domain

import com.mechamanul.cocktaildb.utils.Result
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class getVisitedCocktailsUseCaseImpl @Inject constructor(private val cocktailRepository: CocktailRepository) :
    getVisitedCocktailsUseCase {
    override suspend fun invoke(): Result<Flow<List<Cocktail>>> {
        return cocktailRepository.getVisitedCocktailsList()
    }
}