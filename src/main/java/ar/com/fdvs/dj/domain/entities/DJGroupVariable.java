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

import ar.com.fdvs.dj.core.DJDefaultScriptlet;
import ar.com.fdvs.dj.domain.DJCalculation;
import ar.com.fdvs.dj.domain.DJGroupLabel;
import ar.com.fdvs.dj.domain.DJValueFormatter;
import ar.com.fdvs.dj.domain.Style;
import ar.com.fdvs.dj.domain.entities.columns.AbstractColumn;

/**
 * Entity used to handle global and group variables that represent the value of </br>
 * an operation applied to the corresponding rows.</br>
 * </br>
 * @see DJCalculation
 */
public class DJGroupVariable implements Entity {
	
	private static final Log log = LogFactory.getLog(DJGroupVariable.class);

	private AbstractColumn columnToApplyOperation;
	private DJCalculation operation;
	private Style style;
	private DJValueFormatter valueFormatter;
	
	private DJGroupLabel label;
	
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


	
	public String getTextForValueFormatterExpression(String variableName) {

		String fieldsMap = DJDefaultScriptlet.class.getName() + ".getCurrentFiels()";
		String parametersMap = DJDefaultScriptlet.class.getName() + ".getCurrentParams()";
		String variablesMap = DJDefaultScriptlet.class.getName() + ".getCurrentVariables()";
		
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

}
