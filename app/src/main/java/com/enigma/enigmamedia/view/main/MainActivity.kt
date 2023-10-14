package com.enigma.enigmamedia.view.main

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.enigma.enigmamedia.databinding.ActivityMainBinding
import com.enigma.enigmamedia.view.landing.LandingScreenActivity

class MainActivity : AppCompatActivity() {

    private lateinit var mainBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)

        mainBinding.apply {

//            Menghapus Data Sesi dan Token Ketika Button Logout ditekan
            btnLogout.setOnClickListener {
                val sharedPreferences = getSharedPreferences("Session", Context.MODE_PRIVATE)
                val manager = sharedPreferences.edit()
                manager.remove("userId")
                manager.remove("token")
                manager.apply()
                startActivity(Intent(this@MainActivity, LandingScreenActivity::class.java))
                finish()
            }

        }


    }

    //    Open Function untuk Toast Meesage
    fun toast(pesan: String) {
        Toast.makeText(this, pesan, Toast.LENGTH_SHORT).show()
    }

}