package com.pr.potd.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pr.localdb.entities.DatabaseResult
import com.pr.potd.ui.data.PotdUiModel
import com.pr.potd.intent.MainIntent
import com.pr.potd.network.data.Result
import com.pr.potd.repositories.PotdRepository
import com.pr.potd.state.MainState
import com.pr.potd.utils.NETWORK_DATE_FORMAT
import com.pr.potd.utils.convertMillisToDate
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
internal class MainViewmodel @Inject constructor(val potdRepository: PotdRepository) : ViewModel() {

    val mainIntent = Channel<MainIntent>(Channel.UNLIMITED)
    private val _state = MutableStateFlow<MainState>(MainState.Idle)
    val state: StateFlow<MainState>
        get() = _state

    var potdUiModel: PotdUiModel? = null

    init {
        handleIntent()
    }

    private fun handleIntent() {
        viewModelScope.launch {
            mainIntent.consumeAsFlow().collect {
                when (it) {
                    is MainIntent.FetchPictureOftheDay -> getPotd(
                        convertMillisToDate(
                            NETWORK_DATE_FORMAT,
                            it.dateInMillis
                        )
                    )
                    is MainIntent.ToggleFavorite -> toggleFavorite(it.potdUiModel)
                }
            }
        }
    }

    private fun toggleFavorite(model: PotdUiModel) {
        viewModelScope.launch(Dispatchers.IO) {
            _state.value = MainState.Loading
            val response = potdRepository.updatePotd(model.toPotdEntity())
            _state.value = when (response) {
                is DatabaseResult.Success -> {
                    potdUiModel = response.body!!.toPotdUiModel()
                    MainState.ToggleFavoriteSuccess(response.body?.toPotdUiModel()!!)
                }
                is DatabaseResult.DataBaseError -> {
                    MainState.ToggleFavoriteFailed(response.errorBody)
                }

            }
        }
    }

    fun getPotd(date: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _state.value = MainState.Loading
            val response = potdRepository.getPOTD(date)
            _state.value = when (response) {
                is Result.Success -> {
                    potdUiModel = response.body!!.toPotdUiModel()
                    MainState.FetchPotdSuccess(potdUiModel!!)
                }
                is Result.ApiError -> {
                    MainState.FetchPotdApiError(response.errorBody)
                }
                is Result.NetworkError -> {
                    MainState.FetchPotdNetworkError(response.error.message)
                }
                is Result.UnknownError -> {
                    MainState.FetchPotdUnknownError(response.error?.message)
                }
            }
        }
    }


}