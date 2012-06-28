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

package ar.com.fdvs.dj.domain.chart.dataset;

import java.util.List;
import java.util.Map;

import net.sf.jasperreports.engine.design.JRDesignChartDataset;
import net.sf.jasperreports.engine.design.JRDesignExpression;
import net.sf.jasperreports.engine.design.JRDesignGroup;
import net.sf.jasperreports.engine.design.JRDesignVariable;
import ar.com.fdvs.dj.domain.DJBaseElement;
import ar.com.fdvs.dj.domain.DynamicJasperDesign;
import ar.com.fdvs.dj.domain.entities.Entity;
import ar.com.fdvs.dj.domain.entities.columns.AbstractColumn;
import ar.com.fdvs.dj.domain.entities.columns.PropertyColumn;

public abstract class AbstractDataset extends DJBaseElement {
	private static final long serialVersionUID = Entity.SERIAL_VERSION_UID;
	/**
	 * Generates an expression from a variable
	 * @param var The variable from which to generate the expression
	 * @return A expression that represents the given variable
	 */
	protected static JRDesignExpression getExpressionFromVariable(JRDesignVariable var){
		JRDesignExpression exp = new JRDesignExpression();
		exp.setText("$V{" + var.getName() + "}");
		exp.setValueClass(var.getValueClass());
		return exp;
	}
	
	protected static void setResetStyle(JRDesignChartDataset dataset, JRDesignGroup group, JRDesignGroup parentGroup){
		//When to start a new chart? When the group's parent changes
		dataset.setResetGroup(parentGroup);
		dataset.setIncrementType(JRDesignVariable.RESET_TYPE_GROUP);
		dataset.setIncrementGroup(group);
		if (dataset.getResetGroup().equals(group))
			dataset.setResetType(JRDesignVariable.RESET_TYPE_REPORT);
		else
			dataset.setResetType(JRDesignVariable.RESET_TYPE_GROUP);		
	}
	
	public abstract PropertyColumn getColumnsGroup();
	
	public abstract List getColumns();
	
	public abstract JRDesignChartDataset transform(DynamicJasperDesign design, String name, JRDesignGroup group, JRDesignGroup parentGroup, Map vars);
	
	public abstract void addSerie(AbstractColumn column);
}
