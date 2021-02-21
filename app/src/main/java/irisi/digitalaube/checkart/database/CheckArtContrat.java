package irisi.digitalaube.checkart.database;

import android.provider.BaseColumns;


public class CheckArtContrat {

    private CheckArtContrat() {}


    // This table present the app main images repository
    public static class ImageTable implements BaseColumns {

        public static final String TABLE_NAME = "Image";
        public static final String COLUMN_NAME_IMAGE_UNQ_NAME = "img_unique_name";
        public static final String COLUMN_NAME_IMAGE_CTN = "img_content"; // BLOB
        public static final String COLUMN_NAME_IMAGE_CARPET = "carpet";

    }

    public static class CarpetTable implements BaseColumns {

        public static final String TABLE_NAME = "Carpet";
        public static final String COLUMN_NAME_CARPET_NAME = "carpet_name";

    }

    public static class MotifTable implements BaseColumns {

        public static final String TABLE_NAME = "Motif";
        public static final String COLUMN_NAME_MOTIF_NAME = "motif_name";

    }

    public static class OrigineTable implements BaseColumns {

        public static final String TABLE_NAME = "Origine";
        public static final String COLUMN_NAME_ORIGINE_NAME = "origine_name";

    }

    public static class CaracteristiqueTable implements BaseColumns {

        public static final String TABLE_NAME = "Caracteristique";
        public static final String COLUMN_NAME_ORIGINE_NAME = "carct_name";

    }

}
