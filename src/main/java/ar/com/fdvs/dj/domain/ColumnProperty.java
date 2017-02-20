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

package ar.com.fdvs.dj.domain;

import ar.com.fdvs.dj.domain.entities.Entity;
import ar.com.fdvs.dj.util.PropertiesMap;

public class ColumnProperty extends DJBaseElement {

	private static final long serialVersionUID = Entity.SERIAL_VERSION_UID;
	
	private String property;
	private String valueClassName;
	private PropertiesMap<String, String> fieldProperties = new PropertiesMap<String, String>();

    public ColumnProperty(String property, String valueClass) {
    	this.setProperty(property);
    	this.setValueClassName(valueClass);
    }

    public ColumnProperty(String property, Class clazz) {
    	this.setProperty(property);
    	this.setValueClassName(clazz.getName());
    }    
    
	public ColumnProperty() {
		super();
	}

    public String getProperty() {
		return property;
	}

	public void setProperty(String property) {
		this.property = property;
	}

	public String getValueClassName() {
		return valueClassName;
	}

    public void setValueClassName(String valueClass) {
		this.valueClassName = valueClass;
	}

	public PropertiesMap<String, String> getFieldProperties() {
		return fieldProperties;
	}

	public void setFieldProperties(PropertiesMap<String, String> fieldProperties) {
		this.fieldProperties = fieldProperties;
	}

	public Object addFieldProperty(String key, String value) {
		return fieldProperties.put(key, value);
	}

}
