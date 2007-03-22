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

import java.util.ArrayList;
import java.util.Iterator;

import org.apache.commons.collections.collection.UnmodifiableCollection;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import sun.reflect.ReflectionFactory.GetReflectionFactoryAction;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;
import net.sf.jasperreports.engine.design.JRDesignField;
import ar.com.fdvs.dj.domain.ColumnProperty;
import ar.com.fdvs.dj.domain.DynamicJasperDesign;
import ar.com.fdvs.dj.domain.DynamicReport;
import ar.com.fdvs.dj.domain.entities.Entity;
import ar.com.fdvs.dj.domain.entities.columns.AbstractColumn;
import ar.com.fdvs.dj.domain.entities.columns.ExpressionColumn;
import ar.com.fdvs.dj.domain.entities.columns.PropertyColumn;
import ar.com.fdvs.dj.domain.entities.columns.SimpleColumn;

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

	public ColumnRegistrationManager(DynamicJasperDesign jd, DynamicReport dr) {
		super(jd,dr);
	}

	protected void registerEntity(Entity entity) {
//		log.debug("registering column...");
		//A default name is setted if the user didn't specify one.
		AbstractColumn column = (AbstractColumn)entity;
		if (column.getName() == null){
			column.setName(COLUMN_NAME_PREFIX + colCounter++ );
		}
		if (column.getConditionalStyles() != null && !column.getConditionalStyles().isEmpty()){
			new ConditionalStylesRegistrationManager(getDjd(),getDynamicReport(),column.getName()).registerEntities(column.getConditionalStyles());
		}

		if (entity instanceof PropertyColumn) {
			try {
				//addField() will throw an exception only if the column has already been registered.
				PropertyColumn propertyColumn = ((PropertyColumn)entity);
				log.debug("registering column " + column.getName());
				if ( propertyColumn.getColumnProperty() != null && !(entity instanceof ExpressionColumn)){
					getDjd().addField((JRField)transformEntity(entity));
				}
				if (entity instanceof ExpressionColumn) {
					//The Custom Expression parameter must be registered
					ExpressionColumn expressionColumn = (ExpressionColumn) entity;
					ArrayList l = new ArrayList(getColumns());
					for (Iterator iter = getDynamicReport().getFields().iterator(); iter.hasNext();) {
						ColumnProperty columnProperty = (ColumnProperty) iter.next();
						SimpleColumn simpleColumn = new SimpleColumn();
						simpleColumn.setColumnProperty(columnProperty);
						l.add(simpleColumn);
						
					}
					expressionColumn.setColumns( l );
					registerExpressionColumnParameter(expressionColumn.getColumnProperty().getProperty(), expressionColumn.getExpression());
				}
			} catch (JRException e) {
				log.info(FIELD_ALREADY_REGISTERED);
			}
		}
	}

	protected Object transformEntity(Entity entity) {
		PropertyColumn propertyColumn = (PropertyColumn) entity;
		JRDesignField field = new JRDesignField();
		field.setName(propertyColumn.getColumnProperty().getProperty());
		field.setValueClassName(propertyColumn.getColumnProperty()
				.getValueClassName());
		log.debug("transforming column: " + propertyColumn.getColumnProperty().getProperty() + " / " + propertyColumn.getColumnProperty()
				.getValueClassName());
		return field;
	}

}
