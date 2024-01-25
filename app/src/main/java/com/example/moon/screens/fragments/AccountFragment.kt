package com.example.moon.screens.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.isVisible
import com.example.moon.MainActivity
import com.example.moon.databinding.FragmentAccountBinding
import com.example.moon.screens.activities.FavouriteActivity
import com.example.moon.screens.activities.RecentlyViewedActivity
import com.example.moon.screens.registration.LogInActivity
import com.example.moon.services.CartList
import com.example.moon.services.FavouriteProductList
import com.example.moon.services.SharedPreferenceManager
import com.example.moon.services.ViewedList
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class AccountFragment : Fragment() {
    private lateinit var binding: FragmentAccountBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var recent: LinearLayout
    private lateinit var favourites: LinearLayout
    private lateinit var contactUs: LinearLayout
    private lateinit var logOut: LinearLayout
    private lateinit var register: Button
    private lateinit var databaseReference: DatabaseReference
    private lateinit var themeChanger :LinearLayout
    private  val themeTitleList = arrayOf("Light","Dark","Auto (System Default)")
    private lateinit var facebookButton: ImageView
    private lateinit var linkedinButton: ImageView
    private lateinit var instagramButton: ImageView
    private lateinit var userName: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAccountBinding.inflate(inflater,container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recent = binding.viewedRecently
        favourites = binding.wishList
        contactUs = binding.contactUs
        register = binding.registerButton
        logOut = binding.logOut
        userName = binding.userName
        themeChanger = binding.theme
        facebookButton = binding.faceBookIcon
        linkedinButton = binding.LinkedInIcon
        instagramButton = binding.instagramIcon

        val sharedPreferenceManager = SharedPreferenceManager(requireContext())
        var checkedTheme = sharedPreferenceManager.theme
        val themeDialog = MaterialAlertDialogBuilder(requireContext())
            .setTitle("Theme")
            .setPositiveButton("Ok"){_,_->
                sharedPreferenceManager.theme = checkedTheme
                AppCompatDelegate.setDefaultNightMode(sharedPreferenceManager.themeFlag[checkedTheme])
                Toast.makeText(context, "Theme changed to ${themeTitleList[sharedPreferenceManager.theme]}", Toast.LENGTH_SHORT).show()
            }
            .setSingleChoiceItems(themeTitleList,checkedTheme){_,which -> checkedTheme = which}
            .setCancelable(false)

        themeChanger.setOnClickListener{
            themeDialog.show()
        }

        auth = FirebaseAuth.getInstance()
        databaseReference = FirebaseDatabase.getInstance().reference
        val uId = auth.currentUser?.uid
        databaseReference = FirebaseDatabase.getInstance().getReference("Users")

        if(auth.currentUser!=null){
            logOut.isVisible = true
            register.isVisible = false
            databaseReference.child(uId!!).get().addOnSuccessListener {
                if(it.exists()){
                    val uName = it.child("userName").value.toString()
                    userName.text = " $uName"
                    logOut.setOnClickListener{
                        val intent = Intent(context, MainActivity::class.java)
                        auth.signOut()

                        CartList.getInstance().clearList()
                        FavouriteProductList.getInstance().clearList()
                        ViewedList.getInstance().clearList()

                        startActivity(intent)
                    }
                }else{
                    Toast.makeText(context, "user doesn't exist", Toast.LENGTH_SHORT).show()
                }
            }.addOnFailureListener{}
        }




        favourites.setOnClickListener {
            val intent = Intent(context, FavouriteActivity::class.java)
            startActivity(intent)
        }

        register.setOnClickListener{
            val intent = Intent(context, LogInActivity::class.java)
            startActivity(intent)
        }

        contactUs.setOnClickListener{
            val phoneNumber = "01069176934" // Replace with your actual phone number
            val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$phoneNumber"))
            startActivity(intent)
        }

        recent.setOnClickListener{
            val intent = Intent(context, RecentlyViewedActivity::class.java)
            startActivity(intent)
        }

        facebookButton.setOnClickListener{
            val websiteUrl = "https://www.facebook.com/mohamed.ayman.1675275/" // Replace with your actual website URL
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(websiteUrl))
            startActivity(intent)
        }

        linkedinButton.setOnClickListener{
            val websiteUrl = "https://www.linkedin.com/in/mohamed-ayman-b20794210/" // Replace with your actual website URL
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(websiteUrl))
            startActivity(intent)
        }

        instagramButton.setOnClickListener{
            val websiteUrl = "https://www.instagram.com/mohamed.ayman279/?hl=en" // Replace with your actual website URL
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(websiteUrl))
            startActivity(intent)
        }



    }
}



