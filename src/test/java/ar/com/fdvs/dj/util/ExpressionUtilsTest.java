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
package ar.com.fdvs.dj.util;

import ar.com.fdvs.dj.core.DJConstants;
import ar.com.fdvs.dj.domain.ColumnProperty;
import ar.com.fdvs.dj.domain.DJCalculation;
import ar.com.fdvs.dj.domain.DJDataSource;
import ar.com.fdvs.dj.domain.entities.Subreport;
import ar.com.fdvs.dj.domain.entities.SubreportParameter;
import net.sf.jasperreports.engine.design.JRDesignExpression;
import net.sf.jasperreports.engine.design.JasperDesign;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Unit tests for ExpressionUtils.
 * Tests expression creation and parameter handling utilities.
 */
public class ExpressionUtilsTest {

    @Test
    public void testCreateStringExpression() {
        JRDesignExpression exp = ExpressionUtils.createStringExpression("\"Hello World\"");

        assertNotNull("Expression should not be null", exp);
        assertEquals("Expression text should match", "\"Hello World\"", exp.getText());
    }

    @Test
    public void testCreateExpressionWithClass() {
        JRDesignExpression exp = ExpressionUtils.createExpression("$F{amount}", Float.class);

        assertNotNull("Expression should not be null", exp);
        assertEquals("Expression text should match", "$F{amount}", exp.getText());
    }

    @Test
    public void testCreateExpressionWithClassName() {
        JRDesignExpression exp = ExpressionUtils.createExpression("$V{total}", "java.lang.Double");

        assertNotNull("Expression should not be null", exp);
        assertEquals("Expression text should match", "$V{total}", exp.getText());
    }

    @Test
    public void testGetReportConnectionExpression() {
        JRDesignExpression exp = ExpressionUtils.getReportConnectionExpression();

        assertNotNull("Expression should not be null", exp);
        assertTrue("Should reference REPORT_CONNECTION",
                exp.getText().contains("REPORT_CONNECTION"));
    }

    @Test
    public void testCreateParameterName() {
        String name = ExpressionUtils.createParameterName("prefix_", new Object());

        assertNotNull("Name should not be null", name);
        assertTrue("Name should start with prefix", name.startsWith("prefix_"));
        assertFalse("Name should not contain @", name.contains("@"));
        assertFalse("Name should not contain $", name.contains("$"));
    }

    @Test
    public void testCreateParameterNameWithSuffix() {
        String name = ExpressionUtils.createParameterName("pre_", new Object(), "_suf");

        assertNotNull("Name should not be null", name);
        assertTrue("Name should start with prefix", name.startsWith("pre_"));
        assertTrue("Name should end with suffix", name.endsWith("_suf"));
    }

    @Test
    public void testGetValueClassNameForCountOperation() {
        ColumnProperty prop = new ColumnProperty("amount", Float.class.getName());

        String className = ExpressionUtils.getValueClassNameForOperation(DJCalculation.COUNT, prop);

        assertEquals("COUNT should return Number", Number.class.getName(), className);
    }

    @Test
    public void testGetValueClassNameForDistinctCountOperation() {
        ColumnProperty prop = new ColumnProperty("amount", Float.class.getName());

        String className = ExpressionUtils.getValueClassNameForOperation(DJCalculation.DISTINCT_COUNT, prop);

        assertEquals("DISTINCT_COUNT should return Number", Number.class.getName(), className);
    }

    @Test
    public void testGetValueClassNameForSumOperation() {
        ColumnProperty prop = new ColumnProperty("amount", Float.class.getName());

        String className = ExpressionUtils.getValueClassNameForOperation(DJCalculation.SUM, prop);

        assertEquals("SUM should return property class", Float.class.getName(), className);
    }

    @Test
    public void testGetInitialValueExpressionForCount() {
        ColumnProperty prop = new ColumnProperty("amount", Float.class.getName());

        String initExp = ExpressionUtils.getInitialValueExpressionForOperation(DJCalculation.COUNT, prop);

        assertNotNull("Init expression for COUNT should not be null", initExp);
        assertTrue("Should create Long 0", initExp.contains("Long"));
    }

    @Test
    public void testGetInitialValueExpressionForSum() {
        ColumnProperty prop = new ColumnProperty("amount", Float.class.getName());

        String initExp = ExpressionUtils.getInitialValueExpressionForOperation(DJCalculation.SUM, prop);

        assertNotNull("Init expression for SUM should not be null", initExp);
        assertTrue("Should use property class", initExp.contains(Float.class.getName()));
    }

    @Test
    public void testGetInitialValueExpressionForAverage() {
        ColumnProperty prop = new ColumnProperty("amount", Float.class.getName());

        String initExp = ExpressionUtils.getInitialValueExpressionForOperation(DJCalculation.AVERAGE, prop);

        assertNull("Init expression for AVERAGE should be null", initExp);
    }

    @Test
    public void testGetFieldsMapExpression() {
        List<ColumnProperty> props = new ArrayList<>();
        props.add(new ColumnProperty("name", String.class.getName()));
        props.add(new ColumnProperty("amount", Float.class.getName()));

        String fieldsMap = ExpressionUtils.getFieldsMapExpression(props);

        assertNotNull("Fields map should not be null", fieldsMap);
        assertTrue("Should contain PropertiesMap", fieldsMap.contains("PropertiesMap"));
        assertTrue("Should contain name field", fieldsMap.contains("\"name\""));
        assertTrue("Should contain amount field", fieldsMap.contains("\"amount\""));
    }

    @Test
    public void testGetParametersMapExpression() {
        String paramsMap = ExpressionUtils.getParametersMapExpression();

        assertNotNull("Params map should not be null", paramsMap);
        assertTrue("Should contain PropertiesMap", paramsMap.contains("PropertiesMap"));
    }

    @Test
    public void testGetTextForFieldsFromScriptlet() {
        String text = ExpressionUtils.getTextForFieldsFromScriptlet();

        assertNotNull("Text should not be null", text);
        assertTrue("Should reference scriptlet", text.contains("REPORT_SCRIPTLET"));
        assertTrue("Should call getCurrentFields", text.contains("getCurrentFields"));
    }

    @Test
    public void testGetTextForVariablesFromScriptlet() {
        String text = ExpressionUtils.getTextForVariablesFromScriptlet();

        assertNotNull("Text should not be null", text);
        assertTrue("Should reference scriptlet", text.contains("REPORT_SCRIPTLET"));
        assertTrue("Should call getCurrentVariables", text.contains("getCurrentVariables"));
    }

    @Test
    public void testGetTextForParametersFromScriptlet() {
        String text = ExpressionUtils.getTextForParametersFromScriptlet();

        assertNotNull("Text should not be null", text);
        assertTrue("Should reference scriptlet", text.contains("REPORT_SCRIPTLET"));
        assertTrue("Should call getCurrentParams", text.contains("getCurrentParams"));
    }

    @Test
    public void testGetDataSourceExpressionForCollection() {
        DJDataSource ds = new DJDataSource("myCollection",
                DJConstants.DATA_SOURCE_ORIGIN_PARAMETER,
                DJConstants.DATA_SOURCE_TYPE_COLLECTION);

        JRDesignExpression exp = ExpressionUtils.getDataSourceExpression(ds);

        assertNotNull("Expression should not be null", exp);
        assertTrue("Should wrap in JRBeanCollectionDataSource",
                exp.getText().contains("JRBeanCollectionDataSource"));
    }

    @Test
    public void testGetDataSourceExpressionForArray() {
        DJDataSource ds = new DJDataSource("myArray",
                DJConstants.DATA_SOURCE_ORIGIN_PARAMETER,
                DJConstants.DATA_SOURCE_TYPE_ARRAY);

        JRDesignExpression exp = ExpressionUtils.getDataSourceExpression(ds);

        assertNotNull("Expression should not be null", exp);
        assertTrue("Should wrap in JRBeanArrayDataSource",
                exp.getText().contains("JRBeanArrayDataSource"));
    }

    @Test
    public void testGetParameterExpressionUseParentParams() {
        Subreport sr = new Subreport();
        sr.setUseParentReportParameters(true);

        JRDesignExpression exp = ExpressionUtils.getParameterExpression(sr);

        assertNotNull("Expression should not be null", exp);
        assertEquals("Should reference REPORT_PARAMETERS_MAP",
                "$P{REPORT_PARAMETERS_MAP}", exp.getText());
    }

    @Test
    public void testCreateExpressionForSubreportParameterField() {
        SubreportParameter sp = new SubreportParameter();
        sp.setExpression("fieldName");
        sp.setParameterOrigin(DJConstants.SUBREPORT_PARAM_ORIGIN_FIELD);

        JasperDesign jd = new JasperDesign();
        JRDesignExpression exp = ExpressionUtils.createExpression(jd, sp);

        assertNotNull("Expression should not be null", exp);
        assertEquals("Should use $F{} syntax", "$F{fieldName}", exp.getText());
    }

    @Test
    public void testCreateExpressionForSubreportParameterVariable() {
        SubreportParameter sp = new SubreportParameter();
        sp.setExpression("varName");
        sp.setParameterOrigin(DJConstants.SUBREPORT_PARAM_ORIGIN_VARIABLE);

        JasperDesign jd = new JasperDesign();
        JRDesignExpression exp = ExpressionUtils.createExpression(jd, sp);

        assertNotNull("Expression should not be null", exp);
        assertEquals("Should use $V{} syntax", "$V{varName}", exp.getText());
    }

    @Test
    public void testCreateExpressionForSubreportParameterCustom() {
        SubreportParameter sp = new SubreportParameter();
        sp.setExpression("someCustomExpression()");
        sp.setParameterOrigin(DJConstants.SUBREPORT_PARAM_ORIGIN_CUSTOM);

        JasperDesign jd = new JasperDesign();
        JRDesignExpression exp = ExpressionUtils.createExpression(jd, sp);

        assertNotNull("Expression should not be null", exp);
        assertEquals("Should use raw expression", "someCustomExpression()", exp.getText());
    }
}
