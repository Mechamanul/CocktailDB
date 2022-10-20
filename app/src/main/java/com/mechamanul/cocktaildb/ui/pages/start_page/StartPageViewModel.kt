package com.mechamanul.cocktaildb.ui.pages.start_page

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mechamanul.cocktaildb.domain.model.Cocktail
import com.mechamanul.cocktaildb.domain.usecase.SaveCocktailUseCase
import com.mechamanul.cocktaildb.domain.usecase.SearchCocktailByNameUseCase
import com.mechamanul.cocktaildb.domain.common.AppException
import com.mechamanul.cocktaildb.domain.common.Result
import com.mechamanul.cocktaildb.domain.usecase.GetVisitedCocktailsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

typealias SearchResult = Result<List<Cocktail>>
typealias VisitedCocktailResult = Result<Flow<List<Cocktail>>>

@HiltViewModel
class StartPageViewModel @Inject constructor(
    private val searchCocktailUseCase: SearchCocktailByNameUseCase,
    private val getVisitedCocktailsUseCase: GetVisitedCocktailsUseCase,
    private val saveCocktailUseCase: SaveCocktailUseCase
) :
    ViewModel() {
    private val _cocktailSearch =
        MutableSharedFlow<SearchResult>()
    val cocktailSearch: SharedFlow<SearchResult> = _cocktailSearch
    private val _visitedCocktails: MutableStateFlow<VisitedCocktailResult> =
        MutableStateFlow(Result.Success(flowOf()))
    val visitedCocktails: StateFlow<VisitedCocktailResult> = _visitedCocktails

    init {
        viewModelScope.launch {
            getListOfVisitedCocktails()
        }
    }

    suspend fun searchCocktailByName(name: String) = viewModelScope.launch {
        _cocktailSearch.emit(searchCocktailUseCase.execute(name))


    }

    suspend fun saveChosenCocktailToDatabase(cocktail: Cocktail) = viewModelScope.launch {
        saveCocktailUseCase.execute(cocktail)
    }

    private suspend fun getListOfVisitedCocktails() = viewModelScope.launch {
        _visitedCocktails.value = getVisitedCocktailsUseCase.execute(Unit)
    }

}




