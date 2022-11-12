package com.pr.potd.intent

import com.pr.potd.data.PotdUiModel

internal sealed class MainIntent {

    data class FetchPictureOftheDay(val dateInMillis: Long) : MainIntent()
    data class ToggleFavorite(val potdUiModel: PotdUiModel) : MainIntent()
}
