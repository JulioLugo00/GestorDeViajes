package mx.itesm.noobmasters.gestordeviajes

import android.Manifest
import android.content.Intent
import android.location.Address
import android.location.Geocoder
import android.net.Uri
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import com.google.android.gms.maps.SupportMapFragment
import java.io.IOException
import java.util.*
import com.google.android.gms.maps.model.BitmapDescriptorFactory


class MapActivity() : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnMapClickListener{
    private lateinit var map: GoogleMap
    private lateinit var searchImageView: ImageView
    private lateinit var inputLocation: EditText
    var isPermissionGarantee = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)
        searchImageView = findViewById(R.id.serachIcon)
        inputLocation = findViewById(R.id.inputLocation)

        checkPermissions()

        if(isPermissionGarantee){
            if(checkGooglePlayServices()){
                createFragment()
            }else{
                Toast.makeText(this@MapActivity, "Servicios de Google No Disponibles", LENGTH_SHORT).show()
            }
        }
        searchImageView.setOnClickListener {
            var search = inputLocation.text.toString()
            if(search == null){
                Toast.makeText(this,"Agrega alguna ubicación", LENGTH_SHORT).show()
            }else{
                var geocoder = Geocoder(this, Locale.getDefault())
                try {
                    var listAddress: MutableList<Address>? = geocoder.getFromLocationName(search, 1)
                    if (listAddress != null) {
                        if(listAddress.size > 0) {
                            var direction =
                                LatLng(listAddress.get(0).latitude, listAddress.get(0).longitude)
                            map.addMarker(
                                MarkerOptions()
                                    .position(direction)
                                    .title(search.uppercase().toString())
                                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE))
                            )

                            map.animateCamera(
                                CameraUpdateFactory.newLatLngZoom(direction, 6f),
                                2000,
                                null
                            )
                        }
                    }
                }catch (e: IOException){
                    e.printStackTrace()
                }

            }
        }

    }

    private fun createFragment() {
        val mapFragment = supportFragmentManager.findFragmentById(R.id.mapa) as SupportMapFragment
        mapFragment.getMapAsync(this)

    }

    private fun checkGooglePlayServices(): Boolean {
         var googleApiAvailability = GoogleApiAvailability.getInstance()
         var result = googleApiAvailability.isGooglePlayServicesAvailable(this)
        if(result==ConnectionResult.SUCCESS){
            return true
        }else if(googleApiAvailability.isUserResolvableError(result)){
            googleApiAvailability.getErrorDialog(this, result, 1000).show()
        }
            return false
    }

    private fun checkPermissions() {
        Dexter.withContext(this)
            .withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
            .withListener(object: PermissionListener{
                override fun onPermissionGranted(p0: PermissionGrantedResponse?) {
                    isPermissionGarantee = true;
                    Toast.makeText(this@MapActivity, "Permiso aceptado", LENGTH_SHORT).show()
                }

                override fun onPermissionDenied(p0: PermissionDeniedResponse?) {
                    val intent = Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                    intent.addCategory(Intent.CATEGORY_DEFAULT)
                    intent.setData(Uri.parse("package: " + packageName))
                    startActivity(intent)

                }

                override fun onPermissionRationaleShouldBeShown(
                    p0: PermissionRequest?,
                    p1: PermissionToken?
                ) {
                    p1?.continuePermissionRequest()
                }
            }).check()
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        createMarkers()
        map.uiSettings.isZoomControlsEnabled = true
        map.uiSettings.isMyLocationButtonEnabled = true

        map.setOnMapClickListener(this)
    }

    private fun createMarkers() {
        /*  Crear puntos */
        val puntos: Map<Array<Double>, String> =
            mapOf(
                Pair(arrayOf(19.6891100, -98.8473000), "Piramides de Teotihuacan"),
                Pair(arrayOf(19.422991,-99.1795911592673), "Museo de arte moderno"),
                Pair(arrayOf(19.4205000,-99.1815000), "Castillo de Chapultepec"),
                Pair(arrayOf(20.2152500,-87.4294300), "Ruinas de Tulum"),
                Pair(arrayOf(17.2532500,-92.1099300), "Cascadas de Agua Azul"),
                Pair(arrayOf(20.66682, -103.39182), "Centro de Guadalajara"),
                Pair(arrayOf(32.5027, -117.00371), "Centro de Tijuana"),
                Pair(arrayOf(29.1026, -110.97732), "Centro de Hermosillo")
            )

        val cCenter = LatLng(19.433242, -99.132999)
        val mCenter = MarkerOptions().position(cCenter).title("Centro de la CDMX")
        map.addMarker(mCenter)

        map.animateCamera(
            CameraUpdateFactory.newLatLngZoom(cCenter, 12f),
            2000,
            null
        )
        for ((coordenate, place) in puntos) {
            val cPunto = LatLng(coordenate[0], coordenate[1])
            val mPunto = MarkerOptions().position(cPunto).title(place)

            map.addMarker(mPunto)
        }
    }

    override fun onMapClick(p0: LatLng) {

        var reverseGeocoder = Geocoder(this, Locale.getDefault())
        try {
            var listAddress: MutableList<Address>? = reverseGeocoder.getFromLocation(p0.latitude, p0.longitude, 1)
            if (listAddress != null) {
                if(listAddress.size > 0) {
                    // listAddress.get(0).countryName = pais
                        // listAddress.get(0).thoroughfare Direccion
                            // getLocality() Localidad
                    val direction = listAddress.get(0).thoroughfare + " , CP: "+ listAddress.get(0).postalCode + " , "+ listAddress.get(0).locality + " , " + listAddress.get(0).countryName


                    val crearEventoIntent = Intent(this, CrearEventoActivity::class.java)
                    crearEventoIntent.putExtra("ubicacion_key", direction)
                    startActivity(crearEventoIntent)
                    //Destruye la pantalla, esta actividad (Slash Activity)
                    this.finish()

                }
            }
        }catch (e: IOException){
            e.printStackTrace()
        }
    }

}
