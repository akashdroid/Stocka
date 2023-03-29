package com.akash.sample.features.stocks

import retrofit2.Call
import retrofit2.http.GET

internal interface StocksApi {
    @GET("/v3/6d0ad460-f600-47a7-b973-4a779ebbaeaf")
    fun stockDetails(): Call<StockDetails>
}
