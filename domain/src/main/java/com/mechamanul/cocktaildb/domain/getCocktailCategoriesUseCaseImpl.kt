package com.mechamanul.cocktaildb.domain

import com.mechamanul.cocktaildb.utils.Result
import javax.inject.Inject

class getCocktailCategoriesUseCaseImpl @Inject constructor(private val cocktailRepository: CocktailRepository) :
    getCocktailCategoriesUseCase {
    override suspend fun invoke(): Result<List<String>> {
        return cocktailRepository.getListOfCategories()
    }
}