package com.example.jose_pc.noticiasapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

import static android.app.PendingIntent.getActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Creación del fragmento principal
        if(savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.activity_main, new ListFragment(), "ListFragment").commit();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        // Comprobamos si el resultado de la actividad ListFragment es OK
        if(requestCode == 100 || requestCode == 3){
            if(resultCode == RESULT_OK || resultCode == 203){
                ListFragment listFragment = (ListFragment) getSupportFragmentManager()
                        .findFragmentByTag("ListFragment");
                listFragment.cargarAdaptador();
            }
        }
    }

}
