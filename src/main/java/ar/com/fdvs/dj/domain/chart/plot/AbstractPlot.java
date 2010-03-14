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

import java.awt.Color;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.jfree.chart.plot.PlotOrientation;

import ar.com.fdvs.dj.domain.DJBaseElement;
import ar.com.fdvs.dj.domain.DynamicJasperDesign;
import ar.com.fdvs.dj.domain.entities.Entity;

import net.sf.jasperreports.engine.JRChartPlot;
import net.sf.jasperreports.engine.base.JRBaseChartPlot;

public abstract class AbstractPlot extends DJBaseElement {
	
	private static final long serialVersionUID = Entity.SERIAL_VERSION_UID;
	
	private Double labelRotation;
	private PlotOrientation orientation;
	private List seriesColors = new ArrayList();

	/**
	 * Sets the plot orientation (PlotOrientation.HORIZONTAL or PlotOrientation.VERTICAL).
	 *
	 * @param orientation the plot orientation
	 **/
	public void setOrientation(PlotOrientation orientation) {
		this.orientation = orientation;
	}

	/**
	 * Returns the plot orientation (PlotOrientation.HORIZONTAL or PlotOrientation.VERTICAL).
	 *
	 * @return	the plot orientation
	 **/
	public PlotOrientation getOrientation() {
		return orientation;
	}

	/**
	 * Adds the specified series color to the plot.
	 * 
	 * @param color the series color
	 **/
	public void addSeriesColor(Color color) {
		this.seriesColors.add(color);
	}
	
	/**
	 * Removes all defined series colors.
	 */
	public void clearSeriesColors()	{
		this.seriesColors.clear();
	}
	
	/**
	 * Set the specified series colors to the plot.
	 * 
	 * @param seriesColors the series colors
	 **/
	public void setSeriesColors(List seriesColors) {
		clearSeriesColors();
		this.seriesColors.addAll(seriesColors);
	}

	/**
	 * Returns a list of all the defined series colors.  Every entry in the list is of type JRChartPlot.JRSeriesColor.
	 * If there are no defined series colors this method will return an empty list, not null. 
	 *
	 * @return	the list of series colors
	 **/
	public List getSeriesColors() {
		return seriesColors;
	}
	
	/**
	 * Sets the angle in degrees to rotate the data axis labels.  The range is -360 to 360.  A positive value angles
	 * the label so it reads downwards wile a negative value angles the label so it reads upwards.  Only charts that
	 * use a category based axis (such as line or bar charts) support label rotation.
	 * 
	 * @param labelRotation the label rotation
	 **/
	public void setLabelRotation(Double labelRotation) {
		this.labelRotation = labelRotation;
	}

	/**
	 * Gets the angle in degrees to rotate the data axis labels.  The range is -360 to 360.  A positive value angles
	 * the label so it reads downwards wile a negative value angles the label so it reads upwards.  Only charts that
	 * use a category based axis (such as line or bar charts) support label rotation.
	 * @return	the label rotation
	 **/
	public Double getLabelRotation() {
		return labelRotation;
	}
	
	public void transform(DynamicJasperDesign design, JRChartPlot plot, String name) {
		if (getLabelRotation() != null)
			plot.setLabelRotation(getLabelRotation());
		if (orientation != null)
			plot.setOrientation(orientation);
		if (seriesColors != null) {
			List colors = new ArrayList();
			int i = 1;
			for (Iterator iterator = seriesColors.iterator(); iterator.hasNext();) {
				Color color = (Color) iterator.next();
				colors.add(new JRBaseChartPlot.JRBaseSeriesColor(i++, color));
			}
			plot.setSeriesColors(colors);
		}
	}
}
