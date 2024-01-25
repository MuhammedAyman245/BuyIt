package com.example.moon.screens.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moon.MainActivity
import com.example.moon.R
import com.example.moon.adapter.CatAdapter
import com.example.moon.databinding.ActivityCategoryBinding
import com.example.moon.models.Product
import com.example.moon.services.FetchProduct
import com.example.moon.services.ProductApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CategoryActivity : AppCompatActivity() {

    private lateinit var catTitle:TextView
    private lateinit var binding: ActivityCategoryBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var catAdapter: CatAdapter
    private lateinit var backButton: ImageView
    private lateinit var emptyImage: ImageView
    private lateinit var emptyText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        catTitle = findViewById(R.id.catTitle)
        recyclerView = findViewById(R.id.catRecyclerView)
        backButton = binding.backButton

        var title:String = intent.getStringExtra("cat")!!

        catTitle.text = title

        loadProducts(title)

        backButton.setOnClickListener{
            val intent = Intent(applicationContext, MainActivity::class.java)
            startActivity(intent)
        }


    }

    private fun loadProducts(catTitle:String) {
        val productService = FetchProduct.buildService(ProductApi::class.java)
        val requestCall = productService.getProducts()
        requestCall.enqueue(object :Callback<List<Product>>{
            override fun onResponse(call: Call<List<Product>>, response: Response<List<Product>>) {
                if(response.isSuccessful){
                    val productList = response.body()!!
                    val products : List<Product> = productList.filter { it.category == catTitle }
                    recyclerView = binding.catRecyclerView
                    emptyImage = binding.emptyCart
                    emptyText = binding.emptyTextView
                    if(products.isEmpty()){
                        recyclerView.isVisible = false
                        emptyImage.isVisible = true
                        emptyText.isVisible = true
                    } else{
                        recyclerView.isVisible = true
                        emptyImage.isVisible = false
                        emptyText.isVisible = false
                        recyclerView.layoutManager = GridLayoutManager(applicationContext,2)
                        catAdapter = CatAdapter(products)
                        recyclerView.adapter = catAdapter
                    }

                }
            }

            override fun onFailure(call: Call<List<Product>>, t: Throwable) {
                Toast.makeText(applicationContext, t.message, Toast.LENGTH_SHORT).show()
            }
        })
    }

}