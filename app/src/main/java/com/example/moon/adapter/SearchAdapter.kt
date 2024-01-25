package com.example.moon.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.moon.R
import com.example.moon.models.Cart
import com.example.moon.models.Product
import com.example.moon.services.CartList
import com.example.moon.services.Communicator
import com.example.moon.services.FavouriteProductList
import com.example.moon.services.ViewedList
import com.google.firebase.auth.FirebaseAuth
import com.squareup.picasso.Picasso

class SearchAdapter (private var productList:List<Product>, private val listener: Communicator):
    RecyclerView.Adapter<SearchAdapter.SearchViewHolder>() {


    class SearchViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val productTitle: TextView = itemView.findViewById(R.id.productTitle)
        val productPrice: TextView = itemView.findViewById(R.id.productPrice)
        val productImage: ImageView = itemView.findViewById(R.id.productImg)
        val addToFavourite: ImageView = itemView.findViewById(R.id.addToFav)
        val addToCart: ImageView = itemView.findViewById(R.id.addToCart)
    }

    fun setFilteredList(productList: List<Product>){
        this.productList = productList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.search_product_card, parent, false)
        return  SearchViewHolder(view)
    }

    override fun getItemCount(): Int {
        return productList.size
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        val product = productList[position]
        val favouriteItem = FavouriteProductList.getInstance()
        val cartItem = CartList.getInstance()

        val auth: FirebaseAuth = FirebaseAuth.getInstance()

        holder.productTitle.text = product.title
        holder.productPrice.text = "${product.price} $"

        Picasso.get()
            .load(product.image)
            .placeholder(R.drawable.ic_launcher_background)
            .error(R.drawable.ic_launcher_foreground)
            .into(holder.productImage)

        val productDetails = Product(
            product.id,
            product.title,
            product.price,
            product.description,
            product.category,
            product.image,)

        holder.productTitle.setOnClickListener{
            if(ViewedList.getInstance().getItems().contains(productDetails)){} else{
                    ViewedList.getInstance().addItem(productDetails)
                }
            listener.onItemClicked(product)
        }

        holder.productImage.setOnClickListener{
            if(ViewedList.getInstance().getItems().contains(productDetails)){} else{
                ViewedList.getInstance().addItem(productDetails)
            }
            listener.onItemClicked(product)
        }
        holder.productPrice.setOnClickListener{
            if(ViewedList.getInstance().getItems().contains(productDetails)){} else{
                ViewedList.getInstance().addItem(productDetails)
            }
            listener.onItemClicked(product)
        }

        holder.addToFavourite.setOnClickListener{
            if(favouriteItem.getItems().contains(productDetails)){
                favouriteItem.removeItem(productDetails)
                holder.addToFavourite.setImageResource(R.drawable.heart_icon)
                Toast.makeText(holder.itemView.context, "Removed Successfully", Toast.LENGTH_SHORT)
                    .show()
            }else{
                favouriteItem.addItem(productDetails )
                holder.addToFavourite.setImageResource(R.drawable.red_heart_icon)
                Toast.makeText(holder.itemView.context, "added Successfully", Toast.LENGTH_SHORT)
                    .show()
            }
        }

        holder.addToCart.setOnClickListener{
            if(auth.currentUser!=null){
                if(cartItem.getItems().contains(Cart(product.title, product.price, 1, product.image))){
                    cartItem.removeItem(Cart(product.title, product.price, 1, product.image))
                    Toast.makeText(holder.itemView.context, "Removed from cart", Toast.LENGTH_SHORT).show()
                }else{
                    cartItem.addItem(Cart(product.title, product.price, 1, product.image))
                    Toast.makeText(holder.itemView.context, "Added to cart", Toast.LENGTH_SHORT).show()
                }

            }else{
                Toast.makeText(holder.itemView.context,"You have to sign in", Toast.LENGTH_SHORT).show()
            }
        }


    }


}
