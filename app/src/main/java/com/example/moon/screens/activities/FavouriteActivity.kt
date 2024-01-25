package com.example.moon.screens.activities

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moon.MainActivity
import com.example.moon.adapter.FavouriteAdapter
import com.example.moon.databinding.ActivityFavouriteBinding
import com.example.moon.services.FavouriteProductList

class FavouriteActivity : AppCompatActivity()  {

    private lateinit var binding: ActivityFavouriteBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var favAdapter: FavouriteAdapter
    private lateinit var backBtn: ImageView
    private lateinit var emptyImage: ImageView
    private lateinit var emptyText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavouriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        recyclerView = binding.favouriteRv
        backBtn = binding.backButton
        emptyImage = binding.emptyCart
        emptyText = binding.emptyTextView

        if(FavouriteProductList.getInstance().getItems().isNotEmpty()){
            emptyImage.isVisible = false
            emptyText.isVisible = false
            recyclerView.isVisible = true

            recyclerView.layoutManager = LinearLayoutManager(this@FavouriteActivity)
            favAdapter = FavouriteAdapter(FavouriteProductList.getInstance().getItems())
            recyclerView.adapter = favAdapter
        } else{
            emptyImage.isVisible = true
            emptyText.isVisible = true
            recyclerView.isVisible = false
        }


        backBtn.setOnClickListener{
            val intent = Intent(applicationContext, MainActivity::class.java)
            startActivity(intent)
        }
    }

}