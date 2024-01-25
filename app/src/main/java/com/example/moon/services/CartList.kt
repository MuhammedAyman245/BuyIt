package com.example.moon.services

import com.example.moon.models.Cart

class CartList() {
    var list:MutableList<Cart> = mutableListOf()

    fun clearList(){
         list.clear()
    }

    fun addItem(cartItem:Cart) {
        if(!list.contains(cartItem)){
            list.add(cartItem)
        }
    }

    fun getItems() : List<Cart>{
        return list
    }

    fun removeItem(cartItem: Cart){
        list.remove(cartItem)
    }

    companion object {
        private lateinit var instance: CartList

        fun getInstance(): CartList {
            if (!::instance.isInitialized) {
                instance = CartList()
            }
            return instance
        }
    }

}