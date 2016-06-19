package com.lzm.Cajas.db;

import android.content.Context;

import java.text.Normalizer;
import java.util.List;
import java.util.Locale;

/**
 * Created by DELL on 26/07/2014.
 */
public class Especie {
    private long id = 0;
    private String fecha;
    private Long genero_id;
    private String nombre;
    private String nombreNorm;
    private String nombreComun;
    private String nombreComunNorm;

    private String descripcionEn;
    private String descripcionEs;
    private String autor;

    private Long color1_id;
    private Long color2_id;

    private Long formaVida1_id;
    private Long formaVida2_id;

    private Long idTropicos;

    EspecieDbHelper especieDbHelper;

    Context context;

    private String genero;
    private String familia;
    private String color1;
    private String color2;
    private String formaVida1;
    private String formaVida2;

    public Especie(Context context) {
        especieDbHelper = new EspecieDbHelper(context);
        this.context = context;
    }

    public long getId() {
        return id;
    }

    public String getFamilia() {
        return familia;
    }

    public String getGenero() {
        return genero;
    }

    public String getNombre() {
        return nombre;
    }

    public String getFecha() {
        return fecha;
    }

    public String getColor1() {
        return color1;
    }

    public String getColor2() {
        return color2;
    }

    public String getFormaVida1() {
        return formaVida1;
    }

    public String getFormaVida2() {
        return formaVida2;
    }

    public Long getIdTropicos() {
        return idTropicos;
    }

    public void setIdTropicos(Long idTropicos) {
        this.idTropicos = idTropicos;
    }

    public void setDescripcionEn(String descripcionEn) {
        this.descripcionEn = descripcionEn;
    }

    public String getDescripcion() {
        String language = Locale.getDefault().getLanguage();
        String description;
        if (language.equals("en")) {
            description = descripcionEn;
        } else {
            description = descripcionEs;
        }
        return description;
    }

    public void setDescripcionEs(String descripcionEs) {
        this.descripcionEs = descripcionEs;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setNombreComun(String nombreComun) {
        this.nombreComun = nombreComun;
        if (this.nombreComun != null && !this.nombreComun.equals("") && nombreComun != null && !nombreComun.equals("")) {
            this.nombreComunNorm = Normalizer.normalize(nombreComun, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
        }
    }

    public void setGenero_id(long genero) {
        this.genero_id = genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public void setFamilia(String familia) {
        this.familia = familia;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
        this.nombreNorm = Normalizer.normalize(nombre, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public void setColor1_id(long color1) {
        this.color1_id = color1;
    }

    public void setColor2_id(long color2) {
        this.color2_id = color2;
    }

    public void setColor1(String color1) {
        this.color1 = color1;
    }

    public void setColor2(String color2) {
        this.color2 = color2;
    }

    public void setFormaVida1_id(long formaVida1_id) {
        this.formaVida1_id = formaVida1_id;
    }

    public void setFormaVida2_id(long formaVida2_id) {
        this.formaVida2_id = formaVida2_id;
    }

    public void setFormaVida1(String formaVida1) {
        this.formaVida1 = formaVida1;
    }

    public void setFormaVida2(String formaVida2) {
        this.formaVida2 = formaVida2;
    }

    public static Especie getDatos(Context context, long id) {
        EspecieDbHelper e = new EspecieDbHelper(context);
        return e.getDatosEspecie(id);
    }

    public static Especie get(Context context, long id) {
        EspecieDbHelper e = new EspecieDbHelper(context);
        return e.getEspecie(id);
    }

    public static List<Especie> sortedList(Context context, String sort, String order) {
        EspecieDbHelper e = new EspecieDbHelper(context);
        return e.getAllSortedEspecies(sort, order);
    }

    public static List<Especie> list(Context context) {
        EspecieDbHelper e = new EspecieDbHelper(context);
        return e.getAllEspecies();
    }

    public static int count(Context context) {
        EspecieDbHelper e = new EspecieDbHelper(context);
        return e.countAllEspecies();
    }
}
