package com.pr.potd.dataobjects.entities

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.pr.potd.network.data.PotdResponse

@Entity(tableName = "pictures", indices = [Index(value = ["date"], unique = true)])
data class PotdEntity(
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    val date: String,
    val explanation: String,
    val title: String,
    val hdUrl: String?,
    val url: String,
    var isFavorite: Boolean = false
)

