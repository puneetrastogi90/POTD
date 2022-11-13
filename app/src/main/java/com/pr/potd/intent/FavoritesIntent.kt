package com.pr.potd.intent

import com.pr.potd.ui.data.PotdUiModel

internal sealed class FavoritesIntent {

    object FetchFavorites : FavoritesIntent()
    data class ToggleFavorite(val potdUiModel: PotdUiModel) : FavoritesIntent()
}
