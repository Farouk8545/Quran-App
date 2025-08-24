package com.example.quranapp.location

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import androidx.compose.runtime.mutableStateOf
import androidx.core.content.ContextCompat
import com.google.android.gms.location.LocationServices
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.tan

class QiblaDirectionHandler(private val context: Context){

    var direction = mutableStateOf<Double?>(null)
        private set

    init {
        getLastLocation()
    }

    @SuppressLint("MissingPermission") // tells lint you are checking manually
    fun getLastLocation(){
        val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)

        if (ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            fusedLocationClient.lastLocation
                .addOnSuccessListener { location ->
                    calculateQiblaDirection(location)
                }
        }
    }

    fun calculateQiblaDirection(location: Location) {
        val qiblaLatitude = Math.toRadians(21.4225) // in radians
        val qiblaLongitude = Math.toRadians(39.8262)
        val userLatitude = Math.toRadians(location.latitude)
        val userLongitude = Math.toRadians(location.longitude)
        val differenceLongitude = qiblaLongitude - userLongitude

        val cita = atan2(sin(differenceLongitude), cos(userLatitude) * tan(qiblaLatitude) - sin(userLatitude) * cos(differenceLongitude))
        direction.value = (Math.toDegrees(cita) + 360) % 360
    }

}