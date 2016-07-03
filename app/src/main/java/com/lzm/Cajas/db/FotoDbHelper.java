package com.lzm.Cajas.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class FotoDbHelper extends DbHelper {

    private static final String KEY_ESPECIE_ID = "especie_id";
    private static final String KEY_PATH = "path";
    public static final String KEY_RUTA_ID = "ruta_id";
    private static final String KEY_LONGITUD = "longitud";
    private static final String KEY_LATITUD = "latitud";
    private static final String KEY_ALTITUD = "altitud";
    private static final String KEY_LUGAR_ID = "lugar_id";
    private static final String KEY_COORDENADA_ID = "coordenada_id";
    private static final String KEY_ES_MIA = "es_mia";

    public static final String[] KEYS_FOTO = {KEY_ESPECIE_ID, KEY_PATH,
            KEY_LATITUD, KEY_LONGITUD, KEY_ALTITUD, KEY_LUGAR_ID, KEY_COORDENADA_ID, KEY_RUTA_ID, KEY_ES_MIA};

    public FotoDbHelper(Context context) {
        super(context);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // on upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FOTO);

        // create new tables
        onCreate(db);
    }

    public Foto getFoto(long foto_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String selectQuery = "SELECT  * FROM " + TABLE_FOTO + " WHERE "
                + KEY_ID + " = " + foto_id;

        Cursor c = db.rawQuery(selectQuery, null);
        Foto ft = null;
        if (c != null) {
            c.moveToFirst();
            ft = setDatos(c);
        }
        db.close();
        return ft;
    }

    public int countAllFotos() {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT  count(*) count FROM " + TABLE_FOTO;
        Cursor c = db.rawQuery(selectQuery, null);
        if (c.moveToFirst()) {
            int count = c.getInt(c.getColumnIndex("count"));
            db.close();
            return count;
        }
        db.close();
        return 0;
    }

    public List<Foto> getAllFotos() {
        SQLiteDatabase db = this.getReadableDatabase();
        List<Foto> fotos = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + TABLE_FOTO;

        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Foto f = setDatos(c);

                // adding to tags list
                fotos.add(f);
            } while (c.moveToNext());
        }
        db.close();
        return fotos;
    }

    public List<Foto> getAllFotosByEspecie(Especie especie) {
        return getAllFotosByEspecieId(especie.getId());
    }

    public List<Foto> getAllFotosByEspecieId(Long especieId) {
        SQLiteDatabase db = this.getReadableDatabase();
        List<Foto> fotos = new ArrayList<>();

        String selectQuery = "SELECT " +
                "f.id id," +
                "f.fecha fecha," +
                "f.especie_id especie_id," +
                "f.latitud latitud," +
                "f.longitud longitud," +
                "f.altitud altitud," +
                "f.path path," +
                "f.coordenada_id coordenada_id," +
                "f.lugar_id lugar_id," +
                "l.icon lugar_icon FROM " + TABLE_FOTO + " f " +
                " LEFT JOIN " + TABLE_LUGAR + " l ON f.lugar_id = l.id" +
                " WHERE " + KEY_ESPECIE_ID + " = " + especieId;

        Cursor c = db.rawQuery(selectQuery, null);

        if (c.moveToFirst()) {
            do {
                Foto f = setDatos2(c);
                fotos.add(f);
            } while (c.moveToNext());
        }
        db.close();
        return fotos;
    }

    private Foto setDatos(Cursor c) {
        Foto f = new Foto(this.context);
        f.setId(c.getLong((c.getColumnIndex(KEY_ID))));
        f.setFecha(c.getString(c.getColumnIndex(KEY_FECHA)));
        f.setEspecie_id(c.getLong(c.getColumnIndex(KEY_ESPECIE_ID)));
        f.setLatitud(c.getDouble(c.getColumnIndex(KEY_LATITUD)));
        f.setLongitud(c.getDouble(c.getColumnIndex(KEY_LONGITUD)));
        f.setAltitud(c.getDouble(c.getColumnIndex(KEY_ALTITUD)));
        f.setPath((c.getString(c.getColumnIndex(KEY_PATH))));
        f.setCoordenada_id((c.getLong(c.getColumnIndex(KEY_COORDENADA_ID))));
        f.setLugar_id((c.getLong(c.getColumnIndex(KEY_LUGAR_ID))));
        return f;
    }

    private Foto setDatos2(Cursor c) {
        Foto f = new Foto(this.context);
        f.setId(c.getLong((c.getColumnIndex("id"))));
        f.setFecha(c.getString(c.getColumnIndex("fecha")));
        f.setEspecie_id(c.getLong(c.getColumnIndex("especie_id")));
        f.setLatitud(c.getDouble(c.getColumnIndex("latitud")));
        f.setLongitud(c.getDouble(c.getColumnIndex("longitud")));
        f.setAltitud(c.getDouble(c.getColumnIndex("altitud")));
        f.setPath((c.getString(c.getColumnIndex("path"))));
        f.setCoordenada_id((c.getLong(c.getColumnIndex("coordenada_id"))));
        f.setLugar_id((c.getLong(c.getColumnIndex("lugar_id"))));
        f.setLugarIcon((c.getString(c.getColumnIndex("lugar_icon"))));
        f.setLugar((c.getString(c.getColumnIndex("lugar_nombre"))));
        return f;
    }
}
