package com.mechamanul.cocktaildb.data.local

import com.mechamanul.cocktaildb.data.local.dao.CocktailDao
import com.mechamanul.cocktaildb.data.repository.LocalCocktailDataSource
import com.mechamanul.cocktaildb.domain.Cocktail
import javax.inject.Inject

class LocalCocktailDataSourceImpl @Inject constructor(private val cocktailDao: CocktailDao) :
    LocalCocktailDataSource {
    override suspend fun getVisitedCocktails(): List<Cocktail> {
        return cocktailDao.getVisitedCocktails()
    }
}