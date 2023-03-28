/**
 * Copyright (C) 2020 akash Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.akash.sample.features.stocks

import com.akash.sample.UnitTest
import com.akash.sample.core.exception.Failure.NetworkConnection
import com.akash.sample.core.exception.Failure.ServerError
import com.akash.sample.core.extension.empty
import com.akash.sample.core.functional.Either
import com.akash.sample.core.functional.Either.Right
import com.akash.sample.core.platform.NetworkHandler
import com.akash.sample.features.stocks.stocksRepository.Network
import io.mockk.Called
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import org.amshove.kluent.shouldBeInstanceOf
import org.amshove.kluent.shouldEqual
import org.junit.Before
import org.junit.Test
import retrofit2.Call
import retrofit2.Response

class stocksRepositoryTest : UnitTest() {

    private lateinit var networkRepository: stocksRepository.Network

    @MockK private lateinit var networkHandler: NetworkHandler
    @MockK private lateinit var service: StockService

    @MockK private lateinit var stocksCall: Call<List<stockEntity>>
    @MockK private lateinit var stocksResponse: Response<List<stockEntity>>
    @MockK private lateinit var stockDetailsCall: Call<stockDetailsEntity>
    @MockK private lateinit var stockDetailsResponse: Response<stockDetailsEntity>

    @Before fun setUp() {
        networkRepository = Network(networkHandler, service)
    }

    @Test fun `should return empty list by default`() {
        every { networkHandler.isNetworkAvailable() } returns true
        every { stocksResponse.body() } returns null
        every { stocksResponse.isSuccessful } returns true
        every { stocksCall.execute() } returns stocksResponse
        every { service.stocks() } returns stocksCall

        val stocks = networkRepository.stocks()

        stocks shouldEqual Right(emptyList<stock>())
        verify(exactly = 1) { service.stocks() }
    }

    @Test fun `should get stock list from service`() {
        every { networkHandler.isNetworkAvailable() } returns true
        every { stocksResponse.body() } returns listOf(stockEntity(1, "poster"))
        every { stocksResponse.isSuccessful } returns true
        every { stocksCall.execute() } returns stocksResponse
        every { service.stocks() } returns stocksCall

        val stocks = networkRepository.stocks()

        stocks shouldEqual Right(listOf(stock(1, "poster")))
        verify(exactly = 1) { service.stocks() }
    }

    @Test fun `stocks service should return network failure when no connection`() {
        every { networkHandler.isNetworkAvailable() } returns false

        val stocks = networkRepository.stocks()

        stocks shouldBeInstanceOf Either::class.java
        stocks.isLeft shouldEqual true
        stocks.fold({ failure -> failure shouldBeInstanceOf NetworkConnection::class.java }, {})
        verify { service wasNot Called }
    }

    @Test fun `stocks service should return server error if no successful response`() {
        every { networkHandler.isNetworkAvailable() } returns true
        every { stocksResponse.isSuccessful } returns false
        every { stocksCall.execute() } returns stocksResponse
        every { service.stocks() } returns stocksCall

        val stocks = networkRepository.stocks()

        stocks shouldBeInstanceOf Either::class.java
        stocks.isLeft shouldEqual true
        stocks.fold({ failure -> failure shouldBeInstanceOf ServerError::class.java }, {})
    }

    @Test fun `stocks request should catch exceptions`() {
        every { networkHandler.isNetworkAvailable() } returns true
        every { stocksCall.execute() } returns stocksResponse
        every { service.stocks() } returns stocksCall

        val stocks = networkRepository.stocks()

        stocks shouldBeInstanceOf Either::class.java
        stocks.isLeft shouldEqual true
        stocks.fold({ failure -> failure shouldBeInstanceOf ServerError::class.java }, {})
    }

    @Test fun `should return empty stock details by default`() {
        every { networkHandler.isNetworkAvailable() } returns true
        every { stockDetailsResponse.body() } returns null
        every { stockDetailsResponse.isSuccessful } returns true
        every { stockDetailsCall.execute() } returns stockDetailsResponse
        every { service.stockDetails(1) } returns stockDetailsCall

        val stockDetails = networkRepository.stockDetails(1)

        stockDetails shouldEqual Right(stockDetails.empty)
        verify(exactly = 1) { service.stockDetails(1) }
    }

    @Test fun `should get stock details from service`() {
        every { networkHandler.isNetworkAvailable() } returns true
        every { stockDetailsResponse.body() } returns
                stockDetailsEntity(8, "title", String.empty(), String.empty(),
                        String.empty(), String.empty(), 0, String.empty())
        every { stockDetailsResponse.isSuccessful } returns true
        every { stockDetailsCall.execute() } returns stockDetailsResponse
        every { service.stockDetails(1) } returns stockDetailsCall

        val stockDetails = networkRepository.stockDetails(1)

        stockDetails shouldEqual Right(stockDetails(8, "title", String.empty(),
            String.empty(), String.empty(), String.empty(), 0, String.empty()))
        verify(exactly = 1) { service.stockDetails(1) }
    }

    @Test fun `stock details service should return network failure when no connection`() {
        every { networkHandler.isNetworkAvailable() } returns false

        val stockDetails = networkRepository.stockDetails(1)

        stockDetails shouldBeInstanceOf Either::class.java
        stockDetails.isLeft shouldEqual true
        stockDetails.fold({ failure -> failure shouldBeInstanceOf NetworkConnection::class.java }, {})
        verify { service wasNot Called }
    }

    @Test fun `stock details service should return server error if no successful response`() {
        every { networkHandler.isNetworkAvailable() } returns true
        every { stockDetailsResponse.body() } returns null
        every { stockDetailsResponse.isSuccessful } returns false
        every { stockDetailsCall.execute() } returns stockDetailsResponse
        every { service.stockDetails(1) } returns stockDetailsCall

        val stockDetails = networkRepository.stockDetails(1)

        stockDetails shouldBeInstanceOf Either::class.java
        stockDetails.isLeft shouldEqual true
        stockDetails.fold({ failure -> failure shouldBeInstanceOf ServerError::class.java }, {})
    }

    @Test fun `stock details request should catch exceptions`() {
        every { networkHandler.isNetworkAvailable() } returns true
        every { stockDetailsCall.execute() } returns stockDetailsResponse
        every { service.stockDetails(1) } returns stockDetailsCall

        val stockDetails = networkRepository.stockDetails(1)

        stockDetails shouldBeInstanceOf Either::class.java
        stockDetails.isLeft shouldEqual true
        stockDetails.fold({ failure -> failure shouldBeInstanceOf ServerError::class.java }, {})
    }
}