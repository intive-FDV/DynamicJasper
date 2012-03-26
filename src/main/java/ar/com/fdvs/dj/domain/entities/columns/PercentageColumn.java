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

import ar.com.fdvs.dj.core.CoreException;
import ar.com.fdvs.dj.domain.DJCalculation;
import ar.com.fdvs.dj.domain.entities.DJGroup;
import ar.com.fdvs.dj.domain.entities.Entity;

/**
 * This column shows a percentage relative to another column.
 * 
 * @author mamana and Ricardo Mariaca
 *
 */
public class PercentageColumn extends AbstractColumn {
	private static final long serialVersionUID = Entity.SERIAL_VERSION_UID;
	private PropertyColumn percentageColumn;
//	private DJGroup group;

	public String getTextForExpression() {
		throw new CoreException("invalid operation on PercentageColumn");
//		return "new Double((" + getPercentageColumn().getTextForExpression() + ").doubleValue() / $V{"	+ getGroupVariableName() + "}.doubleValue())";
	}
	
	public String getTextForExpression(DJGroup group) {
		return "new Double((" + getPercentageColumn().getTextForExpression() + ").doubleValue() / $V{"	+ getGroupVariableName(group) + "}.doubleValue())";
	}	

	/**
	 * Returns the formula for the percentage
	 * @param group
	 * @param type
	 * @return
	 */
	public String getTextForExpression(DJGroup group, DJGroup childGroup, String type) {
		return "new Double( $V{" + getGroupVariableName(childGroup) + "}.doubleValue() / $V{"	+ getGroupVariableName(type,group.getColumnToGroupBy().getColumnProperty().getProperty()) + "}.doubleValue())";
	}	

	public String getValueClassNameForExpression() {
		return Number.class.getName();
	}

	public String getGroupVariableName(String type, String columnToGroupByProperty) {
		return "variable-" + type + "_" + columnToGroupByProperty + "_" + getPercentageColumn().getColumnProperty().getProperty() + "_percentage";
	}

	public String getVariableClassName(DJCalculation op) {
		if (op == DJCalculation.COUNT || op == DJCalculation.DISTINCT_COUNT)
			return Long.class.getName();
		else
			return Number.class.getName();
	}

	public String getInitialExpression(DJCalculation op) {
		return "new java.lang.Long(\"0\")";
	}

	/**
	 * The group which the variable will be inside (mostly for reset)
	 * @param group (may be null)
	 * @return
	 */
	public String getGroupVariableName(DJGroup group) {
		String columnToGroupByProperty = null;
		//if (group != null)
			columnToGroupByProperty = group.getColumnToGroupBy().getColumnProperty().getProperty();
		//else
			//columnToGroupByProperty = "global";
		return "variable-" + columnToGroupByProperty + "_" + getPercentageColumn().getColumnProperty().getProperty() + "_percentage";
	}
	
	public void setPercentageColumn(PropertyColumn percentageColumn) {
		this.percentageColumn = percentageColumn;
	}

	public PropertyColumn getPercentageColumn() {
		return percentageColumn;
	}

//	public void setGroup(DJGroup group) {
//		this.group = group;
//	}
//
//	public DJGroup getGroup() {
//		return group;
//	}
}
