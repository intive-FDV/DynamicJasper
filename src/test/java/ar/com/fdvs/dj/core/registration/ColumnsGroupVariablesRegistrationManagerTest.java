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
import ar.com.fdvs.dj.domain.DJCalculation;
import ar.com.fdvs.dj.domain.DJValueFormatter;
import ar.com.fdvs.dj.domain.DynamicJasperDesign;
import ar.com.fdvs.dj.domain.DynamicReport;
import ar.com.fdvs.dj.domain.builders.ColumnBuilder;
import ar.com.fdvs.dj.domain.builders.DynamicReportBuilder;
import ar.com.fdvs.dj.domain.builders.GroupBuilder;
import ar.com.fdvs.dj.domain.constants.GroupLayout;
import ar.com.fdvs.dj.domain.entities.DJGroup;
import ar.com.fdvs.dj.domain.entities.DJGroupVariable;
import ar.com.fdvs.dj.domain.entities.columns.AbstractColumn;
import ar.com.fdvs.dj.domain.entities.columns.PropertyColumn;
import net.sf.jasperreports.engine.design.JRDesignGroup;
import net.sf.jasperreports.engine.design.JRDesignVariable;
import net.sf.jasperreports.engine.type.CalculationEnum;
import net.sf.jasperreports.engine.type.ResetTypeEnum;
import org.junit.Before;
import org.junit.Test;

import java.util.Collections;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * Unit tests for ColumnsGroupVariablesRegistrationManager.
 * Tests the transformation of DJ group variables (header/footer) to JasperReports variables.
 */
public class ColumnsGroupVariablesRegistrationManagerTest {

    private DynamicJasperDesign djd;
    private DynamicReport dr;
    private ClassicLayoutManager layoutManager;

    public static final String HEADER = "header";
    public static final String FOOTER = "footer";

    @Before
    public void setUp() throws Exception {
        djd = new DynamicJasperDesign();
        djd.setName("TestReport");

        // Create a report with a group so we have a registered group
        AbstractColumn stateColumn = ColumnBuilder.getNew()
                .setColumnProperty("state", String.class.getName())
                .setTitle("State")
                .setWidth(85)
                .build();

        AbstractColumn amountColumn = ColumnBuilder.getNew()
                .setColumnProperty("amount", Float.class.getName())
                .setTitle("Amount")
                .setWidth(80)
                .build();

        DJGroup group = new GroupBuilder()
                .setCriteriaColumn((PropertyColumn) stateColumn)
                .setGroupLayout(GroupLayout.DEFAULT)
                .build();

        dr = new DynamicReportBuilder()
                .addColumn(stateColumn)
                .addColumn(amountColumn)
                .addGroup(group)
                .build();

        layoutManager = new ClassicLayoutManager();

        // Register a JRDesignGroup in the design (simulating what DJGroupRegistrationManager does)
        JRDesignGroup jrGroup = new JRDesignGroup();
        jrGroup.setName("TestGroup");
        djd.addGroup(jrGroup);
    }

    @Test
    public void testCreatesFooterVariable() throws Exception {
        AbstractColumn amountColumn = dr.getColumns().get(1); // amount column

        DJGroupVariable groupVar = new DJGroupVariable(amountColumn, DJCalculation.SUM);

        ColumnsGroupVariablesRegistrationManager manager =
                new ColumnsGroupVariablesRegistrationManager(FOOTER, "state", djd, dr, layoutManager);
        manager.registerEntities(Collections.singletonList(groupVar));

        // Variable should be registered
        assertNotNull("Variable name should be set", groupVar.getName());
        assertTrue("Variable name should contain report name",
                groupVar.getName().startsWith("TestReport_"));

        JRDesignVariable jrVar = findVariable(djd, groupVar.getName());
        assertNotNull("Variable should be registered", jrVar);
        assertEquals("Calculation should be SUM",
                CalculationEnum.SUM, jrVar.getCalculationValue());
    }

    @Test
    public void testCreatesHeaderVariable() throws Exception {
        AbstractColumn amountColumn = dr.getColumns().get(1);

        DJGroupVariable groupVar = new DJGroupVariable(amountColumn, DJCalculation.COUNT);

        ColumnsGroupVariablesRegistrationManager manager =
                new ColumnsGroupVariablesRegistrationManager(HEADER, "state", djd, dr, layoutManager);
        manager.registerEntities(Collections.singletonList(groupVar));

        assertNotNull("Variable name should be set", groupVar.getName());

        JRDesignVariable jrVar = findVariable(djd, groupVar.getName());
        assertNotNull("Variable should be registered", jrVar);
        assertEquals("Calculation should be COUNT",
                CalculationEnum.COUNT, jrVar.getCalculationValue());
    }

    @Test
    public void testSetsResetTypeToGroup() throws Exception {
        AbstractColumn amountColumn = dr.getColumns().get(1);

        DJGroupVariable groupVar = new DJGroupVariable(amountColumn, DJCalculation.SUM);

        ColumnsGroupVariablesRegistrationManager manager =
                new ColumnsGroupVariablesRegistrationManager(FOOTER, "state", djd, dr, layoutManager);
        manager.registerEntities(Collections.singletonList(groupVar));

        JRDesignVariable jrVar = findVariable(djd, groupVar.getName());
        assertNotNull("Variable should be registered", jrVar);
        assertEquals("Reset type should be GROUP",
                ResetTypeEnum.GROUP, jrVar.getResetTypeValue());
    }

    @Test
    public void testRegistersValueFormatter() throws Exception {
        AbstractColumn amountColumn = dr.getColumns().get(1);

        DJValueFormatter formatter = new DJValueFormatter() {
            @Override
            public Object evaluate(Object value, Map fields, Map variables, Map parameters) {
                return "formatted: " + value;
            }

            @Override
            public String getClassName() {
                return String.class.getName();
            }
        };

        DJGroupVariable groupVar = new DJGroupVariable(amountColumn, DJCalculation.SUM, null, formatter);

        ColumnsGroupVariablesRegistrationManager manager =
                new ColumnsGroupVariablesRegistrationManager(FOOTER, "state", djd, dr, layoutManager);
        manager.registerEntities(Collections.singletonList(groupVar));

        // Value formatter should be registered with _vf suffix
        Map<String, Object> params = djd.getParametersWithValues();
        boolean formatterFound = false;
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            if (entry.getKey().endsWith("_vf") && entry.getValue() == formatter) {
                formatterFound = true;
                break;
            }
        }
        assertTrue("Value formatter should be registered with _vf suffix", formatterFound);
    }

    @Test
    public void testMapsCalculationCorrectly() throws Exception {
        AbstractColumn amountColumn = dr.getColumns().get(1);

        // Test AVERAGE calculation
        DJGroupVariable groupVar = new DJGroupVariable(amountColumn, DJCalculation.AVERAGE);

        ColumnsGroupVariablesRegistrationManager manager =
                new ColumnsGroupVariablesRegistrationManager(FOOTER, "state", djd, dr, layoutManager);
        manager.registerEntities(Collections.singletonList(groupVar));

        JRDesignVariable jrVar = findVariable(djd, groupVar.getName());
        assertNotNull("Variable should be registered", jrVar);
        assertEquals("Calculation should be AVERAGE",
                CalculationEnum.AVERAGE, jrVar.getCalculationValue());
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
