package com.lzm.Cajas.db;

import android.content.Context;

import java.util.List;

/**
 * Created by Svt on 7/27/2014.
 */
public class Coordenada {
    public long id = 0;
    public double latitud;
    public double longitud;
    public double altitud;
    public CoordenadaDbHelper coordenadaDbHelper;
    public String fecha;
    Context context;

    public Coordenada(Context context, double latitud, double longitud) {
        this.latitud = latitud;
        this.longitud = longitud;
        this.coordenadaDbHelper = new CoordenadaDbHelper(context);
        this.context = context;
    }

    public Coordenada(Context context, double latitud, double longitud, double altitud) {
        this.latitud = latitud;
        this.longitud = longitud;
        this.altitud = altitud;
        this.coordenadaDbHelper = new CoordenadaDbHelper(context);
        this.context = context;
    }

    public Coordenada(Context context) {
        coordenadaDbHelper = new CoordenadaDbHelper(context);
        this.context = context;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getLatitud() {
        return latitud;
    }

    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }

    public double getLongitud() {
        return longitud;
    }

    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getFecha() {
        return fecha;
    }

    public void save() {
        if (this.id == 0) {
            this.id = this.coordenadaDbHelper.createCoordenada(this);
        } else {
            this.coordenadaDbHelper.updateCoordenada(this);
        }
    }

    public double getAltitud() {
        return altitud;
    }

    public void setAltitud(double altitud) {
        this.altitud = altitud;
    }

    public static Coordenada get(Context context, long id) {
        CoordenadaDbHelper e = new CoordenadaDbHelper(context);
        return e.getCoordenada(id);
    }

    public static int count(Context context) {
        CoordenadaDbHelper e = new CoordenadaDbHelper(context);
        return e.countAllCoordenadas();
    }


    public static List<Coordenada> list(Context context) {
        CoordenadaDbHelper e = new CoordenadaDbHelper(context);
        return e.getAllCoordenadas();
    }

    public static List<Coordenada> findAllByCoords(Context context, double lat, double lon, double alt) {
        CoordenadaDbHelper e = new CoordenadaDbHelper(context);
        return e.getAllCoordenadasByCoords(lat, lon, alt);
    }

    public static void empty(Context context) {
        CoordenadaDbHelper e = new CoordenadaDbHelper(context);
        e.deleteAllCoordenadas();
    }

}
