package com.example.jose_pc.noticiasapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class GeoListActivity extends AppCompatActivity {

    private String localidad;
    private String cp;
    private Double latitude;
    private Double longitude;
    private Boolean enGranada;
    private Bundle arg = new Bundle();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Capturo los datos
        Bundle bundle = getIntent().getExtras();
        enGranada = bundle.getBoolean("en_granada");
        latitude = bundle.getDouble("latitud");
        longitude = bundle.getDouble("longitud");
        localidad = bundle.getString("localidad");

        // Envío los datos al fragment
        arg.putBoolean("onGranada", enGranada);
        arg.putDouble("latitud", latitude);
        arg.putDouble("longitud", longitude);
        arg.putString("localidad", localidad);

        GeoListFragment gl = GeoListFragment.createInstance(arg);

        // Creación del fragmento de la lista geolocalizada
        if(savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.activity_main, gl, "GeoListFragment").commit();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        // Comprobamos si el resultado de la actividad GeoListFragment es OK
        if(requestCode == 100 || requestCode == 3){
            if(resultCode == RESULT_OK || resultCode == 203){
                ListFragment listFragment = (ListFragment) getSupportFragmentManager()
                        .findFragmentByTag("GeoListFragment");
                listFragment.cargarAdaptador();
            }
        }
    }

}
