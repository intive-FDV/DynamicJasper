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

import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.export.Exporter;
import net.sf.jasperreports.pdf.JRPdfExporter;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Unit tests for ReportWriter base class functionality.
 * Tests common functionality via MemoryReportWriter concrete implementation.
 */
public class ReportWriterTest {

    @Test
    public void testGetExporterReturnsExporter() {
        JasperPrint jasperPrint = createEmptyJasperPrint();
        Exporter exporter = new JRPdfExporter();

        MemoryReportWriter writer = new MemoryReportWriter(jasperPrint, exporter);

        assertSame("getExporter should return the exporter", exporter, writer.getExporter());
    }

    @Test
    public void testCopyStreamsBasicContent() throws IOException {
        String testContent = "Hello, World!";
        InputStream input = new ByteArrayInputStream(testContent.getBytes());
        ByteArrayOutputStream output = new ByteArrayOutputStream();

        ReportWriter.copyStreams(input, output);

        assertEquals("Content should be copied", testContent, output.toString());
    }

    @Test
    public void testCopyStreamsEmptyStream() throws IOException {
        InputStream input = new ByteArrayInputStream(new byte[0]);
        ByteArrayOutputStream output = new ByteArrayOutputStream();

        ReportWriter.copyStreams(input, output);

        assertEquals("Empty stream should produce empty output", 0, output.size());
    }

    @Test
    public void testCopyStreamsLargeContent() throws IOException {
        // Create content larger than buffer size (10 * 1024)
        byte[] largeContent = new byte[15 * 1024];
        for (int i = 0; i < largeContent.length; i++) {
            largeContent[i] = (byte) (i % 256);
        }

        InputStream input = new ByteArrayInputStream(largeContent);
        ByteArrayOutputStream output = new ByteArrayOutputStream();

        ReportWriter.copyStreams(input, output);

        assertArrayEquals("Large content should be copied correctly", largeContent, output.toByteArray());
    }

    @Test
    public void testCopyStreamsClosesStreams() throws IOException {
        final boolean[] closed = {false, false};

        InputStream input = new ByteArrayInputStream("test".getBytes()) {
            @Override
            public void close() throws IOException {
                super.close();
                closed[0] = true;
            }
        };

        ByteArrayOutputStream output = new ByteArrayOutputStream() {
            @Override
            public void close() throws IOException {
                super.close();
                closed[1] = true;
            }
        };

        ReportWriter.copyStreams(input, output);

        assertTrue("Input stream should be closed", closed[0]);
        assertTrue("Output stream should be closed", closed[1]);
    }

    @Test
    public void testMemoryReportWriterConstruction() {
        JasperPrint jasperPrint = createEmptyJasperPrint();
        Exporter exporter = new JRPdfExporter();

        MemoryReportWriter writer = new MemoryReportWriter(jasperPrint, exporter);

        assertNotNull("Writer should be created", writer);
        assertSame("Exporter should match", exporter, writer.getExporter());
    }

    @Test
    public void testFileReportWriterConstruction() {
        JasperPrint jasperPrint = createEmptyJasperPrint();
        Exporter exporter = new JRPdfExporter();

        FileReportWriter writer = new FileReportWriter(jasperPrint, exporter);

        assertNotNull("Writer should be created", writer);
        assertSame("Exporter should match", exporter, writer.getExporter());
    }

    /**
     * Helper to create empty JasperPrint.
     */
    private JasperPrint createEmptyJasperPrint() {
        JasperPrint jasperPrint = new JasperPrint();
        jasperPrint.setName("TestReport");
        jasperPrint.setPageWidth(612);
        jasperPrint.setPageHeight(792);
        return jasperPrint;
    }
}
