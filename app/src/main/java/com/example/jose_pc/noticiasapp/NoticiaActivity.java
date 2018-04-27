package com.example.jose_pc.noticiasapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.ToolbarWidgetWrapper;
import android.view.MenuItem;
import android.widget.Toast;

import static android.app.PendingIntent.getActivity;

/*
 * Esta actividad contiene un fragmento que muestra las noticias completas
 * por su ID_Noticia
 */
public class NoticiaActivity extends AppCompatActivity {

    // Instancia global del dentificador de la noticia
    private String idNoticia;

    /*
     * Inicia una nueva instancia de la actividad
     *
     * @param activity Contexto desde donde se lanzará
     * @param idNoticia Identificador de la noticia a mostrar
     */
    public static void launch(Activity activity, String idNoticia) {
        Intent intent = getLaunchIntent(activity, idNoticia);
        activity.startActivityForResult(intent, 100);
    }

    // Construye un Intent a partir del contexto y la actividad y lo devuelve
    public static Intent getLaunchIntent(Context context, String idNoticia) {
        Intent intent = new Intent(context, NoticiaActivity.class);
        intent.putExtra(String.valueOf(R.string.id_extra), idNoticia);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (getSupportActionBar() != null) {
            // Setear ícono "<-" como Up button
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        // Retener instancia
        if (getIntent().getStringExtra(String.valueOf(R.string.id_extra)) != null)
            idNoticia = getIntent().getStringExtra(String.valueOf(R.string.id_extra));

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.activity_main, NoticiaFragment.createInstance(idNoticia), "NoticiaFragment")
                    .commit();
        }

    }


    // Evento de los botones de la ToolBar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case android.R.id.home:
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 101) {
            if (resultCode == RESULT_OK) {
                NoticiaFragment fragment = (NoticiaFragment) getSupportFragmentManager().
                        findFragmentByTag("NoticiaFragment");
                fragment.cargarDatos();

                setResult(RESULT_OK);
            } else if (resultCode == 203) {
                setResult(203);
                finish();
            } else {
                setResult(RESULT_CANCELED);
            }
        }

    }


}
