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

package ar.com.fdvs.dj.domain.chart.builder;

import ar.com.fdvs.dj.domain.DJHyperLink;
import ar.com.fdvs.dj.domain.StringExpression;
import ar.com.fdvs.dj.domain.chart.DJChart;
import ar.com.fdvs.dj.domain.chart.dataset.PieDataset;
import ar.com.fdvs.dj.domain.chart.plot.PiePlot;
import ar.com.fdvs.dj.domain.constants.Font;
import ar.com.fdvs.dj.domain.entities.columns.AbstractColumn;
import ar.com.fdvs.dj.domain.entities.columns.PropertyColumn;
import ar.com.fdvs.dj.domain.hyperlink.LiteralExpression;

import java.awt.*;
import java.util.List;

public class DJPieChartBuilder extends AbstractChartBuilder {
	//chart
	/**
	 * Sets the chart data operation (DJChart.CALCULATION_COUNT or DJChart.CALCULATION_SUM).
	 *
	 * @param operation the chart data operation
	 **/
	public DJPieChartBuilder setOperation(byte operation) {
		this.chart.setOperation(operation);
		return this;
	}
	
	/**
	 * Sets the hyperlink.
	 *
	 * @param link the hyperlink
	 **/
	public DJPieChartBuilder setLink(DJHyperLink link) {
		this.chart.setLink(link);
		return this;
	}
	
	//chart options
	/**
	 * Sets the background color.
	 *
	 * @param backColor the background color
	 **/
	public DJPieChartBuilder setBackColor(Color backColor) {
		this.chart.getOptions().setBackColor(backColor);
		return this;
	}

	/**
	 * Sets the chart height.
	 *
	 * @param height the chart height
	 **/
	public DJPieChartBuilder setHeight(int height) {
		this.chart.getOptions().setHeight(height);
		return this;
	}

	/**
	 * Sets the chart width.
	 *
	 * @param width the chart width
	 **/
	public DJPieChartBuilder setWidth(int width) {
		this.chart.getOptions().setWidth(width);
		return this;
	}

	/**
	 * Sets the centered.
	 *
	 * @param centered the centered
	 **/
	public DJPieChartBuilder setCentered(boolean centered) {
		this.chart.getOptions().setCentered(centered);
		return this;
	}

	/**
	 * Sets the position (DJChartOptions.POSITION_FOOTER or DJChartOptions.POSITION_HEADER).
	 *
	 * @param position the position
	 **/
	public DJPieChartBuilder setPosition(byte position) {
		this.chart.getOptions().setPosition(position);
		return this;
	}

	/**
	 * Sets the y position.
	 *
	 * @param y the y position
	 **/
	public DJPieChartBuilder setY(int y) {
		this.chart.getOptions().setY(y);
		return this;
	}

	/**
	 * Sets the x position.
	 *
	 * @param y the x position
	 **/
	public DJPieChartBuilder setX(int x) {
		this.chart.getOptions().setX(x);
		return this;
	}

	/**
	 * Sets the legend visibility.
	 *
	 * @param showLegend the legend visibility
	 **/
	public DJPieChartBuilder setShowLegend(boolean showLegend) {
		this.chart.getOptions().setShowLegend(new Boolean(showLegend));
		return this;
	}

	/**
	 * Sets the title color.
	 *
	 * @param titleColor the title color
	 **/
	public DJPieChartBuilder setTitleColor(Color titleColor) {
		this.chart.getOptions().setTitleColor(titleColor);
		return this;
	}

	/**
	 * Sets the subtitle color.
	 *
	 * @param subtitleColor the subtitle color
	 **/
	public DJPieChartBuilder setSubtitleColor(Color subtitleColor) {
		this.chart.getOptions().setSubtitleColor(subtitleColor);
		return this;
	}

	/**
	 * Sets the legend color.
	 *
	 * @param legendColor the legend color
	 **/
	public DJPieChartBuilder setLegendColor(Color legendColor) {
		this.chart.getOptions().setLegendColor(legendColor);
		return this;
	}

	/**
	 * Sets the legend background color.
	 *
	 * @param legendBackgroundColor the legend background color
	 **/
	public DJPieChartBuilder setLegendBackgroundColor(Color legendBackgroundColor) {
		this.chart.getOptions().setLegendBackgroundColor(legendBackgroundColor);
		return this;
	}

	/**
	 * Sets the theme.
	 * Chart themes support to allow changing the overall appearance of charts generated with the build-in chart element
	 *
	 * @param theme the theme
	 **/
	public DJPieChartBuilder setTheme(String theme) {
		this.chart.getOptions().setTheme(theme);
		return this;
	}

	/**
	 * Sets the title font.
	 *
	 * @param titleFont the title font
	 **/
	public DJPieChartBuilder setTitleFont(Font titleFont) {
		this.chart.getOptions().setTitleFont(titleFont);
		return this;
	}

	/**
	 * Sets the subtitle font.
	 *
	 * @param subtitleFont the subtitle font
	 **/
	public DJPieChartBuilder setSubtitleFont(Font subtitleFont) {
		this.chart.getOptions().setSubtitleFont(subtitleFont);
		return this;
	}

	/**
	 * Sets the legend font.
	 *
	 * @param legendFont the legend font
	 **/
	public DJPieChartBuilder setLegendFont(Font legendFont) {
		this.chart.getOptions().setLegendFont(legendFont);
		return this;
	}

	/**
	 * Sets the legend position (DJChartOptions.EDGE_TOP, DJChartOptions.EDGE_BOTTOM, DJChartOptions.EDGE_LEFT, DJChartOptions.EDGE_RIGHT).
	 *
	 * @param legendPosition the legend position
	 **/
	public DJPieChartBuilder setLegendPosition(byte legendPosition) {
		this.chart.getOptions().setLegendPosition(legendPosition);
		return this;
	}

	/**
	 * Sets the title position (DJChartOptions.EDGE_TOP, DJChartOptions.EDGE_BOTTOM, DJChartOptions.EDGE_LEFT, DJChartOptions.EDGE_RIGHT).
	 *
	 * @param titlePosition the title position
	 **/
	public DJPieChartBuilder setTitlePosition(byte titlePosition) {
		this.chart.getOptions().setTitlePosition(titlePosition);
		return this;
	}

	/**
	 * Sets the title.
	 *
	 * @param title the title
	 **/
	public DJPieChartBuilder setTitle(String title) {
		this.chart.getOptions().setTitleExpression(new LiteralExpression(title));
		return this;
	}

	/**
	 * Sets the title expression.
	 *
	 * @param titleExpression the title expression
	 **/
	public DJPieChartBuilder setTitle(StringExpression titleExpression) {
		this.chart.getOptions().setTitleExpression(titleExpression);
		return this;
	}

	/**
	 * Sets the subtitle.
	 *
	 * @param subtitle the subtitle
	 **/
	public DJPieChartBuilder setSubtitle(String subtitle) {
		this.chart.getOptions().setSubtitleExpression(new LiteralExpression(subtitle));
		return this;
	}

	/**
	 * Sets the subtitle expression.
	 *
	 * @param subtitleExpression the subtitle expression
	 **/
	public DJPieChartBuilder setSubtitle(StringExpression subtitleExpression) {
		this.chart.getOptions().setSubtitleExpression(subtitleExpression);
		return this;
	}

	/**
	 * Sets the line style (DJChartOptions.LINE_STYLE_SOLID, DJChartOptions.LINE_STYLE_DASHED, DJChartOptions.LINE_STYLE_DOTTED, DJChartOptions.LINE_STYLE_DOUBLE).
	 * 
	 * @param lineStyle one of the line style constants in DJChartOptions class
	 */
	public DJPieChartBuilder setLineStyle(byte lineStyle) {
		this.chart.getOptions().setLineStyle(lineStyle);
		return this;
	}

	/**
	 * Sets the line width.
	 *
	 * @param lineWidth the line width
	 **/
	public DJPieChartBuilder setLineWidth(float lineWidth) {
		this.chart.getOptions().setLineWidth(new Float(lineWidth));
		return this;
	}

	/**
	 * Sets the line color.
	 *
	 * @param lineColor the line color
	 **/
	public DJPieChartBuilder setLineColor(Color lineColor) {
		this.chart.getOptions().setLineColor(lineColor);
		return this;
	}

	/**
	 * Sets the padding.
	 *
	 * @param padding the padding
	 **/
	public DJPieChartBuilder setPadding(int padding) {
		this.chart.getOptions().setPadding(new Integer(padding));
		return this;
	}

	/**
	 * Sets a user specified chart customizer class name.
	 * @see net.sf.jasperreports.engine.JRChartCustomizer
 	 */
	public DJPieChartBuilder setCustomizerClass(String customizerClass) {
		this.chart.getOptions().setCustomizerClass(customizerClass);
		return this;
	}
	
	//dataset
	/** 
	 * Allows AbstractChartBuilder to set the key column
	 */
	protected DJPieChartBuilder setCategory(PropertyColumn key) {
		setKey(key);
		return this;
	}
	
	/**
	 * Sets the key column.
	 *
	 * @param key the key column
	 **/
	public DJPieChartBuilder setKey(PropertyColumn key) {
		getDataset().setKey(key);
		return this;
	}

	/**
	 * Adds the specified serie column to the dataset.
	 * 
	 * @param column the serie column
	 **/
	public DJPieChartBuilder addSerie(AbstractColumn column) {
		getDataset().addSerie(column);
		return this;
	}
	
	//plot	
	/**
	 * Adds the specified series color to the plot.
	 * 
	 * @param color the series color
	 **/
	public DJPieChartBuilder addSeriesColor(Color color) {
		getPlot().addSeriesColor(color);
		return this;
	}

	/**
	 * Set the specified series colors to the plot.
	 * 
	 * @param seriesColors the series colors
	 **/
	public DJPieChartBuilder setSeriesColors(List seriesColors) {
		getPlot().setSeriesColors(seriesColors);
		return this;
	}
	
	//pie plot
	/**
	 * Sets the circular.
	 * 
	 * @param circular the circular
	 **/
	public DJPieChartBuilder setCircular(boolean circular) {
		getPlot().setCircular(new Boolean(circular));
		return this;
	}

	/**
	 * Sets the label format.
	 * 
	 * @param labelFormat the label format
	 **/
	public DJPieChartBuilder setLabelFormat(String labelFormat) {
		getPlot().setLabelFormat(labelFormat);
		return this;
	}
	
	/**
	 * Sets the legend label format.
	 * 
	 * @param legendLabelFormat the legend label format
	 **/
	public DJPieChartBuilder setLegendLabelFormat(String legendLabelFormat) {
		getPlot().setLegendLabelFormat(legendLabelFormat);
		return this;
	}
	
	protected PieDataset getDataset() {
		return (PieDataset) chart.getDataset();
	}
	
	protected PiePlot getPlot() {
		return (PiePlot) chart.getPlot();
	}
	
	protected byte getChartType() {
		return DJChart.PIE_CHART;
	}
}
