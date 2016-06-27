package com.lzm.Cajas.db;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

public class FormaVida {
    private long id = 0;
    private String fecha;
    private String nombre;

    FormaVidaDbHelper formaVidaDbHelper;

    public FormaVida(Context context) {
        formaVidaDbHelper = new FormaVidaDbHelper(context);
    }

    public long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getFecha() {
        return fecha;
    }

    //setters
    public void setId(long id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String toString() {
        return "**" + nombre;
    }

    public static FormaVida get(Context context, long id) {
        FormaVidaDbHelper e = new FormaVidaDbHelper(context);
        return e.getFormaVida(id);
    }

    public static int count(Context context) {
        FormaVidaDbHelper e = new FormaVidaDbHelper(context);
        return e.countAllFormasVida();
    }

    public static ArrayList<FormaVida> list(Context context) {
        FormaVidaDbHelper e = new FormaVidaDbHelper(context);
        return e.getAllFormasVida();
    }
}
