package com.pr.potd.state

import com.pr.potd.ui.data.PotdUiModel


internal sealed class FavoritesScreenUiState {

    object Idle : FavoritesScreenUiState()
    object Loading : FavoritesScreenUiState()
    data class FetchFavoritesSuccess(val result: List<PotdUiModel>) : FavoritesScreenUiState()
    data class FetchFavoritesFailed(val error: String?) : FavoritesScreenUiState()
    data class ToggleFavoriteSuccess(val potdUiModel: PotdUiModel) : FavoritesScreenUiState()
    data class ToggleFavoriteFailed(val error: String?) : FavoritesScreenUiState()
}
