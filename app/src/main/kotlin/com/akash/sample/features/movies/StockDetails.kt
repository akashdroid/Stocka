package com.akash.sample.features.movies

import com.google.gson.annotations.SerializedName

data class StockDetails(
    @SerializedName("client_id") val clientId: String? = null,
    @SerializedName("data") val data: List<StockDetailsView> = arrayListOf(),
    @SerializedName("error") val error: String? = null,
    @SerializedName("response_type") val responseType: String? = null,
    @SerializedName("timestamp") val timestamp: Long? = null,
)