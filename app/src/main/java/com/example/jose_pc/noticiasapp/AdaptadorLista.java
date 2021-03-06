package com.example.jose_pc.noticiasapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;


public class AdaptadorLista extends RecyclerView.Adapter<AdaptadorLista.NoticiaViewHolder> implements ItemClickListener {

    // Lista de objetos que representan la fuente de datos de inflado
    private List<Noticia> noticias;

    // Contexto donde actua el recycler view
    private Context context;

    String distGranada[] = {"Genil", "Zaid�n", "Ronda", "Centro", "Albayc�n", "Beiro", "Chana", "Norte"};
    String loc;

    public AdaptadorLista(Context context, List<Noticia> noticias) {
        this.context = context;
        this.noticias = noticias;
    }

    @Override
    public int getItemCount() {
        return noticias.size();
    }

    @Override
    public NoticiaViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.noticia_list, viewGroup, false);
        return new NoticiaViewHolder(v, this);
    }

    @Override
    public void onBindViewHolder(NoticiaViewHolder viewHolder, int i) {
        viewHolder.titular.setText(noticias.get(i).getTitular());
        viewHolder.seccion.setText(noticias.get(i).getSeccion());

        loc = noticias.get(i).getUbicacion();
        for(int j=0; j<distGranada.length; ++j){
            if(noticias.get(i).getUbicacion().equals(distGranada[j])) {
                loc = "Granada - " + noticias.get(i).getUbicacion();
            }
        }

        viewHolder.ubicacion.setText(loc);
    }

    @Override
    public void onItemClick(View view, int position) {
        NoticiaActivity.launch( (Activity) context, noticias.get(position).getID() );
    }

    public static class NoticiaViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        // Campos respectivos de una noticia
        public TextView titular;
        public TextView seccion;
        public TextView ubicacion;
        public ItemClickListener listener;

        public NoticiaViewHolder(View v, ItemClickListener listener) {
            super(v);
            titular = v.findViewById(R.id.titularList);
            seccion = v.findViewById(R.id.seccionList);
            ubicacion = v.findViewById(R.id.ubicacionList);
            this.listener = listener;
            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            listener.onItemClick(v, getAdapterPosition());
        }
    }
}

interface ItemClickListener {
    void onItemClick(View view, int position);
}