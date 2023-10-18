package com.enigma.enigmamedia.view.landing

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import androidx.lifecycle.lifecycleScope
import com.enigma.enigmamedia.databinding.ActivitySplashScreenBinding
import com.enigma.enigmamedia.utils.TokenPreferences
import com.enigma.enigmamedia.view.main.MainActivity
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@Suppress("DEPRECATION")
class SplashScreenActivity : AppCompatActivity() {

    private lateinit var splashScreenBinding: ActivitySplashScreenBinding

    private val tokenPreferences by lazy { TokenPreferences(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        splashScreenBinding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(splashScreenBinding.root)




        splashScreenBinding.apply {
            tvSplash.animate().alpha(1f).setDuration(2000).start()
            ivSplash.animate().alpha(1f).setDuration(2000).start()
            Handler().postDelayed({


//                Check Data Sesi dan Token


                lifecycleScope.launch {
                    val token = tokenPreferences.getToken().first()
                    if (token.isNotEmpty()) {
                        startActivity(Intent(this@SplashScreenActivity, MainActivity::class.java))
                    } else {
                        startActivity(
                            Intent(
                                this@SplashScreenActivity,
                                LandingScreenActivity::class.java
                            )
                        )
                    }
                    finish()
                }


            }, 1000)
        }

    }
}