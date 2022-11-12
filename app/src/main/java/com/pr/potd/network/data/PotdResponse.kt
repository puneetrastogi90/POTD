package com.pr.potd.network.data

import com.google.gson.annotations.SerializedName

data class PotdResponse(
    var copyright: String? = null,
    var date: String,
    var explanation: String,
    var hdUrl: String,
    @SerializedName("media_type") var mediaType: String,
    @SerializedName("service_version") var serviceVersion: String,
    var title: String,
    var url: String
)
