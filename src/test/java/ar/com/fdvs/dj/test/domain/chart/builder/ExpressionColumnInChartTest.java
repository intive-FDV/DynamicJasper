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

package ar.com.fdvs.dj.test.domain.chart.builder;

import ar.com.fdvs.dj.domain.*;
import ar.com.fdvs.dj.domain.builders.ColumnBuilder;
import ar.com.fdvs.dj.domain.builders.DynamicReportBuilder;
import ar.com.fdvs.dj.domain.chart.DJChart;
import ar.com.fdvs.dj.domain.chart.DJChartOptions;
import ar.com.fdvs.dj.domain.chart.builder.DJScatterChartBuilder;
import ar.com.fdvs.dj.domain.chart.plot.DJAxisFormat;
import ar.com.fdvs.dj.domain.constants.*;
import ar.com.fdvs.dj.domain.constants.Font;
import ar.com.fdvs.dj.domain.constants.Transparency;
import ar.com.fdvs.dj.domain.entities.columns.AbstractColumn;
import ar.com.fdvs.dj.domain.entities.columns.PropertyColumn;
import ar.com.fdvs.dj.domain.hyperlink.LiteralExpression;
import ar.com.fdvs.dj.test.BaseDjReportTest;
import net.sf.jasperreports.charts.design.JRDesignScatterPlot;
import net.sf.jasperreports.charts.design.JRDesignXyDataset;
import net.sf.jasperreports.engine.JRFont;
import net.sf.jasperreports.engine.design.JRDesignChart;
import net.sf.jasperreports.engine.design.JRDesignGroup;
import net.sf.jasperreports.engine.design.JRDesignVariable;
import net.sf.jasperreports.engine.type.LineStyleEnum;
import net.sf.jasperreports.view.JasperViewer;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

/**
 * This example demonstrates that you can make a chart using an Expression column
 */
public class ExpressionColumnInChartTest extends BaseDjReportTest {
	private DynamicReportBuilder drb;
	private JRDesignChart chart;
	
	protected void setUp() throws Exception {
		super.setUp();
		drb = new DynamicReportBuilder();

		Style headerStyle = new Style();
		headerStyle.setFont(Font.VERDANA_MEDIUM_BOLD);
		headerStyle.setBorderBottom(Border.PEN_2_POINT());
		headerStyle.setHorizontalAlign(HorizontalAlign.CENTER);
		headerStyle.setVerticalAlign(VerticalAlign.MIDDLE);
		headerStyle.setBackgroundColor(Color.DARK_GRAY);
		headerStyle.setTextColor(Color.WHITE);
		headerStyle.setTransparency(Transparency.OPAQUE);
		drb.setDefaultStyles(null, null, headerStyle, null);			
		
		AbstractColumn columnCode = ColumnBuilder.getNew()
		.setColumnProperty("id", Long.class.getName()).setTitle("ID")
		.setWidth(new Integer(40)).build();
		AbstractColumn columnaQuantity = ColumnBuilder.getNew()
		.setColumnProperty("quantity", Long.class.getName()).setTitle(
				"Quantity").setWidth(new Integer(80)).build();

        AbstractColumn columnAmount = ColumnBuilder.getNew()
                .setTitle("Amount")
                .setCustomExpression(new CustomExpression() {
                    public Object evaluate(Map fields, Map variables, Map parameters) {
                        return ((Float)fields.get("amount")) * 321;
                    }

                    public String getClassName() {
                        return Float.class.getName();
                    }
                })
                .setWidth(new Integer(90)).build();

        drb.addField("amount", Float.class.getName());

		drb.addColumn(columnCode);
		drb.addColumn(columnaQuantity);
		drb.addColumn(columnAmount);
		
		drb.setUseFullPageWidth(true);
		
		DJAxisFormat xAxisFormat = new DJAxisFormat("x");
		xAxisFormat.setLabelFont(Font.ARIAL_SMALL);
		xAxisFormat.setLabelColor(Color.DARK_GRAY);
		xAxisFormat.setTickLabelFont(Font.ARIAL_SMALL);
		xAxisFormat.setTickLabelColor(Color.DARK_GRAY);
		xAxisFormat.setTickLabelMask("#,###.#");
		xAxisFormat.setLineColor(Color.DARK_GRAY);
		
		DJAxisFormat yAxisFormat = new DJAxisFormat("y");
		yAxisFormat.setLabelFont(Font.ARIAL_SMALL);
		yAxisFormat.setLabelColor(Color.DARK_GRAY);
		yAxisFormat.setTickLabelFont(Font.ARIAL_SMALL);
		yAxisFormat.setTickLabelColor(Color.DARK_GRAY);
		yAxisFormat.setTickLabelMask("#,##0.0");
		yAxisFormat.setLineColor(Color.DARK_GRAY);
		
		DJChart djChart = new DJScatterChartBuilder()
		//chart		
		.setX(20)
		.setY(10)
		.setWidth(500)
		.setHeight(250)
		.setCentered(false)
		.setBackColor(Color.LIGHT_GRAY)
		.setShowLegend(true)
		.setPosition(DJChartOptions.POSITION_FOOTER)
		.setTitle("title")
		.setTitleColor(Color.DARK_GRAY)
		.setTitleFont(Font.ARIAL_BIG_BOLD)
		.setSubtitle("subtitle")
		.setSubtitleColor(Color.DARK_GRAY)
		.setSubtitleFont(Font.COURIER_NEW_BIG_BOLD)
		.setLegendColor(Color.DARK_GRAY)
		.setLegendFont(Font.COURIER_NEW_MEDIUM_BOLD)
		.setLegendBackgroundColor(Color.WHITE)
		.setLegendPosition(DJChartOptions.EDGE_BOTTOM)
		.setTitlePosition(DJChartOptions.EDGE_TOP)
		.setLineStyle(DJChartOptions.LINE_STYLE_DOTTED)
		.setLineWidth(1)
		.setLineColor(Color.DARK_GRAY)
		.setPadding(5)
		//dataset
		.setXValue((PropertyColumn) columnCode)
		.addSerie(columnaQuantity, "quant.")
		.addSerie(columnAmount)
		//plot
		.setShowShapes(true)
		.setShowLines(false)
		.setXAxisFormat(xAxisFormat)
		.setYAxisFormat(yAxisFormat)
		.build();
		drb.addChart(djChart);
		
		DJHyperLink djlink = new DJHyperLink();
		djlink.setExpression(new StringExpression() {
			public Object evaluate(Map fields, Map variables, Map parameters) {				
				return "http://thisIsAURL?count=" + variables.get("REPORT_COUNT");
			}
		});
		djlink.setTooltip(new LiteralExpression("I'm a literal tootltip"));
		djChart.setLink(djlink);
		
		Map<AbstractColumn, JRDesignVariable> vars = new HashMap<AbstractColumn, JRDesignVariable>();
		vars.put(columnaQuantity, new JRDesignVariable());
		vars.put(columnAmount, new JRDesignVariable());
		JRDesignGroup group = new JRDesignGroup();
		chart = djChart.transform(new DynamicJasperDesign(), "", group, group, vars, 0);
	}
	
	public void testChart() {
		assertEquals(20, chart.getX());
		assertEquals(10, chart.getY());
		assertEquals(500, chart.getWidth());
		assertEquals(250, chart.getHeight());
		assertEquals(Color.LIGHT_GRAY, chart.getBackcolor());
		assertEquals(Boolean.TRUE, chart.getShowLegend());
		assertNotNull(chart.getTitleExpression().getText());
		assertEquals(Color.DARK_GRAY, chart.getTitleColor());
		testFont(Font.ARIAL_BIG_BOLD, chart.getTitleFont());
		assertNotNull(chart.getSubtitleExpression().getText());
		assertEquals(Color.DARK_GRAY, chart.getSubtitleColor());
		testFont(Font.COURIER_NEW_BIG_BOLD, chart.getSubtitleFont());
		assertEquals(Color.DARK_GRAY, chart.getLegendColor());
		testFont(Font.COURIER_NEW_MEDIUM_BOLD, chart.getLegendFont());
		assertEquals(Color.WHITE, chart.getLegendBackgroundColor());
		assertEquals(new Byte(DJChartOptions.EDGE_BOTTOM), chart.getLegendPositionValue().getValueByte());
		assertEquals(new Byte(DJChartOptions.EDGE_TOP), chart.getTitlePositionValue().getValueByte());
		assertEquals(LineStyleEnum.getByValue(new Byte(DJChartOptions.LINE_STYLE_DOTTED)), chart.getLineBox().getPen().getLineStyleValue());
		assertEquals(1f, chart.getLineBox().getPen().getLineWidth());
		assertEquals(Color.DARK_GRAY, chart.getLineBox().getPen().getLineColor());
		assertEquals(new Integer(5), chart.getLineBox().getPadding());
	}
	
	public void testDataset() {
		JRDesignXyDataset dataset = (JRDesignXyDataset) chart.getDataset();
		assertEquals(2, dataset.getSeriesList().size());
		assertNotNull(dataset.getSeries()[0].getLabelExpression().getText());
		assertNotNull(dataset.getSeries()[0].getSeriesExpression().getText());
	}
	
	public void testPlot() {
		JRDesignScatterPlot plot = (JRDesignScatterPlot) chart.getPlot();
		assertEquals(Boolean.TRUE, plot.getShowShapes());
		assertEquals(Boolean.FALSE, plot.getShowLines());
		
		assertNotNull(plot.getXAxisLabelExpression().getText());
		testFont(Font.ARIAL_SMALL, plot.getXAxisLabelFont());
		assertEquals(Color.DARK_GRAY, plot.getXAxisLabelColor());		
		testFont(Font.ARIAL_SMALL, plot.getXAxisTickLabelFont());
		assertEquals(Color.DARK_GRAY, plot.getXAxisTickLabelColor());
		assertEquals("#,###.#", plot.getXAxisTickLabelMask());
		assertEquals(Color.DARK_GRAY, plot.getXAxisLineColor());
		
		assertNotNull(plot.getYAxisLabelExpression().getText());
		testFont(Font.ARIAL_SMALL, plot.getYAxisLabelFont());
		assertEquals(Color.DARK_GRAY, plot.getYAxisLabelColor());		
		testFont(Font.ARIAL_SMALL, plot.getYAxisTickLabelFont());
		assertEquals(Color.DARK_GRAY, plot.getYAxisTickLabelColor());
		assertEquals("#,##0.0", plot.getYAxisTickLabelMask());
		assertEquals(Color.DARK_GRAY, plot.getYAxisLineColor());
	}
	
	public DynamicReport buildReport() throws Exception {
		return drb.build();
	}

	private void testFont(Font djFont, JRFont jrFont) {
		assertEquals(djFont.getFontName(), jrFont.getFontName());
		assertEquals(djFont.getFontSize(), jrFont.getFontsize());
		assertEquals(djFont.isBold(), jrFont.isBold());
		assertEquals(djFont.isItalic(), jrFont.isItalic());
	}
	
	public static void main(String[] args) throws Exception {
		ExpressionColumnInChartTest test = new ExpressionColumnInChartTest();
		test.setUp();
		test.testReport();
		JasperViewer.viewReport(test.jp);
	}
}
