/*
 * Dynamic Jasper: A library for creating reports dynamically by specifying
 * columns, groups, styles, etc. at runtime. It also saves a lot of development
 * time in many cases! (http://sourceforge.net/projects/dynamicjasper)
 *
 * Copyright (C) 2007  FDV Solutions (http://www.fdvsolutions.com)
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

import java.util.ArrayList;
import java.util.List;

/**
 * One of the main classes of this product. It represents the report itself.
 */
public class DynamicReport {

	private String title;
	private String subtitle;
	private Style titleStyle = new Style();
	private Style subtitleStyle = new Style();

	//<AbstractColumn>
	private List columns = new ArrayList();
	//<ColumnsGroup>
	private List columnsGroups = new ArrayList();
	
	//<DJChart>
	private List charts = new ArrayList();
	
	private DynamicReportOptions options;
	
	//Other fields to register, not necesary aigned to columns
	private List fields = new ArrayList();

	private String templateFileName = null;
	
	private List autoTexts = new ArrayList();

	public DynamicReport() {}

	public DynamicReport(String title, List columns, List columnsGroups, List charts, DynamicReportOptions options) {
		this.title = title;
		this.columns = columns;
		this.columnsGroups = columnsGroups;
		this.charts = charts;
		this.options = options;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List getColumns() {
		return columns;
	}

	public void setColumns(List columns) {
		this.columns = columns;
	}

	public List getColumnsGroups() {
		return columnsGroups;
	}

	public void setColumnsGroups(List columnsGroups) {
		this.columnsGroups = columnsGroups;
	}

	public DynamicReportOptions getOptions() {
		return options;
	}

	public void setOptions(DynamicReportOptions options) {
		this.options = options;
	}

	public String getSubtitle() {
		return subtitle;
	}

	public void setSubtitle(String subtitle) {
		this.subtitle = subtitle;
	}

	public Style getSubtitleStyle() {
		return subtitleStyle;
	}

	public void setSubtitleStyle(Style subtitleStyle) {
		this.subtitleStyle = subtitleStyle;
	}

	public Style getTitleStyle() {
		return titleStyle;
	}

	public void setTitleStyle(Style titleStyle) {
		this.titleStyle = titleStyle;
	}

	public String getTemplateFileName() {
		return templateFileName;
	}

	public void setTemplateFileName(String templateFileName) {
		this.templateFileName = templateFileName;
	}

	public List getFields() {
		return fields;
	}

	public void setFields(List fields) {
		this.fields = fields;
	}

	public List getCharts() {
		return charts;
	}

	public void setCharts(List charts) {
		this.charts = charts;
	}

	public List getAutoTexts() {
		return autoTexts;
	}

	public void setAutoTexts(List autoTexts) {
		this.autoTexts = autoTexts;
	}

}