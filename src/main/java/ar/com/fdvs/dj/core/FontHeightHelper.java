package ar.com.fdvs.dj.core;

import ar.com.fdvs.dj.domain.constants.Font;

public class FontHeightHelper {
	
	/**
	 * Calculates the minium height needed for the specified font and size.
	 * You must also take in count if there is any padding, etc in your textfield.
	 * NOTE: Under development. only works with SansSerif
	 * @param font
	 * @return
	 */
	public static int getHeightFor(Font font) {
		int fontSize = font.getFontSize();
		if (fontSize == 10)
			return 15;
		else if (fontSize >= 18)
			return (int) (1.249* fontSize +0.978 + 1);
		else 
			return (int) (1.249* fontSize +0.978 + 4);
	}

}
