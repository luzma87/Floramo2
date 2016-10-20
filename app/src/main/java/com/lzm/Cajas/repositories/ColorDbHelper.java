package com.lzm.Cajas.repositories;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.lzm.Cajas.db.DbHelper;
import com.lzm.Cajas.models.Color;

import java.util.ArrayList;

public class ColorDbHelper extends DbHelper {

    public static final String KEY_NOMBRE = "nombre";

    public static final String[] KEYS_COLOR = {KEY_NOMBRE};

    public ColorDbHelper(Context context) {
        super(context);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_COLOR);

        onCreate(db);
    }

    public Color getColor(long color_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String selectQuery = "SELECT  * FROM " + TABLE_COLOR + " WHERE "
                + KEY_ID + " = " + color_id;

        Cursor c = db.rawQuery(selectQuery, null);
        Color cl = null;
        if (c.getCount() > 0) {
            c.moveToFirst();
            cl = setDatos(c);
        }
        db.close();
        return cl;
    }

    public ArrayList<Color> getAllColores() {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Color> colores = new ArrayList<Color>();
        String selectQuery = "SELECT  * FROM " + TABLE_COLOR;

        Cursor c = db.rawQuery(selectQuery, null);

        if (c.moveToFirst()) {
            do {
                Color cl = setDatos(c);
                colores.add(cl);
            } while (c.moveToNext());
        }
        db.close();
        return colores;
    }

    public int countAllColores() {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT  count(*) count FROM " + TABLE_COLOR;
        Cursor c = db.rawQuery(selectQuery, null);
        if (c.moveToFirst()) {
            int count = c.getInt(c.getColumnIndex("count"));
            db.close();
            return count;
        }
        db.close();
        return 0;
    }

    private Color setDatos(Cursor c) {
        Color cl = new Color(this.context);
        cl.setId(c.getLong((c.getColumnIndex(KEY_ID))));
        cl.setFecha(c.getString(c.getColumnIndex(KEY_FECHA)));
        cl.setNombre(c.getString(c.getColumnIndex(KEY_NOMBRE)));
        return cl;
    }
}

