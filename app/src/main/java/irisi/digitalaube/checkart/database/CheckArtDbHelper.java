package irisi.digitalaube.checkart.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import org.opencv.core.Mat;

import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import irisi.digitalaube.checkart.api.model.Motif;
import irisi.digitalaube.checkart.api.model.Origine;
import irisi.digitalaube.checkart.api.model.Tapis;
import irisi.digitalaube.checkart.database.CheckArtContrat.CarpetTable;

public class CheckArtDbHelper extends SQLiteOpenHelper {

    public static final int DATA_BASE_VERSION = 1;
    public static final String DATA_BASE_NAME = "checkartr";
    public static final String TEXT_TYPE = " TEXT"; // Works for date also !
    public static final String INT_TYPE = " INTEGER";
    public static final String FLOAT_TYPE = " INTEGER";
    public static final String BLOB_TYPE = " byte[] bytes";
    public static final String UNIQUE_CONSTRAINT = "UNIQUE";


    // --------------------             Carpet-Image TABLE            -------------------------------------
    public static final String SQL_CREATE_TABLE_CARPET =
            "CREATE TABLE " + CheckArtContrat.CarpetTable.TABLE_NAME + " ( " +
                    CheckArtContrat.CarpetTable._ID + INT_TYPE +" PRIMARY KEY AUTOINCREMENT, " +
                    CheckArtContrat.CarpetTable.COLUMN_NAME_TAPIS_NAME + TEXT_TYPE + ", " +
                    CheckArtContrat.CarpetTable.COLUMN_NAME_TAPIS_PHOTO + BLOB_TYPE + ", " +
                    CheckArtContrat.CarpetTable.COLUMN_NAME_TAPIS_DESCRIPTION+ TEXT_TYPE + ", "+
                    CheckArtContrat.CarpetTable.COLUMN_NAME_TAPIS_COULEUR+ TEXT_TYPE + ", "+
                    CheckArtContrat.CarpetTable.COLUMN_NAME_TAPIS_ORIGINE+ TEXT_TYPE + ", "+
                    CheckArtContrat.CarpetTable.COLUMN_NAME_TAPIS_MOTIF+ TEXT_TYPE + ", "+
                    CheckArtContrat.CarpetTable.COLUMN_NAME_TAPIS_TAILLE+ TEXT_TYPE +  ", "+
                    CheckArtContrat.CarpetTable.COLUMN_NAME_TAPIS_URI+ TEXT_TYPE +  ", "+
                    CheckArtContrat.CarpetTable.COLUMN_NAME_TAPIS_W1+ INT_TYPE +  ", "+
                    CheckArtContrat.CarpetTable.COLUMN_NAME_TAPIS_W2+ INT_TYPE +  ", "+
                    CheckArtContrat.CarpetTable.COLUMN_NAME_TAPIS_W3+ INT_TYPE +
                    " )";
    public static final String SQL_DELETE_TABLE_CARPET =
            "DROP TABLE IF EXISTS " + CheckArtContrat.CarpetTable.TABLE_NAME;
    // -------------------- ---------------------------------- -------------------------------------


    // --------------------             Motif TABLE            -------------------------------------
    public static final String SQL_CREATE_TABLE_MOTIF ="CREATE TABLE "+CheckArtContrat.MotifTable
            .TABLE_NAME+ " ( " + CheckArtContrat.MotifTable._ID + INT_TYPE +" PRIMARY KEY AUTOINCREMENT, " +
            CheckArtContrat.MotifTable.COLUMN_NAME_MOTIF_SYMBOLE + TEXT_TYPE + ", " +
            CheckArtContrat.MotifTable.COLUMN_NAME_MOTIF_SIGNIFICATION + TEXT_TYPE +  " )";

    public static final String SQL_DELETE_TABLE_MOTIF =
            "DROP TABLE IF EXISTS " + CheckArtContrat.MotifTable.TABLE_NAME;
    // --
    //
    // ------------------ ---------------------------------- -------------------------------------
   // --------------------             ORIGINE TABLE            -------------------------------------
    public static final String SQL_CREATE_TABLE_ORIGINE ="CREATE TABLE "+CheckArtContrat.OrigineTable
            .TABLE_NAME+ " ( " + CheckArtContrat.OrigineTable._ID + INT_TYPE +" PRIMARY KEY AUTOINCREMENT, " +
            CheckArtContrat.OrigineTable.COLUMN_NAME_REGION + TEXT_TYPE + ", " +
            CheckArtContrat.MotifTable.COLUMN_NAME_MOTIF_SIGNIFICATION + TEXT_TYPE +  " )";

    public static final String SQL_DELETE_TABLE_ORIGINE=
            "DROP TABLE IF EXISTS " + CheckArtContrat.OrigineTable.TABLE_NAME;
    // -------------------- ---------------------------------- -------------------------------------
// --------------------             TAPISMOTIF TABLE            -------------------------------------
    public static final String SQL_CREATE_TABLE_TAPISMOTIF ="CREATE TABLE "+CheckArtContrat.TapisMotifTable
            .TABLE_NAME+ " ( " + CheckArtContrat.TapisMotifTable._ID + INT_TYPE +" PRIMARY KEY AUTOINCREMENT, " +
            CheckArtContrat.TapisMotifTable.COLUMN_NAME_MOTIF+ INT_TYPE + ", " +
            CheckArtContrat.TapisMotifTable.COLUMN_NAME_TAPIS + INT_TYPE + ", " +
            "FOREIGN KEY ("+ CheckArtContrat.TapisMotifTable.COLUMN_NAME_TAPIS +") REFERENCES "+
            CheckArtContrat.CarpetTable.TABLE_NAME + "(_id)" + ", "+
            "FOREIGN KEY ("+ CheckArtContrat.TapisMotifTable.COLUMN_NAME_MOTIF +") REFERENCES "+
            CheckArtContrat.MotifTable.TABLE_NAME + "(_id)" + " )";

    public static final String SQL_DELETE_TABLE_TAPISMOTIF=
            "DROP TABLE IF EXISTS " + CheckArtContrat.TapisMotifTable.TABLE_NAME;
    // --
    // --------------------             TAPISORIGINE TABLE            -------------------------------------
    public static final String SQL_CREATE_TABLE_TAPISORIGINE ="CREATE TABLE "+CheckArtContrat.TapisOrigineTable
            .TABLE_NAME+ " ( " + CheckArtContrat.TapisMotifTable._ID + INT_TYPE +" PRIMARY KEY AUTOINCREMENT, " +
            CheckArtContrat.TapisOrigineTable.COLUMN_NAME_TAPIS+ INT_TYPE + ", " +
            CheckArtContrat.TapisOrigineTable.COLUMN_NAME_ORIGINE + INT_TYPE +", "+
            "FOREIGN KEY ("+ CheckArtContrat.TapisOrigineTable.COLUMN_NAME_TAPIS +") REFERENCES "+
            CheckArtContrat.CarpetTable.TABLE_NAME + "(_id)" + ", "+
     "FOREIGN KEY ("+ CheckArtContrat.TapisOrigineTable.COLUMN_NAME_ORIGINE +") REFERENCES "+
            CheckArtContrat.OrigineTable.TABLE_NAME + "(_id)" + " )";

    public static final String SQL_DELETE_TABLE_TAPISORIGINE=
            "DROP TABLE IF EXISTS " + CheckArtContrat.TapisOrigineTable.TABLE_NAME;
    // --



    public CheckArtDbHelper(Context context){
        super(context,DATA_BASE_NAME,null,DATA_BASE_VERSION);
    }


    // ----------------------------  Data Base Creation :  -----------------------------------------

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE_CARPET);
        /*
                db.execSQL(SQL_CREATE_TABLE_MOTIF);
                db.execSQL(SQL_CREATE_TABLE_CARACTERSTIQUE);
         */
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_TABLE_CARPET);
        /*
                db.execSQL(SQL_DELETE_TABLE_MOTIF);
                db.execSQL(SQL_DELETE_TABLE_CARACTERSTIQUE);
         */
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    // ----------------------------  Data Base Creation .  -----------------------------------------



    // ------------------ Bitmap-BLOB Converter for database storing and retrieving ----------------
    public static class DbBitmapUtility {
        // convert from bitmap to byte array
        public static byte[] getBytes(Bitmap bitmap) {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 0, stream);
            return stream.toByteArray();
        }
        // convert from byte array to bitmap
        @RequiresApi(api = Build.VERSION_CODES.O)
        public static Bitmap getImage(byte[] image) {

            Bitmap bmp = Bitmap.createBitmap(400, 400, Bitmap.Config.RGB_565);
            ByteBuffer buffer = ByteBuffer.wrap(image);
            bmp.copyPixelsFromBuffer(buffer);
            return bmp;

           /*
           BitmapFactory.Options options = new BitmapFactory.Options();
            options.inDither = false;
            options.inPreferredConfig = null;
            options.inSampleSize = 4;
            return BitmapFactory.decodeByteArray(image, 0, image.length, options);
           */

        }
    }
    // ---------------------------------------------------------------------------------------------


    // ----------------------------  Image Table CRUD :  --------------------------------------------

    // Insert New Image

    public void dbput(Tapis tapis, Mat m) {
        long nbytes = m.total() * m.elemSize();
        byte[] bytes = new byte[ (int)nbytes ];
        m.get(0, 0,bytes);
        insertCarpet(tapis, m.type(), m.cols(), m.rows(), bytes);
    }
/////////////CRUD CarpetTable////////////////////////////
    public void insertCarpet(Tapis tapis, int t, int w, int h, byte[] bytes) throws SQLiteException {
        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues values = new  ContentValues();
        values.put(CarpetTable.COLUMN_NAME_TAPIS_NAME, tapis.getNom());
        values.put(CarpetTable.COLUMN_NAME_TAPIS_DESCRIPTION, tapis.getDescription());
        values.put(CarpetTable.COLUMN_NAME_TAPIS_COULEUR, tapis.getCouleur());
        values.put(CarpetTable.COLUMN_NAME_TAPIS_TAILLE, tapis.getTaille());
        values.put(CarpetTable.COLUMN_NAME_TAPIS_ORIGINE, tapis.getOrigine());
        values.put(CarpetTable.COLUMN_NAME_TAPIS_MOTIF, tapis.getMotif());
        values.put(CarpetTable.COLUMN_NAME_TAPIS_W1, t);
        values.put(CarpetTable.COLUMN_NAME_TAPIS_W2, w);
        values.put(CarpetTable.COLUMN_NAME_TAPIS_W3, h);
        values.put(CarpetTable.COLUMN_NAME_TAPIS_PHOTO, bytes);
        values.put(CarpetTable.COLUMN_NAME_TAPIS_URI, tapis.getUri());
        database.insertOrThrow(CheckArtContrat.CarpetTable.TABLE_NAME, null, values );
        database.close();
    }

    // find one carpet by id (row)
    public Cursor findCarpetById(Integer id) {

        SQLiteDatabase db = this.getReadableDatabase();

        String[] projection = {
                CarpetTable.COLUMN_NAME_TAPIS_NAME,
                CarpetTable.COLUMN_NAME_TAPIS_DESCRIPTION,
                CarpetTable.COLUMN_NAME_TAPIS_PHOTO,
                CarpetTable.COLUMN_NAME_TAPIS_TAILLE,
                CarpetTable.COLUMN_NAME_TAPIS_W1,
                CarpetTable.COLUMN_NAME_TAPIS_W2,
                CarpetTable.COLUMN_NAME_TAPIS_W3,

        };

        String selection = CheckArtContrat.CarpetTable._ID + " like ?";

        String[] selectArgs = { String.valueOf(id) };

        Cursor result = db.query(
                CheckArtContrat.CarpetTable.TABLE_NAME,
                projection,
                selection,
                selectArgs,
                null,
                null,
                null
        );

        result.close();

        return result;
    }

    // find all carpets (rows)
    public Cursor findAllCarpets() {

        SQLiteDatabase db = this.getReadableDatabase();

        String[] projection = {
                CarpetTable.COLUMN_NAME_TAPIS_NAME,
                CarpetTable.COLUMN_NAME_TAPIS_DESCRIPTION,
                CarpetTable.COLUMN_NAME_TAPIS_COULEUR,
                CarpetTable.COLUMN_NAME_TAPIS_TAILLE,
                CarpetTable.COLUMN_NAME_TAPIS_ORIGINE,
                CarpetTable.COLUMN_NAME_TAPIS_MOTIF,
                CarpetTable.COLUMN_NAME_TAPIS_W1,
                CarpetTable.COLUMN_NAME_TAPIS_W2,
                CarpetTable.COLUMN_NAME_TAPIS_W3,
                CarpetTable.COLUMN_NAME_TAPIS_PHOTO
        };

        Cursor result = db.query(
                CheckArtContrat.CarpetTable.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null
        );
        return result;
    }

    // find carpets with certain colors
    public Cursor findAllCarpetsWithColors(String[] colors) {

        SQLiteDatabase db = this.getReadableDatabase();

        String[] projection = {
                CarpetTable.COLUMN_NAME_TAPIS_NAME,
                CarpetTable.COLUMN_NAME_TAPIS_PHOTO
        };

        StringBuilder selection = new StringBuilder(CarpetTable.COLUMN_NAME_TAPIS_COULEUR + " like ? ");

        if (colors.length > 1) {
            for (int i = 0; i < colors.length - 1 ; i++) {
                selection.append(" OR " + CarpetTable.COLUMN_NAME_TAPIS_COULEUR + " like ? ");
            }
        }

        String[] selectArgs = colors;

        return db.query(
                CarpetTable.TABLE_NAME,
                projection,
                selection.toString(),
                selectArgs,
                null,
                null,
                null
        );
    }

    // Update Carpet content
   public void updateCarpet(Tapis tapis) throws SQLiteException {
        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues values = new ContentValues();
       values.put(CarpetTable.COLUMN_NAME_TAPIS_NAME, tapis.getNom());
       values.put(CarpetTable.COLUMN_NAME_TAPIS_DESCRIPTION, tapis.getDescription());
       values.put(CarpetTable.COLUMN_NAME_TAPIS_TAILLE, tapis.getTaille());
       values.put(CarpetTable.COLUMN_NAME_TAPIS_COULEUR, tapis.getCouleur());
       values.put(CarpetTable.COLUMN_NAME_TAPIS_W1, tapis.getW1());
       values.put(CarpetTable.COLUMN_NAME_TAPIS_W2, tapis.getW2());
       values.put(CarpetTable.COLUMN_NAME_TAPIS_W3, tapis.getW3());
       values.put(CarpetTable.COLUMN_NAME_TAPIS_PHOTO, tapis.getPhoto());
       values.put(CarpetTable.COLUMN_NAME_TAPIS_URI, tapis.getUri());
        database.update(CheckArtContrat.CarpetTable.TABLE_NAME,values,
                CheckArtContrat.CarpetTable._ID + "= ?", new String[] {String.valueOf(tapis.getId())} );
        database.close();
    }

    // delete carpet by id
    public void deleteCarpetWhereIdLike(Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(CheckArtContrat.CarpetTable.TABLE_NAME,"_id= ?", new String[] {String.valueOf(id)} );
        db.close();
    }
/////////////////////////////////////////////////


// ////////////////////////CRUD MotifTable////////////////////////////
    public void insertMotif(String symbole, String signification) throws SQLiteException {
        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues values = new  ContentValues();
        values.put(CheckArtContrat.MotifTable.COLUMN_NAME_MOTIF_SYMBOLE, symbole );
        values.put(CheckArtContrat.MotifTable.COLUMN_NAME_MOTIF_SIGNIFICATION, signification );

        database.insertOrThrow(CheckArtContrat.MotifTable.TABLE_NAME, null, values );
        database.close();
    }

    // find one carpet by id (row)
    public Cursor findMotifById(Integer id) {

        SQLiteDatabase db = this.getReadableDatabase();

        String[] projection = {
                CheckArtContrat.MotifTable.COLUMN_NAME_MOTIF_SYMBOLE,
                CheckArtContrat.MotifTable.COLUMN_NAME_MOTIF_SIGNIFICATION,

        };

        String selection = CheckArtContrat.MotifTable._ID + " like ?";

        String[] selectArgs = { String.valueOf(id) };

        Cursor result = db.query(
                CheckArtContrat.MotifTable.TABLE_NAME,
                projection,
                selection,
                selectArgs,
                null,
                null,
                null
        );

        result.close();

        return result;
    }

    // find all carpets (rows)
    public Cursor findAllMotifs() {

        SQLiteDatabase db = this.getReadableDatabase();

        String[] projection = {
                CheckArtContrat.MotifTable.COLUMN_NAME_MOTIF_SYMBOLE,
                CheckArtContrat.MotifTable.COLUMN_NAME_MOTIF_SIGNIFICATION,
        };

        Cursor result = db.query(
                CheckArtContrat.MotifTable.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null
        );
        return result;
    }

    // Update Carpet content
   public void updateMotif(Integer id, String symbole, String signification) throws SQLiteException {
        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues values = new ContentValues();
       values.put(CheckArtContrat.MotifTable.COLUMN_NAME_MOTIF_SYMBOLE, symbole);
       values.put(CheckArtContrat.MotifTable.COLUMN_NAME_MOTIF_SIGNIFICATION, signification);

        database.update(CheckArtContrat.MotifTable.TABLE_NAME,values,
                CheckArtContrat.MotifTable._ID + "= ?", new String[] {String.valueOf(id)} );
        database.close();
    }

    // delete carpet by id
    public void deleteMotifWhereIdLike(Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(CheckArtContrat.MotifTable.TABLE_NAME,"_id= ?", new String[] {String.valueOf(id)} );
        db.close();
    }
/////////////////////////////////////////////


// /////////////////CRUD OrigineTable////////////////////////////
    public void insertOrigine(String region) throws SQLiteException {
        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues values = new  ContentValues();
        values.put(CheckArtContrat.OrigineTable.COLUMN_NAME_REGION, region );

        database.insertOrThrow(CheckArtContrat.OrigineTable.TABLE_NAME, null, values );
        database.close();
    }

    // find one carpet by id (row)
    public Cursor findOrigineById(Integer id) {

        SQLiteDatabase db = this.getReadableDatabase();

        String[] projection = {
                CheckArtContrat.OrigineTable.COLUMN_NAME_REGION,

        };

        String selection = CheckArtContrat.OrigineTable._ID + " like ?";

        String[] selectArgs = { String.valueOf(id) };

        Cursor result = db.query(
                CheckArtContrat.OrigineTable.TABLE_NAME,
                projection,
                selection,
                selectArgs,
                null,
                null,
                null
        );

        result.close();

        return result;
    }

    // find all carpets (rows)
    public Cursor findAllOrigines() {

        SQLiteDatabase db = this.getReadableDatabase();

        String[] projection = {
                CheckArtContrat.OrigineTable.COLUMN_NAME_REGION,
        };

        Cursor result = db.query(
                CheckArtContrat.OrigineTable.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null
        );
        return result;
    }

    // Update Carpet content
   public void updateOrigine(String id, String region) throws SQLiteException {
        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues values = new ContentValues();
       values.put(CheckArtContrat.OrigineTable.COLUMN_NAME_REGION, region);

        database.update(CheckArtContrat.OrigineTable.TABLE_NAME,values,
                CheckArtContrat.OrigineTable._ID + "= ?", new String[] {String.valueOf(id)} );
        database.close();
    }

    // delete carpet by id
    public void deleteOrigineWhereIdLike(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(CheckArtContrat.OrigineTable.TABLE_NAME,"_id= ?", new String[] {String.valueOf(id)} );
        db.close();
    }
///////////////////////////////////////////////////////////////

// /////////////////////////////////CRUD TapisMotifTable////////////////////////////
    public void insertTapisMotif(Tapis tapis, Motif motif) throws SQLiteException {
        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues values = new  ContentValues();
        values.put(CheckArtContrat.TapisMotifTable.COLUMN_NAME_TAPIS, tapis.getId() );
        values.put(CheckArtContrat.TapisMotifTable.COLUMN_NAME_MOTIF, motif.getId() );

        database.insertOrThrow(CheckArtContrat.OrigineTable.TABLE_NAME, null, values );
        database.close();
    }

    // find one carpet by id (row)
    public Cursor findTapisMotifById(Integer id) {

        SQLiteDatabase db = this.getReadableDatabase();

        String[] projection = {
                CheckArtContrat.TapisMotifTable.COLUMN_NAME_TAPIS,
                CheckArtContrat.TapisMotifTable.COLUMN_NAME_MOTIF,

        };

        String selection = CheckArtContrat.TapisMotifTable._ID + " like ?";

        String[] selectArgs = { String.valueOf(id) };

        Cursor result = db.query(
                CheckArtContrat.TapisMotifTable.TABLE_NAME,
                projection,
                selection,
                selectArgs,
                null,
                null,
                null
        );

        result.close();

        return result;
    }

    // find all carpets (rows)
    public Cursor findAllTapisMotifs() {

        SQLiteDatabase db = this.getReadableDatabase();

        String[] projection = {
                CheckArtContrat.TapisMotifTable.COLUMN_NAME_TAPIS,
                CheckArtContrat.TapisMotifTable.COLUMN_NAME_MOTIF,
        };

        Cursor result = db.query(
                CheckArtContrat.TapisMotifTable.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null
        );
        return result;
    }

    // Update Carpet content
   public void updateTapisMotif(Integer id, Tapis tapis, Motif motif) throws SQLiteException {
        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues values = new ContentValues();
       values.put(CheckArtContrat.TapisMotifTable.COLUMN_NAME_TAPIS, tapis.getId());
       values.put(CheckArtContrat.TapisMotifTable.COLUMN_NAME_MOTIF, motif.getId());

        database.update(CheckArtContrat.TapisMotifTable.TABLE_NAME,values,
                CheckArtContrat.TapisMotifTable._ID + "= ?", new String[] {String.valueOf(id)} );
        database.close();
    }

    // delete carpet by id
    public void deleteTapisMotifWhereIdLike(Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(CheckArtContrat.TapisMotifTable.TABLE_NAME,"_id= ?", new String[] {String.valueOf(id)} );
        db.close();
    }
//////////////////////////////////////////////////////////////

    // /////////////////////////////////CRUD TapisOrigineTable////////////////////////////
    public void insertTapisOrigine(Tapis tapis, Origine origine) throws SQLiteException {
        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues values = new  ContentValues();
        values.put(CheckArtContrat.TapisOrigineTable.COLUMN_NAME_TAPIS, tapis.getId() );
        values.put(CheckArtContrat.TapisOrigineTable.COLUMN_NAME_ORIGINE, origine.getId() );

        database.insertOrThrow(CheckArtContrat.TapisOrigineTable.TABLE_NAME, null, values );
        database.close();
    }

    // find one carpet by id (row)
    public Cursor findTapisOrigineById(Integer id) {

        SQLiteDatabase db = this.getReadableDatabase();

        String[] projection = {
                CheckArtContrat.TapisOrigineTable.COLUMN_NAME_TAPIS,
                CheckArtContrat.TapisOrigineTable.COLUMN_NAME_ORIGINE,

        };

        String selection = CheckArtContrat.TapisOrigineTable._ID + " like ?";

        String[] selectArgs = { String.valueOf(id) };

        Cursor result = db.query(
                CheckArtContrat.TapisOrigineTable.TABLE_NAME,
                projection,
                selection,
                selectArgs,
                null,
                null,
                null
        );

        result.close();

        return result;
    }

    // find all carpets (rows)
    public Cursor findAllTapisOrigines() {

        SQLiteDatabase db = this.getReadableDatabase();

        String[] projection = {
                CheckArtContrat.TapisOrigineTable.COLUMN_NAME_TAPIS,
                CheckArtContrat.TapisOrigineTable.COLUMN_NAME_ORIGINE,
        };

        Cursor result = db.query(
                CheckArtContrat.TapisOrigineTable.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null
        );
        return result;
    }

    // Update Carpet content
    public void updateTapisOrigine(Integer id, Tapis tapis, Origine origine) throws SQLiteException {
        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(CheckArtContrat.TapisOrigineTable.COLUMN_NAME_TAPIS, tapis.getId());
        values.put(CheckArtContrat.TapisOrigineTable.COLUMN_NAME_ORIGINE, origine.getId());

        database.update(CheckArtContrat.TapisOrigineTable.TABLE_NAME,values,
                CheckArtContrat.TapisOrigineTable._ID + "= ?", new String[] {String.valueOf(id)} );
        database.close();
    }

    // delete carpet by id
    public void deleteTapisOrigineWhereIdLike(Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(CheckArtContrat.TapisOrigineTable.TABLE_NAME,"_id= ?", new String[] {String.valueOf(id)} );
        db.close();
    }
//////////////////////////////////////////////////////////////


    public  void deleteDb(){
        SQLiteDatabase  db = this.getWritableDatabase();
        db.delete(CheckArtContrat.CarpetTable.TABLE_NAME, null, null);
    }

    // ----> find Images as a bitmap objects :

/*
    // find one image by id (bitmap)
    public Bitmap findBitmapImageById(Integer id) {
        Cursor img = this.findCarpetById(id);
        return DbBitmapUtility.getImage(img.getBlob(img.getColumnIndex(CheckArtContrat.CarpetTable.COLUMN_NAME_CARPET_IMAGE)));
    }

    // find all images (bitmap)
    public List<Bitmap> findAllBitmapImages() {
        Cursor imgs = this.findAllCarpets();
        List<Bitmap> result = new ArrayList<>();

        while (imgs.moveToNext()) {
            result.add(DbBitmapUtility.getImage(
                    imgs.getBlob(imgs.getColumnIndex(CheckArtContrat.CarpetTable.COLUMN_NAME_CARPET_IMAGE)))
            );
        }

        return result;
    }*/
    // ----------------------------  Image Table CRUD .  --------------------------------------------



    @RequiresApi(api = Build.VERSION_CODES.O)
    public List<List<?>> findAllBitmapImagesWithColor(String[] colors) {
        Cursor carpets = this.findAllCarpetsWithColors(colors);

        List<List<?>> result = new ArrayList<>();

        while (carpets.moveToNext()) {

            List<Object> tmp = new ArrayList<>();

            tmp.add(DbBitmapUtility.getImage(
                    carpets.getBlob(carpets.getColumnIndex(CarpetTable.COLUMN_NAME_TAPIS_PHOTO))));

            tmp.add(carpets.getString(carpets.getColumnIndex(CarpetTable.COLUMN_NAME_TAPIS_NAME)));

            result.add(tmp);
        }

        return result;
    }

}
