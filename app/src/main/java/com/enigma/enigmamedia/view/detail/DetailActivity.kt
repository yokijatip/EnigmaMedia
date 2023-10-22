package com.enigma.enigmamedia.view.detail

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.enigma.enigmamedia.databinding.ActivityDetailBinding
import com.enigma.enigmamedia.utils.TokenPreferences
import com.enigma.enigmamedia.view.main.MainActivity
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class DetailActivity : AppCompatActivity() {

    //    nangkep ID dari Main Activity
    companion object {
        const val EXTRA_ID = "extra_id"
    }

    private lateinit var detailBinding: ActivityDetailBinding

    private val detailViewModel: DetailViewModel by viewModels()

    private val tokenPreferences by lazy { TokenPreferences(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        detailBinding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(detailBinding.root)

        showLoading(true)

//        Back Button
        detailBinding.icBack.setOnClickListener {

            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
            finish()
            
        }


        lifecycleScope.launch {
            getToken()
        }

        detailViewModel.getStoryDetailLiveData().observe(this) { storyDetail ->

            if (storyDetail != null) {

                showLoading(false)

                detailBinding.apply {

                    Glide.with(this@DetailActivity)
                        .load(storyDetail.photoUrl)
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .centerCrop()
                        .into(ivPhoto)

                    tvName.text = storyDetail.name
                    tvDate.text = storyDetail.createdAt
                    tvDescription.text = storyDetail.description
                }

            }
        }


    }

    private suspend fun getToken() {
        val token = tokenPreferences.getToken().first()
        val id = intent.getStringExtra("extra_id")
        if (id != null) {
            detailViewModel.getStoryDetail(token, id)
        }
    }

    private fun showLoading(state: Boolean) {
        detailBinding.loadingDetail.visibility = if (state) View.VISIBLE else View.GONE
    }

}