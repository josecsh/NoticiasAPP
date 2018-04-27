package com.example.jose_pc.noticiasapp;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.Arrays;

public class NoticiaFragment extends Fragment {

    //Etiqueta de depuración
    private static final String TAG = NoticiaFragment.class.getSimpleName();

    // Instancias de Views
    private TextView titular;
    private TextView texto;
    private TextView seccion;
    private String extraID;
    private Gson gson = new Gson();

    public NoticiaFragment() {}

    public static NoticiaFragment createInstance(String idNoticia) {
        NoticiaFragment noticiaFragment = new NoticiaFragment();
        Bundle bundle = new Bundle();
        bundle.putString(String.valueOf(R.string.id_extra), idNoticia);
        noticiaFragment.setArguments(bundle);
        return noticiaFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_noticia, container, false);

        // Obtención de views
        titular = (TextView) v.findViewById(R.id.titularNoticia);
        texto = (TextView) v.findViewById(R.id.textoNoticia);
        seccion = (TextView) v.findViewById(R.id.seccionNoticia);

        // Obtener extra del intent de envío
        extraID = getArguments().getString(String.valueOf(R.string.id_extra));

        // Cargar datos desde el web service
        cargarDatos();

        return v;
    }

    // Obtiene los datos desde el servidor
    public void cargarDatos() {

        // URL del servidor
        String url = "http://bahia.ugr.es/~noticiasapp/gestion/php_scripts/app_obtener_por_id.php?idNoticia=" + extraID;

        // Realizar petición de noticia por ID
        VolleyS.getInstance(getActivity()).addToRequestQueue(
                new JsonObjectRequest(
                        Request.Method.GET,
                        url,
                        null,
                        new Response.Listener<JSONObject>() {

                            @Override
                            public void onResponse(JSONObject response) {
                                // Procesar respuesta Json
                                procesarRespuesta(response);
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.d(TAG, "Error Volley: " + error.getMessage());
                            }
                        }
                )
        );
    }

    /*
     * Procesa cada uno de los estados posibles de la
     * respuesta enviada desde el servidor
     *
     * @param response Objeto Json
     */

    private void procesarRespuesta(JSONObject response) {

        try {
            // Obtener atributo "estado"
            String estado = response.getString("estado");

            switch (estado) {
                case "1": // ÉXITO
                    // Obtener objeto "noticia"
                    JSONObject object = response.getJSONObject("noticia");

                    //Parsear objeto
                    Noticia noticia = gson.fromJson(object.toString(), Noticia.class);

                    // Rellenando valores en los views
                    titular.setText(noticia.getTitular());
                    texto.setText(noticia.getTexto());
                    seccion.setText(noticia.getSeccion());


                break;

                case "2": // ERROR EN LA BD
                    String mensaje2 = response.getString("mensaje");
                    Toast.makeText(
                            getActivity(),
                            mensaje2,
                            Toast.LENGTH_LONG).show();
                    break;

                case "3": // ERROR DE PETICIÓN
                    String mensaje3 = response.getString("mensaje");
                    Toast.makeText(
                            getActivity(),
                            mensaje3,
                            Toast.LENGTH_LONG).show();
                    break;
            }

        } catch (JSONException e) {
            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

}
