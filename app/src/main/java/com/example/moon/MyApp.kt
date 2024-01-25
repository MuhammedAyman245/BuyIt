package com.example.moon

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.example.moon.services.SharedPreferenceManager

class MyApp: Application() {
    override fun onCreate() {
        super.onCreate()
        val sharedPreferenceManager = SharedPreferenceManager(this)
        AppCompatDelegate.setDefaultNightMode(sharedPreferenceManager.themeFlag[sharedPreferenceManager.theme])
    }
}