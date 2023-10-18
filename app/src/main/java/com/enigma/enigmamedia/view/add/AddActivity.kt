package com.enigma.enigmamedia.view.add

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.enigma.enigmamedia.R
import com.enigma.enigmamedia.databinding.ActivityAddBinding
import com.enigma.enigmamedia.view.main.MainActivity

class AddActivity : AppCompatActivity() {

    private lateinit var addBinding: ActivityAddBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addBinding = ActivityAddBinding.inflate(layoutInflater)
        setContentView(addBinding.root)


        addBinding.apply {
            icBack.setOnClickListener {
                startActivity(Intent(this@AddActivity, MainActivity::class.java))
                finish()
            }
        }


    }
}