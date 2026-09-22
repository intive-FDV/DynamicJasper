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
import ar.com.fdvs.dj.domain.DJCalculation;
import ar.com.fdvs.dj.domain.DynamicJasperDesign;
import ar.com.fdvs.dj.domain.DynamicReport;
import ar.com.fdvs.dj.domain.builders.DynamicReportBuilder;
import ar.com.fdvs.dj.domain.constants.DJVariableResetType;
import ar.com.fdvs.dj.domain.entities.DJVariable;
import net.sf.jasperreports.engine.design.JRDesignVariable;
import net.sf.jasperreports.engine.type.CalculationEnum;
import net.sf.jasperreports.engine.type.ResetTypeEnum;
import org.junit.Before;
import org.junit.Test;

import java.util.Collections;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * Unit tests for VariableRegistrationManager.
 * Tests the transformation of DJ variables to JasperReports variables.
 */
public class VariableRegistrationManagerTest {

    private DynamicJasperDesign djd;
    private DynamicReport dr;
    private ClassicLayoutManager layoutManager;

    @Before
    public void setUp() throws Exception {
        djd = new DynamicJasperDesign();
        djd.setName("TestReport");
        dr = new DynamicReportBuilder().build();
        layoutManager = new ClassicLayoutManager();
    }

    @Test
    public void testCreatesVariableWithNameAndClass() throws Exception {
        CustomExpression expression = createSimpleExpression(Integer.class);

        DJVariable djVar = new DJVariable("testVar", Integer.class.getName(),
                DJCalculation.SUM, expression);

        VariableRegistrationManager manager = new VariableRegistrationManager(djd, dr, layoutManager);
        manager.registerEntities(Collections.singletonList(djVar));

        // Find registered variable
        JRDesignVariable jrVar = findVariable(djd, "testVar");
        assertNotNull("Variable should be registered", jrVar);
        assertEquals("Variable name should match", "testVar", jrVar.getName());
        assertEquals("Variable class should match",
                Integer.class.getName(), jrVar.getValueClassName());
    }

    @Test
    public void testMapsCalculationEnumCorrectly() throws Exception {
        // Test SUM calculation
        CustomExpression expression = createSimpleExpression(Integer.class);

        DJVariable djVar = new DJVariable("sumVar", Integer.class.getName(),
                DJCalculation.SUM, expression);

        VariableRegistrationManager manager = new VariableRegistrationManager(djd, dr, layoutManager);
        manager.registerEntities(Collections.singletonList(djVar));

        JRDesignVariable jrVar = findVariable(djd, "sumVar");
        assertNotNull("Variable should be registered", jrVar);
        assertEquals("Calculation should be SUM",
                CalculationEnum.SUM, jrVar.getCalculationValue());
    }

    @Test
    public void testMapsCountCalculation() throws Exception {
        CustomExpression expression = createSimpleExpression(Integer.class);

        DJVariable djVar = new DJVariable("countVar", Integer.class.getName(),
                DJCalculation.COUNT, expression);

        VariableRegistrationManager manager = new VariableRegistrationManager(djd, dr, layoutManager);
        manager.registerEntities(Collections.singletonList(djVar));

        JRDesignVariable jrVar = findVariable(djd, "countVar");
        assertNotNull("Variable should be registered", jrVar);
        assertEquals("Calculation should be COUNT",
                CalculationEnum.COUNT, jrVar.getCalculationValue());
    }

    @Test
    public void testMapsResetTypeWithIndexAdjustment() throws Exception {
        // DJ uses 1-indexed (REPORT=1), JR uses 0-indexed (REPORT=0)
        CustomExpression expression = createSimpleExpression(Integer.class);

        DJVariable djVar = new DJVariable("resetVar", Integer.class.getName(),
                DJCalculation.SUM, expression);
        djVar.setResetType(DJVariableResetType.REPORT); // value = 1

        VariableRegistrationManager manager = new VariableRegistrationManager(djd, dr, layoutManager);
        manager.registerEntities(Collections.singletonList(djVar));

        JRDesignVariable jrVar = findVariable(djd, "resetVar");
        assertNotNull("Variable should be registered", jrVar);
        assertEquals("Reset type should be REPORT",
                ResetTypeEnum.REPORT, jrVar.getResetTypeValue());
    }

    @Test
    public void testMapsPageResetType() throws Exception {
        CustomExpression expression = createSimpleExpression(Integer.class);

        DJVariable djVar = new DJVariable("pageVar", Integer.class.getName(),
                DJCalculation.SUM, expression);
        djVar.setResetType(DJVariableResetType.PAGE); // value = 2

        VariableRegistrationManager manager = new VariableRegistrationManager(djd, dr, layoutManager);
        manager.registerEntities(Collections.singletonList(djVar));

        JRDesignVariable jrVar = findVariable(djd, "pageVar");
        assertNotNull("Variable should be registered", jrVar);
        assertEquals("Reset type should be PAGE",
                ResetTypeEnum.PAGE, jrVar.getResetTypeValue());
    }

    @Test
    public void testRegistersExpressionAsParameter() throws Exception {
        CustomExpression expression = createSimpleExpression(Integer.class);

        DJVariable djVar = new DJVariable("exprVar", Integer.class.getName(),
                DJCalculation.SUM, expression);

        VariableRegistrationManager manager = new VariableRegistrationManager(djd, dr, layoutManager);
        manager.registerEntities(Collections.singletonList(djVar));

        // Expression should be registered as parameter
        Map<String, Object> params = djd.getParametersWithValues();
        boolean expressionFound = false;
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            if (entry.getKey().contains("exprVar") && entry.getValue() == expression) {
                expressionFound = true;
                break;
            }
        }
        assertTrue("Expression should be registered as parameter", expressionFound);
    }

    @Test
    public void testHandlesInitialValueExpression() throws Exception {
        CustomExpression mainExpression = createSimpleExpression(Integer.class);
        CustomExpression initialExpression = new CustomExpression() {
            @Override
            public Object evaluate(Map fields, Map variables, Map parameters) {
                return 0;
            }

            @Override
            public String getClassName() {
                return Integer.class.getName();
            }
        };

        DJVariable djVar = new DJVariable("initVar", Integer.class.getName(),
                DJCalculation.SUM, mainExpression);
        djVar.setInitialValueExpression(initialExpression);

        VariableRegistrationManager manager = new VariableRegistrationManager(djd, dr, layoutManager);
        manager.registerEntities(Collections.singletonList(djVar));

        JRDesignVariable jrVar = findVariable(djd, "initVar");
        assertNotNull("Variable should be registered", jrVar);
        assertNotNull("Initial value expression should be set",
                jrVar.getInitialValueExpression());
    }

    @Test
    public void testHandlesNullClassName() throws Exception {
        CustomExpression expression = createSimpleExpression(Object.class);

        DJVariable djVar = new DJVariable();
        djVar.setName("nullClassVar");
        djVar.setExpression(expression);
        djVar.setCalculation(DJCalculation.NOTHING);
        // className is null

        VariableRegistrationManager manager = new VariableRegistrationManager(djd, dr, layoutManager);
        manager.registerEntities(Collections.singletonList(djVar));

        JRDesignVariable jrVar = findVariable(djd, "nullClassVar");
        assertNotNull("Variable should be registered even without class name", jrVar);
    }

    private CustomExpression createSimpleExpression(final Class<?> returnType) {
        return new CustomExpression() {
            @Override
            public Object evaluate(Map fields, Map variables, Map parameters) {
                return null;
            }

            @Override
            public String getClassName() {
                return returnType.getName();
            }
        };
    }

    private JRDesignVariable findVariable(DynamicJasperDesign design, String name) {
        for (Object var : design.getVariablesList()) {
            if (var instanceof JRDesignVariable) {
                JRDesignVariable jrVar = (JRDesignVariable) var;
                if (name.equals(jrVar.getName())) {
                    return jrVar;
                }
            }
        }
        return null;
    }
}
