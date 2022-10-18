package com.mechamanul.cocktaildb.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mechamanul.cocktaildb.domain.getCocktailCategoriesUseCase
import com.mechamanul.cocktaildb.utils.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(private val getCocktailCategoriesUseCase: getCocktailCategoriesUseCase) :
    ViewModel() {

    init {
        viewModelScope.launch {
            _isLoaded.value = when (getCocktailCategoriesUseCase.invoke()) {
                is Result.Error -> false
                is Result.Success -> true
            }
        }
    }

    private val _isLoaded = MutableStateFlow(false)
    val isLoaded: StateFlow<Boolean> = _isLoaded


}
