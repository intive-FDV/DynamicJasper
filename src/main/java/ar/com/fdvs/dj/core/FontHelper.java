/*
 * DynamicJasper: A library for creating reports dynamically by specifying
 * columns, groups, styles, etc. at runtime. It also saves a lot of development
 * time in many cases! (http://sourceforge.net/projects/dynamicjasper)
 *
 * Copyright (C) 2008  FDV Solutions (http://www.fdvsolutions.com)
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 *
 * License as published by the Free Software Foundation; either
 *
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 *
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 *
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 *
 *
 */

package ar.com.fdvs.dj.core;

import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import ar.com.fdvs.dj.domain.constants.Font;

public class FontHelper {

	private static Graphics graphics = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB).getGraphics();

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
		java.awt.Font awtFont = java.awt.Font.decode(font.getStandardFontname());

		FontMetrics fm = graphics.getFontMetrics(awtFont);
		return fm;
	}


	public static void main(String[] args) {
		System.out.println(
				getWidthFor(Font.ARIAL_MEDIUM, "Ale Gomez")
		);
	}
}
