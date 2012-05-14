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
            column.setName(this.getDjd().getName() + "_" + COLUMN_NAME_PREFIX + colCounter++ );
		}

        column.setReportName(this.getDjd().getName());

        log.debug("Registering column " + column.getName() + " (" + column.getClass().getSimpleName() +")");
        if (column.getConditionalStyles() != null && !column.getConditionalStyles().isEmpty()){
			ConditionalStylesRegistrationManager conditionalStylesRm = new ConditionalStylesRegistrationManager(getDjd(),getDynamicReport(),column.getName(),getLayoutManager());
			conditionalStylesRm.registerEntities(column.getConditionalStyles());
		}
		if (column.getTextFormatter() != null) {
			JRDesignParameter parameter = new JRDesignParameter();
			parameter.setName(ExpressionUtils.createParameterName("formatter_for_" + column.getName(), column.getTextFormatter()));
			parameter.setValueClassName(Object.class.getName());
            log.debug("Registering text formatter: " + parameter.getName());
			getDjd().getParametersWithValues().put(parameter.getName(), column.getTextFormatter());
			try {
				getDjd().addParameter(parameter);
			} catch (JRException e) {
                log.debug("Repeated parameter: " + parameter.getName());
			}
		}

		if (entity instanceof PropertyColumn) {
			try {
				//addField() will throw an exception only if the column has already been registered.
				PropertyColumn propertyColumn = ((PropertyColumn)entity);

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

        log.debug("Transforming column: " + propertyColumn.getName() + ", property: " + columnProperty.getProperty() + " (" + columnProperty.getValueClassName() +") " );

		field.setDescription(propertyColumn.getFieldDescription()); //hack for XML data source
		Iterator iter = columnProperty.getFieldProperties().keySet().iterator();
		while (iter.hasNext()) {
			String key = (String) iter.next();
			field.getPropertiesMap().setProperty(key, (String) columnProperty.getFieldProperties().get(key));
		}
		return field;
	}

}
