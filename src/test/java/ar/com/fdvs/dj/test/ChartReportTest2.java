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

package ar.com.fdvs.dj.test;

import java.awt.Color;

import net.sf.jasperreports.view.JasperViewer;
import ar.com.fdvs.dj.domain.DJCalculation;
import ar.com.fdvs.dj.domain.DJChart;
import ar.com.fdvs.dj.domain.DJChartOptions;
import ar.com.fdvs.dj.domain.DynamicReport;
import ar.com.fdvs.dj.domain.Style;
import ar.com.fdvs.dj.domain.builders.ColumnBuilder;
import ar.com.fdvs.dj.domain.builders.DJChartBuilder;
import ar.com.fdvs.dj.domain.builders.DynamicReportBuilder;
import ar.com.fdvs.dj.domain.builders.GroupBuilder;
import ar.com.fdvs.dj.domain.constants.Border;
import ar.com.fdvs.dj.domain.constants.Font;
import ar.com.fdvs.dj.domain.constants.GroupLayout;
import ar.com.fdvs.dj.domain.constants.HorizontalAlign;
import ar.com.fdvs.dj.domain.constants.Transparency;
import ar.com.fdvs.dj.domain.constants.VerticalAlign;
import ar.com.fdvs.dj.domain.entities.DJGroup;
import ar.com.fdvs.dj.domain.entities.columns.AbstractColumn;
import ar.com.fdvs.dj.domain.entities.columns.PropertyColumn;

public class ChartReportTest2 extends BaseDjReportTest {

	public DynamicReport buildReport() throws Exception {

		Style detailStyle = new Style();
		Style headerStyle = new Style();
		headerStyle.setFont(Font.ARIAL_MEDIUM_BOLD);
		headerStyle.setBorderBottom(Border.PEN_1_POINT());
		headerStyle.setBackgroundColor(Color.gray);
		headerStyle.setTextColor(Color.white);
		headerStyle.setHorizontalAlign(HorizontalAlign.CENTER);
		headerStyle.setVerticalAlign(VerticalAlign.MIDDLE);
		headerStyle.setTransparency(Transparency.OPAQUE);
		
		Style g2Variables = new Style();
		g2Variables.setFont(Font.ARIAL_MEDIUM_BOLD);
//		headerVariables.setBorderBottom(Border.THIN());
		g2Variables.setHorizontalAlign(HorizontalAlign.RIGHT);
		g2Variables.setVerticalAlign(VerticalAlign.MIDDLE);
		
		Style g1Variables = new Style();
		g1Variables.setFont(Font.ARIAL_MEDIUM_BOLD);
		g1Variables.setTextColor(Color.red);
		g1Variables.setHorizontalAlign(HorizontalAlign.RIGHT);
		g1Variables.setVerticalAlign(VerticalAlign.MIDDLE);				

		Style titleStyle = new Style();
		titleStyle.setFont(new Font(18, Font._FONT_VERDANA, true));
		Style importeStyle = new Style();
		importeStyle.setHorizontalAlign(HorizontalAlign.RIGHT);
		Style oddRowStyle = new Style();
		oddRowStyle.setBorder(Border.NO_BORDER());
		oddRowStyle.setBackgroundColor(Color.LIGHT_GRAY);
		oddRowStyle.setTransparency(Transparency.OPAQUE);

		DynamicReportBuilder drb = new DynamicReportBuilder();
		int margin = 20;
		drb
				.setTitleStyle(titleStyle)
				.setTitle("November " + getYear() +" sales report")					//defines the title of the report
				.setSubtitle("The items in this report correspond "
					+"to the main products: DVDs, Books, Foods and Magazines")
				.setDetailHeight(15).setLeftMargin(margin)
				.setMargins(margin, margin, margin, margin)
//				.setPrintBackgroundOnOddRows(true)
				.setPrintColumnNames(false)
				.setOddRowBackgroundStyle(oddRowStyle);
		
		AbstractColumn columnState = ColumnBuilder.getNew()
				.setColumnProperty("state", String.class.getName()).setTitle(
						"State").setWidth(new Integer(85))
				.setStyle(titleStyle).setHeaderStyle(titleStyle).build();

		AbstractColumn columnBranch = ColumnBuilder.getNew()
				.setColumnProperty("branch", String.class.getName()).setTitle(
						"Branch").setWidth(new Integer(85)).setStyle(
						detailStyle).setHeaderStyle(headerStyle).build();

		AbstractColumn columnaProductLine = ColumnBuilder.getNew()
				.setColumnProperty("productLine", String.class.getName())
				.setTitle("Product Line").setWidth(new Integer(85)).setStyle(
						detailStyle).setHeaderStyle(headerStyle).build();

		AbstractColumn columnaItem = ColumnBuilder.getNew()
				.setColumnProperty("item", String.class.getName()).setTitle(
						"Item").setWidth(new Integer(85)).setStyle(detailStyle)
				.setHeaderStyle(headerStyle).build();

		AbstractColumn columnCode = ColumnBuilder.getNew()
				.setColumnProperty("id", Long.class.getName()).setTitle("ID")
				.setWidth(new Integer(40)).setStyle(importeStyle)
				.setHeaderStyle(headerStyle).build();

		AbstractColumn columnaQuantity = ColumnBuilder.getNew()
				.setColumnProperty("quantity", Long.class.getName()).setTitle(
						"Quantity").setWidth(new Integer(80)).setStyle(
						importeStyle).setHeaderStyle(headerStyle).build();

		AbstractColumn columnAmount = ColumnBuilder.getNew()
				.setColumnProperty("amount", Float.class.getName()).setTitle(
						"Amount").setWidth(new Integer(90))
				.setPattern("$ 0.00").setStyle(importeStyle).setHeaderStyle(
						headerStyle).build();
		
		drb.addGlobalFooterVariable(columnAmount,DJCalculation.SUM,g1Variables);
		drb.addGlobalFooterVariable(columnaQuantity,DJCalculation.SUM,g1Variables);

		GroupBuilder gb1 = new GroupBuilder();

//		 define the criteria column to group by (columnState)
		DJGroup g1 = gb1.setCriteriaColumn((PropertyColumn) columnState)
					.addFooterVariable(columnAmount,DJCalculation.SUM,g1Variables)
				.addFooterVariable(columnaQuantity,DJCalculation.SUM,g1Variables) 
				.setGroupLayout(GroupLayout.VALUE_IN_HEADER_WITH_HEADERS) 
				.build();


		GroupBuilder gb2 = new GroupBuilder(); 
		DJGroup g2 = gb2.setCriteriaColumn((PropertyColumn) columnBranch) 
				.addFooterVariable(columnAmount,DJCalculation.SUM,g2Variables) 
				.addFooterVariable(columnaQuantity,DJCalculation.SUM,g2Variables)
				.build();

		drb.addColumn(columnState);
		drb.addColumn(columnBranch);
		drb.addColumn(columnaProductLine);
		drb.addColumn(columnaItem);
		drb.addColumn(columnCode);
		drb.addColumn(columnaQuantity);
		drb.addColumn(columnAmount);

		drb.addGroup(g1); // add group g1
		drb.addGroup(g2); // add group g2

		drb.setUseFullPageWidth(true);

		DJChartBuilder cb = new DJChartBuilder();
		int chartHeight = 200;
		DJChart chart;
		chart =  cb.addType(DJChart.BAR_CHART)
						.addOperation(DJChart.CALCULATION_SUM)
						.addColumnsGroup(g1)
						.addColumn(columnaQuantity)
						.setHeight(chartHeight)
						.setPosition(DJChartOptions.POSITION_HEADER)
						.setShowLabels(true)
						.build();

		drb.addChart(chart); //add chart
		
		//2nd chart
		cb = new DJChartBuilder();
		chart =  cb.addType(DJChart.BAR_CHART)
			.addOperation(DJChart.CALCULATION_SUM)
			.addColumnsGroup(g2)
			.setHeight(chartHeight)
			.addColumn(columnaQuantity)
			.addColumn(columnAmount)
			.setPosition(DJChartOptions.POSITION_HEADER)
			.setShowLabels(true)
			.build();
		
		drb.addChart(chart); //add chart

		//3rd Chart
		cb = new DJChartBuilder();
		chart =  cb.addType(DJChart.PIE_CHART)
		.addOperation(DJChart.CALCULATION_SUM)
		.addColumnsGroup(g2)
		.addColumn(columnaQuantity)
		.setHeight(chartHeight)		
		.setPosition(DJChartOptions.POSITION_HEADER)
		.build();
		
		drb.addChart(chart); //add chart

		DynamicReport dr = drb.build();

		return dr;
	}

	public static void main(String[] args) throws Exception {
		ChartReportTest2 test = new ChartReportTest2();
		test.testReport();
		JasperViewer.viewReport(test.jp);
		//JasperDesignViewer.viewReportDesign(DynamicJasperHelper.generateJasperReport(test.dr, new ClassicLayoutManager(),test.params));
	}

}