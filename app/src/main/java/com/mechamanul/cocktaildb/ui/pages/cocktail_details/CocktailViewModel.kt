package com.mechamanul.cocktaildb.ui.pages.cocktail_details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mechamanul.cocktaildb.domain.model.Cocktail
import com.mechamanul.cocktaildb.domain.getCocktailByIdUseCase
import com.mechamanul.cocktaildb.domain.changeLikeStateUseCase
import com.mechamanul.cocktaildb.ui.pages.cocktail_details.CocktailViewModel.CocktailUiState.Failure
import com.mechamanul.cocktaildb.ui.pages.cocktail_details.CocktailViewModel.CocktailUiState.Success
import com.mechamanul.cocktaildb.utils.AppException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class CocktailViewModel @Inject constructor(
    private val changeLikeStateUseCase: changeLikeStateUseCase,
    private val getCocktailByIdUseCase: getCocktailByIdUseCase,
    state: SavedStateHandle
) :
    ViewModel() {
    private val _uiFlow =
        MutableStateFlow<CocktailUiState>(CocktailUiState.InitialLoading)
    val uiFlow: StateFlow<CocktailUiState> = _uiFlow

    init {
        val cocktailId = state.get<Int>("id")
        if (cocktailId != null) {
            getCocktail(cocktailId)
        } else {
            _uiFlow.value = Failure(AppException("Can't get cocktail from database"))
        }
    }


    private fun getCocktail(id: Int) = viewModelScope.launch {
        _uiFlow.value = CocktailUiState.InitialLoading
        try {
            val cocktail = getCocktailByIdUseCase.invoke(id)
            _uiFlow.value = Success(cocktail)
        } catch (e: AppException) {
            _uiFlow.value = Failure(e)
        }


    }

    fun changeLikeState(cocktailId: Int) = viewModelScope.launch {
        changeLikeStateUseCase.invoke(cocktailId)
    }


    sealed class CocktailUiState {
        object InitialLoading : CocktailUiState()
        data class Success(val cocktail: Cocktail) : CocktailUiState()
        data class Failure(val exception: AppException) : CocktailUiState()
    }

}