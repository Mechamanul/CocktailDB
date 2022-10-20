package com.mechamanul.cocktaildb.domain.usecase

import com.mechamanul.cocktaildb.domain.common.Result
import com.mechamanul.cocktaildb.domain.common.UseCase
import com.mechamanul.cocktaildb.domain.repository.CocktailRepository
import com.mechamanul.cocktaildb.domain.model.Cocktail
import javax.inject.Inject

class GetCocktailByIdUseCase @Inject constructor(private val cocktailRepository: CocktailRepository) :
    UseCase<Int, Cocktail>() {

    override suspend fun execute(param: Int): Result<Cocktail> = wrapWithResult {
        cocktailRepository.getCocktailById(param)
    }

}