package com.mechamanul.cocktaildb.domain.usecase

import com.mechamanul.cocktaildb.domain.common.Result
import com.mechamanul.cocktaildb.domain.common.UseCase
import com.mechamanul.cocktaildb.domain.repository.CocktailRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCocktailLikeStateUseCase @Inject constructor(private val repository: CocktailRepository) :
    UseCase<Int, Flow<Boolean>>() {
    override suspend fun execute(param: Int): Result<Flow<Boolean>> = wrapWithResult {
        repository.getCocktailLikeState(param)
    }
}