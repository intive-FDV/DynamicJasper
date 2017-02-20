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

import net.sf.jasperreports.view.JasperViewer;
import ar.com.fdvs.dj.core.DJConstants;
import ar.com.fdvs.dj.core.layout.ClassicLayoutManager;
import ar.com.fdvs.dj.domain.DynamicReport;
import ar.com.fdvs.dj.domain.builders.FastReportBuilder;
import ar.com.fdvs.dj.domain.builders.SubReportBuilder;
import ar.com.fdvs.dj.domain.entities.Subreport;
import ar.com.fdvs.dj.test.BaseDjReportTest;
import ar.com.fdvs.dj.test.domain.Product;

public class SubReportBuilderTest extends BaseDjReportTest {

	public DynamicReport buildReport() throws Exception {

		FastReportBuilder drb = new FastReportBuilder();
		drb.addColumn("State", "state", String.class.getName(),30)
			.addColumn("Branch", "branch", String.class.getName(),30)
			.addColumn("Product Line", "productLine", String.class.getName(),50)
			.addColumn("Item", "item", String.class.getName(),50)
			.addColumn("Item Code", "id", Long.class.getName(),30,true)
			.addColumn("Quantity", "quantity", Long.class.getName(),60,true)
			.addColumn("Amount", "amount", Float.class.getName(),70,true)
			.addGroups(2)
			.setTitle("November " + getYear() +" sales report")
			.setSubtitle("This report was generated at " + new Date())
			.setUseFullPageWidth(true);

		/*
		  Create the subreport. Note that the "subreport" object is then passed
		  as parameter to the GroupBuilder
		 */
		Subreport subreport = new SubReportBuilder()
						.setDataSource(	DJConstants.DATA_SOURCE_ORIGIN_PARAMETER,
										DJConstants.DATA_SOURCE_TYPE_COLLECTION,
										"statistics")
						.setDynamicReport(createFooterSubreport(), new ClassicLayoutManager())
						.build();

		drb.addSubreportInGroupFooter(1, subreport);

		/*
		  add in a map the paramter with the data source to use in the subreport.
		  The "params" map is later passed to the DynamicJasperHelper.generateJasperPrint(...)
		 */
		params.put("statistics", Product.statistics_  ); // the 2nd param is a static Collection

		/*
		  Create the group and add the subreport (as a Fotter subreport)
		 */
		drb.setUseFullPageWidth(true);

		DynamicReport dr = drb.build();

		return dr;
	}

	/**
	 * Created and compiles dynamically a report to be used as subreportr
	 * @return
	 * @throws Exception
	 */
	private DynamicReport createFooterSubreport() throws Exception {
		FastReportBuilder rb = new FastReportBuilder();
		DynamicReport dr = rb
		.addColumn("Area", "name", String.class.getName(), 100)
		.addColumn("Average", "average", Float.class.getName(), 50)
		.addColumn("%", "percentage", Float.class.getName(), 50)
		.addColumn("Amount", "amount", Float.class.getName(), 50)
		.addGroups(1)
		.setMargins(5, 5, 20, 20)
		.setUseFullPageWidth(true)
		.setTitle("Subreport for this group")
		.build();
		return dr;
	}


	public static void main(String[] args) throws Exception {
		SubReportBuilderTest test = new SubReportBuilderTest();
		test.testReport();
		JasperViewer.viewReport(test.jp);
	}

}