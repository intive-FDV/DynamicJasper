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

import java.util.Iterator;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.design.JRDesignExpression;
import net.sf.jasperreports.engine.design.JRDesignField;
import net.sf.jasperreports.engine.design.JRDesignGroup;
import net.sf.jasperreports.engine.design.JRDesignVariable;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

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

/**
 * Manager invoked to register temporal variables for groups of columns. </br>
 * A ColumnsGroupVariable is read and transformed into a JRDesignVariable. </br>
 * </br>
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
		log.debug("registering group variable...");
		try {
			JRDesignVariable jrVariable = (JRDesignVariable)transformEntity(entity);
			getDjd().addVariable(jrVariable);			
		} catch (JRException e) {
			throw new EntitiesRegistrationException(e.getMessage(),e);
		}
	}

	protected Object transformEntity(Entity entity) {
		log.debug("transforming group variable...");
		DJGroupVariableDef columnsGroupVariable = (DJGroupVariableDef) entity;
		DJCalculation op = columnsGroupVariable.getOperation();

		JRDesignExpression expression = new JRDesignExpression();
		
		String valueClassName = null;
		String initialExpression = null;

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
		variable.setCalculation(columnsGroupVariable.getOperation().getValue());
		variable.setName(columnsGroupVariable.getName());

		if (group != null) {
			variable.setResetType(JRDesignVariable.RESET_TYPE_GROUP);
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

//		field.setDescription(propertyColumn.getFieldDescription()); //hack for XML data source
		Iterator iter = columnProperty.getFieldProperties().keySet().iterator();
		while (iter.hasNext()) {
			String key = (String) iter.next();
			field.getPropertiesMap().setProperty(key, (String) columnProperty.getFieldProperties().get(key));
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
