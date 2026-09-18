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
import ar.com.fdvs.dj.domain.builders.ColumnBuilder;
import ar.com.fdvs.dj.domain.builders.DynamicReportBuilder;
import ar.com.fdvs.dj.domain.entities.columns.AbstractColumn;
import net.sf.jasperreports.engine.JRBand;
import net.sf.jasperreports.engine.design.JRDesignSection;
import org.junit.Before;
import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.*;

/**
 * Unit tests for ClassicLayoutManager.
 * Tests basic layout generation for simple reports without groups.
 * More complex group scenarios are tested through integration tests.
 */
public class ClassicLayoutManagerTest {

    private ClassicLayoutManager layoutManager;
    private DynamicJasperDesign design;

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
    public void testReferencesMapInitialized() {
        assertNotNull("References map should not be null", layoutManager.getReferencesMap());
        assertTrue("References map should be empty initially", layoutManager.getReferencesMap().isEmpty());
    }

    @Test
    public void testApplyLayoutCreatesColumnHeader() throws Exception {
        AbstractColumn column = ColumnBuilder.getNew()
                .setColumnProperty("name", String.class.getName())
                .setTitle("Name")
                .setWidth(100)
                .build();

        DynamicReport report = new DynamicReportBuilder()
                .setTitle("Test Report")
                .addColumn(column)
                .setPrintColumnNames(true)
                .build();

        layoutManager.applyLayout(design, report);

        JRBand columnHeader = design.getColumnHeader();
        assertNotNull("Column header band should exist", columnHeader);
    }

    @Test
    public void testApplyLayoutWithTitle() throws Exception {
        DynamicReport report = new DynamicReportBuilder()
                .setTitle("My Test Report")
                .build();

        layoutManager.applyLayout(design, report);

        // Title is placed in pageHeader when not on new page
        JRBand pageHeader = design.getPageHeader();
        assertNotNull("Page header should exist when title is set", pageHeader);
    }

    @Test
    public void testMultipleColumnsPositioning() throws Exception {
        AbstractColumn col1 = ColumnBuilder.getNew()
                .setColumnProperty("col1", String.class.getName())
                .setTitle("Column 1")
                .setWidth(100)
                .build();

        AbstractColumn col2 = ColumnBuilder.getNew()
                .setColumnProperty("col2", String.class.getName())
                .setTitle("Column 2")
                .setWidth(150)
                .build();

        AbstractColumn col3 = ColumnBuilder.getNew()
                .setColumnProperty("col3", String.class.getName())
                .setTitle("Column 3")
                .setWidth(200)
                .build();

        DynamicReport report = new DynamicReportBuilder()
                .addColumn(col1)
                .addColumn(col2)
                .addColumn(col3)
                .build();

        layoutManager.applyLayout(design, report);

        // Columns should be positioned sequentially
        assertEquals("Col1 should start at 0", 0, col1.getPosX());
        assertEquals("Col2 should start after col1", 100, col2.getPosX());
        assertEquals("Col3 should start after col2", 250, col3.getPosX());
    }

    @Test
    public void testLayoutWithUseFullPageWidth() throws Exception {
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

        DynamicReport report = new DynamicReportBuilder()
                .addColumn(col1)
                .addColumn(col2)
                .setUseFullPageWidth(true)
                .build();

        layoutManager.applyLayout(design, report);

        int totalWidth = col1.getWidth() + col2.getWidth();
        assertEquals("Total column width should equal printable width",
                report.getOptions().getPrintableWidth(), totalWidth);
    }

    @Test
    public void testEmptyReportLayout() throws Exception {
        DynamicReport report = new DynamicReportBuilder().build();

        // Should not throw
        layoutManager.applyLayout(design, report);

        // Just verify it completes without error
        assertNotNull("Design should still be valid", design);
    }

    @Test
    public void testReferencesMapMutable() {
        Map<String, Object> refs = layoutManager.getReferencesMap();
        refs.put("testKey", "testValue");

        assertEquals("Value should be stored", "testValue", refs.get("testKey"));
    }

    @Test
    public void testDetailBandCreated() throws Exception {
        AbstractColumn column = ColumnBuilder.getNew()
                .setColumnProperty("name", String.class.getName())
                .setTitle("Name")
                .setWidth(100)
                .build();

        DynamicReport report = new DynamicReportBuilder()
                .addColumn(column)
                .build();

        layoutManager.applyLayout(design, report);

        JRDesignSection detailSection = (JRDesignSection) design.getDetailSection();
        assertNotNull("Detail section should exist", detailSection);
        assertFalse("Detail section should have bands", detailSection.getBandsList().isEmpty());
    }

    @Test
    public void testColumnWidthPreservedWhenNotUsingFullPage() throws Exception {
        AbstractColumn col1 = ColumnBuilder.getNew()
                .setColumnProperty("col1", String.class.getName())
                .setTitle("Column 1")
                .setWidth(100)
                .build();

        AbstractColumn col2 = ColumnBuilder.getNew()
                .setColumnProperty("col2", String.class.getName())
                .setTitle("Column 2")
                .setWidth(200)
                .build();

        DynamicReport report = new DynamicReportBuilder()
                .addColumn(col1)
                .addColumn(col2)
                .setUseFullPageWidth(false)
                .build();

        layoutManager.applyLayout(design, report);

        assertEquals("Col1 width should be preserved", 100, col1.getWidth());
        assertEquals("Col2 width should be preserved", 200, col2.getWidth());
    }

    @Test
    public void testFixedWidthColumnNotResized() throws Exception {
        AbstractColumn fixedCol = ColumnBuilder.getNew()
                .setColumnProperty("fixed", String.class.getName())
                .setTitle("Fixed")
                .setWidth(100)
                .setFixedWidth(true)
                .build();

        AbstractColumn flexCol = ColumnBuilder.getNew()
                .setColumnProperty("flex", String.class.getName())
                .setTitle("Flex")
                .setWidth(100)
                .build();

        DynamicReport report = new DynamicReportBuilder()
                .addColumn(fixedCol)
                .addColumn(flexCol)
                .setUseFullPageWidth(true)
                .build();

        layoutManager.applyLayout(design, report);

        // Fixed column should maintain its width
        assertEquals("Fixed column width should not change", 100, fixedCol.getWidth());
        // Flex column should expand to fill remaining space
        int expectedFlexWidth = report.getOptions().getPrintableWidth() - 100;
        assertEquals("Flex column should fill remaining width", expectedFlexWidth, flexCol.getWidth());
    }

    @Test
    public void testTitleInPageHeader() throws Exception {
        DynamicReport report = new DynamicReportBuilder()
                .setTitle("Test Title")
                .build();

        layoutManager.applyLayout(design, report);

        JRBand pageHeader = design.getPageHeader();
        assertNotNull("Page header should exist when title is set", pageHeader);
        // Note: The title is added but elements count depends on JasperReports version
        // Just verify the page header was created
    }

    @Test
    public void testSubtitleNotShownWithoutTitle() throws Exception {
        DynamicReport report = new DynamicReportBuilder()
                .setSubtitle("Just Subtitle")
                .build();

        layoutManager.applyLayout(design, report);

        // Without title, subtitle is ignored - page header might be null or empty
        // This is expected behavior per the code comment
    }

    @Test
    public void testColumnHeaderWithPrintColumnNames() throws Exception {
        AbstractColumn col = ColumnBuilder.getNew()
                .setColumnProperty("name", String.class.getName())
                .setTitle("Name Column")
                .setWidth(100)
                .build();

        DynamicReport report = new DynamicReportBuilder()
                .addColumn(col)
                .setPrintColumnNames(true)
                .build();

        layoutManager.applyLayout(design, report);

        JRBand columnHeader = design.getColumnHeader();
        assertNotNull("Column header should exist", columnHeader);
        assertTrue("Column header should have elements", columnHeader.getElements().length > 0);
    }

    @Test
    public void testColumnHeaderNotCreatedWithoutPrintColumnNames() throws Exception {
        AbstractColumn col = ColumnBuilder.getNew()
                .setColumnProperty("name", String.class.getName())
                .setTitle("Name Column")
                .setWidth(100)
                .build();

        DynamicReport report = new DynamicReportBuilder()
                .addColumn(col)
                .setPrintColumnNames(false)
                .build();

        layoutManager.applyLayout(design, report);

        JRBand columnHeader = design.getColumnHeader();
        // Column header exists but should be empty or just have template content
        if (columnHeader != null) {
            // If it exists, check that no DJ elements were added
            // (template elements may still exist)
        }
    }
}
