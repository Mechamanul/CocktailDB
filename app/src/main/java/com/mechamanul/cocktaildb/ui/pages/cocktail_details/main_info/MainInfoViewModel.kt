package com.mechamanul.cocktaildb.ui.pages.cocktail_details.main_info

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mechamanul.cocktaildb.domain.common.AppException
import com.mechamanul.cocktaildb.domain.common.Result
import com.mechamanul.cocktaildb.domain.usecase.ChangeLikeStateUseCase
import com.mechamanul.cocktaildb.domain.usecase.GetCocktailLikeStateUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

typealias LikeState = Result<Flow<Boolean>>

@HiltViewModel
class MainInfoViewModel @Inject constructor(
    private val changeLikeStateUseCase: ChangeLikeStateUseCase,
    private val getCocktailLikeStateUseCase: GetCocktailLikeStateUseCase,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {
    private val _likeState: MutableStateFlow<LikeState> =
        MutableStateFlow(Result.Success(emptyFlow()))
    val likeState: StateFlow<LikeState> = _likeState
    val id: Int? = savedStateHandle["id"]

    init {
        viewModelScope.launch {

            if (id != null) {
                Log.d("MainInfoViewModel", id.toString())
                _likeState.value = getCocktailLikeStateUseCase.execute(id)
            } else {
                _likeState.value =
                    Result.Error(AppException("Somehow this fragment didn't received cocktail id "))
            }
        }
    }

    fun changeLikeState() = viewModelScope.launch {
        if (id != null) {
            changeLikeStateUseCase.execute(id)
        }
    }
}