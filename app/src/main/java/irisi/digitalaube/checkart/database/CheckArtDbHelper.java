package irisi.digitalaube.checkart.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import org.opencv.core.Mat;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import irisi.digitalaube.checkart.api.model.Tapis;
import irisi.digitalaube.checkart.database.CheckArtContrat.CarpetTable;

public class CheckArtDbHelper extends SQLiteOpenHelper {

    public static final int DATA_BASE_VERSION = 1;
    public static final String DATA_BASE_NAME = "checkar";
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
                    CheckArtContrat.CarpetTable.COLUMN_NAME_TAPIS_TAILLE+ FLOAT_TYPE +  ", "+
                    CheckArtContrat.CarpetTable.COLUMN_NAME_TAPIS_URI+ TEXT_TYPE +  ", "+
                    CheckArtContrat.CarpetTable.COLUMN_NAME_TAPIS_W1+ INT_TYPE +  ", "+
                    CheckArtContrat.CarpetTable.COLUMN_NAME_TAPIS_W2+ INT_TYPE +  ", "+
                    CheckArtContrat.CarpetTable.COLUMN_NAME_TAPIS_W3+ INT_TYPE +
                    " )";
    public static final String SQL_DELETE_TABLE_CARPET =
            "DROP TABLE IF EXISTS " + CheckArtContrat.CarpetTable.TABLE_NAME;
    // -------------------- ---------------------------------- -------------------------------------


    // --------------------             Motif TABLE            -------------------------------------
    public static final String SQL_CREATE_TABLE_MOTIF = "";
    public static final String SQL_DELETE_TABLE_MOTIF =
            "DROP TABLE IF EXISTS " + CheckArtContrat.MotifTable.TABLE_NAME;
    // -------------------- ---------------------------------- -------------------------------------



    // --------------------          Caracteristique TABLE     -------------------------------------
    public static final String SQL_CREATE_TABLE_CARACTERSTIQUE = "";
    public static final String SQL_DELETE_TABLE_CARACTERSTIQUE =
            "DROP TABLE IF EXISTS " + CheckArtContrat.CaracteristiqueTable.TABLE_NAME;
    // -------------------- ---------------------------------- -------------------------------------



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
            bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream);
            return stream.toByteArray();
        }
        // convert from byte array to bitmap
        public static Bitmap getImage(byte[] image) {
            return BitmapFactory.decodeByteArray(image, 0, image.length);
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

    public void insertCarpet(Tapis tapis, int t, int w, int h, byte[] bytes) throws SQLiteException {
        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues values = new  ContentValues();
        values.put(CarpetTable.COLUMN_NAME_TAPIS_NAME, tapis.getNom());
        values.put(CarpetTable.COLUMN_NAME_TAPIS_DESCRIPTION, tapis.getDescription());
        values.put(CarpetTable.COLUMN_NAME_TAPIS_COULEUR, tapis.getCouleur());
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


}
