package com.example.atividade_gps

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import android.util.Log
import android.webkit.WebView
import android.widget.EditText
import com.google.android.gms.location.*

class MainActivity : AppCompatActivity() {

    private lateinit var p1: Ponto
    private lateinit var p2: Ponto
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION,
            ), 1)
        }
    }

    fun reset(v: View?) {
        p1 = Ponto()
        p2 = Ponto()
        var editPonto: EditText = findViewById<EditText>(R.id.editPtoA)
        editPonto.setText("")
        editPonto = findViewById<EditText>(R.id.editPtoB)
        editPonto.setText("")
    }

    fun verPontoA(v: View?) {
        mostrarGoogleMaps(p1.latitude, p1.longitude)
    }

    fun verPontoB(v: View?) {
        mostrarGoogleMaps(p2.latitude, p2.longitude)
    }

    fun lerPontoA(v: View?) {
        getPonto { ponto ->
            p1 = ponto
            Log.d("LER_PONTO_A", "${p1.latitude}, ${p1.longitude}")
            val editPonto: EditText = findViewById(R.id.editPtoA)
            editPonto.setText(p1.imprimir2())
        }
    }

    fun lerPontoB(v: View?) {
        getPonto { ponto ->
            p2 = ponto
            Log.d("LER_PONTO_B", "${p2.latitude}, ${p2.longitude}")
            val editPonto: EditText = findViewById(R.id.editPtoB)
            editPonto.setText(p2.imprimir2())
        }
    }

    fun getPonto(callback: (Ponto) -> Unit) {
        val ponto = Ponto()

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

            Log.d("GET_PONTO", "Solicitando localização atual...")

            fusedLocationProviderClient.getCurrentLocation(Priority.PRIORITY_HIGH_ACCURACY, null)
                .addOnSuccessListener { location ->
                    if (location != null) {
                        ponto.latitude = location.latitude
                        ponto.longitude = location.longitude
                        ponto.altitude = location.altitude

                        Log.d("GET_PONTO", "Latitude: ${ponto.latitude}, Longitude: ${ponto.longitude}, Altitude: ${ponto.altitude}")

                        callback(ponto)
                    } else {
                        Toast.makeText(this, "Não foi possível obter a localização", Toast.LENGTH_LONG).show()
                    }
                }
        } else {
            Toast.makeText(this, "Permissão de localização não concedida", Toast.LENGTH_LONG).show()
        }
    }

    fun calcularDistancia(v: View?) {
        if (::p1.isInitialized && ::p2.isInitialized) {
            val result = FloatArray(1)
            Location.distanceBetween(p1.latitude, p1.longitude, p2.latitude, p2.longitude, result)
            val distancia = result[0]
            Toast.makeText(this, "A distância entre Ponto A e Ponto B é: $distancia metros", Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(this, "Pontos A e B não estão definidos", Toast.LENGTH_LONG).show()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 1) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                reset(null)
            } else {
                Toast.makeText(this, "Permissão de localização negada", Toast.LENGTH_LONG).show()
            }
        }
    }

    fun mostrarGoogleMaps(latitude: Double, longitude: Double) {
        val wv: WebView = findViewById(R.id.webv)
        wv.settings.javaScriptEnabled = true
        wv.loadUrl("https://www.google.com/maps/search/?api=1&query=$latitude,$longitude")
    }
}