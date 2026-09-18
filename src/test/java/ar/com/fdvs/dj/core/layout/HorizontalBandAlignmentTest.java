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
 */
package ar.com.fdvs.dj.core.layout;

import net.sf.jasperreports.engine.design.JRDesignBand;
import net.sf.jasperreports.engine.design.JRDesignTextField;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Unit tests for HorizontalBandAlignment.
 * Tests alignment calculations for LEFT, CENTER, and RIGHT positions.
 */
public class HorizontalBandAlignmentTest {

    private JRDesignBand band;
    private JRDesignTextField element;

    private static final int TOTAL_WIDTH = 500;
    private static final int ELEMENT_WIDTH = 100;
    private static final int OFFSET = 10;

    @Before
    public void setUp() {
        band = new JRDesignBand();
        band.setHeight(50);

        element = new JRDesignTextField();
        element.setWidth(ELEMENT_WIDTH);
        element.setHeight(20);
        element.setX(0);
        element.setY(0);
    }

    @Test
    public void testLeftAlignmentPositionsElementAtOffset() {
        HorizontalBandAlignment.LEFT.align(TOTAL_WIDTH, OFFSET, band, element);

        assertEquals("Element X should be offset", OFFSET, element.getX());
        assertEquals("Element should be added to band", 1, band.getElements().length);
    }

    @Test
    public void testLeftAlignmentWithZeroOffset() {
        HorizontalBandAlignment.LEFT.align(TOTAL_WIDTH, 0, band, element);

        assertEquals("Element X should be 0 with no offset", 0, element.getX());
    }

    @Test
    public void testRightAlignmentPositionsElementAtRightEdge() {
        HorizontalBandAlignment.RIGHT.align(TOTAL_WIDTH, OFFSET, band, element);

        // Expected: totalWidth - elementWidth - offset = 500 - 100 - 10 = 390
        int expectedX = TOTAL_WIDTH - ELEMENT_WIDTH - OFFSET;
        assertEquals("Element X should be at right edge minus offset", expectedX, element.getX());
        assertEquals("Element should be added to band", 1, band.getElements().length);
    }

    @Test
    public void testRightAlignmentWithZeroOffset() {
        HorizontalBandAlignment.RIGHT.align(TOTAL_WIDTH, 0, band, element);

        // Expected: totalWidth - elementWidth = 500 - 100 = 400
        int expectedX = TOTAL_WIDTH - ELEMENT_WIDTH;
        assertEquals("Element X should be at right edge", expectedX, element.getX());
    }

    @Test
    public void testCenterAlignmentPositionsElementInCenter() {
        HorizontalBandAlignment.CENTER.align(TOTAL_WIDTH, OFFSET, band, element);

        // Expected: totalWidth/2 - elementWidth/2 + offset = 250 - 50 + 10 = 210
        int expectedX = TOTAL_WIDTH / 2 - ELEMENT_WIDTH / 2 + OFFSET;
        assertEquals("Element X should be centered with offset", expectedX, element.getX());
        assertEquals("Element should be added to band", 1, band.getElements().length);
    }

    @Test
    public void testCenterAlignmentWithZeroOffset() {
        HorizontalBandAlignment.CENTER.align(TOTAL_WIDTH, 0, band, element);

        // Expected: totalWidth/2 - elementWidth/2 = 250 - 50 = 200
        int expectedX = TOTAL_WIDTH / 2 - ELEMENT_WIDTH / 2;
        assertEquals("Element X should be centered", expectedX, element.getX());
    }

    @Test
    public void testBuildAlignmentReturnsLeft() {
        HorizontalBandAlignment alignment = HorizontalBandAlignment.buildAligment((byte) 1);
        assertSame("Should return LEFT for alignment 1", HorizontalBandAlignment.LEFT, alignment);
    }

    @Test
    public void testBuildAlignmentReturnsCenter() {
        HorizontalBandAlignment alignment = HorizontalBandAlignment.buildAligment((byte) 2);
        assertSame("Should return CENTER for alignment 2", HorizontalBandAlignment.CENTER, alignment);
    }

    @Test
    public void testBuildAlignmentReturnsRight() {
        HorizontalBandAlignment alignment = HorizontalBandAlignment.buildAligment((byte) 3);
        assertSame("Should return RIGHT for alignment 3", HorizontalBandAlignment.RIGHT, alignment);
    }

    @Test
    public void testBuildAlignmentDefaultsToLeft() {
        HorizontalBandAlignment alignment = HorizontalBandAlignment.buildAligment((byte) 99);
        assertSame("Should return LEFT for unknown alignment", HorizontalBandAlignment.LEFT, alignment);
    }

    @Test
    public void testGetAlignmentValues() {
        assertEquals("LEFT alignment value", (byte) 1, HorizontalBandAlignment.LEFT.getAlignment());
        assertEquals("CENTER alignment value", (byte) 2, HorizontalBandAlignment.CENTER.getAlignment());
        assertEquals("RIGHT alignment value", (byte) 3, HorizontalBandAlignment.RIGHT.getAlignment());
    }

    @Test
    public void testAlignmentWithLargeElement() {
        element.setWidth(400);

        HorizontalBandAlignment.RIGHT.align(TOTAL_WIDTH, 0, band, element);

        // Expected: 500 - 400 = 100
        assertEquals("Large element should still be positioned correctly", 100, element.getX());
    }
}
