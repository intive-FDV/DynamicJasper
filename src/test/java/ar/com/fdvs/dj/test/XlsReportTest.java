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

package ar.com.fdvs.dj.test;

import java.util.Date;

import ar.com.fdvs.dj.core.layout.LayoutManager;
import ar.com.fdvs.dj.core.layout.ListLayoutManager;
import ar.com.fdvs.dj.domain.DynamicReport;
import ar.com.fdvs.dj.domain.Style;
import ar.com.fdvs.dj.domain.builders.FastReportBuilder;
import ar.com.fdvs.dj.domain.constants.GroupLayout;
import ar.com.fdvs.dj.domain.constants.Page;
import ar.com.fdvs.dj.domain.entities.DJGroup;

public class XlsReportTest extends BaseDjReportTest {

	public DynamicReport buildReport() throws Exception {


		/**
		 * Creates the DynamicReportBuilder and sets the basic options for
		 * the report
		 */
		FastReportBuilder drb = new FastReportBuilder();
		Style columDetail = new Style();
//		columDetail.setBorder(Border.THIN);

		drb.addColumn("State"			, "state"		, String.class.getName(), 30)
			.addColumn("Branch"			, "branch"		, String.class.getName(), 30)
			.addColumn("Product Line"	, "productLine"	, String.class.getName(), 50)
//			.addColumn("Item"			, "item"		, String.class.getName(), 50)
//			.addColumn("Item Code"		, "id"			, Long.class.getName()	, 30, true)
//			.addColumn("Quantity"		, "quantity"	, Long.class.getName()	, 60, true)
			.addColumn("Amount"			, "amount"		, Float.class.getName()	, 70, true)
			.addColumn("Date"			, "date"		, Date.class.getName()	, 70, true, "dd/MM/yyyy",null)
			.addGroups(2) //Not used by the ListLayoutManager
			.setPrintColumnNames(true)
			.setIgnorePagination(true) //for Excel, we may dont want pagination, just a plain list
			.setMargins(0, 0, 0, 0)
			.setPageSizeAndOrientation(Page.Page_Letter_Landscape())
			.setTitle("November 2006 sales report")
			.setSubtitle("This report was generated at " + new Date())
			.setReportName("My Excel Report")
			.setDefaultStyles(null, null, null, columDetail)
			.setUseFullPageWidth(true);

		DynamicReport dr = drb.build();

		DJGroup group = (DJGroup) dr.getColumnsGroups().iterator().next();
		group.setLayout(GroupLayout.EMPTY); //not used by ListLayoutManager

		return dr;
	}

	protected LayoutManager getLayoutManager() {
		return new ListLayoutManager();
	}

	protected void exportReport() throws Exception {
		ReportExporter.exportReportXls(jp, System.getProperty("user.dir")+ "/target/" + this.getClass().getName() + ".xls");
	}


	public static void main(String[] args) throws Exception {
		XlsReportTest test = new XlsReportTest();
		test.testReport();
	}
}
