/*
 * Copyright (c) 2012, FDV Solutions S.A.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *     * Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 *     * Neither the name of the FDV Solutions S.A. nor the
 *       names of its contributors may be used to endorse or promote products
 *       derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL FDV Solutions S.A. BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
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
