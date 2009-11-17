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

package ar.com.fdvs.dj.domain.entities;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import ar.com.fdvs.dj.domain.BooleanExpression;
import ar.com.fdvs.dj.domain.CustomExpression;
import ar.com.fdvs.dj.domain.DJBaseElement;
import ar.com.fdvs.dj.domain.DJCalculation;
import ar.com.fdvs.dj.domain.DJGroupLabel;
import ar.com.fdvs.dj.domain.DJValueFormatter;
import ar.com.fdvs.dj.domain.Style;
import ar.com.fdvs.dj.domain.entities.columns.AbstractColumn;
import ar.com.fdvs.dj.util.ExpressionUtils;

/**
 * Entity used to handle global and group variables that represent the value of </br>
 * an operation applied to the corresponding rows.</br>
 * DJGroupVariable(s) are shown at the header or footer of the column they operate on.
 * </br>
 * @see DJCalculation
 */
public class DJGroupVariable extends DJBaseElement {
	
	private static final long serialVersionUID = Entity.SERIAL_VERSION_UID;
	
	private static final Log log = LogFactory.getLog(DJGroupVariable.class);

	private AbstractColumn columnToApplyOperation;
	private DJCalculation operation;
	private Style style;
	private DJValueFormatter valueFormatter;
	private BooleanExpression printWhenExpression;
	private CustomExpression valueExpression;
	
	private DJGroupLabel label;

	/**
	 * for internal use.
	 */
	private DJGroup group;
	
	public DJGroupVariable(AbstractColumn columnToApplyOperation, DJCalculation operation) {
		this.columnToApplyOperation = columnToApplyOperation;
		this.operation = operation;
	}

	public DJGroupVariable(AbstractColumn columnToApplyOperation, DJCalculation operation, Style style) {
		this.columnToApplyOperation = columnToApplyOperation;
		this.operation = operation;
		this.style = style;
	}

	public DJGroupVariable(AbstractColumn columnToApplyOperation, DJCalculation operation, Style style, DJValueFormatter formatter) {
		this.columnToApplyOperation = columnToApplyOperation;
		this.operation = operation;
		this.style = style;
		this.valueFormatter = formatter;
	}

	public DJGroupVariable(AbstractColumn columnToApplyOperation, CustomExpression valueExpression) {
		this.columnToApplyOperation = columnToApplyOperation;
		this.valueExpression = valueExpression;
	}

	public DJGroupVariable(AbstractColumn columnToApplyOperation, CustomExpression valueExpression, Style style) {
		this.columnToApplyOperation = columnToApplyOperation;
		this.valueExpression = valueExpression;
		this.style = style;
	}

	
	public String getTextForValueFormatterExpression(String variableName) {
		
		String fieldsMap = ExpressionUtils.getTextForFieldsFromScriptlet();
		String parametersMap = ExpressionUtils.getTextForParametersFromScriptlet();
		String variablesMap = ExpressionUtils.getTextForVariablesFromScriptlet();		
		
		String stringExpression = "((("+DJValueFormatter.class.getName()+")$P{"+variableName+"_vf}).evaluate( "
			+ "$V{"+variableName+"}, " + fieldsMap +", " + variablesMap + ", " + parametersMap +" ))";

		log.debug("Expression for DJValueFormatter = " + stringExpression);

		return stringExpression;
	}	

	public Style getStyle() {
		return style;
	}

	public void setStyle(Style style) {
		this.style = style;
	}


	public AbstractColumn getColumnToApplyOperation() {
		return columnToApplyOperation;
	}

	public void setColumnToApplyOperation(AbstractColumn columnToApplyOperation) {
		this.columnToApplyOperation = columnToApplyOperation;
	}

	public DJCalculation getOperation() {
		return operation;
	}

	public void setOperation(DJCalculation operation) {
		this.operation = operation;
	}

	public DJValueFormatter getValueFormatter() {
		return valueFormatter;
	}

	public void setValueFormatter(DJValueFormatter valueFormatter) {
		this.valueFormatter = valueFormatter;
	}

	public DJGroupLabel getLabel() {
		return label;
	}

	public void setLabel(DJGroupLabel label) {
		this.label = label;
	}

	public static Log getLog() {
		return log;
	}

	public DJGroupVariable(AbstractColumn columnToApplyOperation,
			DJCalculation operation, Style style,
			DJValueFormatter valueFormatter, DJGroupLabel label) {
		super();
		this.columnToApplyOperation = columnToApplyOperation;
		this.operation = operation;
		this.style = style;
		this.valueFormatter = valueFormatter;
		this.label = label;
	}

	public void setPrintWhenExpression(BooleanExpression printWhenExpression) {
		this.printWhenExpression = printWhenExpression;
	}

	public BooleanExpression getPrintWhenExpression() {
		return printWhenExpression;
	}

	public void setValueExpression(CustomExpression valueExpression) {
		this.valueExpression = valueExpression;
	}

	public CustomExpression getValueExpression() {
		return valueExpression;
	}

	public void setGroup(DJGroup djGroup) {
		this.group = djGroup;
	}

	public DJGroup getGroup() {
		return group;
	}

}
