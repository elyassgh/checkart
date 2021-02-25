package irisi.digitalaube.checkart.database;

import android.provider.BaseColumns;


public class CheckArtContrat {

    private CheckArtContrat() {}


    // This table present the app main images repository
    public static class CarpetTable implements BaseColumns {

        public static final String TABLE_NAME = "Carpet";
        public static final String COLUMN_NAME_CARPET_NAME = "img_unique_name";
        public static final String COLUMN_NAME_CARPET_IMAGE = "img_content"; // BLOB
        public static final String COLUMN_NAME_CARPET_ORIGINE = "origine_name";

    }


    public static class MotifTable implements BaseColumns {

        public static final String TABLE_NAME = "Motif";
        public static final String COLUMN_NAME_MOTIF_NAME = "motif_name";

    }

    public static class CaracteristiqueTable implements BaseColumns {

        public static final String TABLE_NAME = "Caracteristique";
        public static final String COLUMN_NAME_ORIGINE_NAME = "carct_name";

    }

}
