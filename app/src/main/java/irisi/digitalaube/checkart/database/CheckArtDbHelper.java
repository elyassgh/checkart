package irisi.digitalaube.checkart.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class CheckArtDbHelper extends SQLiteOpenHelper {

    public static final int DATA_BASE_VERSION = 1;
    public static final String DATA_BASE_NAME = "checkart";
    public static final String TEXT_TYPE = " TEXT"; // Works for date also !
    public static final String INT_TYPE = " INTEGER";
    public static final String BLOB_TYPE = " BLOB";
    public static final String UNIQUE_CONSTRAINT = " UNIQUE";


    // --------------------             Carpet-Image TABLE            -------------------------------------
    public static final String SQL_CREATE_TABLE_CARPET =
            "CREATE TABLE " + CheckArtContrat.CarpetTable.TABLE_NAME + "(" +
                    CheckArtContrat.CarpetTable._ID + INT_TYPE +" PRIMARY KEY AUTOINCREMENT," +
                    CheckArtContrat.CarpetTable.COLUMN_NAME_CARPET_NAME + TEXT_TYPE + "," +
                    CheckArtContrat.CarpetTable.COLUMN_NAME_CARPET_IMAGE  + BLOB_TYPE + "," +
                    CheckArtContrat.CarpetTable.COLUMN_NAME_CARPET_ORIGINE + TEXT_TYPE + ")";
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
    public void insertCarpet(String carpet_name, Bitmap carpet_image, String carpet_origin) throws SQLiteException {
        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues values = new  ContentValues();

        values.put(CheckArtContrat.CarpetTable.COLUMN_NAME_CARPET_NAME, carpet_name);
        values.put(CheckArtContrat.CarpetTable.COLUMN_NAME_CARPET_IMAGE, DbBitmapUtility.getBytes(carpet_image));
        values.put(CheckArtContrat.CarpetTable.COLUMN_NAME_CARPET_ORIGINE, carpet_origin);

        database.insertOrThrow(CheckArtContrat.CarpetTable.TABLE_NAME, null, values );
        database.close();
    }

    // find one carpet by id (row)
    public Cursor findCarpetById(Integer id) {

        SQLiteDatabase db = this.getReadableDatabase();

        String[] projection = {
                CheckArtContrat.CarpetTable.COLUMN_NAME_CARPET_NAME,
                CheckArtContrat.CarpetTable.COLUMN_NAME_CARPET_IMAGE,
                CheckArtContrat.CarpetTable.COLUMN_NAME_CARPET_ORIGINE,
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
                CheckArtContrat.CarpetTable.COLUMN_NAME_CARPET_NAME,
                CheckArtContrat.CarpetTable.COLUMN_NAME_CARPET_IMAGE,
                CheckArtContrat.CarpetTable.COLUMN_NAME_CARPET_ORIGINE,
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

        result.close();

        return result;
    }

    // Update Carpet content
    public void updateCarpet(Long id, String carpet_name, Bitmap carpet_image, String carpet_origin) throws SQLiteException {
        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(CheckArtContrat.CarpetTable.COLUMN_NAME_CARPET_IMAGE, DbBitmapUtility.getBytes(carpet_image));

        database.update(CheckArtContrat.CarpetTable.TABLE_NAME,values,
                CheckArtContrat.CarpetTable._ID + "= ?", new String[] {String.valueOf(id)} );
        database.close();
    }

    // delete carpet by id
    public void deleteCarpetWhereIdLike(Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(CheckArtContrat.CarpetTable.TABLE_NAME,"_id= ?", new String[] {String.valueOf(id)} );
        db.close();
    }



    // ----> find Images as a bitmap objects :


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
    }
    // ----------------------------  Image Table CRUD .  --------------------------------------------


}
