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


    // --------------------             Image TABLE            -------------------------------------
    public static final String SQL_CREATE_TABLE_IMAGE =
            "CREATE TABLE " + CheckArtContrat.ImageTable.TABLE_NAME + "(" +
                    CheckArtContrat.ImageTable._ID + INT_TYPE +" PRIMARY KEY AUTOINCREMENT," +
                    CheckArtContrat.ImageTable.COLUMN_NAME_IMAGE_UNQ_NAME + TEXT_TYPE + UNIQUE_CONSTRAINT + "," +
                    CheckArtContrat.ImageTable.COLUMN_NAME_IMAGE_CTN  + BLOB_TYPE + "," +
                    CheckArtContrat.ImageTable.COLUMN_NAME_IMAGE_CARPET + INT_TYPE + "," +
                    // Foreign key mapping to CARPET TABLE ID
                    "FOREIGN KEY ("+ CheckArtContrat.ImageTable.COLUMN_NAME_IMAGE_CARPET +") REFERENCES " +
                    CheckArtContrat.CarpetTable.TABLE_NAME + " (_id)" + ")";
    public static final String SQL_DELETE_TABLE_IMAGE =
            "DROP TABLE IF EXISTS " + CheckArtContrat.ImageTable.TABLE_NAME;
    // -------------------- ---------------------------------- -------------------------------------


    // --------------------             Carpet TABLE           -------------------------------------
    public static final String SQL_CREATE_TABLE_CARPET =
            "CREATE TABLE " + CheckArtContrat.CarpetTable.TABLE_NAME + "(" +
                    CheckArtContrat.CarpetTable._ID + INT_TYPE + " PRIMARY KEY AUTOINCREMENT," +
                    CheckArtContrat.CarpetTable.COLUMN_NAME_CARPET_NAME + TEXT_TYPE + ")";
    public static final String SQL_DELETE_TABLE_CARPET =
            "DROP TABLE IF EXISTS " + CheckArtContrat.CarpetTable.TABLE_NAME;
    // -------------------- ---------------------------------- -------------------------------------


    // --------------------             Motif TABLE            -------------------------------------
    public static final String SQL_CREATE_TABLE_MOTIF = "";
    public static final String SQL_DELETE_TABLE_MOTIF =
            "DROP TABLE IF EXISTS " + CheckArtContrat.MotifTable.TABLE_NAME;
    // -------------------- ---------------------------------- -------------------------------------


    // --------------------             Origine TABLE          -------------------------------------
    public static final String SQL_CREATE_TABLE_ORIGINE = "";
    public static final String SQL_DELETE_TABLE_ORIGINE =
            "DROP TABLE IF EXISTS " + CheckArtContrat.OrigineTable.TABLE_NAME;
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
        db.execSQL(SQL_CREATE_TABLE_IMAGE);
        db.execSQL(SQL_CREATE_TABLE_CARPET);
        /*
                db.execSQL(SQL_CREATE_TABLE_MOTIF);
                db.execSQL(SQL_CREATE_TABLE_ORIGINE);
                db.execSQL(SQL_CREATE_TABLE_CARACTERSTIQUE);
         */
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_TABLE_IMAGE);
        db.execSQL(SQL_DELETE_TABLE_CARPET);
        /*
                db.execSQL(SQL_DELETE_TABLE_MOTIF);
                db.execSQL(SQL_DELETE_TABLE_ORIGINE);
                db.execSQL(SQL_DELETE_TABLE_CARACTERSTIQUE);
         */
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    // ----------------------------  Data Base Creation .  -----------------------------------------



    // ------------------ Bitmap-BLOB Convector for database storing and retrieving ----------------
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
    public void insertImage(String img_unq_name, Bitmap img_ctn, String fk_carpet) throws SQLiteException {
        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues values = new  ContentValues();

        values.put(CheckArtContrat.ImageTable.COLUMN_NAME_IMAGE_UNQ_NAME, img_unq_name);
        values.put(CheckArtContrat.ImageTable.COLUMN_NAME_IMAGE_CTN, DbBitmapUtility.getBytes(img_ctn));
        // Foreign KEY !
        values.put(CheckArtContrat.ImageTable.COLUMN_NAME_IMAGE_CARPET, fk_carpet);

        database.insertOrThrow(CheckArtContrat.ImageTable.TABLE_NAME, null, values );
        database.close();
    }

    // find one image by id (row)
    public Cursor findImageById(Integer id) {

        SQLiteDatabase db = this.getReadableDatabase();

        String[] projection = {
                CheckArtContrat.ImageTable.COLUMN_NAME_IMAGE_UNQ_NAME,
                CheckArtContrat.ImageTable.COLUMN_NAME_IMAGE_CTN,
                CheckArtContrat.ImageTable.COLUMN_NAME_IMAGE_CARPET,
        };

        String selection = CheckArtContrat.ImageTable._ID + " like ?";

        String[] selectArgs = { String.valueOf(id) };

        Cursor result = db.query(
                CheckArtContrat.ImageTable.TABLE_NAME,
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

    // find all image for certain carpet (rows)
    public Cursor findImagesOfCarpet(Integer carpet_id) {

        SQLiteDatabase db = this.getReadableDatabase();

        String[] projection = {
                CheckArtContrat.ImageTable.COLUMN_NAME_IMAGE_UNQ_NAME,
                CheckArtContrat.ImageTable.COLUMN_NAME_IMAGE_CTN,
                CheckArtContrat.ImageTable.COLUMN_NAME_IMAGE_CARPET,
        };

        String selection = CheckArtContrat.ImageTable.COLUMN_NAME_IMAGE_CARPET + " like ?";

        String[] selectArgs = { String.valueOf(carpet_id) };

        Cursor result = db.query(
                CheckArtContrat.ImageTable.TABLE_NAME,
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

    // find all images (rows)
    public Cursor findAllImages() {

        SQLiteDatabase db = this.getReadableDatabase();

        String[] projection = {
                CheckArtContrat.ImageTable.COLUMN_NAME_IMAGE_UNQ_NAME,
                CheckArtContrat.ImageTable.COLUMN_NAME_IMAGE_CTN,
                CheckArtContrat.ImageTable.COLUMN_NAME_IMAGE_CARPET,
        };

        Cursor result = db.query(
                CheckArtContrat.ImageTable.TABLE_NAME,
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

    // Update Image content
    public void updateImage(String img_unq_name, Bitmap img_ctn) throws SQLiteException {
        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues values = new  ContentValues();

        values.put(CheckArtContrat.ImageTable.COLUMN_NAME_IMAGE_CTN, DbBitmapUtility.getBytes(img_ctn));

        database.update(CheckArtContrat.ImageTable.TABLE_NAME,values,
                CheckArtContrat.ImageTable.COLUMN_NAME_IMAGE_UNQ_NAME + "= ?", new String[] {img_unq_name} );
        database.close();
    }

    // delete image by id
    public void deleteImageWhereIdLike(Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(CheckArtContrat.ImageTable.TABLE_NAME,"_id= ?", new String[] {String.valueOf(id)} );
        db.close();
    }

    // delete image by unique name
    public void deleteImage(String img_unq_name) throws SQLiteException {
        SQLiteDatabase database = this.getWritableDatabase();

        database.delete(CheckArtContrat.ImageTable.TABLE_NAME,
                CheckArtContrat.ImageTable.COLUMN_NAME_IMAGE_UNQ_NAME + "= ?", new String[] {img_unq_name} );
        database.close();
    }



    // ----> find Images as a bitmap objects :


    // find one image by id (bitmap)
    public Bitmap findBitmapImageById(Integer id) {
        Cursor img = this.findImageById(id);
        return DbBitmapUtility.getImage(img.getBlob(img.getColumnIndex(CheckArtContrat.ImageTable.COLUMN_NAME_IMAGE_CTN)));
    }

    // find all image for certain carpet (bitmap)
    public List<Bitmap> findBitmapImagesOfCarpet(Integer carpet_id) {
        Cursor imgs = this.findImagesOfCarpet(carpet_id);
        List<Bitmap> result = new ArrayList<>();

        while (imgs.moveToNext()) {
            result.add(DbBitmapUtility.getImage(
                    imgs.getBlob(imgs.getColumnIndex(CheckArtContrat.ImageTable.COLUMN_NAME_IMAGE_CTN)))
            );
        }

        return result;
    }

    // find all images (bitmap)
    public List<Bitmap> findAllBitmapImages() {
        Cursor imgs = this.findAllImages();
        List<Bitmap> result = new ArrayList<>();

        while (imgs.moveToNext()) {
            result.add(DbBitmapUtility.getImage(
                    imgs.getBlob(imgs.getColumnIndex(CheckArtContrat.ImageTable.COLUMN_NAME_IMAGE_CTN)))
            );
        }

        return result;
    }
    // ----------------------------  Image Table CRUD .  --------------------------------------------


}
