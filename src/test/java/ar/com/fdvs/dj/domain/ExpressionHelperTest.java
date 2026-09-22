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

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for ExpressionHelper.
 */
class ExpressionHelperTest {

    @Test
    @DisplayName("printInFirstPage returns BooleanExpression")
    void printInFirstPageReturnsBooleanExpression() {
        BooleanExpression expr = ExpressionHelper.printInFirstPage();
        assertNotNull(expr);
    }

    @Test
    @DisplayName("printInFirstPage evaluates correctly")
    void printInFirstPageEvaluatesCorrectly() {
        BooleanExpression expr = ExpressionHelper.printInFirstPage();
        Map<String, Object> vars = new HashMap<>();
        vars.put("PAGE_NUMBER", 1);
        Object result = expr.evaluate(null, vars, null);
        assertTrue((Boolean) result);
    }

    @Test
    @DisplayName("printInFirstPage returns false for page > 1")
    void printInFirstPageReturnsFalseForPageGreaterThanOne() {
        BooleanExpression expr = ExpressionHelper.printInFirstPage();
        Map<String, Object> vars = new HashMap<>();
        vars.put("PAGE_NUMBER", 2);
        Object result = expr.evaluate(null, vars, null);
        assertFalse((Boolean) result);
    }

    @Test
    @DisplayName("printNotInFirstPage returns BooleanExpression")
    void printNotInFirstPageReturnsBooleanExpression() {
        BooleanExpression expr = ExpressionHelper.printNotInFirstPage();
        assertNotNull(expr);
    }

    @Test
    @DisplayName("printNotInFirstPage evaluates correctly")
    void printNotInFirstPageEvaluatesCorrectly() {
        BooleanExpression expr = ExpressionHelper.printNotInFirstPage();
        Map<String, Object> vars = new HashMap<>();
        vars.put("PAGE_NUMBER", 2);
        Object result = expr.evaluate(null, vars, null);
        assertTrue((Boolean) result);
    }

    @Test
    @DisplayName("printNotInFirstPage returns false for page 1")
    void printNotInFirstPageReturnsFalseForPageOne() {
        BooleanExpression expr = ExpressionHelper.printNotInFirstPage();
        Map<String, Object> vars = new HashMap<>();
        vars.put("PAGE_NUMBER", 1);
        Object result = expr.evaluate(null, vars, null);
        assertFalse((Boolean) result);
    }

    @Test
    @DisplayName("printWhenGroupHasMoreThanOneRecord returns BooleanExpression")
    void printWhenGroupHasMoreThanOneRecordReturnsBooleanExpression() {
        BooleanExpression expr = ExpressionHelper.printWhenGroupHasMoreThanOneRecord("testGroup");
        assertNotNull(expr);
    }

    @Test
    @DisplayName("printWhenGroupHasMoreThanOneRecord evaluates correctly")
    void printWhenGroupHasMoreThanOneRecordEvaluatesCorrectly() {
        BooleanExpression expr = ExpressionHelper.printWhenGroupHasMoreThanOneRecord("myGroup");
        Map<String, Object> vars = new HashMap<>();
        vars.put("myGroup_COUNT", 5);
        Object result = expr.evaluate(null, vars, null);
        assertTrue((Boolean) result);
    }

    @Test
    @DisplayName("printWhenGroupHasMoreThanOneRecord returns false for count 1")
    void printWhenGroupHasMoreThanOneRecordReturnsFalseForCountOne() {
        BooleanExpression expr = ExpressionHelper.printWhenGroupHasMoreThanOneRecord("myGroup");
        Map<String, Object> vars = new HashMap<>();
        vars.put("myGroup_COUNT", 1);
        Object result = expr.evaluate(null, vars, null);
        assertFalse((Boolean) result);
    }

    @Test
    @DisplayName("BooleanExpression getClassName returns Boolean")
    void booleanExpressionGetClassName() {
        BooleanExpression expr = ExpressionHelper.printInFirstPage();
        assertEquals(Boolean.class.getName(), expr.getClassName());
    }
}
