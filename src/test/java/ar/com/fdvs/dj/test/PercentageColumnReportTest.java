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
import ar.com.fdvs.dj.domain.DynamicReport;
import ar.com.fdvs.dj.domain.Style;
import ar.com.fdvs.dj.domain.builders.ColumnBuilder;
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

public class PercentageColumnReportTest extends BaseDjReportTest {

	public DynamicReport buildReport() throws Exception {

		Style detailStyle = new Style();
		Style headerStyle = new Style();
		headerStyle.setFont(Font.ARIAL_MEDIUM_BOLD); headerStyle.setBorder(Border.PEN_2_POINT());
		headerStyle.setHorizontalAlign(HorizontalAlign.CENTER); headerStyle.setVerticalAlign(VerticalAlign.MIDDLE);

		Style titleStyle = new Style();
		titleStyle.setFont(new Font(18,Font._FONT_VERDANA,true));
		Style amountStyle = new Style();
		amountStyle.setHorizontalAlign(HorizontalAlign.RIGHT);
		Style oddRowStyle = new Style();
		oddRowStyle.setBorder(Border.NO_BORDER());
		Color veryLightGrey = new Color(230,230,230);
		oddRowStyle.setBackgroundColor(veryLightGrey);oddRowStyle.setTransparency(Transparency.OPAQUE);

		DynamicReportBuilder drb = new DynamicReportBuilder();
		Integer margin = 20;
		drb
			.setTitle("November " + getYear() +" sales report")					//defines the title of the report
			.setSubtitle("The items in this report correspond "
					+"to the main products: DVDs, Books, Foods and Magazines")
					.setTitleStyle(titleStyle).setTitleHeight(30)
			.setSubtitleHeight(20)
			.setDetailHeight(16)
			.setLeftMargin(margin)
			.setRightMargin(margin)
			.setTopMargin(margin)
			.setBottomMargin(margin)
			.setPrintBackgroundOnOddRows(true)
			.setOddRowBackgroundStyle(oddRowStyle)
			.setColumnsPerPage(1)
			.setColumnSpace(5);

		AbstractColumn columnState = ColumnBuilder.getNew().setColumnProperty("state", String.class.getName())
			.setTitle("State").setWidth(new Integer(85))
			.setStyle(detailStyle).setHeaderStyle(headerStyle).build();

		AbstractColumn columnBranch = ColumnBuilder.getNew().setColumnProperty("branch", String.class.getName())
			.setTitle("Branch").setWidth(new Integer(85))
			.setStyle(detailStyle).setHeaderStyle(headerStyle).build();

//		AbstractColumn columnaProductLine = ColumnBuilder.getNew().setColumnProperty("productLine", String.class.getName())
//			.setTitle("Product Line").setWidth(new Integer(85))
//			.setStyle(detailStyle).setHeaderStyle(headerStyle).build();

		AbstractColumn columnaItem = ColumnBuilder.getNew().setColumnProperty("item", String.class.getName())
			.setTitle("item").setWidth(new Integer(85))
			.setStyle(detailStyle).setHeaderStyle(headerStyle).build();

		AbstractColumn columnCode = ColumnBuilder.getNew().setColumnProperty("id", Long.class.getName())
			.setTitle("ID").setWidth(new Integer(40))
			.setStyle(amountStyle).setHeaderStyle(headerStyle).build();

		AbstractColumn columnaCantidad = ColumnBuilder.getNew().setColumnProperty("quantity", Long.class.getName())
			.setTitle("Quantity").setWidth(new Integer(80))
			.setStyle(amountStyle).setHeaderStyle(headerStyle).build();

		AbstractColumn columnAmount = ColumnBuilder.getNew().setColumnProperty("amount", Float.class.getName())
			.setTitle("Amount").setWidth(new Integer(90)).setPattern("$ 0.00")
			.setStyle(amountStyle).setHeaderStyle(headerStyle).build();
		
		AbstractColumn columnPercentageAmount = ColumnBuilder.getNew().setPercentageColumn((PropertyColumn) columnAmount)
		.setTitle("Amount [%]").setWidth(new Integer(90))
		.setStyle(amountStyle).setHeaderStyle(headerStyle).build();

		drb.addColumn(columnState);
		drb.addColumn(columnBranch);
//		drb.addColumn(columnaProductLine);
		drb.addColumn(columnaItem);
//		drb.addColumn(columnCode);
		drb.addColumn(columnaCantidad);
		drb.addColumn(columnAmount);		
		drb.addColumn(columnPercentageAmount);
		
		GroupBuilder gb1 = new GroupBuilder();		
//	 define the criteria column to group by (columnState)
	DJGroup g1 = gb1.setCriteriaColumn((PropertyColumn) columnState)
			.addFooterVariable(columnAmount,DJCalculation.SUM)
			.setGroupLayout(GroupLayout.DEFAULT) // tells the group how to be shown, there are manyposibilities, see the GroupLayout for more.
			.setFooterVariablesHeight(20)
			.setFooterHeight(50,true)
			.setHeaderVariablesHeight(35)
			.build();
	
	GroupBuilder gb2 = new GroupBuilder();		
	DJGroup g2 = gb2.setCriteriaColumn((PropertyColumn) columnBranch)
	.addFooterVariable(columnAmount,DJCalculation.SUM)
	.addFooterVariable(columnPercentageAmount,DJCalculation.SUM)
	.setGroupLayout(GroupLayout.DEFAULT) // tells the group how to be shown, there are manyposibilities, see the GroupLayout for more.
	.setFooterVariablesHeight(20)
	.setFooterHeight(50,true)
	.setHeaderVariablesHeight(35)
	.build();
		
	drb.addGroup(g1);
	drb.addGroup(g2);
	
	
		drb.setUseFullPageWidth(true);

		drb.addField("productLine", String.class.getName());
//		drb.addField("branch", String.class.getName());

		DynamicReport dr = drb.build();
		return dr;
	}
	
	public static void main(String[] args) throws Exception {
		PercentageColumnReportTest test = new PercentageColumnReportTest();

		test.testReport();
		
		JasperViewer.viewReport(test.jp);
	}
	
	protected void exportReport() throws Exception {
		ReportExporter.exportReport(jp, System.getProperty("user.dir")+ "/target/reports/" + this.getClass().getName() + ".pdf");
		ReportExporter.exportReportXls(jp, System.getProperty("user.dir")+ "/target/reports/" + this.getClass().getName() + ".xls");
		exportToJRXML();
	}	

}
