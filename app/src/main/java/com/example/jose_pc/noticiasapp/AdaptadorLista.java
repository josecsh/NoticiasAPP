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
    }

    @Override
    public void onItemClick(View view, int position) {
        NoticiaActivity.launch( (Activity) context, noticias.get(position).getID() );
    }

    public static class NoticiaViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        // Campos respectivos de una noticia
        public TextView titular;
        public ImageView imagen;
        public TextView seccion;
        public ItemClickListener listener;

        public NoticiaViewHolder(View v, ItemClickListener listener) {
            super(v);
            titular = (TextView) v.findViewById(R.id.titularList);
            imagen = (ImageView) v.findViewById(R.id.imagenList);
            seccion = (TextView) v.findViewById(R.id.seccionList);
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