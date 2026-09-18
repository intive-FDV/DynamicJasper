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
package ar.com.fdvs.dj.domain.builders;

import ar.com.fdvs.dj.domain.ColumnProperty;
import ar.com.fdvs.dj.domain.CustomExpression;
import ar.com.fdvs.dj.domain.DJCalculation;
import ar.com.fdvs.dj.domain.DJGroupLabel;
import ar.com.fdvs.dj.domain.Style;
import ar.com.fdvs.dj.domain.constants.GroupLayout;
import ar.com.fdvs.dj.domain.entities.DJGroup;
import ar.com.fdvs.dj.domain.entities.DJGroupVariable;
import ar.com.fdvs.dj.domain.entities.columns.AbstractColumn;
import ar.com.fdvs.dj.domain.entities.columns.PropertyColumn;
import org.junit.Before;
import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.*;

/**
 * Unit tests for GroupBuilder.
 * Tests group construction with variables, layouts, and configurations.
 */
public class GroupBuilderTest {

    private PropertyColumn stateColumn;
    private AbstractColumn amountColumn;

    @Before
    public void setUp() throws ColumnBuilderException {
        stateColumn = (PropertyColumn) ColumnBuilder.getNew()
                .setColumnProperty("state", String.class.getName())
                .setTitle("State")
                .setWidth(100)
                .build();

        amountColumn = ColumnBuilder.getNew()
                .setColumnProperty("amount", Float.class.getName())
                .setTitle("Amount")
                .setWidth(80)
                .build();
    }

    @Test
    public void testBuildEmptyGroup() {
        DJGroup group = new GroupBuilder().build();

        assertNotNull("Group should not be null", group);
    }

    @Test
    public void testBuildGroupWithName() {
        DJGroup group = new GroupBuilder("stateGroup").build();

        assertEquals("Name should match", "stateGroup", group.getName());
    }

    @Test
    public void testSetCriteriaColumn() {
        DJGroup group = new GroupBuilder()
                .setCriteriaColumn(stateColumn)
                .build();

        assertSame("Criteria column should match", stateColumn, group.getColumnToGroupBy());
    }

    @Test
    public void testSetGroupLayout() {
        DJGroup group = new GroupBuilder()
                .setCriteriaColumn(stateColumn)
                .setGroupLayout(GroupLayout.VALUE_IN_HEADER)
                .build();

        assertEquals("Layout should match", GroupLayout.VALUE_IN_HEADER, group.getLayout());
    }

    @Test
    public void testAddHeaderVariable() {
        DJGroup group = new GroupBuilder()
                .setCriteriaColumn(stateColumn)
                .addHeaderVariable(amountColumn, DJCalculation.SUM)
                .build();

        assertEquals("Should have one header variable", 1, group.getHeaderVariables().size());
    }

    @Test
    public void testAddHeaderVariableWithStyle() {
        Style style = new Style("varStyle");

        DJGroup group = new GroupBuilder()
                .setCriteriaColumn(stateColumn)
                .addHeaderVariable(amountColumn, DJCalculation.SUM, style)
                .build();

        assertEquals("Should have one header variable", 1, group.getHeaderVariables().size());
        assertSame("Style should match", style, group.getHeaderVariables().get(0).getStyle());
    }

    @Test
    public void testAddHeaderVariableObject() {
        DJGroupVariable var = new DJGroupVariable(amountColumn, DJCalculation.COUNT);

        DJGroup group = new GroupBuilder()
                .setCriteriaColumn(stateColumn)
                .addHeaderVariable(var)
                .build();

        assertEquals("Should have one header variable", 1, group.getHeaderVariables().size());
    }

    @Test
    public void testAddFooterVariable() {
        DJGroup group = new GroupBuilder()
                .setCriteriaColumn(stateColumn)
                .addFooterVariable(amountColumn, DJCalculation.SUM)
                .build();

        assertEquals("Should have one footer variable", 1, group.getFooterVariables().size());
    }

    @Test
    public void testAddFooterVariableWithStyle() {
        Style style = new Style("footerStyle");

        DJGroup group = new GroupBuilder()
                .setCriteriaColumn(stateColumn)
                .addFooterVariable(amountColumn, DJCalculation.AVERAGE, style)
                .build();

        assertEquals("Should have one footer variable", 1, group.getFooterVariables().size());
    }

    @Test
    public void testAddFooterVariableWithCustomExpression() {
        CustomExpression expr = new CustomExpression() {
            @Override
            public Object evaluate(Map fields, Map variables, Map parameters) {
                return 100;
            }
            @Override
            public String getClassName() {
                return Integer.class.getName();
            }
        };

        DJGroup group = new GroupBuilder()
                .setCriteriaColumn(stateColumn)
                .addFooterVariable(amountColumn, expr)
                .build();

        assertEquals("Should have one footer variable", 1, group.getFooterVariables().size());
    }

    @Test
    public void testDefaultVariableStyles() {
        Style headerStyle = new Style("headerVarStyle");
        Style footerStyle = new Style("footerVarStyle");

        DJGroup group = new GroupBuilder()
                .setCriteriaColumn(stateColumn)
                .setDefaultHeaderVariableStyle(headerStyle)
                .setDefaultFooterVariableStyle(footerStyle)
                .addHeaderVariable(amountColumn, DJCalculation.SUM)
                .addFooterVariable(amountColumn, DJCalculation.SUM)
                .build();

        assertSame("Header variable should have default style",
                headerStyle, group.getHeaderVariables().get(0).getStyle());
        assertSame("Footer variable should have default style",
                footerStyle, group.getFooterVariables().get(0).getStyle());
    }

    @Test
    public void testSetHeaderHeight() {
        DJGroup group = new GroupBuilder()
                .setCriteriaColumn(stateColumn)
                .setHeaderHeight(30)
                .build();

        assertEquals("Header height should match", 30, group.getHeaderHeight());
    }

    @Test
    public void testSetHeaderHeightWithFitContent() {
        DJGroup group = new GroupBuilder()
                .setCriteriaColumn(stateColumn)
                .setHeaderHeight(30, false)
                .build();

        assertEquals("Header height should match", 30, group.getHeaderHeight());
        assertFalse("Fit height to content should be false", group.isFitHeaderHeightToContent());
    }

    @Test
    public void testSetFooterHeight() {
        DJGroup group = new GroupBuilder()
                .setCriteriaColumn(stateColumn)
                .setFooterHeight(25)
                .build();

        assertEquals("Footer height should match", 25, group.getFooterHeight());
    }

    @Test
    public void testSetFooterHeightWithFitContent() {
        DJGroup group = new GroupBuilder()
                .setCriteriaColumn(stateColumn)
                .setFooterHeight(25, true)
                .build();

        assertEquals("Footer height should match", 25, group.getFooterHeight());
        assertTrue("Fit footer height to content should be true", group.isFitFooterHeightToContent());
    }

    @Test
    public void testSetHeaderVariablesHeight() {
        DJGroup group = new GroupBuilder()
                .setCriteriaColumn(stateColumn)
                .setHeaderVariablesHeight(20)
                .build();

        assertEquals("Header variables height should match", 20, group.getHeaderVariablesHeight());
    }

    @Test
    public void testSetFooterVariablesHeight() {
        DJGroup group = new GroupBuilder()
                .setCriteriaColumn(stateColumn)
                .setFooterVariablesHeight(20)
                .build();

        assertEquals("Footer variables height should match", 20, group.getFooterVariablesHeight());
    }

    @Test
    public void testSetStartInNewPage() {
        DJGroup group = new GroupBuilder()
                .setCriteriaColumn(stateColumn)
                .setStartInNewPage(true)
                .build();

        assertTrue("Start in new page should be true", group.isStartInNewPage());
    }

    @Test
    public void testSetStartInNewColumn() {
        DJGroup group = new GroupBuilder()
                .setCriteriaColumn(stateColumn)
                .setStartInNewColumn(true)
                .build();

        assertTrue("Start in new column should be true", group.isStartInNewColumn());
    }

    @Test
    public void testAllowHeaderSplit() {
        DJGroup group = new GroupBuilder()
                .setCriteriaColumn(stateColumn)
                .setAllowHeaderSplit(false)
                .build();

        assertFalse("Allow header split should be false", group.isAllowHeaderSplit());
    }

    @Test
    public void testAllowFooterSplit() {
        DJGroup group = new GroupBuilder()
                .setCriteriaColumn(stateColumn)
                .setAllowFooterSplit(false)
                .build();

        assertFalse("Allow footer split should be false", group.isAllowFooterSplit());
    }

    @Test
    public void testAllowSplitting() {
        DJGroup group = new GroupBuilder()
                .setCriteriaColumn(stateColumn)
                .setAllowSplitting(false, false)
                .build();

        assertFalse("Allow header split should be false", group.isAllowHeaderSplit());
        assertFalse("Allow footer split should be false", group.isAllowFooterSplit());
    }

    @Test
    public void testSetFooterLabel() {
        DJGroupLabel label = new DJGroupLabel("Total:", new Style());

        DJGroup group = new GroupBuilder()
                .setCriteriaColumn(stateColumn)
                .setFooterLabel(label)
                .build();

        assertSame("Footer label should match", label, group.getFooterLabel());
    }

    @Test
    public void testSetReprintHeaderOnEachPage() {
        DJGroup group = new GroupBuilder()
                .setCriteriaColumn(stateColumn)
                .setReprintHeaderOnEachPage(true)
                .build();

        assertTrue("Reprint header on each page should be true", group.isReprintHeaderOnEachPage());
    }

    @Test
    public void testSetResetPageNumber() {
        DJGroup group = new GroupBuilder()
                .setCriteriaColumn(stateColumn)
                .setResetPageNumber(true)
                .build();

        assertTrue("Reset page number should be true", group.isResetPageNumber());
    }

    @Test
    public void testAddVariable() {
        DJGroup group = new GroupBuilder()
                .setCriteriaColumn(stateColumn)
                .addVariable("totalAmount", "amount", Float.class.getName(), DJCalculation.SUM)
                .build();

        assertEquals("Should have one variable", 1, group.getVariables().size());
        assertEquals("Variable name should match", "totalAmount", group.getVariables().get(0).getName());
    }

    @Test
    public void testAddVariableWithColumnProperty() {
        ColumnProperty prop = new ColumnProperty("quantity", Integer.class.getName());

        DJGroup group = new GroupBuilder()
                .setCriteriaColumn(stateColumn)
                .addVariable("totalQty", prop, DJCalculation.COUNT)
                .build();

        assertEquals("Should have one variable", 1, group.getVariables().size());
    }

    @Test
    public void testAddVariableWithColumn() {
        DJGroup group = new GroupBuilder()
                .setCriteriaColumn(stateColumn)
                .addVariable("sumAmount", amountColumn, DJCalculation.SUM)
                .build();

        assertEquals("Should have one variable", 1, group.getVariables().size());
    }

    @Test
    public void testAddColumnHeaderStyle() {
        Style style = new Style("colHeaderStyle");

        DJGroup group = new GroupBuilder()
                .setCriteriaColumn(stateColumn)
                .addColumnHeaderStyle(amountColumn, style)
                .build();

        assertNotNull("Column header styles should not be null", group.getColumnHeaderStyles());
    }

    @Test
    public void testSetDefaultColumnHeaderStyle() {
        Style style = new Style("defaultColHeader");

        DJGroup group = new GroupBuilder()
                .setCriteriaColumn(stateColumn)
                .setDefaultColumnHeaderStyle(style)
                .build();

        assertSame("Default column header style should match", style, group.getDefaultColumnHeaederStyle());
    }
}
