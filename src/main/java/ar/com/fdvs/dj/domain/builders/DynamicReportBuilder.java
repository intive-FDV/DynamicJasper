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

import ar.com.fdvs.dj.domain.ColumnProperty;
import ar.com.fdvs.dj.domain.ColumnsGroupVariableOperation;
import ar.com.fdvs.dj.domain.DJChart;
import ar.com.fdvs.dj.domain.DynamicReport;
import ar.com.fdvs.dj.domain.DynamicReportOptions;
import ar.com.fdvs.dj.domain.ImageBanner;
import ar.com.fdvs.dj.domain.Style;
import ar.com.fdvs.dj.domain.constants.GroupLayout;
import ar.com.fdvs.dj.domain.constants.Page;
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

	protected DynamicReport report = new DynamicReport();
	protected DynamicReportOptions options = new DynamicReportOptions();
	protected String globalTitle = "";
	protected ArrayList globalFooterVariables;
	protected ArrayList globalHeaderVariables;

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
	public DynamicReportBuilder addHeaderHeight(int height) {
		options.setHeaderHeight(new Integer(height));
		return this;
	}

	public DynamicReportBuilder addFooterHeight(Integer height) {
		options.setFooterHeight(height);
		return this;
	}
	public DynamicReportBuilder addFooterHeight(int height) {
		options.setFooterHeight(new Integer(height));
		return this;
	}

	public DynamicReportBuilder addDetailHeight(Integer height) {
		options.setDetailHeight(height);
		return this;
	}
	public DynamicReportBuilder addDetailHeight(int height) {
		options.setDetailHeight(new Integer(height));
		return this;
	}

	public DynamicReportBuilder addLeftMargin(Integer margin) {
		options.setLeftMargin(margin);
		return this;
	}
	public DynamicReportBuilder addLeftMargin(int margin) {
		options.setLeftMargin(new Integer(margin));
		return this;
	}

	public DynamicReportBuilder addRightMargin(Integer margin) {
		options.setRightMargin(margin);
		return this;
	}
	public DynamicReportBuilder addRightMargin(int margin) {
		options.setRightMargin(new Integer(margin));
		return this;
	}

	public DynamicReportBuilder addTopMargin(Integer margin) {
		options.setTopMargin(margin);
		return this;
	}
	public DynamicReportBuilder addTopMargin(int margin) {
		options.setTopMargin(new Integer(margin));
		return this;
	}

	public DynamicReportBuilder addBottomMargin(Integer margin) {
		options.setBottomMargin(margin);
		return this;
	}
	public DynamicReportBuilder addBottomMargin(int margin) {
		options.setBottomMargin(new Integer(margin));
		return this;
	}

	public DynamicReportBuilder addColumnsPerPage(Integer numColumns) {
		options.setColumnsPerPage(numColumns);
		return this;
	}
	public DynamicReportBuilder addColumnsPerPage(int numColumns) {
		options.setColumnsPerPage(new Integer(numColumns));
		return this;
	}

	public DynamicReportBuilder addColumnSpace(Integer columSpace) {
		options.setColumnSpace(columSpace);
		return this;
	}
	public DynamicReportBuilder addColumnSpace(int columSpace) {
		options.setColumnSpace(new Integer(columSpace));
		return this;
	}


	public DynamicReportBuilder addUseFullPageWidth(boolean useFullwidth) {
		options.setUseFullPageWidth(useFullwidth);
		return this;
	}
	public DynamicReportBuilder addUseFullPageWidth(Boolean useFullwidth) {
		options.setUseFullPageWidth(useFullwidth.booleanValue());
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
	public DynamicReportBuilder addPrintBackgroundOnOddRows(Boolean printBackgroundOnOddRows) {
		this.options.setPrintBackgroundOnOddRows(printBackgroundOnOddRows.booleanValue());
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

	/**
	 * Defines the page size and orientation.<br/>
	 * Common pages size and orientation are constants of ar.com.fdvs.dj.domain.constants.Page
	 *  
	 * @param page
	 * @return
	 */
	public DynamicReportBuilder addPageSizeAndOrientation(Page page) {
		options.setPage(page);
		return this;
	}

	public DynamicReportBuilder addImageBanner(String path, Integer width, Integer height, byte align) {
		ImageBanner banner = new ImageBanner(path,width.intValue(),height.intValue(),align);
		options.getImageBanners().put(new Byte(align), banner);
		return this;
	}

	public DynamicReportBuilder addFirstPageImageBanner(String path, Integer width, Integer height, byte align) {
		ImageBanner banner = new ImageBanner(path,width.intValue(),height.intValue(),align);
		options.getFirstPageImageBanners().put(new Byte(align), banner);
		return this;
	}

	/**
	 * Registers a field that is not necesary bound to a column, it can be used in a 
	 * custom expression
	 * @param name
	 * @param className
	 * @return
	 */
	public DynamicReportBuilder addField(String name, String className) {
		report.getFields().add(new ColumnProperty(name,className));
		return this;
	}

	/**
	 * Registers a field that is not necesary bound to a column, it can be used in a 
	 * custom expression
	 * @param name
	 * @param className
	 * @return
	 */
	public DynamicReportBuilder addChart(DJChart chart) {
		report.getCharts().add(chart);
		return this;
	}

	/**
	 * The full path of a jrxml file, or the path in the classpath of a jrxml resource.
	 * @param path
	 * @return
	 */
	public DynamicReportBuilder addTemplateFile(String path) {
		report.setTemplateFileName(path);
		return this;
	}
	
	
	public DynamicReportBuilder addMarginss(int top, int bottom, int left, int right) {
		
		options.setTopMargin(new Integer(top));
		options.setBottomMargin(new Integer(bottom));
		options.setLeftMargin(new Integer(left));
		options.setRightMargin(new Integer(right));
		
		return this;
	}
	
	
	public DynamicReportBuilder addDefaultStyles(Style title, Style subtitle, Style columnHeader, Style columDetail) {
		if (columDetail != null) 
			options.setDefaultDetailStyle(columDetail);
		
		if (columnHeader != null)
			options.setDefaultHeaderStyle(columnHeader);
		
		if (subtitle != null)
			report.setSubtitleStyle(subtitle);

		if (title != null)
			report.setTitleStyle(title);
		
		return this;
	}
	
	
	

}