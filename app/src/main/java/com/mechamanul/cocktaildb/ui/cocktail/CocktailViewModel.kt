package com.mechamanul.cocktaildb.ui.cocktail

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mechamanul.cocktaildb.domain.Cocktail
import com.mechamanul.cocktaildb.domain.getRandomCocktailUseCase
import com.mechamanul.cocktaildb.ui.cocktail.CocktailViewModel.CocktailUiState.Failure
import com.mechamanul.cocktaildb.ui.cocktail.CocktailViewModel.CocktailUiState.Success
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class CocktailViewModel @Inject constructor(private val getRandomCocktailUseCase: getRandomCocktailUseCase) :
    ViewModel() {
    private val _uiFlow =
        MutableStateFlow<CocktailUiState>(CocktailUiState.InitialLoading)
    val uiFlow: StateFlow<CocktailUiState> = _uiFlow

    init {
        Log.d("viewModelInitialized", this@CocktailViewModel.hashCode().toString())
        getRandomCocktail()
    }


    fun getRandomCocktail() = viewModelScope.launch {
        try {
            val cocktail = getRandomCocktailUseCase.invoke()
            Log.d("viewModel", cocktail.name)
            _uiFlow.value = Success(cocktail)
        } catch (e: Exception) {
            _uiFlow.value = Failure(e.message ?: "Exception with empty message")
        }

    }


    sealed class CocktailUiState {
        object InitialLoading : CocktailUiState()
        data class Success(val cocktail: Cocktail) : CocktailUiState()
        data class Failure(val message: String) : CocktailUiState()
    }

}