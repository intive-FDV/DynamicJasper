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
package ar.com.fdvs.dj.domain.builders;

import ar.com.fdvs.dj.domain.Style;
import ar.com.fdvs.dj.domain.constants.Border;
import ar.com.fdvs.dj.domain.constants.Font;
import ar.com.fdvs.dj.domain.constants.HorizontalTextAlign;
import ar.com.fdvs.dj.domain.constants.Rotation;
import ar.com.fdvs.dj.domain.constants.Transparency;
import ar.com.fdvs.dj.domain.constants.VerticalTextAlign;
import net.sf.jasperreports.engine.type.StretchTypeEnum;
import net.sf.jasperreports.engine.type.TextAdjustEnum;
import org.junit.Test;

import java.awt.Color;

import static org.junit.Assert.*;

/**
 * Unit tests for StyleBuilder.
 * Tests style construction with various properties.
 */
public class StyleBuilderTest {

    @Test
    public void testBuildDefaultStyle() {
        Style style = new StyleBuilder(false).build();

        assertNotNull("Style should not be null", style);
    }

    @Test
    public void testBuildBlankStyle() {
        Style style = new StyleBuilder(true).build();

        assertNotNull("Style should not be null", style);
    }

    @Test
    public void testBuildWithName() {
        Style style = new StyleBuilder(false, "myStyle").build();

        assertEquals("Name should match", "myStyle", style.getName());
    }

    @Test
    public void testBuildWithNameAndParent() {
        Style style = new StyleBuilder(false, "child", "parent").build();

        assertEquals("Name should match", "child", style.getName());
        assertEquals("Parent name should match", "parent", style.getParentStyleName());
    }

    @Test
    public void testSetName() {
        Style style = new StyleBuilder(false)
                .setName("testStyle")
                .build();

        assertEquals("Name should match", "testStyle", style.getName());
    }

    @Test
    public void testSetPattern() {
        Style style = new StyleBuilder(false)
                .setPattern("#,##0.00")
                .build();

        assertEquals("Pattern should match", "#,##0.00", style.getPattern());
    }

    @Test
    public void testSetFont() {
        Font font = new Font();
        font.setFontName("Arial");
        font.setFontSize(12);

        Style style = new StyleBuilder(false)
                .setFont(font)
                .build();

        // Font is cloned internally, so check properties
        assertNotNull("Font should not be null", style.getFont());
        assertEquals("Font name should match", "Arial", style.getFont().getFontName());
        assertEquals("Font size should match", 12, style.getFont().getFontSize(), 0.01);
    }

    @Test
    public void testSetHorizontalTextAlign() {
        Style style = new StyleBuilder(false)
                .setHorizontalTextAlign(HorizontalTextAlign.CENTER)
                .build();

        assertEquals("Horizontal align should match",
                HorizontalTextAlign.CENTER, style.getHorizontalTextAlign());
    }

    @Test
    public void testSetVerticalTextAlign() {
        Style style = new StyleBuilder(false)
                .setVerticalTextAlign(VerticalTextAlign.MIDDLE)
                .build();

        assertEquals("Vertical align should match",
                VerticalTextAlign.MIDDLE, style.getVerticalTextAlign());
    }

    @Test
    public void testSetTextColor() {
        Style style = new StyleBuilder(false)
                .setTextColor(Color.RED)
                .build();

        assertEquals("Text color should match", Color.RED, style.getTextColor());
    }

    @Test
    public void testSetBackgroundColor() {
        Style style = new StyleBuilder(false)
                .setBackgroundColor(Color.YELLOW)
                .build();

        assertEquals("Background color should match", Color.YELLOW, style.getBackgroundColor());
    }

    @Test
    public void testSetTransparency() {
        Style style = new StyleBuilder(false)
                .setTransparency(Transparency.OPAQUE)
                .build();

        assertEquals("Transparency should match", Transparency.OPAQUE, style.getTransparency());
    }

    @Test
    public void testSetTransparent() {
        Style style = new StyleBuilder(false)
                .setTransparent(true)
                .build();

        assertTrue("Should be transparent", style.isTransparent());
    }

    @Test
    public void testSetBorder() {
        Border border = Border.PEN_1_POINT();

        Style style = new StyleBuilder(false)
                .setBorder(border)
                .build();

        assertSame("Border should match", border, style.getBorder());
    }

    @Test
    public void testSetBorderTop() {
        Border border = Border.PEN_2_POINT();

        Style style = new StyleBuilder(false)
                .setBorderTop(border)
                .build();

        assertSame("Border top should match", border, style.getBorderTop());
    }

    @Test
    public void testSetBorderBottom() {
        Border border = Border.PEN_2_POINT();

        Style style = new StyleBuilder(false)
                .setBorderBottom(border)
                .build();

        assertSame("Border bottom should match", border, style.getBorderBottom());
    }

    @Test
    public void testSetBorderLeft() {
        Border border = Border.PEN_2_POINT();

        Style style = new StyleBuilder(false)
                .setBorderLeft(border)
                .build();

        assertSame("Border left should match", border, style.getBorderLeft());
    }

    @Test
    public void testSetBorderRight() {
        Border border = Border.PEN_2_POINT();

        Style style = new StyleBuilder(false)
                .setBorderRight(border)
                .build();

        assertSame("Border right should match", border, style.getBorderRight());
    }

    @Test
    public void testSetBorderColor() {
        Style style = new StyleBuilder(false)
                .setBorder(Border.PEN_1_POINT())
                .setBorderColor(Color.BLUE)
                .build();

        assertEquals("Border color should match", Color.BLUE, style.getBorder().getColor());
    }

    @Test
    public void testSetBorderColorWithoutBorder() {
        Style style = new StyleBuilder(false)
                .setBorderColor(Color.BLUE)
                .build();

        // Should not throw, just return
        assertNotNull("Style should not be null", style);
    }

    @Test
    public void testSetPadding() {
        Style style = new StyleBuilder(false)
                .setPadding(10)
                .build();

        assertEquals("Padding should match", Integer.valueOf(10), style.getPadding());
    }

    @Test
    public void testSetPaddingTop() {
        Style style = new StyleBuilder(false)
                .setPaddingTop(5)
                .build();

        assertEquals("Padding top should match", Integer.valueOf(5), style.getPaddingTop());
    }

    @Test
    public void testSetPaddingBottom() {
        Style style = new StyleBuilder(false)
                .setPaddingBottom(5)
                .build();

        assertEquals("Padding bottom should match", Integer.valueOf(5), style.getPaddingBottom());
    }

    @Test
    public void testSetPaddingLeft() {
        Style style = new StyleBuilder(false)
                .setPaddingLeft(5)
                .build();

        assertEquals("Padding left should match", Integer.valueOf(5), style.getPaddingLeft());
    }

    @Test
    public void testSetPaddingRight() {
        Style style = new StyleBuilder(false)
                .setPaddingRight(5)
                .build();

        assertEquals("Padding right should match", Integer.valueOf(5), style.getPaddingRight());
    }

    @Test
    public void testSetParentStyleName() {
        Style style = new StyleBuilder(false)
                .setParentStyleName("parentStyle")
                .build();

        assertEquals("Parent style name should match", "parentStyle", style.getParentStyleName());
    }

    @Test
    public void testSetRotation() {
        Style style = new StyleBuilder(false)
                .setRotation(Rotation.LEFT)
                .build();

        assertEquals("Rotation should match", Rotation.LEFT, style.getRotation());
    }

    @Test
    public void testSetStretchType() {
        Style style = new StyleBuilder(false)
                .setStretchType(StretchTypeEnum.ELEMENT_GROUP_HEIGHT)
                .build();

        assertEquals("Stretch type should match",
                StretchTypeEnum.ELEMENT_GROUP_HEIGHT, style.getStretchType());
    }

    @Test
    public void testSetTextAdjust() {
        Style style = new StyleBuilder(false)
                .setTextAdjust(TextAdjustEnum.STRETCH_HEIGHT)
                .build();

        assertEquals("Text adjust should match",
                TextAdjustEnum.STRETCH_HEIGHT, style.getTextAdjust());
    }

    @Test
    public void testFluentApi() {
        Font customFont = new Font();
        customFont.setFontName("Helvetica");

        Style style = new StyleBuilder(false)
                .setName("complete")
                .setFont(customFont)
                .setTextColor(Color.BLACK)
                .setBackgroundColor(Color.WHITE)
                .setHorizontalTextAlign(HorizontalTextAlign.LEFT)
                .setVerticalTextAlign(VerticalTextAlign.TOP)
                .setPadding(5)
                .setBorder(Border.THIN())
                .build();

        assertEquals("Name should match", "complete", style.getName());
        // Font is cloned, check properties
        assertEquals("Font name should match", "Helvetica", style.getFont().getFontName());
        assertEquals("Text color should match", Color.BLACK, style.getTextColor());
        assertEquals("Background color should match", Color.WHITE, style.getBackgroundColor());
    }
}
