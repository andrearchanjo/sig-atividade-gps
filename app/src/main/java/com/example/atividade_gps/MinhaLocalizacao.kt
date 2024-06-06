package com.example.atividade_gps

import android.location.Location
import android.location.LocationListener
import android.os.Bundle
import android.util.Log

class MinhaLocalizacao : LocationListener {
    companion object {
        var latitude: Double = 0.0
        var longitude: Double = 0.0
    }

    override fun onLocationChanged(location: Location) {
        latitude = location.latitude
        longitude = location.longitude

        Log.d("LOCALIZACAO_LISTENER", latitude.toString())
        Log.d("LOCALIZACAO_LISTENER", longitude.toString())
    }

    override fun onProviderEnabled(provider: String) {
        super.onProviderEnabled(provider)
    }

    override fun onProviderDisabled(provider: String) {
        super.onProviderDisabled(provider)
    }

    override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
        super.onStatusChanged(provider, status, extras)
    }
}