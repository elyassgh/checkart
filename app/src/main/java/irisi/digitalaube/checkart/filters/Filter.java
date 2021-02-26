package irisi.digitalaube.checkart.filters;

import org.opencv.core.Mat;

import irisi.digitalaube.checkart.api.model.TapisFound;

public interface Filter {
    public abstract TapisFound apply(final Mat src, final Mat dst);
}
