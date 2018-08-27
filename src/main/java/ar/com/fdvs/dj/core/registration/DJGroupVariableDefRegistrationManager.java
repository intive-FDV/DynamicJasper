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

import ar.com.fdvs.dj.core.layout.LayoutManager;
import ar.com.fdvs.dj.domain.ColumnProperty;
import ar.com.fdvs.dj.domain.DJCalculation;
import ar.com.fdvs.dj.domain.DynamicJasperDesign;
import ar.com.fdvs.dj.domain.DynamicReport;
import ar.com.fdvs.dj.domain.entities.DJGroup;
import ar.com.fdvs.dj.domain.entities.DJGroupVariableDef;
import ar.com.fdvs.dj.domain.entities.Entity;
import ar.com.fdvs.dj.domain.entities.columns.AbstractColumn;
import ar.com.fdvs.dj.domain.entities.columns.ExpressionColumn;
import ar.com.fdvs.dj.util.ExpressionUtils;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.design.JRDesignExpression;
import net.sf.jasperreports.engine.design.JRDesignField;
import net.sf.jasperreports.engine.design.JRDesignGroup;
import net.sf.jasperreports.engine.design.JRDesignVariable;
import net.sf.jasperreports.engine.type.CalculationEnum;
import net.sf.jasperreports.engine.type.ResetTypeEnum;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Manager invoked to register temporal variables for groups of columns. <br>
 * A ColumnsGroupVariable is read and transformed into a JRDesignVariable. <br>
 * <br>
 * @see DJGroup
 */
public class DJGroupVariableDefRegistrationManager extends AbstractEntityRegistrationManager {

	private static final Log log = LogFactory.getLog(DJGroupVariableDefRegistrationManager.class);
	JRDesignGroup group = null;
	
	public DJGroupVariableDefRegistrationManager(DynamicJasperDesign jd,  DynamicReport dr, LayoutManager layoutManager, JRDesignGroup group) {
		super(jd,dr,layoutManager);
		this.group = group;
	}
	
	protected void registerEntity(Entity entity) {
		try {
            DJGroupVariableDef columnsGroupVariable = (DJGroupVariableDef) entity;
            DJCalculation op = columnsGroupVariable.getOperation();

            columnsGroupVariable.setName(this.getDjd().getName() + "_" + columnsGroupVariable.getName());

            log.debug("Registering group variable " + columnsGroupVariable.getName());


            JRDesignVariable jrVariable = (JRDesignVariable)transformEntity(entity);
			getDjd().addVariable(jrVariable);			
		} catch (JRException e) {
			throw new EntitiesRegistrationException(e.getMessage(),e);
		}
	}

	protected Object transformEntity(Entity entity) {

		DJGroupVariableDef columnsGroupVariable = (DJGroupVariableDef) entity;
		DJCalculation op = columnsGroupVariable.getOperation();

		JRDesignExpression expression = new JRDesignExpression();
		
		String valueClassName;
		String initialExpression;

		if (columnsGroupVariable.getColumnProperty() != null) { //A variable that operates over a FIELD
			ColumnProperty prop = columnsGroupVariable.getColumnProperty();
			
			expression.setText("$F{" + prop.getProperty() + "}");
			expression.setValueClassName(prop.getValueClassName());
			registerField(prop);
			
			valueClassName = ExpressionUtils.getValueClassNameForOperation(op, prop);
			initialExpression = ExpressionUtils.getInitialValueExpressionForOperation(op,prop);
			
		} else {
			AbstractColumn col = columnsGroupVariable.getColumnToApplyOperation();
			
			if (col instanceof ExpressionColumn && ((ExpressionColumn)col).getExpressionForCalculation() != null){
				ExpressionColumn expcol = (ExpressionColumn)col;
				expression.setText(expcol.getTextForExpressionForCalculartion());
				expression.setValueClassName(expcol.getExpressionForCalculation().getClassName());
			} 
			else {
				expression.setText(col.getTextForExpression());
				expression.setValueClassName(col.getValueClassNameForExpression());
			}

			valueClassName = col.getVariableClassName(op);
			initialExpression = col.getInitialExpression(op);
		}

		JRDesignVariable variable = new JRDesignVariable();
		variable.setExpression(expression);
		variable.setCalculation(CalculationEnum.getByValue( columnsGroupVariable.getOperation().getValue() ));
		variable.setName(columnsGroupVariable.getName());

        log.debug("Transforming group variable " + variable.getName());

		if (group != null) {
			variable.setResetType( ResetTypeEnum.GROUP );
			variable.setResetGroup(group);
		}


		variable.setValueClassName(valueClassName);

		JRDesignExpression initialExp = new JRDesignExpression();
		initialExp.setText(initialExpression);
		initialExp.setValueClassName(valueClassName);
		variable.setInitialValueExpression(initialExp);

		return variable;
	}

	protected void registerField(ColumnProperty columnProperty) {
		JRDesignField field = new JRDesignField();
		field.setName(columnProperty.getProperty());
		field.setValueClassName(columnProperty.getValueClassName());
		
		log.debug("transforming column property for group variable: " + columnProperty.getProperty() + " (" + columnProperty.getValueClassName() +")");

		for (String key : columnProperty.getFieldProperties().keySet()) {
			field.getPropertiesMap().setProperty(key, columnProperty.getFieldProperties().get(key));
		}
		try {
			if (getDjd().getFieldsMap().get(field.getName())==null){
				getDjd().addField(field);
			}
		} catch (JRException e) {
			log.info("The field has already been registered" + ": " + e.getMessage() + ", (skipping)");
		}
		
	}

}
