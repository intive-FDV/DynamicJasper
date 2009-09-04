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

import ar.com.fdvs.dj.domain.entities.Entity;

public class DJCrosstabRow extends DJBaseElement{

	private static final long serialVersionUID = Entity.SERIAL_VERSION_UID;

	private ColumnProperty property;
	private int height = -1;
	private int headerWidth = -1;
	private int totalHeaderHeight = -1;

	private String title;

	private boolean showTotals = false;

	private Style totalStyle;
	private Style totalHeaderStyle;
	private Style headerStyle;
	
	private DJHyperLink link;

	public Style getTotalStyle() {
		return totalStyle;
	}
	public void setTotalStyle(Style totalStyle) {
		this.totalStyle = totalStyle;
	}
	public boolean isShowTotals() {
		return showTotals;
	}
	public void setShowTotals(boolean showTotals) {
		this.showTotals = showTotals;
	}
	public ColumnProperty getProperty() {
		return property;
	}
	public void setProperty(ColumnProperty property) {
		this.property = property;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	public int getHeaderWidth() {
		return headerWidth;
	}
	public void setHeaderWidth(int headerWidth) {
		this.headerWidth = headerWidth;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Style getTotalHeaderStyle() {
		return totalHeaderStyle;
	}
	public void setTotalHeaderStyle(Style totalHeaderStyle) {
		this.totalHeaderStyle = totalHeaderStyle;
	}
	public Style getHeaderStyle() {
		return headerStyle;
	}
	public void setHeaderStyle(Style headerStyle) {
		this.headerStyle = headerStyle;
	}
	public int getTotalHeaderHeight() {
		return totalHeaderHeight;
	}
	public void setTotalHeaderHeight(int totalHeaderHeight) {
		this.totalHeaderHeight = totalHeaderHeight;
	}
	public DJHyperLink getLink() {
		return link;
	}
	public void setLink(DJHyperLink link) {
		this.link = link;
	}

}
