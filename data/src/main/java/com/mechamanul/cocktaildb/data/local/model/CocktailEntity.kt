package com.mechamanul.cocktaildb.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cocktails")
data class CocktailEntity(
    @PrimaryKey val id:Int,
    val name: String ,
    val category: String ,
    val type: String ,
    val glass: String ,
    val imageUrl: String ,
    val instruction:String,
)