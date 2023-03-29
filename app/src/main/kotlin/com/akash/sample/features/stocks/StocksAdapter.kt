package com.akash.sample.features.stocks

import android.view.ViewGroup
import com.akash.sample.R
import com.akash.sample.core.extension.inflate

class StocksAdapter : BaseAdapter<StockViewModel.StockUI, StocksViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StocksViewHolder {
        return StocksViewHolder(parent.inflate(R.layout.item_stock))
    }

    override fun onBindViewHolder(holder: StocksViewHolder, position: Int) {
        holder.bindData(getItem(position))
    }

    override fun getItemCount(): Int {
        return data.size
    }
}
