package com.akash.sample.features.stocks

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

abstract class BaseAdapter<T> : RecyclerView.Adapter<StocksViewHolder>() {

    var data = mutableListOf<T>()
        set(value) {
            if (field === value) return

            val diff = DiffUtil.calculateDiff(getDiffUtilCallback(field, value))
            field = value
            diff.dispatchUpdatesTo(this)
            onDataSetChanged()
        }

    fun getItem(position: Int): T {
        return try {
            if (position != RecyclerView.NO_POSITION && position < data.size)
                data[position]
            else data[0]
        } catch (e: NullPointerException) {
            return null!!
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

    open fun getRealPosition(position: Int): Int {
        return if (itemCount <= 1) {
            0
        } else {
            position % itemCount
        }
    }

    open fun getLoopPosition(position: Int): Int {
        val realPosition = getRealPosition(position)
        return if (realPosition == 0) {
            itemCount
        } else {
            realPosition
        }
    }

    open fun onDataSetChanged() {
        notifyDataSetChanged()
    }

    private fun getDiffUtilCallback(oldData: List<T>, newData: List<T>): DiffUtil.Callback {
        return object : DiffUtil.Callback() {
            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
                areItemsTheSame(oldData[oldItemPosition], newData[newItemPosition])

            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) =
                areContentsTheSame(oldData[oldItemPosition], newData[newItemPosition])

            override fun getOldListSize() = oldData.size

            override fun getNewListSize() = newData.size
        }
    }

    protected open fun areItemsTheSame(old: T, new: T): Boolean {
        return old == new
    }

    protected open fun areContentsTheSame(old: T, new: T): Boolean {
        return old == new
    }


}