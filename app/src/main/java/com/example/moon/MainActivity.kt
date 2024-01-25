package com.example.moon

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.moon.databinding.ActivityMainBinding
import com.example.moon.models.Product
import com.example.moon.screens.fragments.AccountFragment
import com.example.moon.screens.fragments.CartFragment
import com.example.moon.screens.fragments.HomeFragment
import com.example.moon.screens.fragments.SearchFragment
import com.example.moon.services.FetchProduct
import com.example.moon.services.ProductApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        replaceFragment(HomeFragment())

        binding.bottomNavigation.setOnItemSelectedListener{
            when(it.itemId){
                R.id.ic_home -> {
                    replaceFragment(HomeFragment())
                    true
                }
                R.id.ic_search -> {
                    replaceFragment(SearchFragment())
                    true
                }

                R.id.ic_cart -> {
                    replaceFragment(CartFragment())
                    true
                }

                R.id.ic_account ->{
                    replaceFragment(AccountFragment())
                    true
                }
                else ->{
                    true
                }
            }


        }


    }

    private fun replaceFragment(fragment: Fragment) {

        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout,fragment)
        fragmentTransaction.commit()
    }
    private fun loadProducts(textView:TextView) {

        val productService = FetchProduct.buildService(ProductApi::class.java)
        val requestCall = productService.getProducts()
        requestCall.enqueue(object :Callback<List<Product>>{
            override fun onResponse(call: Call<List<Product>>, response: Response<List<Product>>) {
                if(response.isSuccessful){
                    val productList = response.body()!!
                    textView.text = productList.toString()
                }
            }

            override fun onFailure(call: Call<List<Product>>, t: Throwable) {
                Toast.makeText(applicationContext, t.message, Toast.LENGTH_SHORT).show()
            }
        })
    }

}





