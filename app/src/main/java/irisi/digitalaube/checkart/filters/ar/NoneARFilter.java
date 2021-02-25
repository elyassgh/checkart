package irisi.digitalaube.checkart.filters.ar;

import irisi.digitalaube.checkart.filters.NoneFilter;

public class NoneARFilter extends NoneFilter implements ARFilter {
    @Override
    public float[] getGLPose() {
        return null;
    }
}
