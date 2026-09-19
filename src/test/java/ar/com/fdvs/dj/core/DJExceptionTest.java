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

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for DJException and CoreException.
 */
class DJExceptionTest {

    @Nested
    @DisplayName("DJException")
    class DJExceptionTests {

        @Test
        void defaultConstructor() {
            DJException exception = new DJException();
            assertNotNull(exception);
        }

        @Test
        void constructorWithMessage() {
            DJException exception = new DJException("Test message");
            assertEquals("Test message", exception.getMessage());
        }

        @Test
        void constructorWithCause() {
            Throwable cause = new RuntimeException("Cause");
            DJException exception = new DJException(cause);
            assertSame(cause, exception.getCause());
        }

        @Test
        void constructorWithMessageAndCause() {
            Throwable cause = new RuntimeException("Cause");
            DJException exception = new DJException("Test message", cause);
            assertEquals("Test message", exception.getMessage());
            assertSame(cause, exception.getCause());
        }

        @Test
        void isRuntimeException() {
            DJException exception = new DJException();
            assertTrue(exception instanceof RuntimeException);
        }
    }

    @Nested
    @DisplayName("CoreException")
    class CoreExceptionTests {

        @Test
        void defaultConstructor() {
            CoreException exception = new CoreException();
            assertNotNull(exception);
        }

        @Test
        void constructorWithMessage() {
            CoreException exception = new CoreException("Core error");
            assertEquals("Core error", exception.getMessage());
        }

        @Test
        void constructorWithCause() {
            Throwable cause = new RuntimeException("Root cause");
            CoreException exception = new CoreException(cause);
            assertSame(cause, exception.getCause());
        }

        @Test
        void constructorWithMessageAndCause() {
            Throwable cause = new RuntimeException("Root cause");
            CoreException exception = new CoreException("Core error", cause);
            assertEquals("Core error", exception.getMessage());
            assertSame(cause, exception.getCause());
        }

        @Test
        void extendsRuntimeException() {
            CoreException exception = new CoreException();
            assertTrue(exception instanceof RuntimeException);
        }
    }
}
