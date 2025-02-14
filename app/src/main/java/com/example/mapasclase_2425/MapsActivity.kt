package com.example.mapasclase_2425

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Spinner
import android.widget.Toast
import com.example.mapasclase_2425.databinding.ActivityMapsBinding

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.BitmapDescriptorFactory

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private val imagenes = arrayOf(
        R.drawable.julian_marias_logo,
        R.drawable.virgen_espino_logo,
        R.drawable.claudio_moyano_logo,
        R.drawable.ic_placeholder
    )
    private val institutosLatitud = arrayOf(
        123.2,
        40.0,
        -40.0
    )
    private val institutosLongitud = arrayOf(
        -4.758719054533834,
        30.0,
        -30.0
    )
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
        val spinner = findViewById<Spinner>(R.id.spinner2)
        val verViajes = findViewById<Button>(R.id.buttonVerViajes)
        val añadirViaje = findViewById<Button>(R.id.buttonGuardar)
        verViajes.setOnClickListener(){
            val intentVerViajes = Intent(this,ThirdActivity::class.java)
            startActivity(intentVerViajes)
        }
        añadirViaje.setOnClickListener(){
            Toast.makeText(this,"Viaje añadido",Toast.LENGTH_SHORT).show()
        }

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


        val longitud = institutosLongitud[intent.getIntExtra("position",0)]
        val latitud = institutosLatitud[intent.getIntExtra("position",0)]
        val sydney = LatLng(longitud, latitud)
        mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Institudo Julian Marias"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
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