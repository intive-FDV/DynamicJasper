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
import ar.com.fdvs.dj.domain.ColumnProperty;
import ar.com.fdvs.dj.domain.DJCalculation;
import ar.com.fdvs.dj.domain.DynamicJasperDesign;
import ar.com.fdvs.dj.domain.DynamicReport;
import ar.com.fdvs.dj.domain.builders.ColumnBuilder;
import ar.com.fdvs.dj.domain.builders.DynamicReportBuilder;
import ar.com.fdvs.dj.domain.entities.DJGroupVariableDef;
import ar.com.fdvs.dj.domain.entities.columns.AbstractColumn;
import net.sf.jasperreports.engine.design.JRDesignGroup;
import net.sf.jasperreports.engine.design.JRDesignVariable;
import net.sf.jasperreports.engine.type.CalculationEnum;
import net.sf.jasperreports.engine.type.ResetTypeEnum;
import org.junit.Before;
import org.junit.Test;

import java.util.Collections;

import static org.junit.Assert.*;

/**
 * Unit tests for DJGroupVariableDefRegistrationManager.
 * Tests the transformation of DJ group variable definitions to JasperReports variables.
 */
public class DJGroupVariableDefRegistrationManagerTest {

    private DynamicJasperDesign djd;
    private DynamicReport dr;
    private ClassicLayoutManager layoutManager;
    private JRDesignGroup jrGroup;

    @Before
    public void setUp() throws Exception {
        djd = new DynamicJasperDesign();
        djd.setName("TestReport");
        dr = new DynamicReportBuilder().build();
        layoutManager = new ClassicLayoutManager();

        // Create a JRDesignGroup for testing
        jrGroup = new JRDesignGroup();
        jrGroup.setName("TestGroup");
    }

    @Test
    public void testCreatesVariableFromColumnProperty() throws Exception {
        ColumnProperty prop = new ColumnProperty("amount", Float.class.getName());

        DJGroupVariableDef varDef = new DJGroupVariableDef(
                "sumAmount", prop, DJCalculation.SUM);

        DJGroupVariableDefRegistrationManager manager =
                new DJGroupVariableDefRegistrationManager(djd, dr, layoutManager, jrGroup);
        manager.registerEntities(Collections.singletonList(varDef));

        // Find registered variable
        JRDesignVariable jrVar = findVariable(djd, "TestReport_sumAmount");
        assertNotNull("Variable should be registered", jrVar);
        assertTrue("Expression should reference field",
                jrVar.getExpression().getText().contains("$F{amount}"));
    }

    @Test
    public void testCreatesVariableFromColumn() throws Exception {
        AbstractColumn column = ColumnBuilder.getNew()
                .setColumnProperty("quantity", Long.class.getName())
                .setTitle("Quantity")
                .setWidth(80)
                .build();

        DJGroupVariableDef varDef = new DJGroupVariableDef(
                "sumQuantity", column, DJCalculation.SUM);

        DJGroupVariableDefRegistrationManager manager =
                new DJGroupVariableDefRegistrationManager(djd, dr, layoutManager, jrGroup);
        manager.registerEntities(Collections.singletonList(varDef));

        JRDesignVariable jrVar = findVariable(djd, "TestReport_sumQuantity");
        assertNotNull("Variable should be registered", jrVar);
        assertEquals("Calculation should be SUM",
                CalculationEnum.SUM, jrVar.getCalculationValue());
    }

    @Test
    public void testSetsResetTypeToGroup() throws Exception {
        ColumnProperty prop = new ColumnProperty("amount", Float.class.getName());

        DJGroupVariableDef varDef = new DJGroupVariableDef(
                "groupVar", prop, DJCalculation.SUM);

        DJGroupVariableDefRegistrationManager manager =
                new DJGroupVariableDefRegistrationManager(djd, dr, layoutManager, jrGroup);
        manager.registerEntities(Collections.singletonList(varDef));

        JRDesignVariable jrVar = findVariable(djd, "TestReport_groupVar");
        assertNotNull("Variable should be registered", jrVar);
        assertEquals("Reset type should be GROUP",
                ResetTypeEnum.GROUP, jrVar.getResetTypeValue());
        assertEquals("Reset group should match",
                "TestGroup", jrVar.getResetGroup().getName());
    }

    @Test
    public void testRegistersFieldForColumnProperty() throws Exception {
        ColumnProperty prop = new ColumnProperty("newField", Double.class.getName());

        DJGroupVariableDef varDef = new DJGroupVariableDef(
                "fieldVar", prop, DJCalculation.SUM);

        DJGroupVariableDefRegistrationManager manager =
                new DJGroupVariableDefRegistrationManager(djd, dr, layoutManager, jrGroup);
        manager.registerEntities(Collections.singletonList(varDef));

        // Field should be registered
        assertNotNull("Field should be registered",
                djd.getFieldsMap().get("newField"));
        assertEquals("Field class should match",
                Double.class.getName(),
                djd.getFieldsMap().get("newField").getValueClassName());
    }

    @Test
    public void testMapsCalculationCorrectly() throws Exception {
        ColumnProperty prop = new ColumnProperty("value", Integer.class.getName());

        // Test COUNT calculation
        DJGroupVariableDef countVar = new DJGroupVariableDef(
                "countVar", prop, DJCalculation.COUNT);

        DJGroupVariableDefRegistrationManager manager =
                new DJGroupVariableDefRegistrationManager(djd, dr, layoutManager, jrGroup);
        manager.registerEntities(Collections.singletonList(countVar));

        JRDesignVariable jrVar = findVariable(djd, "TestReport_countVar");
        assertNotNull("Variable should be registered", jrVar);
        assertEquals("Calculation should be COUNT",
                CalculationEnum.COUNT, jrVar.getCalculationValue());
    }

    @Test
    public void testPrefixesVariableNameWithReportName() throws Exception {
        ColumnProperty prop = new ColumnProperty("amount", Float.class.getName());

        DJGroupVariableDef varDef = new DJGroupVariableDef(
                "myVar", prop, DJCalculation.SUM);

        DJGroupVariableDefRegistrationManager manager =
                new DJGroupVariableDefRegistrationManager(djd, dr, layoutManager, jrGroup);
        manager.registerEntities(Collections.singletonList(varDef));

        // Variable name should be prefixed with report name
        JRDesignVariable jrVar = findVariable(djd, "TestReport_myVar");
        assertNotNull("Variable should have prefixed name", jrVar);

        // Original name without prefix should not exist
        assertNull("Variable without prefix should not exist",
                findVariable(djd, "myVar"));
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
