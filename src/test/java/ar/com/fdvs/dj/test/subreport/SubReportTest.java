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
import java.util.List;

import net.sf.jasperreports.view.JasperViewer;
import ar.com.fdvs.dj.core.DJConstants;
import ar.com.fdvs.dj.core.layout.ClassicLayoutManager;
import ar.com.fdvs.dj.domain.DynamicReport;
import ar.com.fdvs.dj.domain.builders.FastReportBuilder;
import ar.com.fdvs.dj.test.BaseDjReportTest;

public class SubReportTest extends BaseDjReportTest {

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
			.setMargins(5, 5, 20, 20)
			.setTitle("November " + getYear() +" sales report")
			.setSubtitle("This report was generated at " + new Date())
			.setUseFullPageWidth(true);

		drb.addField("statistics", List.class.getName());
		drb.addField("emptyStatistics", List.class.getName());

		DynamicReport drHeaderSubreport = createHeaderSubreport();
		drb.addSubreportInGroupHeader(2, drHeaderSubreport, new ClassicLayoutManager(),
				"emptyStatistics", DJConstants.DATA_SOURCE_ORIGIN_FIELD, DJConstants.DATA_SOURCE_TYPE_COLLECTION);

		 DynamicReport drFooterSubreport = createFooterSubreport();
		 drb.addSubreportInGroupHeader(2, drFooterSubreport,  new ClassicLayoutManager(),
				 "statistics", DJConstants.DATA_SOURCE_ORIGIN_FIELD, DJConstants.DATA_SOURCE_TYPE_COLLECTION);

		drb.setUseFullPageWidth(true);

		DynamicReport dr = drb.build();
		return dr;
	}

	private DynamicReport createHeaderSubreport() throws Exception {
		FastReportBuilder rb = new FastReportBuilder();
		DynamicReport dr = rb
			.addColumn("Date", "date", Date.class.getName(), 100)
			.addColumn("Average", "average", Float.class.getName(), 50)
			.addColumn("%", "percentage", Float.class.getName(), 50)
			.addColumn("Amount", "amount", Float.class.getName(), 50)
			.setMargins(5, 5, 20, 20)
			.setUseFullPageWidth(true)
			.setWhenNoDataNoPages()
			.setTitle("Header Subreport for this group")
			.build();
		return dr;
	}

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
		.setTitle("Footer Subreport for this group")
		.build();
		return dr;
	}


	public static void main(String[] args) throws Exception {
		SubReportTest test = new SubReportTest();
		test.testReport();
		JasperViewer.viewReport(test.jp);
	}

}