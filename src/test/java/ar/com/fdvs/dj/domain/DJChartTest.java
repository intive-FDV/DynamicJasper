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

import ar.com.fdvs.dj.domain.chart.DJChart;
import ar.com.fdvs.dj.domain.chart.DJChartOptions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for DJChart (ar.com.fdvs.dj.domain.chart.DJChart).
 */
class DJChartTest {

    @Nested
    @DisplayName("Constructor and Chart Types")
    class ConstructorAndChartTypes {

        @Test
        void barChartConstructor() {
            DJChart chart = new DJChart(DJChart.BAR_CHART);
            assertNotNull(chart);
            assertNotNull(chart.getDataset());
            assertNotNull(chart.getPlot());
        }

        @Test
        void pieChartConstructor() {
            DJChart chart = new DJChart(DJChart.PIE_CHART);
            assertNotNull(chart);
        }

        @Test
        void lineChartConstructor() {
            DJChart chart = new DJChart(DJChart.LINE_CHART);
            assertNotNull(chart);
        }

        @Test
        void areaChartConstructor() {
            DJChart chart = new DJChart(DJChart.AREA_CHART);
            assertNotNull(chart);
        }

        @Test
        void pie3DChartConstructor() {
            DJChart chart = new DJChart(DJChart.PIE3D_CHART);
            assertNotNull(chart);
        }

        @Test
        void bar3DChartConstructor() {
            DJChart chart = new DJChart(DJChart.BAR3D_CHART);
            assertNotNull(chart);
        }

        @Test
        void stackedBarChartConstructor() {
            DJChart chart = new DJChart(DJChart.STACKEDBAR_CHART);
            assertNotNull(chart);
        }

        @Test
        void stackedBar3DChartConstructor() {
            DJChart chart = new DJChart(DJChart.STACKEDBAR3D_CHART);
            assertNotNull(chart);
        }

        @Test
        void stackedAreaChartConstructor() {
            DJChart chart = new DJChart(DJChart.STACKEDAREA_CHART);
            assertNotNull(chart);
        }

        @Test
        void timeSeriesChartConstructor() {
            DJChart chart = new DJChart(DJChart.TIMESERIES_CHART);
            assertNotNull(chart);
        }

        @Test
        void xyAreaChartConstructor() {
            DJChart chart = new DJChart(DJChart.XYAREA_CHART);
            assertNotNull(chart);
        }

        @Test
        void xyBarChartConstructor() {
            DJChart chart = new DJChart(DJChart.XYBAR_CHART);
            assertNotNull(chart);
        }

        @Test
        void xyLineChartConstructor() {
            DJChart chart = new DJChart(DJChart.XYLINE_CHART);
            assertNotNull(chart);
        }

        @Test
        void scatterChartConstructor() {
            DJChart chart = new DJChart(DJChart.SCATTER_CHART);
            assertNotNull(chart);
        }
    }

    @Nested
    @DisplayName("Properties")
    class Properties {

        @Test
        void setAndGetOperation() {
            DJChart chart = new DJChart(DJChart.BAR_CHART);
            chart.setOperation(DJChart.CALCULATION_COUNT);
            assertEquals(DJChart.CALCULATION_COUNT, chart.getOperation());
        }

        @Test
        void defaultOperationIsSum() {
            DJChart chart = new DJChart(DJChart.BAR_CHART);
            assertEquals(DJChart.CALCULATION_SUM, chart.getOperation());
        }

        @Test
        void setAndGetOptions() {
            DJChart chart = new DJChart(DJChart.BAR_CHART);
            DJChartOptions options = new DJChartOptions();
            chart.setOptions(options);
            assertSame(options, chart.getOptions());
        }

        @Test
        void optionsNotNullByDefault() {
            DJChart chart = new DJChart(DJChart.BAR_CHART);
            assertNotNull(chart.getOptions());
        }

        @Test
        void setAndGetLink() {
            DJChart chart = new DJChart(DJChart.BAR_CHART);
            DJHyperLink link = new DJHyperLink();
            chart.setLink(link);
            assertSame(link, chart.getLink());
        }

        @Test
        void linkNullByDefault() {
            DJChart chart = new DJChart(DJChart.BAR_CHART);
            assertNull(chart.getLink());
        }
    }

    @Nested
    @DisplayName("Constants")
    class Constants {

        @Test
        void chartTypeConstants() {
            assertEquals((byte) 0, DJChart.AREA_CHART);
            assertEquals((byte) 2, DJChart.BAR_CHART);
            assertEquals((byte) 1, DJChart.BAR3D_CHART);
            assertEquals((byte) 6, DJChart.LINE_CHART);
            assertEquals((byte) 8, DJChart.PIE_CHART);
            assertEquals((byte) 7, DJChart.PIE3D_CHART);
            assertEquals((byte) 9, DJChart.SCATTER_CHART);
            assertEquals((byte) 11, DJChart.STACKEDBAR_CHART);
            assertEquals((byte) 10, DJChart.STACKEDBAR3D_CHART);
            assertEquals((byte) 19, DJChart.STACKEDAREA_CHART);
            assertEquals((byte) 15, DJChart.TIMESERIES_CHART);
            assertEquals((byte) 12, DJChart.XYAREA_CHART);
            assertEquals((byte) 13, DJChart.XYBAR_CHART);
            assertEquals((byte) 14, DJChart.XYLINE_CHART);
        }

        @Test
        void calculationConstants() {
            assertEquals((byte) 1, DJChart.CALCULATION_COUNT);
            assertEquals((byte) 2, DJChart.CALCULATION_SUM);
        }
    }
}
