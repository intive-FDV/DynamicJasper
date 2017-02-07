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

import net.sf.jasperreports.view.JasperViewer;
import ar.com.fdvs.dj.core.DJConstants;
import ar.com.fdvs.dj.domain.DJCalculation;
import ar.com.fdvs.dj.domain.DJCrosstab;
import ar.com.fdvs.dj.domain.DynamicReport;
import ar.com.fdvs.dj.domain.Style;
import ar.com.fdvs.dj.domain.builders.CrosstabBuilder;
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

public class CrosstabReportWithNullValuesTest extends BaseDjReportTest {

	private Style totalColHeaderStyle;
	private Style totalRowHeaderStyle;

	private Style colHeaderStyle;
	private Style rowHeaderStyle;

	private Style mainHeaderStyle;
	private Style totalColStyle;
	private Style totalRowStyle;

    private Style measureStyle2;
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

		DJCrosstab djcross = new CrosstabBuilder()
			.setHeight(200)
			.setWidth(500)
			.setHeaderStyle(mainHeaderStyle)
			.setDatasource("sr",DJConstants.DATA_SOURCE_ORIGIN_PARAMETER, DJConstants.DATA_SOURCE_TYPE_COLLECTION)
			.setUseFullWidth(true)
			.setColorScheme(DJConstants.COLOR_SCHEMA_GRAY)
			.setAutomaticTitle(true)
			.setCellBorder(Border.PEN_1_POINT())
			.addRow("Product Line", "productLine", String.class.getName(),true)
			.addColumn("State","state",String.class.getName(),true)
			.addRow("Branch","branch",String.class.getName(),true)
			.addColumn("Item", "item", String.class.getName(),true)
//			.addMeasure("id",Long.class.getName(), DJCalculation.SUM , "Id", measureStyle)
			.addMeasure("quantity",Long.class.getName(), DJCalculation.FIRST , "Time",measureStyle2, null/* new DJValueFormatter() {

				public String getClassName() {
					return String.class.getName();
				}

				public Object evaluate(Object value, Map fields, Map variables, Map parameters) {
					Long val = (Long)value;
					return getAsMinutes(val); // + " (" + val + ")";
				}
			}*/)
			.setRowStyles(rowHeaderStyle, totalRowStyle, totalRowHeaderStyle)
			.setColumnStyles(colHeaderStyle, totalColStyle, totalColHeaderStyle)
			.setCellDimension(34, 60)
			.setColumnHeaderHeight(30)
			.setRowHeaderWidth(80)
			.build();

		drb.addHeaderCrosstab(djcross); //add the crosstab in the header band of the report

		DynamicReport dr = drb.build();

		//put a collection in the parameters map to be used by the crosstab
		params.put("sr", SortUtils.sortCollection(TestRepositoryProducts.getDummyCollectionSmall(),djcross));

		return dr;
	}

	public static String getAsMinutes(Long value) {
		if (value == null)
			return null;
		Long amount = value;
		int sec = amount.intValue() % 60;
		int mins = amount.intValue() / 60;
		return mins + "' " + sec + "\"";
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
			.setBorderBottom(Border.PEN_1_POINT())
			.build();

		totalColHeaderStyle = new StyleBuilder(false)
			.setHorizontalAlign(HorizontalAlign.CENTER)
			.setVerticalAlign(VerticalAlign.MIDDLE)
			.setFont(Font.ARIAL_MEDIUM_BOLD)
			.setTextColor(Color.WHITE)
			.setBackgroundColor(Color.BLUE)
			.build();

		totalRowHeaderStyle = new StyleBuilder(false)
			.setHorizontalAlign(HorizontalAlign.CENTER)
			.setVerticalAlign(VerticalAlign.MIDDLE)
			.setFont(Font.ARIAL_MEDIUM_BOLD)
			.setTextColor(Color.WHITE)
			.setBackgroundColor(Color.GREEN)
			.build();

		rowHeaderStyle = new StyleBuilder(false)
			.setHorizontalAlign(HorizontalAlign.LEFT)
			.setVerticalAlign(VerticalAlign.TOP)
			.setFont(Font.ARIAL_MEDIUM_BOLD)
			.build();
		mainHeaderStyle = new StyleBuilder(false)
			.setHorizontalAlign(HorizontalAlign.CENTER)
			.setVerticalAlign(VerticalAlign.MIDDLE)
			.setFont(Font.ARIAL_BIG_BOLD)
			.setTextColor(Color.WHITE)
			.setTransparency(Transparency.OPAQUE)
			.setBackgroundColor(Color.BLACK)
			.build();

		totalColStyle = new StyleBuilder(false).setPattern("#,###.##")
			.setHorizontalAlign(HorizontalAlign.RIGHT)
			.setFont(Font.ARIAL_MEDIUM_BOLD)
			.setTextColor(Color.GREEN)
			.build();

		totalRowStyle = new StyleBuilder(false).setPattern("#,###.##")
			.setHorizontalAlign(HorizontalAlign.RIGHT)
			.setFont(Font.ARIAL_MEDIUM_BOLD)
			.setTextColor(Color.MAGENTA)
			.build();

        Style measureStyle = new StyleBuilder(false).setPattern("#,###.##")
                .setHorizontalAlign(HorizontalAlign.RIGHT)
                .setFont(Font.ARIAL_MEDIUM)
                .build();

		measureStyle2 = new StyleBuilder(false).setPattern("#,###.##")
		.setHorizontalAlign(HorizontalAlign.RIGHT)
		.setFont(new Font(Font.MEDIUM,Font._FONT_ARIAL,false,true,false))
		.setTextColor(Color.BLUE)
		.build();
	}


	public static void main(String[] args) throws Exception {
		CrosstabReportWithNullValuesTest test = new CrosstabReportWithNullValuesTest();
		test.testReport();
		JasperViewer.viewReport(test.jp);	//finally display the report report
//		JasperDesignViewer.viewReportDesign(test.jr);
	}

}
