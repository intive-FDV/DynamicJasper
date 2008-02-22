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


import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import ar.com.fdvs.dj.core.DynamicJasperHelper;
import ar.com.fdvs.dj.core.layout.ClassicLayoutManager;
import ar.com.fdvs.dj.domain.DynamicReport;
import ar.com.fdvs.dj.util.SortUtils;

public abstract class BaseDjReportTest extends TestCase {

	protected JasperPrint jp;
	protected JasperReport jr;
	protected Map params = new HashMap();
	protected DynamicReport dr;

	public abstract DynamicReport buildReport() throws Exception;

	public void testReport() throws Exception {
			dr = buildReport();
			JRDataSource ds = getDataSource();

			jp = DynamicJasperHelper.generateJasperPrint(dr, new ClassicLayoutManager(), ds,params );	//Creates the JasperPrint object, we pass as a Parameter
																											//the DynamicReport, a new ClassicLayoutManager instance (this
																											//one does the magic) and the JRDataSource
			ReportExporter.exportReport(jp, System.getProperty("user.dir")+ "/target/"+this.getClass().getName()+".pdf");
			jr = DynamicJasperHelper.generateJasperReport(dr,  new ClassicLayoutManager());
	}

	/**
	 * @return
	 */
	protected JRDataSource getDataSource() {
		Collection dummyCollection = TestRepositoryProducts.getDummyCollection();
		dummyCollection = SortUtils.sortCollection(dummyCollection,dr.getColumns());

		JRDataSource ds = new JRBeanCollectionDataSource(dummyCollection);		//Create a JRDataSource, the Collection used
																										//here contains dummy hardcoded objects...
		return ds;
	}
}
