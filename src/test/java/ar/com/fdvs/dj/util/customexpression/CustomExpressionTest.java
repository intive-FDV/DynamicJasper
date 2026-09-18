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
package ar.com.fdvs.dj.util.customexpression;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * Unit tests for built-in CustomExpression implementations.
 * Tests PageNumberCustomExpression, RecordsInPageCustomExpression, RecordsInReportCustomExpression.
 */
public class CustomExpressionTest {

    @Test
    public void testPageNumberCustomExpressionEvaluate() {
        PageNumberCustomExpression expr = new PageNumberCustomExpression();
        Map<String, Object> variables = new HashMap<>();
        variables.put("PAGE_NUMBER", 5);

        Object result = expr.evaluate(new HashMap<>(), variables, new HashMap<>());

        assertEquals("Should return page number", 5, result);
    }

    @Test
    public void testPageNumberCustomExpressionClassName() {
        PageNumberCustomExpression expr = new PageNumberCustomExpression();

        assertEquals("Class name should be Integer",
                Integer.class.getName(), expr.getClassName());
    }

    @Test
    public void testPageNumberCustomExpressionNullVariable() {
        PageNumberCustomExpression expr = new PageNumberCustomExpression();

        Object result = expr.evaluate(new HashMap<>(), new HashMap<>(), new HashMap<>());

        assertNull("Should return null when variable not present", result);
    }

    @Test
    public void testRecordsInPageCustomExpressionEvaluate() {
        RecordsInPageCustomExpression expr = new RecordsInPageCustomExpression();
        Map<String, Object> variables = new HashMap<>();
        variables.put("PAGE_COUNT", 10);

        Object result = expr.evaluate(new HashMap<>(), variables, new HashMap<>());

        assertEquals("Should return page count", 10, result);
    }

    @Test
    public void testRecordsInPageCustomExpressionClassName() {
        RecordsInPageCustomExpression expr = new RecordsInPageCustomExpression();

        assertEquals("Class name should be Integer",
                Integer.class.getName(), expr.getClassName());
    }

    @Test
    public void testRecordsInPageCustomExpressionNullVariable() {
        RecordsInPageCustomExpression expr = new RecordsInPageCustomExpression();

        Object result = expr.evaluate(new HashMap<>(), new HashMap<>(), new HashMap<>());

        assertNull("Should return null when variable not present", result);
    }

    @Test
    public void testRecordsInReportCustomExpressionEvaluate() {
        RecordsInReportCustomExpression expr = new RecordsInReportCustomExpression();
        Map<String, Object> variables = new HashMap<>();
        variables.put("REPORT_COUNT", 100);

        Object result = expr.evaluate(new HashMap<>(), variables, new HashMap<>());

        assertEquals("Should return report count", 100, result);
    }

    @Test
    public void testRecordsInReportCustomExpressionClassName() {
        RecordsInReportCustomExpression expr = new RecordsInReportCustomExpression();

        assertEquals("Class name should be Integer",
                Integer.class.getName(), expr.getClassName());
    }

    @Test
    public void testRecordsInReportCustomExpressionNullVariable() {
        RecordsInReportCustomExpression expr = new RecordsInReportCustomExpression();

        Object result = expr.evaluate(new HashMap<>(), new HashMap<>(), new HashMap<>());

        assertNull("Should return null when variable not present", result);
    }

    @Test
    public void testExpressionsIgnoreFieldsAndParameters() {
        PageNumberCustomExpression pageExpr = new PageNumberCustomExpression();
        RecordsInPageCustomExpression pageCountExpr = new RecordsInPageCustomExpression();
        RecordsInReportCustomExpression reportCountExpr = new RecordsInReportCustomExpression();

        Map<String, Object> fields = new HashMap<>();
        fields.put("someField", "value");

        Map<String, Object> params = new HashMap<>();
        params.put("someParam", "value");

        Map<String, Object> variables = new HashMap<>();
        variables.put("PAGE_NUMBER", 1);
        variables.put("PAGE_COUNT", 5);
        variables.put("REPORT_COUNT", 50);

        // Fields and params should not affect result
        assertEquals(1, pageExpr.evaluate(fields, variables, params));
        assertEquals(5, pageCountExpr.evaluate(fields, variables, params));
        assertEquals(50, reportCountExpr.evaluate(fields, variables, params));
    }
}
