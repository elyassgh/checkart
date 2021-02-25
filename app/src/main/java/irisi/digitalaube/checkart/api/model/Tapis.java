package irisi.digitalaube.checkart.api.model;

import java.util.List;

public class Tapis {
    private Long id;
    private String nom;
    private String description;
    private float taille;
    private String couleur;
    private List<TapisOrigine> tapis_origines;
    private List<TapisMotif> tapis_motifs;
    private int w1;
    private int w2;
    private int w3;
    private byte[] photo;
    private String uri;
}
