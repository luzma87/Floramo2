package com.lzm.Cajas.repositories;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.lzm.Cajas.db.DbHelper;
import com.lzm.Cajas.models.Especie;
import com.lzm.Cajas.models.Foto;

import java.util.ArrayList;
import java.util.List;

public class FotoDbHelper extends DbHelper {

    public static final String KEY_ESPECIE_ID = "especie_id";
    public static final String KEY_PATH = "path";

    public static final String[] KEYS_FOTO = {KEY_ESPECIE_ID, KEY_PATH};

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
                "id," +
                "fecha," +
                "especie_id," +
                "path" +
                " FROM " + TABLE_FOTO  +
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

    public List<Foto> getAllFotosByEspecieIdWithCoords(Long especieId) {
        SQLiteDatabase db = this.getReadableDatabase();
        List<Foto> fotos = new ArrayList<>();

        String selectQuery = "SELECT " +
                "f.id id," +
                "f.fecha fecha," +
                "f.especie_id especie_id," +
                "f.path path," +
                "f.coordenada_id coordenada_id," +
                "c.latitud latitud," +
                "c.longitud longitud," +
                "c.altitud altitud," +
                "f.lugar_id lugar_id," +
                "l.icon lugar_icon," +
                "l.nombre lugar_nombre" +
                " FROM " + TABLE_FOTO + " f " +
                " LEFT JOIN " + TABLE_LUGAR + " l ON f.lugar_id = l.id" +
                " INNER JOIN " + TABLE_COORDENADA + " c ON f.coordenada_id = c.id" +
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
        f.setPath((c.getString(c.getColumnIndex(KEY_PATH))));
        return f;
    }

    private Foto setDatos2(Cursor c) {
        Foto f = new Foto(this.context);
        f.setId(c.getLong((c.getColumnIndex("id"))));
        f.setFecha(c.getString(c.getColumnIndex("fecha")));
        f.setEspecie_id(c.getLong(c.getColumnIndex("especie_id")));
        f.setPath((c.getString(c.getColumnIndex("path"))));
        return f;
    }
}
