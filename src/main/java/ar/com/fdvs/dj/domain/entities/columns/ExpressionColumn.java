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

package ar.com.fdvs.dj.domain.entities.columns;

import java.util.Collection;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import ar.com.fdvs.dj.domain.CustomExpression;
import ar.com.fdvs.dj.domain.DJCalculation;
import ar.com.fdvs.dj.domain.entities.Entity;
import ar.com.fdvs.dj.util.ExpressionUtils;

/**
 * Column created to handle Custom Expressions.</br>
 * @see CustomExpression
 */
public class ExpressionColumn extends SimpleColumn {

	private static final long serialVersionUID = Entity.SERIAL_VERSION_UID;
	
	private static final Log log = LogFactory.getLog(ExpressionColumn.class);

	private CustomExpression expression; //for showing
	private CustomExpression expressionForCalculation; //for calculation

	private Collection columns;
	private Collection variables; //List of JRVariables

	private String calculatedExpressionText = null;

	protected String getCalculatedExpressionText() {
		return calculatedExpressionText;
	}

	protected void setCalculatedExpressionText(String calculatedExpressionText) {
		this.calculatedExpressionText = calculatedExpressionText;
	}

	public Collection getVariables() {
		return variables;
	}

	public void setVariables(Collection variables) {
		this.variables = variables;
	}

	public Collection getColumns() {
		return columns;
	}

	public void setColumns(Collection columns) {
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

		String stringExpression = ExpressionUtils.createCustomExpressionInvocationText(expression, getColumnProperty().getProperty());
		
		log.debug("Expression for CustomExpression = " + stringExpression);

		this.calculatedExpressionText = stringExpression;
		return stringExpression;
	}

	public String getTextForExpressionForCalculartion() {
		
		String stringExpression = ExpressionUtils.createCustomExpressionInvocationText(expressionForCalculation, getColumnProperty().getProperty()+"_calc");
		
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
