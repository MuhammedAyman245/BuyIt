package com.example.moon.screens.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moon.MainActivity
import com.example.moon.R
import com.example.moon.adapter.CheckoutAdapter
import com.example.moon.services.CartList
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class CheckoutActivity : AppCompatActivity() {

    private lateinit var userAddress:EditText
    private lateinit var userName:EditText
    private lateinit var userNumber:EditText
    private lateinit var order: Button
    private lateinit var backBtn: TextView
    private lateinit var total: TextView
    private lateinit var recyclerView: RecyclerView
    private lateinit var checkoutAdapter: CheckoutAdapter
    private lateinit var databaseReference: DatabaseReference
    private lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_checkout)

        userAddress = findViewById(R.id.address)
        userName = findViewById(R.id.name)
        userNumber = findViewById(R.id.number)
        order = findViewById(R.id.placeOrder)
        backBtn = findViewById(R.id.cancel)
        recyclerView = findViewById(R.id.recyclerView)
        total = findViewById(R.id.totalPrice)

        auth = FirebaseAuth.getInstance()


        val price = intent.getStringExtra("totalPrice")

        total.text = "Total Price is $price $"

        recyclerView.layoutManager = LinearLayoutManager(applicationContext, RecyclerView.VERTICAL,false)
        checkoutAdapter = CheckoutAdapter(CartList.getInstance().getItems())
        recyclerView.adapter = checkoutAdapter

        backBtn.setOnClickListener{
            val intent = Intent(applicationContext,MainActivity::class.java)
            startActivity(intent)
        }

        order.setOnClickListener{
            databaseReference= FirebaseDatabase.getInstance().getReference("Orders").child(auth.currentUser?.uid!!)
            var hashMap: HashMap<String,Any> = HashMap()

            hashMap["userName"] = userName.text.trim().toString()
            hashMap["userAddress"] = userAddress.text.trim().toString()
            hashMap["userNumber"] = userNumber.text.trim().toString()
            hashMap["totalPrice"] = price.toString()
            hashMap["orderDetails"] = CartList.getInstance().getItems()
            databaseReference.setValue(hashMap).addOnCompleteListener(this){ it ->
                if(it.isSuccessful){
                    Toast.makeText(applicationContext, "added successfully", Toast.LENGTH_SHORT).show()
                    CartList.getInstance().clearList()
                    val intent = Intent(applicationContext, MainActivity::class.java)
                    startActivity(intent)
                }
                else{
                    Toast.makeText(applicationContext, it.exception?.message.toString(), Toast.LENGTH_SHORT).show()
                }
            }
        }


    }
}