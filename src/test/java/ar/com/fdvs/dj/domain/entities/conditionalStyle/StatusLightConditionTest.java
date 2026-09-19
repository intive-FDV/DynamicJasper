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

package ar.com.fdvs.dj.domain.entities.conditionalStyle;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for StatusLightCondition.
 * Tests the three modes: min-only, min-and-max, max-only.
 */
class StatusLightConditionTest {

    @Nested
    @DisplayName("Constructor Modes")
    class ConstructorModes {

        @Test
        void minOnlyMode() {
            // Mode 0: min != null, max == null
            StatusLightCondition condition = new StatusLightCondition(10.0, null);
            assertNotNull(condition);
        }

        @Test
        void minAndMaxMode() {
            // Mode 1: min != null, max != null
            StatusLightCondition condition = new StatusLightCondition(10.0, 100.0);
            assertNotNull(condition);
        }

        @Test
        void maxOnlyMode() {
            // Mode 2: min == null, max != null
            StatusLightCondition condition = new StatusLightCondition(null, 100.0);
            assertNotNull(condition);
        }

        @Test
        void bothNull() {
            // Edge case: both null
            StatusLightCondition condition = new StatusLightCondition(null, null);
            assertNotNull(condition);
        }
    }

    @Nested
    @DisplayName("Evaluate - Min Only Mode")
    class EvaluateMinOnlyMode {

        @Test
        void valueBelowMin() {
            StatusLightCondition condition = new StatusLightCondition(50.0, null);

            Map<String, Object> fields = new HashMap<>();
            Map<String, Object> variables = new HashMap<>();
            Map<String, Object> params = new HashMap<>();

            // Value 30 < min 50, should return TRUE (below threshold)
            Object result = condition.evaluate(fields, variables, params, 30.0);
            assertEquals(Boolean.TRUE, result);
        }

        @Test
        void valueAboveMin() {
            StatusLightCondition condition = new StatusLightCondition(50.0, null);

            Map<String, Object> fields = new HashMap<>();
            Map<String, Object> variables = new HashMap<>();
            Map<String, Object> params = new HashMap<>();

            // Value 70 > min 50, should return FALSE
            Object result = condition.evaluate(fields, variables, params, 70.0);
            assertEquals(Boolean.FALSE, result);
        }

        @Test
        void valueEqualsMin() {
            StatusLightCondition condition = new StatusLightCondition(50.0, null);

            Map<String, Object> fields = new HashMap<>();
            Map<String, Object> variables = new HashMap<>();
            Map<String, Object> params = new HashMap<>();

            // Value 50 == min 50, should return FALSE (not below)
            Object result = condition.evaluate(fields, variables, params, 50.0);
            assertEquals(Boolean.FALSE, result);
        }
    }

    @Nested
    @DisplayName("Evaluate - Min and Max Mode")
    class EvaluateMinAndMaxMode {

        @Test
        void valueBetweenMinAndMax() {
            StatusLightCondition condition = new StatusLightCondition(20.0, 80.0);

            Map<String, Object> fields = new HashMap<>();
            Map<String, Object> variables = new HashMap<>();
            Map<String, Object> params = new HashMap<>();

            // Value 50 is between 20 and 80
            Object result = condition.evaluate(fields, variables, params, 50.0);
            assertEquals(Boolean.TRUE, result);
        }

        @Test
        void valueBelowMin() {
            StatusLightCondition condition = new StatusLightCondition(20.0, 80.0);

            Map<String, Object> fields = new HashMap<>();
            Map<String, Object> variables = new HashMap<>();
            Map<String, Object> params = new HashMap<>();

            // Value 10 < min 20
            Object result = condition.evaluate(fields, variables, params, 10.0);
            assertEquals(Boolean.FALSE, result);
        }

        @Test
        void valueAboveMax() {
            StatusLightCondition condition = new StatusLightCondition(20.0, 80.0);

            Map<String, Object> fields = new HashMap<>();
            Map<String, Object> variables = new HashMap<>();
            Map<String, Object> params = new HashMap<>();

            // Value 90 > max 80
            Object result = condition.evaluate(fields, variables, params, 90.0);
            assertEquals(Boolean.FALSE, result);
        }

        @Test
        void valueEqualsMin() {
            StatusLightCondition condition = new StatusLightCondition(20.0, 80.0);

            Map<String, Object> fields = new HashMap<>();
            Map<String, Object> variables = new HashMap<>();
            Map<String, Object> params = new HashMap<>();

            // Value 20 == min 20
            Object result = condition.evaluate(fields, variables, params, 20.0);
            assertEquals(Boolean.TRUE, result);
        }

        @Test
        void valueEqualsMax() {
            StatusLightCondition condition = new StatusLightCondition(20.0, 80.0);

            Map<String, Object> fields = new HashMap<>();
            Map<String, Object> variables = new HashMap<>();
            Map<String, Object> params = new HashMap<>();

            // Value 80 == max 80, but < max is false
            Object result = condition.evaluate(fields, variables, params, 80.0);
            assertEquals(Boolean.FALSE, result);
        }
    }

    @Nested
    @DisplayName("Evaluate - Max Only Mode")
    class EvaluateMaxOnlyMode {

        @Test
        void valueAboveMax() {
            StatusLightCondition condition = new StatusLightCondition(null, 100.0);

            Map<String, Object> fields = new HashMap<>();
            Map<String, Object> variables = new HashMap<>();
            Map<String, Object> params = new HashMap<>();

            // Value 150 > max 100, should return TRUE
            Object result = condition.evaluate(fields, variables, params, 150.0);
            assertEquals(Boolean.TRUE, result);
        }

        @Test
        void valueBelowMax() {
            StatusLightCondition condition = new StatusLightCondition(null, 100.0);

            Map<String, Object> fields = new HashMap<>();
            Map<String, Object> variables = new HashMap<>();
            Map<String, Object> params = new HashMap<>();

            // Value 50 < max 100, should return FALSE
            Object result = condition.evaluate(fields, variables, params, 50.0);
            assertEquals(Boolean.FALSE, result);
        }

        @Test
        void valueEqualsMax() {
            StatusLightCondition condition = new StatusLightCondition(null, 100.0);

            Map<String, Object> fields = new HashMap<>();
            Map<String, Object> variables = new HashMap<>();
            Map<String, Object> params = new HashMap<>();

            // Value 100 == max 100, returns TRUE (max <= value)
            Object result = condition.evaluate(fields, variables, params, 100.0);
            assertEquals(Boolean.TRUE, result);
        }
    }

    @Nested
    @DisplayName("Special Cases")
    class SpecialCases {

        @Test
        void nullValue() {
            StatusLightCondition condition = new StatusLightCondition(50.0, 100.0);

            Map<String, Object> fields = new HashMap<>();
            Map<String, Object> variables = new HashMap<>();
            Map<String, Object> params = new HashMap<>();

            // Null value should not throw exception
            assertDoesNotThrow(() -> condition.evaluate(fields, variables, params, null));
        }

        @Test
        void integerValue() {
            StatusLightCondition condition = new StatusLightCondition(50.0, null);

            Map<String, Object> fields = new HashMap<>();
            Map<String, Object> variables = new HashMap<>();
            Map<String, Object> params = new HashMap<>();

            // Integer value should work (will be cast)
            Object result = condition.evaluate(fields, variables, params, 30);
            assertEquals(Boolean.TRUE, result);
        }

        @Test
        void longValue() {
            StatusLightCondition condition = new StatusLightCondition(50.0, null);

            Map<String, Object> fields = new HashMap<>();
            Map<String, Object> variables = new HashMap<>();
            Map<String, Object> params = new HashMap<>();

            Object result = condition.evaluate(fields, variables, params, 30L);
            assertEquals(Boolean.TRUE, result);
        }

        @Test
        void floatValue() {
            StatusLightCondition condition = new StatusLightCondition(50.0, null);

            Map<String, Object> fields = new HashMap<>();
            Map<String, Object> variables = new HashMap<>();
            Map<String, Object> params = new HashMap<>();

            Object result = condition.evaluate(fields, variables, params, 30.5f);
            assertEquals(Boolean.TRUE, result);
        }
    }

    @Nested
    @DisplayName("getClassName")
    class GetClassName {

        @Test
        void returnsBoolean() {
            StatusLightCondition condition = new StatusLightCondition(50.0, 100.0);
            assertEquals(Boolean.class.getName(), condition.getClassName());
        }
    }
}
