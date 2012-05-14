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

    /**
     * It can be "header" o "footer"
     */
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
            String name = this.getDjd().getName() +"_" + columnsGroupVariable.getColumnToApplyOperation().getGroupVariableName(type, columnToGroupByProperty);
            columnsGroupVariable.setName(name);
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

		DJGroupVariable groupVariable = (DJGroupVariable) entity;
		AbstractColumn col = groupVariable.getColumnToApplyOperation();

        String variableName =  groupVariable.getName();
        log.debug("transforming group variable " +variableName);
        DJCalculation op = groupVariable.getOperation();

		JRDesignExpression expression = new JRDesignExpression();

		//only variables from the last registered group are important now
		List groupsList = getDjd().getGroupsList();
		JRDesignGroup registeredGroup = (JRDesignGroup)groupsList.get(groupsList.size()-1);

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
