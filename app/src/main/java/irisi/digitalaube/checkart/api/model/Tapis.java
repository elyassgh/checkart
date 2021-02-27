package irisi.digitalaube.checkart.api.model;

import java.util.List;

public class Tapis {
    private Long id;
    private String nom;
    private String description;
    private String taille;
    private String couleur;
   private  String origine;
   private  String motif;
    private int w1;
    private int w2;
    private int w3;
    private String photo;
    private String uri;


    public Tapis(String nom, String description, String taille, String couleur, int w1, int w2, String photo, String uri) {
        this.nom = nom;
        this.description = description;
        this.taille = taille;
        this.couleur = couleur;
        this.w1 = w1;
        this.w2 = w2;
        this.photo = photo;
        this.uri = uri;
    }


    public Tapis() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public String getCouleur() {
        return couleur;
    }

    public void setCouleur(String couleur) {
        this.couleur = couleur;
    }

    public int getW1() {
        return w1;
    }

    public void setW1(int w1) {
        this.w1 = w1;
    }

    public int getW2() {
        return w2;
    }

    public void setW2(int w2) {
        this.w2 = w2;
    }

    public int getW3() {
        return w3;
    }

    public void setW3(int w3) {
        this.w3 = w3;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getOrigine() {
        return origine;
    }

    public void setOrigine(String origine) {
        this.origine = origine;
    }

    public String getMotif() {
        return motif;
    }

    public void setMotif(String motif) {
        this.motif = motif;
    }

    public String getTaille() {
        return taille;
    }

    public void setTaille(String taille) {
        this.taille = taille;
    }
}
