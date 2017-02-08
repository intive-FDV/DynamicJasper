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

/*

 */
package ar.com.fdvs.dj.core.layout;

import ar.com.fdvs.dj.domain.DynamicReport;
import net.sf.jasperreports.engine.design.JasperDesign;

import java.util.Map;

/**
 * An interface that represents a a Manager to make elements respect a desired Layout.
 * @author msimone
 */
public interface LayoutManager {

	/**
	 * Entry point for applying a given layout.
	 * @param design The deseign to be used
	 * @param report The report to show
	 * @throws LayoutException
	 */
	void applyLayout(JasperDesign design, DynamicReport report) throws LayoutException;

	/**
	 * Useful as shared object between RegistrationManagers and the LayOutManager.
	 * An example of usage is the mapping between DJ objects and JR objects through its name, like
	 * a DJGroup and a JRDesignGroup 
	 * @return
	 */
	Map<String, Object> getReferencesMap();

}
