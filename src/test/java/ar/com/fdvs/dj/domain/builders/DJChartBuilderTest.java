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

import ar.com.fdvs.dj.domain.chart.DJChart;
import ar.com.fdvs.dj.domain.chart.DJChartOptions;
import ar.com.fdvs.dj.domain.chart.builder.DJBarChartBuilder;
import ar.com.fdvs.dj.domain.chart.builder.DJBar3DChartBuilder;
import ar.com.fdvs.dj.domain.chart.builder.DJLineChartBuilder;
import ar.com.fdvs.dj.domain.chart.builder.DJPieChartBuilder;
import ar.com.fdvs.dj.domain.chart.builder.DJPie3DChartBuilder;
import ar.com.fdvs.dj.domain.chart.builder.DJAreaChartBuilder;
import ar.com.fdvs.dj.domain.constants.Font;
import ar.com.fdvs.dj.domain.entities.columns.AbstractColumn;
import ar.com.fdvs.dj.domain.entities.columns.PropertyColumn;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.awt.Color;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for DJChartBuilder and related chart builders.
 */
class DJChartBuilderTest {

    private PropertyColumn categoryColumn;
    private AbstractColumn amountColumn;

    @BeforeEach
    void setUp() {
        categoryColumn = (PropertyColumn) ColumnBuilder.getNew()
                .setColumnProperty("state", String.class.getName())
                .setTitle("State")
                .setWidth(100)
                .build();

        amountColumn = ColumnBuilder.getNew()
                .setColumnProperty("amount", Float.class.getName())
                .setTitle("Amount")
                .setWidth(80)
                .build();
    }

    @Nested
    @DisplayName("DJBarChartBuilder")
    class BarChartBuilder {

        @Test
        void buildMinimalChart() {
            DJBarChartBuilder builder = new DJBarChartBuilder();
            DJChart chart = builder
                    .setCategory(categoryColumn)
                    .addSerie(amountColumn)
                    .build();

            assertNotNull(chart);
        }

        @Test
        void buildWithDimensions() {
            DJBarChartBuilder builder = new DJBarChartBuilder();
            DJChart chart = builder
                    .setCategory(categoryColumn)
                    .addSerie(amountColumn)
                    .setX(0)
                    .setY(0)
                    .setWidth(400)
                    .setHeight(300)
                    .build();

            assertNotNull(chart);
        }

        @Test
        void buildWithTitle() {
            DJBarChartBuilder builder = new DJBarChartBuilder();
            DJChart chart = builder
                    .setCategory(categoryColumn)
                    .addSerie(amountColumn)
                    .setTitle("Sales by State")
                    .build();

            assertNotNull(chart);
        }

        @Test
        void buildWithColors() {
            DJBarChartBuilder builder = new DJBarChartBuilder();
            DJChart chart = builder
                    .setCategory(categoryColumn)
                    .addSerie(amountColumn)
                    .setBackColor(Color.WHITE)
                    .build();

            assertNotNull(chart);
        }

        @Test
        void buildWithMultipleSeries() {
            AbstractColumn quantityColumn = ColumnBuilder.getNew()
                    .setColumnProperty("quantity", Integer.class.getName())
                    .setTitle("Quantity")
                    .setWidth(80)
                    .build();

            DJBarChartBuilder builder = new DJBarChartBuilder();
            DJChart chart = builder
                    .setCategory(categoryColumn)
                    .addSerie(amountColumn)
                    .addSerie(quantityColumn)
                    .build();

            assertNotNull(chart);
        }

        @Test
        void buildWithSerieLabel() {
            DJBarChartBuilder builder = new DJBarChartBuilder();
            DJChart chart = builder
                    .setCategory(categoryColumn)
                    .addSerie(amountColumn, "Total Amount")
                    .build();

            assertNotNull(chart);
        }
    }

    @Nested
    @DisplayName("DJBar3DChartBuilder")
    class Bar3DChartBuilder {

        @Test
        void buildMinimalChart() {
            DJBar3DChartBuilder builder = new DJBar3DChartBuilder();
            DJChart chart = builder
                    .setCategory(categoryColumn)
                    .addSerie(amountColumn)
                    .build();

            assertNotNull(chart);
        }

        @Test
        void buildWithShowLabels() {
            DJBar3DChartBuilder builder = new DJBar3DChartBuilder();
            DJChart chart = builder
                    .setCategory(categoryColumn)
                    .addSerie(amountColumn)
                    .setShowLabels(true)
                    .build();

            assertNotNull(chart);
        }
    }

    @Nested
    @DisplayName("DJPieChartBuilder")
    class PieChartBuilder {

        @Test
        void buildMinimalChart() {
            DJPieChartBuilder builder = new DJPieChartBuilder();
            DJChart chart = builder
                    .setKey(categoryColumn)
                    .addSerie(amountColumn)
                    .build();

            assertNotNull(chart);
        }

        @Test
        void buildWithCircular() {
            DJPieChartBuilder builder = new DJPieChartBuilder();
            DJChart chart = builder
                    .setKey(categoryColumn)
                    .addSerie(amountColumn)
                    .setCircular(true)
                    .build();

            assertNotNull(chart);
        }
    }

    @Nested
    @DisplayName("DJPie3DChartBuilder")
    class Pie3DChartBuilder {

        @Test
        void buildMinimalChart() {
            DJPie3DChartBuilder builder = new DJPie3DChartBuilder();
            DJChart chart = builder
                    .setKey(categoryColumn)
                    .addSerie(amountColumn)
                    .build();

            assertNotNull(chart);
        }

        @Test
        void buildWithDepthFactor() {
            DJPie3DChartBuilder builder = new DJPie3DChartBuilder();
            DJChart chart = builder
                    .setKey(categoryColumn)
                    .addSerie(amountColumn)
                    .setDepthFactor(0.5)
                    .build();

            assertNotNull(chart);
        }
    }

    @Nested
    @DisplayName("DJLineChartBuilder")
    class LineChartBuilder {

        @Test
        void buildMinimalChart() {
            DJLineChartBuilder builder = new DJLineChartBuilder();
            DJChart chart = builder
                    .setCategory(categoryColumn)
                    .addSerie(amountColumn)
                    .build();

            assertNotNull(chart);
        }

        @Test
        void buildWithShowShapes() {
            DJLineChartBuilder builder = new DJLineChartBuilder();
            DJChart chart = builder
                    .setCategory(categoryColumn)
                    .addSerie(amountColumn)
                    .setShowShapes(true)
                    .build();

            assertNotNull(chart);
        }

        @Test
        void buildWithShowLines() {
            DJLineChartBuilder builder = new DJLineChartBuilder();
            DJChart chart = builder
                    .setCategory(categoryColumn)
                    .addSerie(amountColumn)
                    .setShowLines(true)
                    .build();

            assertNotNull(chart);
        }
    }

    @Nested
    @DisplayName("DJAreaChartBuilder")
    class AreaChartBuilder {

        @Test
        void buildMinimalChart() {
            DJAreaChartBuilder builder = new DJAreaChartBuilder();
            DJChart chart = builder
                    .setCategory(categoryColumn)
                    .addSerie(amountColumn)
                    .build();

            assertNotNull(chart);
        }
    }

    @Nested
    @DisplayName("DJChartOptions")
    class ChartOptions {

        @Test
        void defaultOptions() {
            DJChartOptions options = new DJChartOptions();
            assertNotNull(options);
        }

        @Test
        void optionsWithPosition() {
            DJChartOptions options = new DJChartOptions();
            options.setX(10);
            options.setY(20);
            assertEquals(10, options.getX());
            assertEquals(20, options.getY());
        }

        @Test
        void optionsWithSize() {
            DJChartOptions options = new DJChartOptions();
            options.setWidth(400);
            options.setHeight(300);
            assertEquals(400, options.getWidth());
            assertEquals(300, options.getHeight());
        }

        @Test
        void optionsWithColors() {
            DJChartOptions options = new DJChartOptions();
            options.setBackColor(Color.LIGHT_GRAY);
            assertEquals(Color.LIGHT_GRAY, options.getBackColor());
        }

        @Test
        void optionsWithLegend() {
            DJChartOptions options = new DJChartOptions();
            options.setShowLegend(true);
            assertTrue(options.getShowLegend());
        }

        @Test
        void optionsWithTitleFont() {
            DJChartOptions options = new DJChartOptions();
            options.setTitleFont(Font.ARIAL_BIG_BOLD);
            assertNotNull(options.getTitleFont());
        }
    }

    @Nested
    @DisplayName("Fluent API")
    class FluentApi {

        @Test
        void chainedSetters() {
            DJBarChartBuilder builder = new DJBarChartBuilder();
            DJChart chart = builder
                    .setCategory(categoryColumn)
                    .addSerie(amountColumn)
                    .setX(10)
                    .setY(20)
                    .setWidth(400)
                    .setHeight(300)
                    .setTitle("Test Chart")
                    .setBackColor(Color.WHITE)
                    .setShowLegend(true)
                    .build();

            assertNotNull(chart);
        }
    }
}
