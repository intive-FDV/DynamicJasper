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
package ar.com.fdvs.dj.core.registration;

import ar.com.fdvs.dj.core.layout.ClassicLayoutManager;
import ar.com.fdvs.dj.domain.CustomExpression;
import ar.com.fdvs.dj.domain.DynamicJasperDesign;
import ar.com.fdvs.dj.domain.DynamicReport;
import ar.com.fdvs.dj.domain.builders.ColumnBuilder;
import ar.com.fdvs.dj.domain.builders.DynamicReportBuilder;
import ar.com.fdvs.dj.domain.entities.columns.AbstractColumn;
import net.sf.jasperreports.engine.JRField;
import org.junit.Before;
import org.junit.Test;

import java.text.DecimalFormat;
import java.text.Format;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * Unit tests for ColumnRegistrationManager.
 * Tests the transformation of DJ columns to JasperReports fields.
 */
public class ColumnRegistrationManagerTest {

    private DynamicJasperDesign djd;
    private ClassicLayoutManager layoutManager;

    @Before
    public void setUp() {
        djd = new DynamicJasperDesign();
        djd.setName("TestReport");
        layoutManager = new ClassicLayoutManager();
    }

    @Test
    public void testAutoGeneratesColumnName() throws Exception {
        // Column without explicit name
        AbstractColumn column = ColumnBuilder.getNew()
                .setColumnProperty("state", String.class.getName())
                .setTitle("State")
                .setWidth(85)
                .build();

        assertNull("Column name should be null before registration", column.getName());

        DynamicReport dr = new DynamicReportBuilder()
                .addColumn(column)
                .build();

        ColumnRegistrationManager manager = new ColumnRegistrationManager(djd, dr, layoutManager);
        manager.registerEntities(dr.getColumns());

        assertNotNull("Column name should be set after registration", column.getName());
        assertTrue("Column name should contain COLUMN_ prefix",
                column.getName().contains("COLUMN_"));
        assertTrue("Column name should contain report name",
                column.getName().startsWith("TestReport_"));
    }

    @Test
    public void testRegistersPropertyColumnAsField() throws Exception {
        AbstractColumn column = ColumnBuilder.getNew()
                .setColumnProperty("productName", String.class.getName())
                .setTitle("Product")
                .setWidth(100)
                .build();

        DynamicReport dr = new DynamicReportBuilder()
                .addColumn(column)
                .build();

        ColumnRegistrationManager manager = new ColumnRegistrationManager(djd, dr, layoutManager);
        manager.registerEntities(dr.getColumns());

        // Verify field was registered
        JRField field = djd.getFieldsMap().get("productName");
        assertNotNull("Field should be registered", field);
        assertEquals("Field name should match property", "productName", field.getName());
        assertEquals("Field class should match",
                String.class.getName(), field.getValueClassName());
    }

    @Test
    public void testRegistersTextFormatterAsParameter() throws Exception {
        Format formatter = new DecimalFormat("#,##0.00");

        AbstractColumn column = ColumnBuilder.getNew()
                .setColumnProperty("amount", Float.class.getName())
                .setTitle("Amount")
                .setWidth(80)
                .setTextFormatter(formatter)
                .build();

        DynamicReport dr = new DynamicReportBuilder()
                .addColumn(column)
                .build();

        ColumnRegistrationManager manager = new ColumnRegistrationManager(djd, dr, layoutManager);
        manager.registerEntities(dr.getColumns());

        // Verify formatter was registered as parameter
        Map<String, Object> paramsWithValues = djd.getParametersWithValues();
        boolean formatterFound = false;
        for (Map.Entry<String, Object> entry : paramsWithValues.entrySet()) {
            if (entry.getKey().contains("formatter_for_") && entry.getValue() == formatter) {
                formatterFound = true;
                break;
            }
        }
        assertTrue("Text formatter should be registered as parameter", formatterFound);
    }

    @Test
    public void testSkipsDuplicateFieldRegistration() throws Exception {
        // Two columns with same property name
        AbstractColumn column1 = ColumnBuilder.getNew()
                .setColumnProperty("state", String.class.getName())
                .setTitle("State 1")
                .setWidth(85)
                .build();

        AbstractColumn column2 = ColumnBuilder.getNew()
                .setColumnProperty("state", String.class.getName())
                .setTitle("State 2")
                .setWidth(85)
                .build();

        DynamicReport dr = new DynamicReportBuilder()
                .addColumn(column1)
                .addColumn(column2)
                .build();

        ColumnRegistrationManager manager = new ColumnRegistrationManager(djd, dr, layoutManager);

        // Should not throw exception for duplicate
        manager.registerEntities(dr.getColumns());

        // Only one field should exist
        JRField field = djd.getFieldsMap().get("state");
        assertNotNull("Field should be registered", field);
        assertEquals("Should have exactly one field with this name",
                1, countFieldsWithName(djd, "state"));
    }

    @Test
    public void testHandlesExpressionColumn() throws Exception {
        CustomExpression expression = new CustomExpression() {
            @Override
            public Object evaluate(Map fields, Map variables, Map parameters) {
                return "calculated";
            }

            @Override
            public String getClassName() {
                return String.class.getName();
            }
        };

        AbstractColumn column = ColumnBuilder.getNew()
                .setCustomExpression(expression)
                .setTitle("Calculated")
                .setWidth(100)
                .build();

        DynamicReport dr = new DynamicReportBuilder()
                .addColumn(column)
                .build();

        ColumnRegistrationManager manager = new ColumnRegistrationManager(djd, dr, layoutManager);
        manager.registerEntities(dr.getColumns());

        // Verify custom expression was registered as parameter
        Map<String, Object> paramsWithValues = djd.getParametersWithValues();
        boolean expressionFound = false;
        for (Map.Entry<String, Object> entry : paramsWithValues.entrySet()) {
            if (entry.getKey().contains("customExpression_for_") && entry.getValue() == expression) {
                expressionFound = true;
                break;
            }
        }
        assertTrue("Custom expression should be registered as parameter", expressionFound);
    }

    @Test
    public void testRegistersFieldWithDescription() throws Exception {
        AbstractColumn column = ColumnBuilder.getNew()
                .setColumnProperty("data", String.class.getName(), "XML path description")
                .setTitle("Data")
                .setWidth(100)
                .build();

        DynamicReport dr = new DynamicReportBuilder()
                .addColumn(column)
                .build();

        ColumnRegistrationManager manager = new ColumnRegistrationManager(djd, dr, layoutManager);
        manager.registerEntities(dr.getColumns());

        JRField field = djd.getFieldsMap().get("data");
        assertNotNull("Field should be registered", field);
        assertEquals("Field description should be set",
                "XML path description", field.getDescription());
    }

    @Test
    public void testMultipleColumnsGetUniqueNames() throws Exception {
        AbstractColumn column1 = ColumnBuilder.getNew()
                .setColumnProperty("field1", String.class.getName())
                .setTitle("Field 1")
                .setWidth(85)
                .build();

        AbstractColumn column2 = ColumnBuilder.getNew()
                .setColumnProperty("field2", String.class.getName())
                .setTitle("Field 2")
                .setWidth(85)
                .build();

        AbstractColumn column3 = ColumnBuilder.getNew()
                .setColumnProperty("field3", String.class.getName())
                .setTitle("Field 3")
                .setWidth(85)
                .build();

        DynamicReport dr = new DynamicReportBuilder()
                .addColumn(column1)
                .addColumn(column2)
                .addColumn(column3)
                .build();

        ColumnRegistrationManager manager = new ColumnRegistrationManager(djd, dr, layoutManager);
        manager.registerEntities(dr.getColumns());

        // All columns should have unique names
        assertNotEquals("Column names should be unique",
                column1.getName(), column2.getName());
        assertNotEquals("Column names should be unique",
                column2.getName(), column3.getName());
        assertNotEquals("Column names should be unique",
                column1.getName(), column3.getName());
    }

    private int countFieldsWithName(DynamicJasperDesign design, String fieldName) {
        int count = 0;
        for (JRField field : design.getFieldsList()) {
            if (fieldName.equals(field.getName())) {
                count++;
            }
        }
        return count;
    }
}
