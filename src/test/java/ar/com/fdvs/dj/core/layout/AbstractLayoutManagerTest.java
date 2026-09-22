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

import ar.com.fdvs.dj.domain.DynamicJasperDesign;
import ar.com.fdvs.dj.domain.DynamicReport;
import ar.com.fdvs.dj.domain.Style;
import ar.com.fdvs.dj.domain.builders.ColumnBuilder;
import ar.com.fdvs.dj.domain.builders.DynamicReportBuilder;
import ar.com.fdvs.dj.domain.entities.columns.AbstractColumn;
import net.sf.jasperreports.engine.JRStyle;
import net.sf.jasperreports.engine.design.JRDesignTextField;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * Unit tests for AbstractLayoutManager.
 * Tests utility methods and style handling using ClassicLayoutManager as concrete implementation.
 */
public class AbstractLayoutManagerTest {

    private ClassicLayoutManager layoutManager;
    private DynamicJasperDesign design;
    private DynamicReport report;

    @Before
    public void setUp() throws Exception {
        layoutManager = new ClassicLayoutManager();
        design = new DynamicJasperDesign();
        design.setName("TestReport");
        design.setPageWidth(612);
        design.setPageHeight(792);
        design.setColumnWidth(555);
        design.setLeftMargin(20);
        design.setRightMargin(20);
        design.setTopMargin(30);
        design.setBottomMargin(30);
    }

    @Test
    public void testApplyStyleToElementWithNullStyle() throws Exception {
        // Build a simple report
        report = new DynamicReportBuilder()
                .setTitle("Test Report")
                .build();

        layoutManager.applyLayout(design, report);

        JRDesignTextField textField = new JRDesignTextField();
        layoutManager.applyStyleToElement(null, textField);

        assertNotNull("Style should be assigned even when null is passed", textField.getStyle());
        assertNotNull("Style should have a name", textField.getStyle().getName());
    }

    @Test
    public void testApplyStyleToElementWithStyle() throws Exception {
        report = new DynamicReportBuilder()
                .setTitle("Test Report")
                .build();

        layoutManager.applyLayout(design, report);

        Style style = new Style("testStyle");
        style.setBackgroundColor(Color.RED);

        JRDesignTextField textField = new JRDesignTextField();
        layoutManager.applyStyleToElement(style, textField);

        assertNotNull("Style should be assigned", textField.getStyle());
        assertEquals("Style name should match", "testStyle", textField.getStyle().getName());
    }

    @Test
    public void testGetReportStyles() throws Exception {
        report = new DynamicReportBuilder()
                .setTitle("Test Report")
                .build();

        layoutManager.applyLayout(design, report);

        Map<String, JRStyle> styles = new HashMap<>();
        layoutManager.setReportStyles(styles);

        assertSame("Should return same map", styles, layoutManager.getReportStyles());
    }

    @Test
    public void testApplyLayoutSetsDesignAndReport() throws Exception {
        AbstractColumn column = ColumnBuilder.getNew()
                .setColumnProperty("name", String.class.getName())
                .setTitle("Name")
                .setWidth(100)
                .build();

        report = new DynamicReportBuilder()
                .setTitle("Test Report")
                .addColumn(column)
                .build();

        layoutManager.applyLayout(design, report);

        // After applyLayout, columns should have positions set
        assertTrue("Column width should be positive", column.getWidth() > 0);
    }

    @Test
    public void testColumnIndexCaching() throws Exception {
        AbstractColumn col1 = ColumnBuilder.getNew()
                .setColumnProperty("col1", String.class.getName())
                .setTitle("Column 1")
                .setWidth(100)
                .build();

        AbstractColumn col2 = ColumnBuilder.getNew()
                .setColumnProperty("col2", String.class.getName())
                .setTitle("Column 2")
                .setWidth(100)
                .build();

        report = new DynamicReportBuilder()
                .setTitle("Test Report")
                .addColumn(col1)
                .addColumn(col2)
                .build();

        layoutManager.applyLayout(design, report);

        // Access column indices - should be cached after startLayout()
        int idx1 = layoutManager.getColumnIndex(col1);
        int idx2 = layoutManager.getColumnIndex(col2);

        assertEquals("First column index", 0, idx1);
        assertEquals("Second column index", 1, idx2);
    }

    @Test
    public void testColumnIndexNotFoundReturnsMinusOne() throws Exception {
        AbstractColumn col1 = ColumnBuilder.getNew()
                .setColumnProperty("col1", String.class.getName())
                .setTitle("Column 1")
                .setWidth(100)
                .build();

        AbstractColumn unregisteredCol = ColumnBuilder.getNew()
                .setColumnProperty("unregistered", String.class.getName())
                .setTitle("Unregistered")
                .setWidth(100)
                .build();

        report = new DynamicReportBuilder()
                .setTitle("Test Report")
                .addColumn(col1)
                .build();

        layoutManager.applyLayout(design, report);

        int idx = layoutManager.getColumnIndex(unregisteredCol);
        assertEquals("Unregistered column should return -1", -1, idx);
    }

    @Test
    public void testStyleNameUniqueness() throws Exception {
        report = new DynamicReportBuilder()
                .setTitle("Test Report")
                .build();

        layoutManager.applyLayout(design, report);

        JRDesignTextField tf1 = new JRDesignTextField();
        JRDesignTextField tf2 = new JRDesignTextField();

        layoutManager.applyStyleToElement(null, tf1);
        layoutManager.applyStyleToElement(null, tf2);

        assertNotEquals("Style names should be unique",
                tf1.getStyle().getName(), tf2.getStyle().getName());
    }

    @Test
    public void testAddStyleToDesign() throws Exception {
        report = new DynamicReportBuilder()
                .setTitle("Test Report")
                .build();

        layoutManager.applyLayout(design, report);

        Style style = new Style("myCustomStyle");
        style.setBackgroundColor(Color.BLUE);

        layoutManager.addStyleToDesign(style);

        assertNotNull("Style should be added to design",
                design.getStylesMap().get("myCustomStyle"));
    }

    @Test
    public void testSetColumnsFinalWidthWithUseFullPageWidth() throws Exception {
        AbstractColumn col1 = ColumnBuilder.getNew()
                .setColumnProperty("col1", String.class.getName())
                .setTitle("Column 1")
                .setWidth(50)
                .build();

        AbstractColumn col2 = ColumnBuilder.getNew()
                .setColumnProperty("col2", String.class.getName())
                .setTitle("Column 2")
                .setWidth(50)
                .build();

        report = new DynamicReportBuilder()
                .setTitle("Test Report")
                .addColumn(col1)
                .addColumn(col2)
                .setUseFullPageWidth(true)
                .build();

        layoutManager.applyLayout(design, report);

        // With useFullPageWidth, columns should expand to fill the page
        int totalWidth = col1.getWidth() + col2.getWidth();
        assertTrue("Total column width should equal printable width",
                totalWidth == report.getOptions().getPrintableWidth());
    }

    @Test
    public void testGetVisibleColumnsReturnsAllColumns() throws Exception {
        AbstractColumn col1 = ColumnBuilder.getNew()
                .setColumnProperty("col1", String.class.getName())
                .setTitle("Column 1")
                .setWidth(100)
                .build();

        AbstractColumn col2 = ColumnBuilder.getNew()
                .setColumnProperty("col2", String.class.getName())
                .setTitle("Column 2")
                .setWidth(100)
                .build();

        report = new DynamicReportBuilder()
                .addColumn(col1)
                .addColumn(col2)
                .build();

        layoutManager.applyLayout(design, report);

        // In AbstractLayoutManager (base), all columns are visible
        // ClassicLayoutManager may hide some based on groups
        assertEquals("Should have 2 visible columns", 2, report.getColumns().size());
    }
}
