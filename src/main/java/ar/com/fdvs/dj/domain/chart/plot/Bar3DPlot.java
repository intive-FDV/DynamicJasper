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

import net.sf.jasperreports.charts.design.JRDesignBar3DPlot;
import net.sf.jasperreports.engine.JRChartPlot;
import net.sf.jasperreports.engine.design.JRDesignExpression;
import ar.com.fdvs.dj.domain.CustomExpression;
import ar.com.fdvs.dj.domain.DynamicJasperDesign;
import ar.com.fdvs.dj.domain.StringExpression;
import ar.com.fdvs.dj.domain.entities.Entity;
import ar.com.fdvs.dj.util.ExpressionUtils;

public class Bar3DPlot extends AbstractCategoryAxisPlot {
	private static final long serialVersionUID = Entity.SERIAL_VERSION_UID;
	private Double xOffset = null;
	private Double yOffset = null;
	private Boolean showLabels = null;

	/**
	 * Sets the x offset.
	 *
	 * @param xOffset the x offset
	 **/
	public void setXOffset(Double xOffset) {
		this.xOffset = xOffset;
	}

	/**
	 * Returns the x offset.
	 *
	 * @return	the x offset
	 **/
	public Double getXOffset() {
		return xOffset;
	}

	/**
	 * Sets the y offset.
	 *
	 * @param yOffset the y offset
	 **/
	public void setYOffset(Double yOffset) {
		this.yOffset = yOffset;
	}

	/**
	 * Returns the y offset.
	 *
	 * @return	the y offset
	 **/
	public Double getYOffset() {
		return yOffset;
	}
	
	/**
	 * Sets the labels visibility.
	 *
	 * @param showLabels the labels visibility
	 **/
	public void setShowLabels(Boolean showLabels) {
		this.showLabels = showLabels;
	}

	/**
	 * Returns the labels visibility.
	 *
	 * @return	the labels visibility
	 **/
	public Boolean getShowLabels() {
		return showLabels;
	}
	
	public void transform(DynamicJasperDesign design, JRChartPlot plot, String name) {
		super.transform(design, plot, name);
		JRDesignBar3DPlot barPlot = (JRDesignBar3DPlot) plot;

		StringExpression categoryAxisLabelExp = getCategoryAxisFormat().getLabelExpression();
		if (categoryAxisLabelExp != null) {
			JRDesignExpression exp = ExpressionUtils.createAndRegisterExpression(design, "categoryAxisLabel_" + name, categoryAxisLabelExp);
			barPlot.setCategoryAxisLabelExpression(exp);
		}
		if (getCategoryAxisFormat().getTickLabelMask() != null) 
			barPlot.setCategoryAxisTickLabelMask(getCategoryAxisFormat().getTickLabelMask());	
		if (getCategoryAxisFormat().getLabelColor() != null)
			barPlot.setCategoryAxisLabelColor(getCategoryAxisFormat().getLabelColor());
		if (getCategoryAxisFormat().getLabelFont() != null)
			barPlot.setCategoryAxisLabelFont(getCategoryAxisFormat().getLabelFont().transform());
		if (getCategoryAxisFormat().getLineColor() != null)
			barPlot.setCategoryAxisLineColor(getCategoryAxisFormat().getLineColor());		
		if (getCategoryAxisFormat().getTickLabelColor() != null)
			barPlot.setCategoryAxisTickLabelColor(getCategoryAxisFormat().getTickLabelColor());
		if (getCategoryAxisFormat().getTickLabelFont() != null)
			barPlot.setCategoryAxisTickLabelFont(getCategoryAxisFormat().getTickLabelFont().transform());
		CustomExpression categoryAxisRangeMinValueExp = getCategoryAxisFormat().getRangeMinValueExpression();
		if (categoryAxisRangeMinValueExp != null) {
			JRDesignExpression exp = ExpressionUtils.createAndRegisterExpression(design, "categoryAxisRangeMinValue_" + name, categoryAxisRangeMinValueExp);
			barPlot.setDomainAxisMinValueExpression(exp);
		}
		CustomExpression categoryAxisRangeMaxValueExp = getCategoryAxisFormat().getRangeMaxValueExpression();
		if (categoryAxisRangeMaxValueExp != null) {
			JRDesignExpression exp = ExpressionUtils.createAndRegisterExpression(design, "categoryAxisRangeMaxValue_" + name, categoryAxisRangeMaxValueExp);
			barPlot.setDomainAxisMaxValueExpression(exp);
		}
		
		StringExpression valueAxisLabelExp = getValueAxisFormat().getLabelExpression();
		if (valueAxisLabelExp != null) {
			JRDesignExpression exp = ExpressionUtils.createAndRegisterExpression(design, "valueAxisLabel_" + name, valueAxisLabelExp);
			barPlot.setValueAxisLabelExpression(exp);
		}
		if (getValueAxisFormat().getTickLabelMask() != null) 
			barPlot.setValueAxisTickLabelMask(getValueAxisFormat().getTickLabelMask());	
		if (getValueAxisFormat().getLabelColor() != null)
			barPlot.setValueAxisLabelColor(getValueAxisFormat().getLabelColor());
		if (getValueAxisFormat().getLabelFont() != null)
			barPlot.setValueAxisLabelFont(getValueAxisFormat().getLabelFont().transform());
		if (getValueAxisFormat().getLineColor() != null)
			barPlot.setValueAxisLineColor(getValueAxisFormat().getLineColor());		
		if (getValueAxisFormat().getTickLabelColor() != null)
			barPlot.setValueAxisTickLabelColor(getValueAxisFormat().getTickLabelColor());
		if (getValueAxisFormat().getTickLabelFont() != null)
			barPlot.setValueAxisTickLabelFont(getValueAxisFormat().getTickLabelFont().transform());
		CustomExpression valueAxisRangeMinValueExp = getValueAxisFormat().getRangeMinValueExpression();
		if (valueAxisRangeMinValueExp != null) {
			JRDesignExpression exp = ExpressionUtils.createAndRegisterExpression(design, "valueAxisRangeMinValue_" + name, valueAxisRangeMinValueExp);
			barPlot.setRangeAxisMinValueExpression(exp);
		}
		CustomExpression valueAxisRangeMaxValueExp = getValueAxisFormat().getRangeMaxValueExpression();
		if (valueAxisRangeMaxValueExp != null) {
			JRDesignExpression exp = ExpressionUtils.createAndRegisterExpression(design, "valueAxisRangeMaxValue_" + name, valueAxisRangeMaxValueExp);
			barPlot.setRangeAxisMaxValueExpression(exp);
		}
		
		if (showLabels != null)
			barPlot.setShowLabels(showLabels);
		if (xOffset != null)
			barPlot.setXOffset(xOffset);
		if (yOffset != null)
			barPlot.setYOffset(yOffset);
	}
}
