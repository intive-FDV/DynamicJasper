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

import net.sf.jasperreports.engine.JRPrintElement;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.fill.JRTemplatePrintText;

import java.util.ArrayList;
import java.util.List;

/**
 * Utility class for extracting text content from JasperPrint objects.
 * Provides methods to extract all text, column headers, data rows, and other
 * report content for use in test assertions.
 *
 * <p>This utility is designed to enable content-based test validation that is
 * resilient to layout changes across JasperReports versions. Instead of validating
 * element positions (which can change), tests can validate that expected content
 * appears in the report.</p>
 *
 * <p>Example usage:</p>
 * <pre>
 * // Extract all text from report
 * List&lt;String&gt; allText = ReportContentExtractor.extractAllText(jasperPrint);
 *
 * // Count occurrences of specific text
 * int count = Collections.frequency(allText, "Total Amount");
 * Assert.assertEquals(4, count);  // Verify appears 4 times
 * </pre>
 *
 * @see ReportAssertions
 * @since 5.3.10
 */
public class ReportContentExtractor {

    /**
     * Extract all text content from all pages in the JasperPrint.
     * Recursively searches through all print elements, including nested frames,
     * to find all text elements.
     *
     * <p>This method handles:</p>
     * <ul>
     *   <li>Simple text elements (JRTemplatePrintText)</li>
     *   <li>Nested frames containing text elements</li>
     *   <li>Multiple pages</li>
     *   <li>All report bands (header, detail, footer, groups)</li>
     * </ul>
     *
     * @param jp The JasperPrint to extract text from
     * @return List of all text content found in the report, in the order encountered
     * @throws IllegalArgumentException if jp is null
     */
    public static List<String> extractAllText(JasperPrint jp) {
        if (jp == null) {
            throw new IllegalArgumentException("JasperPrint cannot be null");
        }

        List<String> texts = new ArrayList<>();
        for (net.sf.jasperreports.engine.JRPrintPage page : jp.getPages()) {
            extractTextFromElements(page.getElements(), texts);
        }
        return texts;
    }

    /**
     * Recursively extract text from a list of print elements.
     * Handles nested elements (e.g., frames containing text elements).
     *
     * <p>This method is recursive and will traverse the entire element tree,
     * extracting text from all JRTemplatePrintText elements found at any depth.</p>
     *
     * @param elements List of print elements to search
     * @param texts List to accumulate found text content
     */
    public static void extractTextFromElements(List<JRPrintElement> elements, List<String> texts) {
        if (elements == null || texts == null) {
            return;
        }

        for (JRPrintElement element : elements) {
            if (element instanceof JRTemplatePrintText) {
                // Extract text from text element
                String text = ((JRTemplatePrintText) element).getFullText();
                if (text != null) {
                    texts.add(text);
                }
            } else if (element instanceof net.sf.jasperreports.engine.fill.JRTemplatePrintFrame) {
                // Recursively search frames for nested text elements
                net.sf.jasperreports.engine.fill.JRTemplatePrintFrame frame =
                    (net.sf.jasperreports.engine.fill.JRTemplatePrintFrame) element;
                extractTextFromElements(frame.getElements(), texts);
            }
        }
    }

    /**
     * Extract column headers from the first page of the report.
     * Attempts to identify column headers by looking for text elements in the
     * column header band.
     *
     * <p>Note: This is a best-effort method. Header detection may not work for
     * all report types, especially those with complex header structures.</p>
     *
     * @param jp The JasperPrint to extract headers from
     * @return List of column header texts, or empty list if none found
     * @throws IllegalArgumentException if jp is null or has no pages
     */
    public static List<String> extractColumnHeaders(JasperPrint jp) {
        if (jp == null) {
            throw new IllegalArgumentException("JasperPrint cannot be null");
        }
        if (jp.getPages() == null || jp.getPages().isEmpty()) {
            throw new IllegalArgumentException("JasperPrint must have at least one page");
        }

        List<String> headers = new ArrayList<>();
        // For simplicity, extract all text from first page and filter
        // This is a simplified implementation - more sophisticated header detection
        // could be added based on element Y positions
        List<JRPrintElement> firstPageElements = jp.getPages().get(0).getElements();
        if (firstPageElements != null) {
            extractTextFromElements(firstPageElements, headers);
        }
        return headers;
    }

    /**
     * Count the number of pages in the report.
     *
     * @param jp The JasperPrint to count pages from
     * @return Number of pages in the report
     * @throws IllegalArgumentException if jp is null
     */
    public static int getPageCount(JasperPrint jp) {
        if (jp == null) {
            throw new IllegalArgumentException("JasperPrint cannot be null");
        }
        return jp.getPages() != null ? jp.getPages().size() : 0;
    }

    /**
     * Check if the report contains any pages.
     *
     * @param jp The JasperPrint to check
     * @return true if report has at least one page, false otherwise
     * @throws IllegalArgumentException if jp is null
     */
    public static boolean hasPages(JasperPrint jp) {
        return getPageCount(jp) > 0;
    }

    /**
     * Check if the report contains any text content.
     *
     * @param jp The JasperPrint to check
     * @return true if report contains at least one text element, false otherwise
     * @throws IllegalArgumentException if jp is null
     */
    public static boolean hasText(JasperPrint jp) {
        return !extractAllText(jp).isEmpty();
    }
}
