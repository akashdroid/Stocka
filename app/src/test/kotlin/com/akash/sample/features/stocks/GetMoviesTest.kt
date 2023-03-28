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
import com.akash.sample.core.functional.Either.Right
import com.akash.sample.core.interactor.UseCase
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class GetstocksTest : UnitTest() {

    private lateinit var getstocks: Getstocks

    @MockK private lateinit var stocksRepository: stocksRepository

    @Before fun setUp() {
        getstocks = Getstocks(stocksRepository)
        every { stocksRepository.stocks() } returns Right(listOf(stock.empty))
    }

    @Test fun `should get data from repository`() {
        runBlocking { getstocks.run(UseCase.None()) }

        verify(exactly = 1) { stocksRepository.stocks() }
    }
}
