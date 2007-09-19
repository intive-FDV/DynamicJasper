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
import net.sf.jasperreports.view.JasperViewer;
import ar.com.fdvs.dj.core.DynamicJasperHelper;
import ar.com.fdvs.dj.core.layout.ClassicLayoutManager;
import ar.com.fdvs.dj.domain.ColumnsGroupVariableOperation;
import ar.com.fdvs.dj.domain.DynamicReport;
import ar.com.fdvs.dj.domain.ImageBanner;
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

public class ImageBannerReportTest extends TestCase {

	public DynamicReport buildReport() throws Exception {

		Style detailStyle = new Style();
		Style headerStyle = new Style();
		headerStyle.setFont(Font.ARIAL_MEDIUM_BOLD); headerStyle.setBorder(Border.PEN_2_POINT);
		headerStyle.setHorizontalAlign(HorizontalAlign.CENTER); headerStyle.setVerticalAlign(VerticalAlign.MIDDLE);

		Style titleStyle = new Style();
		titleStyle.setFont(new Font(18,Font._FONT_VERDANA,true));
		Style importeStyle = new Style();
		importeStyle.setHorizontalAlign(HorizontalAlign.RIGHT);
		Style oddRowStyle = new Style();
		oddRowStyle.setBorder(Border.NO_BORDER); oddRowStyle.setBackgroundColor(Color.LIGHT_GRAY);oddRowStyle.setTransparency(Transparency.OPAQUE);

		DynamicReportBuilder drb = new DynamicReportBuilder();
		Integer margin = new Integer(20);
			drb.addTitleStyle(titleStyle)
			.addTitle("November 2006 sales report")					//defines the title of the report
			.addSubtitle("The items in this report correspond "
					+"to the main products: DVDs, Books, Foods and Magazines")			
			.addDetailHeight(new Integer(15))
			.addLeftMargin(margin)
			.addRightMargin(margin)
			.addTopMargin(margin)
			.addBottomMargin(margin)
			.addPrintBackgroundOnOddRows(true)
			.addOddRowBackgroundStyle(oddRowStyle)
			.addColumnsPerPage(new Integer(1))
			.addColumnSpace(new Integer(5))
			.addFirstPageImageBanner(System.getProperty("user.dir") +"/target/test-classes/images/logo_fdv_solutions_60.jpg", new Integer(197), new Integer(60), ImageBanner.ALIGN_LEFT)
			.addFirstPageImageBanner(System.getProperty("user.dir") +"/target/test-classes/images/dynamicJasper_60.jpg", new Integer(300), new Integer(60), ImageBanner.ALIGN_RIGHT)
			.addImageBanner(System.getProperty("user.dir") +"/target/test-classes/images/logo_fdv_solutions_60.jpg", new Integer(100), new Integer(30), ImageBanner.ALIGN_LEFT)
			.addImageBanner(System.getProperty("user.dir") +"/target/test-classes/images/dynamicJasper_60.jpg", new Integer(150), new Integer(30), ImageBanner.ALIGN_RIGHT);

		AbstractColumn columnState = ColumnBuilder.getInstance().addColumnProperty("state", String.class.getName())
			.addTitle("State").addWidth(new Integer(85))
			.addStyle(detailStyle).addHeaderStyle(headerStyle).build();

		AbstractColumn columnBranch = ColumnBuilder.getInstance().addColumnProperty("branch", String.class.getName())
			.addTitle("Branch").addWidth(new Integer(85))
			.addStyle(detailStyle).addHeaderStyle(headerStyle).build();

		AbstractColumn columnaProductLine = ColumnBuilder.getInstance().addColumnProperty("productLine", String.class.getName())
			.addTitle("Product Line").addWidth(new Integer(85))
			.addStyle(detailStyle).addHeaderStyle(headerStyle).build();

		AbstractColumn columnaItem = ColumnBuilder.getInstance().addColumnProperty("item", String.class.getName())
			.addTitle("Item").addWidth(new Integer(85))
			.addStyle(detailStyle).addHeaderStyle(headerStyle).build();

		AbstractColumn columnCode = ColumnBuilder.getInstance().addColumnProperty("id", Long.class.getName())
			.addTitle("ID").addWidth(new Integer(40))
			.addStyle(importeStyle).addHeaderStyle(headerStyle).build();

		AbstractColumn columnaQuantity = ColumnBuilder.getInstance().addColumnProperty("quantity", Long.class.getName())
			.addTitle("Quantity").addWidth(new Integer(80))
			.addStyle(importeStyle).addHeaderStyle(headerStyle).build();

		AbstractColumn columnAmount = ColumnBuilder.getInstance().addColumnProperty("amount", Float.class.getName())
			.addTitle("Amount").addWidth(new Integer(90)).addPattern("$ 0.00")
			.addStyle(importeStyle).addHeaderStyle(headerStyle).build();


		GroupBuilder gb1 = new GroupBuilder();
		ColumnsGroup g1 = gb1.addCriteriaColumn((PropertyColumn) columnState)		//define the criteria column to group by (columnState)
			.addFooterVariable(columnAmount,ColumnsGroupVariableOperation.SUM)		//tell the group place a variable in the footer
																					//of the column "columnAmount" with the SUM of all
																					//values of the columnAmount in this group.
			
			.addFooterVariable(columnaQuantity,ColumnsGroupVariableOperation.SUM)	//idem for the columnaQuantity column
			.addGroupLayout(GroupLayout.VALUE_IN_HEADER_WITH_COLNAMES)				//tells the group how to be shown, there are many
																					//posibilities, see the GroupLayout for more.
			.build();
		
		GroupBuilder gb2 = new GroupBuilder();										//Create another group (using another column as criteria)
		ColumnsGroup g2 = gb2.addCriteriaColumn((PropertyColumn) columnBranch)		//and we add the same operations for the columnAmount and 
			.addFooterVariable(columnAmount,ColumnsGroupVariableOperation.SUM)		//columnaQuantity columns
			.addFooterVariable(columnaQuantity,ColumnsGroupVariableOperation.SUM)
			.build();

		drb.addColumn(columnState);
		drb.addColumn(columnBranch);
		drb.addColumn(columnaProductLine);
		drb.addColumn(columnaItem);
		drb.addColumn(columnCode);
		drb.addColumn(columnaQuantity);
		drb.addColumn(columnAmount);

		drb.addGroup(g1);	//add group g1
		drb.addGroup(g2);	//add group g2

		drb.addUseFullPageWidth(true);

		DynamicReport dr = drb.build();
		return dr;
	}

	public void testReport() {
		try {
			DynamicReport dr = buildReport();
			Collection dummyCollection = TestRepositoryProducts.getDummyCollection();
			dummyCollection = SortUtils.sortCollection(dummyCollection,dr.getColumns());
			JRDataSource ds = new JRBeanCollectionDataSource(dummyCollection);	//Create a JRDataSource, the Collection used
			
			JasperPrint jp = DynamicJasperHelper.generateJasperPrint(dr, new ClassicLayoutManager(), ds);
			ReportExporter.exportReport(jp, System.getProperty("user.dir")+ "/target/ImageBannerReportTest.pdf");
			JasperViewer.viewReport(jp);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	public static void main(String[] args) {
		ImageBannerReportTest test = new ImageBannerReportTest();
		test.testReport();
	}

}
