package ar.com.fdvs.dj.domain.entities.container;

import ar.com.fdvs.dj.domain.constants.HorizontalAlign;
import ar.com.fdvs.dj.domain.constants.ImageScaleMode;

import java.io.InputStream;

/**
 * Created by mamana on 2/17/16.
 */
public class ImageElement extends GraphicElement {

    private HorizontalAlign horizontalAlign = HorizontalAlign.LEFT;
    private String path;
    private InputStream inputStream;
    private ImageScaleMode scaleMode = ImageScaleMode.FILL_PROPORTIONALLY;

    public ImageElement(String path, int width, int height, ImageScaleMode scaleMode, HorizontalAlign horizontalAlign) {

        this.path = path;
        this.width = width;
        this.height = height;
        this.scaleMode = scaleMode;
        this.horizontalAlign = horizontalAlign;
    }

    public ImageElement(InputStream inputStream, int width, int height, ImageScaleMode scaleMode, HorizontalAlign horizontalAlign) {
        this.inputStream = inputStream;
        this.width = width;
        this.height = height;
        this.scaleMode = scaleMode;
        this.horizontalAlign = horizontalAlign;
    }

    public HorizontalAlign getHorizontalAlign() {
        return horizontalAlign;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public InputStream getInputStream() {
        return inputStream;
    }

    public void setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public ImageScaleMode getScaleMode() {
        return scaleMode;
    }

    public void setScaleMode(ImageScaleMode scaleMode) {
        this.scaleMode = scaleMode;
    }
}
