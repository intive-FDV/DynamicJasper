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


import ar.com.fdvs.dj.core.DJServletHelper;
import ar.com.fdvs.dj.core.DynamicJasperHelper;
import ar.com.fdvs.dj.domain.DJCalculation;
import ar.com.fdvs.dj.domain.DJValueFormatter;
import ar.com.fdvs.dj.domain.DynamicReport;
import ar.com.fdvs.dj.domain.builders.FastReportBuilder;
import ar.com.fdvs.dj.output.ReportWriter;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.view.JasperDesignViewer;
import net.sf.jasperreports.view.JasperViewer;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class HtmlExportReportTest extends BaseDjReportTest {

	public DynamicReport buildReport() throws Exception {


		/**
		 * Creates the DynamicReportBuilder and sets the basic options for
		 * the report
		 */
		FastReportBuilder drb = new FastReportBuilder();
		drb.addColumn("State", "state", String.class.getName(),30)
			.addColumn("Branch", "branch", String.class.getName(),30)
			.addColumn("Product Line", "productLine", String.class.getName(),50)
			.addColumn("Item", "item", String.class.getName(),50)
			.addColumn("Item Code", "id", Long.class.getName(),30,true)
			.addColumn("Quantity", "quantity", Long.class.getName(),60,true)
			.addColumn("Amount", "amount", Float.class.getName(),70,true)
			.addGroups(2)
			.setTitle("November \"2006\" sales report")
			.setSubtitle("This report was generated at " + new Date())
			.setPrintBackgroundOnOddRows(true)			
			.setUseFullPageWidth(true);

        drb.addGlobalFooterVariable(drb.getColumn(4), DJCalculation.COUNT, null, new DJValueFormatter() {

            public String getClassName() {
                return String.class.getName();
            }


            public Object evaluate(Object value, Map fields, Map variables,   Map parameters) {
                return (value == null ? "0" : value.toString()) + " Clients";
            }
        });


		DynamicReport dr = drb.build();

		return dr;
	}

    @Override
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

        //Exporting directly to a Response
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        DJServletHelper.exportToHtml(request, response,"/images", jp, null);

        //Exporting and returning an InputStream
        MockHttpServletRequest request2 = new MockHttpServletRequest();
        InputStream is = DJServletHelper.exportToHtml(request2, "/images", jp, null);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ReportWriter.copyStreams(is,out);

        //Comparing both generated html. They should be the same
        assertEquals(response.getContentAsString(),new String(out.toByteArray()));


        log.debug("test finished");

    }

    public void testReport2() throws Exception {
        dr = buildReport();

        /**
         * Creates the JasperReport object, we pass as a Parameter
         * the DynamicReport, a new ClassicLayoutManager instance (this
         * one does the magic) and the JRDataSource
         */
        jr = DynamicJasperHelper.generateJasperReport(dr, getLayoutManager(), params);

        log.debug("Filling done!");
        log.debug("Exporting the report (pdf, xls, etc)");

        //Exporting directly to a Response
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        DJServletHelper.exportToHtml(request, response,"/images", dr, getLayoutManager(),getDataSource(),new HashMap(),null);

        //Exporting and returning an InputStream
        MockHttpServletRequest request2 = new MockHttpServletRequest();
        InputStream is = DJServletHelper.exportToHtml(request2,"/images", dr, getLayoutManager(),getDataSource(),new HashMap(),null);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ReportWriter.copyStreams(is,out);

        //Comparing both generated html. They should be the same
        assertEquals(response.getContentAsString(),new String(out.toByteArray()));


        log.debug("test finished");

    }


	public static void main(String[] args) throws Exception {
		HtmlExportReportTest test = new HtmlExportReportTest();
		test.testReport();
		test.testReport2();
	}

}
