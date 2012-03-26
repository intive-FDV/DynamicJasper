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

import net.sf.jasperreports.charts.design.JRDesignLinePlot;
import net.sf.jasperreports.engine.JRChartPlot;
import net.sf.jasperreports.engine.design.JRDesignExpression;
import ar.com.fdvs.dj.domain.CustomExpression;
import ar.com.fdvs.dj.domain.DynamicJasperDesign;
import ar.com.fdvs.dj.domain.StringExpression;
import ar.com.fdvs.dj.domain.entities.Entity;
import ar.com.fdvs.dj.util.ExpressionUtils;

public class LinePlot extends AbstractCategoryAxisPlot {
	private static final long serialVersionUID = Entity.SERIAL_VERSION_UID;
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
	
	public void transform(DynamicJasperDesign design, JRChartPlot plot, String name) {
		super.transform(design, plot, name);
		JRDesignLinePlot linePlot = (JRDesignLinePlot) plot;
		
		StringExpression categoryAxisLabelExp = getCategoryAxisFormat().getLabelExpression();
		if (categoryAxisLabelExp != null) {
			JRDesignExpression exp = ExpressionUtils.createAndRegisterExpression(design, "categoryAxisLabel_" + name, categoryAxisLabelExp);
			linePlot.setCategoryAxisLabelExpression(exp);
		}
		if (getCategoryAxisFormat().getTickLabelMask() != null) 
			linePlot.setCategoryAxisTickLabelMask(getCategoryAxisFormat().getTickLabelMask());	
		if (getCategoryAxisFormat().getLabelColor() != null)
			linePlot.setCategoryAxisLabelColor(getCategoryAxisFormat().getLabelColor());
		if (getCategoryAxisFormat().getLabelFont() != null)
			linePlot.setCategoryAxisLabelFont(getCategoryAxisFormat().getLabelFont().transform());
		if (getCategoryAxisFormat().getLineColor() != null)
			linePlot.setCategoryAxisLineColor(getCategoryAxisFormat().getLineColor());		
		if (getCategoryAxisFormat().getTickLabelColor() != null)
			linePlot.setCategoryAxisTickLabelColor(getCategoryAxisFormat().getTickLabelColor());
		if (getCategoryAxisFormat().getTickLabelFont() != null)
			linePlot.setCategoryAxisTickLabelFont(getCategoryAxisFormat().getTickLabelFont().transform());
		CustomExpression categoryAxisRangeMinValueExp = getCategoryAxisFormat().getRangeMinValueExpression();
		if (categoryAxisRangeMinValueExp != null) {
			JRDesignExpression exp = ExpressionUtils.createAndRegisterExpression(design, "categoryAxisRangeMinValue_" + name, categoryAxisRangeMinValueExp);
			linePlot.setDomainAxisMinValueExpression(exp);
		}
		CustomExpression categoryAxisRangeMaxValueExp = getCategoryAxisFormat().getRangeMaxValueExpression();
		if (categoryAxisRangeMaxValueExp != null) {
			JRDesignExpression exp = ExpressionUtils.createAndRegisterExpression(design, "categoryAxisRangeMaxValue_" + name, categoryAxisRangeMaxValueExp);
			linePlot.setDomainAxisMaxValueExpression(exp);
		}
		
		StringExpression valueAxisLabelExp = getValueAxisFormat().getLabelExpression();
		if (valueAxisLabelExp != null) {
			JRDesignExpression exp = ExpressionUtils.createAndRegisterExpression(design, "valueAxisLabel_" + name, valueAxisLabelExp);
			linePlot.setValueAxisLabelExpression(exp);
		}
		if (getValueAxisFormat().getTickLabelMask() != null) 
			linePlot.setValueAxisTickLabelMask(getValueAxisFormat().getTickLabelMask());	
		if (getValueAxisFormat().getLabelColor() != null)
			linePlot.setValueAxisLabelColor(getValueAxisFormat().getLabelColor());
		if (getValueAxisFormat().getLabelFont() != null)
			linePlot.setValueAxisLabelFont(getValueAxisFormat().getLabelFont().transform());
		if (getValueAxisFormat().getLineColor() != null)
			linePlot.setValueAxisLineColor(getValueAxisFormat().getLineColor());		
		if (getValueAxisFormat().getTickLabelColor() != null)
			linePlot.setValueAxisTickLabelColor(getValueAxisFormat().getTickLabelColor());
		if (getValueAxisFormat().getTickLabelFont() != null)
			linePlot.setValueAxisTickLabelFont(getValueAxisFormat().getTickLabelFont().transform());
		CustomExpression valueAxisRangeMinValueExp = getValueAxisFormat().getRangeMinValueExpression();
		if (valueAxisRangeMinValueExp != null) {
			JRDesignExpression exp = ExpressionUtils.createAndRegisterExpression(design, "valueAxisRangeMinValue_" + name, valueAxisRangeMinValueExp);
			linePlot.setRangeAxisMinValueExpression(exp);
		}
		CustomExpression valueAxisRangeMaxValueExp = getValueAxisFormat().getRangeMaxValueExpression();
		if (valueAxisRangeMaxValueExp != null) {
			JRDesignExpression exp = ExpressionUtils.createAndRegisterExpression(design, "valueAxisRangeMaxValue_" + name, valueAxisRangeMaxValueExp);
			linePlot.setRangeAxisMaxValueExpression(exp);
		}
		
		if (showShapes != null)
			linePlot.setShowShapes(showShapes);
		if (showLines != null)
			linePlot.setShowLines(showLines);
	}
}
