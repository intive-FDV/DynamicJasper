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

package ar.com.fdvs.dj.domain.chart.plot;

import ar.com.fdvs.dj.domain.entities.Entity;


public abstract class AbstractCategoryAxisPlot extends AbstractPlot {
	
	private static final long serialVersionUID = Entity.SERIAL_VERSION_UID;
	
	private DJAxisFormat categoryAxisFormat = new DJAxisFormat();
	private DJAxisFormat valueAxisFormat = new DJAxisFormat();

	/**
	 * Sets the category axis format.
	 * 
	 * @param categoryAxisFormat the category axis format
	 **/
	public void setCategoryAxisFormat(DJAxisFormat categoryAxisFormat) {
		this.categoryAxisFormat = categoryAxisFormat;
	}

	/**
	 * Returns the category axis format.
	 * 
	 * @return the category axis format
	 **/
	public DJAxisFormat getCategoryAxisFormat() {
		return categoryAxisFormat;
	}

	/**
	 * Sets the value axis format.
	 * 
	 * @param valueAxisFormat the value axis format
	 **/
	public void setValueAxisFormat(DJAxisFormat valueAxisFormat) {
		this.valueAxisFormat = valueAxisFormat;
	}

	/**
	 * Returns the value axis format.
	 * 
	 * @return the value axis format
	 **/
	public DJAxisFormat getValueAxisFormat() {
		return valueAxisFormat;
	}
}
