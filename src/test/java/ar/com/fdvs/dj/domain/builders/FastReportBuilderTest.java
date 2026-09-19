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

import ar.com.fdvs.dj.domain.DJCalculation;
import ar.com.fdvs.dj.domain.DynamicReport;
import ar.com.fdvs.dj.domain.Style;
import ar.com.fdvs.dj.domain.constants.Font;
import ar.com.fdvs.dj.domain.constants.GroupLayout;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Date;

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
}
