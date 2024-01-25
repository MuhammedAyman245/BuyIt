package com.example.moon.services

import com.example.moon.models.Product

class ViewedList {

    var list:MutableList<Product> = mutableListOf()

    fun addItem(viewedItem: Product) {
        if(!list.contains(viewedItem)) {
            list.add(viewedItem)
        }
    }

    fun clearList(){
        list.clear()
    }

    fun getItems() : List<Product>{
        return list
    }

    fun removeItem(viewedItem: Product){
        list.remove(viewedItem)
    }

    companion object {
        private lateinit var instance: ViewedList
        fun getInstance(): ViewedList {
            if (!::instance.isInitialized) {
                instance = ViewedList()
            }
            return instance
        }
    }
}