package com.lzm.Cajas.db;

import android.content.Context;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by DELL on 26/07/2014.
 */
public class Especie {
    public long id = 0;
    public String fecha;
    //    public Genero genero;
    public Long genero_id;
    public String nombre;
    public String nombreNorm;
    public String nombreComun;
    public String nombreComunNorm;
    public String comentarios;

    public String descripcionEn;
    public String descripcionEs;
    public String autor;

    public Long color1_id;
    public Long color2_id;

    public Long formaVida1_id;
    public Long formaVida2_id;

    public Long idTropicos;

    public Integer esMia = 0;

    EspecieDbHelper especieDbHelper;

    Context context;

    public String genero;
    public String familia;
    public String color1;
    public String color2;
    public String formaVida1;
    public String formaVida2;

    public Especie(Context context) {
        especieDbHelper = new EspecieDbHelper(context);
        this.context = context;
    }

    public Especie(Context context, String nombreComun) {
        this.nombreComun = nombreComun;
        this.context = context;
        especieDbHelper = new EspecieDbHelper(context);
    }

    public Especie(Context context, String nombreEspecie, int algo) {
        this.nombre = nombreEspecie;
        this.context = context;
        especieDbHelper = new EspecieDbHelper(context);
    }

    public Especie(Context context, String nombreComun, Genero genero, String nombre, String comentarios) {
        this.nombre = nombre;
        this.nombreComun = nombreComun;
        this.genero_id = genero.id;
        this.comentarios = comentarios;
        this.context = context;
        especieDbHelper = new EspecieDbHelper(context);
    }

    public Especie(Context context, Genero genero, String nombre, String comentarios) {
        this.nombre = nombre;
        this.genero_id = genero.id;
        this.comentarios = comentarios;
        this.context = context;
        especieDbHelper = new EspecieDbHelper(context);
    }

    public Especie(Context context, Genero genero, String nombre) {
        this.nombre = nombre;
        this.genero_id = genero.id;
        this.context = context;
        especieDbHelper = new EspecieDbHelper(context);
    }

    //getters
    public long getId() {
        return id;
    }

    public String getNombreComun() {
        return nombreComun;
    }

    public Genero getGenero(Context context) {
        return Genero.get(context, genero_id);
    }

    public long getGenero_id() {
        return genero_id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getComentarios() {
        return comentarios;
    }

    public String getFecha() {
        return fecha;
    }

    public String getNombreCientifico() {
        return getGenero(context).nombre + " " + nombre;
    }

    public Color getColor1(Context context) {
        if (color1_id != null) {
            return Color.get(context, color1_id);
        } else {
            return null;
        }
    }

    public Color getColor2(Context context) {
        if (color2_id != null) {
            return Color.get(context, color2_id);
        } else {
            return null;
        }
    }

    public long getColor1_id() {
        return color1_id;
    }

    public long getColor2_id() {
        return color2_id;
    }

    public FormaVida getFormaVida1(Context context) {
        if (formaVida1_id != null) {
            return FormaVida.get(context, formaVida1_id);
        } else {
            return null;
        }
    }

    public FormaVida getFormaVida2(Context context) {
        if (formaVida2_id != null) {
            return FormaVida.get(context, formaVida2_id);
        } else {
            return null;
        }
    }

    public long getFormaVida1_id() {
        return formaVida1_id;
    }

    public long getFormaVida2_id() {
        return formaVida2_id;
    }

    public Long getIdTropicos() {
        return idTropicos;
    }

    public Integer getEsMia() {
        return esMia;
    }

    public void setEsMia(Integer esMia) {
        this.esMia = esMia;
    }

    public void setIdTropicos(Long idTropicos) {
        this.idTropicos = idTropicos;
    }

    public String getDescripcionEn() {
        return descripcionEn;
    }

    public void setDescripcionEn(String descripcionEn) {
        this.descripcionEn = descripcionEn;
    }

    public String getDescripcionEs() {
        return descripcionEs;
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

    //setters
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

    public void setGenero(Genero genero) {
        this.genero_id = genero.id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
        this.nombreNorm = Normalizer.normalize(nombre, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
    }

    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
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

    public void setColor1(Color color1) {
        this.color1_id = color1.id;
    }

    public void setColor2(Color color2) {
        this.color2_id = color2.id;
    }

    public void setFormaVida1_id(long formaVida1_id) {
        this.formaVida1_id = formaVida1_id;
    }

    public void setFormaVida2_id(long formaVida2_id) {
        this.formaVida2_id = formaVida2_id;
    }

    public void setFormaVida1(FormaVida formaVida1) {
        this.formaVida1_id = formaVida1.id;
    }

    public void setFormaVida2(FormaVida formaVida2) {
        this.formaVida2_id = formaVida2.id;
    }

    public void save() {
        if (this.id == 0) {
            this.esMia = 1;
            this.id = this.especieDbHelper.createEspecie(this);
        } else {
            this.especieDbHelper.updateEspecie(this);
        }
    }

    public static Especie getDatos(Context context, long id) {
        EspecieDbHelper e = new EspecieDbHelper(context);
        return e.getDatosEspecie(id);
    }

    public static Especie get(Context context, long id) {
        EspecieDbHelper e = new EspecieDbHelper(context);
        return e.getEspecie(id);
    }

    public static Especie getByNombreOrCreate(Context context, String nombreEspecie) {
        Especie especie;
        List<Especie> listEspecies = findAllByNombre(context, nombreEspecie);
        if (listEspecies.size() == 0) {
            especie = new Especie(context);
            especie.setNombre(nombreEspecie);
            especie.esMia = 1;
            especie.save();
        } else if (listEspecies.size() == 1) {
            especie = getDatos(context, listEspecies.get(0).id);
        } else {
            especie = getDatos(context, listEspecies.get(0).id);
        }
        return especie;
    }

    public static List<Especie> sortedList(Context context, String sort, String order) {
        EspecieDbHelper e = new EspecieDbHelper(context);
        return e.getAllSortedEspecies(sort, order);
    }

    public static List<Especie> list(Context context) {
        EspecieDbHelper e = new EspecieDbHelper(context);
        return e.getAllEspecies();
    }

    public static List<Especie> findAllByGenero(Context context, Genero genero) {
        EspecieDbHelper e = new EspecieDbHelper(context);
        return e.getAllEspeciesByGenero(genero);
    }

    public static List<Especie> findAllByNombre(Context context, String especie) {
        EspecieDbHelper e = new EspecieDbHelper(context);
        return e.getAllEspeciesByNombre(especie);
    }

    public static List<Especie> findAllByNombreLike(Context context, String especie) {
        EspecieDbHelper e = new EspecieDbHelper(context);
        return e.getAllEspeciesByNombreLike(especie);
    }

    public static List<Especie> findAllByNombreComunLike(Context context, String especie) {
        EspecieDbHelper e = new EspecieDbHelper(context);
        return e.getAllEspeciesByNombreComunLike(especie);
    }

    public static List<Especie> findAllByGeneroAndNombreLike(Context context, Genero genero, String especie) {
        EspecieDbHelper e = new EspecieDbHelper(context);
        return e.getAllEspeciesByGeneroAndNombreLike(genero, especie);
    }

    public static int count(Context context) {
        EspecieDbHelper e = new EspecieDbHelper(context);
        return e.countAllEspecies();
    }

    public static int countByGenero(Context context, Genero genero) {
        EspecieDbHelper e = new EspecieDbHelper(context);
        return e.countEspeciesByGenero(genero);
    }

    public static int countByNombre(Context context, String especie) {
        EspecieDbHelper e = new EspecieDbHelper(context);
        return e.countEspeciesByNombre(especie);
    }

    public static int countByColor(Context context, Color color) {
        EspecieDbHelper e = new EspecieDbHelper(context);
        return e.countFotosByColor(color);
    }

    public static List<Especie> findAllByColor(Context context, Color color) {
        EspecieDbHelper e = new EspecieDbHelper(context);
        return e.getAllEspeciesByColor(color);
    }

    public static List<Especie> busqueda_old(Context context, String formaVida, String color, String nombre, String andOr) {
        EspecieDbHelper e = new EspecieDbHelper(context);
        return e.getBusqueda_old(formaVida, color, nombre, andOr);
    }

    public static List<Especie> busqueda(Context context, ArrayList<String> formaVida, ArrayList<String> color, String nombre, String andOr) {
        EspecieDbHelper e = new EspecieDbHelper(context);
        return e.getBusqueda(formaVida, color, nombre, andOr);
    }

    public static void delete(Context context, Especie especie) {
        EspecieDbHelper e = new EspecieDbHelper(context);
        e.deleteEspecie(especie, true);
    }

    public static void empty(Context context) {
        EspecieDbHelper e = new EspecieDbHelper(context);
        e.deleteAllEspecies();
    }


}
