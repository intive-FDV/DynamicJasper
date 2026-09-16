/*
 * DynamicJasper: A library for creating reports dynamically by specifying
 * columns, groups, styles, etc. at runtime. It also saves a lot of development
 * time in many cases! (http://sourceforge.net/projects/dynamicjasper)
 *
 * Copyright (C) 2008 FDV Solutions (http://www.fdvsolutions.com)
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 *
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 *
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA
 *
 *
 */

package ar.com.fdvs.dj.test.util;

import junit.framework.Assert;
import net.sf.jasperreports.engine.JasperPrint;

import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

/**
 * High-level assertion methods for JasperReport content validation.
 * Provides version-agnostic test assertions that validate report content
 * rather than exact element positions.
 *
 * <p>These assertions enable robust testing across JasperReports versions
 * by focusing on content presence and correctness rather than layout details.</p>
 *
 * <p>Example usage:</p>
 * <pre>
 * // Validate column headers appear
 * ReportAssertions.assertColumnHeaderExists(jp, "State");
 * ReportAssertions.assertColumnHeaderExists(jp, "Amount");
 *
 * // Validate specific text appears exact number of times
 * ReportAssertions.assertTextOccurrences(jp, "Total Amount", 4);
 *
 * // Validate report structure
 * ReportAssertions.assertPageCount(jp, 2);
 * ReportAssertions.assertNotEmpty(jp);
 * </pre>
 *
 * @see ReportContentExtractor
 * @since 5.3.10
 */
public class ReportAssertions {

    /**
     * Assert that a specific text value appears exactly the expected number of times
     * in the generated report. This validation is position-independent and works
     * across JasperReports versions that may have different layout behaviors.
     *
     * <p>This is the fundamental assertion method for content-based testing.
     * It validates that expected content appears the correct number of times
     * without depending on exact element positions or indices.</p>
     *
     * @param jp The JasperPrint to validate
     * @param expected The text value to search for
     * @param count The expected number of occurrences
     * @throws AssertionError if actual count doesn't match expected count
     * @throws IllegalArgumentException if jp or expected is null
     */
    public static void assertTextOccurrences(JasperPrint jp, String expected, int count) {
        if (jp == null) {
            throw new IllegalArgumentException("JasperPrint cannot be null");
        }
        if (expected == null) {
            throw new IllegalArgumentException("Expected text cannot be null");
        }

        List<String> allText = ReportContentExtractor.extractAllText(jp);
        int actualCount = Collections.frequency(allText, expected);
        Assert.assertEquals(
            "Expected '" + expected + "' to appear " + count + " times but found " + actualCount,
            count,
            actualCount
        );
    }

    /**
     * Assert that a column header with the specified text exists in the report.
     * Validates that the header text appears at least once.
     *
     * @param jp The JasperPrint to validate
     * @param headerText The column header text to search for
     * @throws AssertionError if header text is not found
     * @throws IllegalArgumentException if jp or headerText is null
     */
    public static void assertColumnHeaderExists(JasperPrint jp, String headerText) {
        if (jp == null) {
            throw new IllegalArgumentException("JasperPrint cannot be null");
        }
        if (headerText == null) {
            throw new IllegalArgumentException("Header text cannot be null");
        }

        List<String> allText = ReportContentExtractor.extractAllText(jp);
        Assert.assertTrue(
            "Expected column header '" + headerText + "' not found in report",
            allText.contains(headerText)
        );
    }

    /**
     * Assert that column headers appear in the specified order.
     * Validates that each header appears at least once, and that the first
     * occurrence of each header follows the specified order.
     *
     * @param jp The JasperPrint to validate
     * @param headers The expected column headers in order
     * @throws AssertionError if headers are not found or not in order
     * @throws IllegalArgumentException if jp or headers is null/empty
     */
    public static void assertColumnHeadersInOrder(JasperPrint jp, String... headers) {
        if (jp == null) {
            throw new IllegalArgumentException("JasperPrint cannot be null");
        }
        if (headers == null || headers.length == 0) {
            throw new IllegalArgumentException("Headers array cannot be null or empty");
        }

        List<String> allText = ReportContentExtractor.extractAllText(jp);

        int lastIndex = -1;
        for (String header : headers) {
            int index = allText.indexOf(header);
            Assert.assertTrue(
                "Expected column header '" + header + "' not found in report",
                index >= 0
            );
            Assert.assertTrue(
                "Expected column header '" + header + "' to appear after previous headers, but found at index " + index + " (previous was " + lastIndex + ")",
                index > lastIndex
            );
            lastIndex = index;
        }
    }

    /**
     * Assert that the report contains at least the specified number of pages.
     *
     * @param jp The JasperPrint to validate
     * @param expectedPages The minimum expected number of pages
     * @throws AssertionError if page count is less than expected
     * @throws IllegalArgumentException if jp is null or expectedPages is negative
     */
    public static void assertPageCount(JasperPrint jp, int expectedPages) {
        if (jp == null) {
            throw new IllegalArgumentException("JasperPrint cannot be null");
        }
        if (expectedPages < 0) {
            throw new IllegalArgumentException("Expected pages cannot be negative");
        }

        int actualPages = ReportContentExtractor.getPageCount(jp);
        Assert.assertTrue(
            "Expected at least " + expectedPages + " pages but found " + actualPages,
            actualPages >= expectedPages
        );
    }

    /**
     * Assert that the report is not empty (has at least one page with content).
     *
     * @param jp The JasperPrint to validate
     * @throws AssertionError if report is empty
     * @throws IllegalArgumentException if jp is null
     */
    public static void assertNotEmpty(JasperPrint jp) {
        if (jp == null) {
            throw new IllegalArgumentException("JasperPrint cannot be null");
        }

        Assert.assertTrue(
            "Expected report to have pages",
            ReportContentExtractor.hasPages(jp)
        );
        Assert.assertTrue(
            "Expected report to have text content",
            ReportContentExtractor.hasText(jp)
        );
    }

    /**
     * Assert that a text value matches the expected number format pattern.
     * Useful for validating that numbers are formatted correctly (e.g., currency, decimals).
     *
     * @param value The text value to validate
     * @param pattern The regex pattern to match (e.g., "\\$ [0-9,]+\\.[0-9]{2}" for currency)
     * @throws AssertionError if value doesn't match pattern
     * @throws IllegalArgumentException if value or pattern is null
     */
    public static void assertNumberFormat(String value, String pattern) {
        if (value == null) {
            throw new IllegalArgumentException("Value cannot be null");
        }
        if (pattern == null) {
            throw new IllegalArgumentException("Pattern cannot be null");
        }

        Assert.assertTrue(
            "Expected value '" + value + "' to match pattern '" + pattern + "'",
            Pattern.matches(pattern, value)
        );
    }

    /**
     * Assert that a text value is formatted as currency (e.g., "$ 1,234.56").
     * Uses a standard currency format pattern.
     *
     * @param value The text value to validate
     * @throws AssertionError if value is not formatted as currency
     * @throws IllegalArgumentException if value is null
     */
    public static void assertCurrencyFormat(String value) {
        assertNumberFormat(value, "\\$ [0-9,]+\\.[0-9]{2}");
    }

    /**
     * Assert that the report contains at least one text element matching the pattern.
     * Useful for validating that formatted values appear (numbers, dates, etc.).
     *
     * @param jp The JasperPrint to validate
     * @param pattern The regex pattern to search for
     * @throws AssertionError if no matching text is found
     * @throws IllegalArgumentException if jp or pattern is null
     */
    public static void assertTextMatchesPattern(JasperPrint jp, String pattern) {
        if (jp == null) {
            throw new IllegalArgumentException("JasperPrint cannot be null");
        }
        if (pattern == null) {
            throw new IllegalArgumentException("Pattern cannot be null");
        }

        List<String> allText = ReportContentExtractor.extractAllText(jp);
        Pattern p = Pattern.compile(pattern);
        boolean found = allText.stream().anyMatch(text -> p.matcher(text).matches());

        Assert.assertTrue(
            "Expected at least one text element matching pattern '" + pattern + "' but none found",
            found
        );
    }

    /**
     * Assert that the report contains at least the specified number of text
     * elements matching the pattern.
     *
     * @param jp The JasperPrint to validate
     * @param pattern The regex pattern to search for
     * @param minCount The minimum expected count of matching elements
     * @throws AssertionError if count is less than expected
     * @throws IllegalArgumentException if jp or pattern is null, or minCount is negative
     */
    public static void assertTextMatchesPatternCount(JasperPrint jp, String pattern, int minCount) {
        if (jp == null) {
            throw new IllegalArgumentException("JasperPrint cannot be null");
        }
        if (pattern == null) {
            throw new IllegalArgumentException("Pattern cannot be null");
        }
        if (minCount < 0) {
            throw new IllegalArgumentException("Min count cannot be negative");
        }

        List<String> allText = ReportContentExtractor.extractAllText(jp);
        Pattern p = Pattern.compile(pattern);
        long actualCount = allText.stream().filter(text -> p.matcher(text).matches()).count();

        Assert.assertTrue(
            "Expected at least " + minCount + " text elements matching pattern '" + pattern + "' but found " + actualCount,
            actualCount >= minCount
        );
    }

    /**
     * Assert that a specific text value appears at least once in the report.
     * Less strict than assertTextOccurrences - just validates presence.
     *
     * @param jp The JasperPrint to validate
     * @param expected The text value to search for
     * @throws AssertionError if text is not found
     * @throws IllegalArgumentException if jp or expected is null
     */
    public static void assertTextExists(JasperPrint jp, String expected) {
        if (jp == null) {
            throw new IllegalArgumentException("JasperPrint cannot be null");
        }
        if (expected == null) {
            throw new IllegalArgumentException("Expected text cannot be null");
        }

        List<String> allText = ReportContentExtractor.extractAllText(jp);
        Assert.assertTrue(
            "Expected text '" + expected + "' not found in report",
            allText.contains(expected)
        );
    }
}
