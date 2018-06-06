package com.example.shotasaitou.myfeed

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.list_item_simple.view.*

class SimpleNumberAdapter : RecyclerView.Adapter<SimpleNumberAdapter.SimpleNumberViewHolder>() {

    private val items = arrayListOf(
            0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10
    )

    override fun onBindViewHolder(holder: SimpleNumberViewHolder, position: Int) {
        holder.bindData(items[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SimpleNumberViewHolder {
        return SimpleNumberViewHolder.newInstance(parent)
    }

    override fun getItemCount(): Int = items.size

    fun removeAt(position: Int) {
        items.removeAt(position)
        notifyItemRemoved(position)
    }

    class SimpleNumberViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        companion object {
            fun newInstance(parent: ViewGroup): SimpleNumberViewHolder {
                return SimpleNumberViewHolder(
                        LayoutInflater.from(parent.context).inflate(
                                R.layout.list_item_simple,
                                parent,
                                false
                        )
                )
            }
        }

        fun bindData(item: Int) {
            itemView.text.text = item.toString()
        }
    }
}
