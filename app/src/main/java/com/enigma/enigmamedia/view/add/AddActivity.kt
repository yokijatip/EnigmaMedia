package com.enigma.enigmamedia.view.add

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.enigma.enigmamedia.R
import com.enigma.enigmamedia.databinding.ActivityAddBinding

class AddActivity : AppCompatActivity() {

    private lateinit var addBinding: ActivityAddBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addBinding = ActivityAddBinding.inflate(layoutInflater)
        setContentView(addBinding.root)



    }
}