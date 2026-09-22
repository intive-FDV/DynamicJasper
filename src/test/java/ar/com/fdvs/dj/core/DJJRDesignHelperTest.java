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

package ar.com.fdvs.dj.core;

import ar.com.fdvs.dj.domain.DynamicJasperDesign;
import ar.com.fdvs.dj.domain.DynamicReport;
import ar.com.fdvs.dj.domain.builders.ColumnBuilder;
import ar.com.fdvs.dj.domain.builders.DynamicReportBuilder;
import ar.com.fdvs.dj.domain.constants.Page;
import ar.com.fdvs.dj.domain.entities.columns.AbstractColumn;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for DJJRDesignHelper.
 * Tests JasperDesign creation and configuration.
 */
class DJJRDesignHelperTest {

    @Nested
    @DisplayName("getNewDesign")
    class GetNewDesign {

        @Test
        void createsDesignWithDefaults() throws Exception {
            DynamicReport dr = new DynamicReportBuilder()
                    .setTitle("Test Report")
                    .build();

            DynamicJasperDesign design = DJJRDesignHelper.getNewDesign(dr);

            assertNotNull(design);
            assertNotNull(design.getName());
        }

        @Test
        void createsDesignWithPortraitOrientation() throws Exception {
            DynamicReport dr = new DynamicReportBuilder()
                    .setTitle("Test Report")
                    .setPageSizeAndOrientation(Page.Page_A4_Portrait())
                    .build();

            DynamicJasperDesign design = DJJRDesignHelper.getNewDesign(dr);

            assertNotNull(design);
            assertEquals(Page.Page_A4_Portrait().getWidth(), design.getPageWidth());
            assertEquals(Page.Page_A4_Portrait().getHeight(), design.getPageHeight());
        }

        @Test
        void createsDesignWithLandscapeOrientation() throws Exception {
            DynamicReport dr = new DynamicReportBuilder()
                    .setTitle("Test Report")
                    .setPageSizeAndOrientation(Page.Page_A4_Landscape())
                    .build();

            DynamicJasperDesign design = DJJRDesignHelper.getNewDesign(dr);

            assertNotNull(design);
            assertEquals(Page.Page_A4_Landscape().getWidth(), design.getPageWidth());
        }

        @Test
        void createsDesignWithMargins() throws Exception {
            DynamicReport dr = new DynamicReportBuilder()
                    .setTitle("Test Report")
                    .setMargins(10, 20, 30, 40)
                    .build();

            DynamicJasperDesign design = DJJRDesignHelper.getNewDesign(dr);

            assertNotNull(design);
            assertEquals(10, design.getTopMargin());
            assertEquals(20, design.getBottomMargin());
            assertEquals(30, design.getLeftMargin());
            assertEquals(40, design.getRightMargin());
        }

        @Test
        void createsDesignWithColumns() throws Exception {
            AbstractColumn column = ColumnBuilder.getNew()
                    .setColumnProperty("name", String.class.getName())
                    .setTitle("Name")
                    .build();

            DynamicReport dr = new DynamicReportBuilder()
                    .setTitle("Test Report")
                    .addColumn(column)
                    .build();

            DynamicJasperDesign design = DJJRDesignHelper.getNewDesign(dr);

            assertNotNull(design);
        }

        @Test
        void createsDesignWithReportName() throws Exception {
            DynamicReport dr = new DynamicReportBuilder()
                    .setTitle("Test Report")
                    .setReportName("my_custom_report")
                    .build();

            DynamicJasperDesign design = DJJRDesignHelper.getNewDesign(dr);

            assertNotNull(design);
            assertEquals("my_custom_report", design.getName());
        }

        @Test
        void createsDesignWithColumnsPerPage() throws Exception {
            DynamicReport dr = new DynamicReportBuilder()
                    .setTitle("Test Report")
                    .setColumnsPerPage(2, 10)
                    .build();

            DynamicJasperDesign design = DJJRDesignHelper.getNewDesign(dr);

            assertNotNull(design);
            assertEquals(2, design.getColumnCount());
            assertEquals(10, design.getColumnSpacing());
        }
    }

    @Nested
    @DisplayName("getJRDesignQuery")
    class GetJRDesignQuery {

        @Test
        void createsQueryWithText() throws Exception {
            DynamicReport dr = new DynamicReportBuilder()
                    .setTitle("Test Report")
                    .setQuery("SELECT * FROM products", "sql")
                    .build();

            JRDesignQuery query = DJJRDesignHelper.getJRDesignQuery(dr);

            assertNotNull(query);
            assertEquals("SELECT * FROM products", query.getText());
            assertEquals("sql", query.getLanguage());
        }

        @Test
        void noQueryThrowsNPE() {
            DynamicReport dr = new DynamicReportBuilder()
                    .setTitle("Test Report")
                    .build();

            // When no query set, getJRDesignQuery throws NPE
            assertThrows(NullPointerException.class, () ->
                DJJRDesignHelper.getJRDesignQuery(dr));
        }

        @Test
        void createsQueryWithDefaultLanguage() throws Exception {
            DynamicReport dr = new DynamicReportBuilder()
                    .setTitle("Test Report")
                    .setQuery("SELECT * FROM orders", null)
                    .build();

            JRDesignQuery query = DJJRDesignHelper.getJRDesignQuery(dr);

            assertNotNull(query);
            assertEquals("SELECT * FROM orders", query.getText());
        }
    }

    @Nested
    @DisplayName("Behavioral Options")
    class BehavioralOptions {

        @Test
        void ignoresPagination() throws Exception {
            DynamicReport dr = new DynamicReportBuilder()
                    .setTitle("Test Report")
                    .setIgnorePagination(true)
                    .build();

            DynamicJasperDesign design = DJJRDesignHelper.getNewDesign(dr);

            assertNotNull(design);
            assertTrue(design.isIgnorePagination());
        }

        @Test
        void setsWhenNoDataType() throws Exception {
            DynamicReport dr = new DynamicReportBuilder()
                    .setTitle("Test Report")
                    .setWhenNoData("No data", null)
                    .build();

            DynamicJasperDesign design = DJJRDesignHelper.getNewDesign(dr);

            assertNotNull(design);
        }
    }

    @Nested
    @DisplayName("Band Creation")
    class BandCreation {

        @Test
        void createsDetailBand() throws Exception {
            AbstractColumn column = ColumnBuilder.getNew()
                    .setColumnProperty("name", String.class.getName())
                    .setTitle("Name")
                    .build();

            DynamicReport dr = new DynamicReportBuilder()
                    .setTitle("Test Report")
                    .addColumn(column)
                    .setDetailHeight(20)
                    .build();

            DynamicJasperDesign design = DJJRDesignHelper.getNewDesign(dr);

            assertNotNull(design);
            assertNotNull(design.getDetailSection());
        }

        @Test
        void createsHeaderBand() throws Exception {
            DynamicReport dr = new DynamicReportBuilder()
                    .setTitle("Test Report")
                    .setHeaderHeight(30)
                    .build();

            DynamicJasperDesign design = DJJRDesignHelper.getNewDesign(dr);

            assertNotNull(design);
        }

        @Test
        void createsFooterBand() throws Exception {
            DynamicReport dr = new DynamicReportBuilder()
                    .setTitle("Test Report")
                    .setFooterHeight(25)
                    .build();

            DynamicJasperDesign design = DJJRDesignHelper.getNewDesign(dr);

            assertNotNull(design);
        }
    }

    @Nested
    @DisplayName("Page Sizes")
    class PageSizes {

        @Test
        void a4Portrait() throws Exception {
            DynamicReport dr = new DynamicReportBuilder()
                    .setTitle("Test")
                    .setPageSizeAndOrientation(Page.Page_A4_Portrait())
                    .build();

            DynamicJasperDesign design = DJJRDesignHelper.getNewDesign(dr);

            assertEquals(595, design.getPageWidth());
            assertEquals(842, design.getPageHeight());
        }

        @Test
        void letterPortrait() throws Exception {
            DynamicReport dr = new DynamicReportBuilder()
                    .setTitle("Test")
                    .setPageSizeAndOrientation(Page.Page_Letter_Portrait())
                    .build();

            DynamicJasperDesign design = DJJRDesignHelper.getNewDesign(dr);

            assertEquals(612, design.getPageWidth());
            assertEquals(792, design.getPageHeight());
        }

        @Test
        void legalPortrait() throws Exception {
            DynamicReport dr = new DynamicReportBuilder()
                    .setTitle("Test")
                    .setPageSizeAndOrientation(Page.Page_Legal_Portrait())
                    .build();

            DynamicJasperDesign design = DJJRDesignHelper.getNewDesign(dr);

            assertEquals(612, design.getPageWidth());
            assertEquals(1008, design.getPageHeight());
        }
    }
}
