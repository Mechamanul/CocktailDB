package com.mechamanul.cocktaildb.data.remote

import com.google.gson.annotations.SerializedName

data class CocktailResponse(
    @SerializedName("idDrink") val id: String,
    @SerializedName("strDrink") val name: String,
    @SerializedName("strCategory") val category: String,
    @SerializedName("strAlcoholic") val type: String,
    @SerializedName("strGlass") val glass: String,
    @SerializedName("strInstructions") val instruction:String,
    @SerializedName("strDrinkThumb") val imageUrl:String,

)


