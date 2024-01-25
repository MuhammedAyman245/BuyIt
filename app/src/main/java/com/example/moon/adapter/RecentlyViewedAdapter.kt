package com.example.moon.adapter

import android.content.Intent
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
import com.example.moon.screens.activities.ProductDetailsActivity
import com.example.moon.services.CartList
import com.example.moon.services.FavouriteProductList
import com.example.moon.services.ViewedList
import com.google.firebase.auth.FirebaseAuth
import com.squareup.picasso.Picasso

class RecentlyViewedAdapter (private var productList:List<Product>):
    RecyclerView.Adapter<RecentlyViewedAdapter.RecentlyViewedAdapterViewHolder>() {


    class RecentlyViewedAdapterViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val productTitle: TextView = itemView.findViewById(R.id.productTitle)
        val productPrice: TextView = itemView.findViewById(R.id.productPrice)
        val productImage: ImageView = itemView.findViewById(R.id.productImg)
        val addToFavourite: ImageView = itemView.findViewById(R.id.addToFav)
        val addToCart: ImageView = itemView.findViewById(R.id.addToCart)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecentlyViewedAdapterViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.search_product_card, parent, false)
        return  RecentlyViewedAdapterViewHolder(view)
    }

    override fun getItemCount(): Int {
        return productList.size
    }

    override fun onBindViewHolder(holder: RecentlyViewedAdapterViewHolder, position: Int) {
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

        if (favouriteItem.getItems().contains(productDetails)){
            holder.addToFavourite.setImageResource(R.drawable.red_heart_icon)
        }else{
            holder.addToFavourite.setImageResource(R.drawable.heart_icon)
        }

        holder.productTitle.setOnClickListener{
            if(ViewedList.getInstance().getItems().contains(productDetails)){} else{
                ViewedList.getInstance().addItem(productDetails)
            }
            val intent = Intent(holder.itemView.context, ProductDetailsActivity::class.java)
            intent.putExtra("productTitle",product.title)
            intent.putExtra("productPrice",product.price.toString())
            intent.putExtra("productDesc",product.description)
            intent.putExtra("productImg",product.image)
            intent.putExtra("productCat",product.category)
            intent.putExtra("productId",product.id.toString())
            holder.itemView.context.startActivity(intent)
        }

        holder.productImage.setOnClickListener{
            if(ViewedList.getInstance().getItems().contains(productDetails)){} else{
                ViewedList.getInstance().addItem(productDetails)
            }
            val intent = Intent(holder.itemView.context, ProductDetailsActivity::class.java)
            intent.putExtra("productTitle",product.title)
            intent.putExtra("productPrice",product.price.toString())
            intent.putExtra("productDesc",product.description)
            intent.putExtra("productImg",product.image)
            intent.putExtra("productCat",product.category)
            intent.putExtra("productId",product.id.toString())
            holder.itemView.context.startActivity(intent)
        }
        holder.productPrice.setOnClickListener{
            if(ViewedList.getInstance().getItems().contains(productDetails)){} else{
                ViewedList.getInstance().addItem(productDetails)
            }
            val intent = Intent(holder.itemView.context, ProductDetailsActivity::class.java)
            intent.putExtra("productTitle",product.title)
            intent.putExtra("productPrice",product.price.toString())
            intent.putExtra("productDesc",product.description)
            intent.putExtra("productImg",product.image)
            intent.putExtra("productCat",product.category)
            intent.putExtra("productId",product.id.toString())
            holder.itemView.context.startActivity(intent)
        }

        if(favouriteItem.getItems().contains(productDetails)){
            holder.addToFavourite.setImageResource(R.drawable.red_heart_icon)
            holder.addToFavourite.setOnClickListener{
                favouriteItem.removeItem(productDetails)
                notifyDataSetChanged()
                holder.addToFavourite.setImageResource(R.drawable.heart_icon)
                Toast.makeText(holder.itemView.context, "Removed Successfully", Toast.LENGTH_SHORT)
                    .show()
            }
        }
        else{
            holder.addToFavourite.setOnClickListener {
                favouriteItem.addItem(productDetails)
                holder.addToFavourite.setImageResource(R.drawable.red_heart_icon)
                Toast.makeText(holder.itemView.context, "added Successfully", Toast.LENGTH_SHORT)
                    .show()
            }
        }
        holder.addToCart.setOnClickListener{
            if(auth.currentUser != null){
                cartItem.addItem(Cart(product.title,product.price,1,product.image))
                Toast.makeText(holder.itemView.context, "Added to cart", Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(holder.itemView.context, "You have to sign in", Toast.LENGTH_SHORT).show()
            }
        }



    }


}
