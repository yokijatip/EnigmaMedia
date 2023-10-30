package com.enigma.enigmamedia.view.main

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.enigma.enigmamedia.adapter.MainAdapterMVVM
import com.enigma.enigmamedia.data.remote.response.ListStoryItem
import com.enigma.enigmamedia.databinding.ActivityMainBinding
import com.enigma.enigmamedia.utils.TokenPreferences
import com.enigma.enigmamedia.view.add.AddActivity
import com.enigma.enigmamedia.view.detail.DetailActivity
import com.enigma.enigmamedia.view.landing.LandingScreenActivity
import com.enigma.enigmamedia.view.maps.MapsActivity
import com.enigma.enigmamedia.view.maps.UniversityMap
import com.enigma.enigmamedia.viewmodel.main.MainViewModelMVVM
import com.enigma.enigmamedia.viewmodel.main.ViewModelFactory
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {

    private lateinit var mainBinding: ActivityMainBinding
    private val mainViewModel: MainViewModelMVVM by viewModels { ViewModelFactory(this) }
    private val mainAdapterMVVM = MainAdapterMVVM()
    private val tokenPreferences by lazy { TokenPreferences(this) }

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)

        mainBinding.rvMain.layoutManager = LinearLayoutManager(this)

        showLoading(true)

        getDataPaging()

        tokenPreferences.getToken().onEach { token ->
            Log.d("TokenDebug", "Token: $token")
        }.launchIn(lifecycleScope)

        mainViewModel.storyListLiveData.observe(this) { stories ->
            Log.d("TokenDebug", "Total cerita diterima: ${stories.size}")
            mainAdapterMVVM.setList(stories)
            val firstStory = stories.firstOrNull()
            if (firstStory != null) {
                val name = firstStory.name
                mainBinding.textView2.text = name
            }
        }

        mainBinding.apply {
            btnLogout.setOnClickListener {
                lifecycleScope.launch {
                    tokenPreferences.clearToken()
                    startActivity(Intent(this@MainActivity, LandingScreenActivity::class.java))
                    finish()
                }
            }

            buttonGoogleMap.setOnClickListener {
                startActivity(Intent(this@MainActivity, MapsActivity::class.java))
            }

            bgMap.setOnClickListener {
                startActivity(Intent(this@MainActivity, UniversityMap::class.java))
            }
        }

        floatingActionButtonAdd()

        mainAdapterMVVM.setOnItemClickCallback(object : MainAdapterMVVM.OnItemClickCallback {
            override fun onItemClicked(storyItem: ListStoryItem) {
                navigateToStoryDetail(storyItem)
            }
        })
    }

    private fun floatingActionButtonAdd() {
        mainBinding.apply {
            fabAdd.setOnClickListener {
                startActivity(Intent(this@MainActivity, AddActivity::class.java))
            }
        }
    }

    private suspend fun getToken(): String {
        return tokenPreferences.getToken().first()
    }

    private fun navigateToStoryDetail(storyItem: ListStoryItem) {
        val intent = Intent(this@MainActivity, DetailActivity::class.java).apply {
            putExtra(DetailActivity.EXTRA_ID, storyItem.id)
        }
        startActivity(intent)
    }

    private fun showLoading(state: Boolean) {
        mainBinding.loadingMain.visibility = if (state) View.VISIBLE else View.GONE
    }

    private fun getDataPaging() {
        val adapter = MainAdapterMVVM()
        mainBinding.rvMain.adapter = adapter
        lifecycleScope.launch {
            val token = getToken()
            Log.e("TokenDebug2", "Token: $token")
            mainViewModel.getStoryPagingFromViewModel(token).observe(this@MainActivity) {
                adapter.submitData(lifecycle, it)
                showLoading(false)
                Log.d("TokenDebug", "Data dimuat ke dalam RecyclerView: $it")
            }
        }
    }
}