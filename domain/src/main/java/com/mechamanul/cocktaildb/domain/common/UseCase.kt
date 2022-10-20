package com.mechamanul.cocktaildb.domain.common

abstract class UseCase<in T, R> {
    inline fun wrapWithResult(block: () -> R): Result<R> {
        return try {
            Result.Success(block())
        } catch (e: AppException) {
            Result.Error(e)
        }
    }

    abstract suspend fun execute(param: T): Result<R>
}