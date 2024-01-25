package com.example.moon.screens.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moon.adapter.CartAdapter
import com.example.moon.databinding.FragmentCartBinding
import com.example.moon.screens.activities.CheckoutActivity
import com.example.moon.services.CartList


class CartFragment : Fragment() {

    private lateinit var binding:FragmentCartBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var cartAdapter: CartAdapter
    private lateinit var order:CardView
    private lateinit var emptyImage: ImageView
    private lateinit var emptyText: TextView
    private lateinit var items: TextView
    private lateinit var total: TextView
    private var itemsCount:Int = 0
    private var totalPrice:Double = 0.0


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCartBinding.inflate(inflater,container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = binding.cartRecyclerView
        emptyImage = binding.emptyCart
        emptyText = binding.emptyTextView
        total = binding.totalPrice
        items = binding.numOfItems
        recyclerView = binding.cartRecyclerView
        order = binding.orderButton


        if(CartList.getInstance().getItems().isNotEmpty()){
            emptyImage.isVisible = false
            emptyText.isVisible = false
            recyclerView.isVisible = true
            order.isVisible = true
            recyclerView.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL,false)
            cartAdapter = CartAdapter(CartList.getInstance().getItems())
            recyclerView.adapter = cartAdapter

            for(q in CartList.getInstance().getItems()){
                itemsCount += q.quantity
                totalPrice += (q.quantity * q.productPrice.toInt())
            }
            items.text = "$itemsCount items"
            total.text = "$ $totalPrice"


            order.setOnClickListener{
                val intent = Intent(context,CheckoutActivity::class.java)
                intent.putExtra("totalPrice","$totalPrice")
                startActivity(intent)
            }
        }else{
            emptyImage.isVisible = true
            emptyText.isVisible = true
            recyclerView.isVisible = false
            order.isVisible = false
        }


    }

}