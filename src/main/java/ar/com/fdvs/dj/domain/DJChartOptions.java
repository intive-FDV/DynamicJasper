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

package ar.com.fdvs.dj.domain;

import java.awt.Color;
import java.util.List;

import ar.com.fdvs.dj.domain.entities.Entity;

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
