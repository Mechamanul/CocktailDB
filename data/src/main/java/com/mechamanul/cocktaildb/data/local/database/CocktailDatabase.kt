package com.mechamanul.cocktaildb.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.mechamanul.cocktaildb.data.local.dao.CocktailDao
import com.mechamanul.cocktaildb.data.local.model.CocktailEntity
import com.mechamanul.cocktaildb.data.local.model.CocktailIngredientsCrossRef
import com.mechamanul.cocktaildb.data.local.model.IngredientEntity
import com.mechamanul.cocktaildb.data.local.type_adapters.LocalDateTimeConverter

@Database(
    entities = [CocktailEntity::class, IngredientEntity::class, CocktailIngredientsCrossRef::class],
    version = 1
)
@TypeConverters(LocalDateTimeConverter::class)
abstract class CocktailDatabase : RoomDatabase() {
    abstract fun cocktailDao(): CocktailDao
}