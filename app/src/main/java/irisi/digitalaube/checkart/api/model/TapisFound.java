package irisi.digitalaube.checkart.api.model;

public class TapisFound {
    TapisMat tapisMat;
    boolean found;

    public TapisMat getTapisMat() {
        return tapisMat;
    }

    public void setTapisMat(TapisMat tapisMat) {
        this.tapisMat = tapisMat;
    }

    public boolean isFound() {
        return found;
    }

    public void setFound(boolean found) {
        this.found = found;
    }
}
