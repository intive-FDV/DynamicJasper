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
import java.util.List;
import java.util.Map;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.view.JasperViewer;
import ar.com.fdvs.dj.core.DJConstants;
import ar.com.fdvs.dj.core.layout.TwoSeedCrossTabColorShema;
import ar.com.fdvs.dj.domain.DJCalculation;
import ar.com.fdvs.dj.domain.DJCrosstab;
import ar.com.fdvs.dj.domain.DJHyperLink;
import ar.com.fdvs.dj.domain.DynamicReport;
import ar.com.fdvs.dj.domain.StringExpression;
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

/**
 * This uses the main datasource instead of one passed as parameter
 * @author mamana
 *
 */
public class CrosstabHyperLinkReportTest extends BaseDjReportTest {

	private Style totalHeaderStyle;
	private Style rowHeaderStyle;
	private Style colHeaderStyle;
	private Style mainHeaderStyle;
	private Style totalStyle;
	private Style measureStyle;
	private Style measureStyle2;
	private Style titleStyle;
	
	Color pastelYellow = new Color(240,248,200);
	Color pastelGreen = new Color(200,248,240);
	Color pastelR = new Color(200,240,248);

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

		
		TwoSeedCrossTabColorShema colorScheme = new TwoSeedCrossTabColorShema(pastelYellow,pastelGreen);
		
		DJCrosstab djcross = new CrosstabBuilder()
			.setHeight(400)
			.setWidth(500)
			.setHeaderStyle(mainHeaderStyle)
			.useMainReportDatasource(false)
			.setUseFullWidth(true)
			.setColorScheme(colorScheme)
			.setAutomaticTitle(true)
			.setCellBorder(Border.PEN_1_POINT())
			.addRow("State","state",String.class.getName(),true)
//			.addRow("Branch","branch",String.class.getName(),true)
			.addColumn("Product Line", "productLine", String.class.getName(),true)
//			.addColumn("Item", "item", String.class.getName(),true)
//			.addColumn("ID","id",Long.class.getName(), true)
			.addMeasure("id",Long.class.getName(), DJCalculation.SUM , "Id", measureStyle)
			.addMeasure("amount",Float.class.getName(), DJCalculation.SUM , "Amount",measureStyle2)
			.setRowStyles(rowHeaderStyle, totalStyle, totalHeaderStyle)
			.setColumnStyles(colHeaderStyle, totalStyle, totalHeaderStyle)
			.setCellDimension(34, 60)
			.setColumnHeaderHeight(30)
			.setRowHeaderWidth(80)
			.setDatasource("sr", DJConstants.DATA_SOURCE_ORIGIN_PARAMETER, DJConstants.DATA_SOURCE_TYPE_COLLECTION, true)
			.build();

		DJHyperLink link1 = new DJHyperLink();
		link1.setExpression(new StringExpression() {
			
			public Object evaluate(Map fields, Map variables, Map parameters) {				
				return "http://linkToAnotherPlace.com?";
			}
		});
		djcross.getMeasure(0).setLink(link1);
		
		drb.addHeaderCrosstab(djcross); //add the crosstab in the header band of the report

		DynamicReport dr = drb.build();

		//put a collection in the parameters map to be used by the crosstab
		List sortedCollection = SortUtils.sortCollection(TestRepositoryProducts.getDummyCollection(),djcross);
		log.info("crosstab datasource has " + sortedCollection.size() + " elements");
		params.put("sr", sortedCollection);

		return dr;
	}
	
	protected JRDataSource getDataSource() {		
		return null;
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
			.setBackgroundColor(pastelR)
			.build();
		rowHeaderStyle = new StyleBuilder(false)
			.setHorizontalAlign(HorizontalAlign.LEFT)
			.setVerticalAlign(VerticalAlign.TOP)
			.setFont(Font.ARIAL_MEDIUM_BOLD)
			.setBackgroundColor(new Color(240,248,255))
			.setBackgroundColor(pastelYellow)
			.build();
		colHeaderStyle = new StyleBuilder(false)
			.setHorizontalAlign(HorizontalAlign.LEFT)
			.setVerticalAlign(VerticalAlign.TOP)
			.setFont(Font.ARIAL_MEDIUM_BOLD)
			.setBackgroundColor(new Color(255,240,248))
			.setBackgroundColor(pastelGreen)
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
			.setBackgroundColor(Color.WHITE)
			.build();

		measureStyle2 = new StyleBuilder(false).setPattern("#,###.##")
		.setHorizontalAlign(HorizontalAlign.RIGHT)
		.setFont(new Font(Font.MEDIUM,Font._FONT_ARIAL,false,true,false))
		.setTextColor(Color.RED)
		.build();
	}


	public static void main(String[] args) throws Exception {
		CrosstabHyperLinkReportTest test = new CrosstabHyperLinkReportTest();
		
//		test.dr = test.buildReport();
//		test.exportToJRXML();
		
		test.testReport();
		JasperViewer.viewReport(test.jp);	//finally display the report report
//		JasperDesignViewer.viewReportDesign(test.jr);
	}

}
