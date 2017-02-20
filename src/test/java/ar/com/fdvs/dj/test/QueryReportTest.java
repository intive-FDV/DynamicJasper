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


import java.sql.Connection;

import net.sf.jasperreports.engine.xml.JRXmlWriter;
import net.sf.jasperreports.view.JasperViewer;
import ar.com.fdvs.dj.core.DJConstants;
import ar.com.fdvs.dj.core.DynamicJasperHelper;
import ar.com.fdvs.dj.core.layout.ClassicLayoutManager;
import ar.com.fdvs.dj.domain.DynamicReport;
import ar.com.fdvs.dj.domain.builders.FastReportBuilder;

public class QueryReportTest extends BaseDjReportTest {

	public DynamicReport buildReport() throws Exception {
		/*
		  Creates the DynamicReportBuilder and sets the basic options for
		  the report
		 */
		FastReportBuilder drb = new FastReportBuilder();
		drb
			.addColumn("Id", "id", Integer.class.getName(),30)
			.addColumn("First Name", "firstname", String.class.getName(),30)
			.addColumn("Last Name", "lastname", String.class.getName(),50)
			.addColumn("Street", "street", String.class.getName(),50)
			.addColumn("City", "city", String.class.getName(),50)
			.setTitle("Customers")
			.setQuery("select * from customer where firstname like $P{start}", DJConstants.QUERY_LANGUAGE_SQL)
			//.setQuery("select * from customer", DJConstants.QUERY_LANGUAGE_SQL)
			.setTemplateFile("templates/TemplateReportTest.jrxml")
			.setUseFullPageWidth(true);

		DynamicReport dr = drb.build();

		//Note that the query has a parameter, by putting in the map
		//an item with the proper key, it will be automatically registered as a parameter
		params.put("start", "A%");

		return dr;
	}

	public static void main(String[] args) throws Exception {
		BaseDjReportTest test = new QueryReportTest();
		test.testReport();
		JasperViewer.viewReport(test.jp);	//finally display the report report
//			JasperDesignViewer.viewReportDesign(jr);

		String jrxml = JRXmlWriter.writeReport(test.jr, "UTF-8");
//		System.out.println(jrxml);
	}

	public void testReport() throws Exception {
		Connection con = null;
		try {
			dr = buildReport();
			con = createSQLConnection();
			jp = DynamicJasperHelper.generateJasperPrint(dr, new ClassicLayoutManager(), con,params );
			ReportExporter.exportReport(jp, System.getProperty("user.dir")+ "/target/"+this.getClass().getName()+".pdf");
			jr = DynamicJasperHelper.generateJasperReport(dr,  new ClassicLayoutManager(),params);
		} finally {
			try {
				con.close();
			} catch (Exception e1) { }
		}
	}


}
