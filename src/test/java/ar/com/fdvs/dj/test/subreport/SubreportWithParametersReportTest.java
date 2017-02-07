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

package ar.com.fdvs.dj.test.subreport;


import java.sql.Connection;

import net.sf.jasperreports.engine.xml.JRXmlWriter;
import net.sf.jasperreports.view.JasperViewer;
import ar.com.fdvs.dj.core.DJConstants;
import ar.com.fdvs.dj.core.DynamicJasperHelper;
import ar.com.fdvs.dj.core.layout.ClassicLayoutManager;
import ar.com.fdvs.dj.domain.DJCalculation;
import ar.com.fdvs.dj.domain.DynamicReport;
import ar.com.fdvs.dj.domain.Style;
import ar.com.fdvs.dj.domain.builders.FastReportBuilder;
import ar.com.fdvs.dj.domain.constants.Border;
import ar.com.fdvs.dj.domain.constants.GroupLayout;
import ar.com.fdvs.dj.domain.constants.HorizontalAlign;
import ar.com.fdvs.dj.domain.constants.Transparency;
import ar.com.fdvs.dj.domain.entities.SubreportParameter;
import ar.com.fdvs.dj.test.BaseDjReportTest;
import ar.com.fdvs.dj.test.ReportExporter;
import ar.com.fdvs.dj.test.util.StyleFactory;

public class SubreportWithParametersReportTest extends BaseDjReportTest {

	private Connection con;

	public DynamicReport buildReport() throws Exception {
		Style valueStyle = StyleFactory.createGroupTileStyle("gtitleTyle");
		valueStyle.setBorderBottom(Border.THIN());
		Style columnTitleStyle = StyleFactory.createGroupDetailStyle("gdetailStyle");
		Style header = StyleFactory.createHeaderStyle2("header");
//		header.setTransparency(Transparency.TRANSPARENT);
		/*
		  Creates the DynamicReportBuilder and sets the basic options for
		  the report
		 */
		FastReportBuilder drb = new FastReportBuilder();
		drb
			.addColumn("City", "city", String.class.getName(),50, valueStyle, columnTitleStyle)
			.addColumn("Id", "id", Integer.class.getName(),30)
			.addColumn("Last Name", "lastname", String.class.getName(),50)
			.addColumn("First Name", "firstname", String.class.getName(),30)
			.addColumn("Street", "street", String.class.getName(),50)
			.addGroups(2,GroupLayout.VALUE_IN_HEADER_WITH_HEADERS_AND_COLUMN_NAME)
			.setGroupLayout(2, GroupLayout.EMPTY)
			.setTitle("Customers consumptions")
			.setPrintColumnNames(false)
			.setDefaultStyles(null, null, header, null)
			.setQuery("select * from customer order by city", DJConstants.QUERY_LANGUAGE_SQL)
			.setUseFullPageWidth(true);

		DynamicReport drLevel2 = createLevel2Subreport();

		SubreportParameter[] subreportParameters = new SubreportParameter[]{new SubreportParameter("custId","id",Integer.class.getName(),DJConstants.SUBREPORT_PARAM_ORIGIN_FIELD)};
		drb.addSubreportInGroupFooter(2, drLevel2, new ClassicLayoutManager(),
				null, DJConstants.DATA_SOURCE_ORIGIN_USE_REPORT_CONNECTION, DJConstants.DATA_SOURCE_TYPE_SQL_CONNECTION, subreportParameters);

		this.dr = drb.build();

		params.put("nom", "Juan"); //Note that the query has a parameter, by putting in the map
		return dr;
	}

	private DynamicReport createLevel2Subreport() throws Exception {
		Style vstyle1 = StyleFactory.createGroupVariableStyle("v1g");
		Style vstyle = StyleFactory.createGroup2VariableStyle("v2g");
		Style header = StyleFactory.createHeaderStyle2("s1header");
		header.setTransparency(Transparency.TRANSPARENT);
		vstyle.setHorizontalAlign(HorizontalAlign.RIGHT);
		FastReportBuilder rb = new FastReportBuilder();
		DynamicReport dr = rb
			.addColumn("Invoice", "invoiceid", Long.class.getName(), 40)
			.addColumn("Descpription", "name", String.class.getName(), 200)
			.addColumn("Quantity", "quantity", Integer.class.getName(), 50)
			.addColumn("Cost", "cost", Float.class.getName(), 80)
			.addColumn("Total", "itemtotal", Float.class.getName(), 80)
			.addGroups(1)
			.addGlobalFooterVariable(3, DJCalculation.SUM, vstyle1)
			.addGlobalFooterVariable(4, DJCalculation.SYSTEM, vstyle1)
			.addGlobalFooterVariable(5, DJCalculation.SUM, vstyle1)
			.addFooterVariable(1, 3, DJCalculation.SUM, vstyle)
			.addFooterVariable(1, 4, DJCalculation.SYSTEM, vstyle)
			.addFooterVariable(1, 5, DJCalculation.SUM, vstyle)
			.setQuery("SELECT a.quantity, a.cost, b.name, b.price, a.invoiceid,  a.cost * b.price itemtotal, c.customerid " +
						" FROM item a, product b, invoice c"+
						" where a.productid = b.id and a.invoiceId = c.id and c.customerid = $P{custId} order by a.invoiceid, item", DJConstants.QUERY_LANGUAGE_SQL)
			.setUseFullPageWidth(false)
			.setDefaultStyles(null, null, header, null)
			.addParameter("custId", Integer.class.getName())
			.setWhenNoData("No invoices for this customer", null)
			.setMargins(0, 0, 30, 30)
			.build();
		return dr;
	}

	public static void main(String[] args) throws Exception {
		SubreportWithParametersReportTest test = new SubreportWithParametersReportTest();
		test.testReport();
		JasperViewer.viewReport(test.jp);	//finally display the report report
//			JasperDesignViewer.viewReportDesign(jr);

		String jrxml = JRXmlWriter.writeReport(test.jr, "UTF-8");
		System.out.println(jrxml);
	}

	public void testReport() throws Exception {
		try {
			con = createSQLConnection();

			dr = buildReport();

			jp = DynamicJasperHelper.generateJasperPrint(dr, new ClassicLayoutManager(), con,params );

			ReportExporter.exportReport(jp, System.getProperty("user.dir")+ "/target/"+this.getClass().getName()+".pdf");
			jr = DynamicJasperHelper.generateJasperReport(dr,  new ClassicLayoutManager(),params);
		} finally {
			if (con != null) {
				con.close();
			}

		}
	}

}
