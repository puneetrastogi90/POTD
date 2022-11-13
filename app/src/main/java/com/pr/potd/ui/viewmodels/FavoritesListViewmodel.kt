package com.pr.potd.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pr.potd.ui.data.PotdUiModel
import com.pr.potd.database.dataobjects.entities.DatabaseResult
import com.pr.potd.intent.FavoritesIntent
import com.pr.potd.repositories.PotdRepository
import com.pr.potd.state.FavoritesScreenUiState
import com.pr.potd.utils.toPotdUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class FavoritesListViewmodel @Inject constructor(val potdRepository: PotdRepository) :
    ViewModel() {

    val favoritesIntent = Channel<FavoritesIntent>(Channel.UNLIMITED)
    private val _state = MutableStateFlow<FavoritesScreenUiState>(FavoritesScreenUiState.Idle)
    val state: StateFlow<FavoritesScreenUiState>
        get() = _state

    init {
        handleIntent()
    }

    private fun handleIntent() {
        viewModelScope.launch {
            favoritesIntent.consumeAsFlow().collect {
                when (it) {
                    is FavoritesIntent.FetchFavorites -> fetchFavorites()
                    is FavoritesIntent.ToggleFavorite -> toggleFavorite(it.potdUiModel)
                }
            }
        }
    }

    private fun toggleFavorite(potdUiModel: PotdUiModel) {
        viewModelScope.launch(Dispatchers.IO) {
            _state.value = FavoritesScreenUiState.Loading
            val response = potdRepository.updatePotd(potdUiModel.toPotdEntity())
            _state.value = when (response) {
                is DatabaseResult.Success -> {
                    FavoritesScreenUiState.ToggleFavoriteSuccess(response.body?.toPotdUiModel()!!)
                }
                is DatabaseResult.DataBaseError -> {
                    FavoritesScreenUiState.ToggleFavoriteFailed(response.errorBody)
                }

            }
        }
    }

    fun fetchFavorites() {
        viewModelScope.launch(Dispatchers.IO) {
            _state.value = FavoritesScreenUiState.Loading
            val response = potdRepository.fetchFavorites()
            _state.value = when (response) {
                is DatabaseResult.Success -> {
                    FavoritesScreenUiState.FetchFavoritesSuccess(response.body?.map { it.toPotdUiModel() }!!)
                }
                is DatabaseResult.DataBaseError -> {
                    FavoritesScreenUiState.FetchFavoritesFailed(response.errorBody)
                }
            }
        }
    }


}