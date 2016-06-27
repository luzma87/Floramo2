package com.lzm.Cajas.db;

import android.content.Context;

import java.text.Normalizer;
import java.util.List;

public class Genero {
    private long id = 0;
    private String fecha;
    private Long familia_id;
    private String nombre;
    private String nombreNorm;

    public GeneroDbHelper generoDbHelper;

    Context context;

    public Genero(Context context) {
        generoDbHelper = new GeneroDbHelper(context);
        this.context = context;
    }

    //getters
    public long getId() {
        return id;
    }

    public Long getFamilia_id() {
        return familia_id;
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

    public void setFamilia(Familia familia) {
        this.familia_id = familia.getId();
    }

    public void setFamilia_id(Long familia_id) {
        this.familia_id = familia_id;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public void save() {
        if (this.id == 0) {
            this.id = this.generoDbHelper.createGenero(this);
        } else {
            this.generoDbHelper.updateGenero(this);
        }
    }

    public static Genero get(Context context, long id) {
        GeneroDbHelper e = new GeneroDbHelper(context);
        return e.getGenero(id);
    }

    public static int count(Context context) {
        GeneroDbHelper e = new GeneroDbHelper(context);
        return e.countAllGeneros();
    }

    public static List<Genero> list(Context context) {
        GeneroDbHelper e = new GeneroDbHelper(context);
        return e.getAllGeneros();
    }
}
