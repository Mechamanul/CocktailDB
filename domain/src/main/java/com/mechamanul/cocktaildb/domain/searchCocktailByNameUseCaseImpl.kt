package com.mechamanul.cocktaildb.domain

import com.mechamanul.cocktaildb.utils.Result
import javax.inject.Inject

class searchCocktailByNameUseCaseImpl @Inject constructor(private val repository: CocktailRepository) :
    searchCocktailByNameUseCase {
    override suspend fun invoke(name:String): Result<Cocktail> {
        return repository.searchCocktailByName(name)
    }
}