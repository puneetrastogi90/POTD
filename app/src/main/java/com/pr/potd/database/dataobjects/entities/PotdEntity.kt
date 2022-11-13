package com.pr.potd.database.dataobjects.entities

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "pictures", indices = [Index(value = ["date"], unique = true)])
data class PotdEntity(
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    val date: Long,
    val explanation: String,
    val title: String,
    val hdUrl: String?,
    val url: String,
    var isFavorite: Boolean = false
)

