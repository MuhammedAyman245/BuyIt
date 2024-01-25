package com.example.moon.models

data class Order(
    val orderId:String,
    val userAddress:String,
    val userName:String,
    val userNumber:Int,
    val totalPrice:String,
    val orderDetails:List<Product>
    )