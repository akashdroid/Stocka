package com.akash.sample.features.movies

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import com.akash.sample.core.platform.BaseActivity
import com.akash.sample.core.platform.BaseFragment
import com.akash.sample.features.login.LoginActivity

class StocksActivity : BaseActivity(){

    val stockViewModel : StockViewModel by viewModels()

    companion object {
        fun callingIntent(context: Context) = Intent(context, StocksActivity::class.java)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        stockViewModel.loadStockDetails(1)

    }

    override fun fragment(): BaseFragment {
        TODO("Not yet implemented")
    }


}