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
import net.sf.jasperreports.engine.design.JRDesignVariable;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import ar.com.fdvs.dj.core.layout.LayoutManager;
import ar.com.fdvs.dj.domain.DJCalculation;
import ar.com.fdvs.dj.domain.DynamicJasperDesign;
import ar.com.fdvs.dj.domain.DynamicReport;
import ar.com.fdvs.dj.domain.entities.DJGroup;
import ar.com.fdvs.dj.domain.entities.DJGroupTemporalVariable;
import ar.com.fdvs.dj.domain.entities.Entity;
import ar.com.fdvs.dj.domain.entities.columns.AbstractColumn;
import ar.com.fdvs.dj.domain.entities.columns.ExpressionColumn;

/**
 * Manager invoked to register temporal variables for groups of columns. </br>
 * A ColumnsGroupVariable is read and transformed into a JRDesignVariable. </br>
 * </br>
 * @see DJGroup
 */
public class ColumnsGroupTemporalVariablesRegistrationManager extends AbstractEntityRegistrationManager {

	private static final Log log = LogFactory.getLog(ColumnsGroupTemporalVariablesRegistrationManager.class);

	public ColumnsGroupTemporalVariablesRegistrationManager(DynamicJasperDesign jd,  DynamicReport dr, LayoutManager layoutManager) {
		super(jd,dr,layoutManager);
	}

	protected void registerEntity(Entity entity) {
		log.debug("registering group variable...");
		try {
			JRDesignVariable jrVariable = (JRDesignVariable)transformEntity(entity);
			getDjd().addVariable(jrVariable);			
		} catch (JRException e) {
			throw new EntitiesRegistrationException(e.getMessage());
		}
	}

	protected Object transformEntity(Entity entity) {
		log.debug("transforming group variable...");
		DJGroupTemporalVariable columnsGroupVariable = (DJGroupTemporalVariable) entity;
		AbstractColumn col = columnsGroupVariable.getColumnToApplyOperation();
		DJCalculation op = columnsGroupVariable.getOperation();

		JRDesignExpression expression = new JRDesignExpression();

		//only variables from the last registered group are important now
		List groupsList = getDjd().getGroupsList();
		JRDesignGroup registeredGroup = (JRDesignGroup)groupsList.get(groupsList.size()-1);

		if (col instanceof ExpressionColumn && ((ExpressionColumn)col).getExpressionForCalculation() != null){
			ExpressionColumn expcol = (ExpressionColumn)col;
			expression.setText(expcol.getTextForExpressionForCalculartion());
			expression.setValueClassName(expcol.getExpressionForCalculation().getClassName());
		} else {
			expression.setText(col.getTextForExpression());
			expression.setValueClassName(col.getValueClassNameForExpression());
		}

		JRDesignVariable variable = new JRDesignVariable();
		variable.setExpression(expression);
		variable.setCalculation(columnsGroupVariable.getOperation().getValue());
		variable.setName(columnsGroupVariable.getName());

		variable.setResetType(JRDesignVariable.RESET_TYPE_GROUP);
		variable.setResetGroup(registeredGroup);

		String valueClassName = col.getVariableClassName(op);
		String initialExpression = col.getInitialExpression(op);

		variable.setValueClassName(valueClassName);

		JRDesignExpression initialExp = new JRDesignExpression();
		initialExp.setText(initialExpression);
		initialExp.setValueClassName(valueClassName);
		variable.setInitialValueExpression(initialExp);

		return variable;
	}

}
