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

package ar.com.fdvs.dj.domain.builders;

import java.util.ArrayList;
import ar.com.fdvs.dj.domain.ColumnsGroupVariableOperation;
import ar.com.fdvs.dj.domain.DynamicReport;
import ar.com.fdvs.dj.domain.DynamicReportOptions;
import ar.com.fdvs.dj.domain.Style;
import ar.com.fdvs.dj.domain.constants.GroupLayout;
import ar.com.fdvs.dj.domain.entities.ColumnsGroup;
import ar.com.fdvs.dj.domain.entities.ColumnsGroupVariable;
import ar.com.fdvs.dj.domain.entities.columns.AbstractColumn;
import ar.com.fdvs.dj.domain.entities.columns.GlobalGroupColumn;

/**
 * Builder created to give users a friendly way of creating a DynamicReport.</br>
 * </br>
 * Usage example: </br>
 * DynamicReportBuilder drb = new DynamicReportBuilder();
 * Integer margin = new Integer(20);
 * drb.addTitle("Clients List").addTitleStyle(titleStyle)
 * .addSubtitle("Clients without debt")
 * .addDetailHeight(new Integer(15))
 * .addLeftMargin(margin).addRightMargin(margin).addTopMargin(margin)
 * .addBottomMargin(margin)
 * .addPrintBackgroundOnOddRows(true).addOddRowBackgroundStyle(oddRowStyle)
 * .addColumnsPerPage(new Integer(1)).addColumnSpace(new Integer(5))
 * .addColumn(column1).addColumn(column2).build();
 * </br>
 * Like with all DJ's builders, it's usage must end with a call to build() mehtod.
 * </br>
 */
public class DynamicReportBuilder {

	private DynamicReport report = new DynamicReport();
	private DynamicReportOptions options = new DynamicReportOptions();
	private String globalTitle = "";
	private ArrayList globalFooterVariables;
	private ArrayList globalHeaderVariables;

	public DynamicReport build(){
		report.setOptions(options);
		if (globalFooterVariables!=null||globalHeaderVariables!=null) {
			buildGlobalGroup();
		}
		return report;
	}

	private void buildGlobalGroup() {
		ColumnsGroup globalGroup = new ColumnsGroup();
		globalGroup.setLayout(GroupLayout.EMPTY);
		GlobalGroupColumn globalCol = new GlobalGroupColumn();
		globalCol.setTitle(globalTitle);
		globalGroup.setColumnToGroupBy(globalCol);
		globalGroup.setHeaderVariables(globalHeaderVariables);
		globalGroup.setFooterVariables(globalFooterVariables);
		report.getColumnsGroups().add(globalGroup);
	}

	public DynamicReportBuilder addTitle(String title) {
		report.setTitle(title);
		return this;
	}

	public DynamicReportBuilder addSubtitle(String subtitle) {
		report.setSubtitle(subtitle);
		return this;
	}

	public DynamicReportBuilder addColumn(AbstractColumn column) {
		report.getColumns().add(column);
		return this;
	}

	public DynamicReportBuilder addGroup(ColumnsGroup group) {
		report.getColumnsGroups().add(group);
		return this;
	}

	public DynamicReportBuilder addHeaderHeight(Integer height) {
		options.setHeaderHeight(height);
		return this;
	}

	public DynamicReportBuilder addFooterHeight(Integer height) {
		options.setFooterHeight(height);
		return this;
	}

	public DynamicReportBuilder addDetailHeight(Integer height) {
		options.setDetailHeight(height);
		return this;
	}

	public DynamicReportBuilder addLeftMargin(Integer margin) {
		options.setLeftMargin(margin);
		return this;
	}

	public DynamicReportBuilder addRightMargin(Integer margin) {
		options.setRightMargin(margin);
		return this;
	}

	public DynamicReportBuilder addTopMargin(Integer margin) {
		options.setTopMargin(margin);
		return this;
	}

	public DynamicReportBuilder addBottomMargin(Integer margin) {
		options.setBottomMargin(margin);
		return this;
	}

	public DynamicReportBuilder addColumnsPerPage(Integer numColumns) {
		options.setColumnsPerPage(numColumns);
		return this;
	}

	public DynamicReportBuilder addColumnSpace(Integer columSpace) {
		options.setColumnSpace(columSpace);
		return this;
	}


	public DynamicReportBuilder addUseFullPageWidth(boolean useFullwidth) {
		options.setUseFullPageWidth(useFullwidth);
		return this;
	}

	public DynamicReportBuilder addTitleStyle(Style titleStyle) {
		this.report.setTitleStyle(titleStyle);
		return this;
	}

	public DynamicReportBuilder addSubtitleStyle(Style subtitleStyle) {
		this.report.setSubtitleStyle(subtitleStyle);
		return this;
	}

	public DynamicReportBuilder addPrintBackgroundOnOddRows(boolean printBackgroundOnOddRows) {
		this.options.setPrintBackgroundOnOddRows(printBackgroundOnOddRows);
		return this;
	}

	public DynamicReportBuilder addOddRowBackgroundStyle(Style oddRowBackgroundStyle) {
		this.options.setOddRowBackgroundStyle(oddRowBackgroundStyle);
		return this;
	}

	public DynamicReportBuilder addGlobalTitle(String title) {
		this.globalTitle = title;
		return this;
	}

	public DynamicReportBuilder addGlobalHeaderVariable(AbstractColumn col, ColumnsGroupVariableOperation op) {
		if (this.globalHeaderVariables == null)
			this.globalHeaderVariables = new ArrayList();
		this.globalHeaderVariables.add(new ColumnsGroupVariable(col, op));
		return this;
	}

	public DynamicReportBuilder addGlobalFooterVariable(AbstractColumn col, ColumnsGroupVariableOperation op) {
		if (this.globalFooterVariables == null)
			this.globalFooterVariables = new ArrayList();
		this.globalFooterVariables.add(new ColumnsGroupVariable(col, op));
		return this;
	}

	public DynamicReportBuilder addTitleHeight(Integer height) {
		options.setTitleHeight(height);
		return this;
	}

	public DynamicReportBuilder addSubtitleHeight(Integer height) {
		options.setSubtitleHeight(height);
		return this;
	}

}