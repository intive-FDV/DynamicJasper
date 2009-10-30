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

package ar.com.fdvs.dj.domain.builders;

import java.util.Iterator;

import ar.com.fdvs.dj.domain.CustomExpression;
import ar.com.fdvs.dj.domain.DJCalculation;
import ar.com.fdvs.dj.domain.DJCrosstab;
import ar.com.fdvs.dj.domain.DJGroupLabel;
import ar.com.fdvs.dj.domain.DJValueFormatter;
import ar.com.fdvs.dj.domain.Style;
import ar.com.fdvs.dj.domain.constants.GroupLayout;
import ar.com.fdvs.dj.domain.entities.DJGroup;
import ar.com.fdvs.dj.domain.entities.DJGroupTemporalVariable;
import ar.com.fdvs.dj.domain.entities.DJGroupVariable;
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
	
	public GroupBuilder addVariable(String label, AbstractColumn column, DJCalculation operation) {
		group.getVariables().add(new DJGroupTemporalVariable(label, column, operation));
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