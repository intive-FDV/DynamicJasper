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

package ar.com.fdvs.dj.domain.chart;

import java.util.Map;

import net.sf.jasperreports.engine.JRDefaultStyleProvider;
import net.sf.jasperreports.engine.JRExpression;
import net.sf.jasperreports.engine.JRRuntimeException;
import net.sf.jasperreports.engine.design.JRDesignChart;
import net.sf.jasperreports.engine.design.JRDesignChartDataset;
import net.sf.jasperreports.engine.design.JRDesignGroup;
import net.sf.jasperreports.engine.design.JRDesignStyle;
import net.sf.jasperreports.engine.design.JRDesignVariable;
import ar.com.fdvs.dj.domain.DJBaseElement;
import ar.com.fdvs.dj.domain.DJHyperLink;
import ar.com.fdvs.dj.domain.DynamicJasperDesign;
import ar.com.fdvs.dj.domain.chart.dataset.AbstractDataset;
import ar.com.fdvs.dj.domain.chart.dataset.CategoryDataset;
import ar.com.fdvs.dj.domain.chart.dataset.PieDataset;
import ar.com.fdvs.dj.domain.chart.dataset.TimeSeriesDataset;
import ar.com.fdvs.dj.domain.chart.dataset.XYDataset;
import ar.com.fdvs.dj.domain.chart.plot.AbstractPlot;
import ar.com.fdvs.dj.domain.chart.plot.AreaPlot;
import ar.com.fdvs.dj.domain.chart.plot.Bar3DPlot;
import ar.com.fdvs.dj.domain.chart.plot.BarPlot;
import ar.com.fdvs.dj.domain.chart.plot.LinePlot;
import ar.com.fdvs.dj.domain.chart.plot.Pie3DPlot;
import ar.com.fdvs.dj.domain.chart.plot.PiePlot;
import ar.com.fdvs.dj.domain.chart.plot.ScatterPlot;
import ar.com.fdvs.dj.domain.chart.plot.TimeSeriesPlot;
import ar.com.fdvs.dj.domain.entities.Entity;
import net.sf.jasperreports.engine.type.CalculationEnum;
import net.sf.jasperreports.engine.type.EvaluationTimeEnum;
import net.sf.jasperreports.engine.util.StyleResolver;

public class DJChart extends DJBaseElement {
	
	private static final long serialVersionUID = Entity.SERIAL_VERSION_UID;
	
	public static final byte CALCULATION_COUNT = CalculationEnum.COUNT.getValue();
	public static final byte CALCULATION_SUM = CalculationEnum.SUM.getValue();

	public static final byte AREA_CHART = JRDesignChart.CHART_TYPE_AREA;
	public static final byte STACKEDAREA_CHART = JRDesignChart.CHART_TYPE_STACKEDAREA;
	public static final byte BAR_CHART = JRDesignChart.CHART_TYPE_BAR;
	public static final byte BAR3D_CHART = JRDesignChart.CHART_TYPE_BAR3D;
	public static final byte STACKEDBAR_CHART = JRDesignChart.CHART_TYPE_STACKEDBAR;
	public static final byte STACKEDBAR3D_CHART = JRDesignChart.CHART_TYPE_STACKEDBAR3D;
	public static final byte LINE_CHART = JRDesignChart.CHART_TYPE_LINE;
	public static final byte PIE_CHART = JRDesignChart.CHART_TYPE_PIE;
	public static final byte PIE3D_CHART = JRDesignChart.CHART_TYPE_PIE3D;
	public static final byte TIMESERIES_CHART = JRDesignChart.CHART_TYPE_TIMESERIES;
	public static final byte XYAREA_CHART = JRDesignChart.CHART_TYPE_XYAREA;
	public static final byte XYBAR_CHART = JRDesignChart.CHART_TYPE_XYBAR;
	public static final byte XYLINE_CHART = JRDesignChart.CHART_TYPE_XYLINE;
	public static final byte SCATTER_CHART = JRDesignChart.CHART_TYPE_SCATTER;
	
	private byte chartType;
	private AbstractDataset dataset;
	private AbstractPlot plot;
	private byte operation = CalculationEnum.SUM.getValue();
	private DJChartOptions chartOptions = new DJChartOptions();
	private DJHyperLink link;
	
	public DJChart(byte chartType) {
		setChartType(chartType);
	}

	private void setChartType(byte chartType)	{
		this.chartType = chartType;
		
		switch(chartType) {
			case AREA_CHART:
			case STACKEDAREA_CHART:
				dataset = new CategoryDataset();
				plot = new AreaPlot();
				break;
			case BAR_CHART:
			case STACKEDBAR_CHART:
				dataset = new CategoryDataset();
				plot = new BarPlot();
				break;
			case BAR3D_CHART:
			case STACKEDBAR3D_CHART:
				dataset = new CategoryDataset();
				plot = new Bar3DPlot();
				break;
			case LINE_CHART:
				dataset = new CategoryDataset();
				plot = new LinePlot();
				break;
			case PIE_CHART:
				dataset = new PieDataset();
				plot = new PiePlot();
				break;
			case PIE3D_CHART:
				dataset = new PieDataset();
				plot = new Pie3DPlot();
				break;
			case TIMESERIES_CHART:
				dataset = new TimeSeriesDataset();
				plot = new TimeSeriesPlot();
				break;
			case XYAREA_CHART:
				dataset = new XYDataset();
				plot = new AreaPlot();
				break;
			case XYBAR_CHART:
				dataset = new XYDataset();
				plot = new BarPlot();
				break;
			case XYLINE_CHART:
				dataset = new XYDataset();
				plot = new LinePlot();
				break;
			case SCATTER_CHART:
				dataset = new XYDataset();
				plot = new ScatterPlot();
				break;
			default:
				throw new JRRuntimeException("Chart type not supported.");
		}
	}
	
	/**
	 * Sets the chart data operation (DJChart.CALCULATION_COUNT or DJChart.CALCULATION_SUM).
	 *
	 * @param operation the chart data operation
	 **/
	public void setOperation(byte operation) {
		this.operation = operation;
	}

	/**
	 * Returns the chart data operation (DJChart.CALCULATION_COUNT or DJChart.CALCULATION_SUM).
	 *
	 * @return	the chart data operation
	 **/
	public byte getOperation() {
		return operation;
	}

	/**
	 * Sets the chart options.
	 *
	 * @param chartOptions the chart options
	 **/
	public void setOptions(DJChartOptions chartOptions) {
		this.chartOptions = chartOptions;
	}

	/**
	 * Returns the chart options.
	 *
	 * @return	the chart options
	 **/
	public DJChartOptions getOptions() {
		return chartOptions;
	}
	
	/**
	 * Returns the chart dataset.
	 *
	 * @return	the chart dataset
	 **/
	public AbstractDataset getDataset() {
		return dataset;
	}
	
	/**
	 * Returns the chart plot.
	 *
	 * @return	the chart plot
	 **/
	public AbstractPlot getPlot() {
		return plot;
	}
	
	/**
	 * Returns the hyperlink.
	 *
	 * @return	the hyperlink
	 **/
	public DJHyperLink getLink() {
		return link;
	}

	/**
	 * Sets the hyperlink.
	 *
	 * @param link the hyperlink
	 **/
	public void setLink(DJHyperLink link) {
		this.link = link;
	}
	
	public JRDesignChart transform(DynamicJasperDesign design, String name, JRDesignGroup group, JRDesignGroup parentGroup, Map vars, int width) {
		JRDesignChart chart = new JRDesignChart(design, chartType);
		JRDesignChartDataset chartDataset = dataset.transform(design, name, group, parentGroup, vars);
		chart.setDataset(chartDataset);
		plot.transform(design, chart.getPlot(), name);
		getOptions().transform(design, name, chart, width);

		if (group.equals(parentGroup))
			chart.setEvaluationTime( EvaluationTimeEnum.REPORT );
		else {
			chart.setEvaluationTime( EvaluationTimeEnum.GROUP );
			chart.setEvaluationGroup(parentGroup);
		}
		return chart;	
	}
}
