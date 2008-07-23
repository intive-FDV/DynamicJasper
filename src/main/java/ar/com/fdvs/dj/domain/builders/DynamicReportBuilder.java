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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import net.sf.jasperreports.engine.JasperReport;
import ar.com.fdvs.dj.core.DJConstants;
import ar.com.fdvs.dj.core.layout.HorizontalBandAlignment;
import ar.com.fdvs.dj.core.layout.LayoutManager;
import ar.com.fdvs.dj.domain.AutoText;
import ar.com.fdvs.dj.domain.ColumnProperty;
import ar.com.fdvs.dj.domain.ColumnsGroupVariableOperation;
import ar.com.fdvs.dj.domain.DJChart;
import ar.com.fdvs.dj.domain.DJCrosstab;
import ar.com.fdvs.dj.domain.DJQuery;
import ar.com.fdvs.dj.domain.DynamicReport;
import ar.com.fdvs.dj.domain.DynamicReportOptions;
import ar.com.fdvs.dj.domain.ImageBanner;
import ar.com.fdvs.dj.domain.Style;
import ar.com.fdvs.dj.domain.constants.GroupLayout;
import ar.com.fdvs.dj.domain.constants.Page;
import ar.com.fdvs.dj.domain.entities.ColumnsGroup;
import ar.com.fdvs.dj.domain.entities.ColumnsGroupVariable;
import ar.com.fdvs.dj.domain.entities.Parameter;
import ar.com.fdvs.dj.domain.entities.Subreport;
import ar.com.fdvs.dj.domain.entities.SubreportParameter;
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
	protected String grandTotalLegend = "";
	protected ArrayList globalFooterVariables;
	protected ArrayList globalHeaderVariables;
	protected ArrayList globalFooterCrosstabs;
	protected ArrayList globalHeaderCrosstabs;
	protected ArrayList autoTexts;
	protected Map groupFooterSubreports = new HashMap();
	protected Map groupHeaderSubreports = new HashMap();


	protected ArrayList concatenatedReports = new ArrayList();
	private Style grandTotalStyle;

	public DynamicReportBuilder addAutoText(AutoText text) {
		if (this.autoTexts == null)
			this.autoTexts = new ArrayList();
		autoTexts.add(text);
		return this;
	}

	/**
	 * Adds an autotext to the Report, this are common texts such us "Page X/Y", "Created on 07/25/2007", etc.
	 * <br>
	 * The parameters are all constants from the <code>ar.com.fdvs.dj.domain.AutoText</code> class
	 *
	 * @param type  One of these constants:     <br>AUTOTEXT_PAGE_X_OF_Y <br> AUTOTEXT_PAGE_X_SLASH_Y <br> AUTOTEXT_PAGE_X, AUTOTEXT_CREATED_ON <br> AUTOTEXT_CUSTOM_MESSAGE
	 *
	 * @param position  POSITION_HEADER or POSITION_FOOTER
	 * @param alignment  <br>ALIGMENT_LEFT <br> ALIGMENT_CENTER <br> ALIGMENT_RIGHT
	 * @param pattern   only for dates:  <br>PATTERN_DATE_DATE_ONLY <br> PATTERN_DATE_TIME_ONLY <br> PATTERN_DATE_DATE_TIME
	 * @return
	 */
	public DynamicReportBuilder addAutoText(byte type, byte position, byte alignment,byte pattern) {
		HorizontalBandAlignment alignment_ = HorizontalBandAlignment.buildAligment(alignment);
		AutoText text = new AutoText(type,position,alignment_,pattern);
		addAutoText(text);

		return this;
	}
	
	/**
	 * Adds an autotext to the Report, this are common texts such us "Page X/Y", "Created on 07/25/2007", etc.
	 * <br>
	 * The parameters are all constants from the <code>ar.com.fdvs.dj.domain.AutoText</code> class
	 *
	 * @param type  One of these constants:     <br>AUTOTEXT_PAGE_X_OF_Y <br> AUTOTEXT_PAGE_X_SLASH_Y <br> AUTOTEXT_PAGE_X, AUTOTEXT_CREATED_ON <br> AUTOTEXT_CUSTOM_MESSAGE
	 *
	 * @param position  POSITION_HEADER or POSITION_FOOTER
	 * @param alignment  <br>ALIGMENT_LEFT <br> ALIGMENT_CENTER <br> ALIGMENT_RIGHT
	 * @param pattern   only for dates:  <br>PATTERN_DATE_DATE_ONLY <br> PATTERN_DATE_TIME_ONLY <br> PATTERN_DATE_DATE_TIME
	 * @param width with of the autotext. If autotext is of the type AUTOTEXT_PAGE_X_OF_Y or AUTOTEXT_PAGE_X_SLASH_Y then is the width of the first part
	 * @param width2  If autotext is of the type AUTOTEXT_PAGE_X_OF_Y or AUTOTEXT_PAGE_X_SLASH_Y, this defines the width given to the "total page" variable
	 * @return
	 */
	public DynamicReportBuilder addAutoText(byte type, byte position, byte alignment,byte pattern, int width, int width2) {
		HorizontalBandAlignment alignment_ = HorizontalBandAlignment.buildAligment(alignment);
		AutoText text = new AutoText(type,position,alignment_,pattern,width,width2);
		addAutoText(text);
		
		return this;
	}

	/**
	 * Adds a custom fixed message (literal) in header or footer. The message width will be the page witdth<br>
	 * The parameters are all constants from the <code>ar.com.fdvs.dj.domain.AutoText</code> class
	 * <br>
	 * <br>
	 * @param message   The text to show
	 * @param position  POSITION_HEADER or POSITION_FOOTER
	 * @param alignment <br>ALIGMENT_LEFT <br> ALIGMENT_CENTER <br> ALIGMENT_RIGHT
	 * @return
	 */
	public DynamicReportBuilder addAutoText(String message, byte position, byte alignment) {
		HorizontalBandAlignment alignment_ = HorizontalBandAlignment.buildAligment(alignment);
		AutoText text = new AutoText(message,position,alignment_);
		text.setWidth(AutoText.WIDTH_NOT_SET);
		addAutoText(text);
		return this;
	}
	
	/**
	 * Adds a custom fixed message (literal) in header or footer. The message width will be the page width<br>
	 * The parameters are all constants from the <code>ar.com.fdvs.dj.domain.AutoText</code> class
	 * <br>
	 * <br>
	 * @param message   The text to show
	 * @param position  POSITION_HEADER or POSITION_FOOTER
	 * @param alignment <br>ALIGMENT_LEFT <br> ALIGMENT_CENTER <br> ALIGMENT_RIGHT
	 * @param width the width of the message
	 * @return
	 */	
	public DynamicReportBuilder addAutoText(String message, byte position, byte alignment, int width) {
		HorizontalBandAlignment alignment_ = HorizontalBandAlignment.buildAligment(alignment);
		AutoText text = new AutoText(message,position,alignment_,new Integer(width));
		addAutoText(text);
		return this;
	}

	/**
 	 * Adds an autotext to the Report, this are common texts such us "Page X/Y", "Created on 07/25/2007", etc.
	 * <br>
	 * The parameters are all constants from the <code>ar.com.fdvs.dj.domain.AutoText</code> class
	 *
	 * @param type       One of these constants:     <br>AUTOTEXT_PAGE_X_OF_Y <br> AUTOTEXT_PAGE_X_SLASH_Y
	 * <br> AUTOTEXT_PAGE_X, AUTOTEXT_CREATED_ON <br> AUTOTEXT_CUSTOM_MESSAGE
	 * @param position   POSITION_HEADER or POSITION_FOOTER
	 * @param alignment  <br>ALIGMENT_LEFT <br> ALIGMENT_CENTER <br> ALIGMENT_RIGHT
	 * @return
	 */
	public DynamicReportBuilder addAutoText(byte type, byte position, byte alignment, int width, int width2) {
		HorizontalBandAlignment alignment_ = HorizontalBandAlignment.buildAligment(alignment);
		AutoText text = new AutoText(type,position,alignment_);
		text.setWidth(new Integer(width));
		text.setWidth2(new Integer(width2));
		addAutoText(text);
		return this;
	}
	/**
	 * Adds an autotext to the Report, this are common texts such us "Page X/Y", "Created on 07/25/2007", etc.
	 * <br>
	 * The parameters are all constants from the <code>ar.com.fdvs.dj.domain.AutoText</code> class
	 *
	 * @param type       One of these constants:     <br>AUTOTEXT_PAGE_X_OF_Y <br> AUTOTEXT_PAGE_X_SLASH_Y
	 * <br> AUTOTEXT_PAGE_X, AUTOTEXT_CREATED_ON <br> AUTOTEXT_CUSTOM_MESSAGE
	 * @param position   POSITION_HEADER or POSITION_FOOTER
	 * @param alignment  <br>ALIGMENT_LEFT <br> ALIGMENT_CENTER <br> ALIGMENT_RIGHT
	 * @return
	 */
	public DynamicReportBuilder addAutoText(byte type, byte position, byte alignment) {
		HorizontalBandAlignment alignment_ = HorizontalBandAlignment.buildAligment(alignment);
		AutoText text = new AutoText(type,position,alignment_);
		text.setWidth(AutoText.WIDTH_NOT_SET);
		text.setWidth2(AutoText.WIDTH_NOT_SET);
		addAutoText(text);
		return this;
	}

	public DynamicReport build(){
		report.setOptions(options);
		if (globalFooterVariables != null || globalHeaderVariables != null) {
			ColumnsGroup globalGroup = createDummyGroup();
			report.getColumnsGroups().add(0,globalGroup);
		}

				
		addGlobalCrosstabs();

		addSubreportsToGroups();

		concatenateReports();

		report.setAutoTexts(autoTexts);
		return report;
	}


	private void addGlobalCrosstabs() {
		//For header
		if (globalHeaderCrosstabs != null) {
			for (Iterator iterator = globalHeaderCrosstabs.iterator(); iterator.hasNext();) {
				DJCrosstab djcross = (DJCrosstab) iterator.next();
				ColumnsGroup globalGroup = createDummyGroupForCrosstabs("crosstabHeaderGroup-" + globalHeaderCrosstabs.indexOf(djcross));
				globalGroup.getHeaderCrosstabs().add(djcross);
				report.getColumnsGroups().add(0,globalGroup);
			}
		}

		//For footer
		if (globalFooterCrosstabs != null) {
			for (Iterator iterator = globalFooterCrosstabs.iterator(); iterator.hasNext();) {
				DJCrosstab djcross = (DJCrosstab) iterator.next();
				ColumnsGroup globalGroup = createDummyGroupForCrosstabs("crosstabFooterGroup-" + globalFooterCrosstabs.indexOf(djcross));
				globalGroup.getFooterCrosstabs().add(djcross);
				report.getColumnsGroups().add(0,globalGroup);
			}
		}

	}

	/**
	 * Because the groups are not created until we call the "build()" method, all the subreports that must go
	 * inside a group are handled here.
	 */
	protected void addSubreportsToGroups() {
		for (Iterator iterator = groupFooterSubreports.keySet().iterator(); iterator.hasNext();) {
			Integer groupNum = (Integer) iterator.next();
			List list = (List) groupFooterSubreports.get(groupNum);

			ColumnsGroup group = (ColumnsGroup) report.getColumnsGroups().get(groupNum.intValue() - 1);
			group.getFooterSubreports().addAll(list);
		}

		for (Iterator iterator = groupHeaderSubreports.keySet().iterator(); iterator.hasNext();) {
			Integer groupNum = (Integer) iterator.next();
			List list = (List) groupHeaderSubreports.get(groupNum);

			ColumnsGroup group = (ColumnsGroup) report.getColumnsGroups().get(groupNum.intValue() - 1);
			group.getHeaderSubreports().addAll(list);
		}

	}

	/**
	 * Create dummy groups for each concatenated report, and in the footer of each group
	 * adds the subreport.
	 */
	protected void concatenateReports() {

		for (Iterator iterator = concatenatedReports.iterator(); iterator.hasNext();) {
			Subreport subreport = (Subreport) iterator.next();
			ColumnsGroup globalGroup = createDummyGroup();
			globalGroup.getFooterSubreports().add(subreport);
			report.getColumnsGroups().add(0,globalGroup);
		}
	}

	/**
	 * @return
	 */
	private ColumnsGroup createDummyGroup() {
		ColumnsGroup globalGroup = new ColumnsGroup();
		globalGroup.setLayout(GroupLayout.EMPTY);
		GlobalGroupColumn globalCol = new GlobalGroupColumn("global");
		globalCol.setTitle(grandTotalLegend);
		globalCol.setHeaderStyle(grandTotalStyle);
		globalCol.setStyle(grandTotalStyle);

		globalGroup.setColumnToGroupBy(globalCol);
		globalGroup.setHeaderVariables(globalHeaderVariables);
		globalGroup.setFooterVariables(globalFooterVariables);
		return globalGroup;
	}

	private ColumnsGroup createDummyGroupForCrosstabs(String name) {
		ColumnsGroup globalGroup = new ColumnsGroup();
		globalGroup.setLayout(GroupLayout.EMPTY);
		GlobalGroupColumn globalCol = new GlobalGroupColumn(name );

		globalCol.setTitle(grandTotalLegend);
		globalCol.setHeaderStyle(grandTotalStyle);
		globalCol.setStyle(grandTotalStyle);

		globalGroup.setColumnToGroupBy(globalCol);
		return globalGroup;
	}

	/**
	 * @deprecated
	 * @param title
	 * @return
	 */
	public DynamicReportBuilder addTitle(String title) {
		return setTitle(title);
	}

	public DynamicReportBuilder setTitle(String title) {
		report.setTitle(title);
		return this;
	}

	/**
	 * @deprecated
	 * @param subtitle
	 * @return
	 */
	public DynamicReportBuilder addSubtitle(String subtitle) {
		return setSubtitle(subtitle);
	}

	public DynamicReportBuilder setSubtitle(String subtitle) {
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

	/**
	 * @deprecated
	 * @param height
	 * @return
	 */
	public DynamicReportBuilder addHeaderHeight(Integer height) {
		return setHeaderHeight(height);
	}

	public DynamicReportBuilder setHeaderHeight(Integer height) {
		options.setHeaderHeight(height);
		return this;
	}

	/**
	 * @deprecated
	 * @param height
	 * @return
	 */
	public DynamicReportBuilder addHeaderHeight(int height) {
		return setHeaderHeight(height);
	}

	public DynamicReportBuilder setHeaderHeight(int height) {
		options.setHeaderHeight(new Integer(height));
		return this;
	}

	/**
	 * @deprecated
	 * @param height
	 * @return
	 */
	public DynamicReportBuilder addFooterHeight(Integer height) {
		options.setFooterHeight(height);
		return this;
	}

	public DynamicReportBuilder setFooterHeight(Integer height) {
		options.setFooterHeight(height);
		return this;
	}

	/**
	 * @deprecated
	 * @param height
	 * @return
	 */
	public DynamicReportBuilder addFooterHeight(int height) {
		options.setFooterHeight(new Integer(height));
		return this;
	}

	public DynamicReportBuilder setFooterHeight(int height) {
		options.setFooterHeight(new Integer(height));
		return this;
	}

	/**
	 * @deprecated
	 * @param height
	 * @return
	 */
	public DynamicReportBuilder addDetailHeight(Integer height) {
		options.setDetailHeight(height);
		return this;
	}

	public DynamicReportBuilder setDetailHeight(Integer height) {
		options.setDetailHeight(height);
		return this;
	}

	/**
	 * @deprecated
	 * @param height
	 * @return
	 */
	public DynamicReportBuilder addDetailHeight(int height) {
		options.setDetailHeight(new Integer(height));
		return this;
	}

	public DynamicReportBuilder setDetailHeight(int height) {
		options.setDetailHeight(new Integer(height));
		return this;
	}

	/**
	 * @deprecated
	 * @param margin
	 * @return
	 */
	public DynamicReportBuilder addLeftMargin(Integer margin) {
		options.setLeftMargin(margin);
		return this;
	}

	public DynamicReportBuilder setLeftMargin(Integer margin) {
		options.setLeftMargin(margin);
		return this;
	}

	/**
	 * @deprecated
	 * @param margin
	 * @return
	 */
	public DynamicReportBuilder addLeftMargin(int margin) {
		options.setLeftMargin(new Integer(margin));
		return this;
	}

	public DynamicReportBuilder setLeftMargin(int margin) {
		options.setLeftMargin(new Integer(margin));
		return this;
	}

	/**
	 * @deprecated
	 * @param margin
	 * @return
	 */
	public DynamicReportBuilder addRightMargin(Integer margin) {
		options.setRightMargin(margin);
		return this;
	}

	public DynamicReportBuilder setRightMargin(Integer margin) {
		options.setRightMargin(margin);
		return this;
	}

	/**
	 * @deprecated
	 * @param margin
	 * @return
	 */
	public DynamicReportBuilder addRightMargin(int margin) {
		options.setRightMargin(new Integer(margin));
		return this;
	}

	public DynamicReportBuilder setRightMargin(int margin) {
		options.setRightMargin(new Integer(margin));
		return this;
	}

	/**
	 * @deprecated
	 * @param margin
	 * @return
	 */
	public DynamicReportBuilder addTopMargin(Integer margin) {
		options.setTopMargin(margin);
		return this;
	}

	public DynamicReportBuilder setTopMargin(Integer margin) {
		options.setTopMargin(margin);
		return this;
	}

	/**
	 * @deprecated
	 * @param margin
	 * @return
	 */
	public DynamicReportBuilder addTopMargin(int margin) {
		options.setTopMargin(new Integer(margin));
		return this;
	}
	public DynamicReportBuilder setTopMargin(int margin) {
		options.setTopMargin(new Integer(margin));
		return this;
	}

	/**
	 * @deprecated
	 * @param margin
	 * @return
	 */
	public DynamicReportBuilder addBottomMargin(Integer margin) {
		options.setBottomMargin(margin);
		return this;
	}
	public DynamicReportBuilder setBottomMargin(Integer margin) {
		options.setBottomMargin(margin);
		return this;
	}

	/**
	 * @deprecated
	 * @param margin
	 * @return
	 */
	public DynamicReportBuilder addBottomMargin(int margin) {
		options.setBottomMargin(new Integer(margin));
		return this;
	}
	public DynamicReportBuilder setBottomMargin(int margin) {
		options.setBottomMargin(new Integer(margin));
		return this;
	}

	/**
	 * @deprecated
	 * @param numColumns
	 * @return
	 */
	public DynamicReportBuilder addColumnsPerPage(Integer numColumns) {
		options.setColumnsPerPage(numColumns);
		return this;
	}
	public DynamicReportBuilder setColumnsPerPage(Integer numColumns) {
		options.setColumnsPerPage(numColumns);
		return this;
	}

	/**
	 * @deprecated
	 * @param numColumns
	 * @return
	 */
	public DynamicReportBuilder addColumnsPerPage(int numColumns) {
		options.setColumnsPerPage(new Integer(numColumns));
		return this;
	}
	public DynamicReportBuilder setColumnsPerPage(int numColumns) {
		options.setColumnsPerPage(new Integer(numColumns));
		return this;
	}

	public DynamicReportBuilder setColumnsPerPage(int numColumns, int columnSpace) {
		options.setColumnsPerPage(new Integer(numColumns));
		options.setColumnSpace(new Integer(columnSpace));
		return this;
	}
	public DynamicReportBuilder setColumnsPerPage(Integer numColumns, Integer columnSpace) {
		options.setColumnsPerPage(numColumns);
		options.setColumnSpace(columnSpace);
		return this;
	}

	/**
	 * @deprecated
	 * @param columSpace
	 * @return
	 */
	public DynamicReportBuilder addColumnSpace(Integer columSpace) {
		options.setColumnSpace(columSpace);
		return this;
	}
	public DynamicReportBuilder setColumnSpace(Integer columSpace) {
		options.setColumnSpace(columSpace);
		return this;
	}
	/**
	 * @deprecated
	 * @param columSpace
	 * @return
	 */
	public DynamicReportBuilder addColumnSpace(int columSpace) {
		options.setColumnSpace(new Integer(columSpace));
		return this;
	}
	public DynamicReportBuilder setColumnSpace(int columSpace) {
		options.setColumnSpace(new Integer(columSpace));
		return this;
	}

	/**
	 * When FALSE, no column names are printed (in the header band)
	 * @param bool
	 * @return
	 */
	public DynamicReportBuilder setPrintColumnNames(boolean bool) {
		options.setPrintColumnNames(bool);
		return this;
	}

	/**
	 * When TRUE, no page break at all (useful for Excell)
	 * Default is FALSE
	 * @param bool
	 * @return
	 */
	public DynamicReportBuilder setIgnorePagination(boolean bool) {
		options.setIgnorePagination(bool);
		return this;
	}

	/**
	 * @deprecated
	 * @param useFullwidth
	 * @return
	 */
	public DynamicReportBuilder addUseFullPageWidth(boolean useFullwidth) {
		options.setUseFullPageWidth(useFullwidth);
		return this;
	}
	public DynamicReportBuilder setUseFullPageWidth(boolean useFullwidth) {
		options.setUseFullPageWidth(useFullwidth);
		return this;
	}

	/**
	 * @deprecated
	 * @param useFullwidth
	 * @return
	 */
	public DynamicReportBuilder addUseFullPageWidth(Boolean useFullwidth) {
		options.setUseFullPageWidth(useFullwidth.booleanValue());
		return this;
	}
	public DynamicReportBuilder setUseFullPageWidth(Boolean useFullwidth) {
		options.setUseFullPageWidth(useFullwidth.booleanValue());
		return this;
	}

	/**
	 * @deprecated
	 * @param titleStyle
	 * @return
	 */
	public DynamicReportBuilder addTitleStyle(Style titleStyle) {
		this.report.setTitleStyle(titleStyle);
		return this;
	}
	public DynamicReportBuilder setTitleStyle(Style titleStyle) {
		this.report.setTitleStyle(titleStyle);
		return this;
	}

	/**
	 * @deprecated
	 * @param subtitleStyle
	 * @return
	 */
	public DynamicReportBuilder addSubtitleStyle(Style subtitleStyle) {
		this.report.setSubtitleStyle(subtitleStyle);
		return this;
	}
	public DynamicReportBuilder setSubtitleStyle(Style subtitleStyle) {
		this.report.setSubtitleStyle(subtitleStyle);
		return this;
	}

	/**
	 * @deprecated
	 * @param printBackgroundOnOddRows
	 * @return
	 */
	public DynamicReportBuilder addPrintBackgroundOnOddRows(boolean printBackgroundOnOddRows) {
		this.options.setPrintBackgroundOnOddRows(printBackgroundOnOddRows);
		return this;
	}
	public DynamicReportBuilder setPrintBackgroundOnOddRows(boolean printBackgroundOnOddRows) {
		this.options.setPrintBackgroundOnOddRows(printBackgroundOnOddRows);
		return this;
	}

	/**
	 * @deprecated
	 * @param printBackgroundOnOddRows
	 * @return
	 */
	public DynamicReportBuilder addPrintBackgroundOnOddRows(Boolean printBackgroundOnOddRows) {
		this.options.setPrintBackgroundOnOddRows(printBackgroundOnOddRows.booleanValue());
		return this;
	}
	public DynamicReportBuilder setPrintBackgroundOnOddRows(Boolean printBackgroundOnOddRows) {
		this.options.setPrintBackgroundOnOddRows(printBackgroundOnOddRows.booleanValue());
		return this;
	}

	/**
	 * @deprecated
	 * @param oddRowBackgroundStyle
	 * @return
	 */
	public DynamicReportBuilder addOddRowBackgroundStyle(Style oddRowBackgroundStyle) {
		this.options.setOddRowBackgroundStyle(oddRowBackgroundStyle);
		return this;
	}
	public DynamicReportBuilder setOddRowBackgroundStyle(Style oddRowBackgroundStyle) {
		this.options.setOddRowBackgroundStyle(oddRowBackgroundStyle);
		return this;
	}

	/**
	 * @deprecated
	 * @param title
	 * @return
	 */
	public DynamicReportBuilder addGlobalTitle(String title) {
		this.grandTotalLegend = title;
		return this;
	}
	public DynamicReportBuilder setGrandTotalLegend(String title) {
		this.grandTotalLegend = title;
		return this;
	}

	/**
	 * @param col
	 * @param op
	 * @return
	 */
	public DynamicReportBuilder addGlobalHeaderVariable(AbstractColumn col, ColumnsGroupVariableOperation op) {
		if (this.globalHeaderVariables == null)
			this.globalHeaderVariables = new ArrayList();
		this.globalHeaderVariables.add(new ColumnsGroupVariable(col, op));
		return this;
	}

	public DynamicReportBuilder addGlobalHeaderVariable(AbstractColumn col, ColumnsGroupVariableOperation op, Style style) {
		if (this.globalHeaderVariables == null)
			this.globalHeaderVariables = new ArrayList();
		this.globalHeaderVariables.add(new ColumnsGroupVariable(col, op, style));
		return this;
	}


	/**
	 * @param col
	 * @param op
	 * @return
	 */
	public DynamicReportBuilder addGlobalFooterVariable(AbstractColumn col, ColumnsGroupVariableOperation op) {
		if (this.globalFooterVariables == null)
			this.globalFooterVariables = new ArrayList();
		this.globalFooterVariables.add(new ColumnsGroupVariable(col, op));
		return this;
	}

	public DynamicReportBuilder addGlobalFooterVariable(AbstractColumn col, ColumnsGroupVariableOperation op, Style style) {
		if (this.globalFooterVariables == null)
			this.globalFooterVariables = new ArrayList();
		this.globalFooterVariables.add(new ColumnsGroupVariable(col, op, style));
		return this;
	}

	/**
	 * @deprecated
	 * @param height
	 * @return
	 */
	public DynamicReportBuilder addTitleHeight(Integer height) {
		options.setTitleHeight(height);
		return this;
	}
	public DynamicReportBuilder setTitleHeight(Integer height) {
		options.setTitleHeight(height);
		return this;
	}

	/**
	 * @deprecated
	 * @param height
	 * @return
	 */
	public DynamicReportBuilder addSubtitleHeight(Integer height) {
		options.setSubtitleHeight(height);
		return this;
	}
	public DynamicReportBuilder setSubtitleHeight(Integer height) {
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
	public DynamicReportBuilder setPageSizeAndOrientation(Page page) {
		options.setPage(page);
		return this;
	}

	/**
	 * @deprecated
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
	public DynamicReportBuilder setTemplateFile(String path) {
		report.setTemplateFileName(path);
		return this;
	}

	/**
	 * @deprecated
	 * @param path
	 * @return
	 */
	public DynamicReportBuilder addTemplateFile(String path) {
		report.setTemplateFileName(path);
		return this;
	}

	/**
	 * @deprecated
	 * @param top
	 * @param bottom
	 * @param left
	 * @param right
	 * @return
	 */
	public DynamicReportBuilder addMargins(int top, int bottom, int left, int right) {

		options.setTopMargin(new Integer(top));
		options.setBottomMargin(new Integer(bottom));
		options.setLeftMargin(new Integer(left));
		options.setRightMargin(new Integer(right));

		return this;
	}

	public DynamicReportBuilder setMargins(int top, int bottom, int left, int right) {

		options.setTopMargin(new Integer(top));
		options.setBottomMargin(new Integer(bottom));
		options.setLeftMargin(new Integer(left));
		options.setRightMargin(new Integer(right));

		return this;
	}


	public DynamicReportBuilder setDefaultStyles(Style title, Style subtitle, Style columnHeader, Style columDetail) {
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
	/**
	 * @deprecated
	 * @param title
	 * @param subtitle
	 * @param columnHeader
	 * @param columDetail
	 * @return
	 */
	public DynamicReportBuilder addDefaultStyles(Style title, Style subtitle, Style columnHeader, Style columDetail) {
		return setDefaultStyles(title,subtitle,columnHeader,columDetail);
	}

	/**
	 * Adds the locale to use when filling the report.
	 * @param locale
	 * @return
	 */
	public DynamicReportBuilder setReportLocale(Locale locale) {
		report.setReportLocale(locale);
		return this;
	}

	/**
	 * @deprecated
	 * @param locale
	 * @return
	 */
	public DynamicReportBuilder addReportLocale(Locale locale) {
		report.setReportLocale(locale);
		return this;
	}

	/**
	 * All concatenated reports are shown in the same order they are inserted
	 * @param subreport
	 * @return
	 */
	public DynamicReportBuilder addConcatenatedReport(Subreport subreport) {
		concatenatedReports.add(subreport);
		return this;
	}

	public DynamicReportBuilder addConcatenatedReport(DynamicReport dynamicReport, LayoutManager layoutManager, String dataSourcePath, int dataSourceOrigin, int dataSourceType) throws DJBuilderException {
		Subreport subreport = new SubReportBuilder()
		.setDataSource(dataSourceOrigin, dataSourceType, dataSourcePath)
		.setDynamicReport(dynamicReport,layoutManager)
		.build();

		concatenatedReports.add(subreport);
		return this;
	}

	public DynamicReportBuilder addConcatenatedReport(JasperReport jasperReport, String dataSourcePath, int dataSourceOrigin, int dataSourceType) throws DJBuilderException {
		Subreport subreport = new SubReportBuilder()
		.setDataSource(dataSourceOrigin, dataSourceType, dataSourcePath)
		.setReport(jasperReport)
		.build();

		concatenatedReports.add(subreport);
		return this;
	}


	/**
	 * Adds in the group (starts with 1) "groupNumber" a subreport in the footer band
	 * @param groupNumber
	 * @param subreport
	 * @return
	 */

	public DynamicReportBuilder addSubreportInGroupFooter(int groupNumber, Subreport subreport) {
		Integer groupNum = new Integer(groupNumber);
		List list = (List) groupFooterSubreports.get(groupNum);
		if (list == null) {
			list = new ArrayList();
			groupFooterSubreports.put(groupNum, list);
		}
		list.add(subreport);
		return this;
	}

	public DynamicReportBuilder addSubreportInGroupFooter(int groupNumber, DynamicReport dynamicReport, LayoutManager layoutManager, String dataSourcePath, int dataSourceOrigin, int dataSourceType) throws DJBuilderException {
		Subreport subreport = new SubReportBuilder()
			.setDataSource(dataSourceOrigin, dataSourceType, dataSourcePath)
			.setDynamicReport(dynamicReport,layoutManager)
			.build();

		return addSubreportInGroupFooter(groupNumber, subreport);
	}
	public DynamicReportBuilder addSubreportInGroupFooter(int groupNumber, DynamicReport dynamicReport, LayoutManager layoutManager, String dataSourcePath, int dataSourceOrigin, int dataSourceType, SubreportParameter[] params) throws DJBuilderException {
		SubReportBuilder srb = new SubReportBuilder();
		
		srb.setDataSource(dataSourceOrigin, dataSourceType, dataSourcePath)
		.setDynamicReport(dynamicReport,layoutManager);
		
		if (params != null){
			for (int i = 0; i < params.length; i++) {
				srb.addParameter(params[i]);
			}
		}
		
		Subreport subreport = srb.build();
		
		return addSubreportInGroupFooter(groupNumber, subreport);
	}
	public DynamicReportBuilder addSubreportInGroupHeader(int groupNumber, DynamicReport dynamicReport, LayoutManager layoutManager, String dataSourcePath, int dataSourceOrigin, int dataSourceType, SubreportParameter[] params) throws DJBuilderException {
		SubReportBuilder srb = new SubReportBuilder();
		
		srb.setDataSource(dataSourceOrigin, dataSourceType, dataSourcePath)
		.setDynamicReport(dynamicReport,layoutManager);
		
		if (params != null){
			for (int i = 0; i < params.length; i++) {
				srb.addParameter(params[i]);
			}
		}
		
		Subreport subreport = srb.build();
		
		return addSubreportInGroupHeader(groupNumber, subreport);
	}
	
	public DynamicReportBuilder addSubreportInGroupFooter(int groupNumber,
			DynamicReport dynamicReport, LayoutManager layoutManager,
			String dataSourcePath, int dataSourceOrigin, int dataSourceType,
			SubreportParameter[] params, boolean startInNewPage)
			throws DJBuilderException {
		SubReportBuilder srb = new SubReportBuilder();

		srb.setDataSource(dataSourceOrigin, dataSourceType, dataSourcePath)
				.setStartInNewPage(startInNewPage).setDynamicReport(
						dynamicReport, layoutManager);

		if (params != null) {
			for (int i = 0; i < params.length; i++) {
				srb.addParameter(params[i]);
			}
		}

		Subreport subreport = srb.build();

		return addSubreportInGroupFooter(groupNumber, subreport);
	}

	public DynamicReportBuilder addSubreportInGroupHeader(int groupNumber,
			DynamicReport dynamicReport, LayoutManager layoutManager,
			String dataSourcePath, int dataSourceOrigin, int dataSourceType,
			SubreportParameter[] params, boolean startInNewPage)
			throws DJBuilderException {
		SubReportBuilder srb = new SubReportBuilder();

		srb.setDataSource(dataSourceOrigin, dataSourceType, dataSourcePath)
				.setStartInNewPage(startInNewPage).setDynamicReport(
						dynamicReport, layoutManager);

		if (params != null) {
			for (int i = 0; i < params.length; i++) {
				srb.addParameter(params[i]);
			}
		}

		Subreport subreport = srb.build();

		return addSubreportInGroupHeader(groupNumber, subreport);
	}
	
	public DynamicReportBuilder addSubreportInGroupFooter(int groupNumber,
			DynamicReport dynamicReport, LayoutManager layoutManager,
			String dataSourcePath, int dataSourceOrigin, int dataSourceType,
			SubreportParameter[] params, boolean startInNewPage, boolean fitParent)
	throws DJBuilderException {
		SubReportBuilder srb = new SubReportBuilder();
		
		srb.setDataSource(dataSourceOrigin, dataSourceType, dataSourcePath)
		.setStartInNewPage(startInNewPage).setDynamicReport(
				dynamicReport, layoutManager).setFitToParentPrintableArea(fitParent);
		
		if (params != null) {
			for (int i = 0; i < params.length; i++) {
				srb.addParameter(params[i]);
			}
		}
		
		Subreport subreport = srb.build();
		
		return addSubreportInGroupFooter(groupNumber, subreport);
	}
	
	public DynamicReportBuilder addSubreportInGroupHeader(int groupNumber,
			DynamicReport dynamicReport, LayoutManager layoutManager,
			String dataSourcePath, int dataSourceOrigin, int dataSourceType,
			SubreportParameter[] params, boolean startInNewPage, boolean fitParent)
	throws DJBuilderException {
		SubReportBuilder srb = new SubReportBuilder();
		
		srb.setDataSource(dataSourceOrigin, dataSourceType, dataSourcePath)
		.setStartInNewPage(startInNewPage).setDynamicReport(
				dynamicReport, layoutManager).setFitToParentPrintableArea(fitParent);
		
		if (params != null) {
			for (int i = 0; i < params.length; i++) {
				srb.addParameter(params[i]);
			}
		}
		
		Subreport subreport = srb.build();
		
		return addSubreportInGroupHeader(groupNumber, subreport);
	}

	public DynamicReportBuilder addSubreportInGroupFooter(int groupNumber,
			String pathToSubreport, String dataSourcePath,
			int dataSourceOrigin, int dataSourceType) throws DJBuilderException {

		Subreport subreport = new SubReportBuilder().setDataSource(
				dataSourceOrigin, dataSourceType, dataSourcePath)
				.setPathToReport(pathToSubreport).build();

		return addSubreportInGroupFooter(groupNumber, subreport);
	}

	public DynamicReportBuilder addSubreportInGroupFooter(int groupNumber,
			String pathToSubreport, String dataSourcePath,
			int dataSourceOrigin, int dataSourceType, boolean startInNewPage)
			throws DJBuilderException {

		Subreport subreport = new SubReportBuilder().setDataSource(
				dataSourceOrigin, dataSourceType, dataSourcePath)
				.setPathToReport(pathToSubreport).setStartInNewPage(
						startInNewPage).build();

		return addSubreportInGroupFooter(groupNumber, subreport);
	}
	
	public DynamicReportBuilder addSubreportInGroupHeader(int groupNumber, Subreport subreport) {
		Integer groupNum = new Integer(groupNumber);
		List list = (List) groupHeaderSubreports.get(groupNum);
		if (list == null) {
			list = new ArrayList();
			groupHeaderSubreports.put(groupNum, list);
		}
		list.add(subreport);
		return this;
	}

	public DynamicReportBuilder addSubreportInGroupHeader(int groupNumber, DynamicReport dynamicReport, LayoutManager layoutManager, String dataSourcePath, int dataSourceOrigin, int dataSourceType) throws DJBuilderException {
		Subreport subreport = new SubReportBuilder()
		.setDataSource(dataSourceOrigin, dataSourceType, dataSourcePath)
		.setDynamicReport(dynamicReport,layoutManager)
		.build();

		return addSubreportInGroupHeader(groupNumber, subreport);
	}

	public DynamicReportBuilder addSubreportInGroupHeader(int groupNumber, String pathToSubreport, String dataSourcePath, int dataSourceOrigin, int dataSourceType) throws DJBuilderException {

		Subreport subreport = new SubReportBuilder()
		.setDataSource(dataSourceOrigin, dataSourceType, dataSourcePath)
		.setPathToReport(pathToSubreport)
		.build();

		return addSubreportInGroupHeader(groupNumber, subreport);
	}

	/**
	 * You can register styles object for later reference them directly.
	 * Parent styles should be registered this way
	 * @param style
	 * @return
	 * @throws DJBuilderException
	 */
	public DynamicReportBuilder addStyle(Style style) throws DJBuilderException {
		if (style.getName() == null)
			throw new DJBuilderException("Invalid style. The style must have a name");

		report.addStyle(style);

		return this;
	}

	public DynamicReportBuilder addResourceBundle(String resourceBundle) {
		report.setResourceBundle(resourceBundle);
		return this;
	}

	public DynamicReportBuilder setGrandTotalLegendStyle(Style grandTotalStyle) {
		this.grandTotalStyle = grandTotalStyle;
		return this;
	}

	/**
	 * Adds a crosstab in the header, before the the data
	 * @param cross
	 * @return
	 */
	public DynamicReportBuilder addHeaderCrosstab(DJCrosstab cross) {
		if (this.globalHeaderCrosstabs == null)
			this.globalHeaderCrosstabs = new ArrayList();
		this.globalHeaderCrosstabs.add(cross);
		return this;
	}

	/**
	 * Adds a crosstab in the footer of the report (at the end of all data)
	 * @param cross
	 * @return
	 */
	public DynamicReportBuilder addFooterCrosstab(DJCrosstab cross) {
		if (this.globalFooterCrosstabs == null)
			this.globalFooterCrosstabs = new ArrayList();
		this.globalFooterCrosstabs.add(cross);
		return this;
	}

	/**
	 * Adds main report query.
	 * @param text
	 * @param language use constants from {@link DJConstants}
	 * @return
	 */
	public DynamicReportBuilder setQuery(String text, String language) {
		this.report.setQuery(new DJQuery(text,language));
		return this;
	}

	public DynamicReportBuilder addFont(String fontName, java.awt.Font font) {
		this.report.getFontsMap().put(fontName, font);
		return this;
	}

	
	/**
	 * Defines the behaviour when the datasource is empty.
	 * Defatul vaue is {@link DJConstants#WHEN_NO_DATA_TYPE_NO_PAGES}
	 * @param whenNoDataType
	 * @return
	 */
	public DynamicReportBuilder setWhenNoDataType(byte whenNoDataType) {
		this.report.setWhenNoDataType(whenNoDataType);
		return this;
	}
	
	/**
	 * @see setWhenNoDataType
	 * @return
	 */
	public DynamicReportBuilder setWhenNoDataNoPages() {
		this.report.setWhenNoDataType(DJConstants.WHEN_NO_DATA_TYPE_NO_PAGES);
		return this;
	}
	/**
	 * @see setWhenNoDataType
	 * @return
	 */
	public DynamicReportBuilder setWhenNoDataBlankPage() {
		this.report.setWhenNoDataType(DJConstants.WHEN_NO_DATA_TYPE_BLANK_PAGE);
		return this;
	}
	/**
	 * @see setWhenNoDataType
	 * @return
	 */
	public DynamicReportBuilder setWhenNoDataAllSectionNoDetail() {
		this.report.setWhenNoDataType(DJConstants.WHEN_NO_DATA_TYPE_ALL_SECTIONS_NO_DETAIL);
		return this;
	}
	/**
	 * @see setWhenNoDataType
	 * @return
	 */
	public DynamicReportBuilder setWhenNoDataShowNoDataSection() {
		this.report.setWhenNoDataType(DJConstants.WHEN_NO_DATA_TYPE_NO_DATA_SECTION);
		return this;
	}

	/**
	 * Defines what to show if a missing resource is referenced Possible values
	 * are:<br>
	 * DJConstants.WHEN_RESOURCE_MISSING_TYPE_EMPTY: Leaves and empty field.<br/>
	 * DJConstants.WHEN_RESOURCE_MISSING_TYPE_ERROR: Throwns and exception.<br/>
	 * DJConstants.WHEN_RESOURCE_MISSING_TYPE_KEY: Shows the key of the missing
	 * resource.<br/> DJConstants.WHEN_RESOURCE_MISSING_TYPE_NULL: returns NULL
	 * 
	 * @param whenResourceMissing
	 * @return
	 */	
	public DynamicReportBuilder setWhenResourceMissing(byte whenResourceMissing) {
		this.report.setWhenResourceMissing(whenResourceMissing);
		return this;
	}
	public DynamicReportBuilder setWhenResourceMissingLeaveEmptySpace() {
		this.report.setWhenResourceMissing(DJConstants.WHEN_RESOURCE_MISSING_TYPE_EMPTY);
		return this;
	}
	public DynamicReportBuilder setWhenResourceMissingThrowException() {
		this.report.setWhenResourceMissing(DJConstants.WHEN_RESOURCE_MISSING_TYPE_ERROR);
		return this;
	}
	public DynamicReportBuilder setWhenResourceMissingShowKey() {
		this.report.setWhenResourceMissing(DJConstants.WHEN_RESOURCE_MISSING_TYPE_KEY);
		return this;
	}
	public DynamicReportBuilder setWhenResourceMissingReturnNull() {
		this.report.setWhenResourceMissing(DJConstants.WHEN_RESOURCE_MISSING_TYPE_NULL);
		return this;
	}
	
	/**
	 * Defines the text to show when the data source is empty.<br> 
	 * By default the title and column headers are shown
	 * @param text
	 * @param style : the style of the text
	 * @return
	 */
	public DynamicReportBuilder setWhenNoData(String text, Style style) {
		this.report.setWhenNoDataStyle(style);
		this.report.setWhenNoDataText(text);
		this.report.setWhenNoDataType(DJConstants.WHEN_NO_DATA_TYPE_NO_DATA_SECTION);
		return this;
	}
	
/**
 * Defines the text to show when the data source is empty.<br> 
 * @param text
 * @param style : the style of the text
 * @param showTitle : if true, the title is shown
 * @param showColumnHeader : if true, the column headers are shown
 * @return
 */	
	public DynamicReportBuilder setWhenNoData(String text, Style style, boolean showTitle, boolean showColumnHeader) {
		this.report.setWhenNoDataStyle(style);
		this.report.setWhenNoDataText(text);
		this.report.setWhenNoDataType(DJConstants.WHEN_NO_DATA_TYPE_NO_DATA_SECTION);
		this.report.setWhenNoDataShowColumnHeader(showColumnHeader);
		this.report.setWhenNoDataShowTitle(showTitle);
		return this;
	}

	public DynamicReportBuilder addParameter(String name, String className){
		this.report.getParameters().add(new Parameter(name, className));
		return this;
	}

	/**
	 * If true and there is no room for a textfield at the end of a page in the detail band, it will be splitted and continued in
	 * next page.
	 * @param split
	 * @return
	 */
	public DynamicReportBuilder setAllowDetailSplit(boolean split){
		this.report.setAllowDetailSplit(split);
		return this;
	}
	
	
}