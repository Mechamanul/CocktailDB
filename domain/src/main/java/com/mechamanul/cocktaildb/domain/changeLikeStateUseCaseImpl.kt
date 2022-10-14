package com.mechamanul.cocktaildb.domain

import javax.inject.Inject

class changeLikeStateUseCaseImpl @Inject constructor(
    val getCocktailByIdUseCase: getCocktailByIdUseCase,
    val repository: CocktailRepository
) :
    changeLikeStateUseCase {
    override suspend fun invoke(cocktailId: Int) {
        val cocktail = getCocktailByIdUseCase.invoke(cocktailId)
        repository.changeLikeState(cocktail.id, !cocktail.isFavourite)
    }
}