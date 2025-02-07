package com.example.mapasclase_2425

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.example.mapasclase_2425.databinding.ActivityMapsBinding
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import java.net.URL

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

        // Add a marker in Sydney and move the camera
        val sydney = LatLng(-34.0, 151.0)
        val julianMarias = LatLng(41.63195004853906, -4.758719054533834)
        mMap.addMarker(MarkerOptions().position(julianMarias).title("Marker in Institudo Julian Marias"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(julianMarias))
        mMap.mapType = GoogleMap.MAP_TYPE_HYBRID

        //Método que muestra la latitud y la longitud
        googleMap.setOnMapClickListener { latLng ->
            // Mostrar un Toast con la latitud y la longitud
            Toast.makeText(
                this,
                "Latitud: ${latLng.latitude}, Longitud: ${latLng.longitude}",
                Toast.LENGTH_LONG
            ).show()
            //Establezco un marcador con el evento
            googleMap.addMarker((MarkerOptions()
                .position(latLng)
                .title("Marcador en destino")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)))
                .draggable(true))
        }

        googleMap.setOnMapLongClickListener { latLng ->
            googleMap.addMarker((MarkerOptions()
                .position(latLng)
                .title("Marcador largo")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)))
                .snippet(("Teléfono: 983989784")))
            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng))
        }

        googleMap.setOnMarkerClickListener{ marker ->
            if(marker.title == "Marker in Institudo Julian Marias") {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse("http://iesjulianmarias.centros.educa.jcyl.es/sitio/"))
                startActivity(intent)
            }
            true
        }
    }


}