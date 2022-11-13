package com.pr.potd.ui.data

import com.pr.localdb.entities.PotdEntity
import com.pr.potd.utils.UI_DATE_FORMAT
import com.pr.potd.utils.convertDateToMillis

internal data class PotdUiModel(
    val id: Int?,
    val date: String,
    val explanation: String,
    val title: String,
    val hdUrl: String?,
    val url: String,
    val isFavorite: Boolean = false
) {

    fun toPotdEntity(): PotdEntity = PotdEntity(
        id = this.id,
        date = convertDateToMillis(UI_DATE_FORMAT, this.date),
        explanation = this.explanation,
        title = this.title,
        hdUrl = this.hdUrl,
        url = this.url,
        isFavorite = this.isFavorite
    )

}

