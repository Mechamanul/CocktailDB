package com.mechamanul.cocktaildb.domain

import com.mechamanul.cocktaildb.utils.Result
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class getRandomCocktailUseCaseImpl @Inject constructor(private val repository: CocktailRepository) :
    getRandomCocktailUseCase {
    override suspend fun invoke(): Flow<Result<Cocktail>> {
        return repository.getRandomCocktail()
    }
}