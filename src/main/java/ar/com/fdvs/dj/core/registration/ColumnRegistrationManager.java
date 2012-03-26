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

import java.util.ArrayList;
import java.util.Iterator;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;
import net.sf.jasperreports.engine.design.JRDesignField;
import net.sf.jasperreports.engine.design.JRDesignParameter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import ar.com.fdvs.dj.core.layout.LayoutManager;
import ar.com.fdvs.dj.domain.ColumnProperty;
import ar.com.fdvs.dj.domain.DynamicJasperDesign;
import ar.com.fdvs.dj.domain.DynamicReport;
import ar.com.fdvs.dj.domain.entities.Entity;
import ar.com.fdvs.dj.domain.entities.columns.AbstractColumn;
import ar.com.fdvs.dj.domain.entities.columns.ExpressionColumn;
import ar.com.fdvs.dj.domain.entities.columns.PropertyColumn;
import ar.com.fdvs.dj.util.ExpressionUtils;

/**
 * Manager invoked to register columns. An AbstractColumn is read and </br>
 * transformed into a JRDesignField.</br>
 * </br>
 * @see AbstractColumn
 */
public class ColumnRegistrationManager extends AbstractEntityRegistrationManager {

	private static final String FIELD_ALREADY_REGISTERED = "The field has already been registered";

	private static final Log log = LogFactory.getLog(ColumnRegistrationManager.class);

	private int colCounter = 0;

	private static final String COLUMN_NAME_PREFIX = "COLUMN_";

	public ColumnRegistrationManager(DynamicJasperDesign jd, DynamicReport dr, LayoutManager layoutManager) {
		super(jd,dr,layoutManager);
	}

	protected void registerEntity(Entity entity) {
//		log.debug("registering column...");
		//A default name is setted if the user didn't specify one.
		AbstractColumn column = (AbstractColumn)entity;
		if (column.getName() == null){
			column.setName(COLUMN_NAME_PREFIX + colCounter++ );
		}
		if (column.getConditionalStyles() != null && !column.getConditionalStyles().isEmpty()){
			ConditionalStylesRegistrationManager conditionalStylesRm = new ConditionalStylesRegistrationManager(getDjd(),getDynamicReport(),column.getName(),getLayoutManager());
			conditionalStylesRm.registerEntities(column.getConditionalStyles());
		}
		if (column.getTextFormatter() != null) {
			JRDesignParameter parameter = new JRDesignParameter();
			parameter.setName(ExpressionUtils.createParameterName("formatter_for_" + column.getName(), column.getTextFormatter()));
			parameter.setValueClassName(Object.class.getName());
			log.debug("registering text formatter: " + parameter.getName());
			getDjd().getParametersWithValues().put(parameter.getName(), column.getTextFormatter());
			try {
				getDjd().addParameter(parameter);
			} catch (JRException e) {
				log.debug("repeated parameter: " + parameter.getName());
			}
		}

		if (entity instanceof PropertyColumn) {
			try {
				//addField() will throw an exception only if the column has already been registered.
				PropertyColumn propertyColumn = ((PropertyColumn)entity);
				log.debug("registering column " + column.getName());
				ColumnProperty columnProperty = propertyColumn.getColumnProperty();
				if ( columnProperty != null && !columnProperty.getProperty().startsWith("__name_to_be_replaced_in_registration_manager_")){
					JRField jrfield = (JRField)transformEntity(entity);
					if (getDjd().getFieldsMap().get(jrfield.getName())==null){
						getDjd().addField(jrfield);
					}					
				}
				if (entity instanceof ExpressionColumn && ((ExpressionColumn) entity).getExpression() != null ) {
					ExpressionColumn expressionColumn = (ExpressionColumn) entity;
					
					//The Custom Expression parameter must be registered
					expressionColumn.setColumns( getDynamicReport().getAllFields() );
					expressionColumn.setVariables( new ArrayList(getDjd().getVariablesList()) );
//					String property_name = expressionColumn.getColumnProperty().getProperty();
					
					//override property column name to make it unique and determinable
					String property_name = "customExpression_for_"+expressionColumn.getName(); //NOTE Since 3.0.14-beta2 Change added to make variable names determinable inside scriptlets 
					
					expressionColumn.getColumnProperty().setProperty(property_name);
					
					registerCustomExpressionParameter(property_name, expressionColumn.getExpression());
					registerCustomExpressionParameter(property_name + "_calc", expressionColumn.getExpressionForCalculation());
				}
			} catch (JRException e) {
				log.info("The field has already been registered" + ": " + e.getMessage() + ", (skipping)");
			}
		} 
	}

	/**
	 * Receives a PropertyColumn and returns a JRDesignField
	 */
	protected Object transformEntity(Entity entity) {
		PropertyColumn propertyColumn = (PropertyColumn) entity;
		JRDesignField field = new JRDesignField();
		ColumnProperty columnProperty = propertyColumn.getColumnProperty();
		field.setName(columnProperty.getProperty());
		field.setValueClassName(columnProperty.getValueClassName());
		
		log.debug("transforming column property: " + columnProperty.getProperty() + " (" + columnProperty.getValueClassName() +")");

		field.setDescription(propertyColumn.getFieldDescription()); //hack for XML data source
		Iterator iter = columnProperty.getFieldProperties().keySet().iterator();
		while (iter.hasNext()) {
			String key = (String) iter.next();
			field.getPropertiesMap().setProperty(key, (String) columnProperty.getFieldProperties().get(key));
		}
		return field;
	}

}
