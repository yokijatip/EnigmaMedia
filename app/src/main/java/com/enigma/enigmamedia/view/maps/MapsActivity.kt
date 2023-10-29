package com.enigma.enigmamedia.view.maps

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.enigma.enigmamedia.R
import com.enigma.enigmamedia.databinding.ActivityMapsBinding
import com.enigma.enigmamedia.utils.TokenPreferences
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        lifecycleScope.launch {
            val token = getToken()
        }

    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        addManyMarker()
    }

    private suspend fun getToken(): String {
        return tokenPreferences.getToken().first()
    }

    data class UserLocation(
        val name: String,
        val lat: Double,
        val lon: Double
    )

    private fun addManyMarker() {
        val userLocation = listOf(
            UserLocation("Yoki Jati Perkasa", -6.835903, 107.366366),
            UserLocation("Bonge dan Kurma", -6.5607131, 106.8989146)
//            Di isi dengan beberapa lat dan long dari user tapi nanti
        )
        userLocation.forEach { location ->

            val latLon = LatLng(location.lat, location.lon)
            val indonesia = LatLng(-5.9305961, 108.0058252)

            mMap.addMarker(
                MarkerOptions()
                    .position(latLon)
                    .title(location.name)
                    .snippet("Slebew")
            )
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(indonesia, 6f))
        }
    }

}