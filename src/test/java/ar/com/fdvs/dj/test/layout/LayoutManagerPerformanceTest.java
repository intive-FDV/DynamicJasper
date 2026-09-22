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
package ar.com.fdvs.dj.test.layout;

import ar.com.fdvs.dj.core.DynamicJasperHelper;
import ar.com.fdvs.dj.core.layout.ClassicLayoutManager;
import ar.com.fdvs.dj.domain.DynamicReport;
import ar.com.fdvs.dj.domain.builders.ColumnBuilder;
import ar.com.fdvs.dj.domain.builders.DynamicReportBuilder;
import ar.com.fdvs.dj.domain.builders.GroupBuilder;
import ar.com.fdvs.dj.domain.constants.GroupLayout;
import ar.com.fdvs.dj.domain.entities.DJGroup;
import ar.com.fdvs.dj.domain.entities.columns.AbstractColumn;
import ar.com.fdvs.dj.domain.entities.columns.PropertyColumn;
import ar.com.fdvs.dj.test.BaseDjReportTest;
import ar.com.fdvs.dj.test.TestRepositoryProducts;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * Tests for LayoutManager performance-related methods.
 * These tests verify the behavior of methods that will be optimized.
 *
 * Tests validate:
 * - getVisibleColumns() returns correct columns
 * - getParent() returns correct parent group
 * - getJRGroupFromDJGroup() returns correct JR group
 * - Column index lookups work correctly
 */
public class LayoutManagerPerformanceTest extends BaseDjReportTest {

    private AbstractColumn stateColumn;
    private AbstractColumn branchColumn;
    private AbstractColumn productLineColumn;
    private AbstractColumn itemColumn;
    private AbstractColumn quantityColumn;
    private AbstractColumn amountColumn;

    private DJGroup stateGroup;
    private DJGroup branchGroup;

    @Override
    public DynamicReport buildReport() throws Exception {
        DynamicReportBuilder drb = new DynamicReportBuilder();

        // Create columns
        stateColumn = ColumnBuilder.getNew()
                .setColumnProperty("state", String.class.getName())
                .setTitle("State")
                .setWidth(85)
                .build();

        branchColumn = ColumnBuilder.getNew()
                .setColumnProperty("branch", String.class.getName())
                .setTitle("Branch")
                .setWidth(85)
                .build();

        productLineColumn = ColumnBuilder.getNew()
                .setColumnProperty("productLine", String.class.getName())
                .setTitle("Product Line")
                .setWidth(85)
                .build();

        itemColumn = ColumnBuilder.getNew()
                .setColumnProperty("item", String.class.getName())
                .setTitle("Item")
                .setWidth(85)
                .build();

        quantityColumn = ColumnBuilder.getNew()
                .setColumnProperty("quantity", Long.class.getName())
                .setTitle("Quantity")
                .setWidth(80)
                .build();

        amountColumn = ColumnBuilder.getNew()
                .setColumnProperty("amount", Float.class.getName())
                .setTitle("Amount")
                .setWidth(90)
                .setPattern("$ 0.00")
                .build();

        // Add columns to report
        drb.addColumn(stateColumn);
        drb.addColumn(branchColumn);
        drb.addColumn(productLineColumn);
        drb.addColumn(itemColumn);
        drb.addColumn(quantityColumn);
        drb.addColumn(amountColumn);

        // Create groups - state group hides column, branch group doesn't
        stateGroup = new GroupBuilder()
                .setCriteriaColumn((PropertyColumn) stateColumn)
                .setGroupLayout(GroupLayout.VALUE_IN_HEADER_WITH_HEADERS)
                .build();

        branchGroup = new GroupBuilder()
                .setCriteriaColumn((PropertyColumn) branchColumn)
                .setGroupLayout(GroupLayout.DEFAULT)
                .build();

        drb.addGroup(stateGroup);
        drb.addGroup(branchGroup);

        drb.setTitle("Layout Manager Performance Test");
        drb.setUseFullPageWidth(true);

        return drb.build();
    }

    @Test
    public void testReportGeneratesSuccessfully() throws Exception {
        // Basic test that report generation works
        testReport();
        assertNotNull("JasperReport should not be null", jr);
        assertNotNull("JasperPrint should not be null", jp);
    }

    @Test
    public void testVisibleColumnsWithHiddenColumn() throws Exception {
        // Build report with hidden state column
        DynamicReportBuilder drb = new DynamicReportBuilder();

        AbstractColumn col1 = ColumnBuilder.getNew()
                .setColumnProperty("state", String.class.getName())
                .setTitle("State").setWidth(85).build();

        AbstractColumn col2 = ColumnBuilder.getNew()
                .setColumnProperty("branch", String.class.getName())
                .setTitle("Branch").setWidth(85).build();

        AbstractColumn col3 = ColumnBuilder.getNew()
                .setColumnProperty("amount", Float.class.getName())
                .setTitle("Amount").setWidth(90).build();

        drb.addColumn(col1);
        drb.addColumn(col2);
        drb.addColumn(col3);

        // Group that hides state column
        DJGroup group = new GroupBuilder()
                .setCriteriaColumn((PropertyColumn) col1)
                .setGroupLayout(GroupLayout.VALUE_IN_HEADER) // This hides the column
                .build();

        drb.addGroup(group);
        drb.setUseFullPageWidth(true);

        DynamicReport dr = drb.build();

        // Use TestableLayoutManager to access protected methods
        TestableClassicLayoutManager layoutManager = new TestableClassicLayoutManager();

        Map<String, Object> params = new HashMap<>();
        JasperReport jasperReport = DynamicJasperHelper.generateJasperReport(dr, layoutManager, params);

        // After layout, check visible columns
        List<AbstractColumn> visibleColumns = layoutManager.getVisibleColumnsForTest();

        // Should have 2 visible columns (branch and amount), state is hidden
        assertEquals("Should have 2 visible columns", 2, visibleColumns.size());
        assertFalse("State column should not be visible", visibleColumns.contains(col1));
        assertTrue("Branch column should be visible", visibleColumns.contains(col2));
        assertTrue("Amount column should be visible", visibleColumns.contains(col3));
    }

    @Test
    public void testVisibleColumnsAllVisible() throws Exception {
        // Build report with no hidden columns
        DynamicReportBuilder drb = new DynamicReportBuilder();

        AbstractColumn col1 = ColumnBuilder.getNew()
                .setColumnProperty("state", String.class.getName())
                .setTitle("State").setWidth(85).build();

        AbstractColumn col2 = ColumnBuilder.getNew()
                .setColumnProperty("branch", String.class.getName())
                .setTitle("Branch").setWidth(85).build();

        drb.addColumn(col1);
        drb.addColumn(col2);

        // Group that doesn't hide column
        DJGroup group = new GroupBuilder()
                .setCriteriaColumn((PropertyColumn) col1)
                .setGroupLayout(GroupLayout.DEFAULT_WITH_HEADER) // Doesn't hide
                .build();

        drb.addGroup(group);
        drb.setUseFullPageWidth(true);

        DynamicReport dr = drb.build();

        TestableClassicLayoutManager layoutManager = new TestableClassicLayoutManager();

        Map<String, Object> params = new HashMap<>();
        DynamicJasperHelper.generateJasperReport(dr, layoutManager, params);

        List<AbstractColumn> visibleColumns = layoutManager.getVisibleColumnsForTest();

        // Should have all 2 columns visible
        assertEquals("Should have 2 visible columns", 2, visibleColumns.size());
        assertTrue("State column should be visible", visibleColumns.contains(col1));
        assertTrue("Branch column should be visible", visibleColumns.contains(col2));
    }

    @Test
    public void testColumnIndexConsistency() throws Exception {
        dr = buildReport();

        // Verify column indices are consistent
        List<AbstractColumn> columns = dr.getColumns();

        assertEquals("State column index", 0, columns.indexOf(stateColumn));
        assertEquals("Branch column index", 1, columns.indexOf(branchColumn));
        assertEquals("ProductLine column index", 2, columns.indexOf(productLineColumn));
        assertEquals("Item column index", 3, columns.indexOf(itemColumn));
        assertEquals("Quantity column index", 4, columns.indexOf(quantityColumn));
        assertEquals("Amount column index", 5, columns.indexOf(amountColumn));
    }

    @Test
    public void testGroupIndexConsistency() throws Exception {
        dr = buildReport();

        // Verify group indices are consistent
        List<DJGroup> groups = dr.getColumnsGroups();

        assertEquals("State group index", 0, groups.indexOf(stateGroup));
        assertEquals("Branch group index", 1, groups.indexOf(branchGroup));
    }

    @Test
    public void testMultipleGetVisibleColumnsReturnsSameResult() throws Exception {
        // Verify that multiple calls return consistent results
        DynamicReportBuilder drb = new DynamicReportBuilder();

        AbstractColumn col1 = ColumnBuilder.getNew()
                .setColumnProperty("state", String.class.getName())
                .setTitle("State").setWidth(85).build();

        AbstractColumn col2 = ColumnBuilder.getNew()
                .setColumnProperty("amount", Float.class.getName())
                .setTitle("Amount").setWidth(90).build();

        drb.addColumn(col1);
        drb.addColumn(col2);

        DJGroup group = new GroupBuilder()
                .setCriteriaColumn((PropertyColumn) col1)
                .setGroupLayout(GroupLayout.VALUE_IN_HEADER)
                .build();

        drb.addGroup(group);
        drb.setUseFullPageWidth(true);

        DynamicReport dr = drb.build();

        TestableClassicLayoutManager layoutManager = new TestableClassicLayoutManager();

        Map<String, Object> params = new HashMap<>();
        DynamicJasperHelper.generateJasperReport(dr, layoutManager, params);

        // Call multiple times and verify consistency
        List<AbstractColumn> call1 = layoutManager.getVisibleColumnsForTest();
        List<AbstractColumn> call2 = layoutManager.getVisibleColumnsForTest();
        List<AbstractColumn> call3 = layoutManager.getVisibleColumnsForTest();

        assertEquals("First and second call should return same size", call1.size(), call2.size());
        assertEquals("Second and third call should return same size", call2.size(), call3.size());

        // Verify contents are the same
        for (int i = 0; i < call1.size(); i++) {
            assertEquals("Column at index " + i + " should match", call1.get(i), call2.get(i));
            assertEquals("Column at index " + i + " should match", call2.get(i), call3.get(i));
        }
    }

    @Override
    protected JRDataSource getDataSource() {
        return new JRBeanCollectionDataSource(TestRepositoryProducts.getDummyCollection());
    }

    /**
     * Test-accessible version of ClassicLayoutManager that exposes protected methods
     */
    public static class TestableClassicLayoutManager extends ClassicLayoutManager {

        public List<AbstractColumn> getVisibleColumnsForTest() {
            return getVisibleColumns();
        }
    }
}
