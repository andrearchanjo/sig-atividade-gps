package com.example.atividade_gps;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void buscarInformacoesGPS(View v) {
        LocationManager mLocManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        LocationListener mLocListener = new MinhaLocalizacao();

        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
            && ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_NETWORK_STATE
                }, 1);
            return;
        }

        Log.d("LOCALIZACAO_TAG", "Solicitando atualizações de localização...");
        mLocManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0f, mLocListener);
        Log.d("LOCALIZACAO_TAG", Double.toString(MinhaLocalizacao.latitude));
        Log.d("LOCALIZACAO_TAG", Double.toString(MinhaLocalizacao.longitude));
        Log.d("LOCALIZACAO_TAG", "Finalizado atualizações de localização...");

        if (mLocManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            String texto = "Latitude: " + MinhaLocalizacao.latitude + "\nLongitude: " + MinhaLocalizacao.longitude;
            Toast.makeText(this, texto, Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "GPS DESABILITADO.", Toast.LENGTH_LONG).show();
        }

        mostrarGoogleMaps(MinhaLocalizacao.latitude, MinhaLocalizacao.longitude);
    }

    public void mostrarGoogleMaps(double latitude, double longitude) {
        WebView wv = findViewById(R.id.webv);
        wv.getSettings().setJavaScriptEnabled(true);
        wv.loadUrl("https://www.google.com/maps/search/?api=1&query=" + latitude + "," + longitude);
    }
}