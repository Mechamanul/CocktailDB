package com.mechamanul.cocktaildb.domain.usecase

import com.mechamanul.cocktaildb.domain.common.Result
import com.mechamanul.cocktaildb.domain.common.UseCase
import com.mechamanul.cocktaildb.domain.repository.CocktailRepository
import com.mechamanul.cocktaildb.domain.model.Cocktail
import javax.inject.Inject

class GetRandomCocktailUseCase @Inject constructor(private val repository: CocktailRepository) :
    UseCase<Unit, Cocktail>() {

    override suspend fun execute(param: Unit): Result<Cocktail> = wrapWithResult {
        repository.getRandomCocktail()
    }
}