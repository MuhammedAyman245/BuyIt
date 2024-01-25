package com.example.moon.screens.registration

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.core.view.isVisible
import com.example.moon.MainActivity
import com.example.moon.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class SignUpActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var databaseRef:DatabaseReference
    private lateinit var registerBtn: Button
    private lateinit var logInBtn: Button
    private lateinit var userName: EditText
    private lateinit var userEmail:EditText
    private lateinit var userPass:EditText
    private lateinit var userAddress:EditText
    private lateinit var userConfirmPass:EditText
    private lateinit var userNumber:EditText
    private lateinit var progressBar: ProgressBar


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        auth = FirebaseAuth.getInstance()
        logInBtn = findViewById(R.id.btnSignIn)
        registerBtn = findViewById(R.id.SignUpBtn)
        userName = findViewById(R.id.NameEt)
        userEmail = findViewById(R.id.EmailEt)
        userPass = findViewById(R.id.PasswordEt)
        userConfirmPass = findViewById(R.id.ConfirmPassEt)
        userNumber = findViewById(R.id.PhoneEt)
        userAddress = findViewById(R.id.addressEt)
        progressBar = findViewById(R.id.progressBar)


        registerBtn.setOnClickListener{
            if(userPass.text.toString()!= userConfirmPass.text.toString()){
                Toast.makeText(applicationContext, "Password doesn't match", Toast.LENGTH_SHORT).show()
            }else {
                if (userName.text.isNotEmpty() && userEmail.text.isNotEmpty() && userPass.text.isNotEmpty()) {
                    progressBar.isVisible = true

                    register(
                        userName.text.toString().trim(),
                        userEmail.text.toString().trim(),
                        userPass.text.toString().trim()
                    )
                }
            }
        }
        logInBtn.setOnClickListener{
            logIn()
        }
    }
    private fun register(userName:String,email:String, pass:String){
        auth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener{
            if(it.isSuccessful){
                var user: FirebaseUser? = auth.currentUser
                var userId:String = user!!.uid

                databaseRef= FirebaseDatabase.getInstance().getReference("Users").child(userId)

                var hashMap: HashMap<String,String> = HashMap()

                hashMap["userId"] = userId
                hashMap["userName"] = userName
                hashMap["userEmail"] = userEmail.text.toString()
                hashMap["userNumber"] = userNumber.text.toString()
                hashMap["userAddress"] = userAddress.text.toString()

                databaseRef.setValue(hashMap).addOnCompleteListener(this){ it ->
                    if(it.isSuccessful){

                        val intent = Intent(this@SignUpActivity, MainActivity::class.java)

                        startActivity(intent)
                    }
                    else{
                        Toast.makeText(applicationContext, it.exception?.message.toString(), Toast.LENGTH_SHORT).show()
                    }
                }
            } else{
                Toast.makeText(applicationContext, it.exception?.message.toString(),Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun logIn(){
        val intent = Intent(this@SignUpActivity, LogInActivity::class.java)
        startActivity(intent)
    }
}