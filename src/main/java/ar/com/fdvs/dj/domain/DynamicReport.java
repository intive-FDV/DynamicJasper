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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import ar.com.fdvs.dj.core.DJConstants;

/**
 * One of the main classes of this product. It represents the report itself.
 */
public class DynamicReport {

	private String title;
	private String subtitle;
	private Style titleStyle = new Style("reportTitleStyle");
	private Style subtitleStyle = new Style("reportSubtitleStyle");

	private Locale reportLocale = Locale.getDefault();
	private String resourceBundle = null;
	
	protected Map fontsMap = new HashMap(); //<String, java.awt.Font>

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

	private Map styles = new HashMap();
	
	private DJQuery query;
	
	private String whenNoDataText;
	private Style  whenNoDataStyle;
	private boolean whenNoDataShowTitle = true;
	private boolean whenNoDataShowColumnHeader = true;
	
	/**
	 * Defines the behaviour when the datasource is empty.
	 * Valid values are: 
	 * DJConstants.WHEN_NO_DATA_TYPE_NO_PAGES
	 * DJConstants.WHEN_NO_DATA_TYPE_BLANK_PAGE
	 * DJConstants.WHEN_NO_DATA_TYPE_ALL_SECTIONS_NO_DETAIL
	 * DJConstants.WHEN_NO_DATA_TYPE_NO_DATA_SECTION
	 * 
	 * Defatul is: DJConstants.WHEN_NO_DATA_TYPE_NO_PAGES
	 */
	private byte whenNoDataType = DJConstants.WHEN_NO_DATA_TYPE_NO_PAGES;

	/**********************
	 * Defines what to show if a missing resource is referenced
	 * Possible values are:<br>
	 * DJConstants.WHEN_RESOURCE_MISSING_TYPE_EMPTY: Leaves and empty field.<br/>
	 * DJConstants.WHEN_RESOURCE_MISSING_TYPE_ERROR: Throwns and exception.<br/>
	 * DJConstants.WHEN_RESOURCE_MISSING_TYPE_KEY: Shows the key of the missing resource.<br/>
	 * DJConstants.WHEN_RESOURCE_MISSING_TYPE_NULL: returns NULL
	 **********************/
	private byte whenResourceMissing = DJConstants.WHEN_RESOURCE_MISSING_TYPE_KEY;
	

	public void addStyle(Style style) {
		styles.put(style.getName(), style);
	}

	public Map getStyles() {
		return styles;
	}

	public void setStyles(Map styles) {
		this.styles = styles;
	}

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

	public Locale getReportLocale() {
		return reportLocale;
	}

	public void setReportLocale(Locale reportLocale) {
		this.reportLocale = reportLocale;
	}

	public String getResourceBundle() {
		return resourceBundle;
	}

	public void setResourceBundle(String resourceBundle) {
		this.resourceBundle = resourceBundle;
	}

	public DJQuery getQuery() {
		return query;
	}

	public void setQuery(DJQuery query) {
		this.query = query;
	}

	public Map getFontsMap() {
		return fontsMap;
	}

	public void setFontsMap(Map fontsMap) {
		this.fontsMap = fontsMap;
	}

	public byte getWhenNoDataType() {
		return whenNoDataType;
	}

	public void setWhenNoDataType(byte whenNoDataType) {
		this.whenNoDataType = whenNoDataType;
	}

	public byte getWhenResourceMissing() {
		return whenResourceMissing;
	}

	public void setWhenResourceMissing(byte whenResourceMissing) {
		this.whenResourceMissing = whenResourceMissing;
	}

	public String getWhenNoDataText() {
		return whenNoDataText;
	}

	public void setWhenNoDataText(String whenNoDataText) {
		this.whenNoDataText = whenNoDataText;
	}

	public Style getWhenNoDataStyle() {
		return whenNoDataStyle;
	}

	public void setWhenNoDataStyle(Style whenNoDataStyle) {
		this.whenNoDataStyle = whenNoDataStyle;
	}

	public boolean isWhenNoDataShowTitle() {
		return whenNoDataShowTitle;
	}

	public void setWhenNoDataShowTitle(boolean whenNoDataShowTitle) {
		this.whenNoDataShowTitle = whenNoDataShowTitle;
	}

	public boolean isWhenNoDataShowColumnHeader() {
		return whenNoDataShowColumnHeader;
	}

	public void setWhenNoDataShowColumnHeader(boolean whenNoDataShowColumnHeader) {
		this.whenNoDataShowColumnHeader = whenNoDataShowColumnHeader;
	}

}