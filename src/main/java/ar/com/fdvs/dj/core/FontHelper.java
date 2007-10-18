package ar.com.fdvs.dj.core;

import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import ar.com.fdvs.dj.domain.constants.Font;

public class FontHelper {
	
	/**
	 * Calculates the minium height needed for the specified font and size.
	 * You must also take in count if there is any padding, etc in your textfield.
	 * NOTE: Under development. only works with SansSerif
	 * @param font
	 * @return
	 */
	public static int getHeightFor(Font font) {
		
		FontMetrics fm = getFontMetric(font);
		
		return fm.getHeight();
		
	}

	public static int getWidthFor(Font font, String text) {
		
		FontMetrics fm = getFontMetric(font);
		
		return fm.stringWidth(text);
	}

	/**
	 * @param font
	 * @return
	 */
	private static FontMetrics getFontMetric(Font font) {
		Graphics g = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB).getGraphics();
		java.awt.Font awtFont = java.awt.Font.decode(font.getStandardFontname());
		
		
		FontMetrics fm = g.getFontMetrics(awtFont);
		return fm;
	}

	
	public static void main(String[] args) {
		System.out.println(
		getWidthFor(Font.ARIAL_MEDIUM, "Ale Gomez")
		);
	}
}
