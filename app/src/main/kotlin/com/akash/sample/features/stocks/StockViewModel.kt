package com.akash.sample.features.stocks

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
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

            val currentValue = ltp * quantity
            val investmentValue = avgPrice * quantity
            val pnl = currentValue - investmentValue
            val pnlToday = close * quantity - currentValue
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
                initial.currentValue + current.currentValue,
                initial.totalInvestment + current.investmentValue,
                initial.pnl + current.pnl,
                initial.pnlToday + current.pnlToday
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


enum class Status {
    SUCCESS, ERROR, LOADING
}