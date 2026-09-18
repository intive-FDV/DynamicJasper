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
package ar.com.fdvs.dj.util;

import ar.com.fdvs.dj.domain.constants.Border;
import net.sf.jasperreports.engine.design.JRDesignBand;
import net.sf.jasperreports.engine.design.JRDesignRectangle;
import net.sf.jasperreports.engine.design.JRDesignSection;
import net.sf.jasperreports.engine.design.JRDesignStyle;
import net.sf.jasperreports.engine.design.JRDesignTextField;
import net.sf.jasperreports.engine.type.LineStyleEnum;
import net.sf.jasperreports.engine.type.SplitTypeEnum;
import org.junit.Test;

import java.awt.*;

import static org.junit.Assert.*;

/**
 * Unit tests for LayoutUtils.
 * Tests layout calculation and band manipulation utilities.
 */
public class LayoutUtilsTest {

    @Test
    public void testFindVerticalOffsetEmptyBand() {
        JRDesignBand band = new JRDesignBand();
        band.setHeight(100);

        int offset = LayoutUtils.findVerticalOffset(band);

        assertEquals("Empty band should have offset 0", 0, offset);
    }

    @Test
    public void testFindVerticalOffsetWithElements() {
        JRDesignBand band = new JRDesignBand();
        band.setHeight(100);

        JRDesignTextField tf1 = new JRDesignTextField();
        tf1.setY(10);
        tf1.setHeight(20);
        band.addElement(tf1);

        JRDesignTextField tf2 = new JRDesignTextField();
        tf2.setY(40);
        tf2.setHeight(25);
        band.addElement(tf2);

        int offset = LayoutUtils.findVerticalOffset(band);

        // tf2 is at Y=40 with height 25, so offset should be 65
        assertEquals("Offset should be at bottom of lowest element", 65, offset);
    }

    @Test
    public void testFindVerticalOffsetNullBand() {
        int offset = LayoutUtils.findVerticalOffset(null);

        assertEquals("Null band should return 0", 0, offset);
    }

    @Test
    public void testMoveBandsElements() {
        JRDesignBand band = new JRDesignBand();

        JRDesignTextField tf = new JRDesignTextField();
        tf.setY(10);
        band.addElement(tf);

        LayoutUtils.moveBandsElemnts(20, band);

        assertEquals("Element should be moved down", 30, tf.getY());
    }

    @Test
    public void testMoveBandsElementsWithMultipleElements() {
        JRDesignBand band = new JRDesignBand();

        JRDesignTextField tf1 = new JRDesignTextField();
        tf1.setY(10);
        band.addElement(tf1);

        JRDesignTextField tf2 = new JRDesignTextField();
        tf2.setY(50);
        band.addElement(tf2);

        LayoutUtils.moveBandsElemnts(15, band);

        assertEquals("First element should be moved", 25, tf1.getY());
        assertEquals("Second element should be moved", 65, tf2.getY());
    }

    @Test
    public void testMoveBandsElementsNullBand() {
        // Should not throw
        LayoutUtils.moveBandsElemnts(20, null);
    }

    @Test
    public void testCopyBandElements() {
        JRDesignBand source = new JRDesignBand();
        source.setHeight(50);

        JRDesignTextField tf = new JRDesignTextField();
        tf.setY(10);
        tf.setHeight(20);
        tf.setX(5);
        tf.setWidth(100);
        source.addElement(tf);

        JRDesignBand dest = new JRDesignBand();
        dest.setHeight(100);

        LayoutUtils.copyBandElements(dest, source);

        assertEquals("Dest band should have 1 element", 1, dest.getElements().length);
    }

    @Test
    public void testCopyBandElementsWithOffset() {
        JRDesignBand dest = new JRDesignBand();

        JRDesignTextField existingTf = new JRDesignTextField();
        existingTf.setY(0);
        existingTf.setHeight(30);
        dest.addElement(existingTf);

        JRDesignBand source = new JRDesignBand();
        JRDesignTextField newTf = new JRDesignTextField();
        newTf.setY(10);
        newTf.setHeight(20);
        source.addElement(newTf);

        LayoutUtils.copyBandElements(dest, source);

        // New element should be placed below existing content (offset = 30)
        // Original Y was 10, so new Y should be 40
        assertEquals("Dest band should have 2 elements", 2, dest.getElements().length);
    }

    @Test
    public void testCopyBandElementsNullSource() {
        JRDesignBand dest = new JRDesignBand();

        // Should not throw
        LayoutUtils.copyBandElements(dest, null);

        assertEquals("No elements should be added", 0, dest.getElements().length);
    }

    @Test
    public void testGetSplitTypeFromBooleanTrue() {
        SplitTypeEnum splitType = LayoutUtils.getSplitTypeFromBoolean(true);

        assertEquals("True should return IMMEDIATE", SplitTypeEnum.IMMEDIATE, splitType);
    }

    @Test
    public void testGetSplitTypeFromBooleanFalse() {
        SplitTypeEnum splitType = LayoutUtils.getSplitTypeFromBoolean(false);

        assertEquals("False should return PREVENT", SplitTypeEnum.PREVENT, splitType);
    }

    @Test
    public void testConvertBorderToPen() {
        JRDesignRectangle rect = new JRDesignRectangle(new JRDesignStyle().getDefaultStyleProvider());

        Border border = new Border(2.0f, Border.BORDER_STYLE_SOLID, Color.RED);
        LayoutUtils.convertBorderToPen(border, rect.getLinePen());

        assertEquals("Line width should match", 2.0f, rect.getLinePen().getLineWidth(), 0.01);
        assertEquals("Line color should match", Color.RED, rect.getLinePen().getLineColor());
        assertEquals("Line style should be solid", LineStyleEnum.SOLID, rect.getLinePen().getLineStyle());
    }

    @Test
    public void testConvertBorderToPenNullBorder() {
        JRDesignRectangle rect = new JRDesignRectangle(new JRDesignStyle().getDefaultStyleProvider());

        // Should not throw
        LayoutUtils.convertBorderToPen(null, rect.getLinePen());
    }

    @Test
    public void testConvertBorderToPenDashed() {
        JRDesignRectangle rect = new JRDesignRectangle(new JRDesignStyle().getDefaultStyleProvider());

        Border border = new Border(1.0f, Border.BORDER_STYLE_DASHED, Color.BLUE);
        LayoutUtils.convertBorderToPen(border, rect.getLinePen());

        assertEquals("Line style should be dashed", LineStyleEnum.DASHED, rect.getLinePen().getLineStyle());
    }

    @Test
    public void testGetBandFromSection() {
        JRDesignSection section = new JRDesignSection() {};
        JRDesignBand band = new JRDesignBand();
        band.setHeight(50);
        section.addBand(band);

        JRDesignBand result = LayoutUtils.getBandFromSection(section);

        assertSame("Should return first band", band, result);
    }
}
