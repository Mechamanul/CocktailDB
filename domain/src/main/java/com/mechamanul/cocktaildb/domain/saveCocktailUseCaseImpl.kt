package com.mechamanul.cocktaildb.domain

import com.mechamanul.cocktaildb.domain.model.Cocktail
import javax.inject.Inject

class saveCocktailUseCaseImpl @Inject constructor(val repository: CocktailRepository) :
    saveCocktailUseCase {
    override suspend fun invoke(cocktail: Cocktail): Boolean {
        return repository.saveCocktail(cocktail)
    }
}