package com.mechamanul.cocktaildb.domain

import com.mechamanul.cocktaildb.utils.Result
import javax.inject.Inject

class prefetchCocktailCategoriesImpl @Inject constructor(private val repository: CocktailRepository) :
    prefetchCocktailCategories {
    override suspend fun invoke(): Result<List<String>> {
        return repository.getListOfCategories()
    }
}