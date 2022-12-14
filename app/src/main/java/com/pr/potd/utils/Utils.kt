package com.pr.potd.utils

import com.pr.localdb.entities.PotdEntity
import com.pr.potd.ui.data.PotdUiModel
import com.pr.potd.network.data.PotdResponse
import java.text.SimpleDateFormat
import java.util.*

const val NETWORK_DATE_FORMAT = "yyyy-MM-dd"
const val UI_DATE_FORMAT = "MM/dd/yyyy"


internal fun convertMillisToDate(dateFormat: String, dateInMilliseconds: Long): String {
    val sdf = SimpleDateFormat(dateFormat)
    val date = Date(dateInMilliseconds)
    return sdf.format(date).toString()
}

internal fun convertDateToMillis(dateFormat: String, dateInString: String): Long {
    val sdf = SimpleDateFormat(dateFormat)
    val mDate: Date = sdf.parse(dateInString)
    val timeInMilliseconds: Long = mDate.getTime()
    return timeInMilliseconds
}


internal fun PotdEntity.toPotdUiModel(): PotdUiModel {
    return PotdUiModel(
        id = this.id,
        date = convertMillisToDate(UI_DATE_FORMAT, this.date),
        explanation = this.explanation,
        title = this.title,
        hdUrl = this.hdUrl,
        url = this.url,
        mediaType = this.mediaType,
        isFavorite = this.isFavorite
    )
}


internal fun PotdResponse.toPotdEntity(): PotdEntity {
    return PotdEntity(
        date = convertDateToMillis(NETWORK_DATE_FORMAT, this.date),
        explanation = this.explanation,
        title = this.title,
        hdUrl = this.hdUrl,
        mediaType = this.mediaType,
        url = this.url
    )
}