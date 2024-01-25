package com.example.moon.services

import com.example.moon.models.Product

interface Communicator {
    fun onItemClicked(product: Product)
}