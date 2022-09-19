package com.mechamanul.cocktaildb.ui.start_page

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mechamanul.cocktaildb.domain.Cocktail
import com.mechamanul.cocktaildb.domain.getVisitedCocktailsUseCase
import com.mechamanul.cocktaildb.domain.searchCocktailByNameUseCase
import com.mechamanul.cocktaildb.ui.start_page.StartPageViewModel.StartPageEvents.CocktailSearchResult.*
import com.mechamanul.cocktaildb.ui.start_page.StartPageViewModel.StartPageEvents.VisitedCocktailsLoaded
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class StartPageViewModel @Inject constructor(
    private val searchCocktailUseCase: searchCocktailByNameUseCase,
    private val getVisitedCocktailsUseCase: getVisitedCocktailsUseCase
) :
    ViewModel() {
    private val _uiEvents = MutableSharedFlow<StartPageEvents>()
    val uiEvents: SharedFlow<StartPageEvents> = _uiEvents
    init {
        viewModelScope.launch {
            getListOfVisitedCocktails()
        }
    }

    suspend fun searchCocktailByName(name: String) = viewModelScope.launch {
        try {
            val cocktail = searchCocktailUseCase.invoke(name)
            _uiEvents.emit(Success(cocktail))
        } catch (e: Exception) {
            _uiEvents.emit(Failure(e))
        }
    }

    suspend fun getListOfVisitedCocktails() = viewModelScope.launch {
        try {
            val visitedCocktails = getVisitedCocktailsUseCase.invoke()
            _uiEvents.emit(VisitedCocktailsLoaded(visitedCocktails))
        } catch (e: Exception) {
            Log.d("Error in viewModel", e.message.toString())
            _uiEvents.emit(VisitedCocktailsLoaded(listOf()))
        }
    }


    sealed class StartPageEvents() {
        data class VisitedCocktailsLoaded(val cocktails: List<Cocktail>) : StartPageEvents()
        sealed class CocktailSearchResult : StartPageEvents() {
            data class Success(val cocktail: Cocktail) : CocktailSearchResult()
            data class Failure(val exception: Exception) : CocktailSearchResult()
        }

    }


}