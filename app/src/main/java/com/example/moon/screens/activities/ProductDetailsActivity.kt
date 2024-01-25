package com.example.moon.screens.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import com.example.moon.MainActivity
import com.example.moon.R
import com.example.moon.models.Cart
import com.example.moon.models.Product
import com.example.moon.services.CartList
import com.example.moon.services.FavouriteProductList
import com.google.firebase.auth.FirebaseAuth
import com.squareup.picasso.Picasso


class ProductDetailsActivity : AppCompatActivity() {
    private lateinit var titleTv: TextView
    private lateinit var priceTv: TextView
    private lateinit var descTv: TextView
    private lateinit var productCategory: TextView
    private lateinit var cartText: TextView
    private lateinit var addCartProduct: CardView
    private lateinit var addFavouriteProduct: ImageView
    private lateinit var productImage: ImageView
    private lateinit var backBtn: ImageView
    private val cartItem = CartList.getInstance()
    private val favouriteItem = FavouriteProductList.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_details)

        titleTv = findViewById(R.id.productTitle)
        priceTv = findViewById(R.id.productPrice)
        descTv = findViewById(R.id.productDescription)
        productCategory = findViewById(R.id.productCat)
        addCartProduct = findViewById(R.id.addToCart)
        addFavouriteProduct = findViewById(R.id.addToFavourite)
        productImage = findViewById(R.id.productImg)
        backBtn = findViewById(R.id.backButton)
        cartText = findViewById(R.id.cartTv)

        val productTitle = intent.getStringExtra("productTitle")
        val productPrice = intent.getStringExtra("productPrice")
        val productDesc = intent.getStringExtra("productDesc")
        val imgUrl = intent.getStringExtra("productImg")
        val productId = intent.getStringExtra("productId")
        val productCat = intent.getStringExtra("productCat")
        val auth: FirebaseAuth = FirebaseAuth.getInstance()

        titleTv.text = productTitle
        priceTv.text = "$productPrice $"
        descTv.text = productDesc
        productCategory.text = productCat

        Picasso.get()
            .load(imgUrl)
            .placeholder(R.drawable.ic_launcher_background)
            .error(R.drawable.ic_launcher_foreground)
            .into(productImage)

        val productDetails:Product = Product(
            productId!!.toInt(),
            productTitle!!,
            productPrice!!.toDouble(),
            productDesc!!,
            productCat!!,
            imgUrl!!,)

        if (favouriteItem.getItems().contains(productDetails)){
            addFavouriteProduct.setImageResource(R.drawable.red_heart_icon)
        }else{
           addFavouriteProduct.setImageResource(R.drawable.heart_icon)
        }


        backBtn.setOnClickListener{
            val intent = Intent(applicationContext, MainActivity::class.java)
            startActivity(intent)
        }

        addCartProduct.setOnClickListener{
            if(auth.currentUser!=null){
                if(cartItem.getItems().contains(Cart(productTitle!!, productPrice!!.toDouble(), 1, imgUrl!!))){
                    cartItem.removeItem(Cart(productTitle!!, productPrice!!.toDouble(), 1, imgUrl!!))
                    cartText.text = "Add to cart"
                }else{
                    cartItem.addItem(Cart(productTitle!!, productPrice.toDouble()!!, 1, imgUrl!!))
                    cartText.text = "Added to cart"
                }

            }else{
                Toast.makeText(applicationContext,"You have to sign in", Toast.LENGTH_SHORT).show()

            }
        }

        addFavouriteProduct.setOnClickListener{
            if(favouriteItem.getItems().contains(productDetails)){
                favouriteItem.removeItem(productDetails)
                addFavouriteProduct.setImageResource(R.drawable.heart_icon)
                Toast.makeText(applicationContext, "Removed Successfully", Toast.LENGTH_SHORT)
                    .show()
            }else{
                favouriteItem.addItem(productDetails )
                addFavouriteProduct.setImageResource(R.drawable.red_heart_icon)
                Toast.makeText(applicationContext, "added Successfully", Toast.LENGTH_SHORT)
                    .show()
            }
        }

        }


}