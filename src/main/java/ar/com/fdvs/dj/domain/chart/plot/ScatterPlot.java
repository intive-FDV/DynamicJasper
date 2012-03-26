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
