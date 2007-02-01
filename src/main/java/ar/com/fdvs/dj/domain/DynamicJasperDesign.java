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

package ar.com.fdvs.dj.domain;

import java.util.TreeMap;
import net.sf.jasperreports.engine.design.JasperDesign;

/**
 * Custom implementation of JasperDesign class. It adds the concept of registerd </br>
 * parameters and some useful methods.</br>
 */
public class DynamicJasperDesign extends JasperDesign {

	private static final long serialVersionUID = -1181633006527486089L;
	//<String paramName, CustomExpression expression>
	private TreeMap parametersWithValues;

	public DynamicJasperDesign() {
		super();
		this.parametersWithValues = new TreeMap();
	}

	public TreeMap getParametersWithValues() {
		return parametersWithValues;
	}

	public void setParametersWithValues(TreeMap parametersWithValues) {
		this.parametersWithValues = parametersWithValues;
	}

	public int getNumberOfRegisteredParameters() {
		int result = 0;
		if (getParametersWithValues()!=null) {
			result = getParametersWithValues().size();
		}
		return result;
	}

}
