package com.lzm.Cajas.repositories;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.lzm.Cajas.db.DbHelper;
import com.lzm.Cajas.models.Especie;
import com.lzm.Cajas.models.Lugar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.lzm.Cajas.repositories.SpeciesPlacesDbHelper.KEY_PLACE_ID;
import static com.lzm.Cajas.repositories.SpeciesPlacesDbHelper.KEY_SPECIES_ID;

public class LugarDbHelper extends DbHelper {

    public static final String KEY_NOMBRE = "nombre";
    public static final String KEY_NOMBRE_NORM = "nombre_norm";
    public static final String KEY_PATH = "path";
    public static final String KEY_ICON = "icon";

    public static final String[] KEYS_LUGAR = {KEY_NOMBRE, KEY_NOMBRE_NORM, KEY_PATH};

    public LugarDbHelper(Context context) {
        super(context);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // on upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LUGAR);

        // create new tables
        onCreate(db);
    }

    public Lugar getLugar(long lugar_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String selectQuery = "SELECT  * FROM " + TABLE_LUGAR + " WHERE "
                + KEY_ID + " = " + lugar_id;

        Cursor c = db.rawQuery(selectQuery, null);
        Lugar cl = null;
        if (c.getCount() > 0) {
            c.moveToFirst();
            cl = setDatos(c);
        }
        db.close();
        return cl;
    }

    public ArrayList<Lugar> getAllLugares() {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Lugar> lugares = new ArrayList<Lugar>();
        String selectQuery = "SELECT  * FROM " + TABLE_LUGAR;

        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Lugar cl = setDatos(c);

                // adding to tags list
                lugares.add(cl);
            } while (c.moveToNext());
        }
        db.close();
        return lugares;
    }

    public List<Lugar> getAllFotosByEspecie(Especie especie) {
        return getAllLugaresByEspecieId(especie.getId());
    }

    private List<Lugar> getAllLugaresByEspecieId(long especieId) {
        SQLiteDatabase db = this.getReadableDatabase();
        List<Lugar> fotos = new ArrayList<>();

        String selectQuery = "SELECT " +
                "l." + KEY_ID + " " + KEY_ID + "," +
                "l." + KEY_NOMBRE + " " + KEY_NOMBRE +
                " FROM " + TABLE_SPECIES_PLACES + " s " +
                " INNER JOIN " + TABLE_LUGAR + " l ON s." + KEY_PLACE_ID + " = l." + KEY_ID +
                " WHERE s." + KEY_SPECIES_ID + " = " + especieId;

        Cursor c = db.rawQuery(selectQuery, null);

//        c = db.rawQuery("select * from " + TABLE_SPECIES_PLACES + " s WHERE s." + KEY_SPECIES_ID + " = " + especieId, null);

        if (c.moveToFirst()) {
            do {
                Lugar f = setDatos(c);
                fotos.add(f);
            } while (c.moveToNext());
        }
        db.close();
        return fotos;
    }

    private Lugar setDatos(Cursor c) {
        Lugar cl = new Lugar(this.context);
        cl.setId(c.getLong((c.getColumnIndex(KEY_ID))));
        cl.setNombre(c.getString(c.getColumnIndex(KEY_NOMBRE)));
        return cl;
    }
}

