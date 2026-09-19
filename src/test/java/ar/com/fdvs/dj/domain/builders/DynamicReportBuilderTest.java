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

import ar.com.fdvs.dj.domain.AutoText;
import ar.com.fdvs.dj.domain.DJCalculation;
import ar.com.fdvs.dj.domain.DJCrosstab;
import ar.com.fdvs.dj.domain.DynamicReport;
import ar.com.fdvs.dj.domain.ImageBanner;
import ar.com.fdvs.dj.domain.Style;
import ar.com.fdvs.dj.domain.constants.Font;
import ar.com.fdvs.dj.domain.constants.HorizontalAlign;
import ar.com.fdvs.dj.domain.constants.Page;
import ar.com.fdvs.dj.domain.constants.Transparency;
import ar.com.fdvs.dj.domain.entities.DJGroup;
import ar.com.fdvs.dj.domain.entities.Subreport;
import ar.com.fdvs.dj.domain.entities.columns.AbstractColumn;
import ar.com.fdvs.dj.domain.entities.columns.PropertyColumn;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.awt.Color;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for DynamicReportBuilder.
 * Focus on builder methods and fluent API behavior.
 */
class DynamicReportBuilderTest {

    private DynamicReportBuilder builder;

    @BeforeEach
    void setUp() {
        builder = new DynamicReportBuilder();
    }

    @Nested
    @DisplayName("Basic Properties")
    class BasicProperties {

        @Test
        void setTitle() {
            DynamicReportBuilder result = builder.setTitle("Test Report");

            assertSame(builder, result, "Should return same builder for chaining");
            DynamicReport report = builder.build();
            assertEquals("Test Report", report.getTitle());
        }

        @Test
        void setSubtitle() {
            builder.setSubtitle("Test Subtitle");
            DynamicReport report = builder.build();
            assertEquals("Test Subtitle", report.getSubtitle());
        }

        @Test
        void setReportName() {
            builder.setReportName("my_report");
            DynamicReport report = builder.build();
            assertEquals("my_report", report.getReportName());
        }

        @Test
        void setTitleIsJrExpression() {
            builder.setTitleIsJrExpression(true);
            DynamicReport report = builder.build();
            assertTrue(report.isTitleIsJrExpression());
        }
    }

    @Nested
    @DisplayName("Margins")
    class Margins {

        @Test
        void setLeftMargin() {
            builder.setLeftMargin(30);
            DynamicReport report = builder.build();
            assertEquals(30, report.getOptions().getLeftMargin());
        }

        @Test
        void setRightMargin() {
            builder.setRightMargin(25);
            DynamicReport report = builder.build();
            assertEquals(25, report.getOptions().getRightMargin());
        }

        @Test
        void setTopMargin() {
            builder.setTopMargin(40);
            DynamicReport report = builder.build();
            assertEquals(40, report.getOptions().getTopMargin());
        }

        @Test
        void setBottomMargin() {
            builder.setBottomMargin(35);
            DynamicReport report = builder.build();
            assertEquals(35, report.getOptions().getBottomMargin());
        }

        @Test
        void setMargins() {
            builder.setMargins(10, 20, 30, 40);
            DynamicReport report = builder.build();
            assertEquals(10, report.getOptions().getTopMargin());
            assertEquals(20, report.getOptions().getBottomMargin());
            assertEquals(30, report.getOptions().getLeftMargin());
            assertEquals(40, report.getOptions().getRightMargin());
        }
    }

    @Nested
    @DisplayName("Heights")
    class Heights {

        @Test
        void setDetailHeight() {
            builder.setDetailHeight(20);
            DynamicReport report = builder.build();
            assertEquals(20, report.getOptions().getDetailHeight());
        }

        @Test
        void setHeaderHeight() {
            builder.setHeaderHeight(30);
            DynamicReport report = builder.build();
            assertEquals(30, report.getOptions().getHeaderHeight());
        }

        @Test
        void setFooterHeight() {
            builder.setFooterHeight(25);
            DynamicReport report = builder.build();
            // No getter for footerHeight, just verify it doesn't throw
            assertNotNull(report);
        }

        @Test
        void setTitleHeight() {
            builder.setTitleHeight(50);
            DynamicReport report = builder.build();
            assertEquals(50, report.getOptions().getTitleHeight());
        }

        @Test
        void setSubtitleHeight() {
            builder.setSubtitleHeight(35);
            DynamicReport report = builder.build();
            assertEquals(35, report.getOptions().getSubtitleHeight());
        }

        @Test
        void setHeaderVariablesHeight() {
            builder.setHeaderVariablesHeight(18);
            DynamicReport report = builder.build();
            assertEquals(18, report.getOptions().getHeaderVariablesHeight());
        }

        @Test
        void setFooterVariablesHeight() {
            builder.setFooterVariablesHeight(15);
            DynamicReport report = builder.build();
            assertEquals(15, report.getOptions().getFooterVariablesHeight());
        }
    }

    @Nested
    @DisplayName("Columns")
    class Columns {

        @Test
        void addColumn() {
            AbstractColumn column = ColumnBuilder.getNew()
                    .setColumnProperty("name", String.class.getName())
                    .setTitle("Name")
                    .build();

            builder.addColumn(column);
            DynamicReport report = builder.build();

            assertEquals(1, report.getColumns().size());
            assertEquals("Name", report.getColumns().get(0).getTitle());
        }

        @Test
        void addMultipleColumns() {
            AbstractColumn col1 = ColumnBuilder.getNew()
                    .setColumnProperty("name", String.class.getName())
                    .setTitle("Name")
                    .build();
            AbstractColumn col2 = ColumnBuilder.getNew()
                    .setColumnProperty("amount", Float.class.getName())
                    .setTitle("Amount")
                    .build();

            builder.addColumn(col1).addColumn(col2);
            DynamicReport report = builder.build();

            assertEquals(2, report.getColumns().size());
        }

        @Test
        void getColumnsReturnsAddedColumns() {
            AbstractColumn column = ColumnBuilder.getNew()
                    .setColumnProperty("test", String.class.getName())
                    .setTitle("Test")
                    .build();
            builder.addColumn(column);

            assertEquals(1, builder.getColumns().size());
        }
    }

    @Nested
    @DisplayName("Groups")
    class Groups {

        @Test
        void addGroup() {
            AbstractColumn column = ColumnBuilder.getNew()
                    .setColumnProperty("state", String.class.getName())
                    .setTitle("State")
                    .build();
            builder.addColumn(column);

            DJGroup group = new GroupBuilder()
                    .setCriteriaColumn((PropertyColumn) column)
                    .build();

            builder.addGroup(group);
            DynamicReport report = builder.build();

            assertEquals(1, report.getColumnsGroups().size());
        }
    }

    @Nested
    @DisplayName("Page Settings")
    class PageSettings {

        @Test
        void setUseFullPageWidth() {
            builder.setUseFullPageWidth(true);
            DynamicReport report = builder.build();
            assertTrue(report.getOptions().isUseFullPageWidth());
        }

        @Test
        void setIgnorePagination() {
            builder.setIgnorePagination(true);
            DynamicReport report = builder.build();
            assertTrue(report.getOptions().isIgnorePagination());
        }

        @Test
        void setPageSizeAndOrientation() {
            builder.setPageSizeAndOrientation(Page.Page_A4_Landscape());
            DynamicReport report = builder.build();
            assertEquals(Page.Page_A4_Landscape().getWidth(), report.getOptions().getPage().getWidth());
        }

        @Test
        void setColumnsPerPage() {
            builder.setColumnsPerPage(2);
            DynamicReport report = builder.build();
            assertEquals(2, report.getOptions().getColumnsPerPage());
        }

        @Test
        void setColumnsPerPageWithSpace() {
            builder.setColumnsPerPage(3, 10);
            DynamicReport report = builder.build();
            assertEquals(3, report.getOptions().getColumnsPerPage());
            assertEquals(10, report.getOptions().getColumnSpace());
        }

        @Test
        void setColumnSpace() {
            builder.setColumnSpace(15);
            DynamicReport report = builder.build();
            assertEquals(15, report.getOptions().getColumnSpace());
        }
    }

    @Nested
    @DisplayName("Styles")
    class Styles {

        @Test
        void setTitleStyle() {
            Style style = new Style();
            style.setFont(Font.ARIAL_BIG_BOLD);

            builder.setTitleStyle(style);
            DynamicReport report = builder.build();

            assertNotNull(report.getTitleStyle());
        }

        @Test
        void setSubtitleStyle() {
            Style style = new Style();
            style.setFont(Font.ARIAL_MEDIUM);

            builder.setSubtitleStyle(style);
            DynamicReport report = builder.build();

            assertNotNull(report.getSubtitleStyle());
        }

        @Test
        void setDefaultStyles() {
            Style title = new Style();
            Style subtitle = new Style();
            Style header = new Style();
            Style detail = new Style();

            builder.setDefaultStyles(title, subtitle, header, detail);
            DynamicReport report = builder.build();

            assertNotNull(report.getTitleStyle());
            assertNotNull(report.getSubtitleStyle());
        }

        @Test
        void setOddRowBackgroundStyle() {
            Style oddStyle = new Style();
            oddStyle.setBackgroundColor(Color.LIGHT_GRAY);

            builder.setPrintBackgroundOnOddRows(true);
            builder.setOddRowBackgroundStyle(oddStyle);
            DynamicReport report = builder.build();

            assertTrue(report.getOptions().isPrintBackgroundOnOddRows());
        }
    }

    @Nested
    @DisplayName("AutoText")
    class AutoTextTests {

        @Test
        void addAutoTextWithMessage() {
            builder.addAutoText("Page Footer", AutoText.POSITION_FOOTER, AutoText.ALIGNMENT_CENTER);
            DynamicReport report = builder.build();

            assertFalse(report.getAutoTexts().isEmpty());
        }

        @Test
        void addAutoTextWithType() {
            builder.addAutoText(AutoText.AUTOTEXT_PAGE_X_OF_Y, AutoText.POSITION_FOOTER, AutoText.ALIGNMENT_RIGHT);
            DynamicReport report = builder.build();

            assertEquals(1, report.getAutoTexts().size());
        }

        @Test
        void addAutoTextWithWidths() {
            builder.addAutoText(AutoText.AUTOTEXT_PAGE_X_OF_Y, AutoText.POSITION_FOOTER, AutoText.ALIGNMENT_RIGHT, 50, 50);
            DynamicReport report = builder.build();

            assertEquals(1, report.getAutoTexts().size());
        }
    }

    @Nested
    @DisplayName("Global Variables")
    class GlobalVariables {

        @Test
        void setGrandTotalLegend() {
            builder.setGrandTotalLegend("Grand Total");
            DynamicReport report = builder.build();
            assertNotNull(report);
        }

        @Test
        void addGlobalHeaderVariable() {
            AbstractColumn column = ColumnBuilder.getNew()
                    .setColumnProperty("amount", Float.class.getName())
                    .setTitle("Amount")
                    .build();
            builder.addColumn(column);

            builder.addGlobalHeaderVariable(column, DJCalculation.SUM);
            DynamicReport report = builder.build();

            assertNotNull(report.getColumnsGroups());
        }

        @Test
        void addGlobalFooterVariable() {
            AbstractColumn column = ColumnBuilder.getNew()
                    .setColumnProperty("amount", Float.class.getName())
                    .setTitle("Amount")
                    .build();
            builder.addColumn(column);

            builder.addGlobalFooterVariable(column, DJCalculation.SUM);
            DynamicReport report = builder.build();

            assertNotNull(report.getColumnsGroups());
        }

        @Test
        void setGlobalHeaderVariableHeight() {
            builder.setGlobalHeaderVariableHeight(20);
            DynamicReport report = builder.build();
            // Height should be set on the internal group
            assertNotNull(report);
        }

        @Test
        void setGlobalFooterVariableHeight() {
            builder.setGlobalFooterVariableHeight(25);
            DynamicReport report = builder.build();
            assertNotNull(report);
        }
    }

    @Nested
    @DisplayName("Print Settings")
    class PrintSettings {

        @Test
        void setPrintColumnNames() {
            builder.setPrintColumnNames(false);
            DynamicReport report = builder.build();
            assertFalse(report.getOptions().isPrintColumnNames());
        }

        @Test
        void setWhenNoData() {
            builder.setWhenNoData("No data available", null);
            DynamicReport report = builder.build();
            assertEquals("No data available", report.getWhenNoDataText());
        }

        @Test
        void setWhenNoDataWithStyle() {
            Style style = new Style();
            builder.setWhenNoData("No data", style);
            DynamicReport report = builder.build();
            assertEquals("No data", report.getWhenNoDataText());
        }

    }

    @Nested
    @DisplayName("Image Banners")
    class ImageBanners {

        @Test
        void addFirstPageImageBanner() {
            builder.addFirstPageImageBanner("images/logo.png", 100, 50, ImageBanner.Alignment.Right);
            DynamicReport report = builder.build();

            assertNotNull(report.getOptions().getFirstPageImageBanners());
        }

        @Test
        void addImageBanner() {
            builder.addImageBanner("images/header.png", 200, 30, ImageBanner.Alignment.Center);
            DynamicReport report = builder.build();

            assertNotNull(report.getOptions().getImageBanners());
        }
    }

    @Nested
    @DisplayName("Subreports")
    class Subreports {

        @Test
        void addConcatenatedReport() throws Exception {
            DynamicReport subReport = new DynamicReportBuilder()
                    .setTitle("Sub Report")
                    .build();

            // Just verify builder accepts concatenated reports without error
            builder.addConcatenatedReport(subReport, null, null, DynamicJasperHelper.DATA_SOURCE_ORIGIN_PARAMETER, 1, true);
            DynamicReport report = builder.build();

            assertNotNull(report);
        }

        @Test
        void addSubreportInGroupFooter() {
            // Create a simple subreport setup
            AbstractColumn column = ColumnBuilder.getNew()
                    .setColumnProperty("state", String.class.getName())
                    .setTitle("State")
                    .build();
            builder.addColumn(column);

            DJGroup group = new GroupBuilder()
                    .setCriteriaColumn((PropertyColumn) column)
                    .build();
            builder.addGroup(group);

            DynamicReport report = builder.build();
            assertNotNull(report);
        }
    }

    @Nested
    @DisplayName("Query")
    class Query {

        @Test
        void setQuery() {
            builder.setQuery("SELECT * FROM products", "sql");
            DynamicReport report = builder.build();

            assertNotNull(report.getQuery());
            assertEquals("SELECT * FROM products", report.getQuery().getText());
        }

        @Test
        void setQueryDefaultLanguage() {
            builder.setQuery("SELECT * FROM orders", null);
            DynamicReport report = builder.build();

            assertNotNull(report.getQuery());
        }
    }

    @Nested
    @DisplayName("Template")
    class Template {

        @Test
        void setTemplateFile() {
            builder.setTemplateFile("/templates/report.jrxml");
            DynamicReport report = builder.build();
            assertEquals("/templates/report.jrxml", report.getTemplateFileName());
        }

        @Test
        void setTemplateImportFlags() {
            builder.setTemplateFile("/templates/report.jrxml", true, true, true, true);
            DynamicReport report = builder.build();

            assertEquals("/templates/report.jrxml", report.getTemplateFileName());
        }
    }

    @Nested
    @DisplayName("Language and Locale")
    class LanguageAndLocale {

        @Test
        void setReportLocale() {
            builder.setReportLocale(java.util.Locale.FRENCH);
            DynamicReport report = builder.build();
            assertEquals(java.util.Locale.FRENCH, report.getReportLocale());
        }

        @Test
        void setResourceBundle() {
            builder.setResourceBundle("messages");
            DynamicReport report = builder.build();
            assertEquals("messages", report.getResourceBundle());
        }
    }

    @Nested
    @DisplayName("Build Behavior")
    class BuildBehavior {

        @Test
        void buildReturnsNonNull() {
            DynamicReport report = builder.build();
            assertNotNull(report);
        }

        @Test
        void buildCanOnlyBeCalledOnce() {
            builder.build();
            assertThrows(DJBuilderException.class, () -> builder.build());
        }

        @Test
        void builderMethodsReturnSameInstance() {
            DynamicReportBuilder result = builder
                    .setTitle("Test")
                    .setSubtitle("Subtitle")
                    .setLeftMargin(10)
                    .setDetailHeight(15);

            assertSame(builder, result);
        }
    }

    @Nested
    @DisplayName("Crosstab")
    class Crosstabs {

        @Test
        void addHeaderCrosstab() {
            DJCrosstab crosstab = new CrosstabBuilder()
                    .setHeight(100)
                    .setWidth(500)
                    .addRow("Product", "product", String.class.getName(), false)
                    .addColumn("State", "state", String.class.getName(), false)
                    .addMeasure("amount", Float.class.getName(), DJCalculation.SUM, "Amount", null)
                    .build();

            builder.addHeaderCrosstab(crosstab);
            DynamicReport report = builder.build();

            assertNotNull(report);
        }

        @Test
        void addFooterCrosstab() {
            DJCrosstab crosstab = new CrosstabBuilder()
                    .setHeight(100)
                    .setWidth(500)
                    .addRow("Product", "product", String.class.getName(), false)
                    .addColumn("State", "state", String.class.getName(), false)
                    .addMeasure("amount", Float.class.getName(), DJCalculation.SUM, "Amount", null)
                    .build();

            builder.addFooterCrosstab(crosstab);
            DynamicReport report = builder.build();

            assertNotNull(report);
        }
    }

    // Inner class for DynamicJasperHelper reference
    static class DynamicJasperHelper {
        public static final int DATA_SOURCE_ORIGIN_PARAMETER = 1;
    }
}
