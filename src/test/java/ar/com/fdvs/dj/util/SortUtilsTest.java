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

import ar.com.fdvs.dj.domain.builders.ColumnBuilder;
import ar.com.fdvs.dj.domain.entities.columns.AbstractColumn;
import ar.com.fdvs.dj.domain.entities.columns.PropertyColumn;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Unit tests for SortUtils.
 * Tests collection sorting utilities.
 */
public class SortUtilsTest {

    // Simple bean for testing
    public static class TestBean {
        private String name;
        private Integer age;
        private String city;

        public TestBean(String name, Integer age, String city) {
            this.name = name;
            this.age = age;
            this.city = city;
        }

        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        public Integer getAge() { return age; }
        public void setAge(Integer age) { this.age = age; }
        public String getCity() { return city; }
        public void setCity(String city) { this.city = city; }
    }

    @Test
    public void testSortCollectionByStringProperties() {
        List<TestBean> beans = new ArrayList<>();
        beans.add(new TestBean("Charlie", 30, "NYC"));
        beans.add(new TestBean("Alice", 25, "LA"));
        beans.add(new TestBean("Bob", 35, "Chicago"));

        List<TestBean> sorted = SortUtils.sortCollection(beans, new String[]{"name"});

        assertEquals("First should be Alice", "Alice", sorted.get(0).getName());
        assertEquals("Second should be Bob", "Bob", sorted.get(1).getName());
        assertEquals("Third should be Charlie", "Charlie", sorted.get(2).getName());
    }

    @Test
    public void testSortCollectionByMultipleProperties() {
        List<TestBean> beans = new ArrayList<>();
        beans.add(new TestBean("Alice", 30, "NYC"));
        beans.add(new TestBean("Alice", 25, "LA"));
        beans.add(new TestBean("Bob", 35, "Chicago"));

        List<TestBean> sorted = SortUtils.sortCollection(beans, new String[]{"name", "age"});

        assertEquals("First should be Alice", "Alice", sorted.get(0).getName());
        assertEquals("First Alice should be younger", 25, sorted.get(0).getAge().intValue());
        assertEquals("Second Alice should be older", 30, sorted.get(1).getAge().intValue());
        assertEquals("Third should be Bob", "Bob", sorted.get(2).getName());
    }

    @Test
    public void testSortCollectionByPropertyColumns() throws Exception {
        List<TestBean> beans = new ArrayList<>();
        beans.add(new TestBean("Charlie", 30, "NYC"));
        beans.add(new TestBean("Alice", 25, "LA"));
        beans.add(new TestBean("Bob", 35, "Chicago"));

        AbstractColumn column = ColumnBuilder.getNew()
                .setColumnProperty("name", String.class.getName())
                .setTitle("Name")
                .setWidth(100)
                .build();

        List<AbstractColumn> columns = new ArrayList<>();
        columns.add(column);

        List<TestBean> sorted = SortUtils.sortCollection(beans, columns);

        assertEquals("First should be Alice", "Alice", sorted.get(0).getName());
        assertEquals("Second should be Bob", "Bob", sorted.get(1).getName());
        assertEquals("Third should be Charlie", "Charlie", sorted.get(2).getName());
    }

    @Test
    public void testSortCollectionByStringProperty() throws Exception {
        List<TestBean> beans = new ArrayList<>();
        beans.add(new TestBean("Charlie", 30, "NYC"));
        beans.add(new TestBean("Alice", 25, "LA"));
        beans.add(new TestBean("Bob", 35, "Chicago"));

        List<String> columns = new ArrayList<>();
        columns.add("city");

        List<TestBean> sorted = SortUtils.sortCollection(beans, columns);

        assertEquals("First should be Chicago", "Chicago", sorted.get(0).getCity());
        assertEquals("Second should be LA", "LA", sorted.get(1).getCity());
        assertEquals("Third should be NYC", "NYC", sorted.get(2).getCity());
    }

    @Test
    public void testSortCollectionEmptyCollection() {
        List<TestBean> beans = new ArrayList<>();

        List<TestBean> sorted = SortUtils.sortCollection(beans, new String[]{"name"});

        assertNotNull("Result should not be null", sorted);
        assertTrue("Result should be empty", sorted.isEmpty());
    }

    @Test
    public void testSortCollectionSingleElement() {
        List<TestBean> beans = new ArrayList<>();
        beans.add(new TestBean("Alice", 25, "LA"));

        List<TestBean> sorted = SortUtils.sortCollection(beans, new String[]{"name"});

        assertEquals("Should have one element", 1, sorted.size());
        assertEquals("Element should be Alice", "Alice", sorted.get(0).getName());
    }

    @Test
    public void testSortCollectionPreservesOriginal() {
        List<TestBean> original = new ArrayList<>();
        original.add(new TestBean("Charlie", 30, "NYC"));
        original.add(new TestBean("Alice", 25, "LA"));

        List<TestBean> sorted = SortUtils.sortCollection(original, new String[]{"name"});

        // Original should be unchanged
        assertEquals("Charlie", original.get(0).getName());
        assertEquals("Alice", original.get(1).getName());

        // Sorted should be different order
        assertEquals("Alice", sorted.get(0).getName());
        assertEquals("Charlie", sorted.get(1).getName());
    }

    @Test
    public void testSortCollectionByNumericProperty() {
        List<TestBean> beans = new ArrayList<>();
        beans.add(new TestBean("Alice", 30, "LA"));
        beans.add(new TestBean("Bob", 25, "NYC"));
        beans.add(new TestBean("Charlie", 35, "Chicago"));

        List<TestBean> sorted = SortUtils.sortCollection(beans, new String[]{"age"});

        assertEquals("First should be youngest", 25, sorted.get(0).getAge().intValue());
        assertEquals("Second should be middle", 30, sorted.get(1).getAge().intValue());
        assertEquals("Third should be oldest", 35, sorted.get(2).getAge().intValue());
    }
}
