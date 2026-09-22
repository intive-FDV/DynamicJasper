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
 */
package ar.com.fdvs.dj.domain.builders;

import ar.com.fdvs.dj.core.BarcodeTypes;
import ar.com.fdvs.dj.domain.ColumnOperation;
import ar.com.fdvs.dj.domain.ColumnProperty;
import ar.com.fdvs.dj.domain.CustomExpression;
import ar.com.fdvs.dj.domain.Style;
import ar.com.fdvs.dj.domain.constants.ImageScaleMode;
import ar.com.fdvs.dj.domain.entities.columns.AbstractColumn;
import ar.com.fdvs.dj.domain.entities.columns.BarCodeColumn;
import ar.com.fdvs.dj.domain.entities.columns.ExpressionColumn;
import ar.com.fdvs.dj.domain.entities.columns.ImageColumn;
import ar.com.fdvs.dj.domain.entities.columns.OperationColumn;
import ar.com.fdvs.dj.domain.entities.columns.PercentageColumn;
import ar.com.fdvs.dj.domain.entities.columns.PropertyColumn;
import ar.com.fdvs.dj.domain.entities.columns.SimpleColumn;
import ar.com.fdvs.dj.domain.entities.conditionalStyle.ConditionStyleExpression;
import ar.com.fdvs.dj.domain.entities.conditionalStyle.ConditionalStyle;
import org.junit.Test;

import java.text.DecimalFormat;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * Unit tests for ColumnBuilder.
 * Tests column construction for all column types using Strategy pattern.
 */
public class ColumnBuilderTest {

    @Test
    public void testGetNewReturnsNewInstance() {
        ColumnBuilder builder1 = ColumnBuilder.getNew();
        ColumnBuilder builder2 = ColumnBuilder.getNew();

        assertNotSame("Each call should return new instance", builder1, builder2);
    }

    @Test
    public void testGetInstanceReturnsNewInstance() {
        ColumnBuilder builder = ColumnBuilder.getInstance();

        assertNotNull("getInstance should return builder", builder);
    }

    @Test(expected = ColumnBuilderException.class)
    public void testBuildWithoutRequiredFieldsThrows() throws ColumnBuilderException {
        ColumnBuilder.getNew().build();
    }

    @Test
    public void testBuildSimpleColumn() throws ColumnBuilderException {
        AbstractColumn column = ColumnBuilder.getNew()
                .setColumnProperty("name", String.class.getName())
                .setTitle("Name")
                .setWidth(100)
                .build();

        assertNotNull("Column should not be null", column);
        assertTrue("Should be SimpleColumn", column instanceof SimpleColumn);
        assertEquals("Title should match", "Name", column.getTitle());
        assertEquals("Width should match", 100, column.getWidth());
    }

    @Test
    public void testBuildSimpleColumnWithClass() throws ColumnBuilderException {
        AbstractColumn column = ColumnBuilder.getNew()
                .setColumnProperty("amount", Float.class)
                .setTitle("Amount")
                .build();

        assertNotNull("Column should not be null", column);
        assertTrue("Should be SimpleColumn", column instanceof SimpleColumn);
    }

    @Test
    public void testBuildSimpleColumnWithColumnProperty() throws ColumnBuilderException {
        ColumnProperty prop = new ColumnProperty("status", String.class.getName());

        AbstractColumn column = ColumnBuilder.getNew()
                .setColumnProperty(prop)
                .setTitle("Status")
                .build();

        assertNotNull("Column should not be null", column);
        assertTrue("Should be SimpleColumn", column instanceof SimpleColumn);
    }

    @Test
    public void testBuildExpressionColumn() throws ColumnBuilderException {
        CustomExpression expr = new CustomExpression() {
            @Override
            public Object evaluate(Map fields, Map variables, Map parameters) {
                return "calculated";
            }
            @Override
            public String getClassName() {
                return String.class.getName();
            }
        };

        AbstractColumn column = ColumnBuilder.getNew()
                .setCustomExpression(expr)
                .setTitle("Calculated")
                .build();

        assertNotNull("Column should not be null", column);
        assertTrue("Should be ExpressionColumn", column instanceof ExpressionColumn);
    }

    @Test
    public void testBuildImageColumn() throws ColumnBuilderException {
        CustomExpression expr = new CustomExpression() {
            @Override
            public Object evaluate(Map fields, Map variables, Map parameters) {
                return "image.png";
            }
            @Override
            public String getClassName() {
                return String.class.getName();
            }
        };

        AbstractColumn column = ColumnBuilder.getNew()
                .setColumnType(ColumnBuilder.COLUMN_TYPE_IMAGE)
                .setCustomExpression(expr)
                .setTitle("Image")
                .setImageScaleMode(ImageScaleMode.FILL)
                .build();

        assertNotNull("Column should not be null", column);
        assertTrue("Should be ImageColumn", column instanceof ImageColumn);
        assertEquals("Scale mode should match", ImageScaleMode.FILL, ((ImageColumn) column).getScaleMode());
    }

    @Test
    public void testBuildBarcodeColumn() throws ColumnBuilderException {
        AbstractColumn column = ColumnBuilder.getNew()
                .setColumnType(ColumnBuilder.COLUMN_TYPE_BARCODE)
                .setColumnProperty("code", String.class.getName())
                .setBarcodeType(BarcodeTypes.CODE_128)
                .setShowText(true)
                .setCheckSum(true)
                .setTitle("Barcode")
                .build();

        assertNotNull("Column should not be null", column);
        assertTrue("Should be BarCodeColumn", column instanceof BarCodeColumn);
        BarCodeColumn bcColumn = (BarCodeColumn) column;
        assertEquals("Barcode type should match", BarcodeTypes.CODE_128, bcColumn.getBarcodeType());
        assertTrue("Show text should be true", bcColumn.isShowText());
        assertTrue("Checksum should be true", bcColumn.isCheckSum());
    }

    @Test
    public void testBuildOperationColumn() throws ColumnBuilderException {
        SimpleColumn col1 = (SimpleColumn) ColumnBuilder.getNew()
                .setColumnProperty("price", Float.class.getName())
                .setTitle("Price")
                .build();

        SimpleColumn col2 = (SimpleColumn) ColumnBuilder.getNew()
                .setColumnProperty("quantity", Integer.class.getName())
                .setTitle("Quantity")
                .build();

        AbstractColumn column = ColumnBuilder.getNew()
                .addColumnOperation(ColumnOperation.SUM, new SimpleColumn[]{col1, col2})
                .setTitle("Total")
                .build();

        assertNotNull("Column should not be null", column);
        assertTrue("Should be OperationColumn", column instanceof OperationColumn);
    }

    @Test
    public void testBuildPercentageColumn() throws ColumnBuilderException {
        PropertyColumn baseColumn = (PropertyColumn) ColumnBuilder.getNew()
                .setColumnProperty("amount", Float.class.getName())
                .setTitle("Amount")
                .build();

        AbstractColumn column = ColumnBuilder.getNew()
                .setPercentageColumn(baseColumn)
                .setTitle("Percentage")
                .build();

        assertNotNull("Column should not be null", column);
        assertTrue("Should be PercentageColumn", column instanceof PercentageColumn);
        assertEquals("Default pattern should be percentage", "#,##0.00%", column.getPattern());
    }

    @Test
    public void testColumnStylesApplied() throws ColumnBuilderException {
        Style headerStyle = new Style("header");
        Style detailStyle = new Style("detail");

        AbstractColumn column = ColumnBuilder.getNew()
                .setColumnProperty("name", String.class.getName())
                .setTitle("Name")
                .setHeaderStyle(headerStyle)
                .setStyle(detailStyle)
                .build();

        assertSame("Header style should match", headerStyle, column.getHeaderStyle());
        assertSame("Detail style should match", detailStyle, column.getStyle());
    }

    @Test
    public void testColumnPattern() throws ColumnBuilderException {
        AbstractColumn column = ColumnBuilder.getNew()
                .setColumnProperty("amount", Float.class.getName())
                .setTitle("Amount")
                .setPattern("$ #,##0.00")
                .build();

        assertEquals("Pattern should match", "$ #,##0.00", column.getPattern());
    }

    @Test
    public void testFixedWidth() throws ColumnBuilderException {
        AbstractColumn column = ColumnBuilder.getNew()
                .setColumnProperty("name", String.class.getName())
                .setTitle("Name")
                .setWidth(100)
                .setFixedWidth(true)
                .build();

        assertTrue("Fixed width should be true", column.isFixedWidth());
    }

    @Test
    public void testPrintRepeatedValues() throws ColumnBuilderException {
        AbstractColumn column = ColumnBuilder.getNew()
                .setColumnProperty("name", String.class.getName())
                .setTitle("Name")
                .setPrintRepeatedValues(false)
                .build();

        assertFalse("Print repeated values should be false", column.isPrintRepeatedValues());
    }

    @Test
    public void testTruncateSuffix() throws ColumnBuilderException {
        AbstractColumn column = ColumnBuilder.getNew()
                .setColumnProperty("name", String.class.getName())
                .setTitle("Name")
                .setTruncateSuffix("...")
                .build();

        assertEquals("Truncate suffix should match", "...", column.getTruncateSuffix());
    }

    @Test
    public void testTextFormatter() throws ColumnBuilderException {
        DecimalFormat formatter = new DecimalFormat("#,##0.00");

        AbstractColumn column = ColumnBuilder.getNew()
                .setColumnProperty("amount", Float.class.getName())
                .setTitle("Amount")
                .setTextFormatter(formatter)
                .build();

        assertSame("Text formatter should match", formatter, column.getTextFormatter());
    }

    @Test
    public void testMarkup() throws ColumnBuilderException {
        AbstractColumn column = ColumnBuilder.getNew()
                .setColumnProperty("description", String.class.getName())
                .setTitle("Description")
                .setMarkup("html")
                .setHeaderMarkup("styled")
                .build();

        assertEquals("Markup should match", "html", column.getMarkup());
        assertEquals("Header markup should match", "styled", column.getHeaderMarkup());
    }

    @Test
    public void testConditionalStyles() throws ColumnBuilderException {
        ConditionStyleExpression condition = new ConditionStyleExpression() {
            @Override
            public Object evaluate(Map fields, Map variables, Map parameters) {
                return true;
            }
            @Override
            public String getClassName() {
                return Boolean.class.getName();
            }
        };
        ConditionalStyle cs = new ConditionalStyle(condition, new Style());

        AbstractColumn column = ColumnBuilder.getNew()
                .setColumnProperty("amount", Float.class.getName())
                .setTitle("Amount")
                .addConditionalStyle(cs)
                .build();

        assertEquals("Should have one conditional style", 1, column.getConditionalStyles().size());
    }

    @Test
    public void testFieldProperty() throws ColumnBuilderException {
        AbstractColumn column = ColumnBuilder.getNew()
                .setColumnProperty("data", String.class.getName())
                .setTitle("Data")
                .addFieldProperty("key", "value")
                .build();

        assertNotNull("Column should not be null", column);
    }

    @Test
    public void testFieldDescription() throws ColumnBuilderException {
        AbstractColumn column = ColumnBuilder.getNew()
                .setColumnProperty("name", String.class.getName(), "Field description")
                .setTitle("Name")
                .build();

        assertTrue("Should be SimpleColumn", column instanceof SimpleColumn);
        assertEquals("Field description should match", "Field description",
                ((SimpleColumn) column).getFieldDescription());
    }

    @Test
    public void testSetCommonProperties() throws ColumnBuilderException {
        AbstractColumn column = ColumnBuilder.getNew()
                .setCommonProperties("Title", "property", String.class.getName(), 150, true)
                .build();

        assertEquals("Title should match", "Title", column.getTitle());
        assertEquals("Width should match", 150, column.getWidth());
        assertTrue("Fixed width should be true", column.isFixedWidth());
    }

    @Test
    public void testSetCommonPropertiesWithClass() throws ColumnBuilderException {
        AbstractColumn column = ColumnBuilder.getNew()
                .setCommonProperties("Title", "property", String.class, 150, false)
                .build();

        assertEquals("Title should match", "Title", column.getTitle());
        assertFalse("Fixed width should be false", column.isFixedWidth());
    }

    @Test
    public void testBarcodeApplicationIdentifier() throws ColumnBuilderException {
        AbstractColumn column = ColumnBuilder.getNew()
                .setColumnType(ColumnBuilder.COLUMN_TYPE_BARCODE)
                .setColumnProperty("code", String.class.getName())
                .setBarcodeType(BarcodeTypes.UCCEAN128)
                .setApplicationIdentifier("01")
                .setTitle("UCC")
                .build();

        assertTrue("Should be BarCodeColumn", column instanceof BarCodeColumn);
        assertEquals("Application identifier should match", "01",
                ((BarCodeColumn) column).getApplicationIdentifier());
    }

    @Test
    public void testDefaultWidth() throws ColumnBuilderException {
        AbstractColumn column = ColumnBuilder.getNew()
                .setColumnProperty("name", String.class.getName())
                .setTitle("Name")
                .build();

        assertEquals("Default width should be 50", 50, column.getWidth());
    }

    @Test
    public void testColumnTypeConstants() {
        assertEquals("Default type should be 0", 0, ColumnBuilder.COLUMN_TYPE_DEFAULT);
        assertEquals("Image type should be 1", 1, ColumnBuilder.COLUMN_TYPE_IMAGE);
        assertEquals("Barcode type should be 2", 2, ColumnBuilder.COLUMN_TYPE_BARCODE);
    }
}
