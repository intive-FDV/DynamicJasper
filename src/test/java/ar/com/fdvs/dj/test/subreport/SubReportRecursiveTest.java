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

import java.util.Collection;
import java.util.Date;

import net.sf.jasperreports.view.JasperViewer;
import ar.com.fdvs.dj.core.DJConstants;
import ar.com.fdvs.dj.core.layout.ClassicLayoutManager;
import ar.com.fdvs.dj.domain.DynamicReport;
import ar.com.fdvs.dj.domain.builders.FastReportBuilder;
import ar.com.fdvs.dj.test.BaseDjReportTest;

public class SubReportRecursiveTest extends BaseDjReportTest {

	public DynamicReport buildReport() throws Exception {



		FastReportBuilder drb = new FastReportBuilder();
		drb.addColumn("State", "state", String.class.getName(),30)
			.addColumn("Branch", "branch", String.class.getName(),30)
			.addGroups(2)
			.setMargins(5, 5, 20, 20)
			.addField("statistics", Collection.class.getName())
			.setTitle("November " + getYear() +" sales report")
			.setSubtitle("This report was generated at " + new Date())
			.setUseFullPageWidth(true);

		//Create level 2 sub-report
		DynamicReport drLevel2 = createLevel2Subreport();

		//now create and put level2 subreport in the main subreport
		drb.addSubreportInGroupFooter(2, drLevel2, new ClassicLayoutManager(),
				"statistics", DJConstants.DATA_SOURCE_ORIGIN_FIELD, DJConstants.DATA_SOURCE_TYPE_COLLECTION);

		DynamicReport mainReport = drb.build();


		return mainReport;
	}

	private DynamicReport createLevel2Subreport() throws Exception {
		FastReportBuilder rb = new FastReportBuilder();
		DynamicReport dr = rb
			.addColumn("Date", "date", Date.class.getName(), 100)
			.addColumn("Average", "average", Float.class.getName(), 50)
			.addColumn("%", "percentage", Float.class.getName(), 50)
			.addColumn("Amount", "amount", Float.class.getName(), 50)
			.addGroups(1)
			.addField("dummy3", Collection.class.getName())
			.setMargins(5, 5, 20, 20)
			.setUseFullPageWidth(true)
			.setTitle("Level 2 Subreport")
			.addSubreportInGroupFooter(1, createLevel3Subreport(), new ClassicLayoutManager(),
				"dummy3", DJConstants.DATA_SOURCE_ORIGIN_FIELD, DJConstants.DATA_SOURCE_TYPE_COLLECTION)
			.build();
		return dr;
	}

	private DynamicReport createLevel3Subreport() throws Exception {
		FastReportBuilder rb = new FastReportBuilder();
		DynamicReport dr = rb
		.addColumn("Name", "name", String.class.getName(), 100)
		.addColumn("Number", "number", Long.class.getName(), 50)
		.setMargins(5, 5, 20, 20)
		.setUseFullPageWidth(false)
		.setTitle("Level 3 Subreport")
		.build();
		return dr;
	}

	public static void main(String[] args) throws Exception {
		SubReportRecursiveTest test = new SubReportRecursiveTest();
		test.testReport();
		JasperViewer.viewReport(test.jp);
	}

}