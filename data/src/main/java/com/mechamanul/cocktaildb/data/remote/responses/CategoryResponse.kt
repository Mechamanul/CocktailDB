package com.mechamanul.cocktaildb.data.remote.responses

import com.google.gson.annotations.SerializedName

data class CategoryResponse(@SerializedName("drinks") val categories: List<CategoryRemote>)

data class CategoryRemote(@SerializedName("strCategory") val name: String)