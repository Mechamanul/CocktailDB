package com.mechamanul.cocktaildb.domain.usecase

import com.mechamanul.cocktaildb.domain.common.Result
import com.mechamanul.cocktaildb.domain.common.UseCase
import com.mechamanul.cocktaildb.domain.repository.CocktailRepository
import com.mechamanul.cocktaildb.domain.model.Cocktail
import javax.inject.Inject

class SearchCocktailByNameUseCase @Inject constructor(private val repository: CocktailRepository) :
    UseCase<String, List<Cocktail>>() {

    override suspend fun execute(param: String): Result<List<Cocktail>> = wrapWithResult {
        repository.searchCocktailByName(param)
    }
}