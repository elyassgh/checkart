package irisi.digitalaube.checkart.filters;

import org.opencv.core.Mat;

public interface Filter {
    public abstract boolean apply(final Mat src, final Mat dst);
}
