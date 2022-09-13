package com.mechamanul.cocktaildb.domain

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class getRandomCocktailUseCaseImpl @Inject constructor(private val repository: CocktailRepository) :
    getRandomCocktailUseCase {
    override suspend fun invoke(): Cocktail{
        return repository.getRandomCocktail()
    }
}