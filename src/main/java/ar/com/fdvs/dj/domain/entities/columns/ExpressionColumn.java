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

package ar.com.fdvs.dj.domain.entities.columns;

import ar.com.fdvs.dj.domain.ColumnProperty;
import ar.com.fdvs.dj.domain.CustomExpression;
import ar.com.fdvs.dj.domain.DJCalculation;
import ar.com.fdvs.dj.domain.entities.Entity;
import ar.com.fdvs.dj.util.ExpressionUtils;
import net.sf.jasperreports.engine.JRVariable;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Collection;

/**
 * Column created to handle Custom Expressions.</br>
 * @see CustomExpression
 */
public class ExpressionColumn extends SimpleColumn {

	private static final long serialVersionUID = Entity.SERIAL_VERSION_UID;
	
	private static final Log log = LogFactory.getLog(ExpressionColumn.class);

	private CustomExpression expression; //for showing
	private CustomExpression expressionForCalculation; //for calculation

	private Collection<ColumnProperty> columns;
	private Collection<JRVariable> variables; //List of JRVariables

	private String calculatedExpressionText = null;

	protected String getCalculatedExpressionText() {
		return calculatedExpressionText;
	}

	protected void setCalculatedExpressionText(String calculatedExpressionText) {
		this.calculatedExpressionText = calculatedExpressionText;
	}

	public Collection<JRVariable> getVariables() {
		return variables;
	}

	public void setVariables(Collection<JRVariable> variables) {
		this.variables = variables;
	}

	public Collection<ColumnProperty> getColumns() {
		return columns;
	}

	public void setColumns(Collection<ColumnProperty> columns) {
		this.columns = columns;
	}

	public CustomExpression getExpression() {
		return expression;
	}

	public void setExpression(CustomExpression expression) {
		this.expression = expression;
	}

	public String getValueClassNameForExpression() {
		if (expression == null)
			return Object.class.getName();

		return expression.getClassName();
	}

	public String getTextForExpression() {

		if (this.calculatedExpressionText != null)
			return this.calculatedExpressionText;

		String stringExpression = ExpressionUtils.createCustomExpressionInvocationText(expression, getColumnProperty().getProperty(), false);
		
		log.debug("Expression for CustomExpression = " + stringExpression);

		this.calculatedExpressionText = stringExpression;
		return stringExpression;
	}

	public String getTextForExpressionForCalculartion() {
		
		String stringExpression = ExpressionUtils.createCustomExpressionInvocationText(expressionForCalculation, getColumnProperty().getProperty()+"_calc", false);
		
		log.debug("Calculation Expression for CustomExpression = " + stringExpression);
		
		return stringExpression;
	}

	public CustomExpression getExpressionForCalculation() {
		return expressionForCalculation;
	}

	public void setExpressionForCalculation(
			CustomExpression expressionForCalculation) {
		this.expressionForCalculation = expressionForCalculation;
	}
	
	public String getVariableClassName(DJCalculation op) {
		if (op == DJCalculation.COUNT || op == DJCalculation.DISTINCT_COUNT)
			return Long.class.getName();
		
		if (expressionForCalculation != null)
			return expressionForCalculation.getClassName();
		
		if (expression != null)
			return expression.getClassName();
		
		return super.getVariableClassName(op);
			
	}	
	
	public String getInitialExpression(DJCalculation op) {
		if (op == DJCalculation.COUNT  || op == DJCalculation.DISTINCT_COUNT)
			return "new java.lang.Long(\"0\")";
		else if (op == DJCalculation.SUM) {
			
			return "new " + getVariableClassName(op) +"(\"0\")";
		}
		else return null;
	}
	

}
