package com.lzm.Cajas.db;

import android.content.Context;

import java.text.Normalizer;
import java.util.ArrayList;

public class Lugar {
    private long id = 0;
    private String fecha;
    private String nombre;
    private String nombreNorm;
    private String path;
    private String icon;

    LugarDbHelper lugarDbHelper;

    public Lugar(Context context) {
        lugarDbHelper = new LugarDbHelper(context);
    }

    public long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getNombreNorm() {
        return nombreNorm;
    }

    public String getFecha() {
        return fecha;
    }

    public String getPath() {
        return path;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
        this.nombreNorm = Normalizer.normalize(nombre, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String toString() {
        return "**" + nombre;
    }

    public static Lugar get(Context context, long id) {
        LugarDbHelper e = new LugarDbHelper(context);
        return e.getLugar(id);
    }

    public static ArrayList<Lugar> list(Context context) {
        LugarDbHelper e = new LugarDbHelper(context);
        return e.getAllLugares();
    }
}
