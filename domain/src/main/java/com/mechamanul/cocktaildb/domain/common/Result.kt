package com.mechamanul.cocktaildb.domain.common

sealed class Result<out T> {
    class Success<out T>(val data: T) : Result<T>()
    class Error(val exception: AppException) : Result<Nothing>()
}