package com.akash.sample.features.movies

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.akash.sample.core.platform.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class StockViewModel@Inject constructor(
    private val getStockDetails: GetStockDetails,
) : BaseViewModel() {

    private val _stockDetails: MutableLiveData<StockDetails> = MutableLiveData()
    val stockDetails: LiveData<StockDetails> = _stockDetails

    fun loadStockDetails(stockId: Int) =
        getStockDetails(GetStockDetails.Params(stockId), viewModelScope) {
            it.fold(
                ::handleFailure,
                ::handleStockDetails
            )
        }


    private fun handleStockDetails(stock: StockDetails) {
        _stockDetails.value = StockDetails(
         stock.client_id,stock.data
        )
    }

}