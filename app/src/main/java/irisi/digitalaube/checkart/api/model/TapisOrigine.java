package irisi.digitalaube.checkart.api.model;

public class TapisOrigine {
    private Long id;
    private Tapis tapis;
    private Origine origine;

    public TapisOrigine(Long id, Tapis tapis, Origine origine) {
        this.id = id;
        this.tapis = tapis;
        this.origine = origine;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Tapis getTapis() {
        return tapis;
    }

    public void setTapis(Tapis tapis) {
        this.tapis = tapis;
    }

    public Origine getOrigine() {
        return origine;
    }

    public void setOrigine(Origine origine) {
        this.origine = origine;
    }
}
