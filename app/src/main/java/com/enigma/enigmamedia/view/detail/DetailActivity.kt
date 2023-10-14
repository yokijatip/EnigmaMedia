package com.enigma.enigmamedia.view.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.enigma.enigmamedia.R
import com.enigma.enigmamedia.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {

    private lateinit var detailBinding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        detailBinding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(detailBinding.root)



    }
}