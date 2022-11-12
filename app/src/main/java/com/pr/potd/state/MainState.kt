package com.pr.potd.state

import com.pr.potd.data.PotdUiModel
import com.pr.potd.network.data.ErrorResponse


internal sealed class MainState {

    object Idle : MainState()
    object Loading : MainState()
    data class FetchPotdSuccess(val result: PotdUiModel) : MainState()
    data class FetchPotdApiError(val error: String?) : MainState()
    data class FetchPotdNetworkError(val error: String?) : MainState()
    data class FetchPotdUnknownError(val error: String?) : MainState()
    data class ToggleFavoriteSuccess(val potdUiModel: PotdUiModel) : MainState()
    data class ToggleFavoriteFailed(val error: String?) : MainState()
}
