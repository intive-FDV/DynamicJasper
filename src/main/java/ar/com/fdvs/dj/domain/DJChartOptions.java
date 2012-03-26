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

package ar.com.fdvs.dj.domain;

import java.awt.Color;
import java.util.List;

import ar.com.fdvs.dj.domain.entities.Entity;

/**
 * @deprecated
 */
public class DJChartOptions extends DJBaseElement {

	private static final long serialVersionUID = Entity.SERIAL_VERSION_UID;

	public static final byte POSITION_FOOTER = 1;

	public static final byte POSITION_HEADER = 2;

	private Color backColor;
	private int height;
	private int width;
	private boolean centered;
	private byte position;
	private int y;
	private int x;
	private boolean showLegend;
	private boolean showLabels;
	private byte border;
	private List colors;
	private boolean useColumnsAsCategorie = false;

	public DJChartOptions() {
		this.showLegend = true;
		this.backColor = Color.WHITE;
		this.height = 200;
		this.width = 200;
		this.centered = true;
		this.position = POSITION_FOOTER;
		this.x = 0;
		this.y = 0;
		this.showLabels = true;
		this.border = 1;
		this.colors = DJChartColors.simpleColors();
		this.useColumnsAsCategorie = false;
	}

	public DJChartOptions(boolean showLegend, Color backColor, int height,
			int width, boolean centered, byte position, int y, int x,
			boolean showLabels, byte border, List colors) {
		this.showLegend = showLegend;
		this.backColor = backColor;
		this.height = height;
		this.width = width;
		this.centered = centered;
		this.position = position;
		this.x = x;
		this.y = y;
		this.showLabels = showLabels;
		this.border = border;
		this.colors = colors;
	}

	public Color getBackColor() {
		return backColor;
	}

	public void setBackColor(Color backColor) {
		this.backColor = backColor;
	}

	public boolean isCentered() {
		return centered;
	}

	public void setCentered(boolean centered) {
		this.centered = centered;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public byte getPosition() {
		return position;
	}

	public void setPosition(byte position) {
		this.position = position;
	}

	public boolean isShowLabels() {
		return showLabels;
	}

	public void setShowLabels(boolean showLabels) {
		this.showLabels = showLabels;
	}

	public boolean isShowLegend() {
		return showLegend;
	}

	public void setShowLegend(boolean showLegend) {
		this.showLegend = showLegend;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public byte getBorder() {
		return border;
	}

	public void setBorder(byte border) {
		this.border = border;
	}

	public List getColors() {
		return colors;
	}

	public void setColors(List colors) {
		this.colors = colors;
	}

	public boolean isUseColumnsAsCategorie() {
		return useColumnsAsCategorie;
	}

	public void setUseColumnsAsCategorie(boolean useColumnsAsCategorie) {
		this.useColumnsAsCategorie = useColumnsAsCategorie;
	}
}
