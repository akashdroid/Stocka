package com.akash.sample.features.movies

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import com.akash.sample.R
import com.akash.sample.core.platform.BaseActivity
import kotlinx.android.synthetic.main.activity_stocks.stocks_list
import kotlinx.android.synthetic.main.activity_stocks.txt_current_value
import kotlinx.android.synthetic.main.activity_stocks.txt_total_investment

class StocksActivity : BaseActivity(){

    private val stockViewModel: StockViewModel by viewModels()

    companion object {
        fun callingIntent(context: Context) = Intent(context, StocksActivity::class.java)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stocks)
        stockViewModel.loadStockDetails(1)

        val adapter = StocksAdapter()
        stocks_list.adapter = adapter

        stockViewModel.stockDetails.observe(this) {
            adapter.setData(it)
        }

        stockViewModel.stockSummary.observe(this) {
            Log.d("xzxzxzx", "onCreate: $it")
            txt_current_value.text = it.currentValue.toString()
            txt_total_investment.text = it.totalInvestment.toString()
        }
    }
}