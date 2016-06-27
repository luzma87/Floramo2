package com.lzm.Cajas.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DbHelper extends SQLiteOpenHelper {

    // Database Version
    public static final int DATABASE_VERSION = 27;

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
        updateWhenNew(db);
    }

    private void updateWhenNew(SQLiteDatabase db) {
        updateWhenVersionLessThan16(db);
        updateWhenVersionLessThan24(db);
        updateWhenVersionLessThan25(db);
        updateWhenVersionLessThan27(db);
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
        if (oldVersion < 27) {
            updateWhenVersionLessThan27(db);
        }
    }

    private void updateWhenVersionLessThan27(SQLiteDatabase db) {
        db.execSQL("UPDATE " + TABLE_ESPECIE + " SET " +
                EspecieDbHelper.KEY_DESCRIPCION_ES + " = \"Plantas dispuestas en almohadillas muy compactas y grandes hasta de 2 m de diámetro. Hojas amontonadas al final de las ramas, hasta 1 cm de largo, muy duras y brillantes, tienen el ápice profundamente partido en 3 a 5 lóbulos espinosos. Inflorescencia pequeña, mide menos de 1 cm y tiene hasta 20 flores. Flores diminutas, hasta 3 mm de largo, color verde amarillento. \n" +
                "Distribución: Sur de Colombia a Ecuador. En el Cajas es una especie muy común en varios ambientes.\"" +
                ", " + EspecieDbHelper.KEY_DESCRIPCION_EN + " = \"Cushion-forming plants that form compact mounds up to 2 m in diameter. Leaves clustered at the tip of the branches, up to 1 cm long, very hard and glossy; apex is deeply divided into 3 to 5 spiny lobes. Inflorescence small, less than 1 cm long and with up to 20 flowers. Flowers minute, up to 3 mm long, yellowish green. \n" +
                "Distribution: South of Colombia to Ecuador. In Cajas it is a common species in various environments.\"" +
                "WHERE " + EspecieDbHelper.KEY_ID + " = 21");

        db.execSQL("UPDATE " + TABLE_ESPECIE + " SET " +
                EspecieDbHelper.KEY_DESCRIPCION_ES + " = \"Arbustos hasta 1.5 m de alto. Hojas alternas, hasta 1.2 cm de largo, duras y punzantes. Inflorescencia tiene cabezuelas vistosas hasta de 5 cm de largo, muy compactas, con brácteas punzantes color anaranjado. Flores (20–45) delgadas, color amarillo o anaranjado, de 20 mm de largo. El fruto con una corona blanca. \n" +
                "Distribución: Sur de Colombia al Sur del Perú. En el Cajas crece en el páramo arbustivo. Flores visitadas por colibríes Oreotrochilus chimborazo.\"" +
                ", " + EspecieDbHelper.KEY_DESCRIPCION_EN + " = \"Shrubs up to 1.5 m tall. Leaves alternate, up to 1.2 cm long, stiff, with a spiny tip. Inflorescence of showy, compact heads, up to 5 cm long; spine-tipped bracts orange. Flowers (20–45) slender, yellow or orange, 20 mm long. The fruit has a white crown. \n" +
                "Distribution: South of Colombia to South of Peru. In Cajas it is found in the shrubby páramo. Flowers visited by Oreotrochilus chimborazo hummingbirds.\"" +
                "WHERE " + EspecieDbHelper.KEY_ID + " = 80");

        db.execSQL("UPDATE " + TABLE_ESPECIE + " SET " +
                EspecieDbHelper.KEY_DESCRIPCION_ES + " = \"Hierbas hasta de 30 cm de alto, recubiertas de pelos blancos; tallos rojizos. Hojas opuestas, hasta 1.5 cm de largo, con tintes color rosado; márgenes crenados, enrollados hacia abajo y a veces teñidos de morado. Flores tubulares y hasta 8 mm de largo, color rosado con blanco o violeta. \n" +
                "Distribución: Sur de Colombia y Ecuador. En el Cajas es una especie común que se encuentra en varios ambientes.\"" +
                ", " + EspecieDbHelper.KEY_DESCRIPCION_EN + " = \"Herbs up to 30 cm tall, covered with white hairs; stems reddish. Leaves opposite, up to 1.5 cm long, tinged with pink; crenate margins curled downwards and sometimes tinged with purple. Flowers tubular, up to 8 mm long, pink with white or violet. \n" +
                "Distribution: South of Colombia and Ecuador. In Cajas it is a common species found in various environments.\"" +
                "WHERE " + EspecieDbHelper.KEY_ID + " = 34");

        db.execSQL("UPDATE " + TABLE_ESPECIE + " SET " +
                EspecieDbHelper.KEY_DESCRIPCION_ES + " = \"Hierbas pequeñas hasta 5 cm de alto, a veces forman almohadillas pequeñas. Hojas, opuestas y amontonadas a lo largo del corto tallo, lanceoladas, hasta 8 mm de largo. Flores solitarias, erguidas, hasta 25 mm de largo, color lila o rara vez rosado, con las venas más oscuras. \n" +
                "Distribución: Sur de Colombia y Ecuador. En el Cajas se encuentra en el páramo de almohadillas.\"" +
                ", " + EspecieDbHelper.KEY_DESCRIPCION_EN + " = \"Small herbs up to 5 cm tall, sometimes forming small cushions. Leaves opposite and clustered along the short stem, lanceolate, up to 8 mm long. Flowers solitary, erect, up to 25 mm long, lilac or rarely pink, with darker veins. \n" +
                "Distribution: South of Colombia and Ecuador. In Cajas it is found in the cushion páramo.\"" +
                "WHERE " + EspecieDbHelper.KEY_ID + " = 133");

        db.execSQL("UPDATE " + TABLE_ESPECIE + " SET " +
                EspecieDbHelper.KEY_DESCRIPCION_ES + " = \"Hierbas pequeñas, reclinadas, hasta 10 cm de alto, generalmente en grupos. Hojas opuestas, hasta 1 cm de largo; márgenes crenados. Flores solitarias, colgantes, con forma de tubo acampanado, hasta 25 mm de largo, color rosado oscuro a rosado-rojizo. \n" +
                "Distribución: Sur de Colombia a Bolivia. En el Cajas se encuentra en áreas rocosas principalmente en las partes más altas del páramo.\"" +
                ", " + EspecieDbHelper.KEY_DESCRIPCION_EN + " = \"Small, reclining herbs, up to 10 cm tall, generally in groups. Leaves opposite, up to 1 cm long; margins crenate. Flowers solitary, pendent, with a bell-tubular shape, up to 25 mm long, dark pink to pinkish-red. \n" +
                "Distribution: South of Colombia to Bolivia. In Cajas it is found in rocky areas mainly in the highest parts of the páramo.\"" +
                "WHERE " + EspecieDbHelper.KEY_ID + " = 230");

        db.execSQL("UPDATE " + TABLE_ESPECIE + " SET " +
                EspecieDbHelper.KEY_DESCRIPCION_ES + " = \"Hierbas hasta 25 cm de alto, con pelos glandulares en varias partes. Hojas de dos tipos: las basales dispuestas en una roseta, hasta 15 cm y tienen los márgenes menudamente espinosos, las del tallo hasta 4 cm de largo y tienen pelos glandulares. Inflorescencia en forma de cabezuela hasta de 3 cm de largo. Flores irregulares, presentan una lengüeta vistosa color azul-violeta o blanco-violeta, hasta 25 mm de largo \n" +
                "Distribución: Sur de Colombia al Norte de Argentina. En el Cajas esta especie se encuentra en el páramo de pajonal y en sitios rocosos.\"" +
                ", " + EspecieDbHelper.KEY_DESCRIPCION_EN + " = \"Herbs up to 25 cm tall, covered with glandular hairs on various parts. Leaves of two types: the basal ones in a rosette, up to 15 cm long with finely spiny margins, and the ones on the stem, up to 4 cm long, with glandular hairs. Inflorescence a head, up to 3 cm long. Flowers irregular, strap-shaped, and showy, violet-blue or violet-white, up to 25 mm long. \n" +
                "Distribution: South of Colombia to North of Argentina. In Cajas it is found in the grass páramo and rocky areas.\"" +
                "WHERE " + EspecieDbHelper.KEY_ID + " = 258");

        db.execSQL("UPDATE " + TABLE_ESPECIE + " SET " +
                EspecieDbHelper.KEY_DESCRIPCION_ES + " = \"Son plantas trepadoras con zarcillos filamentosos enrollados. Hojas alternas, hasta 14 cm de largo, tienen tres lóbulos más o menos triangulares; márgenes aserrados. Flores solitarias, colgantes, hasta 10 cm de diámetro, color rosado violeta, con un tubo largo que hasta de 20 cm; pétalos son rosados; las brácteas florales son color verdoso azulado. Fruto carnoso, color amarillento. \n" +
                "Distribución: Desde el Sur de Colombia al Centro del Perú. En el Cajas se encuentra en los bosques de Polylepis.\"" +
                ", " + EspecieDbHelper.KEY_DESCRIPCION_EN + " = \"Climbers with thin curled tendrils. Leaves alternate, up to 14 cm long, with 3 more or less triangular lobes, the margins serrate. Flowers solitary, pendent, up to 10 cm in diameter, violet pink, with a long tube up to 20 cm; petals pink; floral bracts greenish bluish. The fruit is fleshy, yellowish. \n" +
                "Distribution: South of Colombia to Center of Peru. In Cajas it is found in the Polylepis forests.\"" +
                "WHERE " + EspecieDbHelper.KEY_ID + " = 243");

        db.execSQL("UPDATE " + TABLE_ESPECIE + " SET " +
                EspecieDbHelper.KEY_DESCRIPCION_ES + " = \"Hierbas hasta de 20 cm de alto, forman grupos pequeños. Hojas lanceoladas, opuestas, hasta 1 cm de largo, con tintes morados. Inflorescencia con forma de racimos, hasta 10 cm de largo, tiene 3 a 10 flores, el eje es color rojo-morado. Flores hasta 20 mm de largo, acampanadas y colgantes, color violeta pálido a oscuro o azulado, con las venas más oscuras. \n" +
                "Distribución: Sur de Colombia-Ecuador. En el Cajas se encuentra en el páramo de almohadillas.\"" +
                ", " + EspecieDbHelper.KEY_DESCRIPCION_EN + " = \"Herbs up to 20 cm tall, in small groups. Leaves lanceolate, opposite, up to 1 cm long, tinged with purple. Inflorescence a raceme, up to 10 cm long, with 3 to 10 flowers, the axis purple-red. Flowers up to 20 mm long, bell-shaped and pendent, pale to dark violet or bluish, with darker veins. 0\n" +
                "Distribution: South of Colombia and Ecuador. In Cajas it is found in the cushion páramo.\"" +
                "WHERE " + EspecieDbHelper.KEY_ID + " = 141");

        db.execSQL("UPDATE " + TABLE_ESPECIE + " SET " +
                EspecieDbHelper.KEY_DESCRIPCION_ES + " = \"Arbustos hasta de 50 cm de alto, muy ramificados. Hojas opuestas, hasta de 15 mm de largo, con forma de aguja y se las ve amontonadas. Flores solitarias, en la punta de las ramas, hasta 40 mm de diámetro, con 5 pétalos color amarillo intenso; estambres son numerosos, también color amarillo. Frutos cápsulas ovadas, secas, color café. \n" +
                "Distribución: Sur del Ecuador al Norte del Perú. En el Cajas se lo encuentra dentro de los bosques de Polylepis.\"" +
                ", " + EspecieDbHelper.KEY_DESCRIPCION_EN + " = \"Shrubs up to 50 cm tall, profusely branched. Leaves opposite, up to 15 mm long, needle-like and clustered. Flowers solitary, at the top of the branches, up to 40 mm in diameter, with 5 bright yellow petals, the stamens numerous, yellow. Fruits ovate, dry, brown capsules. \n" +
                "Distribution: South of Ecuador to North of Peru. In Cajas it is found in the Polylepis forests.\"" +
                "WHERE " + EspecieDbHelper.KEY_ID + " = 172");

        db.execSQL("UPDATE " + TABLE_ESPECIE + " SET " +
                EspecieDbHelper.KEY_DESCRIPCION_ES + " = \"Hierbas bajas hasta 15 cm de diámetro. Hojas dispuestas en una roseta basal, hasta 10 cm de largo, alargadas y estrechas. Inflorescencias en cabezuelas solitarias a ras del suelo, ca. 7 cm de diámetro. Flores de dos tipos: las externas (± 25) irregulares y presentan una lengüeta hasta de 25 mm de largo, color blanco, las internas (más de 100) cortas, tubulares y con 5 dientes, color amarillo. Frutos con una corona de pelos blancos. \n" +
                "Distribución: Sur de México, Guatemala, Colombia a Bolivia. En el Cajas es común en el páramo de pajonal.\"" +
                ", " + EspecieDbHelper.KEY_DESCRIPCION_EN + " = \"Low herbs, up to 15 cm in diameter. Leaves arranged in a basal rosette, up to 10 cm long, elongate and narrow. Inflorescences solitary heads at ground level, up to 7 cm in diameter. Flowers of two types: the external (± 25) irregular and strap-shaped, up to 25 mm long, white, the internal (more than 100) short, tubular and with 5 teeth, yellow. Fruits with a crown of white hairs. \n" +
                "Distribution: South of Mexico, Guatemala, Colombia to Bolivia. In Cajas it is common in the grass páramo.\"" +
                "WHERE " + EspecieDbHelper.KEY_ID + " = 344");

        db.execSQL("UPDATE " + TABLE_ESPECIE + " SET " +
                EspecieDbHelper.KEY_DESCRIPCION_ES + " = \"Hierbas bajas forman almohadillas pequeñas en lugares húmedos. Hojas dispuestas en rosetas, muy estrechas y hasta 1 cm de largo. Inflorescencia en forma de cabezuela al nivel del suelo, ca. 2 cm de diámetro. Flores de dos tipos: las externas (± 13) irregulares y presentan una lengüeta vistosa de 10 mm de largo, color rosado fuerte, las internas (± 35) tubulares y con 5 dientes, color amarillo oscuro. Frutos con una corona de pelos sedosos, de 8 mm de largo, color blanco. \n" +
                "Distribución: Es endémica del Sur del Ecuador y prácticamente restringida al Cajas en el páramo de almohadillas.\"" +
                ", " + EspecieDbHelper.KEY_DESCRIPCION_EN + " = \"Low herbs, forming small cushions in moist places. Leaves arranged in rosettes narrow, up to 1 cm long. Inflorescence a head at ground level, up to 2 cm in diameter. Flowers of two types: the external (± 13) irregular, showy, strap-shaped, 10 mm long, dark pink, the internal (± 35) tubular with 5 teeth, dark yellow. Fruits with a crown of silky white hairs, up to 8 mm long. \n" +
                "Distribution: Endemic to South of Ecuador and practically restricted to Cajas in the cushion páramo.\"" +
                "WHERE " + EspecieDbHelper.KEY_ID + " = 353");

        db.execSQL("UPDATE " + TABLE_ESPECIE + " SET " +
                EspecieDbHelper.KEY_DESCRIPCION_ES + " = \"Hierba hasta de 30 cm de alto. Hojas dispuestas en una roseta en la base y a veces algunas alternas a lo largo del tallo, hasta 9 cm de largo, divididas hasta el nervio principal en segmentos crenados, color morado. Inflorescencia hasta de 20 cm de largo, tiene numerosas flores. Flores tubulares, recurvadas y hasta 2 cm de largo, color rosado fuerte, con uno de los pétalos formando un pico color más oscuro. \n" +
                "Distribución: Colombia y Sur del Ecuador. En el Cajas se encuentra en el páramo de pajonal y ocasionalmente en el de almohadillas.\"" +
                ", " + EspecieDbHelper.KEY_DESCRIPCION_EN + " = \"Herbs up to 30 cm tall. Leaves arranged in a basal rosette, sometimes with a few alternate along the stem, up to 9 cm long, deeply divided towards the principal vein into crenate segments, purple. Inflorescence up to 20 cm long, with numerous flowers. Flowers tubular, curved, up to 2 cm long, dark pink, with one of the petals forming a darker beak. \n" +
                "Distribution: Colombia and South of Ecuador. In Cajas it is found in the grass páramo and occasionally in the cushion páramo.\"" +
                "WHERE " + EspecieDbHelper.KEY_ID + " = 246");

        db.execSQL("UPDATE " + TABLE_ESPECIE + " SET " +
                EspecieDbHelper.KEY_DESCRIPCION_ES + " = \"Arbustos hasta de 1 m de alto, densamente ramificados. Hojas opuestas, lanceoladas, hasta de 0.6 cm de largo, brillantes en la cara superior; márgenes engrosados en la cara inferior. Las inflorescencias tienen 1 a 3 flores. Flores hasta 8 mm de largo, blancas teñidas de lila, morado o rosado, tienen un tubo corto con 4 lóbulos triangulares, recubiertos de diminutos pelos blancos. \n" +
                "Distribución: Sur del Ecuador al Norte del Perú. En el Cajas se encuentra en el páramo de pajonal.\"" +
                ", " + EspecieDbHelper.KEY_DESCRIPCION_EN + " = \"Shrubs up to 1 m tall, densely branched. Leaves opposite, lanceolate, up to 0.6 cm long, glossy above; margins thickened below. Inflorescences have 1 to 3 flowers. Flowers 8 mm long, white tinged with lilac, purple or pink; they have a short tube and 4 triangular lobes covered with minute white hairs. \n" +
                "Distribution: South of Ecuador to North of Peru. In Cajas it is found in the grass páramo.\"" +
                "WHERE " + EspecieDbHelper.KEY_ID + " = 11");

        db.execSQL("UPDATE " + TABLE_ESPECIE + " SET " +
                EspecieDbHelper.KEY_DESCRIPCION_ES + " = \"Hierbas hasta 30 cm de alto, el follaje está a menudo teñido de morado. Hojas alternas, hasta 2 cm de largo, divididas en segmentos. Inflorescencia en forma de racimos hasta de 10 cm de largo, tiene hojas con apariencia de pétalos (brácteas) color rojo brillante que sostienen a cada flor. Flores tubulares, algo recurvadas, hasta 20 mm de largo, color verde claro, con pelos diminutos. \n" +
                "Distribución: Es endémica del Sur del Ecuador. En el Cajas se encuentra en varios ambientes. Las partes rojas más vistosas de la planta son las brácteas y no las flores.\"" +
                ", " + EspecieDbHelper.KEY_DESCRIPCION_EN + " = \"Herbs up to 30 cm long; foliage is often tinged with purple. Leaves alternate, up to 2 cm long, divided in segments. Inflorescences racemes up to 10 cm long; they have bright red petal-like leaves (bracts) subtending each flower. Flowers tubular, slightly curved, up to 20 mm long, light green, with minute hairs. \n" +
                "Distribution: Endemic to South of Ecuador. In Cajas it is found in various environments. The showiest red parts of the plant bracts and not flowers.\"" +
                "WHERE " + EspecieDbHelper.KEY_ID + " = 73");

        db.execSQL("UPDATE " + TABLE_ESPECIE + " SET " +
                EspecieDbHelper.KEY_DESCRIPCION_ES + " = \"Plantas en almohadillas muy compactas hasta 1 m de diámetro. Hojas dispuestas en espiral, hasta 1.5 cm de largo, muy estrechas y gruesas. Inflorescencias en cabezuelas que crecen a nivel del suelo, hasta de 1 cm de diámetro. Flores de dos tipos: las externas (± 12) irregulares y presentan una lengüeta blanca, las internas (± 25) cortas, tubulares y con 5 dientes, color amarillo. Frutos con una corona de pelos sedosos, blancos. \n" +
                "Distribución: Centro de Colombia al Norte del Perú. En el Cajas se encuentran en el páramo de almohadillas, en áreas húmedas en los pajonales y raras veces sobre rocas.\"" +
                ", " + EspecieDbHelper.KEY_DESCRIPCION_EN + " = \"Cushion-forming plants, compact, up to 1 m in diameter. Leaves spirally arranged, up to 1.5 cm long, very narrow and leathery. Inflorescence heads grow at ground level, up to 1 cm in diameter. Flowers of two types: the external (± 12) irregular and strap-shaped, white, the internal (± 25) short, and tubular, with 5 teeth, yellow. Fruits with a crown of silky, white hairs. \n" +
                "Distribution: Center of Colombia to North of Peru. In Cajas it is found in the cushion páramo, in moist areas in the grass páramo, and sometimes on rocks.\"" +
                "WHERE " + EspecieDbHelper.KEY_ID + " = 351");

        db.execSQL("UPDATE " + TABLE_ESPECIE + " SET " +
                EspecieDbHelper.KEY_DESCRIPCION_ES + " = \"Hierbas hasta de 40 cm de alto, con varios tallos. Hojas opuestas, hasta 2 cm de largo, lineares. Inflorescencia en grupos hasta de 5 flores a lo largo de los tallos. Flores hasta 15 mm de largo, tienen forma globosa-elíptica, con los pétalos sobrepuestos y escasamente abiertos, color amarillo pálido con tintes rojos. \n" +
                "Distribución: Es endémica del Sur del Ecuador. En el Cajas se la encuentra entre las macollas del pajonal. Se diferencia de la especie anterior por ser más alta, con flores más pequeñas y en mayor número.\"" +
                ", " + EspecieDbHelper.KEY_DESCRIPCION_EN + " = \"Herbs up to 40 cm tall, with various stems. Leaves opposite, up to 2 cm long, linear. Inflorescence has groups of up to 5 flowers along the stem. Flowers up to 15 mm long, globose-elliptic with the petals overlapping and barely open, pale yellow tinged with red. \n" +
                "Distribution: Endemic to South of Ecuador. In Cajas it is the found among the bunch grass. It differs from the previous species by its taller size, with smaller but numerous flowers.\"" +
                "WHERE " + EspecieDbHelper.KEY_ID + " = 137");

        db.execSQL("UPDATE " + TABLE_ESPECIE + " SET " +
                EspecieDbHelper.KEY_DESCRIPCION_ES + " = \"Bambúes enanos hasta 1 m de alto, crecen en grupos densos; tallos huecos y presentan tintes morados. Hojas alargadas, hasta 20 cm de largo, duras y recubiertas de pelos blancos, largos y sedosos; márgenes sumamente cortantes. Inflorescencias entre las hojas o son más altas, color café claro; eje es velloso y con numerosas espiguillas cada una con varios flores (flósculos). Flores poco llamativas y duras, hasta 4 mm de largo. \n" +
                "Distribución: Endémica del Sur del Ecuador. En el Cajas se encuentra en los pajonales en áreas húmedas o en bosques de Polylepis.\"" +
                ", " + EspecieDbHelper.KEY_DESCRIPCION_EN + " = \"Dwarf bamboos up to 1 m tall, growing in dense groups; stems hollow and tinged with purple. Leaves elongate, up to 20 cm long, hard and covered with long silky white hairs; edges quite sharp. Inflorescences among the leaves or taller, light brown; axis is villous and has numerous spikelets each with various flowers (florets). Flowers inconspicuous, stiff, up to 4 mm long. \n" +
                "Distribution: Endemic to South of Ecuador. In Cajas it is found in the grass páramo and moist areas in the Polylepis forests.\"" +
                "WHERE " + EspecieDbHelper.KEY_ID + " = 218");

        db.execSQL("UPDATE " + TABLE_ESPECIE + " SET " +
                EspecieDbHelper.KEY_DESCRIPCION_ES + " = \"Hierbas sin tallo, con apariencia de coliflor, hasta 7 cm de diámetro. Hojas dispuestas en una roseta en la base, tienen la forma arriñonada, hasta 3 cm de largo, carnosas, arrugadas, color verde-oliva violeta en la cara superior, moradas en la inferior; márgenes curvados hacia abajo. Inflorescencias con numerosas flores. Flores hasta 5 mm de largo, tubulares con 5 lóbulos cortos, color blanco. \n" +
                "Distribución: Sur del Ecuador y Sur del Perú. En nuestro país se encuentra sólo en el Cajas donde crece exclusivamente en áreas rocosas por sobre los 4200 m, en el sendero a Cerro Amarillo. Puede fácilmente pasar desapercibida porque se confunde con el color de las rocas.\"" +
                ", " + EspecieDbHelper.KEY_DESCRIPCION_EN + " = \"Stemless herbs, shaped like a cauliflower, up to 7 cm in diameter. Leaves arranged in a basal rosette, kidney-shaped, up to 3 cm long, fleshy, wrinkled, olive green to violet above, purple below; margins curled downwards. Inflorescences with numerous flowers. Flowers 5 mm long, white, tubular with 5 short lobes. \n" +
                "Distribution: South of Ecuador and South of Peru. In Ecuador only found in Cajas in rocky areas above 4200 m on the trail to Cerro Amarillo. It is easily overlooked as it blends well with the color of the rocks.\"" +
                "WHERE " + EspecieDbHelper.KEY_ID + " = 327");

        db.execSQL("UPDATE " + TABLE_ESPECIE + " SET " +
                EspecieDbHelper.KEY_DESCRIPCION_ES + " = \"Hierbas forman almohadillas hasta de 1 m de diámetro, color verde grisáceo, densamente cubiertas por pelos blancos. Hojas amontonadas en la punta de los tallos, hasta 2 cm de largo, marcadamente lobuladas. Flores solitarias y se encuentran entre las hojas, erguidas, muy vistosas, hasta 2.5 cm; 5 pétalos traslapados y son color violeta intenso, con el centro verde; estambres forman una columna blanca con las anteras amarillas en la punta. \n" +
                "Distribución: Es una especie rara, endémica del Sur del Ecuador. En el Cajas se encuentra sólo en sitios rocosos sobre los 4100 m.\"" +
                ", " + EspecieDbHelper.KEY_DESCRIPCION_EN + " = \"Cushion-forming herbs, up to 1 m in diameter, grayish green, densely covered with white hairs. Leaves clustered at the tip of the stems, up to 2 cm long, markedly lobed. Flowers solitary and found among the leaves, erect, showy, up to 2.5 cm long; 5 overlapping petals, deep violet with a green center; stamens form a white column with the yellow anthers at the top. \n" +
                "Distribution: A rare endemic species to South of Ecuador. In Cajas it is only found in rocky areas above 4100 m.\"" +
                "WHERE " + EspecieDbHelper.KEY_ID + " = 222");

        db.execSQL("UPDATE " + TABLE_ESPECIE + " SET " +
                EspecieDbHelper.KEY_DESCRIPCION_ES + " = \"Arbustos hasta 1.5 m de alto, con pelos blancos gruesos en toda la planta. Hojas lanceoladas, opuestas, hasta 1.6 cm de largo, tienen 3 venas principales que salen de la base. Flores colgantes, se las ve en pares, hasta 20 mm de largo, con brácteas y sépalos color rojo-rosado; pétalos sobrepuestos en forma de tubo, color morado oscuro. \n" +
                "Distribución: Es endémica del Centro y Sur del Ecuador. En el Cajas se encuentra en el páramo de pajonal y en los bosques de Polylepis.\"" +
                ", " + EspecieDbHelper.KEY_DESCRIPCION_EN + " = \"Shrubs up to 1.5 m tall, covered with coarse hairs. Leaves opposite, lanceolate, up to 1.6 cm long, with 3 main veins from the base. Flowers pendent, in pairs, up to 20 mm long, with pinkish-red bracts and sepals; overlapping petals form a dark purple tube. \n" +
                "Distribution: Endemic to Center and South of Ecuador. In Cajas it is found in the grass páramo and in the Polylepis forests.\"" +
                "WHERE " + EspecieDbHelper.KEY_ID + " = 50");

        db.execSQL("UPDATE " + TABLE_ESPECIE + " SET " +
                EspecieDbHelper.KEY_DESCRIPCION_ES + " = \"Arbustos bajos, hasta de 30 cm de alto. Hojas alternas, lanceoladas, hasta de 1.5 cm de largo, gruesas, con el margen aserrado. Flores solitarias, hasta de 5 mm de largo, con forma de jarroncito con 5 dientes, blancas a veces teñidas de rosado. Frutos redondeados y carnosos, hasta de 10 mm de largo, color negro-azul oscuro. \n" +
                "Distribución: Centro de México hasta Noroeste de Argentina. En el Cajas se encuentra hasta de los bosques de Polylepis y en el pajonal.\"" +
                ", " + EspecieDbHelper.KEY_DESCRIPCION_EN + " = \"Low shrubs up to 30 cm tall. Leaves alternate, lanceolate, up to 1.5 cm long, leathery, with serrate margins. Flowers solitary, up to 5 mm long, vase-shaped with 5 teeth, white sometimes tinged with pale pink. Fruits round and fleshy, up to 10 mm long, blackish-blue. \n" +
                "Distribution: Center of Mexico to North West of Argentina. In Cajas it is found around the Polylepis forests and in the grass páramo.\"" +
                "WHERE " + EspecieDbHelper.KEY_ID + " = 261");

        db.execSQL("UPDATE " + TABLE_ESPECIE + " SET " +
                EspecieDbHelper.KEY_DESCRIPCION_ES + " = \"Plantas hasta de 25 cm de alto, solitarias o en pequeños grupos; tallos con forma cilíndrica y a veces bifurcados en la punta. Hojas dispuestas en espiral, con forma de escamas alargadas, hasta 1 cm de largo, sobrepuestas, color verde a rojo-anaranjado o rojo-rosado. Hojas superiores llevan las estructuras reproductivas (esporangios) en la base; esporangios de 2 mm de largo, de forma arriñonada y color verde a amarillo. \n" +
                "Distribución: Guatemala a Bolivia. En el Cajas se encuentra en el páramo de almohadillas.\"" +
                ", " + EspecieDbHelper.KEY_DESCRIPCION_EN + " = \"Plants up to 25 cm tall, solitary or in small groups; stems cylindrical and sometimes furcate at apex. Leaves spirally arranged, scale-shaped, up to 1 cm long, overlapping, green to orange-red or pink-red. The upper leaves bear the reproductive structures (sporangia) at their base; sporangia 2 mm long and kidney-shaped, green to yellow. \n" +
                "Distribution: Guatemala to Bolivia. In Cajas it is found in the cushion páramo.\"" +
                "WHERE " + EspecieDbHelper.KEY_ID + " = 168");

        db.execSQL("UPDATE " + TABLE_ESPECIE + " SET " +
                EspecieDbHelper.KEY_DESCRIPCION_ES + " = \"Plantas terrestres o acuáticas, hasta de 10 cm de diámetro; tallos generalmente carnosos, cortos y en general enterrados. Hojas dispuestas en una roseta semienterrada, lineares, hasta de 5 cm de largo, algo triangulares en corte transversal y con cámaras de aire, gruesas, puntiagudas. Las estructuras reproductivas (esporangios) de 5 mm de largo, sean masculinas o femeninas se encuentran hundidas en la base de las hojas, recubiertas colora membrana; las esporas color que va de blanco a gris. \n" +
                "Distribución: Colombia y Ecuador. En el Cajas se encuentran en áreas muy húmedas o totalmente sumergidas en el agua\"" +
                ", " + EspecieDbHelper.KEY_DESCRIPCION_EN + " = \"Terrestrial or aquatic plants, up to 10 cm in diameter; stems generally fleshy, and generally buried. Leaves arranged in a half-buried rosette, linear, up to 5 cm long, triangular in a transversal cut and containing air chambers, leathery, acute. The reproductive structures (sporangia) 5 mm long, masculine or feminine, found at the base of the leaves and covered by a membrane; spores white to gray. \n" +
                "Distribution: Colombia and Ecuador. In Cajas it is found in moist areas or totally submerged in water\"" +
                "WHERE " + EspecieDbHelper.KEY_ID + " = 180");

        db.execSQL("UPDATE " + TABLE_ESPECIE + " SET " +
                EspecieDbHelper.KEY_DESCRIPCION_ES + " = \"Arbustos hasta 3 m de alto, con madera amarilla. Hojas alternas pero se encuentran amontonadas, hasta 2 cm de largo, brillantes en la cara superior y más claras en la inferior, la punta es espinosa; márgenes enteros o espinosos. Flores solitarias, colgantes, y hasta de 10 mm de diámetro, color amarillo brillante, teñidas de rosado por fuera. Frutos subglobosos, carnosos, hasta 8 mm de largo, verdes, luego rojizos y finalmente color negro-morado. \n" +
                "Distribución: Colombia a Bolivia. En el Cajas se encuentra en el páramo de pajonal.\"" +
                ", " + EspecieDbHelper.KEY_DESCRIPCION_EN + " = \"Shrubs up to 3 m tall, with yellow wood. Leaves alternate but clustered, up to 2 cm long, glossy above, paler below, with a spiny tip; margins entire or spiny. Flowers solitary, pendent, up to 10 mm in diameter, bright yellow, tinged with pink. Fruits subglobose, fleshy, up to 8 mm long, green turning reddish and finally purple-black. \n" +
                "Distribution: Colombia to Bolivia. In Cajas it is found in the grass páramo.\"" +
                "WHERE " + EspecieDbHelper.KEY_ID + " = 40");

        db.execSQL("UPDATE " + TABLE_ESPECIE + " SET " +
                EspecieDbHelper.KEY_DESCRIPCION_ES + " = \"Hierbas hasta 25 cm de alto. Hojas muy divididas y se disponen en una roseta en la base, hasta 5 cm de largo. Inflorescencia elevada y está formada por cabezuelas vistosas hasta de 7 cm de diámetro. Flores de dos tipos: las externas (± 20) irregulares, presentan una lengüeta que hasta de 30 mm de largo, color amarillo brillante, las internas (más de 50) tubulares, con 5 dientes, color amarillo oscuro a anaranjado. Frutos con una corona de pelos sedosos hasta 10 mm de largo y son color blanco. \n" +
                "Distribución: Colombia a Perú. En el Cajas es común en el páramo de pajonal.\"" +
                ", " + EspecieDbHelper.KEY_DESCRIPCION_EN + " = \"Herbs up to 25 cm tall. Leaves much divided, up to 5 cm long, and arranged in a basal rosette. The elevated inflorescence is of showy heads, up to 7 cm in diameter. Flowers of two types: the external (± 20) irregular, strap-shaped, up to 30 mm long, bright yellow, the internal (more than 50) tubular, with 5 teeth, dark yellow to orange. Fruits with a crown of white silky hairs up to 10 mm long. \n" +
                "Distribution: Colombia to Peru. In Cajas it is common in the grass páramo.\"" +
                "WHERE " + EspecieDbHelper.KEY_ID + " = 96");

        db.execSQL("UPDATE " + TABLE_ESPECIE + " SET " +
                EspecieDbHelper.KEY_DESCRIPCION_ES + " = \"Hierbas hasta de 30 cm de alto, con largos pelos sedosos blancos. Hojas dispuestas en una roseta basal, hasta 10 cm de largo, alargadas y muy estrechas, con los márgenes engrosados y doblados hacia abajo. Inflorescencias en cabezuelas solitarias erguidas, ca. 2 cm de diámetro. Flores de dos tipos: las externas (± 80) irregulares y presentan una lengüeta estrecha hasta de 10 mm de largo, color blanco, las internas (± 50) cortas, tubulares y con 5 dientes, color amarillo pálido. Frutos con una corona de pelos ásperos ca. 8 mm y son color amarillo. \n" +
                "Distribución: Ecuador y Perú. En el Cajas es común en todos los ambientes.\"" +
                ", " + EspecieDbHelper.KEY_DESCRIPCION_EN + " = \"Herbs up to 30 cm tall, covered with long, silky, white hairs. Leaves arranged in a basal rosette, up to 10 cm long, elongate and narrow, with the thickened margin curled downwards. Inflorescences solitary, erect heads, up to 2 cm in diameter. Flowers of two types: the external (± 80) irregular, strap-shaped, up to 10 mm long, white, the internal (± 50) short, tubular and with 5 teeth, pale yellow. Fruits with a crown of coarse hairs, up to 8 mm long, yellow \n" +
                "Distribution: Ecuador and Peru. In Cajas it is common in all environments.\"" +
                "WHERE " + EspecieDbHelper.KEY_ID + " = 226");

        db.execSQL("UPDATE " + TABLE_ESPECIE + " SET " +
                EspecieDbHelper.KEY_DESCRIPCION_ES + " = \"Arbustos hasta de 1 m de alto, bastante ramificados, las ramas con pelos lanosos color blanco grisáceo. Hojas alternas, lineares, hasta 0.5 cm de largo, gruesas, el margen doblado hacia abajo, la cara inferior cubierta por una densa capa de pelos lanosos color blanco grisáceo. Inflorescencias en cabezuelas ca. 1.5 cm de diámetro, ubicadas al final de las ramas. Flores de dos tipos: las externas (± 15) irregulares y presentan una lengüeta hasta de 8 mm de largo, color blanco, las internas (± 20) tubulares y con 5 dientes, color amarillo a anaranjado. Frutos con una corona de pelos de 10 mm de largo, blancos. \n" +
                "Distribución: Es endémica del país. En el Cajas se encuentra en el páramo de pajonal.\"" +
                ", " + EspecieDbHelper.KEY_DESCRIPCION_EN + " = \"Shrubs up to 1 m tall, profusely branched, with grayish-white, wooly hairs. Leaves alternate, linear, up to 0.5 cm long, leathery, below densely covered with grayish-white wooly hairs; margin curled downwards. Inflorescences heads, up to 1.5 cm in diameter, at the end of the branches. Flowers of two types: the external (± 15) irregular and strap-shaped, up to 8 mm long, white, the internal (± 20) tubular, with 5 teeth, yellow to orange. Fruits with a crown of white hairs, up to 10 mm long. \n" +
                "Distribution: Endemic to Ecuador. In Cajas it is found in the grass páramo.\"" +
                "WHERE " + EspecieDbHelper.KEY_ID + " = 87");

        db.execSQL("UPDATE " + TABLE_ESPECIE + " SET " +
                EspecieDbHelper.KEY_DESCRIPCION_ES + " = \"Plantas hasta de 30 cm de alto; tallos bastante delgados, generalmente huecos en el centro. Hojas diminutas, reducidas a escamas color café, dispuestas en anillos espaciados a lo largo del tallo. Las estructuras reproductivas (esporangios) se encuentran en un tipo de espigas pequeñas (estróbilos) que en las puntas de las ramas, hasta 1.5 cm de largo, color café oscuro; las esporas verdes. \n" +
                "Distribución: Costa Rica a Bolivia. En el Cajas se encuentra a los lados de la carretera.\"" +
                ", " + EspecieDbHelper.KEY_DESCRIPCION_EN + " = \"Plants up to 30 cm tall; stems slender and generally hollow. Leaves minute, reduced to brown scales, arranged in rings spaced along the stem. The reproductive structures (sporangia) found in small spike-like structures (strobili) at the apex of the branches; to 1.5 cm long, dark brown with green spores. \n" +
                "Distribution: Costa Rica to Bolivia. In Cajas it is found along roads.\"" +
                "WHERE " + EspecieDbHelper.KEY_ID + " = 111");

        db.execSQL("UPDATE " + TABLE_ESPECIE + " SET " +
                EspecieDbHelper.KEY_DESCRIPCION_ES + " = \"Arbustos hasta de 2 m de alto, con las puntas de las ramas generalmente terminando en una espina. Hojas alternas, lanceoladas, hasta de 3 cm de largo, rígidas; margen aserrado. Inflorescencias en hasta de 4 cm de largo, con 1 a varias flores. Flores de 10 mm de largo; pétalos blancos con tintes rosados a rojos. Fruto redondeado, carnoso, de 10 mm de largo, color rojo a negro. \n" +
                "Distribución: Venezuela a Bolivia. En el Cajas se encuentra hasta de los bosques de Polylepis y en el pajonal.\"" +
                ", " + EspecieDbHelper.KEY_DESCRIPCION_EN + " = \"Shrubs up to 2 m tall; branches often terminate in a spine. Leaves alternate, lanceolate, up to 3 cm long, stiff; margin serrate. Inflorescences up to 4 cm tall, with 1 to various flowers. Flowers 10 mm long; petals white tinged with pink or red. Fruits round, fleshy, 10 mm long, and red turning black. \n" +
                "Distribution: Venezuela to Bolivia. In Cajas it is found around the Polylepis forests and in the grass páramo.\"" +
                "WHERE " + EspecieDbHelper.KEY_ID + " = 160");

        db.execSQL("UPDATE " + TABLE_ESPECIE + " SET " +
                EspecieDbHelper.KEY_DESCRIPCION_ES + " = \"Hierbas bajas que a veces forman almohadillas pequeñas. Hojas dispuestas en una roseta basaal, lanceoladas, hasta de 1.5 cm de largo, gruesas, brillantes y puntiagudas. Flores solitarias, hasta de 15 mm de largo, blancas con puntos morados hacia el centro, tienen dos labios, el un labio tiene 2 lóbulos triangulares y el otro 3; estambres fusionados y forman una columna central. \n" +
                "Distribución: Endémica del Cajas. Se encuentra en los páramos de pajonal y de almohadillas.\"" +
                ", " + EspecieDbHelper.KEY_DESCRIPCION_EN + " = \"Low herbs, sometimes forming small cushions. Leaves arranged in a basal rosette, lanceolate, up to 1.5 cm long, leathery, glossy and acute at apex. Flowers solitary, up to 15 mm long, white with purple dots towards the center, with 2 lips; one of the lips has 2 triangular lobes and the other 3; stamens fused and form a central column. \n" +
                "Distribution: Endemic to Cajas where it is found in the grass or cushion páramo.\"" +
                "WHERE " + EspecieDbHelper.KEY_ID + " = 200");

        db.execSQL("UPDATE " + TABLE_ESPECIE + " SET " +
                EspecieDbHelper.KEY_DESCRIPCION_ES + " = \"Hierbas hasta de 15 cm de alto, bastante ramificadas, cubiertas de pelos glandulares pegajosos. Hojas opuestas, divididas en 3 en la punta, hasta de 1.5 cm de largo, sobrepuestas. Inflorescencia consiste de 1 o 2 flores al final de los tallos. Flores hasta 15 mm de diámetro; pétalos 5. hendidos en el ápice, color blanco; estambres amarillos. \n" +
                "Distribución: Venezuela a Ecuador. En el Cajas se encuentra en la zona de Tres Cruces, en el páramo de almohadillas y entre rocas.\"" +
                ", " + EspecieDbHelper.KEY_DESCRIPCION_EN + " = \"Herbs up to 15 cm tall, profusely branched, covered with glandular, sticky hairs. Leaves opposite, divided in 3 at the apex, up to 1.5 cm long, overlapping. Inflorescence of 1 or 2 flowers at the end of the branches. Flowers up to 15 mm in diameter; 5 white petals cleft at apex; stamens yellow. \n" +
                "Distribution: Venezuela to Ecuador. In Cajas it is found in the Tres Cruces area, in the cushion páramo and among rocks.\"" +
                "WHERE " + EspecieDbHelper.KEY_ID + " = 77");

        db.execSQL("UPDATE " + TABLE_ESPECIE + " SET " +
                EspecieDbHelper.KEY_DESCRIPCION_ES + " = \"Arbustos bajos, erguidos o algo recostados, hasta 60 cm de alto. Hojas opuestas, ovadas o elípticas, gruesas, hasta de 1 cm de largo. Inflorescencias en las puntas de las ramas, erguidas y hasta 5 cm de largo, con muchas flores. Flores hasta 3 mm de largo, tubulares, con 5 lóbulos cortos, color blanco o rosado, con tintes lilas. \n" +
                "Distribución: Colombia a Perú. Común en el Cajas en varios ambientes. Es una especie variable que cuando crece a mayor altura presenta las hojas y las flores amontonadas, mientras que dentro del bosque son más laxos.\"" +
                ", " + EspecieDbHelper.KEY_DESCRIPCION_EN + " = \"Low shrubs, erect or reclining, up to 60 cm tall. Leaves opposite, ovate or elliptic, leathery, up to 1 cm long. Inflorescences terminal, erect, and up to 5 cm long, many-flowered. Flowers 3 mm long, tubular, with 5 short lobes, white or pink, tinged with lilac. \n" +
                "Distribution: Colombia to Peru. In Cajas it is common in various environments. A variable species presenting clustered leaves and flowers at higher elevations and laxer when growing inside the forest.\"" +
                "WHERE " + EspecieDbHelper.KEY_ID + " = 329");

        db.execSQL("UPDATE " + TABLE_ESPECIE + " SET " +
                EspecieDbHelper.KEY_DESCRIPCION_ES + " = \"Hierbas hasta de 50 cm de alto, laxas, algo reclinadas, cubiertas de pelos ásperos. Hojas alternas, lanceoladas, hasta de 1.5 cm de largo, amontonadas. Inflorescencia en la parte terminal de los tallos y tiene hasta 10 flores. Flores hasta 10 mm de largo, tienen 4 pétalos algo sobrepuestos, color púrpura con la base amarillo verdosa. Frutos lanceolados, alargados o algo curvos. \n" +
                "Distribución: Es endémica del Cajas. Se encuentra principalmente en áreas rocosas, ocasionalmente en bosques de Polylepis y en el páramo de pajonal.\"" +
                ", " + EspecieDbHelper.KEY_DESCRIPCION_EN + " = \"Lax herbs up to 50 cm tall, somewhat reclining, covered with coarse hairs. Leaves alternate, lanceolate, up to 1.5 cm long, clustered. Inflorescence terminal on the stem, with about 10 flowers. Flowers up to 10 mm long, with 4 overlapping petals, purple with a greenish yellow base. Fruits lanceolate, elongate or curved. \n" +
                "Distribution: Endemic to Cajas. It is mainly found in rocky areas, occasionally in the Polylepis forests and grass páramo.\"" +
                "WHERE " + EspecieDbHelper.KEY_ID + " = 99");

        db.execSQL("UPDATE " + TABLE_ESPECIE + " SET " +
                EspecieDbHelper.KEY_DESCRIPCION_ES + " = \"Helechos terrestres, hasta de 35 cm de alto, cubiertos de pelos color café rojizo. Hojas hasta 30 cm de largo, rectas y tiesas, el ápice es enrollado, compuestas de numerosos pares de hojuelas generalmente con forma de orejuelas, hasta de 5 mm de largo, sobrepuestas, gruesas; márgenes enrollados hacia abajo. Las estructuras reproductivas (soros) se encuentran en la cara inferior de las hojas; las esporas color café oscuro. \n" +
                "Distribución: Colombia a Perú. En el Cajas se encuentra en el bosque de Polylepis. Las especies de este género son casi exclusivas de los páramos andinos.\"" +
                ", " + EspecieDbHelper.KEY_DESCRIPCION_EN + " = \"Terrestrial ferns, up to 35 cm tall, covered with reddish brown hairs. Leaves up to 30 cm long, erect and stiff with a coiled apex, and with numerous pairs of ear-shaped, leathery, overlapping leaflets to 5 mm long, with margins curled downwards. The reproductive structures (sori) on the lower side of the leaflets; spores dark brown. \n" +
                "Distribution: Colombia to Peru. In Cajas it is found in the Polylepis forests. The species of this genus almost exclusive to the Andean páramos.\"" +
                "WHERE " + EspecieDbHelper.KEY_ID + " = 184");
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

    private void updateWhenVersionLessThan14(SQLiteDatabase db) {
        upgradeTable(db, TABLE_FAMILIA, KEYS_COMMON, FamiliaDbHelper.KEYS_FAMILIA);
        upgradeTable(db, TABLE_GENERO, KEYS_COMMON, GeneroDbHelper.KEYS_GENERO);
        upgradeTable(db, TABLE_ESPECIE, KEYS_COMMON, EspecieDbHelper.KEYS_ESPECIE);

        DbInserter dbInserter = new DbInserter(db);
        dbInserter.insertFamilias();
        dbInserter.insertGeneros();
        dbInserter.insertEspecies();
    }

    public void upgradeTable(SQLiteDatabase db, String tableName, String[] common, String[] columnNames) {
        db.execSQL("DROP TABLE " + tableName);
        db.execSQL(createTableSql(tableName, common, columnNames));
    }

    public void createTable(SQLiteDatabase db, String tableName, String[] common, String[] columnNames) {
        db.execSQL(createTableSql(tableName, common, columnNames));
    }

    public static String createTableSql(String tableName, String[] common, String[] columnNames) {
        String sql = "CREATE TABLE " + tableName + " (" +
                common[0] + " INTEGER PRIMARY KEY," + //id
                common[1] + " DATETIME"; //date
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
