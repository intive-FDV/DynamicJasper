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

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for DJHyperLink.
 */
class DJHyperLinkTest {

    private DJHyperLink hyperLink;

    @BeforeEach
    void setUp() {
        hyperLink = new DJHyperLink();
    }

    @Test
    @DisplayName("Default constructor creates instance")
    void defaultConstructor() {
        assertNotNull(hyperLink);
    }

    @Test
    @DisplayName("Set and get expression")
    void setAndGetExpression() {
        StringExpression expr = new StringExpression() {
            @Override
            public Object evaluate(Map fields, Map variables, Map parameters) {
                return "http://example.com";
            }
            @Override
            public String getClassName() {
                return String.class.getName();
            }
        };
        hyperLink.setExpression(expr);
        assertSame(expr, hyperLink.getExpression());
    }

    @Test
    @DisplayName("Set and get tooltip")
    void setAndGetTooltip() {
        StringExpression tooltip = new StringExpression() {
            @Override
            public Object evaluate(Map fields, Map variables, Map parameters) {
                return "Click here";
            }
            @Override
            public String getClassName() {
                return String.class.getName();
            }
        };
        hyperLink.setTooltip(tooltip);
        assertSame(tooltip, hyperLink.getTooltip());
    }

    @Test
    @DisplayName("Expression null by default")
    void expressionNullByDefault() {
        assertNull(hyperLink.getExpression());
    }

    @Test
    @DisplayName("Tooltip null by default")
    void tooltipNullByDefault() {
        assertNull(hyperLink.getTooltip());
    }
}
