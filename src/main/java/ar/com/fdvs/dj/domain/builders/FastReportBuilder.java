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

import ar.com.fdvs.dj.core.BarcodeTypes;
import ar.com.fdvs.dj.core.DJConstants;
import ar.com.fdvs.dj.domain.*;
import ar.com.fdvs.dj.domain.constants.*;
import ar.com.fdvs.dj.domain.constants.Font;
import ar.com.fdvs.dj.domain.constants.Transparency;
import ar.com.fdvs.dj.domain.entities.DJGroup;
import ar.com.fdvs.dj.domain.entities.DJGroupVariable;
import ar.com.fdvs.dj.domain.entities.DJVariable;
import ar.com.fdvs.dj.domain.entities.columns.AbstractColumn;
import ar.com.fdvs.dj.domain.entities.columns.PropertyColumn;

import java.awt.*;
import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

/**
 * Builder created to give users a friendly way of creating a DynamicReport.</br>
 * </br>
 * Usage example: </br>
 * DynamicReportBuilder drb = new DynamicReportBuilder();
 * Integer margin = new Integer(20);
 * drb.addTitle("Clients List").addTitleStyle(titleStyle)
 * .addSubtitle("Clients without debt")
 * .addDetailHeight(new Integer(15))
 * .addLeftMargin(margin).addRightMargin(margin).addTopMargin(margin)
 * .addBottomMargin(margin)
 * .addPrintBackgroundOnOddRows(true).addOddRowBackgroundStyle(oddRowStyle)
 * .addColumnsPerPage(new Integer(1)).addColumnSpace(new Integer(5))
 * .addColumn(column1).addColumn(column2).build();
 * </br>
 * Like with all DJ's builders, it's usage must end with a call to build() mehtod.
 * </br>
 */
public class FastReportBuilder extends DynamicReportBuilder {

	Style currencyStyle;
	Style numberStyle;
	Style subtitleStyle;

	protected int groupCount = 0;

//	protected Map headerGroupVariables = new TreeMap();
//	protected Map footerGroupVariables = new TreeMap();

	public FastReportBuilder(){
		currencyStyle = new Style("currencyStyle");
		currencyStyle.setHorizontalAlign(HorizontalAlign.RIGHT);

		numberStyle = new Style("numberStyle");
		numberStyle.setHorizontalAlign(HorizontalAlign.RIGHT);

		Style defaultHeaderStyle = options.getDefaultHeaderStyle();
		defaultHeaderStyle.setFont(Font.ARIAL_MEDIUM_BOLD);
		defaultHeaderStyle.setHorizontalAlign(HorizontalAlign.CENTER);
		defaultHeaderStyle.setBorderBottom(Border.THIN());
		defaultHeaderStyle.setVerticalAlign(VerticalAlign.MIDDLE);
		defaultHeaderStyle.setBackgroundColor(Color.LIGHT_GRAY);
		defaultHeaderStyle.setTransparency(Transparency.OPAQUE);

		Style titleStyle2 = report.getTitleStyle();
		titleStyle2.setFont(Font.ARIAL_BIG_BOLD);
		titleStyle2.setHorizontalAlign(HorizontalAlign.CENTER);
		titleStyle2.setVerticalAlign(VerticalAlign.TOP);
	}

	public DynamicReport build(){

		return super.build();
	}

    public FastReportBuilder addColumn(String title, String property, String className, int width, Style style) throws ColumnBuilderException, ClassNotFoundException {
		AbstractColumn column = ColumnBuilder.getNew()
			.setColumnProperty(new ColumnProperty(property, className))
			.setWidth(new Integer(width))
			.setTitle(title)
			.build();

		column.setStyle(style);

		addColumn(column);

		return this;
	}
    
    public FastReportBuilder addColumn(String title, String property, Class clazz, int width, Style style) throws ColumnBuilderException, ClassNotFoundException {
    	addColumn(title, property, clazz.getName(), width, style);
    	return this;
    }
    
    public FastReportBuilder addColumn(String title, String property, String className, int width, Style style, Style headerStyle) throws ColumnBuilderException, ClassNotFoundException {
    	AbstractColumn column = ColumnBuilder.getNew()
    	.setColumnProperty(new ColumnProperty(property, className))
    	.setWidth(new Integer(width))
    	.setTitle(title)
    	.build();
    	
    	if (style != null)
    		column.setStyle(style);
    	if (headerStyle != null)
    		column.setHeaderStyle(headerStyle);
    	
    	addColumn(column);
    	
    	return this;
    }
    
    public FastReportBuilder addColumn(String title, String property, Class clazz, int width, Style style, Style headerStyle) throws ColumnBuilderException, ClassNotFoundException {
    	return addColumn(title, property, clazz.getName(), width, style, headerStyle);
    }

    public FastReportBuilder addColumn(String title, String property, String className, int width) throws ColumnBuilderException, ClassNotFoundException {
		AbstractColumn column = ColumnBuilder.getNew()
			.setColumnProperty(new ColumnProperty(property, className))
			.setWidth(new Integer(width))
			.setTitle(title)
			.build();

		guessStyle(className, column);

		addColumn(column);

		return this;
	}
    public FastReportBuilder addColumn(String title, String property, Class clazz, int width) throws ColumnBuilderException, ClassNotFoundException {
    	return addColumn(title, property, clazz.getName(), width);
    }

    
	public FastReportBuilder addColumn(String title, String property, String className, int width, boolean fixedWidth) throws ColumnBuilderException, ClassNotFoundException {
		AbstractColumn column = ColumnBuilder.getNew()
			.setColumnProperty(property, className)
			.setWidth(new Integer(width))
			.setTitle(title)
			.setFixedWidth(Boolean.valueOf(fixedWidth))
			.build();

		guessStyle(className, column);

		addColumn(column);

		return this;
	}

	public FastReportBuilder addColumn(String title, String property, String className, int width, Style style, Style headerStyle, boolean fixedWidth) throws ColumnBuilderException, ClassNotFoundException {
		AbstractColumn column = ColumnBuilder.getNew()
		.setColumnProperty(property, className)
		.setWidth(new Integer(width))
		.setTitle(title)
		.setFixedWidth(Boolean.valueOf(fixedWidth))
		.setStyle(style)
		.build();
		
    	if (style != null)
    		column.setStyle(style);
		else 
			guessStyle(className, column);
    	
    	if (headerStyle != null)
    		column.setHeaderStyle(headerStyle);		
		
		
		addColumn(column);
		
		return this;
	}
	
	public FastReportBuilder addColumn(String title, String property, Class clazz, int width, boolean fixedWidth) throws ColumnBuilderException, ClassNotFoundException {
		return addColumn(title, property, clazz.getName(), width, fixedWidth);
	}

	public FastReportBuilder addImageColumn(String title, String property, int width, boolean fixedWidth, ImageScaleMode imageScaleMode) throws ColumnBuilderException, ClassNotFoundException {
		String className = InputStream.class.getName();
		AbstractColumn column = ColumnBuilder.getNew()
			.setColumnProperty(property, className)
			.setWidth(new Integer(width))
			.setTitle(title)
			.setFixedWidth(Boolean.valueOf(fixedWidth))
			.setColumnType(ColumnBuilder.COLUMN_TYPE_IMAGE)
			.setImageScaleMode(imageScaleMode)
			.build();

		guessStyle(className, column);

		addColumn(column);

		return this;
	}

	/**
	 * By default uses InputStream as the type of the image
	 * @param title
	 * @param property
	 * @param width
	 * @param fixedWidth
	 * @param imageScaleMode
	 * @param style
	 * @return
	 * @throws ColumnBuilderException
	 * @throws ClassNotFoundException
	 */
	public FastReportBuilder addImageColumn(String title, String property, int width, boolean fixedWidth,ImageScaleMode imageScaleMode, Style style) throws ColumnBuilderException, ClassNotFoundException {
		String className = InputStream.class.getName();
		AbstractColumn column = ColumnBuilder.getNew()
		.setColumnProperty(property, className)
		.setWidth(new Integer(width))
		.setTitle(title)
		.setFixedWidth(Boolean.valueOf(fixedWidth))
		.setColumnType(ColumnBuilder.COLUMN_TYPE_IMAGE)
		.setImageScaleMode(imageScaleMode)
		.setStyle(style)
		.build();

		guessStyle(className, column);

		addColumn(column);

		return this;
	}

	public FastReportBuilder addImageColumn(String title, CustomExpression expression, int width, boolean fixedWidth,ImageScaleMode imageScaleMode, Style style) throws ColumnBuilderException, ClassNotFoundException {
		String className = InputStream.class.getName();
		AbstractColumn column = ColumnBuilder.getNew()
		.setCustomExpression(expression)
		.setWidth(new Integer(width))
		.setTitle(title)
		.setFixedWidth(Boolean.valueOf(fixedWidth))
		.setColumnType(ColumnBuilder.COLUMN_TYPE_IMAGE)
		.setImageScaleMode(imageScaleMode)
		.setStyle(style)
		.build();
		
		guessStyle(className, column);
		
		addColumn(column);
		
		return this;
	}

	/**
	 *
	 * @param title
	 * @param property
	 * @param className valid class names are: InputStream and java.awt.Image
	 * @param width
	 * @param fixedWidth
	 * @param imageScaleMode
	 * @param style
	 * @return
	 * @throws ColumnBuilderException
	 * @throws ClassNotFoundException
	 */
	public FastReportBuilder addImageColumn(String title, String property, String className, int width, boolean fixedWidth,ImageScaleMode imageScaleMode, Style style) throws ColumnBuilderException, ClassNotFoundException {
		AbstractColumn column = ColumnBuilder.getNew()
		.setColumnProperty(property, className)
		.setWidth(new Integer(width))
		.setTitle(title)
		.setFixedWidth(Boolean.valueOf(fixedWidth))
		.setColumnType(ColumnBuilder.COLUMN_TYPE_IMAGE)
		.setStyle(style)
		.build();

		if (style == null)
			guessStyle(className, column);

		addColumn(column);

		return this;
	}

	public FastReportBuilder addBarcodeColumn(String title, String property, String className, int barcodeType, boolean showText, int width, boolean fixedWidth, ImageScaleMode imageScaleMode) throws ColumnBuilderException, ClassNotFoundException {
		AbstractColumn column = ColumnBuilder.getNew()
		.setColumnProperty(property, className)
		.setWidth(new Integer(width))
		.setTitle(title)
		.setFixedWidth(Boolean.valueOf(fixedWidth))
		.setColumnType(ColumnBuilder.COLUMN_TYPE_BARCODE)
		.setImageScaleMode(imageScaleMode)
		.setBarcodeType(barcodeType)
		.setShowText(showText)
		.build();

//		guessStyle(className, column); //NOT FOR BARCODE!!!

		addColumn(column);

		return this;
	}

	/**
	 * By default uses InputStream as the type of the image
	 * @param title
	 * @param property
	 * @param width
	 * @param fixedWidth
	 * @param imageScaleMode
	 * @param style
	 * @return
	 * @throws ColumnBuilderException
	 * @throws ClassNotFoundException
	 */
	public FastReportBuilder addBarcodeColumn(String title, String property,String className, int  barcodeType,boolean showText, int width, boolean fixedWidth,ImageScaleMode imageScaleMode, Style style) throws ColumnBuilderException, ClassNotFoundException {
		AbstractColumn column = ColumnBuilder.getNew()
		.setColumnProperty(property, className)
		.setWidth(new Integer(width))
		.setTitle(title)
		.setFixedWidth(Boolean.valueOf(fixedWidth))
		.setColumnType(ColumnBuilder.COLUMN_TYPE_BARCODE)
		.setStyle(style)
		.setBarcodeType(barcodeType)
		.setShowText(showText)
		.build();

		if (style == null)
			guessStyle(className, column);

		addColumn(column);

		return this;
	}

/**
 *
 * @param title
 * @param property
 * @param className  valid class names are: InputStream and java.awt.Image
 * @param barcodeType use constansts from {@link BarcodeTypes}
 * @param showText
 * @param checkSum
 * @param applicationIdentifier Only for barcodeType = UCCEAN128, this value must point to a property (it register the property)
 * @param width
 * @param fixedWidth
 * @param imageScaleMode
 * @param style
 * @return
 * @throws ColumnBuilderException
 * @throws ClassNotFoundException
 */
	public FastReportBuilder addBarcodeColumn(String title, String property, String className, int barcodeType, boolean showText, boolean checkSum, String applicationIdentifier, int width, boolean fixedWidth, ImageScaleMode imageScaleMode, Style style) throws ColumnBuilderException, ClassNotFoundException {
		AbstractColumn column = ColumnBuilder.getNew()
		.setColumnProperty(property, className)
		.setWidth(new Integer(width))
		.setTitle(title)
		.setFixedWidth(Boolean.valueOf(fixedWidth))
		.setColumnType(ColumnBuilder.COLUMN_TYPE_BARCODE)
		.setBarcodeType(barcodeType)
		.setApplicationIdentifier(applicationIdentifier)
		.setStyle(style)
		.setShowText(showText)
		.setCheckSum(checkSum)
		.build();

		if (applicationIdentifier != null){
			addField(applicationIdentifier,  Object.class.getName());
		}

		if (style == null)
			guessStyle(className, column);

		addColumn(column);

		return this;
	}

	public FastReportBuilder addColumn(String title, String property, String className, int width, boolean fixedWidth, String pattern) throws ColumnBuilderException, ClassNotFoundException {
		AbstractColumn column = ColumnBuilder.getNew()
		.setColumnProperty(new ColumnProperty(property, className))
		.setWidth(new Integer(width))
		.setTitle(title)
		.setFixedWidth(Boolean.valueOf(fixedWidth))
		.setPattern(pattern)
		.build();

		guessStyle(className, column);

		addColumn(column);

		return this;
	}
	public FastReportBuilder addColumn(String title, String property, Class clazz, int width, boolean fixedWidth, String pattern) throws ColumnBuilderException, ClassNotFoundException {
		return addColumn(title, property, clazz.getName(), width, fixedWidth, pattern);
	}

	public FastReportBuilder addColumn(String title, String property, String className, int width, boolean fixedWidth, String pattern, Style style) throws ColumnBuilderException, ClassNotFoundException {
		AbstractColumn column = ColumnBuilder.getNew()
		.setColumnProperty(new ColumnProperty(property, className))
		.setWidth(new Integer(width))
		.setTitle(title)
		.setFixedWidth(Boolean.valueOf(fixedWidth))
		.setPattern(pattern)
		.setStyle(style)
		.build();

		if (style == null)
			guessStyle(className, column);

		addColumn(column);

		return this;
	}
	public FastReportBuilder addColumn(String title, String property, Class clazz, int width, boolean fixedWidth, String pattern, Style style) throws ColumnBuilderException, ClassNotFoundException {
		return addColumn(title, property, clazz.getName(), width, fixedWidth, pattern, style);
	}
	
	public FastReportBuilder addColumn(String title, String property, String className, int width, boolean fixedWidth, String pattern, Style style, String fieldDescription) throws ColumnBuilderException, ClassNotFoundException {
		AbstractColumn column = ColumnBuilder.getNew()
		.setColumnProperty(new ColumnProperty(property, className))
		.setWidth(new Integer(width))
		.setTitle(title)
		.setFixedWidth(Boolean.valueOf(fixedWidth))
		.setPattern(pattern)
		.setStyle(style)
		.setFieldDescription(fieldDescription)
		.build();
		
		if (style == null)
			guessStyle(className, column);
		
		addColumn(column);
		
		return this;
	}
	public FastReportBuilder addColumn(String title, String property, Class clazz, int width, boolean fixedWidth, String pattern, Style style, String fieldDescription) throws ColumnBuilderException, ClassNotFoundException {
		return addColumn(title, property, clazz.getName(), width, fixedWidth, pattern, style, fieldDescription);
	}
	
	public FastReportBuilder addColumn(String title, CustomExpression expression, int width, boolean fixedWidth, String pattern, Style style) throws ColumnBuilderException, ClassNotFoundException {
		AbstractColumn column = ColumnBuilder.getNew()
		.setCustomExpression(expression)
		.setWidth(new Integer(width))
		.setTitle(title)
		.setFixedWidth(Boolean.valueOf(fixedWidth))
		.setPattern(pattern)
		.setStyle(style)
		.build();

		if (style == null)
			guessStyle(expression.getClassName(), column);

		addColumn(column);

		return this;
	}	

	protected void guessStyle(Class clazz, AbstractColumn column) throws ClassNotFoundException {
		guessStyle(clazz.getName(), column);
	}
	
	protected void guessStyle(String className, AbstractColumn column) throws ClassNotFoundException {

		Class clazz = Class.forName(className);
		if (BigDecimal.class.isAssignableFrom(clazz) || Float.class.isAssignableFrom(clazz) || Double.class.isAssignableFrom(clazz)) {
			if (column.getPattern() == null)
				column.setPattern("$ #.00");
			column.setStyle(currencyStyle);
		}

		if (Integer.class.isAssignableFrom(clazz) || Long.class.isAssignableFrom(clazz)) {
			column.setStyle(numberStyle);
		}

		if (Date.class.isAssignableFrom(clazz)) {
			if (column.getPattern() == null)
				column.setPattern("dd/MM/yy");
		}

		if (Timestamp.class.isAssignableFrom(clazz)) {
			if (column.getPattern() == null)
				column.setPattern("dd/MM/yy hh:mm:ss");
		}
	}

	/**
	 * This method should be called after all column have been added to the report.
	 * @param numgroups
	 * @return
	 */
	public FastReportBuilder addGroups(int numgroups) {
		groupCount = numgroups;
		for (int i = 0; i < groupCount; i++) {
			GroupBuilder gb = new GroupBuilder();
			PropertyColumn col = (PropertyColumn) report.getColumns().get(i);
			gb.setCriteriaColumn(col);
			report.getColumnsGroups().add(gb.build());
		}
		return this;
	}

	public FastReportBuilder addGroups(int numgroups, GroupLayout gLayout) {
		groupCount = numgroups;
		for (int i = 0; i < groupCount; i++) {
			GroupBuilder gb = new GroupBuilder();
			PropertyColumn col = (PropertyColumn) report.getColumns().get(i);
			gb.setCriteriaColumn(col);
			gb.setGroupLayout(gLayout);
			report.getColumnsGroups().add(gb.build());
		}
		return this;
	}

	public FastReportBuilder setGroupLayout(int groupNumber, GroupLayout layout) throws BuilderException {
		DJGroup group = getGroupByNumber(groupNumber);
		group.setLayout(layout);
		return this;
	}

	public FastReportBuilder addGlobalHeaderVariable(int colNumber, DJCalculation op, Style style) {
		PropertyColumn column = (PropertyColumn) report.getColumns().get(colNumber -1);
//		if (this.globalHeaderVariables == null)
//			this.globalHeaderVariables = new ArrayList();
		if (style == null)
			style = numberStyle; //XXX Carefree style assignment
//		this.globalHeaderVariables.add(new DJGroupVariable(column, op, style));
		
		this.globalVariablesGroup.addHeaderVariable(new DJGroupVariable(column, op, style));
		return this;
	}

	public FastReportBuilder addHeaderVariable(int groupNum, int colNumber, DJCalculation op, Style style) throws BuilderException {
		DJGroup group = getGroupByNumber(groupNum);
		PropertyColumn column = (PropertyColumn) report.getColumns().get(colNumber -1);
		if (style == null)
			style = numberStyle;

		DJGroupVariable columnsGroupVariable = new DJGroupVariable(column, op, style);
		group.addHeaderVariable(columnsGroupVariable);
		return this;
	}
	
	public FastReportBuilder addHeaderVariable(int groupNum, int colNumber, DJCalculation op, Style style, DJValueFormatter valueFormatter) throws BuilderException {
		DJGroup group = getGroupByNumber(groupNum);
		PropertyColumn column = (PropertyColumn) report.getColumns().get(colNumber -1);
		if (style == null)
			style = numberStyle;
		
		DJGroupVariable columnsGroupVariable = new DJGroupVariable(column, op, style, valueFormatter);
		group.addHeaderVariable(columnsGroupVariable);
		return this;
	}	

	/**
	 * @param groupNum the group number (1, 2, 3, ...)
	 * @return
	 * @throws BuilderException
	 */
	private DJGroup getGroupByNumber(int groupNum) throws BuilderException {
		DJGroup group;
		try {
			group = report.getColumnsGroups().get(groupNum-1);
		} catch (IndexOutOfBoundsException e) {
			throw new BuilderException("No such group, use addGroups(int) first");
		}
		return group;
	}

	/**
	 * 
	 * @param colNumber the column number (1, 2, 3,...)
	 * @param op {@link DJCalculation}
	 * @param style OPTIONAL, may be null
	 * @return
	 */
	public FastReportBuilder addGlobalFooterVariable(int colNumber, DJCalculation op, Style style) {
		PropertyColumn column = (PropertyColumn) report.getColumns().get(colNumber -1);

		if (style == null)
			style = numberStyle;

		this.globalVariablesGroup.addFooterVariable(new DJGroupVariable(column, op, style));
		return this;
	}

	/**
	 * 
	 * @param groupNum the group number (1, 2, 3, ...)
	 * @param colNumber the column number (1, 2, 3,...)
	 * @param op {@link DJCalculation}
	 * @param style {@link Style}
	 * @return
	 * @throws BuilderException
	 */
	public FastReportBuilder addFooterVariable(int groupNum, int colNumber, DJCalculation op, Style style) throws BuilderException {
		DJGroup group = getGroupByNumber(groupNum);
		PropertyColumn column = (PropertyColumn) report.getColumns().get(colNumber -1);
		if (style == null)
			style = numberStyle;

		DJGroupVariable columnsGroupVariable = new DJGroupVariable(column, op, style);
		group.addFooterVariable(columnsGroupVariable);
		return this;
	}
	
	/**
	 * 
	 * @param position {@link DJConstants#FOOTER} or {@link DJConstants#HEADER}
	 * @param groupNum the group number (1, 2, 3, ...)
	 * @param colNumber the column number (1, 2, 3,...)
	 * @param op {@link DJCalculation}
	 * @param style {@link Style}
	 * @return
	 * @throws BuilderException
	 */
	public FastReportBuilder addGroupVariable(String position, int groupNum, int colNumber, DJCalculation op, Style style) throws BuilderException {
		if (DJConstants.FOOTER.equals(position)){
			addFooterVariable(groupNum, colNumber, op, style);
		} else {
			addHeaderVariable(groupNum, colNumber, op, style);
		}
		return this;
	}

	/**
	 * 
	 * @param groupNum the group number (1, 2, 3, ...)
	 * @param colNumber the column number (1, 2, 3,...)
	 * @param op {@link DJCalculation}
	 * @param style {@link Style}
	 * @param valueFormatter {@link DJValueFormatter}
	 * @return
	 * @throws BuilderException
	 */
	public FastReportBuilder addFooterVariable(int groupNum, int colNumber, DJCalculation op, Style style, DJValueFormatter valueFormatter) throws BuilderException {
		DJGroup group = getGroupByNumber(groupNum);
		PropertyColumn column = (PropertyColumn) report.getColumns().get(colNumber -1);
		if (style == null)
			style = numberStyle;
		
		DJGroupVariable columnsGroupVariable = new DJGroupVariable(column, op, style, valueFormatter);
		group.addFooterVariable(columnsGroupVariable);
		return this;
	}
	
	/**
	 * 
	 * @param position {@link DJConstants#FOOTER} or {@link DJConstants#HEADER} 
	 * @param groupNum the group number (1, 2, 3, ...)
	 * @param colNumber the column number (1, 2, 3,...)
	 * @param op {@link DJCalculation}
	 * @param style {@link Style}
	 * @param valueFormatter {@link DJValueFormatter}
	 * @return
	 * @throws BuilderException
	 */
	public FastReportBuilder addGroupVariable(String position, int groupNum, int colNumber, DJCalculation op, Style style, DJValueFormatter valueFormatter) throws BuilderException {
		if (DJConstants.FOOTER.equals(position)){
			addFooterVariable(groupNum, colNumber, op, style, valueFormatter);
		} else {
			addHeaderVariable(groupNum, colNumber, op, style, valueFormatter);
		}		
		return this;
	}

	public FastReportBuilder addHeaderCrosstab(int groupNumber, DJCrosstab djcross) throws BuilderException {
		DJGroup group = getGroupByNumber(groupNumber);
		group.getHeaderCrosstabs().add(djcross);
		return this;
	}
	
	public FastReportBuilder addFooterCrosstab(int groupNumber, DJCrosstab djcross) throws BuilderException {
		DJGroup group = getGroupByNumber(groupNumber);
		group.getFooterCrosstabs().add(djcross);
		return this;
	}

	public FastReportBuilder addVariable(String name, DJCalculation calculation,
			CustomExpression expression) {
		DJVariable var = new DJVariable(name,expression.getClassName(),calculation,expression);
		super.addVariable(var);
		return this;
	}
	
	/**
	 * 
	 * @param name
	 * @param calculation
	 * @param expression
	 * @param initialValueExpression
	 * @param resetType
	 * @param resetGroup group number used to reset (from 1 to N)
	 * @return
	 * @throws BuilderException 
	 */
	public FastReportBuilder addVariable(String name, DJCalculation calculation,
			CustomExpression expression, CustomExpression initialValueExpression, DJVariableResetType resetType, int resetGroup) throws BuilderException {
		
		DJVariable var = new DJVariable(name,expression.getClassName(),calculation,expression);
		var.setInitialValueExpression(initialValueExpression);
		var.setResetType(resetType);
		
		if (DJVariableResetType.GROUP.equals(resetType) && (resetGroup < 1 || resetGroup > report.getColumnsGroups().size())){
			throw new BuilderException("Invalid parameter [resetGroup = " + resetGroup +"], must be >= 1 and <= group count ");
		}
		DJGroup group = getGroup(resetGroup -1);
		var.setResetGroup(group);
		
		super.addVariable(var);
		return this;
	}	

}