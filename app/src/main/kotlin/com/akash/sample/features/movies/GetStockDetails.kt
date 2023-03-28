package com.akash.sample.features.movies


import com.akash.sample.core.interactor.UseCase
import javax.inject.Inject

class GetStockDetails
@Inject constructor(private val stockRepository: StocksRepository) :
    UseCase<StockDetails, GetStockDetails.Params>() {

    override suspend fun run(params: Params) = stockRepository.stockDetails()

    data class Params(val id: Int)
}
