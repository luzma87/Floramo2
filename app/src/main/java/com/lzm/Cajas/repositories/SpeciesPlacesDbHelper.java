package com.lzm.Cajas.repositories;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.lzm.Cajas.db.DbHelper;
import com.lzm.Cajas.models.Color;

import java.util.ArrayList;

public class SpeciesPlacesDbHelper extends DbHelper {

    public static final String KEY_SPECIES_ID = "species_id";
    public static final String KEY_PLACE_ID = "place_id";

    public static final String[] KEYS_SPECIES_PLACES = {KEY_SPECIES_ID, KEY_PLACE_ID};

    public SpeciesPlacesDbHelper(Context context) {
        super(context);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SPECIES_PLACES);
        onCreate(db);
    }
}

