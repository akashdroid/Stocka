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

import com.akash.sample.AndroidTest
import com.akash.sample.core.functional.Either.Right
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.runBlocking
import org.amshove.kluent.shouldEqualTo
import org.junit.Before
import org.junit.Test

class stocksViewModelTest : AndroidTest() {

    private lateinit var stocksViewModel: stocksViewModel

    @MockK private lateinit var getstocks: Getstocks

    @Before
    fun setUp() {
        stocksViewModel = stocksViewModel(getstocks)
    }

    @Test fun `loading stocks should update live data`() {
        val stocksList = listOf(stock(0, "IronMan"), stock(1, "Batman"))
        coEvery { getstocks.run(any()) } returns Right(stocksList)

        stocksViewModel.stocks.observeForever {
            it!!.size shouldEqualTo 2
            it[0].id shouldEqualTo 0
            it[0].poster shouldEqualTo "IronMan"
            it[1].id shouldEqualTo 1
            it[1].poster shouldEqualTo "Batman"
        }

        runBlocking { stocksViewModel.loadstocks() }
    }
}