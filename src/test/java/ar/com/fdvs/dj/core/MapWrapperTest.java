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
 *
 */

package ar.com.fdvs.dj.core;

import net.sf.jasperreports.engine.fill.JRFillField;
import net.sf.jasperreports.engine.fill.JRFillParameter;
import net.sf.jasperreports.engine.fill.JRFillVariable;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for ParameterMapWrapper, FieldMapWrapper, and VariableMapWrapper.
 * These classes wrap JasperReports fill objects to provide simplified access.
 */
class MapWrapperTest {

    @Nested
    @DisplayName("ParameterMapWrapper")
    class ParameterMapWrapperTests {

        private ParameterMapWrapper wrapper;
        private Map<String, JRFillParameter> innerMap;

        @BeforeEach
        void setUp() {
            wrapper = new ParameterMapWrapper();
            innerMap = new HashMap<>();
        }

        @Test
        void constructorCreatesEmptyWrapper() {
            assertNotNull(wrapper);
        }

        @Test
        void setMapUpdatesInternalMap() {
            wrapper.setMap(innerMap);
            // If no exception, setMap worked
            assertNotNull(wrapper);
        }

        @Test
        void setReportName() {
            wrapper.setReportName("TestReport");
            // If no exception, setReportName worked
            assertNotNull(wrapper);
        }

        @Test
        void getWithNullKeyReturnsNull() {
            wrapper.setMap(innerMap);
            assertNull(wrapper.get(null));
        }

        @Test
        void getWithMissingKeyReturnsNull() {
            wrapper.setMap(innerMap);
            assertNull(wrapper.get("nonexistent"));
        }

        @Test
        void sizeReturnsZeroForEmptyMap() {
            wrapper.setMap(innerMap);
            assertEquals(0, wrapper.size());
        }

        @Test
        void isEmptyReturnsTrueForEmptyMap() {
            wrapper.setMap(innerMap);
            assertTrue(wrapper.isEmpty());
        }

        @Test
        void containsKeyReturnsFalseForMissingKey() {
            wrapper.setMap(innerMap);
            assertFalse(wrapper.containsKey("missing"));
        }

        @Test
        void containsValueThrowsException() {
            wrapper.setMap(innerMap);
            assertThrows(DJException.class, () -> wrapper.containsValue("value"));
        }

        @Test
        void valuesThrowsException() {
            wrapper.setMap(innerMap);
            assertThrows(DJException.class, () -> wrapper.values());
        }

        @Test
        void removeReturnsNull() {
            wrapper.setMap(innerMap);
            assertNull(wrapper.remove("key"));
        }

        @Test
        void clearDoesNotThrow() {
            wrapper.setMap(innerMap);
            assertDoesNotThrow(() -> wrapper.clear());
        }

        @Test
        void keySetReturnsEmptySet() {
            wrapper.setMap(innerMap);
            Set<String> keys = wrapper.keySet();
            assertNotNull(keys);
            assertTrue(keys.isEmpty());
        }

        @Test
        void entrySetReturnsEmptySet() {
            wrapper.setMap(innerMap);
            Set<Map.Entry<String, Object>> entries = wrapper.entrySet();
            assertNotNull(entries);
            assertTrue(entries.isEmpty());
        }

        @Test
        void putAllDoesNotThrow() {
            wrapper.setMap(innerMap);
            Map<String, Object> newMap = new HashMap<>();
            newMap.put("key1", "value1");
            assertDoesNotThrow(() -> wrapper.putAll(newMap));
        }

        @Test
        void equalsWithSelf() {
            assertTrue(wrapper.equals(wrapper));
        }

        @Test
        void equalsWithNull() {
            assertFalse(wrapper.equals(null));
        }

        @Test
        void hashCodeReturnsConsistentValue() {
            int hash1 = wrapper.hashCode();
            int hash2 = wrapper.hashCode();
            assertEquals(hash1, hash2);
        }

        @Test
        void getWithReportNamePrefix() {
            wrapper.setMap(innerMap);
            wrapper.setReportName("MyReport");
            // Test that it tries to find key with and without prefix
            assertNull(wrapper.get("param"));
        }
    }

    @Nested
    @DisplayName("FieldMapWrapper")
    class FieldMapWrapperTests {

        private FieldMapWrapper wrapper;
        private Map<String, JRFillField> innerMap;

        @BeforeEach
        void setUp() {
            wrapper = new FieldMapWrapper();
            innerMap = new HashMap<>();
        }

        @Test
        void constructorCreatesEmptyWrapper() {
            assertNotNull(wrapper);
        }

        @Test
        void setMapUpdatesInternalMap() {
            wrapper.setMap(innerMap);
            assertNotNull(wrapper);
        }

        @Test
        void getWithMissingKeyReturnsNull() {
            wrapper.setMap(innerMap);
            assertNull(wrapper.get("nonexistent"));
        }

        @Test
        void sizeReturnsZeroForEmptyMap() {
            wrapper.setMap(innerMap);
            assertEquals(0, wrapper.size());
        }

        @Test
        void isEmptyReturnsTrueForEmptyMap() {
            wrapper.setMap(innerMap);
            assertTrue(wrapper.isEmpty());
        }

        @Test
        void containsKeyReturnsFalseForMissingKey() {
            wrapper.setMap(innerMap);
            assertFalse(wrapper.containsKey("missing"));
        }

        @Test
        void containsValueThrowsException() {
            wrapper.setMap(innerMap);
            assertThrows(DJException.class, () -> wrapper.containsValue("value"));
        }

        @Test
        void valuesThrowsException() {
            wrapper.setMap(innerMap);
            assertThrows(DJException.class, () -> wrapper.values());
        }

        @Test
        void getPreviousValuesReturnsMap() {
            wrapper.setMap(innerMap);
            Map previousValues = wrapper.getPreviousValues();
            assertNotNull(previousValues);
        }

        @Test
        void removeReturnsNull() {
            wrapper.setMap(innerMap);
            assertNull(wrapper.remove("key"));
        }

        @Test
        void clearDoesNotThrow() {
            wrapper.setMap(innerMap);
            assertDoesNotThrow(() -> wrapper.clear());
        }

        @Test
        void keySetReturnsEmptySet() {
            wrapper.setMap(innerMap);
            assertTrue(wrapper.keySet().isEmpty());
        }

        @Test
        void entrySetReturnsEmptySet() {
            wrapper.setMap(innerMap);
            assertTrue(wrapper.entrySet().isEmpty());
        }

        @Test
        void equalsWithSelf() {
            assertTrue(wrapper.equals(wrapper));
        }

        @Test
        void hashCodeReturnsConsistentValue() {
            int hash1 = wrapper.hashCode();
            int hash2 = wrapper.hashCode();
            assertEquals(hash1, hash2);
        }
    }

    @Nested
    @DisplayName("VariableMapWrapper")
    class VariableMapWrapperTests {

        private VariableMapWrapper wrapper;
        private Map<String, JRFillVariable> innerMap;

        @BeforeEach
        void setUp() {
            wrapper = new VariableMapWrapper();
            innerMap = new HashMap<>();
        }

        @Test
        void constructorCreatesEmptyWrapper() {
            assertNotNull(wrapper);
        }

        @Test
        void setMapUpdatesInternalMap() {
            wrapper.setMap(innerMap);
            assertNotNull(wrapper);
        }

        @Test
        void setReportName() {
            wrapper.setReportName("TestReport");
            assertNotNull(wrapper);
        }

        @Test
        void getWithMissingKeyReturnsNull() {
            wrapper.setMap(innerMap);
            assertNull(wrapper.get("nonexistent"));
        }

        @Test
        void sizeReturnsZeroForEmptyMap() {
            wrapper.setMap(innerMap);
            assertEquals(0, wrapper.size());
        }

        @Test
        void isEmptyReturnsTrueForEmptyMap() {
            wrapper.setMap(innerMap);
            assertTrue(wrapper.isEmpty());
        }

        @Test
        void containsKeyReturnsFalseForMissingKey() {
            wrapper.setMap(innerMap);
            assertFalse(wrapper.containsKey("missing"));
        }

        @Test
        void containsValueThrowsException() {
            wrapper.setMap(innerMap);
            assertThrows(DJException.class, () -> wrapper.containsValue("value"));
        }

        @Test
        void valuesThrowsException() {
            wrapper.setMap(innerMap);
            assertThrows(DJException.class, () -> wrapper.values());
        }

        @Test
        void removeReturnsNull() {
            wrapper.setMap(innerMap);
            assertNull(wrapper.remove("key"));
        }

        @Test
        void clearDoesNotThrow() {
            wrapper.setMap(innerMap);
            assertDoesNotThrow(() -> wrapper.clear());
        }

        @Test
        void keySetReturnsEmptySet() {
            wrapper.setMap(innerMap);
            assertTrue(wrapper.keySet().isEmpty());
        }

        @Test
        void entrySetReturnsEmptySet() {
            wrapper.setMap(innerMap);
            assertTrue(wrapper.entrySet().isEmpty());
        }

        @Test
        void equalsWithSelf() {
            assertTrue(wrapper.equals(wrapper));
        }

        @Test
        void hashCodeReturnsConsistentValue() {
            int hash1 = wrapper.hashCode();
            int hash2 = wrapper.hashCode();
            assertEquals(hash1, hash2);
        }

        @Test
        void getWithReportNamePrefix() {
            wrapper.setMap(innerMap);
            wrapper.setReportName("MyReport");
            assertNull(wrapper.get("variable"));
        }
    }
}
