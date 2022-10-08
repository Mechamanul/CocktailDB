package com.mechamanul.cocktaildb.ui.cocktail

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mechamanul.cocktaildb.domain.Cocktail
import com.mechamanul.cocktaildb.domain.getRandomCocktailUseCase
import com.mechamanul.cocktaildb.ui.cocktail.CocktailViewModel.CocktailUiState.Failure
import com.mechamanul.cocktaildb.ui.cocktail.CocktailViewModel.CocktailUiState.Success
import com.mechamanul.cocktaildb.utils.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class CocktailViewModel @Inject constructor(
    private val getRandomCocktailUseCase: getRandomCocktailUseCase,
    private val savedStateHandle: SavedStateHandle
) :
    ViewModel() {
    private val _uiFlow =
        MutableStateFlow<CocktailUiState>(CocktailUiState.InitialLoading)
    val uiFlow: StateFlow<CocktailUiState> = _uiFlow

    init {
        getRandomCocktail()
    }


    fun getRandomCocktail() = viewModelScope.launch {
        val apiResult = getRandomCocktailUseCase.invoke()
        _uiFlow.value = when (apiResult) {
            is Result.Error -> Failure(apiResult.exception)
            is Result.Success -> Success(apiResult.data)
        }

    }


    sealed class CocktailUiState {
        object InitialLoading : CocktailUiState()
        data class Success(val cocktail: Cocktail) : CocktailUiState()
        data class Failure(val exception: Exception) : CocktailUiState()
    }

}