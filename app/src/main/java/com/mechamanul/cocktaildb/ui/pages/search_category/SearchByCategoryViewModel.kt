package com.mechamanul.cocktaildb.ui.pages.search_category

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mechamanul.cocktaildb.domain.getCocktailCategoriesUseCase
import com.mechamanul.cocktaildb.utils.AppException
import com.mechamanul.cocktaildb.utils.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchByCategoryViewModel @Inject constructor(private val getCategoriesUseCase: getCocktailCategoriesUseCase) :
    ViewModel() {

    private val _uiState = MutableStateFlow<CategoriesUiState>(CategoriesUiState.Success(listOf()))
    val uiState: StateFlow<CategoriesUiState> = _uiState


    init {
        viewModelScope.launch {
            val invokeResult = getCategoriesUseCase.invoke()
            _uiState.value = when (invokeResult) {
                is Result.Error -> CategoriesUiState.Error(invokeResult.exception)
                is Result.Success -> CategoriesUiState.Success(invokeResult.data)
            }
        }
    }

    sealed class CategoriesUiState {
        data class Success(val data: List<String>) : CategoriesUiState()
        data class Error(val e: AppException) : CategoriesUiState()
    }

}