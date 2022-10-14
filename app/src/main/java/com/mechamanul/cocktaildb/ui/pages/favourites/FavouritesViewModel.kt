package com.mechamanul.cocktaildb.ui.pages.favourites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mechamanul.cocktaildb.domain.Cocktail
import com.mechamanul.cocktaildb.domain.getFavouriteCocktailsUseCase
import com.mechamanul.cocktaildb.utils.AppException
import com.mechamanul.cocktaildb.utils.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavouritesViewModel @Inject constructor(private val getFavouriteCocktailsUseCase: getFavouriteCocktailsUseCase) :
    ViewModel() {
    private val _uiFlow: MutableStateFlow<UiState> = MutableStateFlow(UiState.Success(flowOf()))
    val uiFlow: StateFlow<UiState> = _uiFlow

    init {
        getCocktails()

    }

    private fun getCocktails() = viewModelScope.launch {
        val apiResult = getFavouriteCocktailsUseCase.invoke()
        _uiFlow.value = when (apiResult) {
            is Result.Error -> UiState.Failure(apiResult.exception)
            is Result.Success -> UiState.Success(apiResult.data)
        }

    }

    sealed class UiState {
        data class Success(val data: Flow<List<Cocktail>>) : UiState()
        data class Failure(val exception: AppException) : UiState()
    }
}