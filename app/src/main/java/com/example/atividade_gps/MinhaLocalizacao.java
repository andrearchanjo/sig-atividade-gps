package com.example.atividade_gps;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.util.Log;

public class MinhaLocalizacao implements LocationListener {
    public static double latitude = 0.0;
    public static double longitude = 0.0;

    @Override
    public void onLocationChanged(Location location) {
        latitude = location.getLatitude();
        longitude = location.getLongitude();

        Log.d("LOCALIZACAO_LISTENER", Double.toString(latitude));
        Log.d("LOCALIZACAO_LISTENER", Double.toString(longitude));
    }

    @Override
    public void onProviderEnabled(String provider) {
    }

    @Override
    public void onProviderDisabled(String provider) {
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }
}