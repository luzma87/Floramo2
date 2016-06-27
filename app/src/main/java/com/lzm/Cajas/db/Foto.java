package com.lzm.Cajas.db;

import android.content.Context;

import java.util.List;

public class Foto {
    private long id = 0;
    private String fecha;
    private String keywords;

    private Long especie_id;

    private double latitud;
    private double longitud;
    private double altitud = 0;
    private Long coordenada_id;

    private Long lugar_id;

    private String path;

    Context context;

    FotoDbHelper fotoDbHelper;

    public Foto(Context context) {
        fotoDbHelper = new FotoDbHelper(context);
    }

    public long getId() {
        return id;
    }

    public String getFecha() {
        return fecha;
    }

    public String getPath() {
        return path;
    }

    public void setCoordenada_id(Long coordenada_id) {
        this.coordenada_id = coordenada_id;
    }

    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }

    public void setAltitud(double altitud) {
        this.altitud = altitud;
    }

    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }

    public void setEspecie(Especie especie) {
        this.especie_id = especie.getId();
    }

    public void setEspecie_id(Long especie_id) {
        this.especie_id = especie_id;
    }

    public void setLugar_id(Long lugar_id) {
        this.lugar_id = lugar_id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void delete() {
        this.fotoDbHelper.deleteFoto(this);
    }

    public static Foto get(Context context, long id) {
        FotoDbHelper e = new FotoDbHelper(context);
        return e.getFoto(id);
    }

    public static List<Foto> list(Context context) {
        FotoDbHelper e = new FotoDbHelper(context);
        return e.getAllFotos();
    }

    public static int count(Context context) {
        FotoDbHelper e = new FotoDbHelper(context);
        return e.countAllFotos();
    }

    public static List<Foto> findAllByEspecie(Context context, Especie especie) {
        FotoDbHelper e = new FotoDbHelper(context);
        return e.getAllFotosByEspecie(especie);
    }

    public List<Foto> findAllSameEspecie(Context context) {
        FotoDbHelper e = new FotoDbHelper(context);
        return e.getAllFotosByEspecieId(especie_id);
    }
}
