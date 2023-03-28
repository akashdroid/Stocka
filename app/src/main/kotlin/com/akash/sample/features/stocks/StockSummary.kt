package com.akash.sample.features.stocks

data class StockSummary(
    val currentValue: Double,
    val totalInvestment: Double,
    val pnl: Double,
    val pnlToday: Double,
)
