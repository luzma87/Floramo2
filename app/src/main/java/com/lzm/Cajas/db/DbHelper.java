package com.lzm.Cajas.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by DELL on 26/07/2014.
 */
public class DbHelper extends SQLiteOpenHelper {

    // Database Version
    public static final int DATABASE_VERSION = 26;

    // Database Name
//    private static String DB_PATH = "/data/data/com.tmm.android.chuck/databases/";
//    public static String DB_PATH = Environment.getExternalStorageDirectory().getPath() + "/Floramo/db/";
    public static String DB_PATH = "";
    private static final String DATABASE_NAME = "floramoDb.db";

    // Table Names
    protected static final String TABLE_COLOR = "colores";
    protected static final String TABLE_LUGAR = "lugares";
    protected static final String TABLE_FOTO = "fotos";
    protected static final String TABLE_FAMILIA = "familias";
    protected static final String TABLE_GENERO = "generos";
    protected static final String TABLE_ESPECIE = "especies";
    protected static final String TABLE_FORMA_VIDA = "formas_vida";
    protected static final String TABLE_COORDENADA = "coordenadas";

    // Common column names
    protected static final String KEY_ID = "id";
    protected static final String KEY_FECHA = "fecha";
    private static final String[] KEYS_COMMON = {KEY_ID, KEY_FECHA};

    protected Context context;

    public DbHelper(Context context) {
        super(context, DB_PATH + DATABASE_NAME, null, DATABASE_VERSION);
        new File(DB_PATH).mkdirs();
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        createTable(db, TABLE_COLOR, KEYS_COMMON, ColorDbHelper.KEYS_COLOR);
        createTable(db, TABLE_ESPECIE, KEYS_COMMON, EspecieDbHelper.KEYS_ESPECIE);
        createTable(db, TABLE_FAMILIA, KEYS_COMMON, FamiliaDbHelper.KEYS_FAMILIA);
        createTable(db, TABLE_FOTO, KEYS_COMMON, FotoDbHelper.KEYS_FOTO);
        createTable(db, TABLE_GENERO, KEYS_COMMON, GeneroDbHelper.KEYS_GENERO);
        createTable(db, TABLE_LUGAR, KEYS_COMMON, LugarDbHelper.KEYS_LUGAR);
        createTable(db, TABLE_FORMA_VIDA, KEYS_COMMON, FormaVidaDbHelper.KEYS_FORMA_VIDA);
        createTable(db, TABLE_COORDENADA, KEYS_COMMON, CoordenadaDbHelper.KEYS_COORDENADA);

        DbInserter dbInserter = new DbInserter(db);
        dbInserter.insertDb();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 14) {
            updateWhenVersionLessThan14(db);
        }
        if (oldVersion < 16) {
            updateWhenVersionLessThan16(db);
        }
        if (oldVersion < 24) {
            updateWhenVersionLessThan24(db);
        }
        if (oldVersion < 25) {
            updateWhenVersionLessThan25(db);
        }
    }

    private void updateWhenVersionLessThan14(SQLiteDatabase db) {
        upgradeTable(db, TABLE_FAMILIA, KEYS_COMMON, FamiliaDbHelper.KEYS_FAMILIA);
        upgradeTable(db, TABLE_GENERO, KEYS_COMMON, GeneroDbHelper.KEYS_GENERO);
        upgradeTable(db, TABLE_ESPECIE, KEYS_COMMON, EspecieDbHelper.KEYS_ESPECIE);

        DbInserter dbInserter = new DbInserter(db);
        dbInserter.insertFamilias();
        dbInserter.insertGeneros();
        dbInserter.insertEspecies();
    }

    private void updateWhenVersionLessThan25(SQLiteDatabase db) {
        //Jamesonia goudotii
        db.execSQL("UPDATE " + TABLE_ESPECIE + " SET " + EspecieDbHelper.KEY_COLOR1_ID + " = 128 WHERE " + EspecieDbHelper.KEY_ID + " = 184");
        db.execSQL("UPDATE " + TABLE_ESPECIE + " SET " + EspecieDbHelper.KEY_COLOR2_ID + " = null WHERE " + EspecieDbHelper.KEY_ID + " = 184");
    }

    private void updateWhenVersionLessThan24(SQLiteDatabase db) {
        //Diplostephium glandulosum, color de flores solo Lila (parece que esta amarillo y es incorrecto). 47
        db.execSQL("UPDATE " + TABLE_ESPECIE + " SET " + EspecieDbHelper.KEY_COLOR1_ID + " = 47 WHERE " + EspecieDbHelper.KEY_ID + " = 89");

        // Crear un color 'cone' para asignar a las siguientes especies
        db.execSQL("INSERT INTO colores(id, nombre) VALUES (\"128\", \"cone\");");

        //Huperzia
        db.execSQL("UPDATE " + TABLE_ESPECIE + " SET " + EspecieDbHelper.KEY_COLOR1_ID + " = 128 WHERE " + EspecieDbHelper.KEY_ID + " = 168");
        db.execSQL("UPDATE " + TABLE_ESPECIE + " SET " + EspecieDbHelper.KEY_COLOR2_ID + " = null WHERE " + EspecieDbHelper.KEY_ID + " = 168");

        //Isoetes
        db.execSQL("UPDATE " + TABLE_ESPECIE + " SET " + EspecieDbHelper.KEY_COLOR1_ID + " = 128 WHERE " + EspecieDbHelper.KEY_ID + " = 180");
        db.execSQL("UPDATE " + TABLE_ESPECIE + " SET " + EspecieDbHelper.KEY_COLOR2_ID + " = null WHERE " + EspecieDbHelper.KEY_ID + " = 180");

        //Ephedra
        db.execSQL("UPDATE " + TABLE_ESPECIE + " SET " + EspecieDbHelper.KEY_COLOR1_ID + " = 128 WHERE " + EspecieDbHelper.KEY_ID + " = 103");
        db.execSQL("UPDATE " + TABLE_ESPECIE + " SET " + EspecieDbHelper.KEY_COLOR2_ID + " = null WHERE " + EspecieDbHelper.KEY_ID + " = 103");

        //Equisetum
        db.execSQL("UPDATE " + TABLE_ESPECIE + " SET " + EspecieDbHelper.KEY_COLOR1_ID + " = 128 WHERE " + EspecieDbHelper.KEY_ID + " = 111");
        db.execSQL("UPDATE " + TABLE_ESPECIE + " SET " + EspecieDbHelper.KEY_COLOR2_ID + " = null WHERE " + EspecieDbHelper.KEY_ID + " = 111");
    }

    private void updateWhenVersionLessThan16(SQLiteDatabase db) {
        // corregir typo
        db.execSQL("UPDATE " + TABLE_FORMA_VIDA + " SET " + FormaVidaDbHelper.KEY_NOMBRE + " = \"aquatic\" " +
                "WHERE " + FormaVidaDbHelper.KEY_NOMBRE + " = \"acquatic\"");
        //Scirpus rigida add life form herb / aquatic
        db.execSQL("UPDATE " + TABLE_ESPECIE + " SET " + EspecieDbHelper.KEY_FORMA_VIDA2_ID + " = 5 WHERE " + EspecieDbHelper.KEY_ID + " = 308");
        //Lachemilla orbiculara delete flower pink (leave only green)
        db.execSQL("UPDATE " + TABLE_ESPECIE + " SET " + EspecieDbHelper.KEY_COLOR2_ID + " = null WHERE " + EspecieDbHelper.KEY_ID + " = 189");
        //Epidendrum tenicaule add color green 15
        db.execSQL("UPDATE " + TABLE_ESPECIE + " SET " + EspecieDbHelper.KEY_COLOR2_ID + " = 15 WHERE " + EspecieDbHelper.KEY_ID + " = 107");
        //Werneria nubigena add color yellow 36
        db.execSQL("UPDATE " + TABLE_ESPECIE + " SET " + EspecieDbHelper.KEY_COLOR2_ID + " = 36 WHERE " + EspecieDbHelper.KEY_ID + " = 344");
        //Diplostephium ericoides add color white 3
        db.execSQL("UPDATE " + TABLE_ESPECIE + " SET " + EspecieDbHelper.KEY_COLOR2_ID + " = 3 WHERE " + EspecieDbHelper.KEY_ID + " = 87");
    }

    public void upgradeTable(SQLiteDatabase db, String tableName, String[] common, String[] columnNames) {
        db.execSQL("DROP TABLE " + tableName);
        db.execSQL(createTableSql(tableName, common, columnNames));
    }

    public void createTable(SQLiteDatabase db, String tableName, String[] common, String[] columnNames) {
        db.execSQL(createTableSql(tableName, common, columnNames));
    }

    /**
     * @param tableName:   el nombre de la tabla
     * @param common:      los campos comunes a las tablas: en pos 0 el id i en pos 1 la fecha
     * @param columnNames: los campos
     * @return el sql
     */
    public static String createTableSql(String tableName, String[] common, String[] columnNames) {
        String sql = "CREATE TABLE " + tableName + " (" +
                common[0] + " INTEGER PRIMARY KEY," +
                common[1] + " DATETIME";
        for (String c : columnNames) {
            if (c.startsWith("lat") || c.startsWith("long")) {
                sql += ", " + c + " REAL";
            } else {
                sql += ", " + c + " TEXT";
            }
        }
        sql += ")";
        return sql;
    }

    protected static String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }
}
