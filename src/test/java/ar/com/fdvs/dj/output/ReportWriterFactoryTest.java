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
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.design.JRDesignBand;
import net.sf.jasperreports.engine.type.OrientationEnum;
import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.*;

/**
 * Unit tests for ReportWriterFactory.
 * Tests factory selection between Memory and File report writers.
 */
public class ReportWriterFactoryTest {

    @Test
    public void testGetInstanceReturnsSingleton() {
        ReportWriterFactory instance1 = ReportWriterFactory.getInstance();
        ReportWriterFactory instance2 = ReportWriterFactory.getInstance();

        assertSame("Should return same instance", instance1, instance2);
    }

    @Test
    public void testBuildCreatesNewInstance() {
        ReportWriterFactory factory1 = ReportWriterFactory.build(5);
        ReportWriterFactory factory2 = ReportWriterFactory.build(10);

        assertNotSame("build() should create new instances", factory1, factory2);
    }

    @Test
    public void testDefaultConstructor() {
        ReportWriterFactory factory = new ReportWriterFactory();

        assertNotNull("Factory should be created", factory);
    }

    @Test
    public void testConstructorWithThreshold() {
        ReportWriterFactory factory = new ReportWriterFactory(5);

        assertNotNull("Factory should be created with threshold", factory);
    }

    @Test
    public void testConstructorWithNegativeThresholdUsesDefault() {
        ReportWriterFactory factory = new ReportWriterFactory(-1);

        assertNotNull("Factory should be created even with negative threshold", factory);
    }

    @Test
    public void testGetReportWriterSmallReportUsesMemory() {
        JasperPrint jasperPrint = createJasperPrintWithPages(1);
        ReportWriterFactory factory = new ReportWriterFactory(2);

        ReportWriter writer = factory.getReportWriter(jasperPrint, DJConstants.FORMAT_PDF, new HashMap<>());

        assertNotNull("Writer should not be null", writer);
        assertTrue("Small report should use MemoryReportWriter", writer instanceof MemoryReportWriter);
    }

    @Test
    public void testGetReportWriterLargeReportUsesFile() {
        JasperPrint jasperPrint = createJasperPrintWithPages(5);
        ReportWriterFactory factory = new ReportWriterFactory(2);

        ReportWriter writer = factory.getReportWriter(jasperPrint, DJConstants.FORMAT_PDF, new HashMap<>());

        assertNotNull("Writer should not be null", writer);
        assertTrue("Large report should use FileReportWriter", writer instanceof FileReportWriter);
    }

    @Test
    public void testGetReportWriterExactThresholdUsesMemory() {
        JasperPrint jasperPrint = createJasperPrintWithPages(2);
        ReportWriterFactory factory = new ReportWriterFactory(2);

        ReportWriter writer = factory.getReportWriter(jasperPrint, DJConstants.FORMAT_PDF, new HashMap<>());

        assertNotNull("Writer should not be null", writer);
        assertTrue("Report at threshold should use MemoryReportWriter", writer instanceof MemoryReportWriter);
    }

    @Test
    public void testGetReportWriterZeroThresholdAlwaysUsesFile() {
        JasperPrint jasperPrint = createJasperPrintWithPages(1);
        ReportWriterFactory factory = new ReportWriterFactory(0);

        ReportWriter writer = factory.getReportWriter(jasperPrint, DJConstants.FORMAT_PDF, new HashMap<>());

        assertNotNull("Writer should not be null", writer);
        assertTrue("Zero threshold should use FileReportWriter", writer instanceof FileReportWriter);
    }

    @Test
    public void testGetReportWriterHtml() {
        JasperPrint jasperPrint = createJasperPrintWithPages(1);
        ReportWriterFactory factory = new ReportWriterFactory();

        ReportWriter writer = factory.getReportWriter(jasperPrint, DJConstants.FORMAT_HTML, new HashMap<>());

        assertNotNull("HTML writer should not be null", writer);
    }

    @Test
    public void testGetReportWriterCsv() {
        JasperPrint jasperPrint = createJasperPrintWithPages(1);
        ReportWriterFactory factory = new ReportWriterFactory();

        ReportWriter writer = factory.getReportWriter(jasperPrint, DJConstants.FORMAT_CSV, new HashMap<>());

        assertNotNull("CSV writer should not be null", writer);
    }

    @Test
    public void testGetReportWriterXls() {
        JasperPrint jasperPrint = createJasperPrintWithPages(1);
        ReportWriterFactory factory = new ReportWriterFactory();

        ReportWriter writer = factory.getReportWriter(jasperPrint, DJConstants.FORMAT_XLS, new HashMap<>());

        assertNotNull("XLS writer should not be null", writer);
    }

    @Test
    public void testGetReportWriterNullParametersHandled() {
        JasperPrint jasperPrint = createJasperPrintWithPages(1);
        ReportWriterFactory factory = new ReportWriterFactory();

        ReportWriter writer = factory.getReportWriter(jasperPrint, DJConstants.FORMAT_PDF, null);

        assertNotNull("Writer should work with null parameters", writer);
    }

    /**
     * Helper to create JasperPrint with specified number of pages.
     */
    private JasperPrint createJasperPrintWithPages(int pageCount) {
        JasperPrint jasperPrint = new JasperPrint();
        jasperPrint.setName("TestReport");
        jasperPrint.setPageWidth(612);
        jasperPrint.setPageHeight(792);
        jasperPrint.setOrientation(OrientationEnum.PORTRAIT);

        for (int i = 0; i < pageCount; i++) {
            jasperPrint.addPage(new net.sf.jasperreports.engine.JRPrintPage() {
                @Override
                public java.util.List<net.sf.jasperreports.engine.JRPrintElement> getElements() {
                    return new java.util.ArrayList<>();
                }

                @Override
                public void setElements(java.util.List<net.sf.jasperreports.engine.JRPrintElement> elements) {
                }

                @Override
                public void addElement(net.sf.jasperreports.engine.JRPrintElement element) {
                }
            });
        }

        return jasperPrint;
    }
}
