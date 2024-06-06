package com.example.atividade_gps

import android.content.pm.PackageManager
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import android.Manifest
import android.util.Log
import android.webkit.WebView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun buscarInformacoesGPS(v: View?) {
        val mLocManager: LocationManager = getSystemService(LOCATION_SERVICE) as LocationManager
        val mLocListener: LocationListener = MinhaLocalizacao()

        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
            && ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_NETWORK_STATE
                ), 1)
            return
        }

        Log.d("LOCALIZACAO_TAG", "Solicitando atualizações de localização...")
        mLocManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0f, mLocListener)
        Log.d("LOCALIZACAO_TAG", MinhaLocalizacao.latitude.toString())
        Log.d("LOCALIZACAO_TAG", MinhaLocalizacao.longitude.toString())
        Log.d("LOCALIZACAO_TAG", "Finalizado atualizações de localização...")

        if (mLocManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            val texto = """
                Latitude: ${MinhaLocalizacao.latitude}
                Longitude: ${MinhaLocalizacao.longitude}
            """.trimIndent()

            Toast.makeText(this, texto, Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(this, "GPS DESABILITADO.", Toast.LENGTH_LONG).show()
        }

        this.mostrarGoogleMaps(MinhaLocalizacao.latitude, MinhaLocalizacao.longitude)
    }

    fun mostrarGoogleMaps(latitude: Double, longitude: Double) {
        val wv: WebView = findViewById(R.id.webv)
        wv.settings.javaScriptEnabled = true
        wv.loadUrl("https://www.google.com/maps/search/?api=1&query=$latitude,$longitude")
    }
}