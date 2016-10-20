package com.lzm.Cajas.repositories;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.lzm.Cajas.db.DbHelper;
import com.lzm.Cajas.models.Familia;

import java.util.ArrayList;
import java.util.List;

public class FamiliaDbHelper extends DbHelper {

    public static final String KEY_NOMBRE = "nombre";
    public static final String KEY_NOMBRE_NORM = "nombre_norm";

    public static final String[] KEYS_FAMILIA = {KEY_NOMBRE, KEY_NOMBRE_NORM};

    public FamiliaDbHelper(Context context) {
        super(context);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // on upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FAMILIA);

        // create new tables
        onCreate(db);
    }

    public Familia getFamilia(long familia_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String selectQuery = "SELECT  * FROM " + TABLE_FAMILIA + " WHERE "
                + KEY_ID + " = " + familia_id;

        Cursor c = db.rawQuery(selectQuery, null);
        Familia fm = null;
        if (c.getCount() > 0) {
            c.moveToFirst();
            fm = setDatos(c);
        }
        db.close();
        return fm;
    }

    public List<Familia> getAllFamilias() {
        SQLiteDatabase db = this.getReadableDatabase();
        List<Familia> familias = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + TABLE_FAMILIA +
                " ORDER BY " + KEY_NOMBRE;

        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Familia fm = setDatos(c);

                // adding to tags list
                familias.add(fm);
            } while (c.moveToNext());
        }
        db.close();
        return familias;
    }

    public int countAllFamilias() {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT  count(*) count FROM " + TABLE_FAMILIA;
        Cursor c = db.rawQuery(selectQuery, null);
        if (c.moveToFirst()) {
            int count = c.getInt(c.getColumnIndex("count"));
            db.close();
            return count;
        }
        db.close();
        return 0;
    }

    private Familia setDatos(Cursor c) {
        Familia fm = new Familia(this.context);
        fm.setId(c.getLong((c.getColumnIndex(KEY_ID))));
        fm.setFecha(c.getString(c.getColumnIndex(KEY_FECHA)));
        fm.setNombre(c.getString(c.getColumnIndex(KEY_NOMBRE)));
        return fm;
    }
}
