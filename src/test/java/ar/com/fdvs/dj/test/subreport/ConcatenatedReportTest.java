/*
 * Dynamic Jasper: A library for creating reports dynamically by specifying
 * columns, groups, styles, etc. at runtime. It also saves a lot of development
 * time in many cases! (http://sourceforge.net/projects/dynamicjasper)
 *
 * Copyright (C) 2007  FDV Solutions (http://www.fdvsolutions.com)
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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.view.JasperViewer;
import ar.com.fdvs.dj.core.DJConstants;
import ar.com.fdvs.dj.core.DynamicJasperHelper;
import ar.com.fdvs.dj.core.layout.ClassicLayoutManager;
import ar.com.fdvs.dj.domain.AutoText;
import ar.com.fdvs.dj.domain.DynamicReport;
import ar.com.fdvs.dj.domain.Style;
import ar.com.fdvs.dj.domain.builders.DynamicReportBuilder;
import ar.com.fdvs.dj.domain.builders.FastReportBuilder;
import ar.com.fdvs.dj.domain.builders.SubReportBuilder;
import ar.com.fdvs.dj.domain.constants.Font;
import ar.com.fdvs.dj.domain.entities.Subreport;
import ar.com.fdvs.dj.test.ChartReportTest;
import ar.com.fdvs.dj.test.ReportExporter;
import ar.com.fdvs.dj.test.TestRepositoryProducts;
import ar.com.fdvs.dj.test.domain.Product;

public class ConcatenatedReportTest extends TestCase {

	private Map params = new HashMap();

	public DynamicReport buildReport() throws Exception {

		
		
		Style titleStyle = new Style();
		titleStyle.setFont(new Font(24, Font._FONT_VERDANA, true));

		/**
		 * 1st) Whe create an empty report (just title)
		 */
		DynamicReportBuilder drb = new DynamicReportBuilder();
		Integer margin = new Integer(20);
		drb
			.addTitleStyle(titleStyle)
			.addTitle("Concatenated reports")					//defines the title of the report
			.addSubtitle("All the reports shown here are concatenated as sub reports")				
			.addDetailHeight(new Integer(15)).addLeftMargin(margin)
			.addRightMargin(margin).addTopMargin(margin).addBottomMargin(margin)
			.addUseFullPageWidth(true)
			.addAutoText(AutoText.AUTOTEXT_PAGE_X_OF_Y, AutoText.POSITION_FOOTER,AutoText.ALIGMENT_CENTER);

		
		/**
		 * 2nd) First subreport to add
		 */
		//Create a subreport
		Subreport subreport1 =  new SubReportBuilder()
				.addDataSource( DJConstants.SUBREPORT_DATA_SOURCE_ORIGIN_PARAMETER,
								DJConstants.DATA_SOURCE_TYPE_COLLECTION, 
								"statistics")
				.addReport(createSubreport1())
				.build();

		//Add the data source of the sub-report as a parameter
		params.put("statistics", Product.statistics_ );
		//add the subreport to the main report builder
		drb.addConcatenatedReport(subreport1);
		
		/**
		 * 3rd) One more report
		 */
		//Create a subreport
		Subreport subreport2 = new SubReportBuilder()
				.addDataSource( DJConstants.SUBREPORT_DATA_SOURCE_ORIGIN_PARAMETER, 
								DJConstants.DATA_SOURCE_TYPE_ARRAY, 
								"statisticsArray")
				.addReport(createSubreport2())
				.build();
		//Add the data source of the sub-report as a parameter		
		params.put("statisticsArray", Product.statistics_.toArray() );
		//add the subreport to the main report builder
		drb.addConcatenatedReport(subreport2);

		/**
		 * 4th) And one more report (from another test)
		 */
		//Create a subreport
		ChartReportTest crt = new ChartReportTest();
		JasperReport chartJr = DynamicJasperHelper.generateJasperReport(crt.buildReport(), new ClassicLayoutManager());
		SubReportBuilder srb3 = new SubReportBuilder();
		Subreport subreport3 = srb3.addDataSource( DJConstants.SUBREPORT_DATA_SOURCE_ORIGIN_PARAMETER, 
									DJConstants.DATA_SOURCE_TYPE_COLLECTION, 
									"subreportsDataSource")
			.addReport(chartJr)
			.build();		
		//Add the data source of the sub-report as a parameter		
		params.put("subreportsDataSource", TestRepositoryProducts.getDummyCollection()  );
		//add the subreport to the main report builder
		drb.addConcatenatedReport(subreport3);

		
		//thats it!!!!
		DynamicReport dr = drb.build();
		
		return dr;
	}
	
	public void testReport() throws Exception {
			DynamicReport dr = buildReport();
			Collection mainDataSource = new ArrayList();
			// One trick: we must use as data source for the main report a
			// collection with one object (anything)
			mainDataSource.add("");

			JRDataSource ds = new JRBeanCollectionDataSource(mainDataSource);
			JasperPrint jp = DynamicJasperHelper.generateJasperPrint(dr, new ClassicLayoutManager(), ds, params);
			ReportExporter.exportReport(jp, System.getProperty("user.dir") + "/target/ConcatenatedReportTest.pdf");
			//JasperViewer.viewReport(jp);
	}	

	/**
	 * Creates a dynamic reports and compiles it in order to be used
	 * as a subreport
	 * @return
	 * @throws Exception
	 */
	private JasperReport createSubreport1() throws Exception {
		FastReportBuilder rb = new FastReportBuilder();
		DynamicReport dr = rb
			.addColumn("Date", "date", Date.class.getName(), 100)
			.addColumn("Average", "average", Float.class.getName(), 50)
			.addColumn("%", "percentage", Float.class.getName(), 50)
			.addColumn("Amount", "amount", Float.class.getName(), 50)
			.addMargins(5, 5, 20, 20)
			.addUseFullPageWidth(true)
			.addTitle("Subreport for this group")
			.build();
		return DynamicJasperHelper.generateJasperReport(dr, new ClassicLayoutManager());
	}

	/**
	 * Creates a dynamic reports and compiles it in order to be used
	 * as a subreport
	 * @return
	 * @throws Exception
	 */
	private JasperReport createSubreport2() throws Exception {
		FastReportBuilder rb = new FastReportBuilder();
		DynamicReport dr = rb
		.addColumn("Area", "name", String.class.getName(), 100)
		.addColumn("Average", "average", Float.class.getName(), 50)
		.addColumn("%", "percentage", Float.class.getName(), 50)
		.addColumn("Amount", "amount", Float.class.getName(), 50)
		.addGroups(1)
		.addMargins(5, 5, 20, 20)
		.addUseFullPageWidth(true)
		.addTitle("Subreport for this group")
		.build();
		return DynamicJasperHelper.generateJasperReport(dr, new ClassicLayoutManager());
	}

	public static void main(String[] args) throws Exception {
		ConcatenatedReportTest test = new ConcatenatedReportTest();
		test.testReport();
	}

}

