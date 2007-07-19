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
import java.util.Locale;

import junit.framework.TestCase;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.data.JRXmlDataSource;
import net.sf.jasperreports.view.JasperDesignViewer;
import net.sf.jasperreports.view.JasperViewer;
import ar.com.fdvs.dj.core.DynamicJasperHelper;
import ar.com.fdvs.dj.core.layout.ClassicLayoutManager;
import ar.com.fdvs.dj.core.layout.HorizontalBandAlignment;
import ar.com.fdvs.dj.domain.AutoText;
import ar.com.fdvs.dj.domain.ColumnsGroupVariableOperation;
import ar.com.fdvs.dj.domain.DJChart;
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
import ar.com.fdvs.dj.domain.entities.ColumnsGroup;
import ar.com.fdvs.dj.domain.entities.columns.AbstractColumn;
import ar.com.fdvs.dj.domain.entities.columns.PropertyColumn;
import ar.com.fdvs.dj.util.SortUtils;

public class ChartReportTest extends TestCase {

	public DynamicReport buildReport() throws Exception {

		Style detailStyle = new Style();
		Style headerStyle = new Style();
		headerStyle.setFont(Font.ARIAL_MEDIUM_BOLD);
		headerStyle.setBorder(Border.MEDIUM);
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
				.addTitleStyle(titleStyle)
				.addTitle("November 2006 sales report")					//defines the title of the report
				.addSubtitle("The items in this report correspond "
					+"to the main products: DVDs, Books, Foods and Magazines")				
				.addDetailHeight(new Integer(15)).addLeftMargin(margin)
				.addRightMargin(margin).addTopMargin(margin).addBottomMargin(
						margin).addPrintBackgroundOnOddRows(true)
				.addOddRowBackgroundStyle(oddRowStyle).addColumnsPerPage(
						new Integer(1)).addColumnSpace(new Integer(5));

		AbstractColumn columnState = ColumnBuilder.getInstance()
				.addColumnProperty("state", String.class.getName()).addTitle(
						"State").addWidth(new Integer(85))
				.addStyle(detailStyle).addHeaderStyle(headerStyle).build();

		AbstractColumn columnBranch = ColumnBuilder.getInstance()
				.addColumnProperty("branch", String.class.getName()).addTitle(
						"Branch").addWidth(new Integer(85)).addStyle(
						detailStyle).addHeaderStyle(headerStyle).build();

		AbstractColumn columnaProductLine = ColumnBuilder.getInstance()
				.addColumnProperty("productLine", String.class.getName())
				.addTitle("Product Line").addWidth(new Integer(85)).addStyle(
						detailStyle).addHeaderStyle(headerStyle).build();

		AbstractColumn columnaItem = ColumnBuilder.getInstance()
				.addColumnProperty("item", String.class.getName()).addTitle(
						"Item").addWidth(new Integer(85)).addStyle(detailStyle)
				.addHeaderStyle(headerStyle).build();

		AbstractColumn columnCode = ColumnBuilder.getInstance()
				.addColumnProperty("id", Long.class.getName()).addTitle("ID")
				.addWidth(new Integer(40)).addStyle(importeStyle)
				.addHeaderStyle(headerStyle).build();

		AbstractColumn columnaQuantity = ColumnBuilder.getInstance()
				.addColumnProperty("quantity", Long.class.getName()).addTitle(
						"Quantity").addWidth(new Integer(80)).addStyle(
						importeStyle).addHeaderStyle(headerStyle).build();

		AbstractColumn columnAmount = ColumnBuilder.getInstance()
				.addColumnProperty("amount", Float.class.getName()).addTitle(
						"Amount").addWidth(new Integer(90))
				.addPattern("$ 0.00").addStyle(importeStyle).addHeaderStyle(
						headerStyle).build();

		GroupBuilder gb1 = new GroupBuilder();
		
//		 define the criteria column to group by (columnState)
		ColumnsGroup g1 = gb1.addCriteriaColumn((PropertyColumn) columnState).addFooterVariable(columnAmount,
						ColumnsGroupVariableOperation.SUM) // tell the group place a variable footer of the column "columnAmount" with the SUM of allvalues of the columnAmount in this group.
				.addFooterVariable(columnaQuantity,
						ColumnsGroupVariableOperation.SUM) // idem for the columnaQuantity column
				.addGroupLayout(GroupLayout.VALUE_IN_HEADER_WITH_COLNAMES) // tells the group how to be shown, there are manyposibilities, see the GroupLayout for more.
				.build();
		
		
		GroupBuilder gb2 = new GroupBuilder(); // Create another group (using another column as criteria)
		ColumnsGroup g2 = gb2.addCriteriaColumn((PropertyColumn) columnBranch) // and we add the same operations for the columnAmount and
				.addFooterVariable(columnAmount,
						ColumnsGroupVariableOperation.SUM) // columnaQuantity columns
				.addFooterVariable(columnaQuantity,
						ColumnsGroupVariableOperation.SUM).build();

		drb.addColumn(columnState);
		drb.addColumn(columnBranch);
		drb.addColumn(columnaProductLine);
		drb.addColumn(columnaItem);
		drb.addColumn(columnCode);
		drb.addColumn(columnaQuantity);
		drb.addColumn(columnAmount);

		drb.addGroup(g1); // add group g1
		drb.addGroup(g2); // add group g2

		drb.addUseFullPageWidth(true);
		
		DJChartBuilder cb = new DJChartBuilder();
		DJChart chart =  cb.addType(DJChart.BAR_CHART)
						.addOperation(DJChart.CALCULATION_SUM)
						.addColumnsGroup(g2)
						.addColumn(columnAmount)
						.build();

		drb.addChart(chart); //add chart

		/**
		 * Adding many autotexts in the same position (header/footer and aligment) makes them to be one on top of the other
		 */
		drb.addAutoText(new AutoText(AutoText.AUTOTEXT_PAGE_X_OF_Y, AutoText.POSITION_FOOTER, HorizontalBandAlignment.LEFT));
		drb.addAutoText(new AutoText("Created by <b>msimone</b>", AutoText.POSITION_FOOTER, HorizontalBandAlignment.LEFT));
		
		drb.addAutoText(new AutoText("Created by <b>msimone</b>", AutoText.POSITION_FOOTER, HorizontalBandAlignment.RIGHT));
		drb.addAutoText(new AutoText(AutoText.AUTOTEXT_PAGE_X_OF_Y, AutoText.POSITION_FOOTER, HorizontalBandAlignment.RIGHT));
		
		drb.addAutoText(new AutoText(AutoText.AUTOTEXT_CREATED_ON, AutoText.POSITION_FOOTER, HorizontalBandAlignment.LEFT,AutoText.PATTERN_DATE_DATE_TIME));
		
		drb.addAutoText(new AutoText(AutoText.AUTOTEXT_PAGE_X_OF_Y, AutoText.POSITION_HEADER, HorizontalBandAlignment.LEFT));
		drb.addAutoText(new AutoText("Created by <b>msimone</b>", AutoText.POSITION_HEADER, HorizontalBandAlignment.LEFT));
		DynamicReport dr = drb.build();
		
		dr.setReportLocale(new Locale("es","AR"));
		
		return dr;
	}

	public void testReport() {
	try {
		DynamicReport dr = buildReport();
		Collection dummyCollection = TestRepositoryProducts.getDummyCollection();
		dummyCollection = SortUtils.sortCollection(dummyCollection,dr.getColumns());

		JasperPrint jp = DynamicJasperHelper.generateJasperPrint(dr, new ClassicLayoutManager(), dummyCollection);
		ReportExporter.exportReport(jp, System.getProperty("user.dir")+ "/target/ChartReportTest.pdf");
		JasperViewer.viewReport(jp);
		JasperDesignViewer.viewReportDesign(DynamicJasperHelper.generateJasperReport(dr, new ClassicLayoutManager()));
	} catch (Exception e) {
		e.printStackTrace();
	}
}

	public static void main(String[] args) {
		ChartReportTest test = new ChartReportTest();
		test.testReport();
	}

}