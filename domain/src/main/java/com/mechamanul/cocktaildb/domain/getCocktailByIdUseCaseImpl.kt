package com.mechamanul.cocktaildb.domain

import com.mechamanul.cocktaildb.domain.model.Cocktail
import javax.inject.Inject

class getCocktailByIdUseCaseImpl @Inject constructor(private val cocktailRepository: CocktailRepository) :
    getCocktailByIdUseCase {
    override suspend fun invoke(id: Int): Cocktail {
        return cocktailRepository.getCocktailById(id)
    }
}