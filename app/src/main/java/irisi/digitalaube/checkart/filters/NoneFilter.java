package irisi.digitalaube.checkart.filters;

import org.opencv.core.Mat;

import irisi.digitalaube.checkart.api.model.TapisFound;

public class NoneFilter implements Filter {

    @Override
    public TapisFound apply(final Mat src, final Mat dst) {
        // Do nothing.
        return  null;
    }
}