package com.twq.workmanagerhomework

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.twq.workmanagerhomework.databinding.ActivityMapsBinding

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Add a marker in Sydney and move the camera
        val khobar = LatLng(26.395, 50.1957)
        mMap.addMarker(MarkerOptions().position(khobar).title("Marker in Al Khobar"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(khobar))
        mMap.animateCamera(CameraUpdateFactory.zoomTo(13f))
        mMap.uiSettings.isZoomControlsEnabled = true

        mMap.setOnMarkerClickListener {
            AlertDialog.Builder(this).apply {
                title = it.title
                setMessage("you selected ${it.title}")
            }.show()

            true
        }
    }
}