package ar.com.fdvs.dj.domain.entities.columns;

import ar.com.fdvs.dj.domain.constants.ImageScaleMode;

/**
 * Just for marking the column as ImageColum
 * @author Juan Manuel
 *
 */
public class ImageColumn extends SimpleColumn {
	
	private ImageScaleMode scaleMode = ImageScaleMode.FILL_PROPORTIONALLY;

	public ImageScaleMode getScaleMode() {
		return scaleMode;
	}

	public void setScaleMode(ImageScaleMode scaleMode) {
		this.scaleMode = scaleMode;
	}


}
