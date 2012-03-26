/*
 * Copyright (c) 2012, FDV Solutions S.A.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *     * Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 *     * Neither the name of the FDV Solutions S.A. nor the
 *       names of its contributors may be used to endorse or promote products
 *       derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL FDV Solutions S.A. BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package ar.com.fdvs.dj.domain.builders;

import java.text.Format;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

import ar.com.fdvs.dj.core.BarcodeTypes;
import ar.com.fdvs.dj.domain.ColumnOperation;
import ar.com.fdvs.dj.domain.ColumnProperty;
import ar.com.fdvs.dj.domain.CustomExpression;
import ar.com.fdvs.dj.domain.Style;
import ar.com.fdvs.dj.domain.constants.ImageScaleMode;
import ar.com.fdvs.dj.domain.entities.DJGroup;
import ar.com.fdvs.dj.domain.entities.columns.AbstractColumn;
import ar.com.fdvs.dj.domain.entities.columns.BarCodeColumn;
import ar.com.fdvs.dj.domain.entities.columns.ExpressionColumn;
import ar.com.fdvs.dj.domain.entities.columns.ImageColumn;
import ar.com.fdvs.dj.domain.entities.columns.OperationColumn;
import ar.com.fdvs.dj.domain.entities.columns.PercentageColumn;
import ar.com.fdvs.dj.domain.entities.columns.PropertyColumn;
import ar.com.fdvs.dj.domain.entities.columns.SimpleColumn;
import ar.com.fdvs.dj.domain.entities.conditionalStyle.ConditionalStyle;
import ar.com.fdvs.dj.util.PropertiesMap;

/**
 * Builder created to give users a friendly way of adding columns to a report.</br>
 * </br>
 * Usage example: </br>
 * AbstractColumn columnState = ColumnBuilder.getNew() </br>
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
	private CustomExpression customExpressionForCalculation;
	private CustomExpression customExpressionToGroupBy;
	private String pattern;
	private boolean printRepeatedValues = true;
	private ArrayList conditionalStyles = new ArrayList();
	private ColumnOperation operation;
	private List operationColumns = new ArrayList();
	private PropertiesMap fieldProperties = new PropertiesMap();
	private ImageScaleMode imageScaleMode = ImageScaleMode.FILL_PROPORTIONALLY;
	private String fieldDescription;
	private String truncateSuffix;
//	private String formatParameter;
	private Format textFormatter;
	private PropertyColumn percentageColumn;
    
	private int columnType = COLUMN_TYPE_DEFAULT;
	private static Random random = new Random();


	/**
	 * For BARCODE columns
	 */
	private int barcodeType;
	private String applicationIdentifier;
	private boolean showText = false;
	private boolean checkSum = false;

	/**
	 * @deprecated use getNew()
	 * @return
	 */
	public static ColumnBuilder getInstance(){
		return getNew();
	}

	public static ColumnBuilder getNew(){
		return new ColumnBuilder();
	}

	public AbstractColumn build() throws ColumnBuilderException{
		if (customExpression == null && columnProperty == null && operationColumns.isEmpty() && percentageColumn == null){
			throw new ColumnBuilderException("Either a ColumnProperty or a CustomExpression or a PercentageColumn must be present");
		}

		AbstractColumn col = null;
		if (columnType == COLUMN_TYPE_IMAGE){
			col = buildSimpleImageColumn();
		}
		else if (columnType == COLUMN_TYPE_BARCODE){
			col = buildSimpleBarcodeColumn();
		}
		else if (percentageColumn != null) {
			col = buildPercentageColumn();
		}
		else if (columnProperty != null && customExpression == null) { //FIXME Horrible!!! Can't I create an expression column with a propery also?
			col = buildSimpleColumn();
		} 
		else if (!operationColumns.isEmpty()) {
			col = buildOperationColumn();
		} 
		else { //customExpression should NOT be null
			col = buildExpressionColumn();
		}
		return col;
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
		populateExpressionAttributes(column);
		
		column.setExpression(customExpression);
		column.setExpressionToGroupBy(customExpressionToGroupBy);
		column.setExpressionForCalculation(customExpressionForCalculation);
		
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
		
		populateExpressionAttributes(column);
		
		column.setExpression(customExpression);
		column.setExpressionToGroupBy(customExpressionToGroupBy);
		column.setExpressionForCalculation(customExpressionForCalculation);
		
		return column;
	}

	protected void populateExpressionAttributes(ExpressionColumn column) {
		if (columnProperty != null ) {
			columnProperty.getFieldProperties().putAll(fieldProperties);
			column.setColumnProperty(columnProperty);
			column.setExpressionToGroupBy(customExpressionToGroupBy);
			column.setFieldDescription(fieldDescription);		
		} else {
			long random_ = Math.abs(random.nextLong());
			column.setColumnProperty(new ColumnProperty("__name_to_be_replaced_in_registration_manager_" + random_,CustomExpression.class.getName()));
		}
	}

	protected AbstractColumn buildPercentageColumn() {
		PercentageColumn column = new PercentageColumn();
		populateCommonAttributes(column);
		column.setPercentageColumn(percentageColumn);
//		column.setGroup(percentageGroup);
		if (pattern == null)
			column.setPattern("#,##0.00%");
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
		column.setTruncateSuffix(truncateSuffix);
        column.setTextFormatter(textFormatter);
	}

	public ColumnBuilder setTitle(String title) {
		this.title = title;
		return this;
	}

	public ColumnBuilder setPattern(String pattern) {
		this.pattern = pattern;
		return this;
	}

	public ColumnBuilder setPrintRepeatedValues(boolean bool) {
		this.printRepeatedValues = bool;
		return this;
	}

	public ColumnBuilder setPrintRepeatedValues(Boolean bool) {
		this.printRepeatedValues = bool.booleanValue();
		return this;
	}

	public ColumnBuilder setWidth(Integer width) {
		this.width = width;
		return this;
	}

	public ColumnBuilder setWidth(int width) {
		this.width = new Integer(width);
		return this;
	}

	public ColumnBuilder setStyle(Style style) {
		this.style = style;
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
	 */
	public ColumnBuilder setColumnProperty(ColumnProperty columnProperty ){
		this.columnProperty = columnProperty;
		return this;
	}

	/**
	 * Adds a property to the column being created.</br>
	 * @param ColumnProperty columnProperty : BeanUtils like syntax allowed here
	 * @param String valueClassName
	 * @return ColumnBuilder
	 */
	public ColumnBuilder setColumnProperty(String propertyName, String valueClassName ){
		ColumnProperty columnProperty = new ColumnProperty(propertyName,valueClassName);
		this.columnProperty = columnProperty;
		return this;
	}

	public ColumnBuilder setColumnProperty(String propertyName, Class clazz ){
		ColumnProperty columnProperty = new ColumnProperty(propertyName,clazz.getName());
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

	public ColumnBuilder setCustomExpressionToGroupBy(CustomExpression customExpression){
		this.customExpressionToGroupBy = customExpression;
		return this;
	}

	public ColumnBuilder setCustomExpressionForCalculation(CustomExpression customExpression){
		this.customExpressionForCalculation = customExpression;
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

	public ColumnBuilder setFixedWidth(boolean bool) {
		this.fixedWidth = Boolean.valueOf(bool);
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

	public ColumnBuilder setCommonProperties(String title, String property, String className, int width, boolean fixedWidth) {
		setColumnProperty(new ColumnProperty(property, className));
		setWidth(new Integer(width));
		setTitle(title);
		setFixedWidth(Boolean.valueOf(fixedWidth));
		return this;
	}

	public ColumnBuilder setCommonProperties(String title, String property, Class clazz, int width, boolean fixedWidth) {
		setColumnProperty(new ColumnProperty(property, clazz));
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

	/**
	 * A suffix to be used in case content does not fit in given space. 
	 * Must be used with style.setStretchWithOverflow(false);
	 * @param suffix
	 * @return
	 */
	public ColumnBuilder setTruncateSuffix(String suffix) {
		this.truncateSuffix = suffix;
		return this;
	}

    public ColumnBuilder setTextFormatter(Format textFormatter) {
    	this.textFormatter = textFormatter; 
    	return this;
    }

  public ColumnBuilder setPercentageColumn(PropertyColumn percentageColumn) {
  	this.percentageColumn = percentageColumn;
  	return this;
  }
  
  /**
   * Use setPercentageColumn(PropertyColumn percentageColumn)
   * @deprecated
   */
  public ColumnBuilder setPercentageColumn(PropertyColumn percentageColumn, DJGroup group) {
  	return setPercentageColumn(percentageColumn);
  }
}