package com.mechamanul.cocktaildb.data.local.database

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.mechamanul.cocktaildb.data.local.dao.CocktailDao
import com.mechamanul.cocktaildb.data.local.model.CocktailEntity

@Database(entities = [CocktailEntity::class], version = 1)
abstract class CocktailDatabase : RoomDatabase() {
    abstract fun cocktailDao(): CocktailDao
}