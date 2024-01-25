package com.example.moon.screens.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.moon.R
import android.os.Handler
import android.os.Looper
import com.example.moon.MainActivity

class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        Handler(Looper.myLooper()!!).postDelayed(Runnable {

            val intent = Intent(applicationContext, MainActivity::class.java)
            startActivity(intent)
        },2000)
    }
}