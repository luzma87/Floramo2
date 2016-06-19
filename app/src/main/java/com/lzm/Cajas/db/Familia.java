package com.lzm.Cajas.db;

import android.content.Context;

import java.text.Normalizer;
import java.util.List;

/**
 * Created by DELL on 28/07/2014.
 */
public class Familia {
    private long id = 0;
    private String fecha;
    private String nombre;
    private String nombreNorm;

    public FamiliaDbHelper familiaDbHelper;

    public Familia(Context context) {
        familiaDbHelper = new FamiliaDbHelper(context);
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

    public static Familia get(Context context, long id) {
        FamiliaDbHelper e = new FamiliaDbHelper(context);
        return e.getFamilia(id);
    }

    public static int count(Context context) {
        FamiliaDbHelper e = new FamiliaDbHelper(context);
        return e.countAllFamilias();
    }

    public static List<Familia> list(Context context) {
        FamiliaDbHelper e = new FamiliaDbHelper(context);
        return e.getAllFamilias();
    }
}
