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
package ar.com.fdvs.dj.output;

import ar.com.fdvs.dj.core.DJConstants;
import ar.com.fdvs.dj.core.layout.ClassicLayoutManager;
import ar.com.fdvs.dj.core.layout.LayoutManager;
import ar.com.fdvs.dj.core.layout.ListLayoutManager;
import net.sf.jasperreports.engine.JRExporter;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Unit tests for FormatInfoRegistry.
 * Tests format registration, content types, and exporter/layout manager instantiation.
 */
public class FormatInfoRegistryTest {

    @Test
    public void testGetInstanceReturnsSingleton() {
        FormatInfoRegistry instance1 = FormatInfoRegistry.getInstance();
        FormatInfoRegistry instance2 = FormatInfoRegistry.getInstance();

        assertSame("Should return same instance", instance1, instance2);
    }

    @Test
    public void testGetContentTypePdf() {
        String contentType = FormatInfoRegistry.getInstance().getContentType(DJConstants.FORMAT_PDF);

        assertEquals("PDF content type", "application/pdf", contentType);
    }

    @Test
    public void testGetContentTypeHtml() {
        String contentType = FormatInfoRegistry.getInstance().getContentType(DJConstants.FORMAT_HTML);

        assertEquals("HTML content type", "text/html", contentType);
    }

    @Test
    public void testGetContentTypeCsv() {
        String contentType = FormatInfoRegistry.getInstance().getContentType(DJConstants.FORMAT_CSV);

        assertEquals("CSV content type", "text/plain", contentType);
    }

    @Test
    public void testGetContentTypeXls() {
        String contentType = FormatInfoRegistry.getInstance().getContentType(DJConstants.FORMAT_XLS);

        assertEquals("XLS content type", "application/vnd.ms-excel", contentType);
    }

    @Test
    public void testGetContentTypeXml() {
        String contentType = FormatInfoRegistry.getInstance().getContentType(DJConstants.FORMAT_XML);

        assertEquals("XML content type", "text/xml", contentType);
    }

    @Test
    public void testGetContentTypeRtf() {
        String contentType = FormatInfoRegistry.getInstance().getContentType(DJConstants.FORMAT_RTF);

        assertEquals("RTF content type", "application/rtf", contentType);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetContentTypeInvalidFormatThrows() {
        FormatInfoRegistry.getInstance().getContentType("INVALID_FORMAT");
    }

    @Test
    public void testGetExporterPdf() {
        JRExporter exporter = FormatInfoRegistry.getInstance().getExporter(DJConstants.FORMAT_PDF);

        assertNotNull("PDF exporter should not be null", exporter);
    }

    @Test
    public void testGetExporterHtml() {
        JRExporter exporter = FormatInfoRegistry.getInstance().getExporter(DJConstants.FORMAT_HTML);

        assertNotNull("HTML exporter should not be null", exporter);
    }

    @Test
    public void testGetExporterCsv() {
        JRExporter exporter = FormatInfoRegistry.getInstance().getExporter(DJConstants.FORMAT_CSV);

        assertNotNull("CSV exporter should not be null", exporter);
    }

    @Test
    public void testGetExporterXls() {
        JRExporter exporter = FormatInfoRegistry.getInstance().getExporter(DJConstants.FORMAT_XLS);

        assertNotNull("XLS exporter should not be null", exporter);
    }

    @Test
    public void testGetExporterXml() {
        JRExporter exporter = FormatInfoRegistry.getInstance().getExporter(DJConstants.FORMAT_XML);

        assertNotNull("XML exporter should not be null", exporter);
    }

    @Test
    public void testGetExporterRtf() {
        JRExporter exporter = FormatInfoRegistry.getInstance().getExporter(DJConstants.FORMAT_RTF);

        assertNotNull("RTF exporter should not be null", exporter);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetExporterInvalidFormatThrows() {
        FormatInfoRegistry.getInstance().getExporter("INVALID_FORMAT");
    }

    @Test
    public void testGetLayoutManagerPdf() {
        LayoutManager layoutManager = FormatInfoRegistry.getInstance().getLayoutManager(DJConstants.FORMAT_PDF);

        assertNotNull("PDF layout manager should not be null", layoutManager);
        assertTrue("PDF should use ClassicLayoutManager", layoutManager instanceof ClassicLayoutManager);
    }

    @Test
    public void testGetLayoutManagerHtml() {
        LayoutManager layoutManager = FormatInfoRegistry.getInstance().getLayoutManager(DJConstants.FORMAT_HTML);

        assertNotNull("HTML layout manager should not be null", layoutManager);
        assertTrue("HTML should use ClassicLayoutManager", layoutManager instanceof ClassicLayoutManager);
    }

    @Test
    public void testGetLayoutManagerXls() {
        LayoutManager layoutManager = FormatInfoRegistry.getInstance().getLayoutManager(DJConstants.FORMAT_XLS);

        assertNotNull("XLS layout manager should not be null", layoutManager);
        assertTrue("XLS should use ListLayoutManager", layoutManager instanceof ListLayoutManager);
    }

    @Test
    public void testGetLayoutManagerCsv() {
        LayoutManager layoutManager = FormatInfoRegistry.getInstance().getLayoutManager(DJConstants.FORMAT_CSV);

        assertNotNull("CSV layout manager should not be null", layoutManager);
        assertTrue("CSV should use ClassicLayoutManager", layoutManager instanceof ClassicLayoutManager);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetLayoutManagerInvalidFormatThrows() {
        FormatInfoRegistry.getInstance().getLayoutManager("INVALID_FORMAT");
    }

    // testGetXlsConfiguration (SRC) omitted: it exercises
    // FormatInfoRegistry.getXlsConfiguration(), a method that only exists on
    // the JR7 branch (backed by JR7's SimpleXlsReportConfiguration). On this
    // JR 6.21.5 branch, FormatInfoRegistry still configures XLS export via
    // the older JRXlsExporterParameter API and has no equivalent method, so
    // there is nothing in main to test here.
}
