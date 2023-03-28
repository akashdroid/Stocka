package com.akash.sample.features.stocks

import android.view.View
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_stock.view.*

class StocksViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val stockName: AppCompatTextView = itemView.symbol_name
    private val stockqty: AppCompatTextView = itemView.symbol_qty
    private val ltpAmt: AppCompatTextView = itemView.ltp_amt
    private val pl_Amt: AppCompatTextView = itemView.pl_amt

    fun bindData(stock: StockViewModel.StockUI) {
        val symbol = stock.symbol
        val quantity = stock.quantity
        val ltp = stock.ltp
        val pnl = stock.pnl
        stockName.text = symbol
        stockqty.text = quantity.toString()
        ltpAmt.text = " ₹ $ltp"
        pl_Amt.text = " ₹ $pnl"
    }

}
