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

package ar.com.fdvs.dj.test.xml;

import ar.com.fdvs.dj.core.DynamicJasperHelper;
import ar.com.fdvs.dj.core.layout.ClassicLayoutManager;
import ar.com.fdvs.dj.domain.DynamicReport;
import ar.com.fdvs.dj.test.ReportExporter;
import junit.framework.TestCase;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.view.JasperViewer;

import java.beans.XMLDecoder;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Collection;

public class XMLReportTest extends TestCase {

	public DynamicReport buildReport() throws Exception {

		InputStream input = new FileInputStream(System.getProperty("user.dir")+ "/src/test/resources/xml/dynamicReport.xml");
		XMLDecoder dec = new XMLDecoder(input);
		DynamicReport dr = (DynamicReport) dec.readObject();
		return dr;
	}

	public void testReport() {
		try {
			DynamicReport dr = buildReport();
			InputStream input = new FileInputStream(System.getProperty("user.dir")+ "/src/test/resources/xml/reportData.xml");
			XMLDecoder dec = new XMLDecoder(input);
			Collection dummyCollection = (Collection) dec.readObject();
			JRDataSource ds = new JRBeanCollectionDataSource(dummyCollection);
			JasperPrint jp = DynamicJasperHelper.generateJasperPrint(dr, new ClassicLayoutManager(), ds);
			ReportExporter.exportReport(jp, System.getProperty("user.dir")+ "/target/XMLReportTest.pdf");
			JasperViewer.viewReport(jp);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		XMLReportTest test = new XMLReportTest();
		test.testReport();
	}

}
