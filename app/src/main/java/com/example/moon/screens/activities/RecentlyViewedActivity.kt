package com.example.moon.screens.activities

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moon.MainActivity
import com.example.moon.R
import com.example.moon.adapter.RecentlyViewedAdapter
import com.example.moon.services.ViewedList


class RecentlyViewedActivity : AppCompatActivity() {
    private lateinit var backButton: ImageView
    private lateinit var recyclerView: RecyclerView
    private lateinit var recentlyViewedAdapter:RecentlyViewedAdapter
    private lateinit var emptyImage: ImageView
    private lateinit var emptyText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recently_viewed)

        recyclerView = findViewById(R.id.recyclerView)
        backButton = findViewById(R.id.backButton)
        emptyImage = findViewById(R.id.emptyImg)
        emptyText = findViewById(R.id.emptyText)

        backButton.setOnClickListener{
            val intent = Intent(applicationContext, MainActivity::class.java)
            startActivity(intent)
        }

        if(ViewedList.getInstance().getItems().isNotEmpty()) {
         recyclerView.isVisible = true
            recyclerView.layoutManager = GridLayoutManager(applicationContext, 2)
            recentlyViewedAdapter =
                RecentlyViewedAdapter(ViewedList.getInstance().getItems().reversed())
            recyclerView.adapter = recentlyViewedAdapter
        } else{
            emptyImage.isVisible = true
            emptyText.isVisible = true
        }

    }

}