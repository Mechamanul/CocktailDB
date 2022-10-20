package com.mechamanul.cocktaildb.domain.usecase

import com.mechamanul.cocktaildb.domain.common.Result
import com.mechamanul.cocktaildb.domain.common.UseCase
import com.mechamanul.cocktaildb.domain.repository.CocktailRepository
import javax.inject.Inject

class GetCocktailCategoriesUseCase @Inject constructor(private val cocktailRepository: CocktailRepository) :
    UseCase<Unit, List<String>>() {

    override suspend fun execute(param: Unit): Result<List<String>> = wrapWithResult {
        cocktailRepository.getListOfCategories()
    }
}