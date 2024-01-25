package com.example.moon.services

import com.example.moon.models.Product

class FavouriteProductList {
    var list:MutableList<Product> = mutableListOf()

    fun addItem(favItem: Product) {
        if(!list.contains(favItem)) {
            list.add(favItem)
        }
    }

    fun clearList(){
        list.clear()
    }

    fun getItems() : List<Product>{
        return list
    }

    fun removeItem(favItem: Product){
        list.remove(favItem)
    }

    companion object {
        private lateinit var instance: FavouriteProductList
        fun getInstance(): FavouriteProductList {
            if (!::instance.isInitialized) {
                instance = FavouriteProductList()
            }
            return instance
        }
    }
}