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
import ar.com.fdvs.dj.domain.constants.Transparency;
import ar.com.fdvs.dj.domain.constants.VerticalAlign;
import ar.com.fdvs.dj.test.BaseDjReportTest;
import ar.com.fdvs.dj.test.TestRepositoryProducts;
import ar.com.fdvs.dj.util.SortUtils;

public class CrosstabReportTest2 extends BaseDjReportTest {

	private Style totalHeader;
	private Style colAndRowHeaderStyle;
	private Style mainHeaderStyle;
	private Style totalStyle;
	private Style measureStyle;
	private Style titleStyle;

	public DynamicReport buildReport() throws Exception {
		initStyles(); //init some styles to be used


	/*
	  Create an empty report (no columns)!
	 */
		FastReportBuilder drb = new FastReportBuilder();
			drb
			.setTitle("November " + getYear() +" sales report")
			.setSubtitle("This report was generated at " + new Date())
			.setPageSizeAndOrientation(Page.Page_A4_Landscape())
			.setPrintColumnNames(false)
			.setUseFullPageWidth(true)
			.setDefaultStyles(titleStyle, null, null, null);

		DJCrosstab djcross = createCrosstab();

		drb.addHeaderCrosstab(djcross); //add the crosstab in the header band of the report

		DynamicReport dr = drb.build();

		//put a collection in the parameters map to be used by the crosstab
		params.put("sr", SortUtils.sortCollection(TestRepositoryProducts.getDummyCollection(),djcross));

		return dr;
	}

	/**
	 * Creates s DJCrosstab object, ready to be inserted in the main report
	 * @return
	 *
	 */
	private DJCrosstab createCrosstab() {

		CrosstabBuilder cb = new CrosstabBuilder();

		cb.setHeight(200)
			.setWidth(500)
			.setHeaderStyle(mainHeaderStyle)
			.setDatasource("sr",DJConstants.DATA_SOURCE_ORIGIN_PARAMETER, DJConstants.DATA_SOURCE_TYPE_COLLECTION)
			.setUseFullWidth(true)
			.setColorScheme(4)
			.setAutomaticTitle(true)
			.setCellBorder(Border.THIN());

		cb.addMeasure("amount",Float.class.getName(), DJCalculation.SUM , "Amount",measureStyle);

		DJCrosstabRow row = new CrosstabRowBuilder().setProperty("productLine",String.class.getName())
			.setHeaderWidth(100).setHeight(20)
			.setTitle("Product Line")
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

		cb.addRow(row);

		row = new CrosstabRowBuilder().setProperty("id",Long.class.getName())
			.setHeaderWidth(100).setHeight(30)
			.setTitle("ID").setShowTotals(true)
			.setTotalStyle(totalStyle).setTotalHeaderStyle(totalHeader)
			.setHeaderStyle(colAndRowHeaderStyle)
			.build();

		cb.addRow(row);

		DJCrosstabColumn col = new CrosstabColumnBuilder().setProperty("state",String.class.getName())
			.setHeaderHeight(60).setWidth(80)
			.setTitle("State").setShowTotals(true)
			.setTotalStyle(totalStyle).setTotalHeaderStyle(totalHeader)
			.setHeaderStyle(colAndRowHeaderStyle)
			.build();


		cb.addColumn(col);

		col = new CrosstabColumnBuilder().setProperty("branch",String.class.getName())
			.setHeaderHeight(30).setWidth(70)
			.setShowTotals(true).setTitle("Branch")
			.setTotalStyle(totalStyle).setTotalHeaderStyle(totalHeader)
			.setHeaderStyle(colAndRowHeaderStyle)
			.build();

		cb.addColumn(col);

//		col = new CrosstabColumnBuilder().setProperty("id",Long.class.getName())
//			.setHeaderHeight(40).setWidth(70)
//			.setShowTotals(true).setTitle("ID")
//			.setTotalStyle(totalStyle).setTotalHeaderStyle(totalHeader)
//			.setHeaderStyle(colAndRowHeaderStyle)
//			.build();

//		cb.addColumn(col);

		return cb.build();
	}

	/**
	 *
	 */
	private void initStyles() {
		titleStyle =  new StyleBuilder(false)
			.setFont(Font.ARIAL_BIG_BOLD)
			.setHorizontalAlign(HorizontalAlign.LEFT)
			.setVerticalAlign(VerticalAlign.MIDDLE)
			.setTransparency(Transparency.OPAQUE)
			.setBorderBottom(Border.PEN_2_POINT())
			.build();

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
		CrosstabReportTest2 test = new CrosstabReportTest2();
		test.testReport();
		JasperViewer.viewReport(test.jp);	//finally display the report report
		JasperDesignViewer.viewReportDesign(test.jr);
	}

}
