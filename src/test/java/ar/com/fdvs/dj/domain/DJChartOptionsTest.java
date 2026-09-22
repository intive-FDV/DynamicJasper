/*
 * DynamicJasper: A library for creating reports dynamically by specifying
 * columns, groups, styles, etc. at runtime. It also saves a lot of development
 * time in many cases! (http://sourceforge.net/projects/dynamicjasper)
 *
 * Copyright (C) 2008  FDV Solutions (http://www.fdvsolutions.com)
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 */

package ar.com.fdvs.dj.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the deprecated ar.com.fdvs.dj.domain.DJChartOptions.
 */
class DJChartOptionsTest {

    private DJChartOptions options;

    @BeforeEach
    void setUp() {
        options = new DJChartOptions();
    }

    @Nested
    @DisplayName("Default Constructor")
    class Defaults {

        @Test
        void appliesExpectedDefaults() {
            assertTrue(options.isShowLegend());
            assertEquals(Color.WHITE, options.getBackColor());
            assertEquals(200, options.getHeight());
            assertEquals(200, options.getWidth());
            assertTrue(options.isCentered());
            assertEquals(DJChartOptions.POSITION_FOOTER, options.getPosition());
            assertEquals(0, options.getX());
            assertEquals(0, options.getY());
            assertTrue(options.isShowLabels());
            assertEquals((byte) 1, options.getBorder());
            assertFalse(options.isUseColumnsAsCategorie());
        }

        @Test
        void defaultColorsAreNotEmpty() {
            assertNotNull(options.getColors());
            assertFalse(options.getColors().isEmpty());
        }
    }

    @Nested
    @DisplayName("Full-args Constructor")
    class FullArgsConstructor {

        @Test
        void assignsAllFields() {
            List colors = new ArrayList();
            colors.add(Color.RED);

            DJChartOptions o = new DJChartOptions(false, Color.BLACK, 300, 400,
                    false, DJChartOptions.POSITION_HEADER, 10, 20, false, (byte) 2, colors);

            assertFalse(o.isShowLegend());
            assertEquals(Color.BLACK, o.getBackColor());
            assertEquals(300, o.getHeight());
            assertEquals(400, o.getWidth());
            assertFalse(o.isCentered());
            assertEquals(DJChartOptions.POSITION_HEADER, o.getPosition());
            assertEquals(20, o.getX());
            assertEquals(10, o.getY());
            assertFalse(o.isShowLabels());
            assertEquals((byte) 2, o.getBorder());
            assertSame(colors, o.getColors());
        }
    }

    @Nested
    @DisplayName("Setters and Getters")
    class SettersAndGetters {

        @Test
        void backColor() {
            options.setBackColor(Color.BLUE);
            assertEquals(Color.BLUE, options.getBackColor());
        }

        @Test
        void centered() {
            options.setCentered(false);
            assertFalse(options.isCentered());
        }

        @Test
        void height() {
            options.setHeight(123);
            assertEquals(123, options.getHeight());
        }

        @Test
        void width() {
            options.setWidth(456);
            assertEquals(456, options.getWidth());
        }

        @Test
        void position() {
            options.setPosition(DJChartOptions.POSITION_HEADER);
            assertEquals(DJChartOptions.POSITION_HEADER, options.getPosition());
        }

        @Test
        void showLabels() {
            options.setShowLabels(false);
            assertFalse(options.isShowLabels());
        }

        @Test
        void showLegend() {
            options.setShowLegend(false);
            assertFalse(options.isShowLegend());
        }

        @Test
        void x() {
            options.setX(77);
            assertEquals(77, options.getX());
        }

        @Test
        void y() {
            options.setY(88);
            assertEquals(88, options.getY());
        }

        @Test
        void border() {
            options.setBorder((byte) 3);
            assertEquals((byte) 3, options.getBorder());
        }

        @Test
        void colors() {
            List colors = new ArrayList();
            colors.add(Color.GREEN);
            options.setColors(colors);
            assertSame(colors, options.getColors());
        }

        @Test
        void useColumnsAsCategorie() {
            options.setUseColumnsAsCategorie(true);
            assertTrue(options.isUseColumnsAsCategorie());
        }
    }

    @Nested
    @DisplayName("Constants")
    class Constants {

        @Test
        void positionConstants() {
            assertEquals((byte) 1, DJChartOptions.POSITION_FOOTER);
            assertEquals((byte) 2, DJChartOptions.POSITION_HEADER);
        }
    }
}
