package com.enigma.enigmamedia.view.main

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.enigma.enigmamedia.R
import com.enigma.enigmamedia.adapter.MainAdapter
import com.enigma.enigmamedia.databinding.ActivityMainBinding
import com.enigma.enigmamedia.utils.TokenPreferences
import com.enigma.enigmamedia.view.add.AddActivity
import com.enigma.enigmamedia.view.detail.DetailActivity
import com.enigma.enigmamedia.view.landing.LandingScreenActivity
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var mainBinding: ActivityMainBinding
    private val mainViewModel: MainViewModel by viewModels()
    private val mainAdapter = MainAdapter()

    private val tokenPreferences by lazy { TokenPreferences(this) }

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)

        tokenPreferences.getToken().onEach { token ->
            Log.d("TokenDebug", "Token: $token")
        }.launchIn(lifecycleScope)

//        Inisiasi Adapter NotifyDataChanged
        mainAdapter.notifyDataSetChanged()

//        Recycler View
        val recyclerView = findViewById<RecyclerView>(R.id.rv_main)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = mainAdapter

//        Observer Live data dari ViewModel
        mainViewModel.getStoryListLiveData()
            .observe(this, { stories -> mainAdapter.setList(stories) })

//        Fetch Story

        lifecycleScope.launch {
            getToken()
        }


        mainBinding.apply {

//            Menghapus Data Sesi dan Token Ketika Button Logout ditekan
            btnLogout.setOnClickListener {

                lifecycleScope.launch {

                    tokenPreferences.clearToken()

                    startActivity(Intent(this@MainActivity, LandingScreenActivity::class.java))
                    finish()

                }
            }

        }


//        Floating Action Button Add
        floatingActionButtonAdd()


    }

    //    Open Function untuk Toast Meesage
    private fun toast(pesan: String) {
        Toast.makeText(this, pesan, Toast.LENGTH_SHORT).show()
    }

    fun floatingActionButtonAdd() {
        mainBinding.apply {
            fabAdd.setOnClickListener {
                toast("Oke Sudah Masuk Ke Add")
                startActivity(Intent(this@MainActivity, AddActivity::class.java))
            }
        }
    }

    suspend fun getToken() {
        val token = tokenPreferences.getToken().first()
        mainViewModel.getAllStory(token)
    }


    //    Untuk mengarahkan ke Detail Story
    private fun navigateToStoryDetail() {
        startActivity(Intent(this@MainActivity, DetailActivity::class.java))
    }

    //    Fungsi Show Loading
    private fun showLoading(state: Boolean) {
        mainBinding.loadingMain.visibility = if (state) View.VISIBLE else View.GONE
    }

}