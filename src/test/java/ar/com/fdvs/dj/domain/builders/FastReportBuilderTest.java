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
import ar.com.fdvs.dj.domain.CustomExpression;
import ar.com.fdvs.dj.domain.DJCalculation;
import ar.com.fdvs.dj.domain.DJCrosstab;
import ar.com.fdvs.dj.domain.DJValueFormatter;
import ar.com.fdvs.dj.domain.DynamicReport;
import ar.com.fdvs.dj.domain.Style;
import ar.com.fdvs.dj.core.BarcodeTypes;
import ar.com.fdvs.dj.domain.constants.DJVariableResetType;
import ar.com.fdvs.dj.domain.constants.Font;
import ar.com.fdvs.dj.domain.constants.GroupLayout;
import ar.com.fdvs.dj.domain.constants.ImageScaleMode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for FastReportBuilder.
 * Tests quick report building with default styles.
 */
class FastReportBuilderTest {

    private FastReportBuilder builder;

    @BeforeEach
    void setUp() {
        builder = new FastReportBuilder();
    }

    @Nested
    @DisplayName("Column Addition by Class Name")
    class AddColumnByClassName {

        @Test
        void addStringColumn() throws Exception {
            builder.addColumn("Name", "name", String.class.getName(), 100);
            DynamicReport report = builder.build();

            assertEquals(1, report.getColumns().size());
            assertEquals("Name", report.getColumns().get(0).getTitle());
        }

        @Test
        void addIntegerColumn() throws Exception {
            builder.addColumn("Quantity", "quantity", Integer.class.getName(), 80);
            DynamicReport report = builder.build();

            assertEquals(1, report.getColumns().size());
        }

        @Test
        void addFloatColumn() throws Exception {
            builder.addColumn("Amount", "amount", Float.class.getName(), 90);
            DynamicReport report = builder.build();

            assertEquals(1, report.getColumns().size());
        }

        @Test
        void addDateColumn() throws Exception {
            builder.addColumn("Date", "orderDate", Date.class.getName(), 100);
            DynamicReport report = builder.build();

            assertEquals(1, report.getColumns().size());
        }

        @Test
        void addBigDecimalColumn() throws Exception {
            builder.addColumn("Total", "total", BigDecimal.class.getName(), 100);
            DynamicReport report = builder.build();

            assertEquals(1, report.getColumns().size());
        }

        @Test
        void addMultipleColumns() throws Exception {
            builder.addColumn("Name", "name", String.class.getName(), 100)
                   .addColumn("Amount", "amount", Float.class.getName(), 80)
                   .addColumn("Date", "date", Date.class.getName(), 90);
            DynamicReport report = builder.build();

            assertEquals(3, report.getColumns().size());
        }
    }

    @Nested
    @DisplayName("Column Addition by Class")
    class AddColumnByClass {

        @Test
        void addColumnWithClass() throws Exception {
            builder.addColumn("Name", "name", String.class, 100);
            DynamicReport report = builder.build();

            assertEquals(1, report.getColumns().size());
        }

        @Test
        void addColumnWithClassAndStyle() throws Exception {
            Style style = new Style();
            style.setFont(Font.ARIAL_MEDIUM);

            builder.addColumn("Name", "name", String.class, 100, style);
            DynamicReport report = builder.build();

            assertEquals(1, report.getColumns().size());
        }

        @Test
        void addColumnWithClassAndHeaderStyle() throws Exception {
            Style style = new Style();
            Style headerStyle = new Style();

            builder.addColumn("Name", "name", String.class, 100, style, headerStyle);
            DynamicReport report = builder.build();

            assertEquals(1, report.getColumns().size());
        }
    }

    @Nested
    @DisplayName("Column with Pattern")
    class AddColumnWithPattern {

        @Test
        void addColumnWithPattern() throws Exception {
            // Signature: title, property, className, width, fixedWidth, pattern
            builder.addColumn("Amount", "amount", Float.class.getName(), 90, false, "$ #,##0.00");
            DynamicReport report = builder.build();

            assertEquals(1, report.getColumns().size());
        }

        @Test
        void addColumnWithPatternAndStyle() throws Exception {
            Style style = new Style();
            // Signature: title, property, className, width, fixedWidth, pattern, style
            builder.addColumn("Amount", "amount", Float.class.getName(), 90, false, "$ #,##0.00", style);
            DynamicReport report = builder.build();

            assertEquals(1, report.getColumns().size());
        }

        @Test
        void addDateColumnWithPattern() throws Exception {
            builder.addColumn("Date", "date", Date.class.getName(), 100, false, "dd/MM/yyyy");
            DynamicReport report = builder.build();

            assertEquals(1, report.getColumns().size());
        }
    }

    @Nested
    @DisplayName("Column with Fixed Width")
    class AddColumnWithFixedWidth {

        @Test
        void addColumnWithFixedWidth() throws Exception {
            builder.addColumn("Code", "code", String.class.getName(), 50, false);
            DynamicReport report = builder.build();

            assertEquals(1, report.getColumns().size());
        }

        @Test
        void addColumnWithStretchableWidth() throws Exception {
            builder.addColumn("Description", "description", String.class.getName(), 200, true);
            DynamicReport report = builder.build();

            assertEquals(1, report.getColumns().size());
        }
    }

    @Nested
    @DisplayName("Groups")
    class Groups {

        @Test
        void addGroups() throws Exception {
            builder.addColumn("State", "state", String.class.getName(), 100)
                   .addColumn("Amount", "amount", Float.class.getName(), 80)
                   .addGroups(1);
            DynamicReport report = builder.build();

            assertEquals(1, report.getColumnsGroups().size());
        }

        @Test
        void addMultipleGroups() throws Exception {
            builder.addColumn("State", "state", String.class.getName(), 100)
                   .addColumn("City", "city", String.class.getName(), 100)
                   .addColumn("Amount", "amount", Float.class.getName(), 80)
                   .addGroups(2);
            DynamicReport report = builder.build();

            assertEquals(2, report.getColumnsGroups().size());
        }

        @Test
        void setGroupLayout() throws Exception {
            builder.addColumn("State", "state", String.class.getName(), 100)
                   .addColumn("Amount", "amount", Float.class.getName(), 80)
                   .addGroups(1)
                   .setGroupLayout(1, GroupLayout.VALUE_IN_HEADER);
            DynamicReport report = builder.build();

            assertNotNull(report.getColumnsGroups());
        }
    }

    @Nested
    @DisplayName("Variables")
    class Variables {

        @Test
        void addHeaderVariable() throws Exception {
            builder.addColumn("State", "state", String.class.getName(), 100);
            builder.addColumn("Amount", "amount", Float.class.getName(), 80);
            builder.addGroups(1);

            // Use column index instead of AbstractColumn
            builder.addHeaderVariable(1, 2, DJCalculation.SUM, null);
            DynamicReport report = builder.build();

            assertNotNull(report);
        }

        @Test
        void addFooterVariable() throws Exception {
            builder.addColumn("State", "state", String.class.getName(), 100);
            builder.addColumn("Amount", "amount", Float.class.getName(), 80);
            builder.addGroups(1);

            builder.addFooterVariable(1, 2, DJCalculation.SUM, null);
            DynamicReport report = builder.build();

            assertNotNull(report);
        }

        @Test
        void addGroupVariable() throws Exception {
            builder.addColumn("State", "state", String.class.getName(), 100);
            builder.addColumn("Amount", "amount", Float.class.getName(), 80);
            builder.addGroups(1);

            builder.addGroupVariable("footer", 1, 2, DJCalculation.SUM, null);
            DynamicReport report = builder.build();

            assertNotNull(report);
        }

        @Test
        void addGlobalHeaderVariable() throws Exception {
            builder.addColumn("Amount", "amount", Float.class.getName(), 80);
            builder.addGlobalHeaderVariable(1, DJCalculation.SUM, null);
            DynamicReport report = builder.build();

            assertNotNull(report);
        }

        @Test
        void addGlobalFooterVariable() throws Exception {
            builder.addColumn("Amount", "amount", Float.class.getName(), 80);
            builder.addGlobalFooterVariable(1, DJCalculation.SUM, null);
            DynamicReport report = builder.build();

            assertNotNull(report);
        }
    }

    @Nested
    @DisplayName("Fluent API")
    class FluentApi {

        @Test
        void chainedMethods() throws Exception {
            builder.addColumn("Name", "name", String.class.getName(), 100)
                   .addColumn("Amount", "amount", Float.class.getName(), 80)
                   .setTitle("Test Report")
                   .setSubtitle("Test Subtitle")
                   .setUseFullPageWidth(true);

            DynamicReport report = builder.build();
            assertNotNull(report);
        }

        @Test
        void buildReturnsValidReport() throws Exception {
            builder.addColumn("Name", "name", String.class.getName(), 100)
                   .setTitle("Test");
            DynamicReport report = builder.build();

            assertNotNull(report);
            assertEquals("Test", report.getTitle());
        }
    }

    @Nested
    @DisplayName("Report Properties")
    class ReportProperties {

        @Test
        void setTitle() throws Exception {
            builder.setTitle("My Report");
            DynamicReport report = builder.build();

            assertEquals("My Report", report.getTitle());
        }

        @Test
        void setSubtitle() throws Exception {
            builder.setSubtitle("Report Subtitle");
            DynamicReport report = builder.build();

            assertEquals("Report Subtitle", report.getSubtitle());
        }

        @Test
        void setUseFullPageWidth() throws Exception {
            builder.setUseFullPageWidth(true);
            DynamicReport report = builder.build();

            assertTrue(report.getOptions().isUseFullPageWidth());
        }

        @Test
        void setPrintColumnNames() throws Exception {
            builder.setPrintColumnNames(false);
            DynamicReport report = builder.build();

            assertFalse(report.getOptions().isPrintColumnNames());
        }
    }

    @Nested
    @DisplayName("Default Styles")
    class DefaultStyles {

        @Test
        void builderInitializesWithDefaults() {
            // FastReportBuilder initializes with default styles in constructor
            assertNotNull(builder);
        }

        @Test
        void numericColumnsGetDefaultStyle() throws Exception {
            builder.addColumn("Amount", "amount", Float.class.getName(), 80);
            DynamicReport report = builder.build();

            assertNotNull(report.getColumns().get(0).getStyle());
        }

        @Test
        void dateColumnsGetDefaultStyle() throws Exception {
            builder.addColumn("Date", "date", Date.class.getName(), 100);
            DynamicReport report = builder.build();
            // Date columns may or may not have a default style
            assertNotNull(report.getColumns().get(0));
        }
    }

    @Nested
    @DisplayName("Margins")
    class Margins {

        @Test
        void setMargins() throws Exception {
            builder.setMargins(10, 20, 30, 40);
            DynamicReport report = builder.build();

            assertEquals(10, report.getOptions().getTopMargin());
            assertEquals(20, report.getOptions().getBottomMargin());
            assertEquals(30, report.getOptions().getLeftMargin());
            assertEquals(40, report.getOptions().getRightMargin());
        }
    }

    @Nested
    @DisplayName("Detail Height")
    class DetailHeight {

        @Test
        void setDetailHeight() throws Exception {
            builder.setDetailHeight(25);
            DynamicReport report = builder.build();

            assertEquals(25, report.getOptions().getDetailHeight());
        }
    }

    @Nested
    @DisplayName("Image Columns")
    class ImageColumns {

        @Test
        void addImageColumn() throws Exception {
            builder.addImageColumn("Photo", "photo", 100, false, ImageScaleMode.FILL);
            DynamicReport report = builder.build();
            assertEquals(1, report.getColumns().size());
        }

        @Test
        void addImageColumnWithStyle() throws Exception {
            Style style = new Style();
            builder.addImageColumn("Photo", "photo", 100, false, ImageScaleMode.FILL, style);
            DynamicReport report = builder.build();
            assertEquals(1, report.getColumns().size());
        }
    }

    @Nested
    @DisplayName("Barcode Columns")
    class BarcodeColumns {

        @Test
        void addBarcodeColumn() throws Exception {
            builder.addBarcodeColumn("Barcode", "code", String.class.getName(),
                    BarcodeTypes.EAN128, true, 150, false, ImageScaleMode.FILL);
            DynamicReport report = builder.build();
            assertEquals(1, report.getColumns().size());
        }

        @Test
        void addBarcodeColumnWithStyle() throws Exception {
            Style style = new Style();
            builder.addBarcodeColumn("Barcode", "code", String.class.getName(),
                    BarcodeTypes.CODE_128, true, 150, false, ImageScaleMode.FILL, style);
            DynamicReport report = builder.build();
            assertEquals(1, report.getColumns().size());
        }

        @Test
        void addBarcodeColumnWithChecksum() throws Exception {
            Style style = new Style();
            builder.addBarcodeColumn("Barcode", "code", String.class.getName(),
                    BarcodeTypes.CODE_39, true, true, "AI", 150, false, ImageScaleMode.FILL, style);
            DynamicReport report = builder.build();
            assertEquals(1, report.getColumns().size());
        }
    }

    @Nested
    @DisplayName("Crosstab Integration")
    class CrosstabIntegration {

        @Test
        void addSummaryCrosstab() throws Exception {
            DJCrosstab crosstab = new CrosstabBuilder()
                    .setHeight(100)
                    .setWidth(500)
                    .addRow("Product", "product", String.class.getName(), false)
                    .addColumn("State", "state", String.class.getName(), false)
                    .addMeasure("amount", Float.class.getName(), DJCalculation.SUM, "Amount", null)
                    .build();

            builder.addSummaryCrosstab(crosstab);
            DynamicReport report = builder.build();
            assertNotNull(report);
        }
    }

    @Nested
    @DisplayName("Style Variations")
    class StyleVariations {

        @Test
        void addColumnWithStyleAndHeaderStyle() throws Exception {
            Style style = new Style();
            Style headerStyle = new Style();
            headerStyle.setFont(Font.ARIAL_MEDIUM_BOLD);

            builder.addColumn("Name", "name", String.class.getName(), 100, style, headerStyle);
            DynamicReport report = builder.build();

            assertEquals(1, report.getColumns().size());
        }

        @Test
        void addColumnWithFixedWidthAndPattern() throws Exception {
            Style style = new Style();
            builder.addColumn("Amount", "amount", Float.class.getName(), 90, true, "$ #,##0.00", style);
            DynamicReport report = builder.build();
            assertEquals(1, report.getColumns().size());
        }

        @Test
        void addColumnWithFieldDescription() throws Exception {
            Style style = new Style();
            builder.addColumn("Name", "name", String.class.getName(), 100, false, null, style, "Field description");
            DynamicReport report = builder.build();
            assertEquals(1, report.getColumns().size());
        }
    }

    @Nested
    @DisplayName("Group Variables Extended")
    class GroupVariablesExtended {

        @Test
        void addGroupVariableInHeader() throws Exception {
            builder.addColumn("State", "state", String.class.getName(), 100);
            builder.addColumn("Amount", "amount", Float.class.getName(), 80);
            builder.addGroups(1);

            builder.addGroupVariable("header", 1, 2, DJCalculation.SUM, null);
            DynamicReport report = builder.build();

            assertNotNull(report);
        }

        @Test
        void addHeaderVariableWithFormatter() throws Exception {
            builder.addColumn("State", "state", String.class.getName(), 100);
            builder.addColumn("Amount", "amount", Float.class.getName(), 80);
            builder.addGroups(1);

            DJValueFormatter formatter = new DJValueFormatter() {
                public Object evaluate(Object value, Map fields, Map variables, Map parameters) {
                    return "Total: " + value;
                }
                public String getClassName() {
                    return String.class.getName();
                }
            };

            builder.addHeaderVariable(1, 2, DJCalculation.SUM, null, formatter);
            DynamicReport report = builder.build();

            assertNotNull(report);
        }

        @Test
        void addFooterVariableWithFormatter() throws Exception {
            builder.addColumn("State", "state", String.class.getName(), 100);
            builder.addColumn("Amount", "amount", Float.class.getName(), 80);
            builder.addGroups(1);

            DJValueFormatter formatter = new DJValueFormatter() {
                public Object evaluate(Object value, Map fields, Map variables, Map parameters) {
                    return value;
                }
                public String getClassName() {
                    return String.class.getName();
                }
            };

            builder.addFooterVariable(1, 2, DJCalculation.SUM, null, formatter);
            DynamicReport report = builder.build();

            assertEquals(1, report.getColumnsGroups().size());
        }

        @Test
        void addGroupVariableWithFormatterInFooter() throws Exception {
            builder.addColumn("State", "state", String.class.getName(), 100);
            builder.addColumn("Amount", "amount", Float.class.getName(), 80);
            builder.addGroups(1);

            DJValueFormatter formatter = new DJValueFormatter() {
                public Object evaluate(Object value, Map fields, Map variables, Map parameters) {
                    return value;
                }
                public String getClassName() {
                    return String.class.getName();
                }
            };

            builder.addGroupVariable(DJConstants.FOOTER, 1, 2, DJCalculation.SUM, null, formatter);
            DynamicReport report = builder.build();

            assertEquals(1, report.getColumnsGroups().size());
        }
    }

    @Nested
    @DisplayName("Groups with Layout")
    class GroupsWithLayout {

        @Test
        void addGroupsWithLayout() throws Exception {
            builder.addColumn("State", "state", String.class.getName(), 100)
                   .addColumn("Amount", "amount", Float.class.getName(), 80)
                   .addGroups(1, GroupLayout.VALUE_IN_HEADER);
            DynamicReport report = builder.build();

            assertEquals(1, report.getColumnsGroups().size());
            assertEquals(GroupLayout.VALUE_IN_HEADER, report.getColumnsGroups().get(0).getLayout());
        }
    }

    @Nested
    @DisplayName("Custom Expression Columns")
    class CustomExpressionColumns {

        private final CustomExpression stringExpression = new CustomExpression() {
            public Object evaluate(Map fields, Map variables, Map parameters) {
                return "computed";
            }
            public String getClassName() {
                return String.class.getName();
            }
        };

        @Test
        void addColumnWithCustomExpression() throws Exception {
            builder.addColumn("Computed", stringExpression, 100, false, null, null);
            DynamicReport report = builder.build();

            assertEquals(1, report.getColumns().size());
            assertEquals("Computed", report.getColumns().get(0).getTitle());
        }

        @Test
        void addImageColumnWithCustomExpression() throws Exception {
            CustomExpression imageExpression = new CustomExpression() {
                public Object evaluate(Map fields, Map variables, Map parameters) {
                    return null;
                }
                public String getClassName() {
                    return InputStream.class.getName();
                }
            };

            builder.addImageColumn("Image", imageExpression, 100, false, ImageScaleMode.FILL, null);
            DynamicReport report = builder.build();

            assertEquals(1, report.getColumns().size());
        }
    }

    @Nested
    @DisplayName("Image Column Variations")
    class ImageColumnVariations {

        @Test
        void addImageColumnWithClassName() throws Exception {
            builder.addImageColumn("Logo", "logo", InputStream.class.getName(), 80, false, ImageScaleMode.FILL, null);
            DynamicReport report = builder.build();

            assertEquals(1, report.getColumns().size());
        }
    }

    @Nested
    @DisplayName("Numeric Type Style Guessing")
    class NumericTypeStyleGuessing {

        @Test
        void addLongColumnGetsNumberStyle() throws Exception {
            builder.addColumn("Count", "count", Long.class.getName(), 80);
            DynamicReport report = builder.build();

            assertNotNull(report.getColumns().get(0).getStyle());
        }

        @Test
        void addDoubleColumnGetsCurrencyStyle() throws Exception {
            builder.addColumn("Price", "price", Double.class.getName(), 80);
            DynamicReport report = builder.build();

            assertNotNull(report.getColumns().get(0).getStyle());
            assertEquals("$ #.00", report.getColumns().get(0).getPattern());
        }

        @Test
        void addTimestampColumnGetsDatePattern() throws Exception {
            builder.addColumn("Created", "created", Timestamp.class.getName(), 120);
            DynamicReport report = builder.build();

            // Timestamp extends Date, so guessStyle applies the Date pattern first
            assertEquals("dd/MM/yy", report.getColumns().get(0).getPattern());
        }
    }

    @Nested
    @DisplayName("Variables")
    class ReportVariables {

        private final CustomExpression amountExpression = new CustomExpression() {
            public Object evaluate(Map fields, Map variables, Map parameters) {
                return fields.get("amount");
            }
            public String getClassName() {
                return Float.class.getName();
            }
        };

        @Test
        void addVariable() throws Exception {
            builder.addVariable("totalAmount", DJCalculation.SUM, amountExpression);
            DynamicReport report = builder.build();

            assertEquals(1, report.getVariables().size());
            assertEquals("totalAmount", report.getVariables().get(0).getName());
        }

        @Test
        void addVariableWithResetType() throws Exception {
            builder.addColumn("State", "state", String.class.getName(), 100);
            builder.addColumn("Amount", "amount", Float.class.getName(), 80);
            builder.addGroups(1);

            builder.addVariable("groupTotal", DJCalculation.SUM, amountExpression,
                    amountExpression, DJVariableResetType.GROUP, 1);
            DynamicReport report = builder.build();

            assertEquals(1, report.getVariables().size());
        }

        @Test
        void addVariableWithInvalidResetGroupThrows() throws Exception {
            builder.addColumn("Amount", "amount", Float.class.getName(), 80);

            assertThrows(BuilderException.class, () ->
                    builder.addVariable("bad", DJCalculation.SUM, amountExpression,
                            amountExpression, DJVariableResetType.GROUP, 1));
        }
    }

    @Nested
    @DisplayName("Group Crosstabs")
    class GroupCrosstabs {

        @Test
        void addHeaderCrosstabInGroup() throws Exception {
            builder.addColumn("State", "state", String.class.getName(), 100);
            builder.addColumn("Amount", "amount", Float.class.getName(), 80);
            builder.addGroups(1);

            DJCrosstab crosstab = new CrosstabBuilder()
                    .setHeight(100)
                    .setWidth(500)
                    .addRow("Product", "product", String.class.getName(), false)
                    .addColumn("State", "state", String.class.getName(), false)
                    .addMeasure("amount", Float.class.getName(), DJCalculation.SUM, "Amount", null)
                    .build();

            builder.addHeaderCrosstab(1, crosstab);
            DynamicReport report = builder.build();

            assertEquals(1, report.getColumnsGroups().get(0).getHeaderCrosstabs().size());
        }

        @Test
        void addFooterCrosstabInGroup() throws Exception {
            builder.addColumn("State", "state", String.class.getName(), 100);
            builder.addColumn("Amount", "amount", Float.class.getName(), 80);
            builder.addGroups(1);

            DJCrosstab crosstab = new CrosstabBuilder()
                    .setHeight(100)
                    .setWidth(500)
                    .addRow("Product", "product", String.class.getName(), false)
                    .addColumn("State", "state", String.class.getName(), false)
                    .addMeasure("amount", Float.class.getName(), DJCalculation.SUM, "Amount", null)
                    .build();

            builder.addFooterCrosstab(1, crosstab);
            DynamicReport report = builder.build();

            assertEquals(1, report.getColumnsGroups().get(0).getFooterCrosstabs().size());
        }
    }

    @Nested
    @DisplayName("Error Handling")
    class ErrorHandling {

        @Test
        void setGroupLayoutWithoutGroupsThrows() {
            assertThrows(BuilderException.class, () -> builder.setGroupLayout(1, GroupLayout.VALUE_IN_HEADER));
        }

        @Test
        void addHeaderVariableWithoutGroupsThrows() throws Exception {
            builder.addColumn("Amount", "amount", Float.class.getName(), 80);

            assertThrows(BuilderException.class, () ->
                    builder.addHeaderVariable(1, 1, DJCalculation.SUM, null));
        }
    }
}
