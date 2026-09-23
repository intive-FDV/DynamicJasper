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

package ar.com.fdvs.dj.domain;

import ar.com.fdvs.dj.core.layout.CrossTabColorShema;
import ar.com.fdvs.dj.domain.constants.Border;
import org.junit.jupiter.api.BeforeEach;
import static ar.com.fdvs.dj.core.DJConstants.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Comprehensive unit tests for DJCrosstab.
 */
class DJCrosstabFullTest {

    private DJCrosstab crosstab;

    @BeforeEach
    void setUp() {
        crosstab = new DJCrosstab();
    }

    @Nested
    @DisplayName("Dimensions")
    class Dimensions {

        @Test
        void setAndGetHeight() {
            crosstab.setHeight(400);
            assertEquals(400, crosstab.getHeight());
        }

        @Test
        void setAndGetWidth() {
            crosstab.setWidth(600);
            assertEquals(600, crosstab.getWidth());
        }

        @Test
        void setAndGetBottomSpace() {
            crosstab.setBottomSpace(20);
            assertEquals(20, crosstab.getBottomSpace());
        }

        @Test
        void setAndGetTopSpace() {
            crosstab.setTopSpace(15);
            assertEquals(15, crosstab.getTopSpace());
        }

        @Test
        void defaultBottomSpace() {
            assertEquals(10, crosstab.getBottomSpace());
        }

        @Test
        void defaultTopSpace() {
            assertEquals(10, crosstab.getTopSpace());
        }
    }

    @Nested
    @DisplayName("Layout Properties")
    class LayoutProperties {

        @Test
        void setAndGetUseFullWidth() {
            crosstab.setUseFullWidth(false);
            assertFalse(crosstab.isUseFullWidth());
        }

        @Test
        void defaultUseFullWidth() {
            assertTrue(crosstab.isUseFullWidth());
        }

        @Test
        void setAndGetIgnoreWidth() {
            crosstab.setIgnoreWidth(true);
            assertTrue(crosstab.isIgnoreWidth());
        }

        @Test
        void setAndGetColumnBreakOffset() {
            crosstab.setColumnBreakOffset(25);
            assertEquals(25, crosstab.getColumnBreakOffset());
        }

        @Test
        void defaultColumnBreakOffset() {
            assertEquals(10, crosstab.getColumnBreakOffset());
        }
    }

    @Nested
    @DisplayName("Title Properties")
    class TitleProperties {

        @Test
        void setAndGetMainHeaderTitle() {
            crosstab.setMainHeaderTitle("My Crosstab");
            assertEquals("My Crosstab", crosstab.getMainHeaderTitle());
        }

        @Test
        void defaultMainHeaderTitle() {
            assertEquals("", crosstab.getMainHeaderTitle());
        }

        @Test
        void setAndGetAutomaticTitle() {
            crosstab.setAutomaticTitle(true);
            assertTrue(crosstab.isAutomaticTitle());
        }

        @Test
        void defaultAutomaticTitle() {
            assertFalse(crosstab.isAutomaticTitle());
        }

        @Test
        void setAndGetCaption() {
            DJLabel caption = new DJLabel();
            crosstab.setCaption(caption);
            assertSame(caption, crosstab.getCaption());
        }
    }

    @Nested
    @DisplayName("Styles")
    class Styles {

        @Test
        void setAndGetHeaderStyle() {
            Style style = new Style();
            crosstab.setHeaderStyle(style);
            assertSame(style, crosstab.getHeaderStyle());
        }

        @Test
        void setAndGetColumnHeaderStyle() {
            Style style = new Style();
            crosstab.setColumnHeaderStyle(style);
            assertSame(style, crosstab.getColumnHeaderStyle());
        }

        @Test
        void setAndGetColumnTotalheaderStyle() {
            Style style = new Style();
            crosstab.setColumnTotalheaderStyle(style);
            assertSame(style, crosstab.getColumnTotalheaderStyle());
        }

        @Test
        void setAndGetColumnTotalStyle() {
            Style style = new Style();
            crosstab.setColumnTotalStyle(style);
            assertSame(style, crosstab.getColumnTotalStyle());
        }

        @Test
        void setAndGetRowHeaderStyle() {
            Style style = new Style();
            crosstab.setRowHeaderStyle(style);
            assertSame(style, crosstab.getRowHeaderStyle());
        }

        @Test
        void setAndGetRowTotalheaderStyle() {
            Style style = new Style();
            crosstab.setRowTotalheaderStyle(style);
            assertSame(style, crosstab.getRowTotalheaderStyle());
        }

        @Test
        void setAndGetRowTotalStyle() {
            Style style = new Style();
            crosstab.setRowTotalStyle(style);
            assertSame(style, crosstab.getRowTotalStyle());
        }

        @Test
        void setAndGetMeasureStyle() {
            Style style = new Style();
            crosstab.setMeasureStyle(style);
            assertSame(style, crosstab.getMeasureStyle());
        }
    }

    @Nested
    @DisplayName("Color Scheme")
    class ColorSchemeTests {

        @Test
        void setAndGetColorScheme() {
            crosstab.setColorScheme(5);
            assertEquals(5, crosstab.getColorScheme());
        }

        @Test
        void defaultColorScheme() {
            assertEquals(0, crosstab.getColorScheme());
        }

        @Test
        void setAndGetCtColorScheme() {
            CrossTabColorShema schema = new CrossTabColorShema(2, 3);
            crosstab.setCtColorScheme(schema);
            assertSame(schema, crosstab.getCtColorScheme());
        }
    }

    @Nested
    @DisplayName("Border")
    class BorderTests {

        @Test
        void setAndGetCellBorder() {
            Border border = Border.THIN();
            crosstab.setCellBorder(border);
            assertSame(border, crosstab.getCellBorder());
        }

        @Test
        void cellBorderNullByDefault() {
            assertNull(crosstab.getCellBorder());
        }
    }

    @Nested
    @DisplayName("DataSource")
    class DataSourceTests {

        @Test
        void setAndGetDatasource() {
            DJDataSource ds = new DJDataSource("myField", DATA_SOURCE_ORIGIN_FIELD, DATA_SOURCE_TYPE_COLLECTION);
            crosstab.setDatasource(ds);
            assertSame(ds, crosstab.getDatasource());
        }
    }

    @Nested
    @DisplayName("Collections")
    class CollectionsTests {

        @Test
        void rowsInitiallyEmpty() {
            assertTrue(crosstab.getRows().isEmpty());
        }

        @Test
        void columnsInitiallyEmpty() {
            assertTrue(crosstab.getColumns().isEmpty());
        }

        @Test
        void measuresInitiallyEmpty() {
            assertTrue(crosstab.getMeasures().isEmpty());
        }

        @Test
        void setRows() {
            java.util.List<DJCrosstabRow> rows = new java.util.ArrayList<>();
            rows.add(new DJCrosstabRow());
            crosstab.setRows(rows);
            assertEquals(1, crosstab.getRows().size());
        }

        @Test
        void setColumns() {
            java.util.List<DJCrosstabColumn> columns = new java.util.ArrayList<>();
            columns.add(new DJCrosstabColumn());
            crosstab.setColumns(columns);
            assertEquals(1, crosstab.getColumns().size());
        }

        @Test
        void setMeasures() {
            java.util.List<DJCrosstabMeasure> measures = new java.util.ArrayList<>();
            measures.add(new DJCrosstabMeasure());
            crosstab.setMeasures(measures);
            assertEquals(1, crosstab.getMeasures().size());
        }
    }
}
