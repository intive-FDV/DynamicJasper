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

package ar.com.fdvs.dj.test.domain.chart.builder;

import ar.com.fdvs.dj.domain.*;
import ar.com.fdvs.dj.domain.builders.ColumnBuilder;
import ar.com.fdvs.dj.domain.builders.DynamicReportBuilder;
import ar.com.fdvs.dj.domain.chart.DJChart;
import ar.com.fdvs.dj.domain.chart.DJChartOptions;
import ar.com.fdvs.dj.domain.chart.builder.DJTimeSeriesChartBuilder;
import ar.com.fdvs.dj.domain.chart.plot.DJAxisFormat;
import ar.com.fdvs.dj.domain.constants.*;
import ar.com.fdvs.dj.domain.constants.Font;
import ar.com.fdvs.dj.domain.constants.Transparency;
import ar.com.fdvs.dj.domain.entities.columns.AbstractColumn;
import ar.com.fdvs.dj.domain.entities.columns.PropertyColumn;
import ar.com.fdvs.dj.domain.hyperlink.LiteralExpression;
import ar.com.fdvs.dj.test.BaseDjReportTest;
import net.sf.jasperreports.charts.design.JRDesignTimeSeriesDataset;
import net.sf.jasperreports.charts.design.JRDesignTimeSeriesPlot;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRFont;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.charts.design.JRDesignChart;
import net.sf.jasperreports.engine.design.JRDesignGroup;
import net.sf.jasperreports.engine.design.JRDesignVariable;
import net.sf.jasperreports.engine.type.LineStyleEnum;
import net.sf.jasperreports.view.JasperViewer;
import org.jfree.data.time.Month;

import java.awt.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

public class TimeSeriesChartBuilderTest extends BaseDjReportTest {
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
		
		AbstractColumn columnDate = ColumnBuilder.getNew()
		.setColumnProperty("date", Date.class.getName()).setTitle(
				"Date").setWidth(85).build();
		AbstractColumn columnaQuantity = ColumnBuilder.getNew()
		.setColumnProperty("quantity", Long.class.getName()).setTitle(
				"Quantity").setWidth(80).build();
		AbstractColumn columnAmount = ColumnBuilder.getNew()
		.setColumnProperty("amount", Float.class.getName()).setTitle(
				"Amount").setWidth(90).build();

		drb.addColumn(columnDate);
		drb.addColumn(columnaQuantity);
		drb.addColumn(columnAmount);
		
		drb.addGlobalVariable("min_date", columnDate, DJCalculation.LOWEST);
		drb.addGlobalVariable("max_date", columnDate, DJCalculation.HIGHEST);
		
		drb.setUseFullPageWidth(true);
		
		DJAxisFormat timeAxisFormat = new DJAxisFormat("date");
		timeAxisFormat.setLabelFont(Font.ARIAL_SMALL);
		timeAxisFormat.setLabelColor(Color.DARK_GRAY);
		timeAxisFormat.setTickLabelFont(Font.ARIAL_SMALL);
		timeAxisFormat.setTickLabelColor(Color.DARK_GRAY);
		timeAxisFormat.setTickLabelMask("MMM-yy");
		timeAxisFormat.setLineColor(Color.DARK_GRAY);
		
		DJAxisFormat valueAxisFormat = new DJAxisFormat("value");
		valueAxisFormat.setLabelFont(Font.ARIAL_SMALL);
		valueAxisFormat.setLabelColor(Color.DARK_GRAY);
		valueAxisFormat.setTickLabelFont(Font.ARIAL_SMALL);
		valueAxisFormat.setTickLabelColor(Color.DARK_GRAY);
		valueAxisFormat.setTickLabelMask("#,##0.0");
		valueAxisFormat.setLineColor(Color.DARK_GRAY);
		
		DJChart djChart = new DJTimeSeriesChartBuilder()
		//chart		
		.setX(20)
		.setY(10)
		.setWidth(500)
		.setHeight(250)
		.setCentered(false)
		.setBackColor(Color.LIGHT_GRAY)
		.setShowLegend(true)
		.setPosition(DJChartOptions.POSITION_FOOTER)
		.setTitle(new StringExpression() {			
			private SimpleDateFormat format = new SimpleDateFormat("MMMM yyyy");
			public Object evaluate(Map fields, Map variables, Map parameters) {
				return "From " + format.format((Date)variables.get("min_date")) + " to " + format.format((Date)variables.get("max_date"));
			}
		})
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
		.setTimePeriod((PropertyColumn) columnDate)
		.setTimePeriodClass(Month.class)
		.addSerie(columnaQuantity, "quant.")
		.addSerie(columnAmount)
		//plot
		.setShowShapes(true)
		.setShowLines(true)
		.setTimeAxisFormat(timeAxisFormat)
		.setValueAxisFormat(valueAxisFormat)
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
		assertEquals((int)DJChartOptions.EDGE_BOTTOM, chart.getLegendPosition().ordinal() );
		assertEquals((int)DJChartOptions.EDGE_TOP, chart.getTitlePosition().ordinal());
        assertEquals(LineStyleEnum.values()[DJChartOptions.LINE_STYLE_DOTTED], chart.getLineBox().getPen().getLineStyle());
		assertEquals(1f, chart.getLineBox().getPen().getLineWidth());
		assertEquals(Color.DARK_GRAY, chart.getLineBox().getPen().getLineColor());
		assertEquals(5, chart.getLineBox().getPadding().intValue());
	}
	
	public void testDataset() {
		JRDesignTimeSeriesDataset dataset = (JRDesignTimeSeriesDataset) chart.getDataset();
		// JR7: Time period is determined at runtime from data, getTimePeriod() returns null
		// assertEquals(Month.class, dataset.getTimePeriod());
		assertEquals(2, dataset.getSeriesList().size());
		assertNotNull(dataset.getSeries()[0].getLabelExpression().getText());
		assertNotNull(dataset.getSeries()[0].getSeriesExpression().getText());
	}
	
	public void testPlot() {
		JRDesignTimeSeriesPlot plot = (JRDesignTimeSeriesPlot) chart.getPlot();
		assertEquals(Boolean.TRUE, plot.getShowShapes());
		assertEquals(Boolean.TRUE, plot.getShowLines());
		
		assertNotNull(plot.getTimeAxisLabelExpression().getText());
		testFont(Font.ARIAL_SMALL, plot.getTimeAxisLabelFont());
		assertEquals(Color.DARK_GRAY, plot.getTimeAxisLabelColor());		
		testFont(Font.ARIAL_SMALL, plot.getTimeAxisTickLabelFont());
		assertEquals(Color.DARK_GRAY, plot.getTimeAxisTickLabelColor());
		assertEquals("MMM-yy", plot.getTimeAxisTickLabelMask());
		assertEquals(Color.DARK_GRAY, plot.getTimeAxisLineColor());
		
		assertNotNull(plot.getValueAxisLabelExpression().getText());
		testFont(Font.ARIAL_SMALL, plot.getValueAxisLabelFont());
		assertEquals(Color.DARK_GRAY, plot.getValueAxisLabelColor());		
		testFont(Font.ARIAL_SMALL, plot.getValueAxisTickLabelFont());
		assertEquals(Color.DARK_GRAY, plot.getValueAxisTickLabelColor());
		assertEquals("#,##0.0", plot.getValueAxisTickLabelMask());
		assertEquals(Color.DARK_GRAY, plot.getValueAxisLineColor());
	}
	
	public DynamicReport buildReport() throws Exception {		
		return drb.build();
	}

	private void testFont(Font djFont, JRFont jrFont) {
		assertEquals(djFont.getFontName(), jrFont.getFontName());
		assertEquals(djFont.getFontSize(), jrFont.getFontSize());
		assertEquals(djFont.isBold(), jrFont.isBold());
		assertEquals(djFont.isItalic(), jrFont.isItalic());
	}
	
	public static void main(String[] args) throws Exception {
		TimeSeriesChartBuilderTest test = new TimeSeriesChartBuilderTest();
		test.setUp();
		test.testReport();
		JasperViewer.viewReport(test.jp);
	}
	
	protected JRDataSource getDataSource() {
			List col =  new ArrayList();
			try {
				col.add(new Product("1.2008", "Harry Potter 7", 2500L, 10000f));
				col.add(new Product("2.2008", "Harry Potter 7", 1400L, 2831.32f));
				col.add(new Product("3.2008", "Harry Potter 7", 4000L, 38347f));
				col.add(new Product("4.2008", "Harry Potter 7", 3000L, 9482.4f));
				col.add(new Product("5.2008", "Harry Potter 7", 2500L, 27475.5f));
				col.add(new Product("6.2008", "Harry Potter 7", 1400L, 3322f));
				col.add(new Product("7.2008", "Harry Potter 7", 4000L, 78482f));
				col.add(new Product("8.2008", "Harry Potter 7", 3000L, 5831.32f));
				col.add(new Product("9.2008", "Harry Potter 7", 1500L, 8329.2f));
				col.add(new Product("10.2008", "Harry Potter 7", 2500L, 27475.5f));
				col.add(new Product("11.2008", "Harry Potter 7", 2500L, 38347f));
				col.add(new Product("12.2008", "Harry Potter 7", 1400L, 9482.4f));
			} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (ParseException e) {
				e.printStackTrace();
			}
			return new JRBeanCollectionDataSource(col);
	}
	
	public class Product {
		private Date date;
		private String item;
		private Long quantity;
		private Float amount;
		
		Product(String date, String item, Long quantity, Float amount) throws ParseException {
			this.date = new SimpleDateFormat("MM.yyyy").parse(date);
			this.item = item;
			this.quantity = quantity;
			this.amount = amount;			
		}

		public Date getDate() {
			return date;
		}

		public void setDate(Date date) {
			this.date = date;
		}

		public String getItem() {
			return item;
		}

		public void setItem(String item) {
			this.item = item;
		}

		public Long getQuantity() {
			return quantity;
		}

		public void setQuantity(Long quantity) {
			this.quantity = quantity;
		}

		public Float getAmount() {
			return amount;
		}

		public void setAmount(Float amount) {
			this.amount = amount;
		}		
	}
}
