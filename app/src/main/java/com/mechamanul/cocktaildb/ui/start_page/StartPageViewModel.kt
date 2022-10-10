package com.mechamanul.cocktaildb.ui.start_page

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mechamanul.cocktaildb.domain.Cocktail
import com.mechamanul.cocktaildb.domain.getVisitedCocktailsUseCase
import com.mechamanul.cocktaildb.domain.saveCocktailUseCase
import com.mechamanul.cocktaildb.domain.searchCocktailByNameUseCase
import com.mechamanul.cocktaildb.ui.start_page.StartPageViewModel.UiEvent.CocktailSearchResult
import com.mechamanul.cocktaildb.ui.start_page.StartPageViewModel.UiEvent.GetVisitedCocktailResult
import com.mechamanul.cocktaildb.utils.AppException
import com.mechamanul.cocktaildb.utils.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class StartPageViewModel @Inject constructor(
    private val searchCocktailUseCase: searchCocktailByNameUseCase,
    private val getVisitedCocktailsUseCase: getVisitedCocktailsUseCase,
    private val saveCocktailUseCase: saveCocktailUseCase
) :
    ViewModel() {
    private val _uiEvents =
        MutableSharedFlow<UiEvent>()
    val uiEvents: SharedFlow<UiEvent> = _uiEvents

    init {
        viewModelScope.launch {
            getListOfVisitedCocktails()
        }
    }

    suspend fun searchCocktailByName(name: String) = viewModelScope.launch {
        _uiEvents.emit(
            when (val apiResult = searchCocktailUseCase.invoke(name)) {
                is Result.Error ->
                    CocktailSearchResult.Failure(apiResult.exception)
                is Result.Success -> {
                    CocktailSearchResult.Success(apiResult.data)
                }
            }
        )
    }

    suspend fun saveChosenCocktailToDatabase(cocktail: Cocktail) = viewModelScope.launch {
        saveCocktailUseCase.invoke(cocktail)
    }

    private suspend fun getListOfVisitedCocktails() = viewModelScope.launch {
        _uiEvents.emit(
            when (val apiResult = getVisitedCocktailsUseCase.invoke()) {
                is Result.Error ->
                    GetVisitedCocktailResult.Failure(apiResult.exception)
                is Result.Success -> GetVisitedCocktailResult.Success(apiResult.data)
            }
        )
    }


    sealed class UiEvent() {
        sealed class CocktailSearchResult : UiEvent() {
            data class Success(val cocktails: List<Cocktail>) : CocktailSearchResult()
            data class Failure(val exception: AppException) : CocktailSearchResult()
        }

        sealed class GetVisitedCocktailResult : UiEvent() {
            data class Success(val cocktails: List<Cocktail>) :
                GetVisitedCocktailResult()

            data class Failure(val exception: AppException) : GetVisitedCocktailResult()
        }
    }


}