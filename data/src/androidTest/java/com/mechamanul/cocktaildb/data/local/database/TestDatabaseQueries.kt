package com.mechamanul.cocktaildb.data.local.database

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.mechamanul.cocktaildb.data.local.dao.CocktailDao
import com.mechamanul.cocktaildb.data.local.model.CocktailEntity
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class TestDatabaseQueries {
    private lateinit var cocktailDao: CocktailDao
    private lateinit var db: CocktailDatabase

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, CocktailDatabase::class.java).build()
        cocktailDao = db.cocktailDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun writesCocktailAndReadItAsVisitedCocktails() = runBlocking {
        val cocktailEntity = CocktailEntity(
            cocktailId = 1L,
            name = "TestCocktail",
            category = "TestCategory",
            type = "TestType",
            glass = "TestGlass",
            imageUrl = "TestUrl",
            instruction = "TestInstruction"
        )

        cocktailDao.insertCocktail(cocktailEntity)

        val cocktailFromDatabase = cocktailDao.getVisitedCocktails()[0]

        Assert.assertEquals(cocktailFromDatabase.cocktail, cocktailEntity)
    }
}