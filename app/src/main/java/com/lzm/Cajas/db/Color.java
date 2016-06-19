package com.lzm.Cajas.db;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DELL on 27/07/2014.
 */
public class Color {
    private long id = 0;
    private String fecha;
    private String nombre;

    ColorDbHelper colorDbHelper;

    public Color(Context context) {
        colorDbHelper = new ColorDbHelper(context);
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

    public static Color get(Context context, long id) {
        ColorDbHelper e = new ColorDbHelper(context);
        return e.getColor(id);
    }

    public static int count(Context context) {
        ColorDbHelper e = new ColorDbHelper(context);
        return e.countAllColores();
    }

    public static ArrayList<Color> list(Context context) {
        ColorDbHelper e = new ColorDbHelper(context);
        return e.getAllColores();
    }
}
