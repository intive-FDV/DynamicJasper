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

import ar.com.fdvs.dj.domain.*;
import ar.com.fdvs.dj.domain.builders.ColumnBuilder;
import ar.com.fdvs.dj.domain.builders.DJChartBuilder;
import ar.com.fdvs.dj.domain.builders.DynamicReportBuilder;
import ar.com.fdvs.dj.domain.builders.GroupBuilder;
import ar.com.fdvs.dj.domain.constants.*;
import ar.com.fdvs.dj.domain.constants.Font;
import ar.com.fdvs.dj.domain.constants.Transparency;
import ar.com.fdvs.dj.domain.entities.DJGroup;
import ar.com.fdvs.dj.domain.entities.columns.AbstractColumn;
import ar.com.fdvs.dj.domain.entities.columns.PropertyColumn;
import net.sf.jasperreports.view.JasperViewer;

import java.awt.*;
import java.util.Map;

public class ChartReportWithExpressionColumnTest extends BaseDjReportTest {

	public DynamicReport buildReport() throws Exception {

		Style detailStyle = new Style();
		Style headerStyle = new Style();
		headerStyle.setFont(Font.ARIAL_MEDIUM_BOLD);
		headerStyle.setBorder(Border.PEN_2_POINT());
		headerStyle.setHorizontalAlign(HorizontalAlign.CENTER);
		headerStyle.setVerticalAlign(VerticalAlign.MIDDLE);

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
				.setStyle(detailStyle).setHeaderStyle(headerStyle).build();

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
                .setCustomExpression(new CustomExpression() {
                    public Object evaluate(Map fields, Map variables, Map parameters) {
                        return (Float) fields.get("amount") * 500f;
                    }

                    public String getClassName() {
                        return Float.class.getName();
                    }
                })
				//.setColumnProperty("amount", Float.class.getName())
                .setTitle("Amount").setWidth(new Integer(90))
				.setPattern("$ 0.00").setStyle(importeStyle)
                .setHeaderStyle(headerStyle)
                .build();



		GroupBuilder gb1 = new GroupBuilder();

//		 define the criteria column to group by (columnState)
		DJGroup g1 = gb1.setCriteriaColumn((PropertyColumn) columnState).addFooterVariable(columnAmount,
						DJCalculation.SUM) // tell the group place a variable footer of the column "columnAmount" with the SUM of allvalues of the columnAmount in this group.
				.addFooterVariable(columnaQuantity,
						DJCalculation.SUM) // idem for the columnaQuantity column
				.setGroupLayout(GroupLayout.DEFAULT_WITH_HEADER) // tells the group how to be shown, there are manyposibilities, see the GroupLayout for more.
				.build();


		GroupBuilder gb2 = new GroupBuilder(); // Create another group (using another column as criteria)
		DJGroup g2 = gb2.setCriteriaColumn((PropertyColumn) columnBranch) // and we add the same operations for the columnAmount and
				.addFooterVariable(columnAmount,
						DJCalculation.SUM) // columnaQuantity columns
				.addFooterVariable(columnaQuantity,
						DJCalculation.SUM).build();

		drb.addColumn(columnState);
		drb.addColumn(columnBranch);
		drb.addColumn(columnaProductLine);
		drb.addColumn(columnaItem);
		drb.addColumn(columnCode);
		drb.addColumn(columnaQuantity);
		drb.addColumn(columnAmount);

        drb.addField("amount",Float.class.getName());

		drb.addGroup(g1); // add group g1
//		drb.addGroup(g2); // add group g2

		drb.setUseFullPageWidth(true);

		DJChartBuilder cb = new DJChartBuilder();
		DJChart chart =  cb.setType(DJChart.BAR_CHART)
						.setOperation(DJChart.CALCULATION_SUM)
						.setColumnsGroup(g1)
						.addColumn(columnAmount)
//						.addColumn(columnaQuantity)
						.setPosition(DJChartOptions.POSITION_HEADER)
						.setShowLabels(true)
						.build();

		drb.addChart(chart); //add chart

		DynamicReport dr = drb.build();

		return dr;
	}

	public static void main(String[] args) throws Exception {
		ChartReportWithExpressionColumnTest test = new ChartReportWithExpressionColumnTest();
		test.testReport();
		test.exportToJRXML();
		JasperViewer.viewReport(test.jp);
//		JasperDesignViewer.viewReportDesign(DynamicJasperHelper.generateJasperReport(test.dr, test.getLayoutManager(),test.params));
	}

}