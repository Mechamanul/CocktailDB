package com.mechamanul.cocktaildb.domain.usecase

import com.mechamanul.cocktaildb.domain.common.Result
import com.mechamanul.cocktaildb.domain.common.UseCase
import com.mechamanul.cocktaildb.domain.repository.CocktailRepository
import com.mechamanul.cocktaildb.domain.model.Cocktail
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetVisitedCocktailsUseCase @Inject constructor(private val cocktailRepository: CocktailRepository) :
    UseCase<Unit, Flow<List<Cocktail>>>() {
//    override suspend fun block(params: Nothing): Flow<List<Cocktail>> {
//        return cocktailRepository.getVisitedCocktailsList()
//    }

    override suspend fun execute(param: Unit): Result<Flow<List<Cocktail>>> = wrapWithResult {
        cocktailRepository.getVisitedCocktailsList()
    }
}