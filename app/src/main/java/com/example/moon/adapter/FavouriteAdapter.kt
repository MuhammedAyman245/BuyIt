package com.example.moon.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.moon.R
import com.example.moon.models.Product
import com.example.moon.screens.activities.ProductDetailsActivity
import com.example.moon.services.FavouriteProductList
import com.squareup.picasso.Picasso

class FavouriteAdapter(private val favouriteItems: List<Product>) :
    RecyclerView.Adapter<FavouriteAdapter.FavouriteProductViewHolder>() {

    class FavouriteProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val productNameTextView: TextView = itemView.findViewById(R.id.productTitle)
        val productPriceTextView: TextView = itemView.findViewById(R.id.productPrice)
        val productImage: ImageView = itemView.findViewById(R.id.productImg)
        val removeItem : ImageView = itemView.findViewById(R.id.removeItem)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavouriteProductViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.favourite_card, parent, false)
        return FavouriteProductViewHolder(view)
    }

    override fun getItemCount(): Int {
        return favouriteItems.size
    }

    override fun onBindViewHolder(holder: FavouriteProductViewHolder, position: Int) {
        val favItem = favouriteItems[position]
        holder.productNameTextView.text = favItem.title
        holder.productPriceTextView.text = favItem.price.toString()
        Picasso.get()
            .load(favItem.image)
            .placeholder(R.drawable.ic_launcher_background)
            .error(R.drawable.ic_launcher_foreground)
            .into(holder.productImage)
        holder.removeItem.setOnClickListener{
            FavouriteProductList.getInstance().removeItem(favItem)
            notifyDataSetChanged()
        }
        holder.itemView.setOnClickListener{
            val intent = Intent(holder.itemView.context, ProductDetailsActivity::class.java)
            intent.putExtra("productTitle",favItem.title)
            intent.putExtra("productPrice",favItem.price.toString())
            intent.putExtra("productDesc",favItem.description)
            intent.putExtra("productImg",favItem.image)
            intent.putExtra("productCat",favItem.category)
            intent.putExtra("productId",favItem.id)
            holder.itemView.context.startActivity(intent)
        }
    }
}