/*
 * Copyright (c) 2012, FDV Solutions S.A.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *     * Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 *     * Neither the name of the FDV Solutions S.A. nor the
 *       names of its contributors may be used to endorse or promote products
 *       derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL FDV Solutions S.A. BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
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
		/**
		 * Creates the DynamicReportBuilder and sets the basic options for
		 * the report
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
		} catch (Exception e) {
			throw e;
		} finally {
			try {
				con.close();
			} catch (Exception e1) { }
		}
	}


}
