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

import java.util.Date;
import java.util.HashMap;

import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperDesignViewer;
import net.sf.jasperreports.view.JasperViewer;
import ar.com.fdvs.dj.core.DJConstants;
import ar.com.fdvs.dj.core.DynamicJasperHelper;
import ar.com.fdvs.dj.core.layout.ClassicLayoutManager;
import ar.com.fdvs.dj.domain.AutoText;
import ar.com.fdvs.dj.domain.DynamicReport;
import ar.com.fdvs.dj.domain.Style;
import ar.com.fdvs.dj.domain.builders.DynamicReportBuilder;
import ar.com.fdvs.dj.domain.builders.FastReportBuilder;
import ar.com.fdvs.dj.domain.constants.Font;
import ar.com.fdvs.dj.test.BaseDjReportTest;
import ar.com.fdvs.dj.test.ChartReportTest;
import ar.com.fdvs.dj.test.ReportExporter;
import ar.com.fdvs.dj.test.TestRepositoryProducts;
import ar.com.fdvs.dj.test.domain.Product;

public class ConcatenatedReportTest extends BaseDjReportTest {

	public DynamicReport buildReport() throws Exception {

		Style titleStyle = new Style();
		titleStyle.setFont(new Font(24, Font._FONT_VERDANA, true));

		/*
		  1st) Whe create an empty report (just title) and add 2 sub-reports
		 */
		DynamicReportBuilder drb = new DynamicReportBuilder();
		Integer margin = 20;
		drb
			.setTitleStyle(titleStyle)
			.setTitle("Concatenated reports")					//defines the title of the report
			.setSubtitle("All the reports shown here are concatenated as sub reports")
			.setDetailHeight(15).setLeftMargin(margin)
			.setRightMargin(margin).setTopMargin(margin).setBottomMargin(margin)
			.setUseFullPageWidth(true)
			.setWhenNoDataAllSectionNoDetail()
			.addAutoText(AutoText.AUTOTEXT_PAGE_X_OF_Y, AutoText.POSITION_FOOTER,AutoText.ALIGNMENT_CENTER)
			.addConcatenatedReport(createSubreport1("Sub report 1"), new ClassicLayoutManager(), "statistics",
									DJConstants.DATA_SOURCE_ORIGIN_PARAMETER, DJConstants.DATA_SOURCE_TYPE_COLLECTION,false)
			.addConcatenatedReport(createSubreport2("Sub report 2"), "statistics",
									DJConstants.DATA_SOURCE_ORIGIN_PARAMETER, DJConstants.DATA_SOURCE_TYPE_COLLECTION,true)
			.addConcatenatedReport(createSubreport2("Sub report 3"), "statistics",
											DJConstants.DATA_SOURCE_ORIGIN_PARAMETER, DJConstants.DATA_SOURCE_TYPE_COLLECTION,true)
		.addConcatenatedReport(createSubreport2("Sub report 4"), "statistics",
				DJConstants.DATA_SOURCE_ORIGIN_PARAMETER, DJConstants.DATA_SOURCE_TYPE_COLLECTION,true);


		//Add the data source of the sub-report as a parameter
		params.put("statistics", Product.statistics_ );
		//add the subreport to the main report builder

		//Add the data source of the sub-report as a parameter
		params.put("statisticsArray", Product.statistics_.toArray() );

		/*
		  Add one more report (from another test)
		 */
		//Create a subreport
		ChartReportTest crt = new ChartReportTest();
		JasperReport chartJr = DynamicJasperHelper.generateJasperReport(crt.buildReport(), new ClassicLayoutManager(),new HashMap());
//		drb.addConcatenatedReport(chartJr, "subreportsDataSource",
//									DJConstants.DATA_SOURCE_ORIGIN_PARAMETER, DJConstants.DATA_SOURCE_TYPE_COLLECTION,true);
		//Add the data source of the sub-report as a parameter
		params.put("subreportsDataSource", TestRepositoryProducts.getDummyCollection()  );

		//thats it!!!!
//		DynamicReport dr = drb.build();
		dr = drb.build();

		return dr;
	}

	public void testReport() throws Exception {
			dr = buildReport();

			jr = DynamicJasperHelper.generateJasperReport(dr, getLayoutManager(), params);

			/*
			  Creates the JasperPrint object, we pass as a Parameter
			  the JasperReport object, and the JRDataSource
			 */
			jp = JasperFillManager.fillReport(jr, params);			
			ReportExporter.exportReport(jp, System.getProperty("user.dir") + "/target/ConcatenatedReportTest.pdf");

	}

	/**
	 * Creates a dynamic reports and compiles it in order to be used
	 * as a subreport
	 * @return
	 * @throws Exception
	 */
	private DynamicReport createSubreport1(String title) throws Exception {
		FastReportBuilder rb = new FastReportBuilder();
		DynamicReport dr = rb
			.addColumn("Date", "date", Date.class.getName(), 100)
			.addColumn("Average", "average", Float.class.getName(), 50)
			.addColumn("%", "percentage", Float.class.getName(), 50)
			.addColumn("Amount", "amount", Float.class.getName(), 50)
			.setMargins(5, 5, 20, 20)
			.setUseFullPageWidth(true)
			.setTitle(title)
			.build();
		return dr;
	}

	/**
	 * Creates a dynamic reports and compiles it in order to be used
	 * as a subreport
	 * @return
	 * @throws Exception
	 */
	private JasperReport createSubreport2(String title) throws Exception {
		FastReportBuilder rb = new FastReportBuilder();
		DynamicReport dr = rb
		.addColumn("Area", "name", String.class.getName(), 100)
		.addColumn("Average", "average", Float.class.getName(), 50)
		.addColumn("%", "percentage", Float.class.getName(), 50)
		.addColumn("Amount", "amount", Float.class.getName(), 50)
		.setMargins(5, 5, 20, 20)
		.setUseFullPageWidth(true)
		.setTitle(title)
		.build();
		return DynamicJasperHelper.generateJasperReport(dr, new ClassicLayoutManager(),null);
	}

	public static void main(String[] args) throws Exception {
		ConcatenatedReportTest test = new ConcatenatedReportTest();
		test.testReport();
		JasperViewer.viewReport(test.jp);
		JasperDesignViewer.viewReportDesign(test.jr);
		test.exportToJRXML();
	}

}

