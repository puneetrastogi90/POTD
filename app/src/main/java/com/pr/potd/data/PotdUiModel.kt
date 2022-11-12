package com.pr.potd.data

import com.pr.potd.dataobjects.entities.PotdEntity

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
        date = this.date,
        explanation = this.explanation,
        title = this.title,
        hdUrl = this.hdUrl,
        url = this.url,
        isFavorite = this.isFavorite
    )

}

