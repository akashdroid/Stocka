package com.akash.sample.features.movies

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.akash.sample.R
import com.akash.sample.core.extension.inflate

class StocksAdapter : RecyclerView.Adapter<StocksViewHolder>() {

    private var data: List<StockViewModel.StockUI> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StocksViewHolder {
        return StocksViewHolder(parent.inflate(R.layout.item_stock))
    }

    override fun onBindViewHolder(holder: StocksViewHolder, position: Int) {
        holder.bindData(data[position])
    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun setData(data: List<StockViewModel.StockUI>) {
        this.data = data
        notifyDataSetChanged()
    }
}
