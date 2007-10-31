/*
 * Dynamic Jasper: A library for creating reports dynamically by specifying
 * columns, groups, styles, etc. at runtime. It also saves a lot of development
 * time in many cases! (http://sourceforge.net/projects/dynamicjasper)
 *
 * Copyright (C) 2007  FDV Solutions (http://www.fdvsolutions.com)
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
import java.util.Collection;

import junit.framework.TestCase;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.view.JasperDesignViewer;
import net.sf.jasperreports.view.JasperViewer;
import ar.com.fdvs.dj.core.DynamicJasperHelper;
import ar.com.fdvs.dj.core.layout.ClassicLayoutManager;
import ar.com.fdvs.dj.domain.ColumnsGroupVariableOperation;
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
import ar.com.fdvs.dj.domain.entities.ColumnsGroup;
import ar.com.fdvs.dj.domain.entities.columns.AbstractColumn;
import ar.com.fdvs.dj.domain.entities.columns.PropertyColumn;
import ar.com.fdvs.dj.util.SortUtils;

public class GroupsReportTest extends TestCase {

	public DynamicReport buildReport() throws Exception {

		Style detailStyle = new Style();
		
		Style headerStyle = new Style();
		headerStyle.setFont(Font.COMIC_SANS_SMALL_BOLD);
		headerStyle.setBorder(Border.PEN_2_POINT);
		headerStyle.setHorizontalAlign(HorizontalAlign.CENTER);
		headerStyle.setVerticalAlign(VerticalAlign.MIDDLE);
		
		Style headerVariables = new Style();
		headerVariables.setFont(Font.ARIAL_SMALL_BOLD);
		headerVariables.setBorderBottom(Border.PEN_2_POINT);
		headerVariables.setHorizontalAlign(HorizontalAlign.RIGHT);
		headerVariables.setVerticalAlign(VerticalAlign.MIDDLE);		

		Style titleStyle = new Style();
		titleStyle.setFont(new Font(18, Font._FONT_VERDANA, true));
		Style importeStyle = new Style();
		importeStyle.setHorizontalAlign(HorizontalAlign.RIGHT);
		Style oddRowStyle = new Style();
		oddRowStyle.setBorder(Border.NO_BORDER);
		oddRowStyle.setBackgroundColor(Color.LIGHT_GRAY);
		oddRowStyle.setTransparency(Transparency.OPAQUE);

		DynamicReportBuilder drb = new DynamicReportBuilder();
		Integer margin = new Integer(20);
		drb
			.setTitleStyle(titleStyle)
			.setTitle("November 2006 sales report")					//defines the title of the report
			.setSubtitle("The items in this report correspond "
					+"to the main products: DVDs, Books, Foods and Magazines")				
			.setDetailHeight(new Integer(15)).setLeftMargin(margin)
			.setRightMargin(margin).setTopMargin(margin).setBottomMargin(margin)
			.setPrintBackgroundOnOddRows(true)
			.setGrandTotalLegend("Grand Total")
			.setGrandTotalLegendStyle(headerVariables)
			.setOddRowBackgroundStyle(oddRowStyle);
		

		AbstractColumn columnState = ColumnBuilder.getInstance()
				.setColumnProperty("state", String.class.getName()).setTitle(
						"State").setWidth(new Integer(85))
				.setStyle(titleStyle).setHeaderStyle(titleStyle).build();

		AbstractColumn columnBranch = ColumnBuilder.getInstance()
				.setColumnProperty("branch", String.class.getName()).setTitle(
						"Branch").setWidth(new Integer(85)).setStyle(
						detailStyle).setHeaderStyle(headerStyle).build();

		AbstractColumn columnaProductLine = ColumnBuilder.getInstance()
				.setColumnProperty("productLine", String.class.getName())
				.setTitle("Product Line").setWidth(new Integer(85)).setStyle(
						detailStyle).setHeaderStyle(headerStyle).build();

		AbstractColumn columnaItem = ColumnBuilder.getInstance()
				.setColumnProperty("item", String.class.getName()).setTitle(
						"Item").setWidth(new Integer(85)).setStyle(detailStyle)
				.setHeaderStyle(headerStyle).build();

		AbstractColumn columnCode = ColumnBuilder.getInstance()
				.setColumnProperty("id", Long.class.getName()).setTitle("ID")
				.setWidth(new Integer(40)).setStyle(importeStyle)
				.setHeaderStyle(headerStyle).build();

		AbstractColumn columnaQuantity = ColumnBuilder.getInstance()
				.setColumnProperty("quantity", Long.class.getName()).setTitle(
						"Quantity").setWidth(new Integer(80)).setStyle(
						importeStyle).setHeaderStyle(headerStyle).build();

		AbstractColumn columnAmount = ColumnBuilder.getInstance()
				.setColumnProperty("amount", Float.class.getName()).setTitle(
						"Amount").setWidth(new Integer(90))
				.setPattern("$ 0.00").setStyle(importeStyle).setHeaderStyle(
						headerStyle).build();
		
		drb.addGlobalHeaderVariable(columnAmount, ColumnsGroupVariableOperation.SUM,headerVariables);
		drb.addGlobalHeaderVariable(columnaQuantity, ColumnsGroupVariableOperation.SUM,headerVariables);

		GroupBuilder gb1 = new GroupBuilder();
		
//		 define the criteria column to group by (columnState)
		ColumnsGroup g1 = gb1.setCriteriaColumn((PropertyColumn) columnState).addFooterVariable(columnAmount,
						ColumnsGroupVariableOperation.SUM) // tell the group place a variable footer of the column "columnAmount" with the SUM of allvalues of the columnAmount in this group.
				.addFooterVariable(columnaQuantity,
						ColumnsGroupVariableOperation.SUM) // idem for the columnaQuantity column
				.setGroupLayout(GroupLayout.VALUE_IN_HEADER) // tells the group how to be shown, there are manyposibilities, see the GroupLayout for more.
				.build();

		GroupBuilder gb2 = new GroupBuilder(); // Create another group (using another column as criteria)
		ColumnsGroup g2 = gb2.setCriteriaColumn((PropertyColumn) columnBranch) // and we add the same operations for the columnAmount and
				.addFooterVariable(columnAmount,
						ColumnsGroupVariableOperation.SUM) // columnaQuantity columns
				.addFooterVariable(columnaQuantity,	ColumnsGroupVariableOperation.SUM)
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

		DynamicReport dr = drb.build();
		return dr;
	}

	public void testReport() {
	try {
		DynamicReport dr = buildReport();
		Collection dummyCollection = TestRepositoryProducts.getDummyCollection();
		dummyCollection = SortUtils.sortCollection(dummyCollection,dr.getColumns());
		
		JRDataSource ds = new JRBeanCollectionDataSource(dummyCollection);
		JasperPrint jp = DynamicJasperHelper.generateJasperPrint(dr, new ClassicLayoutManager(), ds);
//		ReportExporter.exportReport(jp, System.getProperty("user.dir")+ "/target/GroupsReportTest.pdf");
		ReportExporter.exportReportPlainXls(jp, System.getProperty("user.dir")+ "/target/GroupsReportTest.xls");
		JasperViewer.viewReport(jp);
//		JasperDesignViewer.viewReportDesign(DynamicJasperHelper.generateJasperReport(dr, new ClassicLayoutManager()));
	} catch (Exception e) {
		e.printStackTrace();
	}
}

	public static void main(String[] args) {
		GroupsReportTest test = new GroupsReportTest();
		test.testReport();
	}

}