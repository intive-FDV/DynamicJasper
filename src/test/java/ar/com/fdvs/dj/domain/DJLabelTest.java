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

package ar.com.fdvs.dj.domain;

import ar.com.fdvs.dj.domain.constants.LabelPosition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for DJLabel.
 */
class DJLabelTest {

    @Nested
    @DisplayName("Constructors")
    class Constructors {

        @Test
        void defaultConstructor() {
            DJLabel label = new DJLabel();
            assertNotNull(label);
        }

        @Test
        void constructorWithTextAndStyle() {
            Style style = new Style();
            DJLabel label = new DJLabel("Test Label", style);
            assertEquals("Test Label", label.getText());
            assertSame(style, label.getStyle());
        }

        @Test
        void constructorWithTextStyleAndJasperExpression() {
            Style style = new Style();
            DJLabel label = new DJLabel("$V{REPORT_COUNT}", style, true);
            assertEquals("$V{REPORT_COUNT}", label.getText());
            assertSame(style, label.getStyle());
            assertTrue(label.isJasperExpression());
        }

        @Test
        void constructorWithTextStyleAndPosition() {
            Style style = new Style();
            DJLabel label = new DJLabel("Test", style, LabelPosition.LEFT);
            assertEquals("Test", label.getText());
            assertSame(style, label.getStyle());
        }

        @Test
        void constructorWithExpressionAndStyle() {
            Style style = new Style();
            CustomExpression expr = new CustomExpression() {
                @Override
                public Object evaluate(Map fields, Map variables, Map parameters) {
                    return "Dynamic";
                }
                @Override
                public String getClassName() {
                    return String.class.getName();
                }
            };
            DJLabel label = new DJLabel(expr, style);
            assertSame(expr, label.getLabelExpression());
            assertSame(style, label.getStyle());
        }
    }

    @Nested
    @DisplayName("Properties")
    class Properties {

        private DJLabel label;

        @BeforeEach
        void setUp() {
            label = new DJLabel();
        }

        @Test
        void setAndGetText() {
            label.setText("New Text");
            assertEquals("New Text", label.getText());
        }

        @Test
        void setAndGetStyle() {
            Style style = new Style();
            label.setStyle(style);
            assertSame(style, label.getStyle());
        }

        @Test
        void setAndGetLabelExpression() {
            CustomExpression expr = new CustomExpression() {
                @Override
                public Object evaluate(Map fields, Map variables, Map parameters) {
                    return "test";
                }
                @Override
                public String getClassName() {
                    return String.class.getName();
                }
            };
            label.setLabelExpression(expr);
            assertSame(expr, label.getLabelExpression());
        }

        @Test
        void setAndGetHeight() {
            label.setHeight(25);
            assertEquals(25, label.getHeight());
        }

        @Test
        void defaultHeight() {
            assertEquals(15, label.getHeight());
        }

        @Test
        void setAndGetJasperExpression() {
            label.setJasperExpression(true);
            assertTrue(label.isJasperExpression());
            label.setJasperExpression(false);
            assertFalse(label.isJasperExpression());
        }

        @Test
        void defaultIsJasperExpression() {
            assertFalse(label.isJasperExpression());
        }
    }
}
