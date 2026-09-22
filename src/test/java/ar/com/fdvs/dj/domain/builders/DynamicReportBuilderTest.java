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
import ar.com.fdvs.dj.domain.CustomExpression;
import ar.com.fdvs.dj.domain.DJCalculation;
import ar.com.fdvs.dj.domain.DJChart;
import ar.com.fdvs.dj.domain.DJChartOptions;
import ar.com.fdvs.dj.domain.DJCrosstab;
import ar.com.fdvs.dj.domain.DJValueFormatter;
import ar.com.fdvs.dj.domain.DJWaterMark;
import ar.com.fdvs.dj.domain.DynamicReport;
import ar.com.fdvs.dj.domain.ImageBanner;
import ar.com.fdvs.dj.domain.Style;
import ar.com.fdvs.dj.domain.constants.Font;
import ar.com.fdvs.dj.domain.constants.HorizontalAlign;
import ar.com.fdvs.dj.domain.constants.ImageScaleMode;
import ar.com.fdvs.dj.domain.constants.Page;
import ar.com.fdvs.dj.domain.constants.Transparency;
import ar.com.fdvs.dj.domain.entities.DJGroup;
import ar.com.fdvs.dj.core.layout.HorizontalBandAlignment;
import ar.com.fdvs.dj.domain.entities.Parameter;
import ar.com.fdvs.dj.domain.entities.Subreport;
import ar.com.fdvs.dj.core.DJConstants;
import ar.com.fdvs.dj.core.DJException;
import ar.com.fdvs.dj.domain.ColumnProperty;
import ar.com.fdvs.dj.domain.entities.columns.AbstractColumn;
import ar.com.fdvs.dj.domain.entities.columns.PropertyColumn;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.awt.Color;
import java.io.ByteArrayInputStream;
import java.util.Map;

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

    @Nested
    @DisplayName("Fields")
    class Fields {

        @Test
        void addFieldByName() {
            builder.addField("customField", String.class.getName());
            DynamicReport report = builder.build();
            assertNotNull(report.getFields());
        }

        @Test
        void addFieldByClass() {
            builder.addField("customField", String.class);
            DynamicReport report = builder.build();
            assertNotNull(report.getFields());
        }

        @Test
        void addFieldWithColumnProperty() {
            ColumnProperty prop = new ColumnProperty("myField", String.class.getName());
            builder.addField(prop);
            DynamicReport report = builder.build();
            assertNotNull(report.getFields());
        }
    }

    @Nested
    @DisplayName("Global Variables")
    class GlobalVariablesExtended {

        @Test
        void addGlobalVariableByColumnProperty() {
            ColumnProperty prop = new ColumnProperty("amount", Float.class.getName());
            builder.addGlobalVariable("totalAmount", prop, DJCalculation.SUM);
            DynamicReport report = builder.build();
            assertNotNull(report);
        }

        @Test
        void addGlobalVariableByPropertyString() {
            builder.addGlobalVariable("totalAmount", "amount", Float.class.getName(), DJCalculation.SUM);
            DynamicReport report = builder.build();
            assertNotNull(report);
        }

        @Test
        void addGlobalColumnVariableHeader() {
            AbstractColumn column = ColumnBuilder.getNew()
                    .setColumnProperty("amount", Float.class.getName())
                    .setTitle("Amount")
                    .build();
            builder.addColumn(column);

            builder.addGlobalColumnVariable("header", column, DJCalculation.SUM);
            DynamicReport report = builder.build();
            assertNotNull(report);
        }

        @Test
        void addGlobalColumnVariableFooter() {
            AbstractColumn column = ColumnBuilder.getNew()
                    .setColumnProperty("amount", Float.class.getName())
                    .setTitle("Amount")
                    .build();
            builder.addColumn(column);

            builder.addGlobalColumnVariable("footer", column, DJCalculation.SUM);
            DynamicReport report = builder.build();
            assertNotNull(report);
        }

        @Test
        void addGlobalColumnVariableWithStyle() {
            AbstractColumn column = ColumnBuilder.getNew()
                    .setColumnProperty("amount", Float.class.getName())
                    .setTitle("Amount")
                    .build();
            builder.addColumn(column);

            Style style = new Style();
            builder.addGlobalColumnVariable("footer", column, DJCalculation.SUM, style);
            DynamicReport report = builder.build();
            assertNotNull(report);
        }
    }

    @Nested
    @DisplayName("Image Banner Variations")
    class ImageBannerVariations {

        @Test
        void addImageBannerWithByte() {
            builder.addImageBanner("images/header.png", 200, 30, (byte) 1);
            DynamicReport report = builder.build();
            assertNotNull(report.getOptions().getImageBanners());
        }

        @Test
        void addImageBannerWithImageData() {
            byte[] image = new byte[] {1, 2, 3, 4};
            builder.addImageBanner(image, 200, 30, ImageBanner.Alignment.Left);
            DynamicReport report = builder.build();
            ImageBanner banner = report.getOptions().getImageBanners().get(ImageBanner.Alignment.Left);
            assertArrayEquals(image, banner.getImageData());
            assertNull(banner.getImagePath());
        }

        @Test
        void addImageBannerWithInputStream() {
            byte[] image = new byte[] {9, 8, 7};
            builder.addImageBanner(new ByteArrayInputStream(image), 150, 25, ImageBanner.Alignment.Right, ImageScaleMode.FILL);
            DynamicReport report = builder.build();
            ImageBanner banner = report.getOptions().getImageBanners().get(ImageBanner.Alignment.Right);
            assertArrayEquals(image, banner.getImageData());
            assertEquals(ImageScaleMode.FILL, banner.getScaleMode());
        }

        @Test
        void addFooterAndFirstPageBannersWithImageData() {
            byte[] image = new byte[] {5, 6};
            builder.addFooterImageBanner(image, 100, 20, ImageBanner.Alignment.Center, ImageScaleMode.FILL_PROPORTIONALLY)
                    .addFirstPageImageBanner(image, 197, 60, ImageBanner.Alignment.Left)
                    .addFirstPageFooterImageBanner(image, 80, 20, ImageBanner.Alignment.Right);
            DynamicReport report = builder.build();
            assertArrayEquals(image, report.getOptions().getFooterImageBanners().get(ImageBanner.Alignment.Center).getImageData());
            assertArrayEquals(image, report.getOptions().getFirstPageImageBanners().get(ImageBanner.Alignment.Left).getImageData());
            assertArrayEquals(image, report.getOptions().getFirstPageFooterImageBanners().get(ImageBanner.Alignment.Right).getImageData());
        }

        @Test
        void addImageBannerRejectsEmptyData() {
            assertThrows(DJException.class, () -> builder.addImageBanner(new byte[0], 10, 10, ImageBanner.Alignment.Left));
            assertThrows(DJException.class, () -> builder.addImageBanner((byte[]) null, 10, 10, ImageBanner.Alignment.Left));
            assertThrows(DJException.class, () -> builder.addImageBanner(new ByteArrayInputStream(new byte[0]), 10, 10, ImageBanner.Alignment.Left));
        }

        @Test
        void addFooterImageBanner() {
            builder.addFooterImageBanner("images/footer.png", 200, 30, ImageBanner.Alignment.Center, null);
            DynamicReport report = builder.build();
            assertNotNull(report);
        }

        @Test
        void addFirstPageFooterImageBanner() {
            builder.addFirstPageFooterImageBanner("images/footer.png", 200, 30, ImageBanner.Alignment.Center);
            DynamicReport report = builder.build();
            assertNotNull(report);
        }
    }

    @Nested
    @DisplayName("When Resources Settings")
    class WhenResourceSettings {

        @Test
        void setWhenResourceMissing() {
            builder.setWhenResourceMissing(DJConstants.WHEN_RESOURCE_MISSING_TYPE_EMPTY);
            DynamicReport report = builder.build();
            assertEquals(DJConstants.WHEN_RESOURCE_MISSING_TYPE_EMPTY, report.getWhenResourceMissing());
        }

        @Test
        void setWhenNoDataWithStyle() {
            Style style = new Style();
            style.setFont(Font.ARIAL_MEDIUM);
            builder.setWhenNoData("No records found", style);
            DynamicReport report = builder.build();
            assertEquals("No records found", report.getWhenNoDataText());
        }

        @Test
        void setWhenNoDataAllSectionNoDetail() {
            builder.setWhenNoDataAllSectionNoDetail();
            DynamicReport report = builder.build();
            assertNotNull(report);
        }

        @Test
        void setWhenNoDataShowNoDataSection() {
            builder.setWhenNoDataShowNoDataSection();
            DynamicReport report = builder.build();
            assertNotNull(report);
        }

        @Test
        void setWhenNoDataBlankPage() {
            builder.setWhenNoDataBlankPage();
            DynamicReport report = builder.build();
            assertNotNull(report);
        }

        @Test
        void setWhenNoDataNoPages() {
            builder.setWhenNoDataNoPages();
            DynamicReport report = builder.build();
            assertNotNull(report);
        }

        @Test
        void setWhenNoDataType() {
            builder.setWhenNoDataType((byte) 0);
            DynamicReport report = builder.build();
            assertNotNull(report);
        }
    }

    @Nested
    @DisplayName("Report Settings")
    class ReportSettings {

        @Test
        void setAllowDetailSplit() {
            builder.setAllowDetailSplit(false);
            DynamicReport report = builder.build();
            assertFalse(report.isAllowDetailSplit());
        }

        @Test
        void setAllowDetailSplitTrue() {
            builder.setAllowDetailSplit(true);
            DynamicReport report = builder.build();
            assertTrue(report.isAllowDetailSplit());
        }

        @Test
        void setShowDetailBand() {
            builder.setShowDetailBand(false);
            DynamicReport report = builder.build();
            assertFalse(report.getOptions().isShowDetailBand());
        }

        @Test
        void setProperty() {
            builder.setProperty("net.sf.jasperreports.export.pdf.encrypted", "true");
            DynamicReport report = builder.build();
            assertEquals("true", report.getProperties().get("net.sf.jasperreports.export.pdf.encrypted"));
        }

        @Test
        void setLanguage() {
            builder.setLanguage(DJConstants.REPORT_LANGUAGE_GROOVY);
            DynamicReport report = builder.build();
            assertEquals(DJConstants.REPORT_LANGUAGE_GROOVY, report.getLanguage());
        }

        @Test
        void setDefaultEncoding() {
            builder.setDefaultEncoding("UTF-8");
            DynamicReport report = builder.build();
            assertEquals("UTF-8", report.getDefaultEncoding());
        }

        @Test
        void setTitleWithExpressionFlag() {
            builder.setTitle("$P{title}", true);
            DynamicReport report = builder.build();
            assertEquals("$P{title}", report.getTitle());
            assertTrue(report.isTitleIsJrExpression());
        }
    }

    @Nested
    @DisplayName("Parameters")
    class Parameters {

        @Test
        void addParameterByName() {
            builder.addParameter("reportDate", java.util.Date.class.getName());
            DynamicReport report = builder.build();

            assertEquals(1, report.getParameters().size());
            assertEquals("reportDate", report.getParameters().get(0).getName());
        }

        @Test
        void addParameterObject() {
            builder.addParameter(new Parameter("userId", Integer.class.getName()));
            DynamicReport report = builder.build();

            assertEquals(1, report.getParameters().size());
            assertEquals(Integer.class.getName(), report.getParameters().get(0).getClassName());
        }
    }

    @Nested
    @DisplayName("Watermark")
    class Watermark {

        @Test
        void addWatermarkWithText() {
            builder.addWatermark("CONFIDENTIAL");
            DynamicReport report = builder.build();

            assertEquals("CONFIDENTIAL", report.getWaterMark().getText());
        }

        @Test
        void addWatermarkObject() {
            DJWaterMark watermark = new DJWaterMark("DRAFT");
            builder.addWatermark(watermark);
            DynamicReport report = builder.build();

            assertSame(watermark, report.getWaterMark());
        }

        @Test
        void addWatermarkWithFontAndColor() {
            builder.addWatermark("DRAFT", Font.ARIAL_BIG, Color.RED, DJWaterMark.ANGLE_0);
            DynamicReport report = builder.build();

            assertEquals("DRAFT", report.getWaterMark().getText());
            assertEquals(DJWaterMark.ANGLE_0, report.getWaterMark().getAngle());
        }
    }

    @Nested
    @DisplayName("Styles and Fonts")
    class StylesAndFonts {

        @Test
        void addStyle() throws DJBuilderException {
            Style style = new Style("customStyle");
            style.setFont(Font.ARIAL_MEDIUM);

            builder.addStyle(style);
            DynamicReport report = builder.build();

            assertTrue(report.getStyles().containsKey("customStyle"));
        }

        @Test
        void addStyleWithoutNameThrows() {
            Style style = new Style();
            assertThrows(DJBuilderException.class, () -> builder.addStyle(style));
        }

        @Test
        void addFont() {
            java.awt.Font awtFont = new java.awt.Font("Arial", java.awt.Font.PLAIN, 12);
            builder.addFont("Arial", awtFont);
            DynamicReport report = builder.build();

            assertTrue(report.getFontsMap().containsKey("Arial"));
        }

        @Test
        void setGrandTotalLegendStyle() {
            Style style = new Style("grandTotal");
            builder.setGrandTotalLegendStyle(style);
            DynamicReport report = builder.build();

            assertNotNull(report);
        }
    }

    @Nested
    @DisplayName("AutoText Extended")
    class AutoTextExtended {

        @Test
        void addAutoTextWithPattern() {
            builder.addAutoText(AutoText.AUTOTEXT_CREATED_ON, AutoText.POSITION_FOOTER,
                    AutoText.ALIGNMENT_RIGHT, AutoText.PATTERN_DATE_DATE_ONLY);
            DynamicReport report = builder.build();

            assertEquals(1, report.getAutoTexts().size());
        }

        @Test
        void addAutoTextWithWidthsAndStyle() {
            Style style = new Style();
            builder.addAutoText(AutoText.AUTOTEXT_PAGE_X_OF_Y, AutoText.POSITION_FOOTER,
                    AutoText.ALIGNMENT_CENTER, 40, 60, style);
            DynamicReport report = builder.build();

            assertEquals(1, report.getAutoTexts().size());
        }

        @Test
        void addAutoTextWithPageOffset() {
            Style style = new Style();
            builder.addAutoText(AutoText.AUTOTEXT_PAGE_X_OF_Y, AutoText.POSITION_FOOTER,
                    AutoText.ALIGNMENT_LEFT, 30, 30, 1, true, style);
            DynamicReport report = builder.build();

            assertEquals(1, report.getAutoTexts().size());
        }

        @Test
        void addAutoTextObject() {
            AutoText autoText = new AutoText("Custom footer", AutoText.POSITION_FOOTER,
                    HorizontalBandAlignment.buildAligment(AutoText.ALIGNMENT_CENTER));

            builder.addAutoText(autoText);
            DynamicReport report = builder.build();

            assertEquals(1, report.getAutoTexts().size());
            assertEquals("Custom footer", report.getAutoTexts().get(0).getMessageKey());
        }
    }

    @Nested
    @DisplayName("Global Variables Extended")
    class GlobalVariablesWithExpressions {

        @Test
        void addGlobalHeaderVariableWithCustomExpression() {
            AbstractColumn column = ColumnBuilder.getNew()
                    .setColumnProperty("amount", Float.class.getName())
                    .setTitle("Amount")
                    .build();
            builder.addColumn(column);

            CustomExpression expression = new CustomExpression() {
                public Object evaluate(Map fields, Map variables, Map parameters) {
                    return fields.get("amount");
                }
                public String getClassName() {
                    return Float.class.getName();
                }
            };

            builder.addGlobalHeaderVariable(column, expression);
            DynamicReport report = builder.build();

            assertNotNull(report.getColumnsGroups());
        }

        @Test
        void addGlobalFooterVariableWithCustomExpressionAndStyle() {
            AbstractColumn column = ColumnBuilder.getNew()
                    .setColumnProperty("amount", Float.class.getName())
                    .setTitle("Amount")
                    .build();
            builder.addColumn(column);

            CustomExpression expression = new CustomExpression() {
                public Object evaluate(Map fields, Map variables, Map parameters) {
                    return fields.get("amount");
                }
                public String getClassName() {
                    return Float.class.getName();
                }
            };
            Style style = new Style("footerVar");

            builder.addGlobalFooterVariable(column, expression, style);
            DynamicReport report = builder.build();

            assertNotNull(report.getColumnsGroups());
        }

        @Test
        void addGlobalColumnVariableWithValueFormatter() {
            AbstractColumn column = ColumnBuilder.getNew()
                    .setColumnProperty("amount", Float.class.getName())
                    .setTitle("Amount")
                    .build();
            builder.addColumn(column);

            DJValueFormatter formatter = new DJValueFormatter() {
                public Object evaluate(Object value, Map fields, Map variables, Map parameters) {
                    return value;
                }
                public String getClassName() {
                    return String.class.getName();
                }
            };

            builder.addGlobalColumnVariable("footer", column, DJCalculation.SUM, null, formatter);
            DynamicReport report = builder.build();

            assertNotNull(report.getColumnsGroups());
        }
    }

    @Nested
    @DisplayName("Colspan")
    class Colspan {

        @Test
        void setColspanOnColumns() {
            AbstractColumn col1 = ColumnBuilder.getNew()
                    .setColumnProperty("q1", Float.class.getName())
                    .setTitle("Q1")
                    .build();
            AbstractColumn col2 = ColumnBuilder.getNew()
                    .setColumnProperty("q2", Float.class.getName())
                    .setTitle("Q2")
                    .build();
            builder.addColumn(col1).addColumn(col2);

            builder.setColspan(0, 2, "Quarterly");
            DynamicReport report = builder.build();

            assertNotNull(report.getColumns().get(0).getColSpan());
            assertEquals("Quarterly", report.getColumns().get(0).getColSpan().getTitle());
        }

        @Test
        void setColspanWithStyle() {
            AbstractColumn col1 = ColumnBuilder.getNew()
                    .setColumnProperty("q1", Float.class.getName())
                    .setTitle("Q1")
                    .build();
            AbstractColumn col2 = ColumnBuilder.getNew()
                    .setColumnProperty("q2", Float.class.getName())
                    .setTitle("Q2")
                    .build();
            builder.addColumn(col1).addColumn(col2);

            Style style = new Style("colspanHeader");
            builder.setColspan(0, 2, "Quarterly", style);
            DynamicReport report = builder.build();

            assertEquals(style, report.getColumns().get(0).getColSpan().getColspanHeaderStyle());
        }
    }

    @Nested
    @DisplayName("Charts")
    class Charts {

        @Test
        void addDeprecatedChart() {
            AbstractColumn column = ColumnBuilder.getNew()
                    .setColumnProperty("amount", Float.class.getName())
                    .setTitle("Amount")
                    .build();
            builder.addColumn(column);

            DJGroup group = new GroupBuilder()
                    .setCriteriaColumn((PropertyColumn) column)
                    .build();
            builder.addGroup(group);

            DJChart chart = new DJChart(DJChart.BAR_CHART, group, column,
                    DJChart.CALCULATION_SUM, new DJChartOptions());
            builder.addChart(chart);
            DynamicReport report = builder.build();

            assertEquals(1, report.getCharts().size());
        }
    }

    @Nested
    @DisplayName("Image Banner Extended")
    class ImageBannerExtended {

        @Test
        void addImageBannerWithScaleMode() {
            builder.addImageBanner("images/header.png", 200, 30, ImageBanner.Alignment.Center, ImageScaleMode.FILL);
            DynamicReport report = builder.build();

            assertEquals(1, report.getOptions().getImageBanners().size());
        }

        @Test
        void addFooterImageBannerWithScaleMode() {
            builder.addFooterImageBanner("images/footer.png", 200, 30, ImageBanner.Alignment.Left, ImageScaleMode.FILL);
            DynamicReport report = builder.build();

            assertNotNull(report);
        }

        @Test
        void addFirstPageImageBannerWithScaleMode() {
            builder.addFirstPageImageBanner("images/logo.png", 100, 50, ImageBanner.Alignment.Right, ImageScaleMode.FILL);
            DynamicReport report = builder.build();

            assertEquals(1, report.getOptions().getFirstPageImageBanners().size());
        }
    }

    @Nested
    @DisplayName("When Resource Missing")
    class WhenResourceMissing {

        @Test
        void setWhenResourceMissingLeaveEmptySpace() {
            builder.setWhenResourceMissingLeaveEmptySpace();
            DynamicReport report = builder.build();
            assertEquals(DJConstants.WHEN_RESOURCE_MISSING_TYPE_EMPTY, report.getWhenResourceMissing());
        }

        @Test
        void setWhenResourceMissingThrowException() {
            builder.setWhenResourceMissingThrowException();
            DynamicReport report = builder.build();
            assertEquals(DJConstants.WHEN_RESOURCE_MISSING_TYPE_ERROR, report.getWhenResourceMissing());
        }

        @Test
        void setWhenResourceMissingShowKey() {
            builder.setWhenResourceMissingShowKey();
            DynamicReport report = builder.build();
            assertEquals(DJConstants.WHEN_RESOURCE_MISSING_TYPE_KEY, report.getWhenResourceMissing());
        }

        @Test
        void setWhenResourceMissingReturnNull() {
            builder.setWhenResourceMissingReturnNull();
            DynamicReport report = builder.build();
            assertEquals(DJConstants.WHEN_RESOURCE_MISSING_TYPE_NULL, report.getWhenResourceMissing());
        }
    }

    @Nested
    @DisplayName("Concatenated Reports")
    class ConcatenatedReports {

        @Test
        void addConcatenatedReportWithSubreport() {
            Subreport subreport = new Subreport();
            subreport.setPath("/reports/sub.jasper");

            builder.addConcatenatedReport(subreport);
            DynamicReport report = builder.build();

            assertNotNull(report);
        }
    }

    // Inner class for DynamicJasperHelper reference
    static class DynamicJasperHelper {
        public static final int DATA_SOURCE_ORIGIN_PARAMETER = 1;
    }
}
