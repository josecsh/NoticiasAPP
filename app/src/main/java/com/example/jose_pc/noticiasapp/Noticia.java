package com.example.jose_pc.noticiasapp;

public class Noticia {
    private String idNoticia;
    private String titularNoticia;
    private String textoNoticia;
    private String seccionNoticia;


    public Noticia(String id, String titular, String texto, String seccion) {
        this.idNoticia=id;
        this.titularNoticia=titular;
        this.textoNoticia=texto;
        this.seccionNoticia=seccion;
    }

    public String getID(){ return idNoticia; }

    public String getTitular() {
        return titularNoticia;
    }

    public String getTexto() { return textoNoticia; }

    public String getSeccion() { return seccionNoticia; }
}
