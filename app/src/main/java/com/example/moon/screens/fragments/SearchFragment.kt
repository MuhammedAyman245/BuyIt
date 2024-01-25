package com.example.moon.screens.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moon.adapter.SearchAdapter
import com.example.moon.databinding.FragmentSearchBinding
import com.example.moon.models.Product
import com.example.moon.screens.activities.ProductDetailsActivity
import com.example.moon.services.Communicator
import com.example.moon.services.FetchProduct
import com.example.moon.services.ProductApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.Locale


class SearchFragment : Fragment(),Communicator {

    private lateinit var searchView: SearchView
    private lateinit var recyclerView: RecyclerView
    private lateinit var binding: FragmentSearchBinding
    private lateinit var searchAdapter: SearchAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSearchBinding.inflate(inflater,container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        searchView = binding.searchView
        recyclerView = binding.recyclerView

        loadProducts()

    }

    private fun loadProducts() {
        val productService = FetchProduct.buildService(ProductApi::class.java)
        val requestCall = productService.getProducts()
        requestCall.enqueue(object : Callback<List<Product>> {
            override fun onResponse(call: Call<List<Product>>, response: Response<List<Product>>) {
                if(response.isSuccessful){
                    val productList = response.body()!!

                    recyclerView.layoutManager = GridLayoutManager(context,2)
                    searchAdapter= SearchAdapter(productList.take(6),this@SearchFragment)
                    recyclerView.adapter = searchAdapter

                    searchView.setOnQueryTextListener(object :SearchView.OnQueryTextListener{
                            override fun onQueryTextSubmit(query: String?): Boolean {
                                return false
                            }

                            override fun onQueryTextChange(newText: String?): Boolean {
                                val filteredList = ArrayList<Product>()
                                for(i in productList){
                                    if(i.title.lowercase(Locale.ROOT).contains(newText!!)){
                                        filteredList.add(i)
                                    }
                                }
                                if(filteredList.isEmpty()){
                                    Toast.makeText(context,"No data found",Toast.LENGTH_SHORT).show()
                                }else{
                                    searchAdapter.setFilteredList(filteredList)
                                }
                                return true
                            }
                        })
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