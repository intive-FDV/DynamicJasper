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

import ar.com.fdvs.dj.core.layout.HorizontalBandAlignment;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for AutoText.
 */
class AutoTextTest {

    @Nested
    @DisplayName("Constructors")
    class Constructors {

        @Test
        void basicConstructor() {
            AutoText autoText = new AutoText(AutoText.AUTOTEXT_PAGE_X, AutoText.POSITION_HEADER, HorizontalBandAlignment.LEFT);
            assertEquals(AutoText.AUTOTEXT_PAGE_X, autoText.getType());
            assertEquals(AutoText.POSITION_HEADER, autoText.getPosition());
            assertEquals(HorizontalBandAlignment.LEFT, autoText.getAlignment());
        }

        @Test
        void constructorWithPattern() {
            AutoText autoText = new AutoText(AutoText.AUTOTEXT_CREATED_ON, AutoText.POSITION_FOOTER,
                    HorizontalBandAlignment.CENTER, AutoText.PATTERN_DATE_DATE_TIME);
            assertEquals(AutoText.AUTOTEXT_CREATED_ON, autoText.getType());
            assertEquals(AutoText.POSITION_FOOTER, autoText.getPosition());
            assertEquals(AutoText.PATTERN_DATE_DATE_TIME, autoText.getPattern());
        }

        @Test
        void constructorWithWidth() {
            AutoText autoText = new AutoText(AutoText.AUTOTEXT_PAGE_X_OF_Y, AutoText.POSITION_FOOTER,
                    HorizontalBandAlignment.RIGHT, AutoText.PATTERN_DATE_DATE_ONLY, 100);
            assertEquals(100, autoText.getWidth());
        }

        @Test
        void constructorWithWidthAndWidth2() {
            AutoText autoText = new AutoText(AutoText.AUTOTEXT_PAGE_X_OF_Y, AutoText.POSITION_FOOTER,
                    HorizontalBandAlignment.RIGHT, AutoText.PATTERN_DATE_DATE_ONLY, 100, 30);
            assertEquals(100, autoText.getWidth());
            assertEquals(30, autoText.getWidth2());
        }

        @Test
        void customMessageConstructor() {
            AutoText autoText = new AutoText("Custom Message", AutoText.POSITION_HEADER, HorizontalBandAlignment.LEFT);
            assertEquals(AutoText.AUTOTEXT_CUSTOM_MESSAGE, autoText.getType());
            assertEquals("Custom Message", autoText.getMessageKey());
        }

        @Test
        void customMessageConstructorWithWidth() {
            AutoText autoText = new AutoText("Custom Message", AutoText.POSITION_HEADER, HorizontalBandAlignment.LEFT, 150);
            assertEquals(150, autoText.getWidth());
        }
    }

    @Nested
    @DisplayName("Properties")
    class Properties {

        @Test
        void setAndGetType() {
            AutoText autoText = new AutoText(AutoText.AUTOTEXT_PAGE_X, AutoText.POSITION_HEADER, HorizontalBandAlignment.LEFT);
            autoText.setType(AutoText.AUTOTEXT_CREATED_ON);
            assertEquals(AutoText.AUTOTEXT_CREATED_ON, autoText.getType());
        }

        @Test
        void setAndGetPosition() {
            AutoText autoText = new AutoText(AutoText.AUTOTEXT_PAGE_X, AutoText.POSITION_HEADER, HorizontalBandAlignment.LEFT);
            autoText.setPosition(AutoText.POSITION_FOOTER);
            assertEquals(AutoText.POSITION_FOOTER, autoText.getPosition());
        }

        @Test
        void setAndGetAlignment() {
            AutoText autoText = new AutoText(AutoText.AUTOTEXT_PAGE_X, AutoText.POSITION_HEADER, HorizontalBandAlignment.LEFT);
            autoText.setAlignment(HorizontalBandAlignment.RIGHT);
            assertEquals(HorizontalBandAlignment.RIGHT, autoText.getAlignment());
        }

        @Test
        void setAndGetWidth() {
            AutoText autoText = new AutoText(AutoText.AUTOTEXT_PAGE_X, AutoText.POSITION_HEADER, HorizontalBandAlignment.LEFT);
            autoText.setWidth(150);
            assertEquals(150, autoText.getWidth());
        }

        @Test
        void setAndGetWidth2() {
            AutoText autoText = new AutoText(AutoText.AUTOTEXT_PAGE_X, AutoText.POSITION_HEADER, HorizontalBandAlignment.LEFT);
            autoText.setWidth2(50);
            assertEquals(50, autoText.getWidth2());
        }

        @Test
        void setAndGetHeight() {
            AutoText autoText = new AutoText(AutoText.AUTOTEXT_PAGE_X, AutoText.POSITION_HEADER, HorizontalBandAlignment.LEFT);
            autoText.setHeight(25);
            assertEquals(25, autoText.getHeight());
        }

        @Test
        void defaultHeight() {
            AutoText autoText = new AutoText(AutoText.AUTOTEXT_PAGE_X, AutoText.POSITION_HEADER, HorizontalBandAlignment.LEFT);
            assertEquals(15, autoText.getHeight());
        }

        @Test
        void setAndGetMessageKey() {
            AutoText autoText = new AutoText("Test", AutoText.POSITION_HEADER, HorizontalBandAlignment.LEFT);
            autoText.setMessageKey("Another Message");
            assertEquals("Another Message", autoText.getMessageKey());
        }

        @Test
        void setAndGetStyle() {
            AutoText autoText = new AutoText(AutoText.AUTOTEXT_PAGE_X, AutoText.POSITION_HEADER, HorizontalBandAlignment.LEFT);
            Style style = new Style();
            autoText.setStyle(style);
            assertSame(style, autoText.getStyle());
        }

        @Test
        void setAndGetPageOffset() {
            AutoText autoText = new AutoText(AutoText.AUTOTEXT_PAGE_X, AutoText.POSITION_HEADER, HorizontalBandAlignment.LEFT);
            autoText.setPageOffset(5);
            assertEquals(5, autoText.getPageOffset());
        }

        @Test
        void setAndGetUseI18n() {
            AutoText autoText = new AutoText(AutoText.AUTOTEXT_PAGE_X, AutoText.POSITION_HEADER, HorizontalBandAlignment.LEFT);
            autoText.setUseI18n(true);
            assertTrue(autoText.isUseI18n());
        }

        @Test
        void getPattern() {
            AutoText autoText = new AutoText(AutoText.AUTOTEXT_CREATED_ON, AutoText.POSITION_FOOTER,
                    HorizontalBandAlignment.CENTER, AutoText.PATTERN_DATE_TIME_ONLY);
            assertEquals(AutoText.PATTERN_DATE_TIME_ONLY, autoText.getPattern());
        }

        @Test
        void setAndGetFixedWith() {
            AutoText autoText = new AutoText(AutoText.AUTOTEXT_PAGE_X, AutoText.POSITION_HEADER, HorizontalBandAlignment.LEFT);
            autoText.setFixedWith(false);
            assertFalse(autoText.isFixedWith());
        }
    }

    @Nested
    @DisplayName("Constants")
    class Constants {

        @Test
        void positionConstants() {
            assertEquals((byte) 0, AutoText.POSITION_FOOTER);
            assertEquals((byte) 1, AutoText.POSITION_HEADER);
        }

        @Test
        void alignmentConstants() {
            assertEquals((byte) 1, AutoText.ALIGMENT_LEFT);
            assertEquals((byte) 2, AutoText.ALIGMENT_CENTER);
            assertEquals((byte) 3, AutoText.ALIGMENT_RIGHT);
            assertEquals((byte) 1, AutoText.ALIGNMENT_LEFT);
            assertEquals((byte) 2, AutoText.ALIGNMENT_CENTER);
            assertEquals((byte) 3, AutoText.ALIGNMENT_RIGHT);
        }

        @Test
        void typeConstants() {
            assertEquals((byte) 0, AutoText.AUTOTEXT_PAGE_X_OF_Y);
            assertEquals((byte) 1, AutoText.AUTOTEXT_PAGE_X_SLASH_Y);
            assertEquals((byte) 2, AutoText.AUTOTEXT_PAGE_X);
            assertEquals((byte) 3, AutoText.AUTOTEXT_CREATED_ON);
            assertEquals((byte) 4, AutoText.AUTOTEXT_CUSTOM_MESSAGE);
            assertEquals((byte) 5, AutoText.AUTOTEXT_JREXPRESSION);
        }

        @Test
        void patternConstants() {
            assertEquals((byte) 1, AutoText.PATTERN_DATE_DATE_ONLY);
            assertEquals((byte) 2, AutoText.PATTERN_DATE_TIME_ONLY);
            assertEquals((byte) 3, AutoText.PATTERN_DATE_DATE_TIME);
        }

        @Test
        void widthConstants() {
            assertEquals(Integer.MIN_VALUE, AutoText.WIDTH_NOT_SET);
            assertEquals(80, AutoText.DEFAULT_WIDTH);
            assertEquals(30, AutoText.DEFAULT_WIDTH2);
        }
    }

    @Nested
    @DisplayName("PrintWhenExpression")
    class PrintWhenExpressionTests {

        @Test
        void setAndGetPrintWhenExpression() {
            AutoText autoText = new AutoText(AutoText.AUTOTEXT_PAGE_X, AutoText.POSITION_HEADER, HorizontalBandAlignment.LEFT);
            BooleanExpression expr = ExpressionHelper.printNotInFirstPage();
            autoText.setPrintWhenExpression(expr);
            assertSame(expr, autoText.getPrintWhenExpression());
        }

        @Test
        void printWhenExpressionNullByDefault() {
            AutoText autoText = new AutoText(AutoText.AUTOTEXT_PAGE_X, AutoText.POSITION_HEADER, HorizontalBandAlignment.LEFT);
            assertNull(autoText.getPrintWhenExpression());
        }
    }
}
