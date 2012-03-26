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

package ar.com.fdvs.dj.domain.entities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.list.UnmodifiableList;

import ar.com.fdvs.dj.domain.DJBaseElement;
import ar.com.fdvs.dj.domain.DJCrosstab;
import ar.com.fdvs.dj.domain.DJGroupLabel;
import ar.com.fdvs.dj.domain.DynamicReportOptions;
import ar.com.fdvs.dj.domain.Style;
import ar.com.fdvs.dj.domain.constants.GroupLayout;
import ar.com.fdvs.dj.domain.entities.columns.AbstractColumn;
import ar.com.fdvs.dj.domain.entities.columns.PropertyColumn;

/**
 * Entity created to handle groups of columns.</br>
 * Multiple groups can be created for a single report. In this case the result </br>
 * would be a nesting with the latest groups added to the report being the inner ones.
 */
public class DJGroup extends DJBaseElement {

	private static final long serialVersionUID = Entity.SERIAL_VERSION_UID;
	
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

	//<DJGroupVariable>
	private List headerVariables = new ArrayList();
	//<DJGroupVariable>
	private List footerVariables = new ArrayList();
	//<DJGroupTemporalVariable>
	private List variables = new ArrayList();
	
	private boolean fitHeaderHeightToContent = true;
	private boolean fitFooterHeightToContent = true;
	private String name;
	
	public boolean isFitHeaderHeightToContent() {
		return fitHeaderHeightToContent;
	}

	public void setFitHeaderHeightToContent(boolean fitHeaderHeightToContent) {
		this.fitHeaderHeightToContent = fitHeaderHeightToContent;
	}

	public boolean isFitFooterHeightToContent() {
		return fitFooterHeightToContent;
	}

	/**
	 * When false, the footer height is not shrink to its content (variables in general), leaving a white space 
	 * @param fitFooterHeightToContent
	 */
	public void setFitFooterHeightToContent(boolean fitFooterHeightToContent) {
		this.fitFooterHeightToContent = fitFooterHeightToContent;
	}

	private Integer headerHeight = DynamicReportOptions.DEFAULT_HEADER_HEIGHT; //for headers
	private Integer footerHeight = DynamicReportOptions.DEFAULT_FOOTER_VARIABLES_HEIGHT; //for headers
	
	private Integer headerVariablesHeight = null; //for values such as calculations, current value, etc.
	private Integer footerVariablesHeight = null; //for values such as calculations, current value, etc.
	
	private GroupLayout layout = GroupLayout.DEFAULT;
	private List footerSubreports = new ArrayList();
	private List headerSubreports = new ArrayList();

	private List headerCrosstabs = new ArrayList();
	private List footerCrosstabs = new ArrayList();

	private Boolean startInNewPage = Boolean.FALSE;
	private Boolean startInNewColumn = Boolean.FALSE;
	
	/**
	 * If the group is configured to print column names, they will be printed on every page 
	 * (even if a group is splitted in two pages)
	 * NOTE: this may cause unexpected results if header variables are present. 
	 */
	private Boolean reprintHeaderOnEachPage = Boolean.FALSE;
	
	private DJGroupLabel footerLabel; //general label, goes at the right of the group variables

	/**
	 * pass-through property to setup group header band "allowSplit" property.
	 * When FALSE, if the content reaches end of the page, the whole band gets pushed
	 * to the next page.
	 */
	private boolean allowHeaderSplit = true;

	/**
	 * pass-through property to setup group footer band "allowSplit" property.
	 * When FALSE, if the content reaches end of the page, the whole band gets pushed
	 * to the next page.
	 */
	private boolean allowFooterSplit = true;

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
		return UnmodifiableList.decorate(footerVariables);
	}

	public void setFooterVariables(ArrayList footerVariables) {
		this.footerVariables = footerVariables;
	}

	public List getHeaderVariables() {
		return  UnmodifiableList.decorate(headerVariables);
	}

	public void setHeaderVariables(ArrayList headerVariables) {
		this.headerVariables = headerVariables;
	}

	public List getVariables() {
		return variables;
	}

	public void setVariables(ArrayList variables) {
		this.variables = variables;
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

	public boolean isAllowHeaderSplit() {
		return allowHeaderSplit;
	}

	public void setAllowHeaederSplit(boolean allowHeaederSplit) {
		this.allowHeaderSplit = allowHeaederSplit;
	}

	public boolean isAllowFooterSplit() {
		return allowFooterSplit;
	}

	public void setAllowFooterSplit(boolean allowFooterSplit) {
		this.allowFooterSplit = allowFooterSplit;
	}

	public Integer getHeaderVariablesHeight() {
		return headerVariablesHeight;
	}

	public void setHeaderVariablesHeight(Integer headerVariablesHeight) {
		this.headerVariablesHeight = headerVariablesHeight;
	}

	public Integer getFooterVariablesHeight() {
		return footerVariablesHeight;
	}

	public void setFooterVariablesHeight(Integer footerVariablesHeight) {
		this.footerVariablesHeight = footerVariablesHeight;
	}
	
	public void addHeaderVariable(DJGroupVariable var) {
		headerVariables.add(var);
		var.setGroup(this);
	}
	
	public void addFooterVariable(DJGroupVariable var) {
		footerVariables.add(var);
		var.setGroup(this);
	}

	public void addVariable(DJGroupVariableDef var) {
		variables.add(var);
	}
	
	public DJGroupLabel getFooterLabel() {
		return footerLabel;
	}

	public void setFooterLabel(DJGroupLabel footerLabel) {
		this.footerLabel = footerLabel;
	}

	public Boolean getReprintHeaderOnEachPage() {
		return reprintHeaderOnEachPage;
	}

	public void setReprintHeaderOnEachPage(Boolean reprintHeaderOnEachPage) {
		this.reprintHeaderOnEachPage = reprintHeaderOnEachPage;
	}

	public void addHeaderCrosstab(DJCrosstab cross){
		this.headerCrosstabs.add(cross);
	}
	
	public void addFooterCrosstab(DJCrosstab cross){
		this.footerCrosstabs.add(cross);
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
}
