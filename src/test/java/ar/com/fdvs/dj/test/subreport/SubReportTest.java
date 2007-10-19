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

package ar.com.fdvs.dj.test.subreport;

import java.awt.Color;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import junit.framework.TestCase;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.view.JasperViewer;
import ar.com.fdvs.dj.core.DJConstants;
import ar.com.fdvs.dj.core.DynamicJasperHelper;
import ar.com.fdvs.dj.core.layout.ClassicLayoutManager;
import ar.com.fdvs.dj.domain.ColumnsGroupVariableOperation;
import ar.com.fdvs.dj.domain.DynamicReport;
import ar.com.fdvs.dj.domain.Style;
import ar.com.fdvs.dj.domain.builders.ColumnBuilder;
import ar.com.fdvs.dj.domain.builders.DynamicReportBuilder;
import ar.com.fdvs.dj.domain.builders.FastReportBuilder;
import ar.com.fdvs.dj.domain.builders.GroupBuilder;
import ar.com.fdvs.dj.domain.constants.Border;
import ar.com.fdvs.dj.domain.constants.Font;
import ar.com.fdvs.dj.domain.constants.GroupLayout;
import ar.com.fdvs.dj.domain.constants.HorizontalAlign;
import ar.com.fdvs.dj.domain.constants.Stretching;
import ar.com.fdvs.dj.domain.constants.Transparency;
import ar.com.fdvs.dj.domain.constants.VerticalAlign;
import ar.com.fdvs.dj.domain.entities.ColumnsGroup;
import ar.com.fdvs.dj.domain.entities.columns.AbstractColumn;
import ar.com.fdvs.dj.domain.entities.columns.PropertyColumn;
import ar.com.fdvs.dj.test.ReportExporter;
import ar.com.fdvs.dj.test.TestRepositoryProducts;
import ar.com.fdvs.dj.util.SortUtils;

public class SubReportTest extends TestCase {

	public DynamicReport buildReport() throws Exception {

		Style detailStyle = new Style();
		Style headerStyle = new Style();
		headerStyle.setFont(Font.ARIAL_MEDIUM_BOLD);
		headerStyle.setBorder(Border.PEN_2_POINT);
		headerStyle.setHorizontalAlign(HorizontalAlign.CENTER);
		headerStyle.setVerticalAlign(VerticalAlign.MIDDLE);

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
			.setPrintColumnNames(false)
			.setOddRowBackgroundStyle(oddRowStyle);

		AbstractColumn columnState = ColumnBuilder.getInstance()
				.setColumnProperty("state", String.class.getName()).setTitle(
						"State").setWidth(new Integer(85))
				.setStyle(detailStyle).setHeaderStyle(headerStyle).build();

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

		GroupBuilder gb1 = new GroupBuilder();
		
//		 define the criteria column to group by (columnState)
		ColumnsGroup g1 = gb1.setCriteriaColumn((PropertyColumn) columnState).addFooterVariable(columnAmount,
						ColumnsGroupVariableOperation.SUM) // tell the group place a variable footer of the column "columnAmount" with the SUM of allvalues of the columnAmount in this group.
				.addFooterVariable(columnaQuantity,
						ColumnsGroupVariableOperation.SUM) // idem for the columnaQuantity column
				.setGroupLayout(GroupLayout.DEFAULT_WITH_HEADER) // tells the group how to be shown, there are manyposibilities, see the GroupLayout for more.
				.build();

		Style defaultFooterVariableStyle = new Style();
		defaultFooterVariableStyle.setStreching(Stretching.NO_STRETCH);
		defaultFooterVariableStyle.setHorizontalAlign(HorizontalAlign.RIGHT);
		defaultFooterVariableStyle.setFont(Font.ARIAL_MEDIUM_BOLD);
		
		GroupBuilder gb2 = new GroupBuilder(); // Create another group (using another column as criteria)
		ColumnsGroup g2 = gb2.setCriteriaColumn((PropertyColumn) columnBranch) // and we add the same operations for the columnAmount and
				.addFooterVariable(columnAmount,ColumnsGroupVariableOperation.SUM) // columnaQuantity columns
				.addFooterVariable(columnaQuantity,ColumnsGroupVariableOperation.SUM)
				.addHeaderVariable(columnAmount,ColumnsGroupVariableOperation.SUM) // columnaQuantity columns
				.addHeaderVariable(columnaQuantity,ColumnsGroupVariableOperation.SUM)
				.setDefaultFooterVariableStyle(defaultFooterVariableStyle)
				.setDefaultHeaderVariableStyle(defaultFooterVariableStyle)
				.setGroupLayout(GroupLayout.DEFAULT)
				.build();

		drb.addColumn(columnState);
		drb.addColumn(columnBranch);
		drb.addColumn(columnaProductLine);
		drb.addColumn(columnaItem);
		drb.addColumn(columnCode);
		drb.addColumn(columnaQuantity);
		drb.addColumn(columnAmount);
		
		drb.addField("statistics", List.class.getName());

		drb.addGroup(g1); // add group g1
		drb.addGroup(g2); // add group g2

		DynamicReport drHeaderSubreport = createHeaderSubreport();
		drb.addSubreportInGroupHeader(2, drHeaderSubreport, new ClassicLayoutManager(),
				"statistics", DJConstants.SUBREPORT_DATA_SOURCE_ORIGIN_FIELD, DJConstants.DATA_SOURCE_TYPE_COLLECTION);

		 DynamicReport drFooterSubreport = createFooterSubreport();
		 drb.addSubreportInGroupFooter(2, drFooterSubreport,  new ClassicLayoutManager(), 
				 "statistics", DJConstants.SUBREPORT_DATA_SOURCE_ORIGIN_FIELD, DJConstants.DATA_SOURCE_TYPE_COLLECTION);
		
		drb.setUseFullPageWidth(true);

		DynamicReport dr = drb.build();
		return dr;
	}

	private DynamicReport createHeaderSubreport() throws Exception {
		FastReportBuilder rb = new FastReportBuilder();
		DynamicReport dr = rb
			.addColumn("Date", "date", Date.class.getName(), 100)
			.addColumn("Average", "average", Float.class.getName(), 50)
			.addColumn("%", "percentage", Float.class.getName(), 50)
			.addColumn("Amount", "amount", Float.class.getName(), 50)
			.setMargins(5, 5, 20, 20)
			.setUseFullPageWidth(true)
			.setTitle("Subreport for this group")
			.build();
		return dr;
	}

	private DynamicReport createFooterSubreport() throws Exception {
		FastReportBuilder rb = new FastReportBuilder();
		DynamicReport dr = rb
		.addColumn("Area", "name", String.class.getName(), 100)
		.addColumn("Average", "average", Float.class.getName(), 50)
		.addColumn("%", "percentage", Float.class.getName(), 50)
		.addColumn("Amount", "amount", Float.class.getName(), 50)
		.addGroups(1)
		.setMargins(5, 5, 20, 20)
		.setUseFullPageWidth(true)
		.setTitle("Subreport for this group")
		.build();
		return dr;
	}

	public void testReport() {
	try {
		DynamicReport dr = buildReport();
		Collection dummyCollection = TestRepositoryProducts.getDummyCollection();
		dummyCollection = SortUtils.sortCollection(dummyCollection,dr.getColumns());
		
		JRDataSource ds = new JRBeanCollectionDataSource(dummyCollection);
		JasperPrint jp = DynamicJasperHelper.generateJasperPrint(dr, new ClassicLayoutManager(), ds);
		ReportExporter.exportReport(jp, System.getProperty("user.dir")+ "/target/SubReportTest.pdf");
		JasperViewer.viewReport(jp);
//		JasperDesignViewer.viewReportDesign(DynamicJasperHelper.generateJasperReport(dr, new ClassicLayoutManager()));
	} catch (Exception e) {
		e.printStackTrace();
	}
}

	public static void main(String[] args) {
		SubReportTest test = new SubReportTest();
		test.testReport();
	}

}