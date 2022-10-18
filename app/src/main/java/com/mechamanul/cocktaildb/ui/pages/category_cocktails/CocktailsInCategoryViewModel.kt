package com.mechamanul.cocktaildb.ui.pages.category_cocktails

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mechamanul.cocktaildb.domain.getCocktailsByCategoryName
import com.mechamanul.cocktaildb.domain.model.Cocktail
import com.mechamanul.cocktaildb.domain.saveCocktailUseCase
import com.mechamanul.cocktaildb.ui.pages.start_page.StartPageViewModel
import com.mechamanul.cocktaildb.utils.AppException
import com.mechamanul.cocktaildb.utils.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CocktailsInCategoryViewModel @Inject constructor(
    private val getCocktailsByCategoryName: getCocktailsByCategoryName,
    private val saveCocktailUseCase: saveCocktailUseCase,
    savedStateHandle: SavedStateHandle
) :
    ViewModel() {
    init {
        viewModelScope.launch {
            val categoryName: String? = savedStateHandle["categoryName"]
            categoryName?.let {
                val apiResult = getCocktailsByCategoryName.invoke(categoryName)
                _uiState.value = when (apiResult) {
                    is Result.Error -> InCategoryUiState.Failure(apiResult.exception)
                    is Result.Success -> InCategoryUiState.Success(apiResult.data)
                }
            }
        }
    }

    private val _uiState =
        MutableStateFlow<InCategoryUiState>(InCategoryUiState.Success(listOf()))
    val uiState: StateFlow<InCategoryUiState> = _uiState

    suspend fun saveChosenCocktailToDatabase(cocktail: Cocktail) = viewModelScope.launch {
        saveCocktailUseCase.invoke(cocktail)
    }

    sealed class InCategoryUiState {
        data class Success(val cocktails: List<Cocktail>) : InCategoryUiState()
        data class Failure(val exception: AppException) : InCategoryUiState()
    }
}