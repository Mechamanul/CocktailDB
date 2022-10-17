package com.mechamanul.cocktaildb.domain

import com.mechamanul.cocktaildb.domain.model.Cocktail
import com.mechamanul.cocktaildb.utils.Result
import javax.inject.Inject

class getRandomCocktailUseCaseImpl @Inject constructor(private val repository: CocktailRepository) :
    getRandomCocktailUseCase {
    override suspend fun invoke(): Result<Cocktail> {
        return repository.getRandomCocktail()
    }
}