package com.lzm.Cajas.models;

import android.content.Context;

import com.lzm.Cajas.repositories.FotoDbHelper;

import java.util.List;

public class Foto {
    private long id = 0;
    private String fecha;

    private Long especie_id;

    private String path;

    Context context;

    private FotoDbHelper fotoDbHelper;

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

    public void setEspecie(Especie especie) {
        this.especie_id = especie.getId();
    }

    public void setEspecie_id(Long especie_id) {
        this.especie_id = especie_id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public void setPath(String path) {
        this.path = path;
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

    public static List<Foto> findAllByEspecieWithdCoords(Context context, Especie especie) {
        FotoDbHelper e = new FotoDbHelper(context);
        return e.getAllFotosByEspecieIdWithCoords(especie.getId());
    }

    public List<Foto> findAllSameEspecie(Context context) {
        FotoDbHelper e = new FotoDbHelper(context);
        return e.getAllFotosByEspecieId(especie_id);
    }
}
