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

package ar.com.fdvs.dj.domain.builders;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for builder exception classes.
 */
class BuilderExceptionsTest {

    @Nested
    @DisplayName("BuilderException")
    class BuilderExceptionTests {

        @Test
        void defaultConstructor() {
            BuilderException ex = new BuilderException();
            assertNotNull(ex);
        }

        @Test
        void constructorWithMessage() {
            BuilderException ex = new BuilderException("Test error");
            assertEquals("Test error", ex.getMessage());
        }

        @Test
        void constructorWithMessageAndCause() {
            Throwable cause = new RuntimeException("Root cause");
            BuilderException ex = new BuilderException("Test error", cause);
            assertEquals("Test error", ex.getMessage());
            assertSame(cause, ex.getCause());
        }

        @Test
        void constructorWithCause() {
            Throwable cause = new RuntimeException("Root cause");
            BuilderException ex = new BuilderException(cause);
            assertSame(cause, ex.getCause());
        }
    }

    @Nested
    @DisplayName("ColumnBuilderException")
    class ColumnBuilderExceptionTests {

        @Test
        void defaultConstructor() {
            ColumnBuilderException ex = new ColumnBuilderException();
            assertNotNull(ex);
        }

        @Test
        void constructorWithMessage() {
            ColumnBuilderException ex = new ColumnBuilderException("Column error");
            assertEquals("Column error", ex.getMessage());
        }

        @Test
        void constructorWithMessageAndCause() {
            Throwable cause = new RuntimeException("Root cause");
            ColumnBuilderException ex = new ColumnBuilderException("Column error", cause);
            assertEquals("Column error", ex.getMessage());
            assertSame(cause, ex.getCause());
        }

        @Test
        void constructorWithCause() {
            Throwable cause = new RuntimeException("Root cause");
            ColumnBuilderException ex = new ColumnBuilderException(cause);
            assertSame(cause, ex.getCause());
        }

        @Test
        void extendsBuilderException() {
            ColumnBuilderException ex = new ColumnBuilderException();
            assertTrue(ex instanceof BuilderException);
        }
    }

    @Nested
    @DisplayName("DJBuilderException")
    class DJBuilderExceptionTests {

        @Test
        void defaultConstructor() {
            DJBuilderException ex = new DJBuilderException();
            assertNotNull(ex);
        }

        @Test
        void constructorWithMessage() {
            DJBuilderException ex = new DJBuilderException("DJ Builder error");
            assertEquals("DJ Builder error", ex.getMessage());
        }

        @Test
        void constructorWithMessageAndCause() {
            Throwable cause = new RuntimeException("Root cause");
            DJBuilderException ex = new DJBuilderException("DJ Builder error", cause);
            assertEquals("DJ Builder error", ex.getMessage());
            assertSame(cause, ex.getCause());
        }

        @Test
        void constructorWithCause() {
            Throwable cause = new RuntimeException("Root cause");
            DJBuilderException ex = new DJBuilderException(cause);
            assertSame(cause, ex.getCause());
        }
    }

    @Nested
    @DisplayName("ChartBuilderException")
    class ChartBuilderExceptionTests {

        @Test
        void constructorWithMessage() {
            ChartBuilderException ex = new ChartBuilderException("Chart error");
            assertEquals("Chart error", ex.getMessage());
        }

        @Test
        void extendsRuntimeException() {
            ChartBuilderException ex = new ChartBuilderException("error");
            assertTrue(ex instanceof RuntimeException);
        }
    }

    @Nested
    @DisplayName("ColumnsGroupBuilderException")
    class ColumnsGroupBuilderExceptionTests {

        @Test
        void defaultConstructor() {
            ColumnsGroupBuilderException ex = new ColumnsGroupBuilderException();
            assertNotNull(ex);
        }

        @Test
        void extendsBuilderException() {
            ColumnsGroupBuilderException ex = new ColumnsGroupBuilderException();
            assertTrue(ex instanceof BuilderException);
        }
    }
}
