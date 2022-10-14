package com.mechamanul.cocktaildb.domain

interface changeLikeStateUseCase {
    suspend fun invoke(cocktailId: Int)
}