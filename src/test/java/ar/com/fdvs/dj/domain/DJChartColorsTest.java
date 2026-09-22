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

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.awt.Color;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for DJChartColors.
 */
class DJChartColorsTest {

    @Test
    @DisplayName("simpleColors returns non-empty list")
    void simpleColorsReturnsNonEmptyList() {
        List<Color> colors = DJChartColors.simpleColors();
        assertNotNull(colors);
        assertFalse(colors.isEmpty());
        assertEquals(6, colors.size());
    }

    @Test
    @DisplayName("pale returns non-empty list")
    void paleReturnsNonEmptyList() {
        List<Color> colors = DJChartColors.pale();
        assertNotNull(colors);
        assertFalse(colors.isEmpty());
        assertEquals(6, colors.size());
    }

    @Test
    @DisplayName("msOfficePurples returns non-empty list")
    void msOfficePurplesReturnsNonEmptyList() {
        List<Color> colors = DJChartColors.msOfficePurples();
        assertNotNull(colors);
        assertFalse(colors.isEmpty());
        assertEquals(6, colors.size());
    }

    @Test
    @DisplayName("desert returns non-empty list")
    void desertReturnsNonEmptyList() {
        List<Color> colors = DJChartColors.desert();
        assertNotNull(colors);
        assertFalse(colors.isEmpty());
        assertEquals(6, colors.size());
    }

    @Test
    @DisplayName("justAnotherColorScheme returns non-empty list")
    void justAnotherColorSchemeReturnsNonEmptyList() {
        List<Color> colors = DJChartColors.justAnotherColorScheme();
        assertNotNull(colors);
        assertFalse(colors.isEmpty());
        assertEquals(6, colors.size());
    }

    @Test
    @DisplayName("googleAnalytics returns non-empty list")
    void googleAnalyticsReturnsNonEmptyList() {
        List<Color> colors = DJChartColors.googleAnalytics();
        assertNotNull(colors);
        assertFalse(colors.isEmpty());
        assertEquals(6, colors.size());
    }

    @Test
    @DisplayName("All color methods return different color schemes")
    void colorMethodsReturnDifferentSchemes() {
        List<Color> simple = DJChartColors.simpleColors();
        List<Color> pale = DJChartColors.pale();
        List<Color> desert = DJChartColors.desert();
        List<Color> google = DJChartColors.googleAnalytics();

        // All have same size but different colors
        assertEquals(simple.size(), pale.size());
        assertEquals(simple.size(), desert.size());
        assertEquals(simple.size(), google.size());

        // First color should be different across schemes
        assertNotEquals(simple.get(0), pale.get(0));
        assertNotEquals(simple.get(0), desert.get(0));
    }
}
