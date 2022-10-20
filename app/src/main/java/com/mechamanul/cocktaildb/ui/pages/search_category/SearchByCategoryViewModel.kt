package com.mechamanul.cocktaildb.ui.pages.search_category

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mechamanul.cocktaildb.domain.usecase.GetCocktailCategoriesUseCase
import com.mechamanul.cocktaildb.domain.common.AppException
import com.mechamanul.cocktaildb.domain.common.Result
import com.mechamanul.cocktaildb.domain.common.Result.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchByCategoryViewModel @Inject constructor(private val getCategoriesUseCase: GetCocktailCategoriesUseCase) :
    ViewModel() {

    private val _uiState =
        MutableStateFlow<Result<List<String>>>(Success(listOf()))
    val uiState: StateFlow<Result<List<String>>> = _uiState


    init {
        viewModelScope.launch {
            _uiState.value = getCategoriesUseCase.execute(Unit)

        }
    }



}