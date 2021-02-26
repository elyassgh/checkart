package irisi.digitalaube.checkart.api.model;

public class Motif {
    private Long id;
    private String symbole;
    private String signification;

    public Motif(Long id, String symbole, String signification) {
        this.id = id;
        this.symbole = symbole;
        this.signification = signification;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSymbole() {
        return symbole;
    }

    public void setSymbole(String symbole) {
        this.symbole = symbole;
    }

    public String getSignification() {
        return signification;
    }

    public void setSignification(String signification) {
        this.signification = signification;
    }
}
