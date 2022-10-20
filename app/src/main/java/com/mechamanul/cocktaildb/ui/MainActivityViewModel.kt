package com.mechamanul.cocktaildb.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mechamanul.cocktaildb.domain.usecase.GetCocktailCategoriesUseCase
import com.mechamanul.cocktaildb.domain.common.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

typealias CategoriesResult = Result<List<String>>

@HiltViewModel
class MainActivityViewModel @Inject constructor(private val getCocktailCategoriesUseCase: GetCocktailCategoriesUseCase) :
    ViewModel() {

    init {
        viewModelScope.launch {
            _isLoaded.value = getCocktailCategoriesUseCase.execute(Unit)
        }
    }

    private val _isLoaded = MutableStateFlow<CategoriesResult>(Result.Success(listOf()))
    val isLoaded: StateFlow<CategoriesResult> = _isLoaded


}
