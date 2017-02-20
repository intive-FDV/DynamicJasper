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
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;

import net.sf.jasperreports.view.JasperDesignViewer;
import net.sf.jasperreports.view.JasperViewer;

import org.apache.commons.beanutils.BeanUtils;

import ar.com.fdvs.dj.core.DJConstants;
import ar.com.fdvs.dj.domain.DJCalculation;
import ar.com.fdvs.dj.domain.DJCrosstab;
import ar.com.fdvs.dj.domain.DJCrosstabMeasure;
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
import ar.com.fdvs.dj.domain.entities.conditionalStyle.ConditionalStyle;
import ar.com.fdvs.dj.domain.entities.conditionalStyle.StatusLightCondition;
import ar.com.fdvs.dj.test.BaseDjReportTest;
import ar.com.fdvs.dj.test.TestRepositoryProducts;
import ar.com.fdvs.dj.util.SortUtils;

public class CrosstabReportTest8 extends BaseDjReportTest {

	private Style totalHeaderStyle;
	private Style colAndRowHeaderStyle;
	private Style mainHeaderStyle;
	private Style measureStyle;
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

			ArrayList conditionalStyles = createConditionalStyles(measureStyle2);
			DJCrosstabMeasure measure = new DJCrosstabMeasure("amount",Float.class.getName(), DJCalculation.SUM , "Amount");
			measure.setStyle(measureStyle2);
			measure.setConditionalStyles(conditionalStyles);
			
		DJCrosstab djcross = new CrosstabBuilder()
			.setHeight(200)
			.setWidth(500)
			.setHeaderStyle(mainHeaderStyle)
			.setDatasource("sr",DJConstants.DATA_SOURCE_ORIGIN_PARAMETER, DJConstants.DATA_SOURCE_TYPE_COLLECTION)
			.setUseFullWidth(true)
			.setColorScheme(DJConstants.COLOR_SCHEMA_GRAY)
			.setAutomaticTitle(true)
			.setCellBorder(Border.PEN_1_POINT())
			.addRow("Product Line", "productLine", String.class.getName(),false)
			.addColumn("State","state",String.class.getName(),false)
			.addColumn("Branch","branch",String.class.getName(),true)
			.addColumn("Item", "item", String.class.getName(),true)
			.addMeasure("id",Long.class.getName(), DJCalculation.SUM , "Id", measureStyle)
			.addMeasure(measure)
			.setRowStyles(colAndRowHeaderStyle, null, totalHeaderStyle)
			.setColumnStyles(colAndRowHeaderStyle, null, totalHeaderStyle)
			.setCellDimension(34, 60)
			.setColumnHeaderHeight(30)
			.setRowHeaderWidth(80)
			.build();

		drb.addHeaderCrosstab(djcross); //add the crosstab in the header band of the report

		DynamicReport dr = drb.build();

		//put a collection in the parameters map to be used by the crosstab
		params.put("sr", SortUtils.sortCollection(TestRepositoryProducts.getDummyCollection(),djcross));

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
		measureStyle = new StyleBuilder(false).setPattern("#,###.##")
			.setHorizontalAlign(HorizontalAlign.RIGHT)
			.setFont(Font.ARIAL_MEDIUM)
			.build();

		measureStyle2 = new StyleBuilder(false).setPattern("#,###.##")
		.setHorizontalAlign(HorizontalAlign.RIGHT)
		.setFont(new Font(Font.MEDIUM,Font._FONT_ARIAL,false,true,false))
		.setTextColor(Color.RED)
		.build();
	}

	private ArrayList createConditionalStyles(Style baseStyle) throws IllegalAccessException, InstantiationException, InvocationTargetException, NoSuchMethodException {
		Style style0 = (Style) BeanUtils.cloneBean(baseStyle);
		style0.setTextColor(Color.RED);
		style0.setFont(Font.GEORGIA_MEDIUM_BOLD);
		Style style1 = (Style) BeanUtils.cloneBean(baseStyle);
		style1.setTextColor(new Color(128,128,0));
		Style style2 = (Style) BeanUtils.cloneBean(baseStyle);
		style2.setTextColor(new Color(0,128,0)); //dark green
		style2.setFont(Font.ARIAL_SMALL_BOLD);

		StatusLightCondition status0 = new StatusLightCondition(0d, 3000d); //TODO ENHANCEMENT make it come from a parameter??? $P{...}
		StatusLightCondition status1 = new StatusLightCondition(5000d, 6000d);
		StatusLightCondition status2 = new StatusLightCondition(6000d, 100000d);

		ConditionalStyle condition0 = new ConditionalStyle(status0,style0);
		ConditionalStyle condition1 = new ConditionalStyle(status1,style1);
		ConditionalStyle condition2 = new ConditionalStyle(status2,style2);

		ArrayList conditionalStyles = new ArrayList();
		conditionalStyles.add(condition0);
		conditionalStyles.add(condition1);
		conditionalStyles.add(condition2);
		return conditionalStyles;
	}
	
	public static void main(String[] args) throws Exception {
		CrosstabReportTest8 test = new CrosstabReportTest8();
		test.testReport();
		JasperViewer.viewReport(test.jp);	//finally display the report report
		JasperDesignViewer.viewReportDesign(test.jr);
	}

}
