package irisi.digitalaube.checkart.database;

import android.provider.BaseColumns;


public class CheckArtContrat {

    private CheckArtContrat() {}


    // This table present the app main images repository
    public static class CarpetTable implements BaseColumns {
        public static final String TABLE_NAME = "Carpet";
        public static final String COLUMN_NAME_TAPIS_NAME = "nom";
        public static final String COLUMN_NAME_TAPIS_DESCRIPTION = "description"; // BLOB
        public static final String COLUMN_NAME_TAPIS_TAILLE = "taille";
        public static final String COLUMN_NAME_TAPIS_COULEUR = "couleur";
        public static final String COLUMN_NAME_TAPIS_ORIGINE = "origine";
        public static final String COLUMN_NAME_TAPIS_MOTIF = "motif";
        public static final String COLUMN_NAME_TAPIS_W1 = "t";
        public static final String COLUMN_NAME_TAPIS_W2 = "w";
        public static final String COLUMN_NAME_TAPIS_W3 = "h";
        public static final String COLUMN_NAME_TAPIS_PHOTO = "photo";
        public static final String COLUMN_NAME_TAPIS_URI = "uri";

    }


    public static class MotifTable implements BaseColumns {
        public static final String TABLE_NAME = "Motif";
        public static final String COLUMN_NAME_MOTIF_SYMBOLE = "motif_symbole";
        public static final String COLUMN_NAME_MOTIF_SIGNIFICATION = "motif_signification";
    }

    public static class OrigineTable implements BaseColumns {
        public static final String TABLE_NAME = "Origine";
        public static final String COLUMN_NAME_REGION = "motif_region";
    }

    public static class TapisMotifTable implements BaseColumns {
        public static final String TABLE_NAME = "tapis_motif";
        public static final String COLUMN_NAME_MOTIF = "motif";
        public static final String COLUMN_NAME_TAPIS = "tapis";
    }

    public static class TapisOrigineTable implements BaseColumns {
        public static final String TABLE_NAME = "tapis_origine";
        public static final String COLUMN_NAME_ORIGINE = "origine";
        public static final String COLUMN_NAME_TAPIS = "tapis";
    }



}
