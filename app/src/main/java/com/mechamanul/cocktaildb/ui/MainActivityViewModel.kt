package com.mechamanul.cocktaildb.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mechamanul.cocktaildb.domain.prefetchCocktailCategories
import com.mechamanul.cocktaildb.utils.AppException
import com.mechamanul.cocktaildb.utils.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(private val prefetchCocktailCategories: prefetchCocktailCategories) :
    ViewModel() {

    init {
        viewModelScope.launch {
            _uiState.value = when (val apiResult = prefetchCocktailCategories.invoke()) {
                is Result.Error -> FetchState.Error(apiResult.exception)
                is Result.Success -> FetchState.Success(apiResult.data)
            }
        }
    }

    private val _uiState = MutableStateFlow<FetchState>(FetchState.Loading)
    val uiState: StateFlow<FetchState> = _uiState

    sealed class FetchState {
        object Loading : FetchState()
        data class Error(val e: AppException) : FetchState()
        data class Success(val result: Boolean) : FetchState()
    }

}
