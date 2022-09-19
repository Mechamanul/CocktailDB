package com.mechamanul.cocktaildb.domain

import javax.inject.Inject

class searchCocktailByNameUseCaseImpl @Inject constructor(private val repository: CocktailRepository) :
    searchCocktailByNameUseCase {
    override suspend fun invoke(name:String): Cocktail {
        return repository.searchCocktailByName(name)
    }
}