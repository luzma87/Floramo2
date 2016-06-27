package com.lzm.Cajas.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class GeneroDbHelper extends DbHelper {

    public static final String KEY_NOMBRE = "nombre";
    public static final String KEY_NOMBRE_NORM = "nombre_norm";
    public static final String KEY_FAMILIA_ID = "familia_id";

    public static final String[] KEYS_GENERO = {KEY_NOMBRE, KEY_NOMBRE_NORM, KEY_FAMILIA_ID};

    public GeneroDbHelper(Context context) {
        super(context);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // on upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_GENERO);

        // create new tables
        onCreate(db);
    }

    public long createGenero(Genero genero) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = setValues(genero, true);

        // insert row
        long res = db.insert(TABLE_GENERO, null, values);
        db.close();
        return res;
    }

    public Genero getGenero(long genero_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String selectQuery = "SELECT  * FROM " + TABLE_GENERO + " WHERE "
                + KEY_ID + " = " + genero_id;

        Cursor c = db.rawQuery(selectQuery, null);
        Genero gn = null;
        if (c != null) {
            c.moveToFirst();
            gn = setDatos(c);
        }
        db.close();
        return gn;
    }

    public List<Genero> getAllGeneros() {
        SQLiteDatabase db = this.getReadableDatabase();
        List<Genero> generos = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + TABLE_GENERO;

        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Genero gn = setDatos(c);

                // adding to tags list
                generos.add(gn);
            } while (c.moveToNext());
        }
        db.close();
        return generos;
    }

    public int countAllGeneros() {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT  count(*) count FROM " + TABLE_GENERO;
        Cursor c = db.rawQuery(selectQuery, null);
        if (c.moveToFirst()) {
            int count = c.getInt(c.getColumnIndex("count"));
            db.close();
            return count;
        }
        db.close();
        return 0;
    }

    public int updateGenero(Genero genero) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = setValues(genero);

        // updating row
        int res = db.update(TABLE_GENERO, values, KEY_ID + " = ?",
                new String[]{String.valueOf(genero.getId())});
        db.close();
        return res;
    }

    private Genero setDatos(Cursor c) {
        Genero gn = new Genero(this.context);
        gn.setId(c.getLong((c.getColumnIndex(KEY_ID))));
        gn.setFecha(c.getString(c.getColumnIndex(KEY_FECHA)));
        gn.setNombre(c.getString(c.getColumnIndex(KEY_NOMBRE)));
        gn.setFamilia_id(c.getLong(c.getColumnIndex(KEY_FAMILIA_ID)));
        return gn;
    }

    private ContentValues setValues(Genero genero, boolean fecha) {
        ContentValues values = new ContentValues();
        if (fecha) {
            values.put(KEY_FECHA, getDateTime());
        }
        values.put(KEY_NOMBRE, genero.getNombre());
        values.put(KEY_NOMBRE_NORM, genero.getNombreNorm());
        if (genero.getFamilia_id() != null) {
            values.put(KEY_FAMILIA_ID, genero.getFamilia_id());
        }
        return values;
    }

    private ContentValues setValues(Genero genero) {
        return setValues(genero, false);
    }
}
