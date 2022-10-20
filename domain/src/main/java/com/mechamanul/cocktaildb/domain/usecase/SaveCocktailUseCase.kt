package com.mechamanul.cocktaildb.domain.usecase

import com.mechamanul.cocktaildb.domain.common.Result
import com.mechamanul.cocktaildb.domain.common.UseCase
import com.mechamanul.cocktaildb.domain.repository.CocktailRepository
import com.mechamanul.cocktaildb.domain.model.Cocktail
import javax.inject.Inject

class SaveCocktailUseCase @Inject constructor(val repository: CocktailRepository) :
    UseCase<Cocktail, Boolean>() {


    override suspend fun execute(param: Cocktail): Result<Boolean> = wrapWithResult {
        repository.saveCocktail(param)
    }
}