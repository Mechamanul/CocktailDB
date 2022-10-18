package com.mechamanul.cocktaildb.domain

import com.mechamanul.cocktaildb.domain.model.Cocktail
import com.mechamanul.cocktaildb.utils.Result
import javax.inject.Inject

class getCocktailsByCategoryNameImpl @Inject constructor(private val repository: CocktailRepository) :
    getCocktailsByCategoryName {
    override suspend fun invoke(categoryName: String): Result<List<Cocktail>> {
        return repository.getCocktailsByCategoryName(categoryName)
    }
}