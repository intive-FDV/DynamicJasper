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

import net.sf.jasperreports.charts.design.JRDesignBar3DPlot;
import net.sf.jasperreports.engine.JRChartPlot;
import net.sf.jasperreports.engine.design.JRDesignExpression;
import net.sf.jasperreports.engine.design.JRDesignGroup;
import ar.com.fdvs.dj.domain.CustomExpression;
import ar.com.fdvs.dj.domain.DynamicJasperDesign;
import ar.com.fdvs.dj.domain.StringExpression;
import ar.com.fdvs.dj.domain.entities.Entity;
import ar.com.fdvs.dj.util.ExpressionUtils;

import java.util.Map;

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

	public void transform(JRChartPlot plot, DynamicJasperDesign design, String name, JRDesignGroup group, JRDesignGroup parentGroup, Map vars, int width) {
		super.transform(plot, design, name, group, parentGroup, vars, width);
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
