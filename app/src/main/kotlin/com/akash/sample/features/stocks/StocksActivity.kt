package com.akash.sample.features.stocks

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import com.akash.sample.R
import com.akash.sample.core.platform.BaseActivity
import com.akash.sample.databinding.ActivityStocksBinding


class StocksActivity : BaseActivity(){

    private val stockViewModel: StockViewModel by viewModels()
    private var _binding: ActivityStocksBinding? = null
    private val binding get() = _binding!!
    companion object {
        fun callingIntent(context: Context) = Intent(context, StocksActivity::class.java)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityStocksBinding.inflate(layoutInflater)
        setContentView(_binding?.root)
        binding.progressCircular.visibility = View.VISIBLE
        stockViewModel.loadStockDetails(1)

        val adapter = StocksAdapter()
        binding.stocksList.adapter = adapter
        binding.stocksList.setDivider(R.drawable.recycler_view_divider_live_theme)

        stockViewModel.stockDetails.observe(this) {
            adapter.data = (it)
        }

        stockViewModel.stockSummary.observe(this) {
            binding.progressCircular.visibility = View.GONE
            binding.mtrlChildContentContainer.visibility = View.VISIBLE
            binding.currentValueAmt.text = "₹ ${it.currentValue}"
            binding.totalInvestmentAmt.text = "₹ ${it.totalInvestment}"
            binding.tplAmt.text = "₹ ${it.pnlToday}"
            binding.pLAmt.text = "₹ ${it.pnl}"
        }
    }
}