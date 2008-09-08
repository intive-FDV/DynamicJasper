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

package ar.com.fdvs.dj.domain.entities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ar.com.fdvs.dj.domain.Style;
import ar.com.fdvs.dj.domain.constants.GroupLayout;
import ar.com.fdvs.dj.domain.entities.columns.AbstractColumn;
import ar.com.fdvs.dj.domain.entities.columns.PropertyColumn;

/**
 * Entity created to handle groups of columns.</br>
 * Multiple groups can be created for a single report. In this case the result </br>
 * would be a nesting with the latest groups added to the report being the inner ones.
 */
public class ColumnsGroup implements Entity {

	//The column used to group by
	private PropertyColumn columnToGroupBy;

	public PropertyColumn getColumnToGroupBy() {
		return columnToGroupBy;
	}

	public void setColumnToGroupBy(PropertyColumn columnToGroupBy) {
		this.columnToGroupBy = columnToGroupBy;
	}

	/**
	 * Map<Column, Style>
	 */
	private Map columnHeaderStyles = new HashMap();
	private Style defaultColumnHeaederStyle;

	//<ColumnsGroupVariable>
	private List headerVariables = new ArrayList();
	//<ColumnsGroupVariable>
	private List footerVariables = new ArrayList();
	private Integer headerHeight = new Integer(20);
	private Integer footerHeight = new Integer(20);
	private GroupLayout layout = GroupLayout.DEFAULT;
	private List footerSubreports = new ArrayList();
	private List headerSubreports = new ArrayList();

	private List headerCrosstabs = new ArrayList();
	private List footerCrosstabs = new ArrayList();

	private Boolean startInNewPage = Boolean.FALSE;
	private Boolean startInNewColumn = Boolean.FALSE;

	/**
	 * Default Style for variables when showing in footer.
	 * First looks for the style at the ColumnsGroupVariable, then the default, finally
	 * it uses the columns style.
	 */
	private Style defaulFooterVariableStyle;

	/**
	 * Default Style for variables when showing in header.
	 * The lookup order is the same as for "defaulFooterStyle"
	 */
	private Style defaulHeaderVariableStyle;

	public Style getDefaulFooterVariableStyle() {
		return defaulFooterVariableStyle;
	}

	public void setDefaulFooterVariableStyle(Style defaulFooterStyle) {
		this.defaulFooterVariableStyle = defaulFooterStyle;
	}

	public Style getDefaulHeaderVariableStyle() {
		return defaulHeaderVariableStyle;
	}

	public void setDefaulHeaderVariableStyle(Style defaulHeaderStyle) {
		this.defaulHeaderVariableStyle = defaulHeaderStyle;
	}

	public List getFooterVariables() {
		return footerVariables;
	}

	public void setFooterVariables(ArrayList footerVariables) {
		this.footerVariables = footerVariables;
	}

	public List getHeaderVariables() {
		return headerVariables;
	}

	public void setHeaderVariables(ArrayList headerVariables) {
		this.headerVariables = headerVariables;
	}

	public Integer getFooterHeight() {
		return footerHeight;
	}

	public void setFooterHeight(Integer footerHeight) {
		this.footerHeight = footerHeight;
	}

	public Integer getHeaderHeight() {
		return headerHeight;
	}

	public void setHeaderHeight(Integer headerHeight) {
		this.headerHeight = headerHeight;
	}

	public GroupLayout getLayout() {
		return layout;
	}

	public void setLayout(GroupLayout layout) {
		this.layout = layout;
	}

	public List getFooterSubreports() {
		return footerSubreports;
	}

	public List getHeaderSubreports() {
		return headerSubreports;
	}

	public Boolean getStartInNewPage() {
		return startInNewPage;
	}

	public void setStartInNewPage(Boolean startInNewPage) {
		this.startInNewPage = startInNewPage;
	}

	public Boolean getStartInNewColumn() {
		return startInNewColumn;
	}

	public void setStartInNewColumn(Boolean startInNewColumn) {
		this.startInNewColumn = startInNewColumn;
	}

	public List getHeaderCrosstabs() {
		return headerCrosstabs;
	}

	public void setHeaderCrosstabs(List headerCrosstabs) {
		this.headerCrosstabs = headerCrosstabs;
	}

	public List getFooterCrosstabs() {
		return footerCrosstabs;
	}

	public void setFooterCrosstabs(List footerCrosstabs) {
		this.footerCrosstabs = footerCrosstabs;
	}

	public Map getColumnHeaderStyles() {
		return columnHeaderStyles;
	}

	public void setColumnHeaderStyles(Map columnHeaderStyles) {
		this.columnHeaderStyles = columnHeaderStyles;
	}

	public void addColumHeaderStyle(AbstractColumn col, Style style) {
		columnHeaderStyles.put(col, style);
	}

	public Style getColumnHeaderStyle(AbstractColumn col) {
		if (this.columnHeaderStyles == null)
			return null;

		return (Style) this.columnHeaderStyles.get(col);
	}

	public Style getDefaultColumnHeaederStyle() {
		return defaultColumnHeaederStyle;
	}

	public void setDefaultColumnHeaederStyle(Style defaultColumnHeaederStyle) {
		this.defaultColumnHeaederStyle = defaultColumnHeaederStyle;
	}

}
