package com.example.moon.screens.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moon.adapter.ProductAdapter
import com.example.moon.databinding.FragmentHomeBinding
import com.example.moon.models.Product
import com.example.moon.screens.activities.CategoryActivity
import com.example.moon.screens.activities.ProductDetailsActivity
import com.example.moon.services.Communicator
import com.example.moon.services.FetchProduct
import com.example.moon.services.ProductApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class HomeFragment : Fragment(),Communicator {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var productAdapter: ProductAdapter
    private lateinit var progressBar: ProgressBar




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater,container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        progressBar = binding.progressBar
        recyclerView = binding.productRv

        loadProducts()

        progressBar.isVisible = false
        recyclerView.isVisible = true

        binding.electronicCat.setOnClickListener{
            val intent = Intent(context, CategoryActivity::class.java)
            intent.putExtra("cat","electronics")
            startActivity(intent)
        }

        binding.clothesCat.setOnClickListener{
            val intent = Intent(context,CategoryActivity::class.java)
            intent.putExtra("cat","men's clothing")
            startActivity(intent)
        }

        binding.mobileCat.setOnClickListener{
            val intent = Intent(context,CategoryActivity::class.java)
            intent.putExtra("cat","mobile")
            startActivity(intent)
        }

        binding.shoesCat.setOnClickListener{
            val intent = Intent(context,CategoryActivity::class.java)
            intent.putExtra("cat","Shoes")
            startActivity(intent)
        }

        binding.watchCat.setOnClickListener{
            val intent = Intent(context,CategoryActivity::class.java)
            intent.putExtra("cat","jewelery")
            startActivity(intent)
        }

        binding.laptopCat.setOnClickListener{
            val intent = Intent(context,CategoryActivity::class.java)
            intent.putExtra("cat","laptops")
            startActivity(intent)
        }

        binding.banner1.setOnClickListener{
            val intent = Intent(context,CategoryActivity::class.java)
            intent.putExtra("cat","hoodie")
            startActivity(intent)
        }
        binding.banner2.setOnClickListener{
            val intent = Intent(context,CategoryActivity::class.java)
            intent.putExtra("cat","AirPods")
            startActivity(intent)
        }
    }


    private fun loadProducts() {
        val productService = FetchProduct.buildService(ProductApi::class.java)
        val requestCall = productService.getProducts()
        requestCall.enqueue(object :Callback<List<Product>>{
            override fun onResponse(call: Call<List<Product>>, response: Response<List<Product>>) {
                if(response.isSuccessful){
                    val productList = response.body()!!
                    recyclerView.layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL,false)
                    productAdapter= ProductAdapter(productList.take(5),this@HomeFragment)
                    recyclerView.adapter = productAdapter
                }
            }

            override fun onFailure(call: Call<List<Product>>, t: Throwable) {
                Toast.makeText(context, t.message, Toast.LENGTH_SHORT).show()
            }
        })
    }


    override fun onItemClicked(product: Product) {
        val intent = Intent(context, ProductDetailsActivity::class.java)
        intent.putExtra("productTitle",product.title)
        intent.putExtra("productPrice",product.price.toString())
        intent.putExtra("productDesc",product.description)
        intent.putExtra("productImg",product.image)
        intent.putExtra("productCat",product.category)
        intent.putExtra("productId",product.id.toString())
        startActivity(intent)
    }
}