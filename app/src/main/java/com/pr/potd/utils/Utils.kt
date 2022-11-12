package com.pr.potd.utils

import android.text.format.DateFormat
import com.pr.potd.data.PotdUiModel
import com.pr.potd.dataobjects.entities.PotdEntity
import com.pr.potd.network.data.PotdResponse

internal fun convertMillisToDate(dateFormat: String, dateInMilliseconds: Long): String {
    return DateFormat.format(dateFormat, dateInMilliseconds).toString()
}


internal fun PotdEntity.toPotdUiModel(): PotdUiModel {
    return PotdUiModel(
        id = this.id ,
        date = this.date,
        explanation = this.explanation,
        title = this.title,
        hdUrl = this.hdUrl,
        url = this.url,
        isFavorite = this.isFavorite
    )
}


internal fun PotdResponse.toPotdEntity(): PotdEntity {
    return PotdEntity(
        date = this.date,
        explanation = this.explanation,
        title = this.title,
        hdUrl = this.hdUrl,
        url = this.url
    )
}