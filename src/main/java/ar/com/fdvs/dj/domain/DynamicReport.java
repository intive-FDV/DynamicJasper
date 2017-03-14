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

import ar.com.fdvs.dj.core.DJConstants;
import ar.com.fdvs.dj.core.JasperDesignDecorator;
import ar.com.fdvs.dj.domain.builders.StyleBuilder;
import ar.com.fdvs.dj.domain.constants.Stretching;
import ar.com.fdvs.dj.domain.entities.DJGroup;
import ar.com.fdvs.dj.domain.entities.DJVariable;
import ar.com.fdvs.dj.domain.entities.Entity;
import ar.com.fdvs.dj.domain.entities.Parameter;
import ar.com.fdvs.dj.domain.entities.columns.AbstractColumn;
import ar.com.fdvs.dj.domain.entities.columns.ExpressionColumn;
import ar.com.fdvs.dj.domain.entities.columns.SimpleColumn;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * One of the main classes of this product. It represents the report itself.
 */
public class DynamicReport extends DJBaseElement {

	private static final long serialVersionUID = Entity.SERIAL_VERSION_UID;
	protected Map<String, java.awt.Font> fontsMap = new HashMap<String, java.awt.Font>(); //<String, java.awt.Font>
	private String reportName;
    /**
     Sets the language of the expressions used in the report
     * (can be one of java, groovy, or javascript).
     *
     * Default is DJConstants#REPORT_LANGUAGE_JAVA
     *
     * @see DJConstants#REPORT_LANGUAGE_JAVA
     *      DJConstants#REPORT_LANGUAGE_GROOVY
     *      DJConstants#REPORT_LANGUAGE_JAVASCRIPT
     *
     */
    private String language = DJConstants.REPORT_LANGUAGE_JAVA;
    private String title;
	private boolean titleIsJrExpression = false;
	private String subtitle;
	private Style titleStyle = new StyleBuilder(false,"reportTitleStyle")
								.setStretching(Stretching.NO_STRETCH)
								.build();
	private Style subtitleStyle = new StyleBuilder(false,"reportSubtitleStyle")
									.setStretching(Stretching.NO_STRETCH)
									.build();
	private Locale reportLocale = Locale.getDefault();
	private String resourceBundle = null;
	private List<AbstractColumn> columns = new ArrayList<AbstractColumn>();

	//<DJGroup>
	private List<DJGroup> columnsGroups = new ArrayList<DJGroup>();

	//<DJChart>
	private List<DJChart> charts = new ArrayList<DJChart>();

	//<DJChart>
	private List<ar.com.fdvs.dj.domain.chart.DJChart> newCharts = new ArrayList<ar.com.fdvs.dj.domain.chart.DJChart>();
	
	private DynamicReportOptions options;

	/**
	 * List of ColumnProperty
	 * Other fields to register, not necessary assigned to columns
	 */
	private List<ColumnProperty> fields = new ArrayList<ColumnProperty>();

	//Other parameters needed (E.g. Subreports)
	private List<Parameter> parameters = new ArrayList<Parameter>();
	
	private List<DJVariable> variables = new ArrayList<DJVariable>();

	private Map<String, String> properties = new HashMap<String, String>();

	private String templateFileName = null;
	private boolean templateImportDatasets = false;
	private boolean templateImportFields = false;
	private boolean templateImportVariables = false;
	private boolean templateImportParameters = true;

	private List<AutoText> autoTexts = new ArrayList<AutoText>();

	private Map<String, Style> styles = new LinkedHashMap<String, Style>();
	private DJQuery query;

	private String whenNoDataText;
	private Style  whenNoDataStyle;
	private boolean whenNoDataShowTitle = true;
	private boolean whenNoDataShowColumnHeader = true;

	private boolean allowDetailSplit = true;

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

	private DJWaterMark waterMark;

	private JasperDesignDecorator jasperDesignDecorator;

    private String defaultEncoding;

	public DynamicReport() {}

	public void addStyle(Style style) {
		styles.put(style.getName(), style);
	}

	public Map<String, Style> getStyles() {
		return styles;
	}

	public void setStyles(Map<String, Style> styles) {
		this.styles = styles;
	}

	public void setProperty(String name, String value) {
		properties.put(name, value);
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<AbstractColumn> getColumns() {
		return columns;
	}

	public void setColumns(List<AbstractColumn> columns) {
		this.columns = columns;
	}

	public List<DJGroup> getColumnsGroups() {
		return columnsGroups;
	}

	public void setColumnsGroups(List<DJGroup> columnsGroups) {
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

	public void setTitleStyle(Style titleStyle, boolean isExp) {
		this.titleStyle = titleStyle;
		this.titleIsJrExpression = isExp;
	}

	public String getTemplateFileName() {
		return templateFileName;
	}

	public void setTemplateFileName(String templateFileName) {
		this.templateFileName = templateFileName;
	}

	public List<ColumnProperty> getFields() {
		return fields;
	}

	public void setFields(List<ColumnProperty> fields) {
		this.fields = fields;
	}

	public List<DJChart> getCharts() {
		return charts;
	}

	public void setCharts(List<DJChart> charts) {
		this.charts = charts;
	}

	public List<ar.com.fdvs.dj.domain.chart.DJChart> getNewCharts() {
		return newCharts;
	}

	public void setNewCharts(List<ar.com.fdvs.dj.domain.chart.DJChart> charts) {
		this.newCharts = charts;
	}
	
	public List<AutoText> getAutoTexts() {
		return autoTexts;
	}

	public void setAutoTexts(List<AutoText> autoTexts) {
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

	public Map<String, java.awt.Font> getFontsMap() {
		return fontsMap;
	}

	public void setFontsMap(Map<String, java.awt.Font> fontsMap) {
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

	public List<Parameter> getParameters() {
		return parameters;
	}
	
	public void setParameters(List<Parameter> parameters) {
		this.parameters = parameters;
	}

	public void addParameter(String name, String className){
		this.parameters.add(new Parameter(name, className));
	}

	public void addParameter(Parameter parameter){
		this.parameters.add(parameter);
	}

	public boolean isAllowDetailSplit() {
		return allowDetailSplit;
	}

	public void setAllowDetailSplit(boolean allowDetailSplit) {
		this.allowDetailSplit = allowDetailSplit;
	}

   public boolean isTemplateImportDatasets() {
		return templateImportDatasets;
	}

	public void setTemplateImportDatasets(boolean templateImportDatasets) {
		this.templateImportDatasets = templateImportDatasets;
	}

	public boolean isTemplateImportFields() {
		return templateImportFields;
	}

	public void setTemplateImportFields(boolean templateImportFields) {
		this.templateImportFields = templateImportFields;
	}

	public boolean isTemplateImportVariables() {
		return templateImportVariables;
	}

	public void setTemplateImportVariables(boolean templateImportVariables) {
		this.templateImportVariables = templateImportVariables;
	}

	public boolean isTemplateImportParameters() {
		return templateImportParameters;
	}

	public void setTemplateImportParameters(boolean templateImportParameters) {
		this.templateImportParameters = templateImportParameters;
	}

	public Map<String, String> getProperties() {
		return properties;
	}

	/**
	 * Must be a Map<String, String>
	 * @param properties
	 */
	public void setProperties(Map<String, String> properties) {
		this.properties = properties;
	}

	public String getReportName() {
		return reportName;
	}

	public void setReportName(String reportName) {
		this.reportName = reportName;
	}
	
	/**
	 * Collects all the fields from columns and also the fields not bounds to columns
	 * @return List<ColumnProperty>
	 */
	public List<ColumnProperty> getAllFields(){
		ArrayList<ColumnProperty> l = new ArrayList<ColumnProperty>();
		for (AbstractColumn abstractColumn : this.getColumns()) {
			if (abstractColumn instanceof SimpleColumn && !(abstractColumn instanceof ExpressionColumn)) {
				l.add(((SimpleColumn)abstractColumn).getColumnProperty());
			}
		}
		l.addAll(this.getFields());

		return l;
		
	}

	public boolean isTitleIsJrExpression() {
		return titleIsJrExpression;
	}

	public void setTitleIsJrExpression(boolean titleIsJrExpression) {
		this.titleIsJrExpression = titleIsJrExpression;
	}

	public List<DJVariable> getVariables() {
		return variables;
	}

	public void setVariables(List<DJVariable> variables) {
		this.variables = variables;
	}

    public String getLanguage() {
        return language;
    }

    /**
     * @see DynamicReport#language
     * @param language
     */
    public void setLanguage(String language) {
        this.language = language;
    }

	public DJWaterMark getWaterMark() {
		return waterMark;
	}

	public void setWaterMark(DJWaterMark waterMark) {
		this.waterMark = waterMark;
	}

	public JasperDesignDecorator getJasperDesignDecorator() {
		return jasperDesignDecorator;
	}

	public void setJasperDesignDecorator(JasperDesignDecorator jasperDesignDecorator) {
		this.jasperDesignDecorator = jasperDesignDecorator;
	}

    public String getDefaultEncoding() {
        return defaultEncoding;
    }

    public void setDefaultEncoding(String defaultEncoding) {
        this.defaultEncoding = defaultEncoding;
    }
}