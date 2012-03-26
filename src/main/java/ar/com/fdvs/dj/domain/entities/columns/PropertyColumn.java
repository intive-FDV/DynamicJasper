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

import ar.com.fdvs.dj.domain.ColumnProperty;
import ar.com.fdvs.dj.domain.CustomExpression;
import ar.com.fdvs.dj.domain.DJCalculation;
import ar.com.fdvs.dj.domain.entities.Entity;

/**
 * Basic abstract column type representing a property from the obtained </br>
 * result set. Only subclasses of this class can be grouped.
 */
public abstract class PropertyColumn extends AbstractColumn {

	private static final long serialVersionUID = Entity.SERIAL_VERSION_UID;
	
	private ColumnProperty columnProperty;
	private CustomExpression expressionToGroupBy;

	/**
	 * This parameter goes to JRField.description, needed when using XML dataSources
	 */
	private String fieldDescription;
	/**
	 * Field properties are passed directly to JasperReports JRField
	 */

	public String getFieldDescription() {
		return fieldDescription;
	}

	public void setFieldDescription(String fieldDescription) {
		this.fieldDescription = fieldDescription;
	}

	public ColumnProperty getColumnProperty() {
		return columnProperty;
	}

	public void setColumnProperty(ColumnProperty columnProperty) {
		this.columnProperty = columnProperty;
	}

	public CustomExpression getExpressionToGroupBy() {
		return expressionToGroupBy;
	}

	public void setExpressionToGroupBy(CustomExpression expressionToGroupBy) {
		this.expressionToGroupBy = expressionToGroupBy;
	}

	public String getGroupVariableName(String type, String columnToGroupByProperty) {
		return "variable-"+type+"_"+columnToGroupByProperty+"_"+getColumnProperty().getProperty();
	}

	public String getVariableClassName(DJCalculation op) {
		if (op == DJCalculation.COUNT || op == DJCalculation.DISTINCT_COUNT)
			return Long.class.getName();
		else
			return getColumnProperty().getValueClassName();
	}

	public String getInitialExpression(DJCalculation op) {
		if (op == DJCalculation.COUNT  || op == DJCalculation.DISTINCT_COUNT)
			return "new java.lang.Long(\"0\")";
		else if (op == DJCalculation.SUM)
			return "new " + getColumnProperty().getValueClassName()+"(\"0\")";
		else return null;
	}

}
