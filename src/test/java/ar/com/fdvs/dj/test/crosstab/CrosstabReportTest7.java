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
import ar.com.fdvs.dj.core.layout.CrossTabColorShema;
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

/**
 * This uses the main datasource instead of one passed as parameter
 * @author mamana
 *
 */
public class CrosstabReportTest7 extends BaseDjReportTest {

	private Style totalHeaderStyle;
	private Style rowHeaderStyle;
	private Style colHeaderStyle;
	private Style mainHeaderStyle;
	private Style totalStyle;
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
			.setWhenNoDataAllSectionNoDetail()
			.setDefaultStyles(titleStyle, null, null, null);

		CrossTabColorShema colorScheme = new CrossTabColorShema(2,2);
		colorScheme.setColorForMeasure(Color.ORANGE);
		colorScheme.setTotalColorForColumn(1, Color.PINK);
		colorScheme.setTotalColorForColumn(2, Color.YELLOW);
		colorScheme.setTotalColorForRow(1, Color.GRAY);
		colorScheme.setTotalColorForRow(2, Color.magenta);

		colorScheme.setColorForTotal(2, 2, Color.CYAN);
		
		DJCrosstab djcross = new CrosstabBuilder()
			.setHeight(400)
			.setWidth(500)
			.setHeaderStyle(mainHeaderStyle)
			.useMainReportDatasource(true)
			.setUseFullWidth(true)
			.setColorScheme(colorScheme)
			.setAutomaticTitle(true)
			.setCellBorder(Border.PEN_1_POINT())
			.addRow("State","state",String.class.getName(),true)
			.addRow("Branch","branch",String.class.getName(),true)
			.addColumn("Product Line", "productLine", String.class.getName(),true)
			.addColumn("Item", "item", String.class.getName(),true)
//			.addColumn("ID","id",Long.class.getName(), true)
//			.addMeasure("id",Long.class.getName(), DJCalculation.SUM , "Id", measureStyle)
			.addMeasure("amount",Float.class.getName(), DJCalculation.SUM , "Amount",measureStyle2)
			.setRowStyles(rowHeaderStyle, totalStyle, totalHeaderStyle)
			.setColumnStyles(colHeaderStyle, totalStyle, totalHeaderStyle)
			.setCellDimension(34, 60)
			.setColumnHeaderHeight(30)
			.setRowHeaderWidth(80)
			.build();

		drb.addHeaderCrosstab(djcross); //add the crosstab in the header band of the report

		DynamicReport dr = drb.build();

		//put a collection in the parameters map to be used by the crosstab
//		params.put("sr", SortUtils.sortCollection(TestRepositoryProducts.getDummyCollection(),djcross));

		return dr;
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

		totalHeaderStyle = new StyleBuilder(false)
			.setHorizontalAlign(HorizontalAlign.CENTER)
			.setVerticalAlign(VerticalAlign.MIDDLE)
			.setFont(Font.ARIAL_MEDIUM_BOLD)
			.setTransparency(Transparency.OPAQUE)
			.setTextColor(Color.BLUE)
			.setBackgroundColor(Color.GREEN)
			.build();
		rowHeaderStyle = new StyleBuilder(false)
			.setHorizontalAlign(HorizontalAlign.LEFT)
			.setVerticalAlign(VerticalAlign.TOP)
			.setFont(Font.ARIAL_MEDIUM_BOLD)
			.setBackgroundColor(new Color(240,248,255))
			.setBackgroundColor(Color.BLUE)
			.build();
		colHeaderStyle = new StyleBuilder(false)
			.setHorizontalAlign(HorizontalAlign.LEFT)
			.setVerticalAlign(VerticalAlign.TOP)
			.setFont(Font.ARIAL_MEDIUM_BOLD)
			.setBackgroundColor(new Color(255,240,248))
			.setBackgroundColor(Color.RED)
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
        Style measureStyle = new StyleBuilder(false).setPattern("#,###.##")
                .setHorizontalAlign(HorizontalAlign.RIGHT)
                .setFont(Font.ARIAL_MEDIUM)
                .setBackgroundColor(Color.WHITE)
                .build();

		measureStyle2 = new StyleBuilder(false).setPattern("#,###.##")
		.setHorizontalAlign(HorizontalAlign.RIGHT)
		.setFont(new Font(Font.MEDIUM,Font._FONT_ARIAL,false,true,false))
		.setTextColor(Color.RED)
		.build();
	}


	public static void main(String[] args) throws Exception {
		CrosstabReportTest7 test = new CrosstabReportTest7();
		test.testReport();
		JasperViewer.viewReport(test.jp);	//finally display the report report
		JasperDesignViewer.viewReportDesign(test.jr);
	}

}
