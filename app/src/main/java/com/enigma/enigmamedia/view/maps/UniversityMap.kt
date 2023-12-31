package com.enigma.enigmamedia.view.maps

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.enigma.enigmamedia.R
import com.enigma.enigmamedia.databinding.ActivityUniversityMapBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class UniversityMap : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityUniversityMapBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityUniversityMapBinding.inflate(layoutInflater)
        setContentView(binding.root)

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

        mMap.uiSettings.isZoomControlsEnabled = true
        mMap.uiSettings.isIndoorLevelPickerEnabled = true
        mMap.uiSettings.isCompassEnabled = true
        mMap.uiSettings.isMapToolbarEnabled = true

        // Add a marker in Sydney and move the camera
        val university = LatLng(-6.9465742, 107.593596)

        mMap.addMarker(

            MarkerOptions()
                .position(university)
                .title("STMIK Mardira Indonesia \\n Kampus Saya")
                .snippet("Best University In Town")

        )

        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(university, 15f))
    }
}