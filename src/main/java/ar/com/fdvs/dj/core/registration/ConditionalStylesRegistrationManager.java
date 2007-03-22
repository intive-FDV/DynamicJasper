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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import ar.com.fdvs.dj.domain.DynamicJasperDesign;
import ar.com.fdvs.dj.domain.DynamicReport;
import ar.com.fdvs.dj.domain.entities.ColumnsGroup;
import ar.com.fdvs.dj.domain.entities.Entity;
import ar.com.fdvs.dj.domain.entities.conditionalStyle.ConditionalStyle;

/**
 * Manager invoked to register conditional styles. </br>
 * A ConditionalStyle is read and it's expression is registered. </br>
 * </br>
 * @see ColumnsGroup
 */
public class ConditionalStylesRegistrationManager extends AbstractEntityRegistrationManager {

	private static final Log log = LogFactory.getLog(ConditionalStylesRegistrationManager.class);
	private String columnName;
	int counter = 0;

	public ConditionalStylesRegistrationManager(DynamicJasperDesign jd,  DynamicReport dr, String columnName) {
		super(jd,dr);
		this.columnName = columnName;
	}

	protected void registerEntity(Entity entity) {
		log.debug("registering conditional style...");
		//The column names are already registered when we get here
		ConditionalStyle condition = (ConditionalStyle) entity;
		String expressionName = columnName + "_style_" + counter;
		condition.setName(expressionName);
		registerExpressionColumnParameter(expressionName, condition.getCondition());
		counter++;
	}

	protected Object transformEntity(Entity entity) {
		return null;
	}

}
