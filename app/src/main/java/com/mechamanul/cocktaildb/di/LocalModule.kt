package com.mechamanul.cocktaildb.di

import androidx.room.Room
import com.mechamanul.cocktaildb.data.local.database.CocktailDatabase
import dagger.Module
import dagger.Provides
import android.content.Context
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.mechamanul.cocktaildb.R
import com.mechamanul.cocktaildb.data.local.dao.CocktailDao
import com.mechamanul.cocktaildb.data.local.model.CategoryEntity
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.*
import org.json.JSONArray
import java.io.BufferedReader
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object LocalModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context) =
        Room.databaseBuilder(context, CocktailDatabase::class.java, "cocktail_database").build()

    @Singleton
    @Provides
    fun provideDao(database: CocktailDatabase) = database.cocktailDao()

//    @Singleton
//    @Provides
//    fun provideScope(): CoroutineScope {
//        return CoroutineScope(SupervisorJob() + Dispatchers.IO)
//    }
//
//    class CategoriesPrefiller(
//        @ApplicationContext val context: Context,
//        private val dao: CocktailDao,
//        private val scope: CoroutineScope
//    ) :
//        RoomDatabase.Callback() {
//        override fun onCreate(db: SupportSQLiteDatabase) {
//            super.onCreate(db)
//            scope.launch {
//
//                val categories = loadCategories(context)
//                categories.forEach { name ->
//                    dao.insertCategory(CategoryEntity(name = name))
//
//                }
//            }
//        }
//    }
//
//    private fun loadCategories(context: Context): List<String> {
//        val inputStream = context.resources.openRawResource(R.raw.categories)
//        BufferedReader(inputStream.reader()).use {
//            return it.readLines()
//        }
//    }


}