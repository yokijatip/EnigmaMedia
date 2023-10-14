package com.enigma.enigmamedia.view.landing

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.enigma.enigmamedia.databinding.ActivitySplashScreenBinding
import com.enigma.enigmamedia.view.main.MainActivity

class SplashScreenActivity : AppCompatActivity() {

    private lateinit var splashScreenBinding: ActivitySplashScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        splashScreenBinding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(splashScreenBinding.root)




        splashScreenBinding.apply {
            tvSplash.animate().alpha(1f).setDuration(2000).start()
            ivSplash.animate().alpha(1f).setDuration(2000).start()
            Handler().postDelayed({

                val sharedPreferences = getSharedPreferences("Session", Context.MODE_PRIVATE)

//                Check Data Sesi dan Token
                val userId = sharedPreferences.getString("userId", null)
                val token = sharedPreferences.getString("token", null)

                if (userId != null && token != null) {
                    startActivity(Intent(this@SplashScreenActivity, MainActivity::class.java))
                    finish()
                } else {
                    startActivity(Intent(this@SplashScreenActivity, LandingScreenActivity::class.java))
                    finish()
                }



            }, 2000)
        }

    }
}