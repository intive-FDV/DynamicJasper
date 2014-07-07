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


import ar.com.fdvs.dj.core.DynamicJasperHelper;
import ar.com.fdvs.dj.core.layout.ClassicLayoutManager;
import ar.com.fdvs.dj.core.layout.LayoutManager;
import ar.com.fdvs.dj.domain.DynamicJasperDesign;
import ar.com.fdvs.dj.domain.DynamicReport;
import ar.com.fdvs.dj.util.SortUtils;
import junit.framework.TestCase;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JRCompiler;
import net.sf.jasperreports.engine.util.JRProperties;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.*;

public abstract class BaseDjReportTest2 extends BaseDjReportTest {

    protected DynamicJasperDesign djd;

    public Map getParams() {
		return params;
	}

	protected static final Log log = LogFactory.getLog(BaseDjReportTest2.class);


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
            djd = DynamicJasperHelper.generateDynamicJasperDesign(dr, getLayoutManager(), params, "r");

            handleDesign(djd);

            JRProperties.setProperty(JRCompiler.COMPILER_PREFIX, "ar.com.fdvs.dj.util.DJJRJdtCompiler");
            jr = JasperCompileManager.compileReport(djd);

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

    protected void handleDesign(DynamicJasperDesign djd){

    };


}
