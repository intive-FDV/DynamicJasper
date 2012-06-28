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

package ar.com.fdvs.dj.domain.builders;

import java.util.Iterator;

import ar.com.fdvs.dj.domain.ColumnProperty;
import ar.com.fdvs.dj.domain.CustomExpression;
import ar.com.fdvs.dj.domain.DJCalculation;
import ar.com.fdvs.dj.domain.DJCrosstab;
import ar.com.fdvs.dj.domain.DJGroupLabel;
import ar.com.fdvs.dj.domain.DJValueFormatter;
import ar.com.fdvs.dj.domain.Style;
import ar.com.fdvs.dj.domain.constants.GroupLayout;
import ar.com.fdvs.dj.domain.entities.DJGroup;
import ar.com.fdvs.dj.domain.entities.DJGroupVariable;
import ar.com.fdvs.dj.domain.entities.DJGroupVariableDef;
import ar.com.fdvs.dj.domain.entities.Subreport;
import ar.com.fdvs.dj.domain.entities.columns.AbstractColumn;
import ar.com.fdvs.dj.domain.entities.columns.PropertyColumn;

/**
 * Builder created to give users a friendly way of adding groups to a report.</br>
 * </br>
 * Usage example: </br>
 * GroupBuilder gb1 = new GroupBuilder();
 * ColumnsGroup g1 = gb1.addCriteriaColumn((PropertyColumn) columnState)</br>
 * .addFooterVariable(columnAmount,ColumnsGroupVariableOperation.SUM)</br>
 * .addFooterVariable(columnaQuantity,ColumnsGroupVariableOperation.SUM)</br>
 * .addGroupLayout(GroupLayout.VALUE_IN_HEADER_WITH_HEADERS)</br>
 * .build();</br>
 * </br>
 * Like with all DJ's builders, it's usage must end with a call to build() mehtod.
 * </br>
 */
public class GroupBuilder {

	private DJGroup group = new DJGroup();

	private Style defaultFooterVariableStyle;
	private Style defaultHeaderVariableStyle;

	public GroupBuilder() {		
	}

	public GroupBuilder(String name) {		
		group.setName(name);
	}
	
	public DJGroup build(){
		//Apply Styles if any (for variables)
		for (Iterator iterator = group.getHeaderVariables().iterator(); iterator.hasNext();) {
			DJGroupVariable var = (DJGroupVariable) iterator.next();
			if (defaultHeaderVariableStyle != null)
				var.setStyle(defaultHeaderVariableStyle);
		}

		for (Iterator iterator = group.getFooterVariables().iterator(); iterator.hasNext();) {
			DJGroupVariable var = (DJGroupVariable) iterator.next();
			if (defaultFooterVariableStyle != null)
				var.setStyle(defaultFooterVariableStyle);
		}

		return group;
	}

	public GroupBuilder setCriteriaColumn(PropertyColumn column) {
		group.setColumnToGroupBy(column);
		return this;
	}

	public GroupBuilder addHeaderVariable(DJGroupVariable variable) {
		group.addHeaderVariable(variable);
		return this;
	}

	public GroupBuilder addHeaderVariable(AbstractColumn column, DJCalculation operation) {
		group.addHeaderVariable(new DJGroupVariable(column,operation));
		return this;
	}

	public GroupBuilder addHeaderVariable(AbstractColumn column, DJCalculation operation, Style style) {
		group.addHeaderVariable(new DJGroupVariable(column,operation,style));
		return this;
	}

	public GroupBuilder addHeaderVariable(AbstractColumn column, DJCalculation operation, Style style, DJValueFormatter formatter) {
		group.addHeaderVariable(new DJGroupVariable(column,operation,style,formatter));
		return this;
	}	
	
	public GroupBuilder addHeaderVariable(AbstractColumn column, DJCalculation operation, Style style, DJValueFormatter formatter, DJGroupLabel glabel1) {
		group.addHeaderVariable(new DJGroupVariable(column,operation,style,formatter,glabel1));
		return this;
	}	

	public GroupBuilder addHeaderVariable(AbstractColumn column, CustomExpression valueExpression) {
		group.addHeaderVariable(new DJGroupVariable(column, valueExpression));
		return this;
	}
	
	public GroupBuilder addHeaderVariable(AbstractColumn column, CustomExpression valueExpression, Style style) {
		group.addHeaderVariable(new DJGroupVariable(column, valueExpression, style));
		return this;
	}
	
	public GroupBuilder addFooterVariable(DJGroupVariable variable) {
		group.addFooterVariable(variable);
		return this;
	}
	
	public GroupBuilder addFooterVariable(AbstractColumn column3, DJCalculation operation) {
		group.addFooterVariable(new DJGroupVariable(column3,operation));
		return this;
	}
	
	public GroupBuilder addFooterVariable(AbstractColumn column3, DJCalculation operation, Style style) {
		group.addFooterVariable(new DJGroupVariable(column3,operation,style));
		return this;
	}

	public GroupBuilder addFooterVariable(AbstractColumn column3, DJCalculation operation, Style style, DJValueFormatter valueFormatter) {
		group.addFooterVariable(new DJGroupVariable(column3,operation,style,valueFormatter));
		return this;
	}

	public GroupBuilder addFooterVariable(AbstractColumn column3, DJCalculation operation, Style style, DJValueFormatter valueFormatter, DJGroupLabel label) {
		group.addFooterVariable(new DJGroupVariable(column3,operation,style,valueFormatter,label));
		return this;
	}

	public GroupBuilder addFooterVariable(AbstractColumn column, CustomExpression valueExpression) {
		group.addFooterVariable(new DJGroupVariable(column, valueExpression));
		return this;
	}
	
	public GroupBuilder addFooterVariable(AbstractColumn column, CustomExpression valueExpression, Style style) {
		group.addFooterVariable(new DJGroupVariable(column, valueExpression, style));
		return this;
	}
	
	/**
	 * Registers a variable for later reference inside custom expressions.
	 * The new variable will perform the calculation using the field information passed in the ColumnProperty
	 * parameter. Such ColumnProperty will be properly registered in the report design as a field ($F{...})
	 * 
	 * 
	 * @param name
	 * @param col
	 * @param op
	 * @return
	 */
	public GroupBuilder addVariable(String name, String property, String className, DJCalculation operation) {		
		group.getVariables().add(new DJGroupVariableDef(name, new ColumnProperty(property,className), operation));
		return this;
	}

	public GroupBuilder addVariable(String name, ColumnProperty prop, DJCalculation operation) {		
		group.getVariables().add(new DJGroupVariableDef(name, prop, operation));
		return this;
	}
	
	public GroupBuilder addVariable(String name, AbstractColumn column, DJCalculation operation) {
		group.getVariables().add(new DJGroupVariableDef(name, column, operation));
		return this;
	}
	
	/**
	 * Height for headers (column titles) 
	 * @param height
	 * @return
	 */
	public GroupBuilder setHeaderHeight(Integer height) {
		group.setHeaderHeight(height);
		return this;
	}

	/**
	 * 
	 * @param height
	 * @param fitHeightToContent is false, an empty space will be left
	 * @return
	 */
	public GroupBuilder setHeaderHeight(Integer height, boolean fitHeightToContent) {
		group.setHeaderHeight(height);
		group.setFitHeaderHeightToContent(fitHeightToContent);
		return this;
	}

	/**
	 * Height for footer band. NOT USED AT ALL 
	 * @param height
	 * @return
	 */
	public GroupBuilder setFooterHeight(Integer height) {
		group.setFooterHeight(height);
		return this;
	}
	
	/**
	 * 
	 * @param height
	 * @param fitHeightToConent if false, an empty space will be left
	 * @return
	 */
	public GroupBuilder setFooterHeight(Integer height,boolean fitHeightToConent) {
		group.setFooterHeight(height);
		group.setFitFooterHeightToContent(fitHeightToConent);
		return this;
	}

	/**
	 * height for values shown in group header such as calculations, current value, etc.
	 * @param height
	 * @return
	 */
	public GroupBuilder setHeaderVariablesHeight(Integer height) {
		group.setHeaderVariablesHeight(height);
		return this;
	}
	
	/**
	 * height for values shown in group footer such as calculations, current value, etc.
	 * @param height
	 * @return
	 */
	public GroupBuilder setFooterVariablesHeight(Integer height) {
		group.setFooterVariablesHeight(height);
		return this;
	}

	public GroupBuilder setGroupLayout(GroupLayout layout) {
		group.setLayout(layout);
		return this;
	}

	public GroupBuilder setDefaultFooterVariableStyle(Style defaultFooterVariableStyle) {
		this.defaultFooterVariableStyle = defaultFooterVariableStyle;
		return this;
	}

	public GroupBuilder setDefaultHeaderVariableStyle(Style defaultHeaderVariableStyle) {
		this.defaultHeaderVariableStyle = defaultHeaderVariableStyle;
		return this;
	}

	public GroupBuilder addHeaderSubreport(Subreport subreport) {
		group.getHeaderSubreports().add(subreport);
		return this;
	}

	public GroupBuilder addFooterSubreport(Subreport subreport) {
		group.getFooterSubreports().add(subreport);
		return this;
	}

	public GroupBuilder setStartInNewPage(Boolean bool) {
		group.setStartInNewPage(bool);
		return this;
	}

	public GroupBuilder setStartInNewPage(boolean bool) {
		group.setStartInNewPage(Boolean.valueOf(bool));
		return this;
	}

	public GroupBuilder setStartInNewColumn(Boolean bool) {
		group.setStartInNewColumn(bool);
		return this;
	}

	public GroupBuilder setStartInNewColumn(boolean bool) {
		group.setStartInNewColumn(Boolean.valueOf(bool));
		return this;
	}

	public GroupBuilder addColumnHeaderStyle(AbstractColumn column, Style style) {
		group.addColumHeaderStyle(column, style);
		return this;
	}

	public GroupBuilder setDefaultColumnHeaderStyle(Style style) {
		group.setDefaultColumnHeaederStyle(style);
		return this;
	}

	/***
	 * pass-through property to setup group header band "allowSplit" property.
	 * When FALSE, if the content reaches end of the page, the whole band gets pushed
	 * to the next page.
	 * @param headerSplit
	 * @return
	 */
	public GroupBuilder setAllowHeaderSplit(boolean headerSplit) {
		group.setAllowHeaederSplit(headerSplit);
		return this;
	}


	/***
	 * pass-through property to setup group footer band "allowSplit" property.
	 * When FALSE, if the content reaches end of the page, the whole band gets pushed
	 * to the next page.
	 * @param footerSplit
	 * @return
	 */
	public GroupBuilder setAllowFooterSplit(boolean footerSplit) {
		group.setAllowFooterSplit(footerSplit);
		return this;
	}

	/***
	 * pass-through property to setup group header and footer bands "allowSplit" property.
	 * When FALSE, if the content reaches end of the page, the whole band gets pushed
	 * to the next page.
	 * @param headerSplit
	 * @param footerSplit
	 * @return
	 */
	public GroupBuilder setAllowSplitting(boolean headerSplit, boolean footerSplit) {
		group.setAllowHeaederSplit(headerSplit);
		group.setAllowFooterSplit(footerSplit);
		return this;
	}

	/**
	 * Footer labels are placed at the right of of the footer variables. 
	 * It should be used as a general footer to describe variables
	 * @param label
	 * @return
	 */
	public GroupBuilder setFooterLabel(DJGroupLabel label) {
		group.setFooterLabel(label);
		return this;
	}


	/**
	 * If the group is configured to print column names, they will be printed on every page 
	 * (even if a group is splitted in two pages)
	 * NOTE: this may cause unexpected results if header variables are present.
	 * @param bool
	 * @return
	 */
	public GroupBuilder setReprintHeaderOnEachPage(boolean bool) {
		group.setReprintHeaderOnEachPage(Boolean.valueOf(bool));
		return this;
	}

	public GroupBuilder addHeaderCrosstab(DJCrosstab cross){
		group.addHeaderCrosstab(cross);
		return this;
	}

	public GroupBuilder addFooterCrosstab(DJCrosstab cross){
		group.addFooterCrosstab(cross);
		return this;
	}


}