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
package com.akash.sample.core.navigation

import android.content.Context
import com.akash.sample.features.login.Authenticator
import com.akash.sample.features.stocks.StocksActivity
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class Navigator
@Inject constructor(private val authenticator: Authenticator) {

    // can write other cases like login or something
    fun showMain(context: Context) {
        when (authenticator.userLoggedIn()) {
            true -> showStocks(context)
            //false -> showLogin(context)
        }
    }

    private fun showStocks(context: Context) =
        context.startActivity(StocksActivity.callingIntent(context))

}


