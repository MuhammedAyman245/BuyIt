package com.example.moon.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.moon.R
import com.example.moon.models.Cart
import com.squareup.picasso.Picasso

class CheckoutAdapter (private val cartItems: List<Cart>) :
    RecyclerView.Adapter<CheckoutAdapter.CheckoutAdapterViewHolder>() {

    class CheckoutAdapterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val productNameTextView: TextView = itemView.findViewById(R.id.productName)
        val productPriceTextView: TextView = itemView.findViewById(R.id.productPrice)
        val quantityTextView: TextView = itemView.findViewById(R.id.quantity)
        val productImage : ImageView = itemView.findViewById(R.id.productImg)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CheckoutAdapterViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.check_out_card, parent, false)
        return CheckoutAdapterViewHolder(view)
    }

    override fun onBindViewHolder(holder: CheckoutAdapterViewHolder, position: Int) {
        val cartItem = cartItems[position]

        holder.productNameTextView.text = cartItem.productName
        holder.productPriceTextView.text = "$${cartItem.productPrice}"
        holder.quantityTextView.text = "Quantity: ${cartItem.quantity}"
        Picasso.get()
            .load(cartItem.pImg)
            .placeholder(R.drawable.ic_launcher_background)
            .error(R.drawable.ic_launcher_foreground)
            .into(holder.productImage)
    }

    override fun getItemCount(): Int {
        return cartItems.size
    }
}