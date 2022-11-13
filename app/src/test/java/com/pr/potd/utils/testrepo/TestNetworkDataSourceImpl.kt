package com.pr.potd.utils.testrepo

import com.pr.network.NetworkDataSource
import com.pr.potd.network.data.PotdResponse
import com.pr.potd.network.data.Result

/**
 * This is a dummy implementation of response from the network
 *
 */
internal class TestNetworkDataSourceImpl : NetworkDataSource {

    override suspend fun fetchNasaPotd(date: String): Result<PotdResponse, String> {
        if (date != "2022-12-12") {
            val potdResponse = PotdResponse(
                date = date,
                explanation = "this is dummy response from network",
                url = "dummyUrl",
                mediaType = "image",
                serviceVersion = "v1",
                title = "Dummy",
                hdUrl = "Dummy"
            )
            return Result.Success(potdResponse)
        } else {
            return Result.ApiError("date is a future date.")
        }

    }
}