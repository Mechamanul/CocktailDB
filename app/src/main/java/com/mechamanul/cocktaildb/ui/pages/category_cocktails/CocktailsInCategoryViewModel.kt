package com.mechamanul.cocktaildb.ui.pages.category_cocktails

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mechamanul.cocktaildb.domain.usecase.GetCocktailsByCategoryName
import com.mechamanul.cocktaildb.domain.model.Cocktail
import com.mechamanul.cocktaildb.domain.usecase.SaveCocktailUseCase
import com.mechamanul.cocktaildb.domain.common.AppException
import com.mechamanul.cocktaildb.domain.common.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

typealias ApiResult = Result<List<Cocktail>>

@HiltViewModel
class CocktailsInCategoryViewModel @Inject constructor(
    private val getCocktailsByCategoryName: GetCocktailsByCategoryName,
    private val saveCocktailUseCase: SaveCocktailUseCase,
    savedStateHandle: SavedStateHandle
) :
    ViewModel() {
    init {
        viewModelScope.launch {
            val categoryName: String? = savedStateHandle["categoryName"]
            categoryName?.let {
                _uiState.value = getCocktailsByCategoryName.execute(categoryName)
            }
        }
    }


    private val _uiState =
        MutableStateFlow<ApiResult>(Result.Success(listOf()))
    val uiState: StateFlow<ApiResult> = _uiState

    suspend fun saveChosenCocktailToDatabase(cocktail: Cocktail) = viewModelScope.launch {
        saveCocktailUseCase.execute(cocktail)
    }
}


