package com.lzm.Cajas.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;

public class EspecieDbHelper extends DbHelper {

    public static final String KEY_NOMBRE_COMUN = "nombre_comun";
    public static final String KEY_NOMBRE_COMUN_NORM = "nombre_comun_norm";
    public static final String KEY_NOMBRE = "nombre";
    public static final String KEY_NOMBRE_NORM = "nombre_norm";
    public static final String KEY_GENERO_ID = "genero_id";
    public static final String KEY_COLOR1_ID = "color1_id";
    public static final String KEY_COLOR2_ID = "color2_id";
    public static final String KEY_FORMA_VIDA1_ID = "forma_vida1_id";
    public static final String KEY_FORMA_VIDA2_ID = "forma_vida2_id";
    public static final String KEY_ID_TROPICOS = "id_tropicos";

    public static final String KEY_DESCRIPCION_ES = "descripcion_es";
    public static final String KEY_DESCRIPCION_EN = "descripcion_en";
    public static final String KEY_AUTOR = "autor";

    public static final String[] KEYS_ESPECIE = {KEY_NOMBRE_COMUN, KEY_NOMBRE_COMUN_NORM, KEY_NOMBRE, KEY_NOMBRE_NORM,
            KEY_GENERO_ID, KEY_COLOR1_ID, KEY_COLOR2_ID, KEY_FORMA_VIDA1_ID, KEY_FORMA_VIDA2_ID, KEY_ID_TROPICOS,
            KEY_DESCRIPCION_ES, KEY_DESCRIPCION_EN, KEY_AUTOR};

    public EspecieDbHelper(Context context) {
        super(context);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // on upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ESPECIE);

        // create new tables
        onCreate(db);
    }

    public Especie getEspecie(long especie_id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT  * FROM " + TABLE_ESPECIE + " WHERE "
                + KEY_ID + " = " + especie_id;

        Cursor c = db.rawQuery(selectQuery, null);
        Especie especie = null;
        if (c.getCount() > 0) {
            c.moveToFirst();
            especie = setDatos(c);
        }
        db.close();
        return especie;
    }

    public Especie getDatosEspecie(long especie_id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT " +
                "    e.id id," +
                "    e.nombre especie," +
                "    e.id_tropicos tropicos," +
                "    e.descripcion_es desc_es," +
                "    e.descripcion_en desc_en," +
                "    e.autor autor," +
                "    g.nombre genero," +
                "    f.nombre familia," +
                "    c1.nombre color1," +
                "    c2.nombre color2," +
                "    f1.nombre forma_vida1," +
                "    f2.nombre forma_vida2" +
                " FROM especies e" +
                " INNER JOIN generos g on e.genero_id = g.id" +
                " INNER JOIN familias f on g.familia_id = f.id" +
                " INNER JOIN colores c1 on e.color1_id = c1.id" +
                " OUTER LEFT JOIN colores c2 on e.color2_id = c2.id" +
                " INNER JOIN formas_vida f1 on e.forma_vida1_id = f1.id" +
                " OUTER LEFT JOIN formas_vida f2 on e.forma_vida2_id = f2.id" +
                " WHERE e.id = \"" + especie_id + "\"";

        Cursor c = db.rawQuery(selectQuery, null);
        Especie es = new Especie(context);
        if (c.getCount() > 0) {
            c.moveToFirst();
            es = setDatos2(c);
        }
        db.close();
        return es;
    }

    public List<Especie> getAllEspecies() {
        SQLiteDatabase db = this.getReadableDatabase();
        List<Especie> todos = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + TABLE_ESPECIE;

        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Especie es = setDatos(c);
                // adding to especie list
                todos.add(es);
            } while (c.moveToNext());
        }
        db.close();
        return todos;
    }

    public int countAllEspecies() {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT  count(*) count FROM " + TABLE_ESPECIE;
        Cursor c = db.rawQuery(selectQuery, null);
        if (c.moveToFirst()) {
            int count = c.getInt(c.getColumnIndex("count"));
            db.close();
            return count;
        }
        db.close();
        return 0;
    }

    public List<Especie> getBusqueda(List<Long> formasVida, List<Long> colores, String nombre, String andOr, String sort, String order) {
        SQLiteDatabase db = this.getReadableDatabase();
        List<Especie> especies = new ArrayList<>();
        String sql;

        String select = "SELECT " +
                "    e.id id," +
                "    e.nombre especie," +
                "    e.id_tropicos tropicos," +
                "    e.descripcion_es desc_es," +
                "    e.descripcion_en desc_en," +
                "    e.autor autor," +
                "    g.nombre genero," +
                "    f.nombre familia," +
                "    c1.nombre color1," +
                "    c2.nombre color2," +
                "    f1.nombre forma_vida1," +
                "    f2.nombre forma_vida2";
        String from = " FROM " + TABLE_ESPECIE + " e";
        String joins = "";
        String where = "";
        String groupBy = " GROUP BY e." + KEY_ID;

        String colSort = "";
        String colOrder = "";

        String orderBy = "";

        if (order.equalsIgnoreCase("a")) { //asc
            colOrder = "ASC";
        } else if (order.equalsIgnoreCase("d")) { //desc
            colOrder = "DESC";
        }

        if (sort.equalsIgnoreCase("f")) { //familia
            colSort = "f.nombre";
        } else if (sort.equalsIgnoreCase("n")) { //nombre cientifico
            colSort = "g.nombre";
        }

        if (!colSort.equals("")) {
            orderBy = " ORDER BY " + colSort + " " + colOrder;
        }

        if (colores.size() > 0) {
            joins += " LEFT JOIN " + TABLE_COLOR + " c1 ON e." + KEY_COLOR1_ID + " = c1." + KEY_ID;
            joins += " LEFT JOIN " + TABLE_COLOR + " c2 ON e." + KEY_COLOR2_ID + " = c2." + KEY_ID;

            if (where.equals("")) {
                where += " WHERE ";
            } else {
                where += " " + andOr + " ";
            }
            String whereColor = "";
            for (Long color : colores) {
                if (!whereColor.equals("")) {
                    whereColor += " " + andOr + " ";
                }
                whereColor += " (c1." + ColorDbHelper.KEY_ID + " = '" + color + "'";
                whereColor += " OR c2." + ColorDbHelper.KEY_ID + " = '" + color + "')";
            }
            where += whereColor;
        } else {
            joins += " INNER JOIN colores c1 on e.color1_id = c1.id" +
                    " OUTER LEFT JOIN colores c2 on e.color2_id = c2.id";
        }
        if (formasVida.size() > 0) {
            joins += " LEFT JOIN " + TABLE_FORMA_VIDA + " f1 ON e." + KEY_FORMA_VIDA1_ID + " = f1." + KEY_ID;
            joins += " LEFT JOIN " + TABLE_FORMA_VIDA + " f2 ON e." + KEY_FORMA_VIDA2_ID + " = f2." + KEY_ID;

            if (where.equals("")) {
                where += " WHERE ";
            } else {
                where += " " + andOr + " ";
            }
            String whereFv = "";
            for (Long formaVida : formasVida) {
                if (!whereFv.equals("")) {
                    whereFv += " " + andOr + " ";
                }
                whereFv += " (f1." + FormaVidaDbHelper.KEY_ID + " = '" + formaVida + "'";
                whereFv += " OR f2." + FormaVidaDbHelper.KEY_ID + " = '" + formaVida + "')";
            }
            where += whereFv;
        } else {
            joins += " INNER JOIN formas_vida f1 on e.forma_vida1_id = f1.id" +
                    " OUTER LEFT JOIN formas_vida f2 on e.forma_vida2_id = f2.id";
        }

        joins += " LEFT JOIN " + TABLE_GENERO + " g ON e." + KEY_GENERO_ID + " = g." + KEY_ID;
        joins += " LEFT JOIN " + TABLE_FAMILIA + " f ON g." + GeneroDbHelper.KEY_FAMILIA_ID + " = f." + KEY_ID;

        if (!nombre.equals("")) {
            if (where.equals("")) {
                where += " WHERE ";
            } else {
                where += " " + andOr + " ";
            }
            where += "(";
            where += "LOWER(e." + KEY_NOMBRE_NORM + ") LIKE '%" + Normalizer.normalize(nombre, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "").toLowerCase() + "%' ";
            where += " OR ";
            where += "LOWER(g." + GeneroDbHelper.KEY_NOMBRE_NORM + ") LIKE '%" + Normalizer.normalize(nombre, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "").toLowerCase() + "%' ";
            where += " OR ";
            where += "LOWER(f." + FamiliaDbHelper.KEY_NOMBRE_NORM + ") LIKE '%" + Normalizer.normalize(nombre, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "").toLowerCase() + "%' ";
            where += ")";
        }

        sql = select + from + joins + where + groupBy + orderBy;

//        System.out.println(sql);

        Cursor c = db.rawQuery(sql, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Especie es = setDatos2(c);
                especies.add(es);
            } while (c.moveToNext());
        }
        db.close();
        return especies;
    }

    private Especie setDatos(Cursor c) {
        Especie es = new Especie(this.context);
        es.setId(c.getLong((c.getColumnIndex(KEY_ID))));
        es.setNombreComun((c.getString(c.getColumnIndex(KEY_NOMBRE_COMUN))));
        es.setNombre(c.getString(c.getColumnIndex(KEY_NOMBRE)));
        es.setFecha(c.getString(c.getColumnIndex(KEY_FECHA)));
        es.setGenero_id(c.getLong(c.getColumnIndex(KEY_GENERO_ID)));
        es.setColor1_id(c.getLong(c.getColumnIndex(KEY_COLOR1_ID)));
        es.setColor2_id(c.getLong(c.getColumnIndex(KEY_COLOR2_ID)));
        es.setFormaVida1_id(c.getLong(c.getColumnIndex(KEY_FORMA_VIDA1_ID)));
        es.setFormaVida2_id(c.getLong(c.getColumnIndex(KEY_FORMA_VIDA2_ID)));
        es.setIdTropicos(c.getLong(c.getColumnIndex(KEY_ID_TROPICOS)));
        es.setDescripcionEn(c.getString(c.getColumnIndex(KEY_DESCRIPCION_EN)));
        es.setDescripcionEs(c.getString(c.getColumnIndex(KEY_DESCRIPCION_ES)));
        es.setAutor(c.getString(c.getColumnIndex(KEY_AUTOR)));
        return es;
    }

    private Especie setDatos2(Cursor c) {
        Especie es = new Especie(this.context);
        es.setId(c.getLong((c.getColumnIndex(KEY_ID))));
        es.setNombre(c.getString(c.getColumnIndex("especie")));
        es.setGenero(c.getString(c.getColumnIndex("genero")));
        es.setFamilia(c.getString(c.getColumnIndex("familia")));
        es.setColor1(c.getString(c.getColumnIndex("color1")));
        es.setColor2(c.getString(c.getColumnIndex("color2")));
        es.setFormaVida1(c.getString(c.getColumnIndex("forma_vida1")));
        es.setFormaVida2(c.getString(c.getColumnIndex("forma_vida2")));
        es.setIdTropicos(c.getLong(c.getColumnIndex("tropicos")));
        es.setDescripcionEn(c.getString(c.getColumnIndex("desc_en")));
        es.setDescripcionEs(c.getString(c.getColumnIndex("desc_es")));
        es.setAutor(c.getString(c.getColumnIndex("autor")));
        return es;
    }
}
