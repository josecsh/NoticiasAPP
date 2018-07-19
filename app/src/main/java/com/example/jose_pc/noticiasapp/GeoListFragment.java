package com.example.jose_pc.noticiasapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;


public class GeoListFragment extends Fragment {

    // Etiqueta de depuración
    private static final String TAG = GeoListFragment.class.getSimpleName();

    // Adaptador del recycler view
    private AdaptadorLista adapter;

    // Instancia global del recycler view
    private RecyclerView listaRV;

    // Instancia global del LayoutManager del recycler view
    private RecyclerView.LayoutManager layoutManager;

    private Gson gson = new Gson();

    private FloatingActionButton fab;

    private String extraURL;

    private Boolean onGranada;

    public GeoListFragment() {}

    public static GeoListFragment createInstance(Bundle arg) {
        GeoListFragment f = new GeoListFragment();
        if(arg != null){
            f.setArguments(arg);
        }
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View v = inflater.inflate(R.layout.fragment_list, container, false);

        onGranada = getArguments().getBoolean("onGranada");

        if(onGranada) {
            extraURL = "?latitud=" + getArguments().getDouble("latitud") + "&longitud=" + getArguments().getDouble("longitud");
        }else{
            extraURL = "?localidad=" + getArguments().getString("localidad");
        }


        // Botón flotante para pasar al modo de geolocalización
        fab = v.findViewById(R.id.fab_location);

        fab.setImageResource(R.mipmap.no_location);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(v.getContext(), MainActivity.class);
                startActivity(i);

            }
        });

        // Referencia de la lista de Recyclerview
        listaRV = (RecyclerView) v.findViewById(R.id.noticias_recyclerview);
        listaRV.setHasFixedSize(true);

        // Usar un administrador para LinearLayout
        layoutManager = new LinearLayoutManager(getActivity());
        listaRV.setLayoutManager(layoutManager);

        listaRV.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        listaRV.computeVerticalScrollOffset();

        // Cargar datos en el adaptador
        cargarAdaptador();

        return v;
    }

    // Carga el adaptador con la información obtenida en la respuesta
    public void cargarAdaptador() {

        // URL del servidor
        String url = "http://192.168.1.36/noticias_web/gestion/php_scripts/app_obtener_geonoticias.php" + extraURL;

        // Petición GET
        VolleyS.getInstance(getActivity()).addToRequestQueue(
                new JsonObjectRequest(
                        Request.Method.GET,
                        url,
                        null,
                        new Response.Listener<JSONObject>() {

                            @Override
                            public void onResponse(JSONObject response) {
                                // Procesar la respuesta Json
                                procesarRespuesta(response);
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(getActivity(), "Error de conexión: "  + error.toString(), Toast.LENGTH_SHORT).show();
                            }
                        }

                )
        );

    }

    /* Interpreta los resultados de la respuesta para mostrarla corréctamente
     *
     * @param response Objeto Json con la respuesta
     */
    private void procesarRespuesta(JSONObject response) {
        try {
            // Obtener atributo "estado"
            String estado = response.getString("estado");

            if(onGranada){
                ((GeoListActivity) getActivity()).getSupportActionBar().setTitle(
                        "Granada - " + response.getString("localidad"));
            }else ((GeoListActivity) getActivity()).getSupportActionBar().setTitle(
                    response.getString("localidad"));

            // Realizar acción según estado
            switch (estado) {

                case "1": // ÉXITO
                    // Obtener información de noticias JSON
                    JSONArray info_noticias = response.getJSONArray("noticias");
                    // Parsear con Gson
                    Noticia[] noticias = gson.fromJson(info_noticias.toString(), Noticia[].class);
                    // Inicializar adaptador
                    adapter = new AdaptadorLista(getActivity(), Arrays.asList(noticias));
                    // Setear adaptador a la lista
                    listaRV.setAdapter(adapter);
                    break;

                case "2": // NO HAY NOTICIAS
                    String mensaje2 = response.getString("mensaje");
                    Toast.makeText(
                            getActivity(),
                            mensaje2,
                            Toast.LENGTH_LONG).show();
                    break;

                case "3": // ERROR DE LOCALIZACIÓN
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