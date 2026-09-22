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
import ar.com.fdvs.dj.domain.DynamicJasperDesign;
import ar.com.fdvs.dj.domain.DynamicReport;
import ar.com.fdvs.dj.domain.Style;
import ar.com.fdvs.dj.domain.builders.DynamicReportBuilder;
import ar.com.fdvs.dj.domain.entities.conditionalStyle.ConditionalStyle;
import ar.com.fdvs.dj.domain.entities.conditionalStyle.ConditionStyleExpression;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * Unit tests for ConditionalStylesRegistrationManager.
 * Tests the registration of conditional style expressions.
 */
public class ConditionalStylesRegistrationManagerTest {

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
    public void testRegistersConditionalStyleExpression() throws Exception {
        ConditionStyleExpression condition = new ConditionStyleExpression() {
            @Override
            public Object evaluate(Map fields, Map variables, Map parameters) {
                return true;
            }

            @Override
            public String getClassName() {
                return Boolean.class.getName();
            }
        };

        ConditionalStyle conditionalStyle = new ConditionalStyle(condition, new Style());

        ConditionalStylesRegistrationManager manager =
                new ConditionalStylesRegistrationManager(djd, dr, "testColumn", layoutManager);
        manager.registerEntities(Collections.singletonList(conditionalStyle));

        // Expression should be registered as parameter
        Map<String, Object> params = djd.getParametersWithValues();
        boolean found = false;
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            if (entry.getKey().contains("testColumn_style_") && entry.getValue() == condition) {
                found = true;
                break;
            }
        }
        assertTrue("Condition expression should be registered as parameter", found);
    }

    @Test
    public void testSetsConditionalStyleName() throws Exception {
        ConditionStyleExpression condition = createSimpleCondition();
        ConditionalStyle conditionalStyle = new ConditionalStyle(condition, new Style());

        assertNull("Name should be null before registration", conditionalStyle.getName());

        ConditionalStylesRegistrationManager manager =
                new ConditionalStylesRegistrationManager(djd, dr, "myColumn", layoutManager);
        manager.registerEntities(Collections.singletonList(conditionalStyle));

        assertNotNull("Name should be set after registration", conditionalStyle.getName());
        assertTrue("Name should contain column name",
                conditionalStyle.getName().contains("myColumn"));
        assertTrue("Name should contain 'style'",
                conditionalStyle.getName().contains("_style_"));
    }

    @Test
    public void testMultipleStylesGetUniqueNames() throws Exception {
        ConditionalStyle style1 = new ConditionalStyle(createSimpleCondition(), new Style());
        ConditionalStyle style2 = new ConditionalStyle(createSimpleCondition(), new Style());
        ConditionalStyle style3 = new ConditionalStyle(createSimpleCondition(), new Style());

        ConditionalStylesRegistrationManager manager =
                new ConditionalStylesRegistrationManager(djd, dr, "column", layoutManager);
        manager.registerEntities(Arrays.asList(style1, style2, style3));

        // All names should be unique
        assertNotEquals("Style names should be unique",
                style1.getName(), style2.getName());
        assertNotEquals("Style names should be unique",
                style2.getName(), style3.getName());
        assertNotEquals("Style names should be unique",
                style1.getName(), style3.getName());

        // Names should follow pattern with counter
        assertEquals("First style should have counter 0", "column_style_0", style1.getName());
        assertEquals("Second style should have counter 1", "column_style_1", style2.getName());
        assertEquals("Third style should have counter 2", "column_style_2", style3.getName());
    }

    @Test
    public void testTransformEntityReturnsNull() throws Exception {
        ConditionalStyle conditionalStyle = new ConditionalStyle(createSimpleCondition(), new Style());

        ConditionalStylesRegistrationManager manager =
                new ConditionalStylesRegistrationManager(djd, dr, "column", layoutManager);

        // transformEntity should return null (this manager only registers, doesn't transform)
        Object result = manager.transformEntity(conditionalStyle);
        assertNull("transformEntity should return null", result);
    }

    private ConditionStyleExpression createSimpleCondition() {
        return new ConditionStyleExpression() {
            @Override
            public Object evaluate(Map fields, Map variables, Map parameters) {
                return true;
            }

            @Override
            public String getClassName() {
                return Boolean.class.getName();
            }
        };
    }
}
