package com.example.jose_pc.noticiasapp;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public final class VolleyS {

    // Atributos
    private static VolleyS singleton;
    private RequestQueue requestQueue;
    private static Context context;


    private VolleyS(Context context) {
        VolleyS.context = context;
        requestQueue = getRequestQueue();
    }

    /**
     * Retorna la instancia unica del singleton
     *
     * @param context contexto donde se ejecutarán las peticiones
     * @return Instancia
     */
    public static synchronized VolleyS getInstance(Context context) {
        if (singleton == null) {
            singleton = new VolleyS(context.getApplicationContext());
        }
        return singleton;
    }

    /**
     * Obtiene la instancia de la cola de peticiones
     *
     * @return cola de peticiones
     */
    public RequestQueue getRequestQueue() {
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(context.getApplicationContext());
        }
        return requestQueue;
    }

    /**
     * Añade la petición a la cola
     *
     * @param req petición
     * @param <T> Resultado final de tipo T
     */
    public <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }

}
