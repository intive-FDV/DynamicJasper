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

import java.util.Iterator;
import java.util.List;

import ar.com.fdvs.dj.domain.ColumnOperation;
import ar.com.fdvs.dj.domain.DJCalculation;
import ar.com.fdvs.dj.domain.entities.Entity;

/**
 * Column created to handle numerical operations between two or more </br>
 * PropertyColumns.</br>
 * </br>
 * @see ColumnOperation
 * @see PropertyColumn
 */
public class OperationColumn extends AbstractColumn {

	private static final long serialVersionUID = Entity.SERIAL_VERSION_UID;
	
	private ColumnOperation columnOperation;
	private List columns;

	public ColumnOperation getColumnOperation() {
		return columnOperation;
	}

	public void setColumnOperation(ColumnOperation columnOperation) {
		this.columnOperation = columnOperation;
	}

	public List getColumns() {
		return columns;
	}

	public void setColumns(List columns) {
		this.columns = columns;
	}

	public String getTextForExpression() {
		StringBuffer exp = new StringBuffer();
		for (Iterator iter = columns.iterator(); iter
				.hasNext();) {
			SimpleColumn col = (SimpleColumn) iter.next();
			exp.append(" ((java.lang.Number)$F{" + col.getColumnProperty().getProperty()
					+ "}).doubleValue() ");
			if (iter.hasNext())
				exp.append(columnOperation.getValue());
		}
		return "new java.lang.Double(" + exp.toString() + ")";
	}

	public String getValueClassNameForExpression() {
		return "java.lang.Number";
	}

	public String getGroupVariableName(String type, String columnToGroupByProperty) {
		return "variable-"+type+"_"+columnToGroupByProperty+"_"+columnOperation.getValue();
	}

	public String getVariableClassName(DJCalculation op) {
		if (op == DJCalculation.COUNT || op == DJCalculation.DISTINCT_COUNT )
			return Long.class.getName();
		else return Number.class.getName();
	}

	public String getInitialExpression(DJCalculation op) {
		return "new java.lang.Long(\"0\")";
	}

}
