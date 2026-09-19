/*
 * DynamicJasper: A library for creating reports dynamically by specifying
 * columns, groups, styles, etc. at runtime. It also saves a lot of development
 * time in many cases! (http://sourceforge.net/projects/dynamicjasper)
 *
 * Copyright (C) 2008  FDV Solutions (http://www.fdvsolutions.com)
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 */

package ar.com.fdvs.dj.domain.constants;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for Page constant/factory class.
 */
class PageTest {

    @Nested
    @DisplayName("Constructors")
    class Constructors {

        @Test
        void emptyConstructorDefaults() {
            Page page = new Page();
            assertEquals(0, page.getHeight());
            assertEquals(0, page.getWidth());
            assertTrue(page.isOrientationPortrait());
        }

        @Test
        void heightWidthConstructorIsPortrait() {
            Page page = new Page(800, 600);
            assertEquals(800, page.getHeight());
            assertEquals(600, page.getWidth());
            assertTrue(page.isOrientationPortrait());
        }

        @Test
        void heightWidthOrientationConstructor() {
            Page page = new Page(600, 800, false);
            assertEquals(600, page.getHeight());
            assertEquals(800, page.getWidth());
            assertFalse(page.isOrientationPortrait());
        }
    }

    @Nested
    @DisplayName("Setters")
    class Setters {

        @Test
        void setHeight() {
            Page page = new Page();
            page.setHeight(123);
            assertEquals(123, page.getHeight());
        }

        @Test
        void setWidth() {
            Page page = new Page();
            page.setWidth(456);
            assertEquals(456, page.getWidth());
        }

        @Test
        void setOrientationPortrait() {
            Page page = new Page();
            page.setOrientationPortrait(false);
            assertFalse(page.isOrientationPortrait());
        }
    }

    @Nested
    @DisplayName("Factory Methods")
    class FactoryMethods {

        @Test
        void a4Portrait() {
            Page page = Page.Page_A4_Portrait();
            assertEquals(842, page.getHeight());
            assertEquals(595, page.getWidth());
            assertTrue(page.isOrientationPortrait());
        }

        @Test
        void a4Landscape() {
            Page page = Page.Page_A4_Landscape();
            assertEquals(595, page.getHeight());
            assertEquals(842, page.getWidth());
            assertFalse(page.isOrientationPortrait());
        }

        @Test
        void legalPortrait() {
            Page page = Page.Page_Legal_Portrait();
            assertEquals(1008, page.getHeight());
            assertEquals(612, page.getWidth());
            assertTrue(page.isOrientationPortrait());
        }

        @Test
        void legalLandscape() {
            Page page = Page.Page_Legal_Landscape();
            assertEquals(612, page.getHeight());
            assertEquals(1008, page.getWidth());
            assertFalse(page.isOrientationPortrait());
        }

        @Test
        void letterPortrait() {
            Page page = Page.Page_Letter_Portrait();
            assertEquals(792, page.getHeight());
            assertEquals(612, page.getWidth());
            assertTrue(page.isOrientationPortrait());
        }

        @Test
        void letterLandscape() {
            Page page = Page.Page_Letter_Landscape();
            assertEquals(612, page.getHeight());
            assertEquals(792, page.getWidth());
            assertFalse(page.isOrientationPortrait());
        }
    }
}
