package com.example.jose_pc.noticiasapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class GeoListActivity extends AppCompatActivity {

    private String localidad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Capturo las coordenadas de la geolocalización
        Bundle bundle = getIntent().getExtras();
        localidad = bundle.getString("ciudad");

        // Envío las coordenadas al fragment
        Bundle arg = new Bundle();
        arg.putString("ciudad", localidad);
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
