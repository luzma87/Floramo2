package com.lzm.Cajas.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

/**
 * Created by DELL on 27/07/2014.
 */
public class FormaVidaDbHelper extends DbHelper {

    public static final String KEY_NOMBRE = "nombre";

    public static final String[] KEYS_FORMA_VIDA = {KEY_NOMBRE};

    public FormaVidaDbHelper(Context context) {
        super(context);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // on upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FORMA_VIDA);

        // create new tables
        onCreate(db);
    }

    public FormaVida getFormaVida(long formavida_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String selectQuery = "SELECT  * FROM " + TABLE_FORMA_VIDA + " WHERE "
                + KEY_ID + " = " + formavida_id;

        Cursor c = db.rawQuery(selectQuery, null);
        FormaVida cl = null;
        if (c.getCount() > 0) {
            c.moveToFirst();
            cl = setDatos(c);
        }
        db.close();
        return cl;
    }

    public ArrayList<FormaVida> getAllFormasVida() {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<FormaVida> formavidaes = new ArrayList<FormaVida>();
        String selectQuery = "SELECT  * FROM " + TABLE_FORMA_VIDA;

        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                FormaVida cl = setDatos(c);

                // adding to tags list
                formavidaes.add(cl);
            } while (c.moveToNext());
        }
        db.close();
        return formavidaes;
    }

    public int countAllFormasVida() {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT  count(*) count FROM " + TABLE_FORMA_VIDA;
        Cursor c = db.rawQuery(selectQuery, null);
        if (c.moveToFirst()) {
            int count = c.getInt(c.getColumnIndex("count"));
            db.close();
            return count;
        }
        db.close();
        return 0;
    }

    private FormaVida setDatos(Cursor c) {
        FormaVida cl = new FormaVida(this.context);
        cl.setId(c.getLong((c.getColumnIndex(KEY_ID))));
        cl.setFecha(c.getString(c.getColumnIndex(KEY_FECHA)));
        cl.setNombre(c.getString(c.getColumnIndex(KEY_NOMBRE)));
        return cl;
    }
}

