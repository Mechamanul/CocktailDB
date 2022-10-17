package com.mechamanul.cocktaildb.domain

import com.mechamanul.cocktaildb.domain.model.Cocktail
import com.mechamanul.cocktaildb.utils.Result
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class getFavouriteCocktailsUseCaseImpl @Inject constructor(private val repository: CocktailRepository) :
    getFavouriteCocktailsUseCase {
    override suspend fun invoke(): Result<Flow<List<Cocktail>>> {
        return repository.getFavouriteCocktailsFlow()
    }
}