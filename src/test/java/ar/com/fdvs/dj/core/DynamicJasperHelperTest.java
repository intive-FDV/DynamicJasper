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
package ar.com.fdvs.dj.core;

import ar.com.fdvs.dj.core.layout.ClassicLayoutManager;
import ar.com.fdvs.dj.domain.DynamicReport;
import ar.com.fdvs.dj.domain.builders.ColumnBuilder;
import ar.com.fdvs.dj.domain.builders.DynamicReportBuilder;
import ar.com.fdvs.dj.domain.entities.columns.AbstractColumn;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * Unit tests for DynamicJasperHelper.
 * Tests report generation and compilation methods.
 */
public class DynamicJasperHelperTest {

    private DynamicReport simpleReport;
    private List<TestBean> testData;

    public static class TestBean {
        private String name;
        private Integer quantity;
        private Float amount;

        public TestBean(String name, Integer quantity, Float amount) {
            this.name = name;
            this.quantity = quantity;
            this.amount = amount;
        }

        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        public Integer getQuantity() { return quantity; }
        public void setQuantity(Integer quantity) { this.quantity = quantity; }
        public Float getAmount() { return amount; }
        public void setAmount(Float amount) { this.amount = amount; }
    }

    @Before
    public void setUp() throws Exception {
        AbstractColumn nameCol = ColumnBuilder.getNew()
                .setColumnProperty("name", String.class.getName())
                .setTitle("Name")
                .setWidth(100)
                .build();

        AbstractColumn qtyCol = ColumnBuilder.getNew()
                .setColumnProperty("quantity", Integer.class.getName())
                .setTitle("Qty")
                .setWidth(50)
                .build();

        AbstractColumn amtCol = ColumnBuilder.getNew()
                .setColumnProperty("amount", Float.class.getName())
                .setTitle("Amount")
                .setWidth(80)
                .build();

        simpleReport = new DynamicReportBuilder()
                .setTitle("Test Report")
                .addColumn(nameCol)
                .addColumn(qtyCol)
                .addColumn(amtCol)
                .setUseFullPageWidth(true)
                .build();

        testData = new ArrayList<>();
        testData.add(new TestBean("Product A", 10, 100.0f));
        testData.add(new TestBean("Product B", 20, 200.0f));
        testData.add(new TestBean("Product C", 30, 300.0f));
    }

    @Test
    public void testGenerateJasperReport() throws JRException {
        JasperReport report = DynamicJasperHelper.generateJasperReport(
                simpleReport, new ClassicLayoutManager(), new HashMap<>());

        assertNotNull("Report should not be null", report);
        assertNotNull("Report name should be set", report.getName());
    }

    @Test
    public void testGenerateJasperReportWithEmptyParams() throws JRException {
        JasperReport report = DynamicJasperHelper.generateJasperReport(
                simpleReport, new ClassicLayoutManager(), new HashMap<>());

        assertNotNull("Report should not be null", report);
    }

    @Test
    public void testGenerateJasperPrintWithCollection() throws JRException {
        JasperPrint print = DynamicJasperHelper.generateJasperPrint(
                simpleReport, new ClassicLayoutManager(),
                new JRBeanCollectionDataSource(testData));

        assertNotNull("JasperPrint should not be null", print);
        assertTrue("Should have at least one page", print.getPages().size() >= 1);
    }

    @Test
    public void testGenerateJasperPrintWithCollectionAndParams() throws JRException {
        Map<String, Object> params = new HashMap<>();
        params.put("customParam", "value");

        JasperPrint print = DynamicJasperHelper.generateJasperPrint(
                simpleReport, new ClassicLayoutManager(),
                new JRBeanCollectionDataSource(testData), params);

        assertNotNull("JasperPrint should not be null", print);
    }

    @Test
    public void testGenerateJasperPrintWithEmptyCollection() throws JRException {
        JasperPrint print = DynamicJasperHelper.generateJasperPrint(
                simpleReport, new ClassicLayoutManager(),
                new JRBeanCollectionDataSource(new ArrayList<>()));

        assertNotNull("JasperPrint should not be null", print);
    }

    @Test
    public void testDefaultXmlEncoding() {
        assertEquals("Default encoding should be UTF-8",
                "UTF-8", DynamicJasperHelper.DEFAULT_XML_ENCODING);
    }

    @Test
    public void testGenerateJasperPrintDirectCollection() throws JRException {
        JasperPrint print = DynamicJasperHelper.generateJasperPrint(
                simpleReport, new ClassicLayoutManager(), testData);

        assertNotNull("JasperPrint should not be null", print);
        assertTrue("Should have pages", print.getPages().size() >= 1);
    }

    @Test
    public void testGenerateJasperPrintDirectCollectionAgain() throws JRException {
        JasperPrint print = DynamicJasperHelper.generateJasperPrint(
                simpleReport, new ClassicLayoutManager(), testData);

        assertNotNull("JasperPrint should not be null", print);
    }

    @Test
    public void testReportWithTitle() throws JRException {
        DynamicReport report = new DynamicReportBuilder()
                .setTitle("My Report Title")
                .setSubtitle("Subtitle Here")
                .addColumn(ColumnBuilder.getNew()
                        .setColumnProperty("name", String.class.getName())
                        .setTitle("Name")
                        .build())
                .build();

        JasperPrint print = DynamicJasperHelper.generateJasperPrint(
                report, new ClassicLayoutManager(), testData);

        assertNotNull("JasperPrint should not be null", print);
    }

    @Test
    public void testReportGenerationMultipleTimes() throws JRException {
        // Verify report can be generated multiple times
        JasperReport report1 = DynamicJasperHelper.generateJasperReport(
                simpleReport, new ClassicLayoutManager(), new HashMap<>());
        JasperReport report2 = DynamicJasperHelper.generateJasperReport(
                simpleReport, new ClassicLayoutManager(), new HashMap<>());

        assertNotNull("First report should not be null", report1);
        assertNotNull("Second report should not be null", report2);
    }

    @Test
    public void testReportWithDifferentDataSets() throws JRException {
        List<TestBean> data1 = new ArrayList<>();
        data1.add(new TestBean("A", 1, 10.0f));

        List<TestBean> data2 = new ArrayList<>();
        data2.add(new TestBean("B", 2, 20.0f));
        data2.add(new TestBean("C", 3, 30.0f));

        JasperPrint print1 = DynamicJasperHelper.generateJasperPrint(
                simpleReport, new ClassicLayoutManager(), data1);
        JasperPrint print2 = DynamicJasperHelper.generateJasperPrint(
                simpleReport, new ClassicLayoutManager(), data2);

        assertNotNull("First print should not be null", print1);
        assertNotNull("Second print should not be null", print2);
    }
}
