/*
 * Dynamic Jasper: A library for creating reports dynamically by specifying
 * columns, groups, styles, etc. at runtime. It also saves a lot of development
 * time in many cases! (http://sourceforge.net/projects/dynamicjasper)
 *
 * Copyright (C) 2007  FDV Solutions (http://www.fdvsolutions.com)
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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.design.JRDesignExpression;
import net.sf.jasperreports.engine.design.JRDesignGroup;
import net.sf.jasperreports.engine.design.JRDesignVariable;
import ar.com.fdvs.dj.core.registration.AbstractEntityRegistrationManager;
import ar.com.fdvs.dj.core.registration.EntitiesRegistrationException;
import ar.com.fdvs.dj.domain.ColumnsGroupVariableOperation;
import ar.com.fdvs.dj.domain.DynamicJasperDesign;
import ar.com.fdvs.dj.domain.entities.ColumnsGroup;
import ar.com.fdvs.dj.domain.entities.ColumnsGroupVariable;
import ar.com.fdvs.dj.domain.entities.Entity;
import ar.com.fdvs.dj.domain.entities.columns.AbstractColumn;

/**
 * Manager invoked to register variables for groups of columns. </br>
 * A ColumnsGroupVariable is read and transformed into a JRDesignVariable. </br>
 * </br>
 * @see ColumnsGroup
 */
public class ColumnsGroupVariablesRegistrationManager extends AbstractEntityRegistrationManager {

	private static final Log log = LogFactory.getLog(ColumnsGroupVariablesRegistrationManager.class);

	public static final String HEADER = "header";
	public static final String FOOTER = "footer";

	private String type;
	private String columnToGroupByProperty;

	public ColumnsGroupVariablesRegistrationManager(String type, String columnToGroupByProperty, DynamicJasperDesign jd) {
		super(jd);
		this.type = type;
		this.columnToGroupByProperty = columnToGroupByProperty;
	}

	protected void registerEntity(Entity entity) {
		log.debug("registering group variable...");
		try {
			getDjd().addVariable((JRDesignVariable)transformEntity(entity));
		} catch (JRException e) {
			throw new EntitiesRegistrationException(e.getMessage());
		}
	}

	protected Object transformEntity(Entity entity) {
		log.debug("transforming group variable...");
		ColumnsGroupVariable columnsGroupVariable = (ColumnsGroupVariable) entity;
		AbstractColumn col = columnsGroupVariable.getColumnToApplyOperation();
		ColumnsGroupVariableOperation op = columnsGroupVariable.getOperation();

		JRDesignExpression expression = new JRDesignExpression();

		//only variables from the last registered group are important now
		List groupsList = getDjd().getGroupsList();
		JRDesignGroup registeredGroup = (JRDesignGroup)groupsList.get(groupsList.size()-1);

		expression.setText(col.getTextForExpression());
		expression.setValueClassName(col.getValueClassNameForExpression());
		String variableName = col.getGroupVariableName(type, columnToGroupByProperty);

		JRDesignVariable variable = new JRDesignVariable();
		variable.setExpression(expression);
		variable.setCalculation(columnsGroupVariable.getOperation().getValue());
		variable.setName(variableName);
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
