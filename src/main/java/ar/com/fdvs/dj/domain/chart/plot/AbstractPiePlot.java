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

public class AbstractPiePlot extends AbstractPlot {
	
	private static final long serialVersionUID = Entity.SERIAL_VERSION_UID;
	
	private Boolean circular = null;
	private String labelFormat = null;
	private String legendLabelFormat = null;

	/**
	 * Returns the circular.
	 * 
	 * @return the circular
	 **/
	public Boolean getCircular() {
		return circular;
	}

	/**
	 * Sets the circular.
	 * 
	 * @param circular the circular
	 **/
	public void setCircular(Boolean circular) {
		this.circular = circular;
	}

	/**
	 * Returns the label format.
	 * 
	 * @return the label format
	 **/
	public String getLabelFormat() {
		return labelFormat;
	}

	/**
	 * Sets the label format.
	 * 
	 * @param labelFormat the label format
	 **/
	public void setLabelFormat(String labelFormat) {
		this.labelFormat = labelFormat;
	}

	/**
	 * Returns the legend label format.
	 * 
	 * @return the legend label format
	 **/
	public String getLegendLabelFormat() {
		return legendLabelFormat;
	}

	/**
	 * Sets the legend label format.
	 * 
	 * @param legendLabelFormat the legend label format
	 **/
	public void setLegendLabelFormat(String legendLabelFormat) {
		this.legendLabelFormat = legendLabelFormat;
	}
}
