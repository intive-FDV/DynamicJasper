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

package ar.com.fdvs.dj.domain.chart;

import ar.com.fdvs.dj.domain.StringExpression;
import ar.com.fdvs.dj.domain.constants.Font;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.awt.Color;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for DJChartOptions.
 */
class DJChartOptionsTest {

    private DJChartOptions options;

    @BeforeEach
    void setUp() {
        options = new DJChartOptions();
    }

    @Nested
    @DisplayName("Basic Properties")
    class BasicProperties {

        @Test
        void setAndGetBackColor() {
            options.setBackColor(Color.WHITE);
            assertEquals(Color.WHITE, options.getBackColor());
        }

        @Test
        void setAndGetHeight() {
            options.setHeight(300);
            assertEquals(300, options.getHeight());
        }

        @Test
        void setAndGetWidth() {
            options.setWidth(500);
            assertEquals(500, options.getWidth());
        }

        @Test
        void setAndGetCentered() {
            options.setCentered(true);
            assertTrue(options.isCentered());
        }

        @Test
        void setAndGetPosition() {
            options.setPosition(DJChartOptions.POSITION_HEADER);
            assertEquals(DJChartOptions.POSITION_HEADER, options.getPosition());
        }

        @Test
        void setAndGetX() {
            options.setX(100);
            assertEquals(100, options.getX());
        }

        @Test
        void setAndGetY() {
            options.setY(200);
            assertEquals(200, options.getY());
        }
    }

    @Nested
    @DisplayName("Title Properties")
    class TitleProperties {

        @Test
        void setAndGetTitleColor() {
            options.setTitleColor(Color.BLUE);
            assertEquals(Color.BLUE, options.getTitleColor());
        }

        @Test
        void setAndGetTitleFont() {
            Font font = Font.ARIAL_BIG;
            options.setTitleFont(font);
            assertSame(font, options.getTitleFont());
        }

        @Test
        void setAndGetTitlePosition() {
            options.setTitlePosition(DJChartOptions.EDGE_TOP);
            assertEquals(DJChartOptions.EDGE_TOP, options.getTitlePosition().byteValue());
        }

        @Test
        void setAndGetTitleExpression() {
            StringExpression expr = new StringExpression() {
                @Override
                public Object evaluate(Map fields, Map variables, Map parameters) {
                    return "Chart Title";
                }
                @Override
                public String getClassName() {
                    return String.class.getName();
                }
            };
            options.setTitleExpression(expr);
            assertSame(expr, options.getTitleExpression());
        }
    }

    @Nested
    @DisplayName("Subtitle Properties")
    class SubtitleProperties {

        @Test
        void setAndGetSubtitleColor() {
            options.setSubtitleColor(Color.GRAY);
            assertEquals(Color.GRAY, options.getSubtitleColor());
        }

        @Test
        void setAndGetSubtitleFont() {
            Font font = Font.ARIAL_MEDIUM;
            options.setSubtitleFont(font);
            assertSame(font, options.getSubtitleFont());
        }

        @Test
        void setAndGetSubtitleExpression() {
            StringExpression expr = new StringExpression() {
                @Override
                public Object evaluate(Map fields, Map variables, Map parameters) {
                    return "Subtitle";
                }
                @Override
                public String getClassName() {
                    return String.class.getName();
                }
            };
            options.setSubtitleExpression(expr);
            assertSame(expr, options.getSubtitleExpression());
        }
    }

    @Nested
    @DisplayName("Legend Properties")
    class LegendProperties {

        @Test
        void setAndGetShowLegend() {
            options.setShowLegend(true);
            assertTrue(options.getShowLegend());
        }

        @Test
        void setAndGetLegendColor() {
            options.setLegendColor(Color.BLACK);
            assertEquals(Color.BLACK, options.getLegendColor());
        }

        @Test
        void setAndGetLegendBackgroundColor() {
            options.setLegendBackgroundColor(Color.LIGHT_GRAY);
            assertEquals(Color.LIGHT_GRAY, options.getLegendBackgroundColor());
        }

        @Test
        void setAndGetLegendFont() {
            Font font = Font.ARIAL_SMALL;
            options.setLegendFont(font);
            assertSame(font, options.getLegendFont());
        }

        @Test
        void setAndGetLegendPosition() {
            options.setLegendPosition(DJChartOptions.EDGE_BOTTOM);
            assertEquals(DJChartOptions.EDGE_BOTTOM, options.getLegendPosition().byteValue());
        }
    }

    @Nested
    @DisplayName("Line Style Properties")
    class LineStyleProperties {

        @Test
        void setAndGetLineStyle() {
            options.setLineStyle(DJChartOptions.LINE_STYLE_DASHED);
            assertEquals(DJChartOptions.LINE_STYLE_DASHED, options.getLineStyle().byteValue());
        }

        @Test
        void setAndGetLineWidth() {
            options.setLineWidth(2.5f);
            assertEquals(2.5f, options.getLineWidth());
        }

        @Test
        void setAndGetLineColor() {
            options.setLineColor(Color.RED);
            assertEquals(Color.RED, options.getLineColor());
        }

        @Test
        void setAndGetPadding() {
            options.setPadding(10);
            assertEquals(10, options.getPadding().intValue());
        }
    }

    @Nested
    @DisplayName("Theme")
    class ThemeTests {

        @Test
        void setAndGetTheme() {
            options.setTheme("myTheme");
            assertEquals("myTheme", options.getTheme());
        }
    }

    @Nested
    @DisplayName("Constants")
    class ConstantsTests {

        @Test
        void positionConstants() {
            assertEquals((byte) 1, DJChartOptions.POSITION_FOOTER);
            assertEquals((byte) 2, DJChartOptions.POSITION_HEADER);
        }

        @Test
        void edgeConstants() {
            assertEquals((byte) 0, DJChartOptions.EDGE_TOP);
            assertEquals((byte) 1, DJChartOptions.EDGE_BOTTOM);
            assertEquals((byte) 2, DJChartOptions.EDGE_LEFT);
            assertEquals((byte) 3, DJChartOptions.EDGE_RIGHT);
        }

        @Test
        void lineStyleConstants() {
            assertEquals((byte) 0, DJChartOptions.LINE_STYLE_SOLID);
            assertEquals((byte) 1, DJChartOptions.LINE_STYLE_DASHED);
            assertEquals((byte) 2, DJChartOptions.LINE_STYLE_DOTTED);
            assertEquals((byte) 3, DJChartOptions.LINE_STYLE_DOUBLE);
        }
    }
}
