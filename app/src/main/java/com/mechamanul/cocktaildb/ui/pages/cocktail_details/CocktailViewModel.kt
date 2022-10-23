package com.mechamanul.cocktaildb.ui.pages.cocktail_details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mechamanul.cocktaildb.domain.model.Cocktail
import com.mechamanul.cocktaildb.domain.usecase.ChangeLikeStateUseCase
import com.mechamanul.cocktaildb.domain.usecase.GetCocktailByIdUseCase
import com.mechamanul.cocktaildb.domain.common.AppException
import com.mechamanul.cocktaildb.domain.common.Result
import com.mechamanul.cocktaildb.domain.usecase.GetCocktailLikeStateUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

typealias CocktailResult = Result<Cocktail>

@HiltViewModel
class CocktailViewModel @Inject constructor(
    private val getCocktailByIdUseCase: GetCocktailByIdUseCase,
    state: SavedStateHandle,
) :
    ViewModel() {
    private val _uiFlow =
        MutableStateFlow<UiState>(UiState.InitialLoading)
    val uiFlow: StateFlow<UiState> = _uiFlow


    init {
        val cocktailId = state.get<Int>("id")
        if (cocktailId != null) {
            getCocktail(cocktailId)
        } else {
            _uiFlow.value =
                UiState.CallCompleted(Result.Error(AppException("CocktailId was not passed as argument")))
        }
    }


    private fun getCocktail(id: Int) = viewModelScope.launch {
        _uiFlow.value = UiState.CallCompleted(getCocktailByIdUseCase.execute(id))
    }


    sealed class UiState {
        object InitialLoading : UiState()
        data class CallCompleted(val result: CocktailResult) : UiState()
    }

}