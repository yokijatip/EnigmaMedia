package com.enigma.enigmamedia.view.maps

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.enigma.enigmamedia.R
import com.enigma.enigmamedia.databinding.ActivityUserLocationBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class UserLocation : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityUserLocationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityUserLocationBinding.inflate(layoutInflater)
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

        val dLat = intent.getDoubleExtra("LATITUDE", 0.0)
        val dLon = intent.getDoubleExtra("LONGITUDE", 0.0)


        // Add a marker in Sydney and move the camera
        val userLocation = LatLng(dLat, dLon)

        mMap.addMarker(

            MarkerOptions()
                .position(userLocation)
                .title("Nama")
                .snippet("Deskripsi")

        )

        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(userLocation, 15f))
    }
}