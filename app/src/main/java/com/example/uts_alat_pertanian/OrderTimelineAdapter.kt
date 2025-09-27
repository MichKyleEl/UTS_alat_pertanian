package com.example.uts_alat_pertanian

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

data class OrderTimelineItem(
    val title: String,
    val description: String,
    val time: String
)

class OrderTimelineAdapter(
    private val items: MutableList<OrderTimelineItem> = mutableListOf()
) : RecyclerView.Adapter<OrderTimelineAdapter.VH>() {

    inner class VH(view: View) : RecyclerView.ViewHolder(view) {
        val tvTitle: TextView = view.findViewById(R.id.tvTitle)
        val tvDesc: TextView  = view.findViewById(R.id.tvDesc)
        val tvTime: TextView  = view.findViewById(R.id.tvTime)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_order_timeline, parent, false)
        return VH(v)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val it = items[position]
        holder.tvTitle.text = it.title
        holder.tvDesc.text  = it.description
        holder.tvTime.text  = it.time
    }

    override fun getItemCount(): Int = items.size

    fun submit(newItems: List<OrderTimelineItem>) {
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }
}
