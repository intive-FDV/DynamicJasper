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

import net.sf.jasperreports.charts.design.JRDesignTimeSeriesPlot;
import net.sf.jasperreports.engine.JRChartPlot;
import net.sf.jasperreports.engine.design.JRDesignExpression;
import ar.com.fdvs.dj.domain.CustomExpression;
import ar.com.fdvs.dj.domain.DynamicJasperDesign;
import ar.com.fdvs.dj.domain.StringExpression;
import ar.com.fdvs.dj.domain.entities.Entity;
import ar.com.fdvs.dj.util.ExpressionUtils;

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
			JRDesignExpression exp = ExpressionUtils.createAndRegisterExpression(design, "timeAxisLabel_" + name, timeAxisLabelExp);
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
			JRDesignExpression exp = ExpressionUtils.createAndRegisterExpression(design, "timeAxisRangeMinValue_" + name, timeAxisRangeMinValueExp);
			timeSeriesPlot.setDomainAxisMinValueExpression(exp);
		}
		CustomExpression timeAxisRangeMaxValueExp = getTimeAxisFormat().getRangeMaxValueExpression();
		if (timeAxisRangeMaxValueExp != null) {
			JRDesignExpression exp = ExpressionUtils.createAndRegisterExpression(design, "timeAxisRangeMaxValue_" + name, timeAxisRangeMaxValueExp);
			timeSeriesPlot.setDomainAxisMaxValueExpression(exp);
		}
		
		StringExpression valueAxisLabelExp = getValueAxisFormat().getLabelExpression();
		if (valueAxisLabelExp != null) {
			JRDesignExpression exp = ExpressionUtils.createAndRegisterExpression(design, "valueAxisLabel_" + name, valueAxisLabelExp);
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
			JRDesignExpression exp = ExpressionUtils.createAndRegisterExpression(design, "valueAxisRangeMinValue_" + name, valueAxisRangeMinValueExp);
			timeSeriesPlot.setRangeAxisMinValueExpression(exp);
		}
		CustomExpression valueAxisRangeMaxValueExp = getValueAxisFormat().getRangeMaxValueExpression();
		if (valueAxisRangeMaxValueExp != null) {
			JRDesignExpression exp = ExpressionUtils.createAndRegisterExpression(design, "valueAxisRangeMaxValue_" + name, valueAxisRangeMaxValueExp);
			timeSeriesPlot.setRangeAxisMaxValueExpression(exp);
		}
		
		if (showShapes != null)
			timeSeriesPlot.setShowShapes(showShapes);
		if (showLines != null)
			timeSeriesPlot.setShowLines(showLines);
	}
}
