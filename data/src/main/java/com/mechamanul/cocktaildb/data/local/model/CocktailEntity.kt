package com.mechamanul.cocktaildb.data.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cocktails")
data class CocktailEntity(
    @PrimaryKey(autoGenerate = false) val cocktailId: Long,
    val name: String,
    val category: String,
    val type: String,
    val glass: String,
    val imageUrl: String,
    val instruction: String,
    @ColumnInfo(defaultValue = "(datetime('now'))")
    val createdAt: String?,
    val isFavourite: Boolean = false
    // for sake of simplicity
)