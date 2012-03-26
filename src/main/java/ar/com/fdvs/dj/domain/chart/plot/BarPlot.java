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

import net.sf.jasperreports.charts.design.JRDesignBarPlot;
import net.sf.jasperreports.engine.JRChartPlot;
import net.sf.jasperreports.engine.design.JRDesignExpression;
import ar.com.fdvs.dj.domain.CustomExpression;
import ar.com.fdvs.dj.domain.DynamicJasperDesign;
import ar.com.fdvs.dj.domain.StringExpression;
import ar.com.fdvs.dj.domain.entities.Entity;
import ar.com.fdvs.dj.util.ExpressionUtils;

public class BarPlot extends AbstractCategoryAxisPlot {
	private static final long serialVersionUID = Entity.SERIAL_VERSION_UID;
	private Boolean showTickMarks = null;
	private Boolean showTickLabels = null;
	private Boolean showLabels = null;

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

	/**
	 * Sets the tick labels visibility.
	 *
	 * @param showTickLabels the tick labels visibility
	 **/
	public void setShowTickLabels(Boolean showTickLabels) {
		this.showTickLabels = showTickLabels;
	}

	/**
	 * Returns the tick labels visibility.
	 *
	 * @return	the tick labels visibility
	 **/
	public Boolean getShowTickLabels() {
		return showTickLabels;
	}

	/**
	 * Sets the tick marks visibility.
	 *
	 * @param showTickMarks the tick marks visibility
	 **/
	public void setShowTickMarks(Boolean showTickMarks) {
		this.showTickMarks = showTickMarks;
	}

	/**
	 * Returns the tick marks visibility.
	 *
	 * @return	the tick marks visibility
	 **/
	public Boolean getShowTickMarks() {
		return showTickMarks;
	}
	
	public void transform(DynamicJasperDesign design, JRChartPlot plot, String name) {
		super.transform(design, plot, name);
		JRDesignBarPlot barPlot = (JRDesignBarPlot) plot;

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
		if (showTickLabels != null)
			barPlot.setShowTickLabels(showTickLabels);
		if (showTickMarks != null)
			barPlot.setShowTickMarks(showTickMarks);
	}
}
