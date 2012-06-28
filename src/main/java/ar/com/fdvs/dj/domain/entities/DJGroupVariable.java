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
     * for Internal use only
     */
    private String name;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
