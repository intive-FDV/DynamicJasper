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

import java.util.Collection;
import java.util.Iterator;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.design.JRDesignParameter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import ar.com.fdvs.dj.core.DJConstants;
import ar.com.fdvs.dj.core.layout.AbstractLayoutManager;
import ar.com.fdvs.dj.core.layout.LayoutManager;
import ar.com.fdvs.dj.domain.CustomExpression;
import ar.com.fdvs.dj.domain.DynamicJasperDesign;
import ar.com.fdvs.dj.domain.DynamicReport;
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
public abstract class AbstractEntityRegistrationManager implements DJConstants {

	private static final Log log = LogFactory.getLog(AbstractEntityRegistrationManager.class);

	private DynamicJasperDesign djd;

//	private Collection columns;

	private DynamicReport dynamicReport;
	
	private LayoutManager layoutManager;

	public AbstractEntityRegistrationManager(DynamicJasperDesign djd, DynamicReport dr, LayoutManager layoutManager) {
		this.djd = djd;
		this.dynamicReport = dr;
		this.layoutManager = layoutManager;
	}

	public final void registerEntities(Collection entities) throws EntitiesRegistrationException {
//		this.columns = entities;
		log.debug("Registering entities: " + this.getClass().getName());
		try {
			if (entities!=null) {
				Iterator it = entities.iterator();
				while (it.hasNext()) {
					Entity entity = (Entity)it.next();
					registerEntity(entity);
				}
			}
		} catch (RuntimeException e) {
			throw new EntitiesRegistrationException(e.getMessage(),e);
		} finally {
//			this.columns = null;
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
	protected abstract Object transformEntity(Entity entity) throws JRException;

	protected void registerCustomExpressionParameter(String name, CustomExpression customExpression) {
		if (customExpression == null){
			log.debug("No customExpression for calculation for property " + name );
			return;
		}
		JRDesignParameter dparam = new JRDesignParameter();
		dparam.setName(name);
		dparam.setValueClassName(CustomExpression.class.getName());
		log.debug("Registering customExpression parameter for property " + name );
		try {
			getDjd().addParameter(dparam);
		} catch (JRException e) {
			throw new EntitiesRegistrationException(e.getMessage(),e);
		}
		getDjd().getParametersWithValues().put(name, customExpression);
	}

//	public Collection getColumns() {
//		return columns;
//	}

	public DynamicReport getDynamicReport() {
		return dynamicReport;
	}

	public LayoutManager getLayoutManager() {
		return layoutManager;
	}

	public DynamicJasperDesign getDjd() {
		return djd;
	}

}
