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

package ar.com.fdvs.dj.core.registration;

import java.util.List;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.design.JRDesignExpression;
import net.sf.jasperreports.engine.design.JRDesignGroup;
import net.sf.jasperreports.engine.design.JRDesignParameter;
import net.sf.jasperreports.engine.design.JRDesignVariable;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import ar.com.fdvs.dj.core.layout.LayoutManager;
import ar.com.fdvs.dj.domain.DJCalculation;
import ar.com.fdvs.dj.domain.DJValueFormatter;
import ar.com.fdvs.dj.domain.DynamicJasperDesign;
import ar.com.fdvs.dj.domain.DynamicReport;
import ar.com.fdvs.dj.domain.entities.DJGroup;
import ar.com.fdvs.dj.domain.entities.DJGroupVariable;
import ar.com.fdvs.dj.domain.entities.Entity;
import ar.com.fdvs.dj.domain.entities.columns.AbstractColumn;
import ar.com.fdvs.dj.domain.entities.columns.ExpressionColumn;
import ar.com.fdvs.dj.domain.entities.columns.PercentageColumn;
import ar.com.fdvs.dj.domain.entities.columns.PropertyColumn;
import ar.com.fdvs.dj.util.LayoutUtils;

/**
 * Manager invoked to register variables for groups of columns. </br>
 * A ColumnsGroupVariable is read and transformed into a JRDesignVariable. </br>
 * </br>
 * @see DJGroup
 */
public class ColumnsGroupVariablesRegistrationManager extends AbstractEntityRegistrationManager {

	private static final Log log = LogFactory.getLog(ColumnsGroupVariablesRegistrationManager.class);

	private String type;
	private String columnToGroupByProperty;

	public ColumnsGroupVariablesRegistrationManager(String type, String columnToGroupByProperty, DynamicJasperDesign jd,  DynamicReport dr, LayoutManager layoutManager) {
		super(jd,dr,layoutManager);
		this.type = type;
		this.columnToGroupByProperty = columnToGroupByProperty;
	}

	protected void registerEntity(Entity entity) {
		DJGroupVariable columnsGroupVariable = (DJGroupVariable) entity;
		try {
			String name = columnsGroupVariable.getColumnToApplyOperation().getGroupVariableName(type, columnToGroupByProperty);
			if (columnsGroupVariable.getValueExpression() == null) {			
				JRDesignVariable jrVariable = (JRDesignVariable)transformEntity(entity);
				
				log.debug("registering group variable " + jrVariable.getName() + " (" + jrVariable.getValueClassName() + ")");
				getDjd().addVariable(jrVariable);
			
				registerValueFormatter( columnsGroupVariable, jrVariable.getName() );
			} 
			else {
				registerCustomExpressionParameter(name + "_valueExpression", columnsGroupVariable.getValueExpression());
			}
			
			if (columnsGroupVariable.getPrintWhenExpression() != null){
				registerCustomExpressionParameter(name + "_printWhenExpression", columnsGroupVariable.getPrintWhenExpression());	
			}
			if (columnsGroupVariable.getLabel() != null && columnsGroupVariable.getLabel().getLabelExpression() != null) {
				registerCustomExpressionParameter(name + "_labelExpression", columnsGroupVariable.getLabel().getLabelExpression());
			}
			
		} catch (JRException e) {
			throw new EntitiesRegistrationException(e.getMessage(),e);
		}
	}

	/**
	 * Registers the parameter for the value formatter for the given variable and puts
	 * it's implementation in the parameters map.
	 * @param djVariable
	 * @param variableName
	 */
	protected void registerValueFormatter(DJGroupVariable djVariable, String variableName) {
		if ( djVariable.getValueFormatter() == null){
			return;
		}
		
		JRDesignParameter dparam = new JRDesignParameter();
		dparam.setName(variableName + "_vf"); //value formater suffix
		dparam.setValueClassName(DJValueFormatter.class.getName());
		log.debug("Registering value formatter parameter for property " + dparam.getName() );
		try {
			getDjd().addParameter(dparam);
		} catch (JRException e) {
			throw new EntitiesRegistrationException(e.getMessage(),e);
		}
		getDjd().getParametersWithValues().put(dparam.getName(), djVariable.getValueFormatter());		
		
	}

	protected Object transformEntity(Entity entity) {
		log.debug("transforming group variable...");
		DJGroupVariable groupVariable = (DJGroupVariable) entity;
		AbstractColumn col = groupVariable.getColumnToApplyOperation();
		DJCalculation op = groupVariable.getOperation();

		JRDesignExpression expression = new JRDesignExpression();

		//only variables from the last registered group are important now
		List groupsList = getDjd().getGroupsList();
		JRDesignGroup registeredGroup = (JRDesignGroup)groupsList.get(groupsList.size()-1);
		
		String variableName = col.getGroupVariableName(type, columnToGroupByProperty);

		if (col instanceof ExpressionColumn && ((ExpressionColumn)col).getExpressionForCalculation() != null){
			ExpressionColumn expcol = (ExpressionColumn)col;
			expression.setText(expcol.getTextForExpressionForCalculartion());
			expression.setValueClassName(expcol.getExpressionForCalculation().getClassName());
		} 
		else if (col instanceof PercentageColumn) {
			PercentageColumn pcol = (PercentageColumn) col;
			expression.setText(pcol.getPercentageColumn().getTextForExpression());
			expression.setValueClassName(pcol.getPercentageColumn().getValueClassNameForExpression());
			
			DJGroup djgroup = groupVariable.getGroup();
			registeredGroup = LayoutUtils.findParentJRGroup(djgroup, getDynamicReport(), getDjd(), getLayoutManager());
		}
		else {
			if (col.getTextFormatter() != null){
				PropertyColumn pcol = (PropertyColumn) col; 
				expression.setText("$F{" + pcol.getColumnProperty().getProperty() + "}");
				expression.setValueClassName(pcol.getColumnProperty().getValueClassName());
			} else {
				expression.setText(col.getTextForExpression());
				expression.setValueClassName(col.getValueClassNameForExpression());
			}
		}
		
		JRDesignVariable variable = new JRDesignVariable();
		variable.setExpression(expression);
		variable.setCalculation(groupVariable.getOperation().getValue());
		variable.setName(variableName);		

		variable.setResetType(JRDesignVariable.RESET_TYPE_GROUP);
		variable.setResetGroup(registeredGroup);

		String valueClassName = col.getVariableClassName(op);
		String initialExpression = col.getInitialExpression(op);

//		if (DJCalculation.SYSTEM.equals(groupVariable.getOperation())){
//			variable.setValueClassName(Object.class.getName());
//		} else {
//		}
		variable.setValueClassName(valueClassName);
				
		if (initialExpression != null){
			JRDesignExpression initialExp = new JRDesignExpression();
			initialExp.setText(initialExpression);
			initialExp.setValueClassName(valueClassName);
			variable.setInitialValueExpression(initialExp);
		}

		return variable;
	}

}
