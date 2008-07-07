/*
 * Dynamic Jasper: A library for creating reports dynamically by specifying
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

import ar.com.fdvs.dj.domain.constants.Border;

import java.util.ArrayList;
import java.util.List;

public class DJCrosstab {

	private List rows = new ArrayList();
	private List columns = new ArrayList();
	private List measures = new ArrayList();

	private int height;
	private int width;

	private int bottomSpace = 10; //blank space AFTER the crosstab
	private int topSpace = 10; //blank space BEFORE the crosstab

	/**
	 * if true, the width will be the page width - margins
	 */
	private boolean useFullWidth = true;

	private DJDataSource datasource;

	private String mainHeaderTitle;

	private boolean automaticTitle = false;

	private Style headerStyle;

	private int colorScheme = 0;

	/**
	 * If not null or NO_BORDER, all cells will have this border
	 */
	private Border cellBorder;

	/**
	 * From here on, pass-throug properties
	 */
	private int columnBreakOffset = 10;

	public int getColumnBreakOffset() {
		return columnBreakOffset;
	}
	public void setColumnBreakOffset(int columnBreakOffset) {
		this.columnBreakOffset = columnBreakOffset;
	}
	public Style getHeaderStyle() {
		return headerStyle;
	}
	public void setHeaderStyle(Style headerStyle) {
		this.headerStyle = headerStyle;
	}
	public List getRows() {
		return rows;
	}
	public void setRows(List rows) {
		this.rows = rows;
	}
	public List getColumns() {
		return columns;
	}
	public void setColumns(List columns) {
		this.columns = columns;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public List getMeasures() {
		return measures;
	}
	public void setMeasures(List measures) {
		this.measures = measures;
	}

	public DJCrosstabMeasure getMeasure(int index) {
		return (DJCrosstabMeasure) measures.get(index);
	}
	public boolean isUseFullWidth() {
		return useFullWidth;
	}
	public void setUseFullWidth(boolean useFullWidth) {
		this.useFullWidth = useFullWidth;
	}
	public DJDataSource getDatasource() {
		return datasource;
	}
	public void setDatasource(DJDataSource datasource) {
		this.datasource = datasource;
	}
	public Border getCellBorder() {
		return cellBorder;
	}
	public void setCellBorder(Border cellBorder) {
		this.cellBorder = cellBorder;
	}
	public int getColorScheme() {
		return colorScheme;
	}
	public void setColorScheme(int colorScheme) {
		this.colorScheme = colorScheme;
	}
	public String getMainHeaderTitle() {
		return mainHeaderTitle;
	}
	public void setMainHeaderTitle(String mainHeaderTitle) {
		this.mainHeaderTitle = mainHeaderTitle;
	}
	public boolean isAutomaticTitle() {
		return automaticTitle;
	}
	public void setAutomaticTitle(boolean automaticTitle) {
		this.automaticTitle = automaticTitle;
	}
	public int getBottomSpace() {
		return bottomSpace;
	}
	public void setBottomSpace(int bottomSpace) {
		this.bottomSpace = bottomSpace;
	}
	public int getTopSpace() {
		return topSpace;
	}
	public void setTopSpace(int topSpace) {
		this.topSpace = topSpace;
	}

}
