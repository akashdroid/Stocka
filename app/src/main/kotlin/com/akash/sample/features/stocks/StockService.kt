package com.akash.sample.features.stocks

import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StockService
@Inject constructor(retrofit: Retrofit) : StocksApi {
    private val stockApi by lazy { retrofit.create(StocksApi::class.java) }

    override fun stockDetails() = stockApi.stockDetails()
}
