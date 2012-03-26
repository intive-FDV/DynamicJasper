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


import ar.com.fdvs.dj.core.DynamicJasperHelper;
import ar.com.fdvs.dj.core.layout.ClassicLayoutManager;
import ar.com.fdvs.dj.core.layout.LayoutManager;
import ar.com.fdvs.dj.domain.DynamicReport;
import ar.com.fdvs.dj.util.SortUtils;
import junit.framework.TestCase;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class BaseDjReportTest extends TestCase {

	public Map getParams() {
		return params;
	}

	protected static final Log log = LogFactory.getLog(BaseDjReportTest.class);

	protected JasperPrint jp;
	protected JasperReport jr;
	protected Map params = new HashMap();
	protected DynamicReport dr;

	public abstract DynamicReport buildReport() throws Exception;

	public void testReport() throws Exception {
			dr = buildReport();

			/**
			 * Get a JRDataSource implementation
			 */
			JRDataSource ds = getDataSource();


			/**
			 * Creates the JasperReport object, we pass as a Parameter
			 * the DynamicReport, a new ClassicLayoutManager instance (this
			 * one does the magic) and the JRDataSource
			 */
			jr = DynamicJasperHelper.generateJasperReport(dr, getLayoutManager(), params);

			/**
			 * Creates the JasperPrint object, we pass as a Parameter
			 * the JasperReport object, and the JRDataSource
			 */
			log.debug("Filling the report");
			if (ds != null)
				jp = JasperFillManager.fillReport(jr, params, ds);
			else
				jp = JasperFillManager.fillReport(jr, params);

			log.debug("Filling done!");
			log.debug("Exporting the report (pdf, xls, etc)");
            exportReport();

            log.debug("test finished");

	}

	protected LayoutManager getLayoutManager() {
		return new ClassicLayoutManager();
	}

	protected void exportReport() throws Exception {
		ReportExporter.exportReport(jp, System.getProperty("user.dir")+ "/target/reports/" + this.getClass().getName() + ".pdf");
		exportToJRXML();
	}
	
	protected void exportToJRXML() throws Exception {
		if (this.jr != null){
			DynamicJasperHelper.generateJRXML(this.jr, "UTF-8",System.getProperty("user.dir")+ "/target/reports/" + this.getClass().getName() + ".jrxml");
			
		} else {
			DynamicJasperHelper.generateJRXML(this.dr, this.getLayoutManager(), this.params, "UTF-8",System.getProperty("user.dir")+ "/target/reports/" + this.getClass().getName() + ".jrxml");
		}
	}	

	protected void exportToHTML() throws Exception {
		ReportExporter.exportReportHtml(this.jp, System.getProperty("user.dir")+ "/target/reports/" + this.getClass().getName() + ".html");
	}	

	/**
	 * @return JRDataSource
	 */
	protected JRDataSource getDataSource() {
		Collection dummyCollection = TestRepositoryProducts.getDummyCollection();
		dummyCollection = SortUtils.sortCollection(dummyCollection,dr.getColumns());

		JRDataSource ds = new JRBeanCollectionDataSource(dummyCollection);		//Create a JRDataSource, the Collection used
																				//here contains dummy hardcoded objects...
		return ds;
	}

	public Collection getDummyCollectionSorted(List columnlist) {
		Collection dummyCollection = TestRepositoryProducts.getDummyCollection();
		return SortUtils.sortCollection(dummyCollection,columnlist);

	}

	public DynamicReport getDynamicReport() {
		return dr;
	}

	/**
	 * Uses a non blocking HSQL DB. Also uses HSQL default test data
	 * @return a Connection
	 * @throws Exception
	 */
	public static Connection createSQLConnection() throws Exception {
		Connection con = null;
		     Class.forName("org.hsqldb.jdbcDriver" );
			 con = DriverManager.getConnection("jdbc:hsqldb:file:target/test-classes/hsql/test_dj_db", "sa", "");

        return con;
	  }
}
