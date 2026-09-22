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
import ar.com.fdvs.dj.domain.builders.ColumnBuilder;
import ar.com.fdvs.dj.domain.builders.DynamicReportBuilder;
import ar.com.fdvs.dj.domain.builders.GroupBuilder;
import ar.com.fdvs.dj.domain.constants.GroupLayout;
import ar.com.fdvs.dj.domain.entities.DJGroup;
import ar.com.fdvs.dj.domain.entities.columns.AbstractColumn;
import ar.com.fdvs.dj.domain.entities.columns.PropertyColumn;
import net.sf.jasperreports.engine.design.JRDesignGroup;
import org.junit.Before;
import org.junit.Test;

import java.util.Collections;

import static org.junit.Assert.*;

/**
 * Unit tests for DJGroupRegistrationManager.
 * Tests the transformation of DJ groups to JasperReports groups.
 */
public class DJGroupRegistrationManagerTest {

    private DynamicJasperDesign djd;
    private ClassicLayoutManager layoutManager;

    @Before
    public void setUp() {
        djd = new DynamicJasperDesign();
        djd.setName("TestReport");
        layoutManager = new ClassicLayoutManager();
    }

    @Test
    public void testAutoGeneratesGroupName() throws Exception {
        AbstractColumn column = ColumnBuilder.getNew()
                .setColumnProperty("state", String.class.getName())
                .setTitle("State")
                .setWidth(85)
                .build();

        DJGroup group = new GroupBuilder()
                .setCriteriaColumn((PropertyColumn) column)
                .setGroupLayout(GroupLayout.DEFAULT)
                .build();

        assertNull("Group name should be null before registration", group.getName());

        DynamicReport dr = new DynamicReportBuilder()
                .addColumn(column)
                .addGroup(group)
                .build();

        DJGroupRegistrationManager manager = new DJGroupRegistrationManager(djd, dr, layoutManager);
        manager.registerEntities(dr.getColumnsGroups());

        assertNotNull("Group name should be set after registration", group.getName());
        assertTrue("Group name should contain report name",
                group.getName().startsWith("TestReport_"));
        assertTrue("Group name should contain 'group'",
                group.getName().contains("group"));
    }

    @Test
    public void testCreatesJRDesignGroup() throws Exception {
        AbstractColumn column = ColumnBuilder.getNew()
                .setColumnProperty("state", String.class.getName())
                .setTitle("State")
                .setWidth(85)
                .build();

        DJGroup group = new GroupBuilder()
                .setCriteriaColumn((PropertyColumn) column)
                .setGroupLayout(GroupLayout.DEFAULT)
                .build();

        DynamicReport dr = new DynamicReportBuilder()
                .addColumn(column)
                .addGroup(group)
                .build();

        DJGroupRegistrationManager manager = new DJGroupRegistrationManager(djd, dr, layoutManager);
        manager.registerEntities(dr.getColumnsGroups());

        // Verify JRDesignGroup was created
        JRDesignGroup jrGroup = findGroup(djd, group.getName());
        assertNotNull("JRDesignGroup should be registered", jrGroup);
        assertEquals("Group names should match", group.getName(), jrGroup.getName());
    }

    @Test
    public void testCreatesGroupExpression() throws Exception {
        AbstractColumn column = ColumnBuilder.getNew()
                .setColumnProperty("state", String.class.getName())
                .setTitle("State")
                .setWidth(85)
                .build();

        DJGroup group = new GroupBuilder()
                .setCriteriaColumn((PropertyColumn) column)
                .setGroupLayout(GroupLayout.DEFAULT)
                .build();

        DynamicReport dr = new DynamicReportBuilder()
                .addColumn(column)
                .addGroup(group)
                .build();

        DJGroupRegistrationManager manager = new DJGroupRegistrationManager(djd, dr, layoutManager);
        manager.registerEntities(dr.getColumnsGroups());

        JRDesignGroup jrGroup = findGroup(djd, group.getName());
        assertNotNull("JRDesignGroup should be registered", jrGroup);
        assertNotNull("Group should have expression", jrGroup.getExpression());
        assertTrue("Group expression should reference field",
                jrGroup.getExpression().getText().contains("state"));
    }

    @Test
    public void testCreatesHeaderAndFooterSections() throws Exception {
        AbstractColumn column = ColumnBuilder.getNew()
                .setColumnProperty("state", String.class.getName())
                .setTitle("State")
                .setWidth(85)
                .build();

        DJGroup group = new GroupBuilder()
                .setCriteriaColumn((PropertyColumn) column)
                .setGroupLayout(GroupLayout.DEFAULT)
                .build();

        DynamicReport dr = new DynamicReportBuilder()
                .addColumn(column)
                .addGroup(group)
                .build();

        DJGroupRegistrationManager manager = new DJGroupRegistrationManager(djd, dr, layoutManager);
        manager.registerEntities(dr.getColumnsGroups());

        JRDesignGroup jrGroup = findGroup(djd, group.getName());
        assertNotNull("JRDesignGroup should be registered", jrGroup);

        // Header and footer sections should be created
        assertNotNull("Header section should exist", jrGroup.getGroupHeaderSection());
        assertNotNull("Footer section should exist", jrGroup.getGroupFooterSection());
        assertFalse("Header section should have bands",
                jrGroup.getGroupHeaderSection().getBands().length == 0);
        assertFalse("Footer section should have bands",
                jrGroup.getGroupFooterSection().getBands().length == 0);
    }

    @Test
    public void testCreatesCountVariable() throws Exception {
        AbstractColumn column = ColumnBuilder.getNew()
                .setColumnProperty("state", String.class.getName())
                .setTitle("State")
                .setWidth(85)
                .build();

        DJGroup group = new GroupBuilder()
                .setCriteriaColumn((PropertyColumn) column)
                .setGroupLayout(GroupLayout.DEFAULT)
                .build();

        DynamicReport dr = new DynamicReportBuilder()
                .addColumn(column)
                .addGroup(group)
                .build();

        DJGroupRegistrationManager manager = new DJGroupRegistrationManager(djd, dr, layoutManager);
        manager.registerEntities(dr.getColumnsGroups());

        JRDesignGroup jrGroup = findGroup(djd, group.getName());
        assertNotNull("JRDesignGroup should be registered", jrGroup);
        assertNotNull("Group should have count variable", jrGroup.getCountVariable());
        assertEquals("Count variable should be Integer",
                Integer.class.getName(), jrGroup.getCountVariable().getValueClassName());
    }

    @Test
    public void testRegistersGroupInLayoutManagerReferences() throws Exception {
        AbstractColumn column = ColumnBuilder.getNew()
                .setColumnProperty("state", String.class.getName())
                .setTitle("State")
                .setWidth(85)
                .build();

        DJGroup group = new GroupBuilder()
                .setCriteriaColumn((PropertyColumn) column)
                .setGroupLayout(GroupLayout.DEFAULT)
                .build();

        DynamicReport dr = new DynamicReportBuilder()
                .addColumn(column)
                .addGroup(group)
                .build();

        DJGroupRegistrationManager manager = new DJGroupRegistrationManager(djd, dr, layoutManager);
        manager.registerEntities(dr.getColumnsGroups());

        // Verify group is registered in layout manager's references
        assertTrue("Group should be registered in references map",
                layoutManager.getReferencesMap().containsKey(group.getName()));
        assertEquals("References should map to DJGroup",
                group, layoutManager.getReferencesMap().get(group.getName()));
    }

    @Test
    public void testMultipleGroupsGetUniqueNames() throws Exception {
        AbstractColumn stateColumn = ColumnBuilder.getNew()
                .setColumnProperty("state", String.class.getName())
                .setTitle("State")
                .setWidth(85)
                .build();

        AbstractColumn branchColumn = ColumnBuilder.getNew()
                .setColumnProperty("branch", String.class.getName())
                .setTitle("Branch")
                .setWidth(85)
                .build();

        DJGroup stateGroup = new GroupBuilder()
                .setCriteriaColumn((PropertyColumn) stateColumn)
                .setGroupLayout(GroupLayout.DEFAULT)
                .build();

        DJGroup branchGroup = new GroupBuilder()
                .setCriteriaColumn((PropertyColumn) branchColumn)
                .setGroupLayout(GroupLayout.DEFAULT)
                .build();

        DynamicReport dr = new DynamicReportBuilder()
                .addColumn(stateColumn)
                .addColumn(branchColumn)
                .addGroup(stateGroup)
                .addGroup(branchGroup)
                .build();

        DJGroupRegistrationManager manager = new DJGroupRegistrationManager(djd, dr, layoutManager);
        manager.registerEntities(dr.getColumnsGroups());

        assertNotNull("State group name should be set", stateGroup.getName());
        assertNotNull("Branch group name should be set", branchGroup.getName());
        assertNotEquals("Group names should be unique",
                stateGroup.getName(), branchGroup.getName());
    }

    private JRDesignGroup findGroup(DynamicJasperDesign design, String name) {
        for (Object group : design.getGroupsList()) {
            if (group instanceof JRDesignGroup) {
                JRDesignGroup jrGroup = (JRDesignGroup) group;
                if (name.equals(jrGroup.getName())) {
                    return jrGroup;
                }
            }
        }
        return null;
    }
}
