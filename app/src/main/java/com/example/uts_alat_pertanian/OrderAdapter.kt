package com.example.uts_alat_pertanian

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

data class Order(
    val name: String,
    val price: String,
    val status: String,
    val imageRes: Int
)

class OrderAdapter(private val orderList: List<Order>) :
    RecyclerView.Adapter<OrderAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgOrderProduct: ImageView = itemView.findViewById(R.id.imgOrderProduct)
        val tvOrderName: TextView = itemView.findViewById(R.id.tvOrderName)
        val tvOrderStatus: TextView = itemView.findViewById(R.id.tvOrderStatus)
        val tvOrderPrice: TextView = itemView.findViewById(R.id.tvOrderPrice)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_order, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val order = orderList[position]
        holder.imgOrderProduct.setImageResource(order.imageRes)
        holder.tvOrderName.text = order.name
        holder.tvOrderStatus.text = "Status: ${order.status}"
        holder.tvOrderPrice.text = order.price
    }

    override fun getItemCount(): Int = orderList.size
}
