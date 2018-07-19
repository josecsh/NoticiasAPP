package com.example.jose_pc.noticiasapp;

public class Noticia {
    private String idNoticia;
    private String titularNoticia;
    private String textoNoticia;
    private String seccionNoticia;
    private String ubicacionNoticia;
    private String fechaNoticia;
    private String horaNoticia;


    public Noticia(String id, String titular, String texto, String seccion, String ubicacion, String fecha, String hora) {
        this.idNoticia=id;
        this.titularNoticia=titular;
        this.textoNoticia=texto;
        this.seccionNoticia=seccion;
        this.ubicacionNoticia=ubicacion;
        this.fechaNoticia=fecha;
        this.horaNoticia=hora;
    }

    public String getID(){ return idNoticia; }

    public String getTitular() {
        return titularNoticia;
    }

    public String getTexto() { return textoNoticia; }

    public String getSeccion() { return seccionNoticia; }

    public String getUbicacion() { return ubicacionNoticia; }

    public String getFecha() { return fechaNoticia; }

    public String getHora() { return horaNoticia; }
}
