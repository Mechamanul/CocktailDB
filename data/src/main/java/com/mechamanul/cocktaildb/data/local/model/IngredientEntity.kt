package com.mechamanul.cocktaildb.data.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(indices = [Index(value = ["name"], unique = true)])
data class IngredientEntity(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(
        index = true
    ) val ingredientId: Long = 0L,
    val name: String
)