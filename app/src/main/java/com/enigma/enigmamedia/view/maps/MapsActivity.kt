package com.enigma.enigmamedia.view.maps

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.enigma.enigmamedia.R
import com.enigma.enigmamedia.databinding.ActivityMapsBinding
import com.enigma.enigmamedia.utils.TokenPreferences
import com.enigma.enigmamedia.viewmodel.maps.MapsActivityViewModelMVVM
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.coroutines.flow.first

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding
    private lateinit var mapsViewModelMVVM: MapsActivityViewModelMVVM
    private val tokenPreferences by lazy { TokenPreferences(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        mapsViewModelMVVM = ViewModelProvider(this)[MapsActivityViewModelMVVM::class.java]

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
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
            UserLocation("Yoki Jati Perkasa", -6.835903, 107.366366)
        )
        userLocation.forEach { location ->

            val latLon = LatLng(location.lat, location.lon)
            val indonesia = LatLng(-2.4032005, 107.1917352)

            mMap.addMarker(
                MarkerOptions()
                    .position(latLon)
                    .title(location.name)
                    .snippet("Slebew")
            )
            mMap.moveCamera(CameraUpdateFactory.newLatLng(indonesia))
        }
    }

}