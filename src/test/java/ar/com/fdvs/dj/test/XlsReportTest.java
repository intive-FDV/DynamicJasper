/*
 * Dynamic Jasper: A library for creating reports dynamically by specifying
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


import ar.com.fdvs.dj.core.DynamicJasperHelper;
import ar.com.fdvs.dj.core.layout.ListLayoutManager;
import ar.com.fdvs.dj.domain.DynamicReport;
import ar.com.fdvs.dj.domain.Style;
import ar.com.fdvs.dj.domain.builders.FastReportBuilder;
import ar.com.fdvs.dj.domain.constants.Border;
import ar.com.fdvs.dj.domain.constants.GroupLayout;
import ar.com.fdvs.dj.domain.entities.ColumnsGroup;
import ar.com.fdvs.dj.util.SortUtils;
import junit.framework.TestCase;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.view.JasperViewer;

import java.util.Collection;
import java.util.Date;

public class XlsReportTest extends TestCase {

	public DynamicReport buildReport() throws Exception {


		/**
		 * Creates the DynamicReportBuilder and sets the basic options for
		 * the report
		 */
		FastReportBuilder drb = new FastReportBuilder();
		Style columDetail = new Style();
		columDetail.setBorder(Border.THIN);

		drb.addColumn("State"			, "state"		, String.class.getName(), 30)
			.addColumn("Branch"			, "branch"		, String.class.getName(), 30)
			.addColumn("Product Line"	, "productLine"	, String.class.getName(), 50)
			.addColumn("Item"			, "item"		, String.class.getName(), 50)
			.addColumn("Item Code"		, "id"			, Long.class.getName()	, 30, true)
			.addColumn("Quantity"		, "quantity"	, Long.class.getName()	, 60, true)
			.addColumn("Amount"			, "amount"		, Float.class.getName()	, 70, true)
			.addGroups(2) //Not used by the ListLayoutManager
			.setPrintColumnNames(true)
			.setIgnorePagination(true) //for Excel, we may dont want pagination, just a plain list
			.setMargins(0, 0, 0, 0)
			.setTitle("November 2006 sales report")
			.setSubtitle("This report was generated at " + new Date())
			.setDefaultStyles(null, null, null, columDetail)
			.setUseFullPageWidth(true);

		DynamicReport dr = drb.build();

		ColumnsGroup group = (ColumnsGroup) dr.getColumnsGroups().iterator().next();
		group.setLayout(GroupLayout.EMPTY); //not used by ListLayoutManager

		return dr;
	}

	public void testReport() {
		try {
			DynamicReport dr = buildReport();
			Collection dummyCollection = TestRepositoryProducts.getDummyCollection();
			dummyCollection = SortUtils.sortCollection(dummyCollection,dr.getColumns());

			JRDataSource ds = new JRBeanCollectionDataSource(dummyCollection);		//Create a JRDataSource, the Collection used
																											//here contains dummy hardcoded objects...

			JasperPrint jp = DynamicJasperHelper.generateJasperPrint(dr, new ListLayoutManager(), ds);	//Creates the JasperPrint object, we pass as a Parameter
																											//the DynamicReport, a new ClassicLayoutManager instance (this
																											//one does the magic) and the JRDataSource
			ReportExporter.exportReportPlainXls(jp, System.getProperty("user.dir")+ "/target/XlsReportTest.xls");
			JasperViewer.viewReport(jp);	//finally display the report report
//			JasperReport jr = DynamicJasperHelper.generateJasperReport(dr,  new ClassicLayoutManager());
//			JasperDesignViewer.viewReportDesign(jr);
		} catch (Exception e) {
//			e.getCause().printStackTrace();
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		XlsReportTest test = new XlsReportTest();
		test.testReport();
	}

}
