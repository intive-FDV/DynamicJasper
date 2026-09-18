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

import ar.com.fdvs.dj.domain.Style;
import net.sf.jasperreports.engine.design.JRDesignStyle;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Unit tests for Utils.
 * Tests collection and string utility methods.
 */
public class UtilsTest {

    @Test
    public void testIsEmptyCollectionNull() {
        assertTrue("Null collection should be empty", Utils.isEmpty((java.util.Collection) null));
    }

    @Test
    public void testIsEmptyCollectionEmpty() {
        assertTrue("Empty list should be empty", Utils.isEmpty(new ArrayList<>()));
    }

    @Test
    public void testIsEmptyCollectionWithElements() {
        List<String> list = new ArrayList<>();
        list.add("item");
        assertFalse("Non-empty list should not be empty", Utils.isEmpty(list));
    }

    @Test
    public void testIsEmptyStringNull() {
        assertTrue("Null string should be empty", Utils.isEmpty((String) null));
    }

    @Test
    public void testIsEmptyStringEmpty() {
        assertTrue("Empty string should be empty", Utils.isEmpty(""));
    }

    @Test
    public void testIsEmptyStringWhitespace() {
        assertTrue("Whitespace string should be empty", Utils.isEmpty("   "));
    }

    @Test
    public void testIsEmptyStringWithContent() {
        assertFalse("String with content should not be empty", Utils.isEmpty("hello"));
    }

    @Test
    public void testStringSetNull() {
        assertFalse("Null string should not be set", Utils.stringSet(null));
    }

    @Test
    public void testStringSetEmpty() {
        assertFalse("Empty string should not be set", Utils.stringSet(""));
    }

    @Test
    public void testStringSetWithContent() {
        assertTrue("String with content should be set", Utils.stringSet("hello"));
    }

    @Test
    public void testStringSetWhitespace() {
        // Note: stringSet returns true for whitespace-only strings
        // (it doesn't trim, unlike isEmpty)
        assertTrue("Whitespace string is considered set", Utils.stringSet("   "));
    }

    @Test
    public void testAddNotNullAddsElement() {
        List<String> list = new ArrayList<>();
        Utils.addNotNull(list, "item");

        assertEquals("Element should be added", 1, list.size());
        assertEquals("Element should match", "item", list.get(0));
    }

    @Test
    public void testAddNotNullIgnoresNull() {
        List<String> list = new ArrayList<>();
        Utils.addNotNull(list, null);

        assertTrue("List should remain empty", list.isEmpty());
    }

    @Test
    public void testAddNotNullIgnoresNullCollection() {
        // Should not throw
        Utils.addNotNull(null, "item");
    }

    @Test
    public void testEscapeTextForExpressionNull() {
        assertNull("Null should return null", Utils.escapeTextForExpression(null));
    }

    @Test
    public void testEscapeTextForExpressionNoEscape() {
        String result = Utils.escapeTextForExpression("Hello World");
        assertEquals("Simple text should not change", "Hello World", result);
    }

    @Test
    public void testEscapeTextForExpressionQuotes() {
        String result = Utils.escapeTextForExpression("Hello \"World\"");

        assertNotNull("Result should not be null", result);
        assertTrue("Quotes should be escaped", result.contains("\\\""));
        assertFalse("Original quotes should be escaped", result.contains("\"World\""));
    }

    @Test
    public void testEscapeTextForExpressionBackslash() {
        String result = Utils.escapeTextForExpression("path\\to\\file");

        assertNotNull("Result should not be null", result);
        assertTrue("Backslashes should be escaped", result.contains("\\\\"));
    }

    @Test
    public void testEscapeTextForExpressionMixed() {
        String result = Utils.escapeTextForExpression("Say \"Hello\" in C:\\temp");

        assertNotNull("Result should not be null", result);
        assertTrue("Quotes should be escaped", result.contains("\\\""));
        assertTrue("Backslashes should be escaped", result.contains("\\\\"));
    }

    @Test
    public void testCloneStyle() {
        JRDesignStyle original = new Style("original").transform();
        original.setBold(true);
        original.setItalic(true);

        JRDesignStyle cloned = Utils.cloneStyle(original);

        assertNotSame("Clone should be different instance", original, cloned);
        assertEquals("Bold should be preserved", original.isBold(), cloned.isBold());
        assertEquals("Italic should be preserved", original.isItalic(), cloned.isItalic());
    }

    @Test
    public void testCopyPropertiesBasic() {
        JRDesignStyle source = new Style("source").transform();
        source.setBold(true);
        source.setItalic(true);
        source.setUnderline(true);

        JRDesignStyle dest = new Style("dest").transform();

        Utils.copyProperties(dest, source);

        assertEquals("Bold should be copied", source.isBold(), dest.isBold());
        assertEquals("Italic should be copied", source.isItalic(), dest.isItalic());
        assertEquals("Underline should be copied", source.isUnderline(), dest.isUnderline());
    }

    @Test
    public void testCopyPropertiesNullSource() {
        JRDesignStyle dest = new Style("dest").transform();

        // Should not throw
        Utils.copyProperties(dest, null);
    }

    @Test
    public void testCopyPropertiesNullDest() {
        JRDesignStyle source = new Style("source").transform();

        // Should not throw
        Utils.copyProperties(null, source);
    }
}
