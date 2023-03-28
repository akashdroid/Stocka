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

class stockDetailsViewModelTest : AndroidTest() {

    private lateinit var stockDetailsViewModel: stockDetailsViewModel

    @MockK private lateinit var getstockDetails: GetstockDetails
    @MockK private lateinit var playstock: Playstock

    @Before
    fun setUp() {
        stockDetailsViewModel = stockDetailsViewModel(getstockDetails, playstock)
    }

    @Test fun `loading stock details should update live data`() {
        val stockDetails = stockDetails(0, "IronMan", "poster", "summary",
                "cast", "director", 2018, "trailer")
        coEvery { getstockDetails.run(any()) } returns Right(stockDetails)

        stockDetailsViewModel.stockDetails.observeForever {
            with(it!!) {
                id shouldEqualTo 0
                title shouldEqualTo "IronMan"
                poster shouldEqualTo "poster"
                summary shouldEqualTo "summary"
                cast shouldEqualTo "cast"
                director shouldEqualTo "director"
                year shouldEqualTo 2018
                trailer shouldEqualTo "trailer"
            }
        }

        runBlocking { stockDetailsViewModel.loadstockDetails(0) }
    }
}