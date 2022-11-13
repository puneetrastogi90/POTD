package com.pr.potd

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.pr.localdb.entities.DatabaseResult
import com.pr.localdb.entities.PotdEntity
import com.pr.potd.network.data.Result
import com.pr.potd.repositories.PotdRepositoryImpl
import com.pr.potd.utils.testrepo.TestLocalDataSourceImpl
import com.pr.potd.utils.testrepo.TestNetworkDataSourceImpl
import com.pr.potd.utils.toPotdUiModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.*
import org.mockito.MockitoAnnotations

internal class PotdRepositoryTest {


    val repository: PotdRepositoryImpl =
        PotdRepositoryImpl(TestNetworkDataSourceImpl(), TestLocalDataSourceImpl())

    @Test
    fun `return favorites from local database`() = runTest {
        val result: DatabaseResult<List<PotdEntity>, String> =
            repository.fetchFavorites() as DatabaseResult.Success
        Assert.assertEquals(
            2,
            (repository.fetchFavorites() as DatabaseResult.Success<List<PotdEntity>>).body?.size
        )
        Assert.assertEquals(
            1668277800000,
            (repository.fetchFavorites() as DatabaseResult.Success<List<PotdEntity>>).body!!.get(0).date
        )
        Assert.assertEquals(
            1665340200000,
            (repository.fetchFavorites() as DatabaseResult.Success<List<PotdEntity>>).body!!.get(1).date
        )
    }

    @Test
    fun `return error when a picture is updated in local database`() = runTest {
        val potdEntity = PotdEntity(
            date = 1668277800000,
            explanation = "this is test",
            title = "test",
            hdUrl = null,
            url = "https://dummy.com",
            isFavorite = false
        )
        val result = repository.updatePotd(potdEntity)
        Assert.assertTrue(result is DatabaseResult.DataBaseError)
    }

    @Test
    fun `return success when a picture is updated in local database`() = runTest {
        val potdEntity = PotdEntity(
            date = 1668277800110,
            explanation = "this is test",
            title = "test",
            hdUrl = null,
            url = "https://dummy.com",
            isFavorite = false
        )
        val result = repository.updatePotd(potdEntity)
        Assert.assertTrue(result is DatabaseResult.Success)
        Assert.assertEquals("11/13/2022", (result as DatabaseResult.Success).body!!.toPotdUiModel().date)
    }

    @Test
    fun `returns the picture of the day as PotdEntity after saving it in the database successfully`() = runTest {
        val result = repository.getPOTD("2022-11-11")
        Assert.assertTrue(result is Result.Success)
        Assert.assertEquals(1668105000000, (result as Result.Success).body!!.date)
        Assert.assertEquals("11/11/2022", (result as Result.Success).body!!.toPotdUiModel().date)
    }


    @Test
    fun `returns the picture of the day as PotdEntity from local database as api failed`() = runTest {
        val result = repository.getPOTD("2022-12-12")
        Assert.assertTrue(result is Result.Success)
        Assert.assertEquals(1670783400000, (result as Result.Success).body!!.date)
        Assert.assertEquals("12/12/2022", (result as Result.Success).body!!.toPotdUiModel().date)
    }

}