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

package ar.com.fdvs.dj.test.crosstab;


import java.awt.Color;
import java.util.Date;

import net.sf.jasperreports.view.JasperDesignViewer;
import net.sf.jasperreports.view.JasperViewer;
import ar.com.fdvs.dj.core.DJConstants;
import ar.com.fdvs.dj.domain.DJCalculation;
import ar.com.fdvs.dj.domain.DJCrosstab;
import ar.com.fdvs.dj.domain.DJCrosstabColumn;
import ar.com.fdvs.dj.domain.DJCrosstabRow;
import ar.com.fdvs.dj.domain.DynamicReport;
import ar.com.fdvs.dj.domain.Style;
import ar.com.fdvs.dj.domain.builders.CrosstabBuilder;
import ar.com.fdvs.dj.domain.builders.CrosstabColumnBuilder;
import ar.com.fdvs.dj.domain.builders.CrosstabRowBuilder;
import ar.com.fdvs.dj.domain.builders.FastReportBuilder;
import ar.com.fdvs.dj.domain.builders.StyleBuilder;
import ar.com.fdvs.dj.domain.constants.Border;
import ar.com.fdvs.dj.domain.constants.Font;
import ar.com.fdvs.dj.domain.constants.HorizontalAlign;
import ar.com.fdvs.dj.domain.constants.Page;
import ar.com.fdvs.dj.domain.constants.VerticalAlign;
import ar.com.fdvs.dj.test.BaseDjReportTest;
import ar.com.fdvs.dj.test.TestRepositoryProducts;
import ar.com.fdvs.dj.util.SortUtils;

public class CrosstabReportTest extends BaseDjReportTest {

	private DJCrosstab djcross;
	private Style totalHeader;
	private Style colAndRowHeaderStyle;
	private Style mainHeaderStyle;
	private Style totalStyle;
	private Style measureStyle;

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
			.addGroups(1)
			.addFooterVariable(1, 7, DJCalculation.SUM, null)
			.addFooterVariable(1, 6, DJCalculation.SUM, null)
			.setTitle("November 2006 sales report")
			.setSubtitle("This report was generated at " + new Date())
			.setPageSizeAndOrientation(Page.Page_A4_Landscape())
			.setUseFullPageWidth(true);
//			drb.setTemplateFile("templates/crosstab2-test.jrxml");


		initStyles();

		CrosstabBuilder cb = new CrosstabBuilder();

		cb.setHeight(200)
			.setWidth(500)
			.setHeaderStyle(mainHeaderStyle)
			.setDatasource("sr",DJConstants.DATA_SOURCE_ORIGIN_PARAMETER, DJConstants.DATA_SOURCE_TYPE_COLLECTION)
			.setUseFullWidth(true)
			.setColorScheme(4)
			.setAutomaticTitle(true)
			.setCellBorder(Border.PEN_1_POINT);

		cb.addMeasure("amount",Float.class.getName(), DJCalculation.SUM , "Amount",measureStyle);

		DJCrosstabRow row = new CrosstabRowBuilder().setProperty("productLine",String.class.getName())
			.setHeaderWidth(100).setHeight(0)
			.setTitle("Product Line my mother teressa")
			.setShowTotals(true).setTotalStyle(totalStyle)
			.setTotalHeaderStyle(totalHeader).setHeaderStyle(colAndRowHeaderStyle)
			.build();

		cb.addRow(row);

		row = new CrosstabRowBuilder().setProperty("item",String.class.getName())
			.setHeaderWidth(100).setHeight(20)
			.setTitle("Item").setShowTotals(true)
			.setTotalStyle(totalStyle).setTotalHeaderStyle(totalHeader)
			.setHeaderStyle(colAndRowHeaderStyle)
			.build();

		row.setTotalHeaderHeight(100);

		cb.addRow(row);

//		row = new CrosstabRowBuilder().setProperty("id",Long.class.getName())
//			.setHeaderWidth(100).setHeight(30)
//			.setTitle("ID").setShowTotals(true)
//			.setTotalStyle(totalStyle).setTotalHeaderStyle(totalHeader)
//			.setHeaderStyle(colAndRowHeaderStyle)
//			.build();

//		cb.addRow(row);

		DJCrosstabColumn col = new CrosstabColumnBuilder().setProperty("state",String.class.getName())
			.setHeaderHeight(60).setWidth(50)
			.setTitle("State").setShowTotals(true)
			.setTotalStyle(totalStyle).setTotalHeaderStyle(totalHeader)
			.setHeaderStyle(colAndRowHeaderStyle)
			.build();


		cb.addColumn(col);

		col = new CrosstabColumnBuilder().setProperty("branch",String.class.getName())
			.setHeaderHeight(30).setWidth(60)
			.setShowTotals(true).setTitle("Branch")
			.setTotalStyle(totalStyle).setTotalHeaderStyle(totalHeader).setTotalLegend("Total for \"+ $V{branch}+ \"")
			.setHeaderStyle(colAndRowHeaderStyle)
			.build();

		cb.addColumn(col);

		col = new CrosstabColumnBuilder().setProperty("id",Long.class.getName())
		.setHeaderHeight(40)
		.setWidth(70)
		.setShowTotals(true)
		.setTitle("ID")
		.setTotalStyle(totalStyle)
		.setTotalHeaderStyle(totalHeader)
		.setHeaderStyle(colAndRowHeaderStyle)
		.build();

//		cb.addColumn(col);

		djcross = cb.build();

		drb.addHeaderCrosstab(djcross);

		DynamicReport dr = drb.build();

		params.put("sr", SortUtils.sortCollection(TestRepositoryProducts.getDummyCollection(),djcross));
		return dr;
	}

	/**
	 *
	 */
	private void initStyles() {
		totalHeader = new StyleBuilder(false)
			.setHorizontalAlign(HorizontalAlign.CENTER)
			.setVerticalAlign(VerticalAlign.MIDDLE)
			.setFont(Font.ARIAL_MEDIUM_BOLD)
			.setTextColor(Color.BLUE)
			.build();
		colAndRowHeaderStyle = new StyleBuilder(false)
			.setHorizontalAlign(HorizontalAlign.LEFT)
			.setVerticalAlign(VerticalAlign.TOP)
			.setFont(Font.ARIAL_MEDIUM_BOLD)
			.build();
		mainHeaderStyle = new StyleBuilder(false)
			.setHorizontalAlign(HorizontalAlign.CENTER)
			.setVerticalAlign(VerticalAlign.MIDDLE)
			.setFont(Font.ARIAL_BIG_BOLD)
			.setTextColor(Color.BLACK)
			.build();
		totalStyle = new StyleBuilder(false).setPattern("#,###.##")
			.setHorizontalAlign(HorizontalAlign.RIGHT)
			.setFont(Font.ARIAL_MEDIUM_BOLD)
			.build();
		measureStyle = new StyleBuilder(false).setPattern("#,###.##")
			.setHorizontalAlign(HorizontalAlign.RIGHT)
			.setFont(Font.ARIAL_MEDIUM)
			.build();
	}


	public static void main(String[] args) throws Exception {
		CrosstabReportTest test = new CrosstabReportTest();
		test.testReport();
		JasperViewer.viewReport(test.jp);	//finally display the report report
		JasperDesignViewer.viewReportDesign(test.jr);
	}

}
