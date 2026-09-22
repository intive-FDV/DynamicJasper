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

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for DJGroupLabel.
 */
class DJGroupLabelTest {

    @Nested
    @DisplayName("Constructors")
    class Constructors {

        @Test
        void defaultConstructor() {
            DJGroupLabel label = new DJGroupLabel();
            assertNotNull(label);
        }

        @Test
        void constructorWithTextAndStyle() {
            Style style = new Style();
            DJGroupLabel label = new DJGroupLabel("Total:", style);
            assertEquals("Total:", label.getText());
            assertSame(style, label.getStyle());
        }

        @Test
        void constructorWithTextStyleAndPosition() {
            Style style = new Style();
            DJGroupLabel label = new DJGroupLabel("Total:", style, LabelPosition.LEFT);
            assertEquals("Total:", label.getText());
            assertSame(style, label.getStyle());
            assertEquals(LabelPosition.LEFT, label.getLabelPosition());
        }

        @Test
        void constructorWithExpressionStyleAndPosition() {
            Style style = new Style();
            CustomExpression expression = new CustomExpression() {
                @Override
                public Object evaluate(java.util.Map fields, java.util.Map variables, java.util.Map parameters) {
                    return "Dynamic Label";
                }
                @Override
                public String getClassName() {
                    return String.class.getName();
                }
            };
            DJGroupLabel label = new DJGroupLabel(expression, style, LabelPosition.TOP);
            assertSame(expression, label.getLabelExpression());
            assertSame(style, label.getStyle());
            assertEquals(LabelPosition.TOP, label.getLabelPosition());
        }
    }

    @Nested
    @DisplayName("Properties")
    class Properties {

        private DJGroupLabel label;

        @BeforeEach
        void setUp() {
            label = new DJGroupLabel();
        }

        @Test
        void setAndGetText() {
            label.setText("Subtotal:");
            assertEquals("Subtotal:", label.getText());
        }

        @Test
        void setAndGetStyle() {
            Style style = new Style();
            label.setStyle(style);
            assertSame(style, label.getStyle());
        }

        @Test
        void setAndGetLabelExpression() {
            label.setLabelExpression(null);
            assertNull(label.getLabelExpression());
        }

        @Test
        void setAndGetLabelPosition() {
            label.setLabelPosition(LabelPosition.LEFT);
            assertEquals(LabelPosition.LEFT, label.getLabelPosition());
        }

        @Test
        void defaultLabelPositionIsTop() {
            assertEquals(LabelPosition.TOP, label.getLabelPosition());
        }

        @Test
        void setAndGetHeight() {
            label.setHeight(20);
            assertEquals(20, label.getHeight());
        }

        @Test
        void defaultHeight() {
            assertEquals(15, label.getHeight());
        }
    }
}
