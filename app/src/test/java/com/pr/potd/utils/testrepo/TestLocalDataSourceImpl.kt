package com.pr.potd.utils.testrepo

import com.pr.localdb.LocalDataSource
import com.pr.localdb.entities.PotdEntity

/**
 * This is a test implementation for unit testing of the repository
 *
 */
internal class TestLocalDataSourceImpl : LocalDataSource {

    override fun insertPictureOfTheDay(potdEntity: PotdEntity) {
        // Do nothing
    }

    override fun getPictureOfTheDay(date: Long): PotdEntity? {
        if (date != 1668277800000) {
            return PotdEntity(
                date = date,
                explanation = "this is test from database",
                title = "test database",
                hdUrl = null,
                url = "https://dummy.com",
                isFavorite = false
            )
        } else {
            return null
        }
    }

    override fun updatePictureOftheDay(potdEntity: PotdEntity): Int {
        if (potdEntity.date != 1668277800000) {
            return 1
        } else {
            return 0
        }
    }

    override fun getFavorites(): List<PotdEntity>? {
        return listOf(
            PotdEntity(
                date = 1668277800000,
                explanation = "this is test from database",
                title = "test database",
                hdUrl = null,
                url = "https://dummy.com",
                isFavorite = false
            ),
            PotdEntity(
                date = 1665340200000, //2022,oct,10
                explanation = "this is test from database",
                title = "test database",
                hdUrl = null,
                url = "https://dummy.com",
                isFavorite = true
            )
        )
    }

    override fun clearTable() {
    }

}