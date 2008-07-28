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

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

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
import ar.com.fdvs.dj.domain.entities.columns.SimpleColumn;
import ar.com.fdvs.dj.domain.entities.conditionalStyle.ConditionalStyle;
import ar.com.fdvs.dj.util.PropertiesMap;

/**
 * Builder created to give users a friendly way of adding columns to a report.</br>
 * </br>
 * Usage example: </br>
 * AbstractColumn columnState = ColumnBuilder.getInstance() </br>
 * .addColumnProperty("state", String.class.getName()) </br>
 * .addTitle("State").addWidth(new Integer(85)) </br>
 * .addStyle(detailStyle).addHeaderStyle(headerStyle).build(); </br>
 * </br>
 * Like with all DJ's builders, it's usage must end with a call to build() mehtod.
 * </br>
 */
public class ColumnBuilder {

	public static final int COLUMN_TYPE_DEFAULT = 0;
	public static final int COLUMN_TYPE_IMAGE = 1;
	public static final int COLUMN_TYPE_BARCODE = 2;

	private String title;
	private Integer width = new Integer(50);
	private Boolean fixedWidth = Boolean.FALSE;
	private Style style;
	private Style headerStyle;
	private ColumnProperty columnProperty;
	private CustomExpression customExpression;
	private CustomExpression customExpressionToGroupBy;
	private String pattern;
	private boolean printRepeatedValues = true;
	private ArrayList conditionalStyles = new ArrayList();
	private ColumnOperation operation;
	private List operationColumns;
	private PropertiesMap fieldProperties = new PropertiesMap();
	private ImageScaleMode imageScaleMode = ImageScaleMode.FILL_PROPORTIONALLY;
	private String fieldDescription;

	private int columnType = COLUMN_TYPE_DEFAULT;


	/**
	 * For BARCODE columns
	 */
	private int barcodeType;
	private String applicationIdentifier;
	private boolean showText = false;
	private boolean checkSum = false;

	public static ColumnBuilder getInstance(){
		return new ColumnBuilder();
	}

	public AbstractColumn build() throws ColumnBuilderException{
		if (customExpression == null && columnProperty == null){
			throw new ColumnBuilderException("Either a ColumnProperty or a CustomExpression must be present");
		}

		if (columnType == COLUMN_TYPE_IMAGE){
			return buildSimpleImageColumn();
		}
		else if (columnType == COLUMN_TYPE_BARCODE){
			return buildSimpleBarcodeColumn();
		}
		else if (columnProperty != null) {
			return buildSimpleColumn();
		} else if (customExpression==null) {
			return buildOperationColumn();
		} else {
			return buildExpressionColumn();
		}
	}

	/**
	 * When creating barcode columns
	 * @return
	 */
	protected AbstractColumn buildSimpleBarcodeColumn() {
		BarCodeColumn column = new BarCodeColumn();
		populateCommonAttributes(column);
		column.setColumnProperty(columnProperty);
		column.setExpressionToGroupBy(customExpressionToGroupBy);
		column.setScaleMode(imageScaleMode);
		column.setApplicationIdentifier(applicationIdentifier);
		column.setBarcodeType(barcodeType);
		column.setShowText(showText);
		column.setCheckSum(checkSum);
		return column;
	}

	/**
	 * When creating image columns
	 * @return
	 */
	protected AbstractColumn buildSimpleImageColumn() {
		ImageColumn column = new ImageColumn();
		populateCommonAttributes(column);
		column.setColumnProperty(columnProperty);
		column.setExpressionToGroupBy(customExpressionToGroupBy);
		column.setScaleMode(imageScaleMode);
		return column;
	}

	/**
	 * For creating expression columns
	 * @return
	 */
	protected AbstractColumn buildExpressionColumn() {
		ExpressionColumn column = new ExpressionColumn();
		populateCommonAttributes(column);
		int random = new Random().nextInt();
		column.setColumnProperty(new ColumnProperty("expressionColumn" + random,CustomExpression.class.getName()));
		column.setExpression(customExpression);
		column.setExpressionToGroupBy(customExpressionToGroupBy);
//		column.getFieldProperties().putAll(fieldProperties);
		return column;
	}

	/**
	 * For creating regular columns
	 * @return
	 */
	protected AbstractColumn buildSimpleColumn() {
		SimpleColumn column = new SimpleColumn();
		populateCommonAttributes(column);
		columnProperty.getFieldProperties().putAll(fieldProperties);
		column.setColumnProperty(columnProperty);
		column.setExpressionToGroupBy(customExpressionToGroupBy);
		column.setFieldDescription(fieldDescription);
		return column;
	}

	protected AbstractColumn buildOperationColumn() {
		OperationColumn column = new OperationColumn();
		populateCommonAttributes(column);
		column.setColumnOperation(operation);
		column.setColumns(operationColumns);
		return column;
	}

	protected void populateCommonAttributes(AbstractColumn column) {
		column.setTitle(title);
		column.setWidth(width);
		column.setPattern(pattern);
		column.setHeaderStyle(headerStyle);
		column.setStyle(style);
		column.setPrintRepeatedValues(Boolean.valueOf(printRepeatedValues));
		column.getConditionalStyles().addAll(conditionalStyles);
		column.setFixedWidth(fixedWidth);
	}

	/**
	 * @deprecated
	 * @param title
	 * @return
	 */
	public ColumnBuilder addTitle(String title) {
		this.title = title;
		return this;
	}

	public ColumnBuilder setTitle(String title) {
		this.title = title;
		return this;
	}

	/**
	 * @deprecated
	 * @param pattern
	 * @return
	 */
	public ColumnBuilder addPattern(String pattern) {
		this.pattern = pattern;
		return this;
	}

	public ColumnBuilder setPattern(String pattern) {
		this.pattern = pattern;
		return this;
	}

	/**
	 * @deprecated
	 * @param bool
	 * @return
	 */
	public ColumnBuilder addPrintRepeatedValues(boolean bool) {
		this.printRepeatedValues = bool;
		return this;
	}

	public ColumnBuilder setPrintRepeatedValues(boolean bool) {
		this.printRepeatedValues = bool;
		return this;
	}

	/**
	 * @deprecated
	 * @param bool
	 * @return
	 */
	public ColumnBuilder addPrintRepeatedValues(Boolean bool) {
		this.printRepeatedValues = bool.booleanValue();
		return this;
	}
	public ColumnBuilder setPrintRepeatedValues(Boolean bool) {
		this.printRepeatedValues = bool.booleanValue();
		return this;
	}

	/**
	 * @deprecated
	 * @param width
	 * @return
	 */
	public ColumnBuilder addWidth(Integer width) {
		this.width = width;
		return this;
	}
	public ColumnBuilder setWidth(Integer width) {
		this.width = width;
		return this;
	}

	/**
	 * @deprecated
	 * @param width
	 * @return
	 */
	public ColumnBuilder addWidth(int width) {
		this.width = new Integer(width);
		return this;
	}
	public ColumnBuilder setWidth(int width) {
		this.width = new Integer(width);
		return this;
	}

	/**
	 * @deprecated
	 * @param style
	 * @return
	 */
	public ColumnBuilder addStyle(Style style) {
		this.style = style;
		return this;
	}
	public ColumnBuilder setStyle(Style style) {
		this.style = style;
		return this;
	}

	/**
	 * @deprecated
	 * @param style
	 * @return
	 */
	public ColumnBuilder addHeaderStyle(Style style) {
		this.headerStyle = style;
		return this;
	}
	public ColumnBuilder setHeaderStyle(Style style) {
		this.headerStyle = style;
		return this;
	}

	/**
	 * Adds a property to the column being created.</br>
	 * @param ColumnProperty columnProperty : BeanUtils like syntax allowed here
	 * @return ColumnBuilder
	 * @deprecated
	 */
	public ColumnBuilder addColumnProperty(ColumnProperty columnProperty ){
		this.columnProperty = columnProperty;
		return this;
	}
	public ColumnBuilder setColumnProperty(ColumnProperty columnProperty ){
		this.columnProperty = columnProperty;
		return this;
	}

	/**
	 * Adds a property to the column being created.</br>
	 * @param ColumnProperty columnProperty : BeanUtils like syntax allowed here
	 * @param String valueClassName
	 * @return ColumnBuilder
	 * @deprecated
	 */
	public ColumnBuilder addColumnProperty(String propertyName, String valueClassName ){
		ColumnProperty columnProperty = new ColumnProperty(propertyName,valueClassName);
		this.columnProperty = columnProperty;
		return this;
	}
	public ColumnBuilder setColumnProperty(String propertyName, String valueClassName ){
		ColumnProperty columnProperty = new ColumnProperty(propertyName,valueClassName);
		this.columnProperty = columnProperty;
		return this;
	}

	public ColumnBuilder setFieldDescription(String fieldDescription){
		this.fieldDescription = fieldDescription;
		return this;
	}
	public ColumnBuilder setColumnProperty(String propertyName, String valueClassName, String fieldDescription ){
		ColumnProperty columnProperty = new ColumnProperty(propertyName,valueClassName);
		this.columnProperty = columnProperty;
		this.fieldDescription = fieldDescription;
		return this;
	}

	/**
	 * When the JRField needs properties, use this method.
	 * @param propertyName
	 * @param value
	 * @return
	 */
	public ColumnBuilder addFieldProperty(String propertyName, String value) {
		fieldProperties.put(propertyName, value);
		return this;
	}

	public ColumnBuilder setCustomExpression(CustomExpression customExpression){
		this.customExpression = customExpression;
		return this;
	}
	/**
	 * @deprecated
	 */
	public ColumnBuilder addCustomExpression(CustomExpression customExpression){
		this.customExpression = customExpression;
		return this;
	}

/**
 * @param conditionalStyle
 * @return
 */
	public ColumnBuilder addConditionalStyle(ConditionalStyle conditionalStyle) {
		this.conditionalStyles.add(conditionalStyle);
		return this;
	}

	/**
	 * @param conditionalStyles
	 * @return
	 */
	public ColumnBuilder addConditionalStyles(Collection conditionalStyles) {
		this.conditionalStyles.addAll(conditionalStyles);
		return this;
	}

	public ColumnBuilder addColumnOperation(ColumnOperation operation, AbstractColumn[] operationColumns) {
		this.operation = operation;
		this.operationColumns = new ArrayList();
		for (int i = 0; i < operationColumns.length; i++) {
			this.operationColumns.add(operationColumns[i]);
		}
		return this;
	}

/**
 * @deprecated
 * @param bool
 * @return
 */
	public ColumnBuilder addFixedWidth(boolean bool) {
		this.fixedWidth = Boolean.valueOf(bool);
		return this;
	}
	public ColumnBuilder setFixedWidth(boolean bool) {
		this.fixedWidth = Boolean.valueOf(bool);
		return this;
	}

	/**
	 * @deprecated
	 * @param bool
	 * @return
	 */
	public ColumnBuilder addFixedWidth(Boolean bool) {
		this.fixedWidth = bool;
		return this;
	}
	public ColumnBuilder setFixedWidth(Boolean bool) {
		this.fixedWidth = bool;
		return this;
	}

	/**
	 * For image columns use: {@link #COLUMN_TYPE_IMAGE} or {@link #COLUMN_TYPE_BARCODE}
	 * @param columnType
	 * @return
	 */
	public ColumnBuilder setColumnType(int columnType) {
		this.columnType = columnType;
		return this;
	}

	public ColumnBuilder setImageScaleMode(ImageScaleMode imageScaleMode) {
		this.imageScaleMode = imageScaleMode;
		return this;
	}

	public ColumnBuilder setCommonProperties(String title, String property, String className, int width, boolean fixedWidth) throws ColumnBuilderException, ClassNotFoundException {
		setColumnProperty(new ColumnProperty(property, className));
		setWidth(new Integer(width));
		setTitle(title);
		setFixedWidth(Boolean.valueOf(fixedWidth));
		return this;
	}



	/**
	 *
	 * @param barcodeType use constanst defined in {@link BarcodeTypes}
	 * @return
	 */
	public ColumnBuilder setBarcodeType(int barcodeType) {
		this.barcodeType = barcodeType;
		return this;
	}

	public ColumnBuilder setShowText(boolean showText) {
		this.showText  = showText;
		return this;
	}
	public ColumnBuilder setCheckSum(boolean checkSum) {
		this.checkSum  = checkSum;
		return this;
	}


	/**
	 * Only used when barcode type is UCCEAN128
	 * @param applicationIdentifier
	 * @return
	 */
	public ColumnBuilder setApplicationIdentifier(String applicationIdentifier) {
		this.applicationIdentifier = applicationIdentifier;
		return this;
	}

}