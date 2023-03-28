package com.akash.sample.features.movies

import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MoviesService
@Inject constructor(retrofit: Retrofit) : StocksApi {
    private val stockApi by lazy { retrofit.create(StocksApi::class.java) }

    override fun stockDetails(movieId: Int) = stockApi.stockDetails(movieId)
}
