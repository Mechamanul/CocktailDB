package com.mechamanul.cocktaildb.domain.usecase

import com.mechamanul.cocktaildb.domain.repository.CocktailRepository
import com.mechamanul.cocktaildb.domain.common.AppException
import com.mechamanul.cocktaildb.domain.common.Result
import com.mechamanul.cocktaildb.domain.common.UseCase
import javax.inject.Inject

class ChangeLikeStateUseCase @Inject constructor(
    val repository: CocktailRepository
) : UseCase<Int, Unit>() {

//    override suspend fun block(params: Int): Boolean {
//        val cocktail = repository.getCocktailById(params)
//        cocktail.isFavourite?.let {
//            repository.changeLikeState(cocktail.id, !cocktail.isFavourite)
//        } ?: throw AppException("Can't get cocktail instance to change it is like state")
//        return true
//    }

    override suspend fun execute(param: Int): Result<Unit> = wrapWithResult {
        val cocktail = repository.getCocktailById(param)
        cocktail.isFavourite?.let {
            repository.changeLikeState(cocktail.id, !cocktail.isFavourite)
        } ?: throw AppException("Can't get cocktail instance to change it is like state")
    }

}