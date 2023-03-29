package com.akash.sample.features.stocks

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.akash.sample.core.extension.formatToRupee
import com.akash.sample.core.platform.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class StockViewModel @Inject constructor(
    private val getStockDetails: GetStockDetails,
) : BaseViewModel() {

    private val _stockDetails: MutableLiveData<List<StockDetailsView>> = MutableLiveData()

    val stockDetails: LiveData<List<StockUI>> = _stockDetails.map { stockDetails ->
        stockDetails.map { stockDetail ->
            val quantity = stockDetail.quantity ?: 0
            val ltp = stockDetail.ltp ?: 0.0
            val avgPrice = stockDetail.avgPrice.orEmpty().toDoubleOrNull() ?: 0.0
            val close = stockDetail.close ?: 0.0

            val currentValue = (ltp * quantity).formatToRupee()
            val investmentValue = (avgPrice * quantity).formatToRupee()
            val pnl = (currentValue - investmentValue).formatToRupee()
            val pnlToday = (close * quantity - currentValue).formatToRupee()
            StockUI(
                stockDetail.symbol.orEmpty(),
                quantity,
                ltp,
                pnl,
                currentValue,
                investmentValue,
                pnlToday
            )
        }
    }

    val stockSummary: LiveData<StockSummary> = stockDetails.map {
        it.fold(StockSummary(0.0, 0.0, 0.0, 0.0)) { initial: StockSummary, current: StockUI ->
            StockSummary(
                (initial.currentValue + current.currentValue).formatToRupee(),
                (initial.totalInvestment + current.investmentValue).formatToRupee(),
                (initial.pnl + current.pnl).formatToRupee(),
                (initial.pnlToday + current.pnlToday).formatToRupee()
            )
        }
    }
// added this (stockId: Int) param just to demonstrate how to pass params for post Api
    fun loadStockDetails(stockId: Int) =
        getStockDetails(GetStockDetails.Params(stockId), viewModelScope) {
            it.fold(
                ::handleFailure,
                ::handleStockDetails
            )
        }

    data class StockUI(
        val symbol: String,
        val quantity: Int,
        val ltp: Double,
        val pnl: Double,
        val currentValue: Double,
        val investmentValue: Double,
        val pnlToday: Double,
    )

    private fun handleStockDetails(stock: StockDetails) {
        val data = stock.data
        _stockDetails.value = data
    }
}