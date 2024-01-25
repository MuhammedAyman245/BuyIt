package com.example.moon.screens.registration

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.example.moon.MainActivity
import com.example.moon.R
import com.google.firebase.auth.FirebaseAuth

class LogInActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var email: EditText
    private lateinit var pass: EditText
    private lateinit var signUpBtn: Button
    private lateinit var signInBtn:Button
    private lateinit var progressBar:ProgressBar

    private fun signIn(){
        val intent = Intent(this@LogInActivity, MainActivity::class.java)
        auth.signInWithEmailAndPassword(email.text.toString().trim(),pass.text.toString().trim()).addOnCompleteListener{
            if(it.isSuccessful){
                email.setText("")
                pass.setText("")
                Toast.makeText(applicationContext, "LogIn Successfully", Toast.LENGTH_SHORT).show()
                startActivity(intent)
            }
            else{
                Toast.makeText(applicationContext, it.exception?.message.toString(), Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun signUp(){
        val intent = Intent(this@LogInActivity, SignUpActivity::class.java)
        startActivity(intent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log_in)
        auth = FirebaseAuth.getInstance()
        email = findViewById(R.id.EmailEt)
        pass = findViewById(R.id.PasswordEt)
        signInBtn = findViewById(R.id.btnSignIn)
        signUpBtn = findViewById(R.id.SignUpBtn)
        progressBar = findViewById(R.id.progressBar)

        if (auth.currentUser != null) {
            val intent = Intent(this@LogInActivity, MainActivity::class.java)
            startActivity(intent)
        } else {
            signInBtn.setOnClickListener {
                progressBar.isVisible = true
                if (email.text.toString().isNotEmpty() && pass.text.toString().isNotEmpty()) {
                    signIn()
                    val intent = Intent(this@LogInActivity, MainActivity::class.java)
                    startActivity(intent)
                } else {
                    progressBar.isVisible = false
                    Toast.makeText(
                        applicationContext,
                        "You have to enter you email and password",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
            signUpBtn.setOnClickListener {
                signUp()
            }
        }
    }
}

