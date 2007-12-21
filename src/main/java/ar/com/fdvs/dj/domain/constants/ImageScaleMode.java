package ar.com.fdvs.dj.domain.constants;

import net.sf.jasperreports.engine.design.JRDesignImage;

public class ImageScaleMode {

	private byte value = JRDesignImage.SCALE_IMAGE_RETAIN_SHAPE;

	public static ImageScaleMode NO_RESIZE = new ImageScaleMode(JRDesignImage.SCALE_IMAGE_CLIP);
	public static ImageScaleMode FILL = new ImageScaleMode(JRDesignImage.SCALE_IMAGE_FILL_FRAME);
	public static ImageScaleMode FILL_PROPORTIONALLY = new ImageScaleMode(JRDesignImage.SCALE_IMAGE_RETAIN_SHAPE);
	
	public byte getValue() {
		return value;
	}

	private ImageScaleMode(byte mode){
		this.value = mode;
	}
}
