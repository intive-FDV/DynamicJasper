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

import ar.com.fdvs.dj.core.DJConstants;
import ar.com.fdvs.dj.core.DJException;
import ar.com.fdvs.dj.core.JasperDesignDecorator;
import ar.com.fdvs.dj.core.layout.HorizontalBandAlignment;
import ar.com.fdvs.dj.core.layout.LayoutManager;
import ar.com.fdvs.dj.domain.AutoText;
import ar.com.fdvs.dj.domain.ColumnProperty;
import ar.com.fdvs.dj.domain.CustomExpression;
import ar.com.fdvs.dj.domain.DJCalculation;
import ar.com.fdvs.dj.domain.DJChart;
import ar.com.fdvs.dj.domain.DJCrosstab;
import ar.com.fdvs.dj.domain.DJQuery;
import ar.com.fdvs.dj.domain.DJValueFormatter;
import ar.com.fdvs.dj.domain.DJWaterMark;
import ar.com.fdvs.dj.domain.DynamicReport;
import ar.com.fdvs.dj.domain.DynamicReportOptions;
import ar.com.fdvs.dj.domain.ImageBanner;
import ar.com.fdvs.dj.domain.Style;
import ar.com.fdvs.dj.domain.constants.Font;
import ar.com.fdvs.dj.domain.constants.GroupLayout;
import ar.com.fdvs.dj.domain.constants.ImageScaleMode;
import ar.com.fdvs.dj.domain.constants.Page;
import ar.com.fdvs.dj.domain.entities.DJColSpan;
import ar.com.fdvs.dj.domain.entities.DJGroup;
import ar.com.fdvs.dj.domain.entities.DJGroupVariable;
import ar.com.fdvs.dj.domain.entities.DJGroupVariableDef;
import ar.com.fdvs.dj.domain.entities.DJVariable;
import ar.com.fdvs.dj.domain.entities.Parameter;
import ar.com.fdvs.dj.domain.entities.Subreport;
import ar.com.fdvs.dj.domain.entities.SubreportParameter;
import ar.com.fdvs.dj.domain.entities.columns.AbstractColumn;
import ar.com.fdvs.dj.domain.entities.columns.GlobalGroupColumn;
import ar.com.fdvs.dj.domain.entities.columns.PercentageColumn;
import ar.com.fdvs.dj.domain.entities.columns.PropertyColumn;
import net.sf.jasperreports.engine.JasperReport;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Builder created to give users a friendly way of creating a
 * DynamicReport.</br>
 * </br>
 * Usage example: </br>
 * DynamicReportBuilder drb = new DynamicReportBuilder(); Integer margin = new
 * Integer(20); drb.addTitle("Clients List").addTitleStyle(titleStyle)
 * .addSubtitle("Clients without debt") .addDetailHeight(new Integer(15))
 * .addLeftMargin(margin).addRightMargin(margin).addTopMargin(margin)
 * .addBottomMargin(margin)
 * .addPrintBackgroundOnOddRows(true).addOddRowBackgroundStyle(oddRowStyle)
 * .addColumnsPerPage(new Integer(1)).addColumnSpace(new Integer(5))
 * .addColumn(column1).addColumn(column2).build();
 * </br>
 * Like with all DJ's builders, it's usage must end with a call to build()
 * mehtod.
 * </br>
 */
@SuppressWarnings("WeakerAccess")
public class DynamicReportBuilder {

    /**
     * DynamicReportBuilder cannot be used twice since this produced undesired
     * results on the generated DynamicReport Object.
     */
    protected boolean built = false;

    protected final DynamicReport report = new DynamicReport();
    protected final DynamicReportOptions options = new DynamicReportOptions();
    protected final List<DJCrosstab> globalFooterCrosstabs = new ArrayList<DJCrosstab>();
    protected final List<DJCrosstab> globalHeaderCrosstabs = new ArrayList<DJCrosstab>();
    protected final List<AutoText> autoTexts = new ArrayList<AutoText>();
    protected final Map<Integer, List<Subreport>> groupFooterSubreports = new HashMap<Integer, List<Subreport>>();
    protected final Map<Integer, List<Subreport>> groupHeaderSubreports = new HashMap<Integer, List<Subreport>>();

    protected final DJGroup globalVariablesGroup;

    protected final List<Subreport> concatenatedReports = new ArrayList<Subreport>();

    public DynamicReportBuilder() {
        super();
        globalVariablesGroup = createDummyGroup();
    }

    public DynamicReportBuilder addAutoText(AutoText text) {
        if (AutoText.WIDTH_NOT_SET == text.getWidth()) {
            text.setWidth(AutoText.DEFAULT_WIDTH);
        }
        if (AutoText.WIDTH_NOT_SET == text.getWidth2()) {
            text.setWidth2(AutoText.DEFAULT_WIDTH);
        }

        autoTexts.add(text);
        return this;
    }

    /**
     * Adds an autotext to the Report, this are common texts such us "Page X/Y",
     * "Created on 07/25/2007", etc.
     * <br>
     * The parameters are all constants from the
     * <code>ar.com.fdvs.dj.domain.AutoText</code> class
     *
     * @param type      One of these constants:     <br>AUTOTEXT_PAGE_X_OF_Y <br>
     *                  AUTOTEXT_PAGE_X_SLASH_Y <br> AUTOTEXT_PAGE_X, AUTOTEXT_CREATED_ON <br>
     *                  AUTOTEXT_CUSTOM_MESSAGE
     * @param position  POSITION_HEADER or POSITION_FOOTER
     * @param alignment <br>ALIGMENT_LEFT <br> ALIGMENT_CENTER <br>
     *                  ALIGMENT_RIGHT
     * @param pattern   only for dates:  <br>PATTERN_DATE_DATE_ONLY <br>
     *                  PATTERN_DATE_TIME_ONLY <br> PATTERN_DATE_DATE_TIME
     * @return
     */
    public DynamicReportBuilder addAutoText(byte type, byte position, byte alignment, byte pattern) {
        HorizontalBandAlignment alignment_ = HorizontalBandAlignment.buildAligment(alignment);
        AutoText text = new AutoText(type, position, alignment_, pattern);
        addAutoText(text);
        return this;
    }

    /**
     * Adds an autotext to the Report, this are common texts such us "Page X/Y",
     * "Created on 07/25/2007", etc.
     * <br>
     * The parameters are all constants from the
     * <code>ar.com.fdvs.dj.domain.AutoText</code> class
     *
     * @param type      One of these constants:     <br>AUTOTEXT_PAGE_X_OF_Y <br>
     *                  AUTOTEXT_PAGE_X_SLASH_Y <br> AUTOTEXT_PAGE_X, AUTOTEXT_CREATED_ON <br>
     *                  AUTOTEXT_CUSTOM_MESSAGE
     * @param position  POSITION_HEADER or POSITION_FOOTER
     * @param alignment <br>ALIGMENT_LEFT <br> ALIGMENT_CENTER <br>
     *                  ALIGMENT_RIGHT
     * @param pattern   only for dates:  <br>PATTERN_DATE_DATE_ONLY <br>
     *                  PATTERN_DATE_TIME_ONLY <br> PATTERN_DATE_DATE_TIME
     * @param width     with of the autotext. If autotext is of the type
     *                  AUTOTEXT_PAGE_X_OF_Y or AUTOTEXT_PAGE_X_SLASH_Y then is the width of the
     *                  first part
     * @param width2    If autotext is of the type AUTOTEXT_PAGE_X_OF_Y or
     *                  AUTOTEXT_PAGE_X_SLASH_Y, this defines the width given to the "total page"
     *                  variable
     * @return
     */
    public DynamicReportBuilder addAutoText(byte type, byte position, byte alignment, byte pattern, int width, int width2) {
        HorizontalBandAlignment alignment_ = HorizontalBandAlignment.buildAligment(alignment);
        AutoText text = new AutoText(type, position, alignment_, pattern, width, width2);
        addAutoText(text);

        return this;
    }

    /**
     * Adds a custom fixed message (literal) in header or footer. The message
     * width will be the page witdth<br>
     * The parameters are all constants from the
     * <code>ar.com.fdvs.dj.domain.AutoText</code> class
     * <br>
     * <br>
     *
     * @param message   The text to show
     * @param position  POSITION_HEADER or POSITION_FOOTER
     * @param alignment <br>ALIGMENT_LEFT <br> ALIGMENT_CENTER <br>
     *                  ALIGMENT_RIGHT
     * @return
     */
    public DynamicReportBuilder addAutoText(String message, byte position, byte alignment) {
        HorizontalBandAlignment alignment_ = HorizontalBandAlignment.buildAligment(alignment);
        AutoText text = new AutoText(message, position, alignment_);
        text.setWidth(AutoText.WIDTH_NOT_SET);
        addAutoText(text);
        return this;
    }

    /**
     * Adds a custom fixed message (literal) in header or footer. The message
     * width will be the page width<br>
     * The parameters are all constants from the
     * <code>ar.com.fdvs.dj.domain.AutoText</code> class
     * <br>
     * <br>
     *
     * @param message   The text to show
     * @param position  POSITION_HEADER or POSITION_FOOTER
     * @param alignment <br>ALIGMENT_LEFT <br> ALIGMENT_CENTER <br>
     *                  ALIGMENT_RIGHT
     * @param width     the width of the message
     * @return
     */
    public DynamicReportBuilder addAutoText(String message, byte position, byte alignment, int width) {
        HorizontalBandAlignment alignment_ = HorizontalBandAlignment.buildAligment(alignment);
        AutoText text = new AutoText(message, position, alignment_, width);
        addAutoText(text);
        return this;
    }

    public DynamicReportBuilder addAutoText(String message, byte position, byte alignment, int width, Style style) {
        HorizontalBandAlignment alignment_ = HorizontalBandAlignment.buildAligment(alignment);
        AutoText text = new AutoText(message, position, alignment_, width);
        text.setStyle(style);
        addAutoText(text);
        return this;
    }

    /**
     * Adds an autotext to the Report, this are common texts such us "Page X/Y",
     * "Created on 07/25/2007", etc.
     * <br>
     * The parameters are all constants from the
     * <code>ar.com.fdvs.dj.domain.AutoText</code> class
     *
     * @param type      One of these constants:     <br>AUTOTEXT_PAGE_X_OF_Y <br>
     *                  AUTOTEXT_PAGE_X_SLASH_Y
     *                  <br> AUTOTEXT_PAGE_X, AUTOTEXT_CREATED_ON <br> AUTOTEXT_CUSTOM_MESSAGE
     * @param position  POSITION_HEADER or POSITION_FOOTER
     * @param alignment <br>ALIGMENT_LEFT <br> ALIGMENT_CENTER <br>
     *                  ALIGMENT_RIGHT
     * @return
     */
    public DynamicReportBuilder addAutoText(byte type, byte position, byte alignment, int width, int width2) {
        HorizontalBandAlignment alignment_ = HorizontalBandAlignment.buildAligment(alignment);
        AutoText text = new AutoText(type, position, alignment_);
        text.setWidth(width);
        text.setWidth2(width2);
        addAutoText(text);
        return this;
    }

    public DynamicReportBuilder addAutoText(byte type, byte position, byte alignment, int width, int width2, Style style) {
        return addAutoText(type, position, alignment, width, width2, 0, false, style);
    }

    public DynamicReportBuilder addAutoText(byte type, byte position, byte alignment, int width, int width2, int pageOffset, Style style) {
        return addAutoText(type, position, alignment, width, width2, pageOffset, false, style);
    }

    public DynamicReportBuilder addAutoText(byte type, byte position, byte alignment, int width, int width2, int pageOffset, boolean useI18n, Style style) {
        HorizontalBandAlignment alignment_ = HorizontalBandAlignment.buildAligment(alignment);
        AutoText text = new AutoText(type, position, alignment_);
        text.setPageOffset(pageOffset);
        text.setUseI18n(useI18n);
        text.setWidth(width);
        text.setWidth2(width2);
        text.setStyle(style);
        addAutoText(text);
        return this;
    }

    /**
     * Adds an autotext to the Report, this are common texts such us "Page X/Y",
     * "Created on 07/25/2007", etc.
     * <br>
     * The parameters are all constants from the
     * <code>ar.com.fdvs.dj.domain.AutoText</code> class
     *
     * @param type      One of these constants:     <br>AUTOTEXT_PAGE_X_OF_Y <br>
     *                  AUTOTEXT_PAGE_X_SLASH_Y
     *                  <br> AUTOTEXT_PAGE_X, AUTOTEXT_CREATED_ON <br> AUTOTEXT_CUSTOM_MESSAGE
     * @param position  POSITION_HEADER or POSITION_FOOTER
     * @param alignment <br>ALIGMENT_LEFT <br> ALIGMENT_CENTER <br>
     *                  ALIGMENT_RIGHT
     * @return
     */
    public DynamicReportBuilder addAutoText(byte type, byte position, byte alignment) {
        HorizontalBandAlignment alignment_ = HorizontalBandAlignment.buildAligment(alignment);
        AutoText text = new AutoText(type, position, alignment_);
        text.setWidth(AutoText.WIDTH_NOT_SET);
        text.setWidth2(AutoText.WIDTH_NOT_SET);
        addAutoText(text);
        return this;
    }

    /**
     * Builds the DynamicReport object. Cannot be used twice since this produced
     * undesired results on the generated DynamicReport object
     *
     * @return
     */
    public DynamicReport build() {

        if (built) {
            throw new DJBuilderException("DynamicReport already built. Cannot use more than once.");
        } else {
            built = true;
        }

        report.setOptions(options);
        if (!globalVariablesGroup.getFooterVariables().isEmpty() || !globalVariablesGroup.getHeaderVariables().isEmpty() || !globalVariablesGroup.getVariables().isEmpty() || hasPercentageColumn()) {
            report.getColumnsGroups().add(0, globalVariablesGroup);
        }

        createChartGroups();

        addGlobalCrosstabs();

        addSubreportsToGroups();

        concatenateReports();

        report.setAutoTexts(autoTexts);
        return report;
    }

    private boolean hasPercentageColumn() {
        for (AbstractColumn abstractColumn : report.getColumns()) {
            if (abstractColumn instanceof PercentageColumn) {
                return true;
            }
        }
        return false;
    }

    private void addGlobalCrosstabs() {
        //For header
        for (DJCrosstab djcross : globalHeaderCrosstabs) {
            DJGroup globalGroup = createDummyGroupForCrosstabs("crosstabHeaderGroup-" + globalHeaderCrosstabs.indexOf(djcross));
            globalGroup.getHeaderCrosstabs().add(djcross);
            report.getColumnsGroups().add(0, globalGroup);

        }

        //For footer
        for (DJCrosstab djcross : globalFooterCrosstabs) {
            DJGroup globalGroup = createDummyGroupForCrosstabs("crosstabFooterGroup-" + globalFooterCrosstabs.indexOf(djcross));
            globalGroup.getFooterCrosstabs().add(djcross);
            report.getColumnsGroups().add(0, globalGroup);

        }
    }

    /**
     * Because the groups are not created until we call the "build()" method,
     * all the subreports that must go inside a group are handled here.
     */
    protected void addSubreportsToGroups() {
        for (Integer groupNum : groupFooterSubreports.keySet()) {
            List<Subreport> list = groupFooterSubreports.get(groupNum);
            DJGroup group = report.getColumnsGroups().get(groupNum - 1);
            group.getFooterSubreports().addAll(list);
        }

        for (Integer groupNum : groupHeaderSubreports.keySet()) {
            List<Subreport> list = groupHeaderSubreports.get(groupNum);
            DJGroup group = report.getColumnsGroups().get(groupNum - 1);
            group.getHeaderSubreports().addAll(list);
        }

    }

    /**
     * Create dummy groups for each concatenated report, and in the footer of
     * each group adds the subreport.
     */
    protected void concatenateReports() {

        if (!concatenatedReports.isEmpty()) { // dummy group for page break if needed
            DJGroup globalGroup = createDummyGroup();
            report.getColumnsGroups().add(0, globalGroup);
        }

        for (Subreport subreport : concatenatedReports) {
            DJGroup globalGroup = createDummyGroup();
            globalGroup.getFooterSubreports().add(subreport);
            report.getColumnsGroups().add(0, globalGroup);
        }
    }

    /**
     * @return
     */
    private DJGroup createDummyGroup() {
        DJGroup globalGroup = new DJGroup();
        globalGroup.setLayout(GroupLayout.EMPTY);
        GlobalGroupColumn globalCol = new GlobalGroupColumn("global");
        globalGroup.setColumnToGroupBy(globalCol);
        return globalGroup;
    }

    private DJGroup createDummyGroupForCrosstabs(String name) {
        DJGroup globalGroup = new DJGroup();
        globalGroup.setLayout(GroupLayout.EMPTY);
        GlobalGroupColumn globalCol = new GlobalGroupColumn(name);
        globalCol.setTitle("");
        globalGroup.setColumnToGroupBy(globalCol);
        return globalGroup;
    }

    public DynamicReportBuilder setTitle(String title) {
        report.setTitle(title);
        return this;
    }

    public DynamicReportBuilder setTitleIsJrExpression(boolean isExpression) {
        report.setTitleIsJrExpression(isExpression);
        return this;
    }

    /**
     * Sets the name of the report.<br>
     * When exporting to Excel, this is going to be the sheet name. <b>Be
     * careful</b> because Excel only allows 32 alphanumeric characters
     *
     * @param reportName
     * @return
     */
    public DynamicReportBuilder setReportName(String reportName) {
        report.setReportName(reportName);
        return this;
    }

    public DynamicReportBuilder setSubtitle(String subtitle) {
        report.setSubtitle(subtitle);
        return this;
    }

    public DynamicReportBuilder addColumn(AbstractColumn column) {
        report.getColumns().add(column);
        return this;
    }

    /**
     * returns an unmodifiable List of the columns so far
     *
     * @return
     */
    public List getColumns() {
        return Collections.unmodifiableList(report.getColumns());
    }

    public DynamicReportBuilder addGroup(DJGroup group) {
        report.getColumnsGroups().add(group);
        return this;
    }

    public DynamicReportBuilder setHeaderHeight(int height) {
        options.setHeaderHeight(height);
        return this;
    }

    /**
     * @return
     * @deprecated @param height
     */
    public DynamicReportBuilder setFooterHeight(int height) {
        options.setFooterVariablesHeight(height);
        return this;
    }

    public DynamicReportBuilder setFooterVariablesHeight(int height) {
        options.setFooterVariablesHeight(height);
        return this;
    }

    public DynamicReportBuilder setHeaderVariablesHeight(int height) {
        options.setHeaderVariablesHeight(height);
        return this;
    }

    public DynamicReportBuilder setDetailHeight(int height) {
        options.setDetailHeight(height);
        return this;
    }

    public DynamicReportBuilder setLeftMargin(int margin) {
        options.setLeftMargin(margin);
        return this;
    }

    public DynamicReportBuilder setRightMargin(int margin) {
        options.setRightMargin(margin);
        return this;
    }

    public DynamicReportBuilder setTopMargin(int margin) {
        options.setTopMargin(margin);
        return this;
    }


    public DynamicReportBuilder setBottomMargin(int margin) {
        options.setBottomMargin(margin);
        return this;
    }

    public DynamicReportBuilder setColumnsPerPage(int numColumns) {
        options.setColumnsPerPage(numColumns);
        return this;
    }

    public DynamicReportBuilder setColumnsPerPage(int numColumns, int columnSpace) {
        options.setColumnsPerPage(numColumns);
        options.setColumnSpace(columnSpace);
        return this;
    }

    public DynamicReportBuilder setColumnSpace(int columSpace) {
        options.setColumnSpace(columSpace);
        return this;
    }

    /**
     * When FALSE, no column names are printed (in the header band)
     *
     * @param bool
     * @return
     */
    public DynamicReportBuilder setPrintColumnNames(boolean bool) {
        options.setPrintColumnNames(bool);
        return this;
    }

    /**
     * When TRUE, no page break at all (useful for Excell) Default is FALSE
     *
     * @param bool
     * @return
     */
    public DynamicReportBuilder setIgnorePagination(boolean bool) {
        options.setIgnorePagination(bool);
        return this;
    }

    public DynamicReportBuilder setUseFullPageWidth(boolean useFullwidth) {
        options.setUseFullPageWidth(useFullwidth);
        return this;
    }

    public DynamicReportBuilder setTitleStyle(Style titleStyle) {
        this.report.setTitleStyle(titleStyle);
        return this;
    }

    public DynamicReportBuilder setSubtitleStyle(Style subtitleStyle) {
        this.report.setSubtitleStyle(subtitleStyle);
        return this;
    }

    public DynamicReportBuilder setPrintBackgroundOnOddRows(boolean printBackgroundOnOddRows) {
        this.options.setPrintBackgroundOnOddRows(printBackgroundOnOddRows);
        return this;
    }

    public DynamicReportBuilder setOddRowBackgroundStyle(Style oddRowBackgroundStyle) {
        this.options.setOddRowBackgroundStyle(oddRowBackgroundStyle);
        return this;
    }

    public DynamicReportBuilder setGrandTotalLegend(String title) {
        this.globalVariablesGroup.getColumnToGroupBy().setTitle(title);
        return this;
    }

    public DynamicReportBuilder setGlobalHeaderVariableHeight(int height) {
        globalVariablesGroup.setHeaderVariablesHeight(height);
        return this;
    }

    public DynamicReportBuilder setGlobalFooterVariableHeight(int height) {
        globalVariablesGroup.setFooterVariablesHeight(height);
        return this;
    }

    /**
     * @param col
     * @param op
     * @return
     */
    public DynamicReportBuilder addGlobalHeaderVariable(AbstractColumn col, DJCalculation op) {
        globalVariablesGroup.addHeaderVariable(new DJGroupVariable(col, op));
        return this;
    }

    public DynamicReportBuilder addGlobalHeaderVariable(AbstractColumn col, DJCalculation op, Style style) {
        globalVariablesGroup.addHeaderVariable(new DJGroupVariable(col, op, style));
        return this;
    }

    public DynamicReportBuilder addGlobalHeaderVariable(AbstractColumn col, DJCalculation op, Style style, DJValueFormatter valueFormatter) {
        globalVariablesGroup.addHeaderVariable(new DJGroupVariable(col, op, style, valueFormatter));
        return this;
    }

    public DynamicReportBuilder addGlobalHeaderVariable(DJGroupVariable variable) {
        globalVariablesGroup.addHeaderVariable(variable);
        return this;
    }

    public DynamicReportBuilder addGlobalHeaderVariable(AbstractColumn column, CustomExpression valueExpression) {
        globalVariablesGroup.addHeaderVariable(new DJGroupVariable(column, valueExpression));
        return this;
    }

    public DynamicReportBuilder addGlobalHeaderVariable(AbstractColumn column, CustomExpression valueExpression, Style style) {
        globalVariablesGroup.addHeaderVariable(new DJGroupVariable(column, valueExpression, style));
        return this;
    }

    /**
     * @param col
     * @param op
     * @return
     */
    public DynamicReportBuilder addGlobalFooterVariable(AbstractColumn col, DJCalculation op) {
        globalVariablesGroup.addFooterVariable(new DJGroupVariable(col, op));
        return this;
    }

    public DynamicReportBuilder addGlobalFooterVariable(AbstractColumn col, DJCalculation op, Style style) {
        globalVariablesGroup.addFooterVariable(new DJGroupVariable(col, op, style));
        return this;
    }

    public DynamicReportBuilder addGlobalFooterVariable(AbstractColumn col, DJCalculation op, Style style, DJValueFormatter valueFormatter) {
        globalVariablesGroup.addFooterVariable(new DJGroupVariable(col, op, style, valueFormatter));
        return this;
    }

    public DynamicReportBuilder addGlobalFooterVariable(DJGroupVariable variable) {
        globalVariablesGroup.addFooterVariable(variable);
        return this;
    }

    public DynamicReportBuilder addGlobalFooterVariable(AbstractColumn column, CustomExpression valueExpression) {
        globalVariablesGroup.addFooterVariable(new DJGroupVariable(column, valueExpression));
        return this;
    }

    public DynamicReportBuilder addGlobalFooterVariable(AbstractColumn column, CustomExpression valueExpression, Style style) {
        globalVariablesGroup.addFooterVariable(new DJGroupVariable(column, valueExpression, style));
        return this;
    }

    public DynamicReportBuilder addGlobalColumnVariable(String position, AbstractColumn col, DJCalculation op) {
        if (DJConstants.FOOTER.equals(position)) {
            globalVariablesGroup.addFooterVariable(new DJGroupVariable(col, op));
        } else {
            globalVariablesGroup.addHeaderVariable(new DJGroupVariable(col, op));
        }
        return this;
    }

    public DynamicReportBuilder addGlobalColumnVariable(String position, AbstractColumn col, DJCalculation op, Style style) {
        if (DJConstants.FOOTER.equals(position)) {
            globalVariablesGroup.addFooterVariable(new DJGroupVariable(col, op, style));
        } else {
            globalVariablesGroup.addHeaderVariable(new DJGroupVariable(col, op, style));
        }
        return this;
    }

    public DynamicReportBuilder addGlobalColumnVariable(String position, AbstractColumn col, DJCalculation op, Style style, DJValueFormatter valueFormatter) {
        if (DJConstants.FOOTER.equals(position)) {
            globalVariablesGroup.addFooterVariable(new DJGroupVariable(col, op, style, valueFormatter));
        } else {
            globalVariablesGroup.addHeaderVariable(new DJGroupVariable(col, op, style, valueFormatter));
        }
        return this;
    }

    public DynamicReportBuilder addGlobalColumnVariable(String position, DJGroupVariable variable) {
        if (DJConstants.FOOTER.equals(position)) {
            globalVariablesGroup.addFooterVariable(variable);
        } else {
            globalVariablesGroup.addHeaderVariable(variable);
        }
        return this;
    }

    public DynamicReportBuilder addGlobalColumnVariable(String position, AbstractColumn column, CustomExpression valueExpression) {
        if (DJConstants.FOOTER.equals(position)) {
            globalVariablesGroup.addFooterVariable(new DJGroupVariable(column, valueExpression));
        } else {
            globalVariablesGroup.addHeaderVariable(new DJGroupVariable(column, valueExpression));
        }
        return this;
    }

    /**
     * @param position        DJConstants.FOOTER or DJConstants.HEADER
     * @param column          column to operate with
     * @param valueExpression
     * @param style
     * @return
     */
    public DynamicReportBuilder addGlobalColumnVariable(String position, AbstractColumn column, CustomExpression valueExpression, Style style) {
        if (DJConstants.FOOTER.equals(position)) {
            globalVariablesGroup.addFooterVariable(new DJGroupVariable(column, valueExpression, style));
        } else {
            globalVariablesGroup.addHeaderVariable(new DJGroupVariable(column, valueExpression, style));
        }
        return this;
    }

    /**
     * For variable registration only (can bee later referenced in custom
     * expression)
     *
     * @param name
     * @param col
     * @param op
     * @return
     */
    public DynamicReportBuilder addGlobalVariable(String name, AbstractColumn col, DJCalculation op) {
        globalVariablesGroup.addVariable(new DJGroupVariableDef(name, col, op));
        return this;
    }

    /**
     * @param name
     * @param prop
     * @param op
     * @return
     * @see DynamicReportBuilder#addGlobalVariable(String, AbstractColumn,
     * DJCalculation)
     */
    public DynamicReportBuilder addGlobalVariable(String name, ColumnProperty prop, DJCalculation op) {
        globalVariablesGroup.addVariable(new DJGroupVariableDef(name, prop, op));
        return this;
    }

    /**
     * @param name
     * @param property
     * @param className
     * @param op
     * @return
     * @see DynamicReportBuilder#addGlobalVariable(String, AbstractColumn,
     * DJCalculation)
     */
    public DynamicReportBuilder addGlobalVariable(String name, String property, String className, DJCalculation op) {
        globalVariablesGroup.addVariable(new DJGroupVariableDef(name, new ColumnProperty(property, className), op));
        return this;
    }

    public DynamicReportBuilder setTitleHeight(int height) {
        options.setTitleHeight(height);
        return this;
    }

    public DynamicReportBuilder setSubtitleHeight(int height) {
        options.setSubtitleHeight(height);
        return this;
    }

    /**
     * Defines the page size and orientation.<br/>
     * Common pages size and orientation are constants of
     * ar.com.fdvs.dj.domain.constants.Page
     *
     * @param page
     * @return
     */
    public DynamicReportBuilder setPageSizeAndOrientation(Page page) {
        options.setPage(page);
        return this;
    }

    @Deprecated
    public DynamicReportBuilder addImageBanner(String path, int width, int height, byte align) {
        ImageBanner.Alignment alignment = ImageBanner.Alignment.fromValue(align);
        if (alignment == null)
            throw new DJException("Invalid ImageBanner.Alignment");

        ImageBanner banner = new ImageBanner(path, width, height, alignment);
        options.getImageBanners().put(alignment, banner);
        return this;
    }

    public DynamicReportBuilder addImageBanner(String path, int width, int height, ImageBanner.Alignment alignment) {
        ImageBanner banner = new ImageBanner(path, width, height, alignment);
        options.getImageBanners().put(alignment, banner);
        return this;
    }

    @Deprecated
    public DynamicReportBuilder addImageBanner(String path, int width, int height, byte align, ImageScaleMode scaleMode) {
        ImageBanner.Alignment alignment = ImageBanner.Alignment.fromValue(align);
        if (alignment == null)
            throw new DJException("Invalid ImageBanner.Alignment");

        ImageBanner banner = new ImageBanner(path, width, height, alignment);
        banner.setScaleMode(scaleMode);
        options.getImageBanners().put(alignment, banner);
        return this;
    }

    public DynamicReportBuilder addImageBanner(String path, int width, int height, ImageBanner.Alignment alignment, ImageScaleMode scaleMode) {
        ImageBanner banner = new ImageBanner(path, width, height, alignment);
        banner.setScaleMode(scaleMode);
        options.getImageBanners().put(alignment, banner);
        return this;
    }

    @Deprecated
    public DynamicReportBuilder addFooterImageBanner(String path, int width, int height, byte align, ImageScaleMode scaleMode) {
        ImageBanner.Alignment alignment = ImageBanner.Alignment.fromValue(align);
        if (alignment == null)
            throw new DJException("Invalid ImageBanner.Alignment");

        ImageBanner banner = new ImageBanner(path, width, height, alignment);
        banner.setScaleMode(scaleMode);
        options.getFooterImageBanners().put(alignment, banner);
        return this;
    }

    public DynamicReportBuilder addFooterImageBanner(String path, int width, int height, ImageBanner.Alignment alignment, ImageScaleMode scaleMode) {
        ImageBanner banner = new ImageBanner(path, width, height, alignment);
        banner.setScaleMode(scaleMode);
        options.getFooterImageBanners().put(alignment, banner);
        return this;
    }


    public DynamicReportBuilder addFirstPageImageBanner(String path, int width, int height, ImageBanner.Alignment align) {
        ImageBanner banner = new ImageBanner(path, width, height, align);
        options.getFirstPageImageBanners().put(align, banner);
        return this;
    }

    @Deprecated
    public DynamicReportBuilder addFirstPageImageBanner(String path, int width, int height, byte align) {
        ImageBanner.Alignment alignment = ImageBanner.Alignment.fromValue(align);
        if (alignment == null)
            throw new DJException("Invalid ImageBanner.Alignment");

        ImageBanner banner = new ImageBanner(path, width, height, alignment);
        options.getFirstPageImageBanners().put(alignment, banner);
        return this;
    }

    @Deprecated
    public DynamicReportBuilder addFirstPageFooterImageBanner(String path, int width, int height, byte align) {
        ImageBanner.Alignment alignment = ImageBanner.Alignment.fromValue(align);
        if (alignment == null)
            throw new DJException("Invalid ImageBanner.Alignment");

        ImageBanner banner = new ImageBanner(path, width, height, alignment);
        options.getFirstPageFooterImageBanners().put(alignment, banner);
        return this;
    }

    public DynamicReportBuilder addFirstPageFooterImageBanner(String path, int width, int height, ImageBanner.Alignment alignment) {
        ImageBanner banner = new ImageBanner(path, width, height, alignment);
        options.getFirstPageFooterImageBanners().put(alignment, banner);
        return this;
    }

    @Deprecated
    public DynamicReportBuilder addFirstPageImageBanner(String path, int width, int height, byte align, ImageScaleMode scaleMode) {
        ImageBanner.Alignment alignment = ImageBanner.Alignment.fromValue(align);
        if (alignment == null)
            throw new DJException("Invalid ImageBanner.Alignment");

        ImageBanner banner = new ImageBanner(path, width, height, alignment);
        banner.setScaleMode(scaleMode);
        options.getFirstPageImageBanners().put(alignment, banner);
        return this;
    }

    public DynamicReportBuilder addFirstPageImageBanner(String path, int width, int height, ImageBanner.Alignment alignment, ImageScaleMode scaleMode) {
        ImageBanner banner = new ImageBanner(path, width, height, alignment);
        banner.setScaleMode(scaleMode);
        options.getFirstPageImageBanners().put(alignment, banner);
        return this;
    }


    /**
     * Registers a field that is not necesary bound to a column, it can be used
     * in a custom expression
     *
     * @param name
     * @param className
     * @return
     */
    public DynamicReportBuilder addField(String name, String className) {
        return addField(new ColumnProperty(name, className));
    }

    public DynamicReportBuilder addField(String name, Class clazz) {
        return addField(new ColumnProperty(name, clazz.getName()));
    }

    /**
     * Registers a field that is not necesary bound to a column, it can be used
     * in a custom expression
     *
     * @param columnProperty
     * @return
     */
    public DynamicReportBuilder addField(ColumnProperty columnProperty) {
        report.getFields().add(columnProperty);
        return this;
    }

    /**
     * Returns registered fields so far.
     *
     * @return List<ColumnProperty>
     */
    public List<ColumnProperty> getFields() {
        return report.getFields();
    }

    /**
     * Registers a field that is not necesary bound to a column, it can be used
     * in a custom expression
     *
     * @return A Dynamic Report Builder
     * @deprecated
     */
    public DynamicReportBuilder addChart(DJChart chart) {
        report.getCharts().add(chart);
        return this;
    }

    /**
     * Registers a field that is not necesary bound to a column, it can be used
     * in a custom expression
     *
     * @param djChart a DJ chart
     * @return A Dynamic Report Builder
     */
    public DynamicReportBuilder addChart(ar.com.fdvs.dj.domain.chart.DJChart djChart) {
        report.getNewCharts().add(djChart);
        return this;
    }

    private void createChartGroups() {
        for (ar.com.fdvs.dj.domain.chart.DJChart djChart : report.getNewCharts()) {
            DJGroup djGroup = getChartColumnsGroup(djChart);
            if (djGroup == null) {
                djGroup = new GroupBuilder().setCriteriaColumn(djChart.getDataset().getColumnsGroup())
                        .setGroupLayout(GroupLayout.VALUE_FOR_EACH)
                        .build();
                addGroup(djGroup);
            }
        }
    }

    private DJGroup getChartColumnsGroup(ar.com.fdvs.dj.domain.chart.DJChart djChart) {
        PropertyColumn columnsGroup = djChart.getDataset().getColumnsGroup();
        for (DJGroup djGroup : report.getColumnsGroups()) {
            if (djGroup.getColumnToGroupBy() == columnsGroup) {
                return djGroup;
            }
        }
        return null;
    }

    /**
     * The full path of a jrxml file, or the path in the classpath of a jrxml
     * resource.
     *
     * @param path
     * @return
     */
    public DynamicReportBuilder setTemplateFile(String path) {
        report.setTemplateFileName(path);
        return this;
    }

    /**
     * The full path of a jrxml file, or the path in the classpath of a jrxml
     * resource.
     *
     * @param path
     * @return
     */
    public DynamicReportBuilder setTemplateFile(String path, boolean importFields, boolean importVariables, boolean importParameters, boolean importDatasets) {
        report.setTemplateFileName(path);
        report.setTemplateImportFields(importFields);
        report.setTemplateImportParameters(importParameters);
        report.setTemplateImportVariables(importVariables);
        report.setTemplateImportDatasets(importDatasets);
        return this;
    }

    public DynamicReportBuilder setMargins(int top, int bottom, int left, int right) {

        options.setTopMargin(top);
        options.setBottomMargin(bottom);
        options.setLeftMargin(left);
        options.setRightMargin(right);

        return this;
    }

    public DynamicReportBuilder setDefaultStyles(Style title, Style subtitle, Style columnHeader, Style columDetail) {
        if (columDetail != null) {
            options.setDefaultDetailStyle(columDetail);
        }

        if (columnHeader != null) {
            options.setDefaultHeaderStyle(columnHeader);
        }

        if (subtitle != null) {
            report.setSubtitleStyle(subtitle);
        }

        if (title != null) {
            report.setTitleStyle(title);
        }

        return this;
    }

    /**
     * Adds the locale to use when filling the report.
     *
     * @param locale
     * @return
     */
    public DynamicReportBuilder setReportLocale(Locale locale) {
        report.setReportLocale(locale);
        return this;
    }

    /**
     * All concatenated reports are shown in the same order they are inserted
     *
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
                .setDynamicReport(dynamicReport, layoutManager)
                .build();

        concatenatedReports.add(subreport);
        return this;
    }

    public DynamicReportBuilder addConcatenatedReport(DynamicReport dynamicReport, LayoutManager layoutManager, String dataSourcePath, int dataSourceOrigin, int dataSourceType, boolean startOnNewPage) throws DJBuilderException {
        Subreport subreport = new SubReportBuilder()
                .setDataSource(dataSourceOrigin, dataSourceType, dataSourcePath)
                .setDynamicReport(dynamicReport, layoutManager)
                .setStartInNewPage(startOnNewPage)
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

    public DynamicReportBuilder addConcatenatedReport(JasperReport jasperReport, String dataSourcePath, int dataSourceOrigin, int dataSourceType, boolean startOnNewPage) throws DJBuilderException {
        Subreport subreport = new SubReportBuilder()
                .setDataSource(dataSourceOrigin, dataSourceType, dataSourcePath)
                .setReport(jasperReport)
                .setStartInNewPage(startOnNewPage)
                .build();

        concatenatedReports.add(subreport);
        return this;
    }

    /**
     * Adds in the group (starts with 1) "groupNumber" a subreport in the footer
     * band
     *
     * @param groupNumber
     * @param subreport
     * @return
     */
    public DynamicReportBuilder addSubreportInGroupFooter(int groupNumber, Subreport subreport) {
        return addSubreportInContainer(groupNumber, subreport, groupFooterSubreports);
    }

    protected DynamicReportBuilder addSubreportInContainer(int groupNumber, Subreport subreport, Map<Integer, List<Subreport>> container) {
        Integer groupNum = groupNumber;
        List<Subreport> list = container.get(groupNum);
        if (list == null) {
            list = new ArrayList<Subreport>();
            container.put(groupNum, list);
        }
        list.add(subreport);
        return this;
    }

    public DynamicReportBuilder addSubreportInGroupFooter(int groupNumber, DynamicReport dynamicReport, LayoutManager layoutManager, String dataSourcePath, int dataSourceOrigin, int dataSourceType) throws DJBuilderException {
        Subreport subreport = new SubReportBuilder()
                .setDataSource(dataSourceOrigin, dataSourceType, dataSourcePath)
                .setDynamicReport(dynamicReport, layoutManager)
                .build();

        return addSubreportInGroupFooter(groupNumber, subreport);
    }

    public DynamicReportBuilder addSubreportInGroupFooter(int groupNumber, DynamicReport dynamicReport, LayoutManager layoutManager, String dataSourcePath, int dataSourceOrigin, int dataSourceType, SubreportParameter[] params) throws DJBuilderException {
        SubReportBuilder srb = new SubReportBuilder();

        srb.setDataSource(dataSourceOrigin, dataSourceType, dataSourcePath)
                .setDynamicReport(dynamicReport, layoutManager);

        if (params != null) {
            for (SubreportParameter param : params) {
                srb.addParameter(param);
            }
        }

        Subreport subreport = srb.build();

        return addSubreportInGroupFooter(groupNumber, subreport);
    }

    /**
     * @param position         {@link DJConstants#FOOTER} or {@link DJConstants#HEADER}
     * @param groupNumber
     * @param dynamicReport
     * @param layoutManager
     * @param dataSourcePath
     * @param dataSourceOrigin
     * @param dataSourceType
     * @param params
     * @return
     * @throws DJBuilderException
     */
    public DynamicReportBuilder addSubreportInGroup(String position, int groupNumber, DynamicReport dynamicReport, LayoutManager layoutManager, String dataSourcePath, int dataSourceOrigin, int dataSourceType, SubreportParameter[] params) throws DJBuilderException {
        if (DJConstants.FOOTER.equals(position)) {
            addSubreportInGroupFooter(groupNumber, dynamicReport, layoutManager, dataSourcePath, dataSourceOrigin, dataSourceType, params);
        } else {
            addSubreportInGroupHeader(groupNumber, dynamicReport, layoutManager, dataSourcePath, dataSourceOrigin, dataSourceType, params);
        }
        return this;
    }

    public DynamicReportBuilder addSubreportInGroupHeader(int groupNumber, DynamicReport dynamicReport, LayoutManager layoutManager, String dataSourcePath, int dataSourceOrigin, int dataSourceType, SubreportParameter[] params) throws DJBuilderException {
        SubReportBuilder srb = new SubReportBuilder();

        srb.setDataSource(dataSourceOrigin, dataSourceType, dataSourcePath)
                .setDynamicReport(dynamicReport, layoutManager);

        if (params != null) {
            for (SubreportParameter param : params) {
                srb.addParameter(param);
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
            for (SubreportParameter param : params) {
                srb.addParameter(param);
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
            for (SubreportParameter param : params) {
                srb.addParameter(param);
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
            for (SubreportParameter param : params) {
                srb.addParameter(param);
            }
        }

        Subreport subreport = srb.build();

        return addSubreportInGroupFooter(groupNumber, subreport);
    }

    /**
     * @param position         position {@link DJConstants#FOOTER} or
     *                         {@link DJConstants#HEADER}
     * @param groupNumber
     * @param dynamicReport
     * @param layoutManager
     * @param dataSourcePath
     * @param dataSourceOrigin
     * @param dataSourceType
     * @param params
     * @param startInNewPage
     * @param fitParent
     * @return
     * @throws DJBuilderException
     */
    public DynamicReportBuilder addSubreportInGroup(String position, int groupNumber,
                                                    DynamicReport dynamicReport, LayoutManager layoutManager,
                                                    String dataSourcePath, int dataSourceOrigin, int dataSourceType,
                                                    SubreportParameter[] params, boolean startInNewPage, boolean fitParent)
            throws DJBuilderException {

        if (DJConstants.FOOTER.equals(position)) {
            addSubreportInGroupFooter(groupNumber, dynamicReport, layoutManager, dataSourcePath, dataSourceOrigin, dataSourceType, params, startInNewPage, fitParent);
        } else {
            addSubreportInGroupHeader(groupNumber, dynamicReport, layoutManager, dataSourcePath, dataSourceOrigin, dataSourceType, params, startInNewPage, fitParent);
        }

        return this;
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
            for (SubreportParameter param : params) {
                srb.addParameter(param);
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
        return addSubreportInContainer(groupNumber, subreport, groupHeaderSubreports);
    }

    public DynamicReportBuilder addSubreportInGroupHeader(int groupNumber, DynamicReport dynamicReport, LayoutManager layoutManager, String dataSourcePath, int dataSourceOrigin, int dataSourceType) throws DJBuilderException {
        Subreport subreport = new SubReportBuilder()
                .setDataSource(dataSourceOrigin, dataSourceType, dataSourcePath)
                .setDynamicReport(dynamicReport, layoutManager)
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
     * You can register styles object for later reference them directly. Parent
     * styles should be registered this way
     *
     * @param style
     * @return
     * @throws DJBuilderException
     */
    public DynamicReportBuilder addStyle(Style style) throws DJBuilderException {
        if (style.getName() == null) {
            throw new DJBuilderException("Invalid style. The style must have a name");
        }

        report.addStyle(style);

        return this;
    }

    /**
     * @return
     * @deprecated @param resourceBundle
     */
    public DynamicReportBuilder addResourceBundle(String resourceBundle) {
        return setResourceBundle(resourceBundle);
    }

    public DynamicReportBuilder setResourceBundle(String resourceBundle) {
        report.setResourceBundle(resourceBundle);
        return this;
    }

    public DynamicReportBuilder setGrandTotalLegendStyle(Style grandTotalStyle) {
        this.globalVariablesGroup.getColumnToGroupBy().setHeaderStyle(grandTotalStyle);
        this.globalVariablesGroup.getColumnToGroupBy().setStyle(grandTotalStyle);
        return this;
    }

    /**
     * Adds a crosstab in the header, before the the data
     *
     * @param cross
     * @return
     */
    public DynamicReportBuilder addHeaderCrosstab(DJCrosstab cross) {
        this.globalHeaderCrosstabs.add(cross);
        return this;
    }

    /**
     * Adds a crosstab in the footer of the report (at the end of all data)
     *
     * @param cross
     * @return
     */
    public DynamicReportBuilder addFooterCrosstab(DJCrosstab cross) {
        this.globalFooterCrosstabs.add(cross);
        return this;
    }

    /**
     * Adds main report query.
     *
     * @param text
     * @param language use constants from {@link DJConstants}
     * @return
     */
    public DynamicReportBuilder setQuery(String text, String language) {
        this.report.setQuery(new DJQuery(text, language));
        return this;
    }

    public DynamicReportBuilder addFont(String fontName, java.awt.Font font) {
        this.report.getFontsMap().put(fontName, font);
        return this;
    }

    /**
     * Defines the behaviour when the datasource is empty. Defatul vaue is
     * {@link DJConstants#WHEN_NO_DATA_TYPE_NO_PAGES}
     *
     * @return A Dynamic Report Builder
     */
    public DynamicReportBuilder setWhenNoDataType(byte whenNoDataType) {
        this.report.setWhenNoDataType(whenNoDataType);
        return this;
    }

    /**
     * @return A Dynamic Report Builder
     * @see DynamicReportBuilder#setWhenNoDataType
     */
    public DynamicReportBuilder setWhenNoDataNoPages() {
        this.report.setWhenNoDataType(DJConstants.WHEN_NO_DATA_TYPE_NO_PAGES);
        return this;
    }

    /**
     * @return A Dynamic Report Builder
     * @see DynamicReportBuilder#setWhenNoDataType
     */
    public DynamicReportBuilder setWhenNoDataBlankPage() {
        this.report.setWhenNoDataType(DJConstants.WHEN_NO_DATA_TYPE_BLANK_PAGE);
        return this;
    }

    /**
     * @return A Dynamic Report Builder
     * @see DynamicReportBuilder#setWhenNoDataType
     */
    public DynamicReportBuilder setWhenNoDataAllSectionNoDetail() {
        this.report.setWhenNoDataType(DJConstants.WHEN_NO_DATA_TYPE_ALL_SECTIONS_NO_DETAIL);
        return this;
    }

    /**
     * @return A Dynamic Report Builder
     * @see DynamicReportBuilder#setWhenNoDataType
     */
    public DynamicReportBuilder setWhenNoDataShowNoDataSection() {
        this.report.setWhenNoDataType(DJConstants.WHEN_NO_DATA_TYPE_NO_DATA_SECTION);
        return this;
    }

    /**
     * Defines what to show if a missing resource is referenced Possible values
     * are:<br>
     * DJConstants.WHEN_RESOURCE_MISSING_TYPE_EMPTY: Leaves and empty
     * field.<br/>
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
     *
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
     *
     * @param text
     * @param style            : the style of the text
     * @param showTitle        : if true, the title is shown
     * @param showColumnHeader : if true, the column headers are shown
     * @return A Dynamic Report Builder
     */
    public DynamicReportBuilder setWhenNoData(String text, Style style, boolean showTitle, boolean showColumnHeader) {
        this.report.setWhenNoDataStyle(style);
        this.report.setWhenNoDataText(text);
        this.report.setWhenNoDataType(DJConstants.WHEN_NO_DATA_TYPE_NO_DATA_SECTION);
        this.report.setWhenNoDataShowColumnHeader(showColumnHeader);
        this.report.setWhenNoDataShowTitle(showTitle);
        return this;
    }

    public DynamicReportBuilder addParameter(String name, String className) {
        this.report.addParameter(name, className);
        return this;
    }
    
    public DynamicReportBuilder addParameter(Parameter parameter) {
        this.report.addParameter(parameter);
        return this;
    }

    /**
     * If true and there is no room for a textfield at the end of a page in the
     * detail band, it will be splitted and continued in next page.
     *
     * @param split
     * @return A Dynamic Report Builder
     */
    public DynamicReportBuilder setAllowDetailSplit(boolean split) {
        this.report.setAllowDetailSplit(split);
        return this;
    }

    /**
     * Adds a property to report design, this properties are mostly used by
     * exporters to know if any specific configuration is needed
     *
     * @param name
     * @param value
     * @return A Dynamic Report Builder
     */
    public DynamicReportBuilder setProperty(String name, String value) {
        this.report.setProperty(name, value);
        return this;
    }

    /**
     * When false, no detail is shown. This is useful when using certain
     * grouping layout and header variables
     *
     * @param bool
     * @return A Dynamic Report Builder
     */
    public DynamicReportBuilder setShowDetailBand(boolean bool) {
        this.options.setShowDetailBand(bool);
        return this;
    }

    /**
     * Returns the "idx" column, idx is 0 based
     *
     * @param idx
     * @return
     */
    public AbstractColumn getColumn(int idx) {
        return this.report.getColumns().get(idx);
    }

    /**
     * Returns the "idx" group, idx is 0 based
     *
     * @param idx
     * @return
     */
    public DJGroup getGroup(int idx) {
        return this.report.getColumnsGroups().get(idx);
    }

    public DynamicReportBuilder setTitle(String title, boolean isExpression) {
        setTitle(title);
        setTitleIsJrExpression(isExpression);
        return this;
    }

    /**
     * Use this to register variables manually.
     *
     * @param var
     * @return
     */
    public DynamicReportBuilder addVariable(DJVariable var) {
        report.getVariables().add(var);
        return this;
    }

    /**
     * Set a colspan in a group of columns. First add the cols to the report
     *
     * @param colNumber    the index of the col
     * @param colQuantity  the number of cols how i will take
     * @param colspanTitle colspan title
     * @return a Dynamic Report Builder
     * @throws ColumnBuilderException When the index of the cols is out of
     *                                bounds.
     */
    public DynamicReportBuilder setColspan(int colNumber, int colQuantity, String colspanTitle) {
        this.setColspan(colNumber, colQuantity, colspanTitle, null);

        return this;

    }

    public DynamicReportBuilder setColspan(int colNumber, int colQuantity, String colspanTitle, Style colspanStyle) {
        List<AbstractColumn> cols;

        try {
            cols = report.getColumns().subList(colNumber, (colQuantity + colNumber));
        } catch (IndexOutOfBoundsException e) {
            String message = "Cols must be between 0 and " + (report.getColumns().size() - 1);
            throw new ColumnBuilderException(message, e);
        }

        DJColSpan colSpan = new DJColSpan();
        colSpan.setTitle(colspanTitle);
        colSpan.setColumns(cols);
        colSpan.setColspanHeaderStyle(colspanStyle);

        for (AbstractColumn abstractColumn : colSpan.getColumns()) {
            abstractColumn.setColSpan(colSpan);
        }

        return this;
    }

    /**
     * Sets the language of the expressions used in the report (can be one of
     * java, groovy, or javascript).
     * <p>
     * Default is DJConstants#REPORT_LANGUAGE_JAVA
     *
     * @param language
     * @return
     * @see DJConstants#REPORT_LANGUAGE_JAVA DJConstants#REPORT_LANGUAGE_GROOVY
     * DJConstants#REPORT_LANGUAGE_JAVASCRIPT
     * @see DynamicReport#language
     */
    public DynamicReportBuilder setLanguage(String language) {
        this.report.setLanguage(language);
        return this;

    }

    public DynamicReportBuilder addWatermark(String text) {
        return addWatermark(new DJWaterMark(text));
    }

    public DynamicReportBuilder addWatermark(DJWaterMark waterMark) {
        this.report.setWaterMark(waterMark);
        return this;
    }

    public DynamicReportBuilder addWatermark(String text, Font font, Color color, int angle) {
        return addWatermark(new DJWaterMark(text, font, Color.cyan, angle));
    }

    public DynamicReportBuilder setJasperDesignDecorator(JasperDesignDecorator jasperDesignDecorator) {
        this.report.setJasperDesignDecorator(jasperDesignDecorator);
        return this;
    }

    public DynamicReportBuilder setDefaultEncoding(String encoding) {
        this.report.setDefaultEncoding(encoding);
        return this;
    }
}
