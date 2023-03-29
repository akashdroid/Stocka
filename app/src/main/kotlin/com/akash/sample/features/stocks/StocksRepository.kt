package com.akash.sample.features.stocks

import android.util.Log
import com.akash.sample.core.exception.Failure
import com.akash.sample.core.functional.Either
import com.akash.sample.core.platform.NetworkHandler
import javax.inject.Inject
import retrofit2.Call

interface StocksRepository {
    fun stockDetails(): Either<Failure, StockDetails>

    class Network
    @Inject constructor(
        private val networkHandler: NetworkHandler,
        private val service: StockService,
    ) : StocksRepository {


        override fun stockDetails(): Either<Failure, StockDetails> {
            return when (networkHandler.isNetworkAvailable()) {
                true -> request(
                    service.stockDetails(),
                    { it },
                    StockDetails("", ArrayList())
                )
                false -> Either.Left(Failure.NetworkConnection)
            }
        }

        private fun <T, R : Any> request(
            call: Call<T>,
            transform: (T) -> R,
            default: T,
        ): Either<Failure, R> {
            return try {
                val response = call.execute()
                when (response.isSuccessful) {
                    true -> Either.Right(transform((response.body() ?: default)))
                    false -> Either.Left(Failure.ServerError)
                }
            } catch (exception: Throwable) {
                Log.d("xzxzxz", "request: $exception")
                Either.Left(Failure.ServerError)
            }
        }
    }
}
