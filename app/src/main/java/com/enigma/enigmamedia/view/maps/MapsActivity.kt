package com.enigma.enigmamedia.view.maps

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.enigma.enigmamedia.R
import com.enigma.enigmamedia.databinding.ActivityMapsBinding
import com.enigma.enigmamedia.utils.TokenPreferences
import com.enigma.enigmamedia.viewmodel.maps.MapsViewModelMVVM
import com.enigma.enigmamedia.viewmodel.maps.ViewModelFactory
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding
    private val tokenPreferences by lazy { TokenPreferences(this) }
    private val mapsViewModel: MapsViewModelMVVM by viewModels { ViewModelFactory(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        lifecycleScope.launch {
            val token = getToken()
            mapsViewModel.getStoryLocation(token)
        }

    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        userMarker()
    }
    private suspend fun getToken(): String {
        return tokenPreferences.getToken().first()
    }
    private fun userMarker() {
        val indonesia = LatLng(-5.9305961, 108.0058252)

        mapsViewModel.storyListLiveData.observe(this) { stories ->
            mMap.clear()
            stories?.forEach { story ->
                val latLng = LatLng(story.lat as Double, story.lon as Double)
                mMap.addMarker(
                    MarkerOptions()
                        .position(latLng)
                        .title(story.name)
                        .snippet(story.description)
                )
            }
        }
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(indonesia, 4f))
    }
}