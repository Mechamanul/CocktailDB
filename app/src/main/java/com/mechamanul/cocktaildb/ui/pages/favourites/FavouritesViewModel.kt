package com.mechamanul.cocktaildb.ui.pages.favourites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mechamanul.cocktaildb.domain.model.Cocktail
import com.mechamanul.cocktaildb.domain.usecase.GetFavouriteCocktailsUseCase
import com.mechamanul.cocktaildb.domain.common.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import javax.inject.Inject

typealias FavouritesResult = Result<Flow<List<Cocktail>>>

@HiltViewModel
class FavouritesViewModel @Inject constructor(private val getFavouriteCocktailsUseCase: GetFavouriteCocktailsUseCase) :
    ViewModel() {

    private val _uiFlow: MutableStateFlow<FavouritesResult> =
        MutableStateFlow(Result.Success(flowOf()))
    val uiFlow: StateFlow<FavouritesResult> = _uiFlow

    init {
        getCocktails()

    }

    private fun getCocktails() = viewModelScope.launch {
        _uiFlow.value = getFavouriteCocktailsUseCase.execute(Unit)

    }

}