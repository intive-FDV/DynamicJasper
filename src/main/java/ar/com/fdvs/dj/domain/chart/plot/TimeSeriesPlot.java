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

import ar.com.fdvs.dj.domain.CustomExpression;
import ar.com.fdvs.dj.domain.DynamicJasperDesign;
import ar.com.fdvs.dj.domain.StringExpression;
import ar.com.fdvs.dj.domain.entities.Entity;
import ar.com.fdvs.dj.util.ExpressionUtils;
import net.sf.jasperreports.charts.design.JRDesignTimeSeriesPlot;
import net.sf.jasperreports.engine.JRChartPlot;
import net.sf.jasperreports.engine.design.JRDesignExpression;

public class TimeSeriesPlot extends AbstractPlot {
	private static final long serialVersionUID = Entity.SERIAL_VERSION_UID;
	private DJAxisFormat timeAxisFormat = new DJAxisFormat();
	private DJAxisFormat valueAxisFormat = new DJAxisFormat();
	private Boolean showShapes = null;
	private Boolean showLines = null;
	
	/**
	 * Sets the shapes visibility.
	 *
	 * @param showShapes the shapes visibility
	 **/
	public void setShowShapes(Boolean showShapes) {
		this.showShapes = showShapes;
	}

	/**
	 * Returns the shapes visibility.
	 *
	 * @return the shapes visibility
	 **/
	public Boolean getShowShapes() {
		return showShapes;
	}

	/**
	 * Sets the lines visibility.
	 *
	 * @param showLines the lines visibility
	 **/
	public void setShowLines(Boolean showLines) {
		this.showLines = showLines;
	}

	/**
	 * Returns the lines visibility.
	 *
	 * @return the lines visibility
	 **/
	public Boolean getShowLines() {
		return showLines;
	}
	
	/**
	 * Sets the time axis format.
	 *
	 * @param timeAxisFormat the time axis format
	 **/
	public void setTimeAxisFormat(DJAxisFormat timeAxisFormat) {
		this.timeAxisFormat = timeAxisFormat;
	}

	/**
	 * Returns the time axis format.
	 *
	 * @return the time axis format
	 **/
	public DJAxisFormat getTimeAxisFormat() {
		return timeAxisFormat;
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
	
	public void transform(DynamicJasperDesign design, JRChartPlot plot, String name) {
		super.transform(design, plot, name);
		JRDesignTimeSeriesPlot timeSeriesPlot = (JRDesignTimeSeriesPlot) plot;
		
		StringExpression timeAxisLabelExp = getTimeAxisFormat().getLabelExpression();
		if (timeAxisLabelExp != null) {
			JRDesignExpression exp = ExpressionUtils.createExpression(design, "timeAxisLabel_" + name, timeAxisLabelExp);
			timeSeriesPlot.setTimeAxisLabelExpression(exp);
		}
		if (timeAxisFormat.getTickLabelMask() != null) 
			timeSeriesPlot.setTimeAxisTickLabelMask(timeAxisFormat.getTickLabelMask());	
		if (timeAxisFormat.getLabelColor() != null)
			timeSeriesPlot.setTimeAxisLabelColor(timeAxisFormat.getLabelColor());
		if (timeAxisFormat.getLabelFont() != null)
			timeSeriesPlot.setTimeAxisLabelFont(timeAxisFormat.getLabelFont().transform());
		if (timeAxisFormat.getLineColor() != null)
			timeSeriesPlot.setTimeAxisLineColor(timeAxisFormat.getLineColor());		
		if (timeAxisFormat.getTickLabelColor() != null)
			timeSeriesPlot.setTimeAxisTickLabelColor(timeAxisFormat.getTickLabelColor());
		if (timeAxisFormat.getTickLabelFont() != null)
			timeSeriesPlot.setTimeAxisTickLabelFont(timeAxisFormat.getTickLabelFont().transform());
		CustomExpression timeAxisRangeMinValueExp = getTimeAxisFormat().getRangeMinValueExpression();
		if (timeAxisRangeMinValueExp != null) {
			JRDesignExpression exp = ExpressionUtils.createExpression(design, "timeAxisRangeMinValue_" + name, timeAxisRangeMinValueExp);
			timeSeriesPlot.setDomainAxisMinValueExpression(exp);
		}
		CustomExpression timeAxisRangeMaxValueExp = getTimeAxisFormat().getRangeMaxValueExpression();
		if (timeAxisRangeMaxValueExp != null) {
			JRDesignExpression exp = ExpressionUtils.createExpression(design, "timeAxisRangeMaxValue_" + name, timeAxisRangeMaxValueExp);
			timeSeriesPlot.setDomainAxisMaxValueExpression(exp);
		}
		
		StringExpression valueAxisLabelExp = getValueAxisFormat().getLabelExpression();
		if (valueAxisLabelExp != null) {
			JRDesignExpression exp = ExpressionUtils.createExpression(design, "valueAxisLabel_" + name, valueAxisLabelExp);
			timeSeriesPlot.setValueAxisLabelExpression(exp);
		}
		if (valueAxisFormat.getTickLabelMask() != null) 
			timeSeriesPlot.setValueAxisTickLabelMask(valueAxisFormat.getTickLabelMask());	
		if (valueAxisFormat.getLabelColor() != null)
			timeSeriesPlot.setValueAxisLabelColor(valueAxisFormat.getLabelColor());
		if (valueAxisFormat.getLabelFont() != null)
			timeSeriesPlot.setValueAxisLabelFont(valueAxisFormat.getLabelFont().transform());
		if (valueAxisFormat.getLineColor() != null)
			timeSeriesPlot.setValueAxisLineColor(valueAxisFormat.getLineColor());		
		if (valueAxisFormat.getTickLabelColor() != null)
			timeSeriesPlot.setValueAxisTickLabelColor(valueAxisFormat.getTickLabelColor());
		if (valueAxisFormat.getTickLabelFont() != null)
			timeSeriesPlot.setValueAxisTickLabelFont(valueAxisFormat.getTickLabelFont().transform());
		CustomExpression valueAxisRangeMinValueExp = getValueAxisFormat().getRangeMinValueExpression();
		if (valueAxisRangeMinValueExp != null) {
			JRDesignExpression exp = ExpressionUtils.createExpression(design, "valueAxisRangeMinValue_" + name, valueAxisRangeMinValueExp);
			timeSeriesPlot.setRangeAxisMinValueExpression(exp);
		}
		CustomExpression valueAxisRangeMaxValueExp = getValueAxisFormat().getRangeMaxValueExpression();
		if (valueAxisRangeMaxValueExp != null) {
			JRDesignExpression exp = ExpressionUtils.createExpression(design, "valueAxisRangeMaxValue_" + name, valueAxisRangeMaxValueExp);
			timeSeriesPlot.setRangeAxisMaxValueExpression(exp);
		}
		
		if (showShapes != null)
			timeSeriesPlot.setShowShapes(showShapes);
		if (showLines != null)
			timeSeriesPlot.setShowLines(showLines);
	}
}
