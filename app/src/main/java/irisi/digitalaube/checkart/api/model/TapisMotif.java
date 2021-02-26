package irisi.digitalaube.checkart.api.model;

public class TapisMotif {
    private Long id;
    private Tapis tapis;
    private Motif motif;

    public TapisMotif(Long id, Tapis tapis, Motif motif) {
        this.id = id;
        this.tapis = tapis;
        this.motif = motif;
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

    public Motif getMotif() {
        return motif;
    }

    public void setMotif(Motif motif) {
        this.motif = motif;
    }
}
