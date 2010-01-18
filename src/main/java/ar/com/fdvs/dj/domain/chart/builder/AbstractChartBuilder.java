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

import java.awt.Color;
import java.util.List;

import ar.com.fdvs.dj.domain.DJHyperLink;
import ar.com.fdvs.dj.domain.StringExpression;
import ar.com.fdvs.dj.domain.builders.ChartBuilderException;
import ar.com.fdvs.dj.domain.chart.DJChart;
import ar.com.fdvs.dj.domain.chart.DJChartOptions;
import ar.com.fdvs.dj.domain.chart.dataset.AbstractDataset;
import ar.com.fdvs.dj.domain.chart.dataset.CategoryDataset;
import ar.com.fdvs.dj.domain.chart.plot.AbstractPlot;
import ar.com.fdvs.dj.domain.chart.plot.BarPlot;
import ar.com.fdvs.dj.domain.constants.Font;
import ar.com.fdvs.dj.domain.entities.columns.AbstractColumn;
import ar.com.fdvs.dj.domain.hyperlink.LiteralExpression;

@SuppressWarnings("unchecked")
public abstract class AbstractChartBuilder<T extends AbstractChartBuilder> {
	protected DJChart chart;
	
	public AbstractChartBuilder() {
		chart = new DJChart(getChartType());
	}
	
	protected abstract byte getChartType();
	
	public DJChart build() throws ChartBuilderException {
		if (chart.getDataset().getColumnsGroup() == null) throw new ChartBuilderException("The group to which the chart is related must be specified");
		if (chart.getDataset().getColumns().isEmpty()) throw new ChartBuilderException("At least one column to which the chart is related must be specified");
		return chart;
	}
	
	/**
	 * Sets a bunch of parameters from a DJChartOptions object.
	 * This overwrites every other option included in DJChartOptions
	 * set trough the builder's setter methods.
	 *
	 * @param operation the chart data operation
	 **/
	public T setChartOptions(DJChartOptions chartOptions) {
		chart.setOptions(chartOptions);
		return (T) this;
	}
	
	//chart
	/**
	 * Sets the chart data operation (DJChart.CALCULATION_COUNT or DJChart.CALCULATION_SUM).
	 *
	 * @param operation the chart data operation
	 **/
	public abstract T setOperation(byte operation);
	
	/**
	 * Sets the hyperlink.
	 *
	 * @param link the hyperlink
	 **/
	public abstract T setLink(DJHyperLink link);
	
	//chart options
	/**
	 * Sets the background color.
	 *
	 * @param backColor the background color
	 **/
	public abstract T setBackColor(Color backColor);

	/**
	 * Sets the chart height.
	 *
	 * @param height the chart height
	 **/
	public abstract T setHeight(int height);

	/**
	 * Sets the chart width.
	 *
	 * @param width the chart width
	 **/
	public abstract T setWidth(int width);

	/**
	 * Sets the centered.
	 *
	 * @param centered the centered
	 **/
	public abstract T setCentered(boolean centered);
	
	/**
	 * Sets the position (DJChartOptions.POSITION_FOOTER or DJChartOptions.POSITION_HEADER).
	 *
	 * @param position the position
	 **/
	public abstract T setPosition(byte position);

	/**
	 * Sets the y position.
	 *
	 * @param y the y position
	 **/
	public abstract T setY(int y);

	/**
	 * Sets the x position.
	 *
	 * @param y the x position
	 **/
	public abstract T setX(int x);

	/**
	 * Sets the legend visibility.
	 *
	 * @param showLegend the legend visibility
	 **/
	public abstract T setShowLegend(boolean showLegend);

	/**
	 * Sets the title color.
	 *
	 * @param titleColor the title color
	 **/
	public abstract T setTitleColor(Color titleColor);

	/**
	 * Sets the subtitle color.
	 *
	 * @param subtitleColor the subtitle color
	 **/
	public abstract T setSubtitleColor(Color subtitleColor);

	/**
	 * Sets the legend color.
	 *
	 * @param legendColor the legend color
	 **/
	public abstract T setLegendColor(Color legendColor);
	
	/**
	 * Sets the legend background color.
	 *
	 * @param legendBackgroundColor the legend background color
	 **/
	public abstract T setLegendBackgroundColor(Color legendBackgroundColor);

	/**
	 * Sets the theme.
	 * Chart themes support to allow changing the overall appearance of charts generated with the build-in chart element
	 *
	 * @param theme the theme
	 **/
	public abstract T setTheme(String theme);

	/**
	 * Sets the title font.
	 *
	 * @param titleFont the title font
	 **/
	public abstract T setTitleFont(Font titleFont);

	/**
	 * Sets the subtitle font.
	 *
	 * @param subtitleFont the subtitle font
	 **/
	public abstract T setSubtitleFont(Font subtitleFont);
	
	/**
	 * Sets the legend font.
	 *
	 * @param legendFont the legend font
	 **/
	public abstract T setLegendFont(Font legendFont);

	/**
	 * Sets the legend position (DJChartOptions.EDGE_TOP, DJChartOptions.EDGE_BOTTOM, DJChartOptions.EDGE_LEFT, DJChartOptions.EDGE_RIGHT).
	 *
	 * @param legendPosition the legend position
	 **/
	public abstract T setLegendPosition(byte legendPosition);

	/**
	 * Sets the title position (DJChartOptions.EDGE_TOP, DJChartOptions.EDGE_BOTTOM, DJChartOptions.EDGE_LEFT, DJChartOptions.EDGE_RIGHT).
	 *
	 * @param titlePosition the title position
	 **/
	public abstract T setTitlePosition(byte titlePosition);

	/**
	 * Sets the title.
	 *
	 * @param title the title
	 **/
	public abstract T setTitle(String title);

	/**
	 * Sets the title expression.
	 *
	 * @param titleExpression the title expression
	 **/
	public abstract T setTitle(StringExpression titleExpression);

	/**
	 * Sets the subtitle.
	 *
	 * @param subtitle the subtitle
	 **/
	public abstract T setSubtitle(String subtitle);

	/**
	 * Sets the subtitle expression.
	 *
	 * @param subtitleExpression the subtitle expression
	 **/
	public abstract T setSubtitle(StringExpression subtitleExpression);

	/**
	 * Sets the line style (DJChartOptions.LINE_STYLE_SOLID, DJChartOptions.LINE_STYLE_DASHED, DJChartOptions.LINE_STYLE_DOTTED, DJChartOptions.LINE_STYLE_DOUBLE).
	 * 
	 * @param lineStyle one of the line style constants in DJChartOptions class
	 */
	public abstract T setLineStyle(byte lineStyle);

	/**
	 * Sets the line width.
	 *
	 * @param lineWidth the line width
	 **/
	public abstract T setLineWidth(float lineWidth);

	/**
	 * Sets the line color.
	 *
	 * @param lineColor the line color
	 **/
	public abstract T setLineColor(Color lineColor);

	/**
	 * Sets the padding.
	 *
	 * @param padding the padding
	 **/
	public abstract T setPadding(int padding); /*{
		this.chart.getOptions().setPadding(new Integer(padding));
		return (T) this;
	}*/

	/**
	 * Sets a user specified chart customizer class name.
	 * @see net.sf.jasperreports.engine.JRChartCustomizer
 	 */
	public abstract T setCustomizerClass(String customizerClass);	
	
	//dataset
	/**
	 * Adds the specified serie column to the dataset.
	 * 
	 * @param column the serie column
	 **/
	public abstract T addSerie(AbstractColumn column);
	
	
	//plot
	/**
	 * Adds the specified series color to the plot.
	 * 
	 * @param color the series color
	 **/
	public abstract T addSeriesColor(Color color);
	
	/**
	 * Set the specified series colors to the plot.
	 * 
	 * @param seriesColors the series colors
	 **/
	public abstract T setSeriesColors(List seriesColors);
	
	protected abstract AbstractDataset getDataset();
	
	protected abstract AbstractPlot getPlot();
	
	
}
