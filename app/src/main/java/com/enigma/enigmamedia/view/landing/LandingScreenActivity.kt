package com.enigma.enigmamedia.view.landing

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.enigma.enigmamedia.R
import com.enigma.enigmamedia.databinding.ActivityLandingScreenBinding
import com.enigma.enigmamedia.view.login.LoginActivity
import com.enigma.enigmamedia.view.register.RegisterActivity

class LandingScreenActivity : AppCompatActivity() {

    private lateinit var landingScreenBinding: ActivityLandingScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        landingScreenBinding = ActivityLandingScreenBinding.inflate(layoutInflater)
        setContentView(landingScreenBinding.root)

        landingScreenBinding.apply {
            btnLogin.setOnClickListener {
                startActivity(Intent(this@LandingScreenActivity, LoginActivity::class.java))
            }

            btnRegister.setOnClickListener {
                startActivity(Intent(this@LandingScreenActivity, RegisterActivity::class.java))
            }

        }

    }
}