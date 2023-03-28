package com.akash.sample.features.movies

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_stock.view.txt_current_value

class StocksViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val stockSummary: TextView = itemView.txt_current_value

    fun bindData(stock: StockViewModel.StockUI) {
        val symbol = stock.symbol
        val quantity = stock.quantity
        val ltp = stock.ltp
        val pnl = stock.pnl
        stockSummary.text = stock.toString()
    }

}
