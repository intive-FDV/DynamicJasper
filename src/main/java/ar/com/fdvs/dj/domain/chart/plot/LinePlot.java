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

import net.sf.jasperreports.charts.design.JRDesignLinePlot;
import net.sf.jasperreports.engine.JRChartPlot;
import net.sf.jasperreports.engine.design.JRDesignExpression;
import net.sf.jasperreports.engine.design.JRDesignGroup;
import ar.com.fdvs.dj.domain.CustomExpression;
import ar.com.fdvs.dj.domain.DynamicJasperDesign;
import ar.com.fdvs.dj.domain.StringExpression;
import ar.com.fdvs.dj.domain.entities.Entity;
import ar.com.fdvs.dj.util.ExpressionUtils;

import java.util.Map;

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

	public void transform(JRChartPlot plot, DynamicJasperDesign design, String name, JRDesignGroup group, JRDesignGroup parentGroup, Map vars, int width) {
		super.transform(plot, design, name, group, parentGroup, vars, width);
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
