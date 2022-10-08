package com.mechamanul.cocktaildb.utils

sealed class Result<out T> {
    class Success<out T>(val data: T) : Result<T>()
    class Error(val exception: AppException) : Result<Nothing>()
}