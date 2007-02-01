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

import java.util.Collection;
import java.util.Iterator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.design.JRDesignParameter;
import ar.com.fdvs.dj.core.layout.AbstractLayoutManager;
import ar.com.fdvs.dj.domain.CustomExpression;
import ar.com.fdvs.dj.domain.DynamicJasperDesign;
import ar.com.fdvs.dj.domain.entities.Entity;

/**
 * Abstract Class used as base for the different Entities Registration Managers.</br>
 * </br>
 * Every implementation of this class should know how to register a given Entity</br>
 * and tranform it into any JasperReport object in order to add it to the </br>
 * JasperDesign.</br>
 * </br>
 * A Registration Manager is the first step required to create a report.</br>
 * A subclass should be created only when we want to add new features to DJ.</br>
 * Probably a new class from this hierarchy will imply a change to one or many </br>
 * Layout Managers.</br>
 * </br>
 * @see Entity
 * @see AbstractLayoutManager
 */
public abstract class AbstractEntityRegistrationManager {

	private static final Log log = LogFactory.getLog(AbstractEntityRegistrationManager.class);

	private DynamicJasperDesign djd;

	public DynamicJasperDesign getDjd() {
		return djd;
	}

	public AbstractEntityRegistrationManager(DynamicJasperDesign djd) {
		super();
		this.djd = djd;
	}

	public final void registerEntities(Collection entities) throws EntitiesRegistrationException {
		log.debug("registering entities...");
		try {
			if (entities!=null) {
				Iterator it = entities.iterator();
				while (it.hasNext()) {
					Entity entity = (Entity)it.next();
					registerEntity(entity);
				}
			}
		} catch (RuntimeException e) {
			throw new EntitiesRegistrationException(e.getMessage());
		}
	}

	/**
	 * Registers in the report's JasperDesign instance whatever is needed to
	 * show a given entity.
	 * @param Entity entity
	 * @throws EntitiesRegistrationException
	 */
	protected abstract void registerEntity(Entity entity);

	/**
	 * Transforms a DynamicJasper entity into a JasperReport one
	 * (JRDesignField, JRDesignParameter, JRDesignVariable)
	 * @param Entity entity
	 * @throws EntitiesRegistrationException
	 */
	protected abstract Object transformEntity(Entity entity);

	protected void registerExpressionColumnParameter(String property, CustomExpression customExpression) {
		JRDesignParameter dparam = new JRDesignParameter();
		dparam.setName(property);
		dparam.setValueClassName(CustomExpression.class.getName());
		try {
			getDjd().addParameter(dparam);
		} catch (JRException e) {
			throw new EntitiesRegistrationException(e.getMessage());
		}
		getDjd().getParametersWithValues().put(property, customExpression);
	}

}
