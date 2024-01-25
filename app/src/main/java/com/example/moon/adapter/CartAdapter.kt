package com.example.moon.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.moon.MainActivity
import com.example.moon.R
import com.example.moon.models.Cart
import com.example.moon.services.CartList
import com.squareup.picasso.Picasso

class CartAdapter(private val cartItems: List<Cart>) :
    RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    class CartViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val productNameTextView: TextView = itemView.findViewById(R.id.productName)
        val productPriceTextView: TextView = itemView.findViewById(R.id.productPrice)
        val quantityTextView: TextView = itemView.findViewById(R.id.quantity)
        val productImage : ImageView = itemView.findViewById(R.id.productImg)
        val removeItem : ImageView = itemView.findViewById(R.id.removeItem)
        val addButton : ImageView = itemView.findViewById(R.id.addBtn)
        val minusButton : ImageView = itemView.findViewById(R.id.minusBtn)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.cart_card, parent, false)
        return CartViewHolder(view)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val cartItem = cartItems[position]

        holder.productNameTextView.text = cartItem.productName
        holder.productPriceTextView.text = "$${cartItem.productPrice}"
        holder.quantityTextView.text = "Quantity: ${cartItem.quantity}"
        Picasso.get()
            .load(cartItem.pImg)
            .placeholder(R.drawable.ic_launcher_background)
            .error(R.drawable.ic_launcher_foreground)
            .into(holder.productImage)
        holder.addButton.setOnClickListener{
            if(cartItem.quantity <=10){
                cartItem.quantity ++
                notifyDataSetChanged()
            }
        }
        holder.minusButton.setOnClickListener{
            if(cartItem.quantity > 1){
                cartItem.quantity --
                notifyDataSetChanged()
            }
        }
        holder.removeItem.setOnClickListener{
            CartList.getInstance().removeItem(cartItem)
            notifyDataSetChanged()
            val intent = Intent(holder.itemView.context, MainActivity::class.java)
            holder.itemView.context.startActivity(intent)
        }

    }

    override fun getItemCount(): Int {
        return cartItems.size
    }
}