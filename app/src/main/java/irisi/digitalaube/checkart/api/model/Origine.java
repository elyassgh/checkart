package irisi.digitalaube.checkart.api.model;

public class Origine {
    private Long id;
    private String region;

    public Origine(Long id, String region) {
        this.id = id;
        this.region = region;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }
}
