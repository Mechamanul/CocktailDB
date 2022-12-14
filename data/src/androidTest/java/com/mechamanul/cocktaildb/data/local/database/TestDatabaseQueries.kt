package com.mechamanul.cocktaildb.data.local.database

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.mechamanul.cocktaildb.data.local.LocalCocktailDataSourceImpl
import com.mechamanul.cocktaildb.data.local.dao.CocktailDao
import com.mechamanul.cocktaildb.data.local.model.CocktailEntity
import com.mechamanul.cocktaildb.data.local.model.CocktailWithIngredients
import com.mechamanul.cocktaildb.data.repository.LocalCocktailDataSource
import com.mechamanul.cocktaildb.domain.model.Cocktail
import com.mechamanul.cocktaildb.domain.model.Ingredient
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.After
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals

import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class TestDatabaseQueries {
    private lateinit var cocktailDao: CocktailDao
    private lateinit var db: CocktailDatabase
    private lateinit var dataSource: LocalCocktailDataSource

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, CocktailDatabase::class.java).build()
        cocktailDao = db.cocktailDao()
        dataSource = LocalCocktailDataSourceImpl(cocktailDao)
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun writesCocktailAndReadItAsVisitedCocktails() = runTest {
        val cocktailEntity = CocktailEntity(
            cocktailId = 1,
            name = "TestCocktail",
            category = "TestCategory",
            type = "TestType",
            glass = "TestGlass",
            imageUrl = "TestUrl",
            instruction = "TestInstruction"
        )

        cocktailDao.insertCocktail(cocktailEntity)

        val cocktailFromDatabase = cocktailDao.getVisitedCocktails()[0]

        assertEquals(cocktailFromDatabase.cocktail, cocktailEntity)
    }


    @Test
    fun visitedCocktailReturnsEmptyListWhenNoCocktailsInserted() = runTest {
        val cocktails = cocktailDao.getVisitedCocktails()
        assertEquals(cocktails, listOf<CocktailWithIngredients>())
    }

    @Test
    fun writesCocktailFromDomainAndReadsItAsVisited() = runTest {
        val cocktail = Cocktail(
            id = 1,
            name = "TestCocktail",
            category = "TestCategory",
            type = "TestType",
            glass = "TestGlass",
            imageUrl = "TestUrl",
            instruction = "TestInstruction",
            listOfIngredients = listOf(
                Ingredient("testIngredient1", "testMeasure1"),
                Ingredient("testIngredient2", "testMeasure2")
            )
        )
        dataSource.saveCocktailAndIngredientsToDatabase(cocktail)

        assertEquals(dataSource.getVisitedCocktails()[0], cocktail)

    }

    @Test
    fun writesCocktailFromDomainAndReadsItById() = runTest {
        val cocktail = Cocktail(
            id = 1,
            name = "TestCocktail",
            category = "TestCategory",
            type = "TestType",
            glass = "TestGlass",
            imageUrl = "TestUrl",
            instruction = "TestInstruction",
            listOfIngredients = listOf(
                Ingredient("testIngredient1", "testMeasure1"),
                Ingredient("testIngredient2", "testMeasure2")
            ),
            isFavourite = false
        )
        dataSource.saveCocktailAndIngredientsToDatabase(cocktail)

        assertEquals(dataSource.getCocktailById(cocktail.id), cocktail)

    }

}