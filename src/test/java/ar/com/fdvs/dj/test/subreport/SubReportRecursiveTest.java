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
			.setTitle("November 2006 sales report")
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