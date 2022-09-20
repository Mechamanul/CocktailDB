package com.mechamanul.cocktaildb.data.local.database

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.mechamanul.cocktaildb.data.local.dao.CocktailDao
import com.mechamanul.cocktaildb.data.local.model.CocktailEntity
import com.mechamanul.cocktaildb.data.local.model.CocktailIngredientsCrossRef
import com.mechamanul.cocktaildb.data.local.model.CocktailWithIngredients
import com.mechamanul.cocktaildb.data.local.model.IngredientEntity

@Database(
    entities = [CocktailEntity::class, IngredientEntity::class, CocktailIngredientsCrossRef::class],
    version = 1
)
abstract class CocktailDatabase : RoomDatabase() {
    abstract fun cocktailDao(): CocktailDao
}