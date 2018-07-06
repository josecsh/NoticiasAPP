package com.example.jose_pc.noticiasapp;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class Geolocation extends AppCompatActivity {

    private double latitude, longitude;
    String loc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // https://github.com/eddydn/GetAddressGeocode

        // Casa
        //latitude = 37.163894;
        //longitude = -3.596656;

        // Apartamento playa
        //latitude = 36.732444;
        //longitude = -3.682693;

        // Armilla
        //latitude = 37.142772;
        //longitude = -3.627786;

        // Casa Lorena
        latitude = 37.190968;
        longitude = -3.624540;

        Geocoder gc = new Geocoder(this, Locale.getDefault());

        try {
            List<Address> list = gc.getFromLocation(latitude, longitude, 1);
            if(list.size() > 0) {
                Address address = list.get(0);
                loc = address.getPostalCode();
            }

            Intent i = new Intent(getApplicationContext(), GeoListActivity.class);

            //i.putExtra("latitud", latitude);
            //i.putExtra("longitud", longitude);
            i.putExtra("ciudad", loc);

            startActivity(i);

        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
