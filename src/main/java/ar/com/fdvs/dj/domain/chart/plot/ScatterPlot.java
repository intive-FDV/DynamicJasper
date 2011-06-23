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

import net.sf.jasperreports.charts.design.JRDesignScatterPlot;
import net.sf.jasperreports.engine.JRChartPlot;
import net.sf.jasperreports.engine.design.JRDesignExpression;
import ar.com.fdvs.dj.domain.CustomExpression;
import ar.com.fdvs.dj.domain.DynamicJasperDesign;
import ar.com.fdvs.dj.domain.StringExpression;
import ar.com.fdvs.dj.domain.entities.Entity;
import ar.com.fdvs.dj.util.ExpressionUtils;

public class ScatterPlot extends AbstractPlot {
	private static final long serialVersionUID = Entity.SERIAL_VERSION_UID;
	private DJAxisFormat xAxisFormat = new DJAxisFormat();
	private DJAxisFormat yAxisFormat = new DJAxisFormat();
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
	 * Sets the x axis format.
	 *
	 * @param xAxisFormat the x axis format
	 **/
	public void setXAxisFormat(DJAxisFormat xAxisFormat) {
		this.xAxisFormat = xAxisFormat;
	}

	/**
	 * Returns the x axis format.
	 *
	 * @return the x axis format
	 **/
	public DJAxisFormat getXAxisFormat() {
		return xAxisFormat;
	}

	/**
	 * Sets the y axis format.
	 *
	 * @param yAxisFormat the y axis format
	 **/
	public void setYAxisFormat(DJAxisFormat yAxisFormat) {
		this.yAxisFormat = yAxisFormat;
	}

	/**
	 * Returns the y axis format.
	 *
	 * @return the y axis format
	 **/
	public DJAxisFormat getYAxisFormat() {
		return yAxisFormat;
	}
	
	public void transform(DynamicJasperDesign design, JRChartPlot plot, String name) {
		super.transform(design, plot, name);
		JRDesignScatterPlot scatterPlot = (JRDesignScatterPlot) plot;
		
		StringExpression xAxisLabelExp = getXAxisFormat().getLabelExpression();
		if (xAxisLabelExp != null) {
			JRDesignExpression exp = ExpressionUtils.createAndRegisterExpression(design, "xAxisLabel_" + name, xAxisLabelExp);
			scatterPlot.setXAxisLabelExpression(exp);
		}
		if (xAxisFormat.getTickLabelMask() != null) 
			scatterPlot.setXAxisTickLabelMask(xAxisFormat.getTickLabelMask());	
		if (xAxisFormat.getLabelColor() != null)
			scatterPlot.setXAxisLabelColor(xAxisFormat.getLabelColor());
		if (xAxisFormat.getLabelFont() != null)
			scatterPlot.setXAxisLabelFont(xAxisFormat.getLabelFont().transform());
		if (xAxisFormat.getLineColor() != null)
			scatterPlot.setXAxisLineColor(xAxisFormat.getLineColor());		
		if (xAxisFormat.getTickLabelColor() != null)
			scatterPlot.setXAxisTickLabelColor(xAxisFormat.getTickLabelColor());
		if (xAxisFormat.getTickLabelFont() != null)
			scatterPlot.setXAxisTickLabelFont(xAxisFormat.getTickLabelFont().transform());
		CustomExpression xAxisRangeMinValueExp = getXAxisFormat().getRangeMinValueExpression();
		if (xAxisRangeMinValueExp != null) {
			JRDesignExpression exp = ExpressionUtils.createAndRegisterExpression(design, "xAxisRangeMinValue_" + name, xAxisRangeMinValueExp);
			scatterPlot.setDomainAxisMinValueExpression(exp);
		}
		CustomExpression xAxisRangeMaxValueExp = getXAxisFormat().getRangeMaxValueExpression();
		if (xAxisRangeMaxValueExp != null) {
			JRDesignExpression exp = ExpressionUtils.createAndRegisterExpression(design, "xAxisRangeMaxValue_" + name, xAxisRangeMaxValueExp);
			scatterPlot.setDomainAxisMaxValueExpression(exp);
		}
		
		StringExpression yAxisLabelExp = getYAxisFormat().getLabelExpression();
		if (yAxisLabelExp != null) {
			JRDesignExpression exp = ExpressionUtils.createAndRegisterExpression(design, "yAxisLabel_" + name, yAxisLabelExp);
			scatterPlot.setYAxisLabelExpression(exp);
		}
		if (yAxisFormat.getTickLabelMask() != null) 
			scatterPlot.setYAxisTickLabelMask(yAxisFormat.getTickLabelMask());	
		if (yAxisFormat.getLabelColor() != null)
			scatterPlot.setYAxisLabelColor(yAxisFormat.getLabelColor());
		if (yAxisFormat.getLabelFont() != null)
			scatterPlot.setYAxisLabelFont(yAxisFormat.getLabelFont().transform());
		if (yAxisFormat.getLineColor() != null)
			scatterPlot.setYAxisLineColor(yAxisFormat.getLineColor());		
		if (yAxisFormat.getTickLabelColor() != null)
			scatterPlot.setYAxisTickLabelColor(yAxisFormat.getTickLabelColor());
		if (yAxisFormat.getTickLabelFont() != null)
			scatterPlot.setYAxisTickLabelFont(yAxisFormat.getTickLabelFont().transform());
		CustomExpression yAxisRangeMinValueExp = getYAxisFormat().getRangeMinValueExpression();
		if (yAxisRangeMinValueExp != null) {
			JRDesignExpression exp = ExpressionUtils.createAndRegisterExpression(design, "yAxisRangeMinValue_" + name, yAxisRangeMinValueExp);
			scatterPlot.setRangeAxisMinValueExpression(exp);
		}
		CustomExpression yAxisRangeMaxValueExp = getYAxisFormat().getRangeMaxValueExpression();
		if (yAxisRangeMaxValueExp != null) {
			JRDesignExpression exp = ExpressionUtils.createAndRegisterExpression(design, "yAxisRangeMaxValue_" + name, yAxisRangeMaxValueExp);
			scatterPlot.setRangeAxisMaxValueExpression(exp);
		}
		
		if (showShapes != null)
			scatterPlot.setShowShapes(showShapes);
		if (showLines != null)
			scatterPlot.setShowLines(showLines);
	}
}
