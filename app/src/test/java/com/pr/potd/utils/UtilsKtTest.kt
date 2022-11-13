package com.pr.potd.utils

import com.pr.localdb.entities.PotdEntity
import com.pr.potd.ui.data.PotdUiModel
import org.junit.*

/**
 * Unit tests for utility functions.
 *
 */
internal class UtilsKtTest {

    @Test
    fun `returns date in string in ui format when time is passed in millisecond`() {
        val inputMilli = 1668337679883
        val expectedValue = "11/13/2022"
        Assert.assertEquals(expectedValue, convertMillisToDate(UI_DATE_FORMAT, inputMilli))
    }

    @Test
    fun `returns millisecond when date is given in ui date format as string`() {
        val inputDateStr = "11/13/2022"
        val expectedValue = 1668277800000
        Assert.assertEquals(expectedValue, convertDateToMillis(UI_DATE_FORMAT, inputDateStr))
    }

    @Test
    fun `returns date in string in network format when time is passed in millisecond`() {
        val inputMilli = 1668337679883
        val expectedValue = "2022-11-13"
        Assert.assertEquals(expectedValue, convertMillisToDate(NETWORK_DATE_FORMAT, inputMilli))
    }

    @Test
    fun `returns millisecond when date is given in network date format as string`() {
        val inputDateStr = "2022-11-13"
        val expectedValue = 1668277800000
        Assert.assertEquals(expectedValue, convertDateToMillis(NETWORK_DATE_FORMAT, inputDateStr))
    }


    @Test
    fun toPotdUiModel() {
        val potdEntity = PotdEntity(
            date = 1668277800000,
            explanation = "this is test",
            title = "test",
            hdUrl = null,
            url = "https://dummy.com",
            isFavorite = false
        )

        Assert.assertEquals("11/13/2022", potdEntity.toPotdUiModel().date)
        Assert.assertEquals("this is test", potdEntity.toPotdUiModel().explanation)

    }

    @Test
    fun toPotdEntity() {
        val potdUiModel = PotdUiModel(
            id = null,
            date = "11/13/2022",
            explanation = "this is test",
            title = "test",
            hdUrl = null,
            url = "https://dummy.com",
            isFavorite = false
        )

        Assert.assertEquals(1668277800000, potdUiModel.toPotdEntity().date)
        Assert.assertEquals("this is test", potdUiModel.toPotdEntity().explanation)
    }
}