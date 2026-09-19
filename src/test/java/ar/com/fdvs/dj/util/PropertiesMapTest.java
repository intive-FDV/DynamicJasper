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

package ar.com.fdvs.dj.util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for PropertiesMap.
 */
class PropertiesMapTest {

    private PropertiesMap propertiesMap;

    @BeforeEach
    void setUp() {
        propertiesMap = new PropertiesMap();
    }

    @Nested
    @DisplayName("Basic Operations")
    class BasicOperations {

        @Test
        void putAndGet() {
            propertiesMap.put("key1", "value1");
            assertEquals("value1", propertiesMap.get("key1"));
        }

        @Test
        void putReturnsOldValue() {
            propertiesMap.put("key1", "value1");
            Object oldValue = propertiesMap.put("key1", "value2");
            assertEquals("value1", oldValue);
        }

        @Test
        void getReturnsNullForMissingKey() {
            assertNull(propertiesMap.get("nonexistent"));
        }

        @Test
        void containsKey() {
            propertiesMap.put("key1", "value1");
            assertTrue(propertiesMap.containsKey("key1"));
            assertFalse(propertiesMap.containsKey("key2"));
        }

        @Test
        void containsValue() {
            propertiesMap.put("key1", "value1");
            assertTrue(propertiesMap.containsValue("value1"));
            assertFalse(propertiesMap.containsValue("value2"));
        }

        @Test
        void remove() {
            propertiesMap.put("key1", "value1");
            Object removed = propertiesMap.remove("key1");
            assertEquals("value1", removed);
            assertNull(propertiesMap.get("key1"));
        }

        @Test
        void size() {
            assertEquals(0, propertiesMap.size());
            propertiesMap.put("key1", "value1");
            assertEquals(1, propertiesMap.size());
            propertiesMap.put("key2", "value2");
            assertEquals(2, propertiesMap.size());
        }

        @Test
        void isEmpty() {
            assertTrue(propertiesMap.isEmpty());
            propertiesMap.put("key1", "value1");
            assertFalse(propertiesMap.isEmpty());
        }

        @Test
        void clear() {
            propertiesMap.put("key1", "value1");
            propertiesMap.put("key2", "value2");
            propertiesMap.clear();
            assertTrue(propertiesMap.isEmpty());
        }
    }

    @Nested
    @DisplayName("Collection Views")
    class CollectionViews {

        @Test
        void keySet() {
            propertiesMap.put("key1", "value1");
            propertiesMap.put("key2", "value2");
            Set<Object> keys = propertiesMap.keySet();
            assertEquals(2, keys.size());
            assertTrue(keys.contains("key1"));
            assertTrue(keys.contains("key2"));
        }

        @Test
        void values() {
            propertiesMap.put("key1", "value1");
            propertiesMap.put("key2", "value2");
            var values = propertiesMap.values();
            assertEquals(2, values.size());
            assertTrue(values.contains("value1"));
            assertTrue(values.contains("value2"));
        }

        @Test
        void entrySet() {
            propertiesMap.put("key1", "value1");
            var entries = propertiesMap.entrySet();
            assertEquals(1, entries.size());
        }
    }

    @Nested
    @DisplayName("putAll")
    class PutAll {

        @Test
        void putAllFromMap() {
            Map<Object, Object> source = new HashMap<>();
            source.put("key1", "value1");
            source.put("key2", "value2");

            propertiesMap.putAll(source);

            assertEquals(2, propertiesMap.size());
            assertEquals("value1", propertiesMap.get("key1"));
            assertEquals("value2", propertiesMap.get("key2"));
        }
    }

    @Nested
    @DisplayName("Null Handling")
    class NullHandling {

        @Test
        void putNullValue() {
            propertiesMap.put("key1", null);
            assertNull(propertiesMap.get("key1"));
            assertTrue(propertiesMap.containsKey("key1"));
        }

        @Test
        void putNullKey() {
            propertiesMap.put(null, "value1");
            assertEquals("value1", propertiesMap.get(null));
        }
    }
}
