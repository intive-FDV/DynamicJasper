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

package ar.com.fdvs.dj.test.groups.labels;

import java.awt.Color;

import net.sf.jasperreports.engine.type.StretchTypeEnum;
import net.sf.jasperreports.engine.type.TextAdjustEnum;
import net.sf.jasperreports.view.JasperDesignViewer;
import net.sf.jasperreports.view.JasperViewer;
import ar.com.fdvs.dj.domain.AutoText;
import ar.com.fdvs.dj.domain.DJCalculation;
import ar.com.fdvs.dj.domain.DJGroupLabel;
import ar.com.fdvs.dj.domain.DynamicReport;
import ar.com.fdvs.dj.domain.ImageBanner;
import ar.com.fdvs.dj.domain.Style;
import ar.com.fdvs.dj.domain.builders.ColumnBuilder;
import ar.com.fdvs.dj.domain.builders.DynamicReportBuilder;
import ar.com.fdvs.dj.domain.builders.GroupBuilder;
import ar.com.fdvs.dj.domain.builders.StyleBuilder;
import ar.com.fdvs.dj.domain.constants.Border;
import ar.com.fdvs.dj.domain.constants.Font;
import ar.com.fdvs.dj.domain.constants.GroupLayout;
import ar.com.fdvs.dj.domain.constants.HorizontalImageAlign;
import ar.com.fdvs.dj.domain.constants.HorizontalTextAlign;
import ar.com.fdvs.dj.domain.constants.Transparency;
import ar.com.fdvs.dj.domain.constants.VerticalImageAlign;
import ar.com.fdvs.dj.domain.constants.VerticalTextAlign;
import ar.com.fdvs.dj.domain.entities.DJGroup;
import ar.com.fdvs.dj.domain.entities.columns.AbstractColumn;
import ar.com.fdvs.dj.domain.entities.columns.PropertyColumn;
import ar.com.fdvs.dj.test.BaseDjReportTest;

public class GroupLabelTest1b extends BaseDjReportTest {

	public DynamicReport buildReport() throws Exception {

		Style detailStyle = new Style();

		Style headerStyle = new Style();
		headerStyle.setFont(Font.ARIAL_MEDIUM_BOLD);
		headerStyle.setBorderBottom(Border.PEN_1_POINT());
		headerStyle.setBackgroundColor(Color.gray);
		headerStyle.setTextColor(Color.white);
		headerStyle.setHorizontalTextAlign(HorizontalTextAlign.CENTER);
		headerStyle.setHorizontalImageAlign(HorizontalImageAlign.CENTER);
		headerStyle.setVerticalTextAlign(VerticalTextAlign.MIDDLE);
		headerStyle.setVerticalImageAlign(VerticalImageAlign.MIDDLE);

		headerStyle.setTransparency(Transparency.OPAQUE);

		Style headerVariables = new Style();
		headerVariables.setFont(Font.ARIAL_MEDIUM_BOLD);
//		headerVariables.setBorder(Border.THIN());
		headerVariables.setHorizontalTextAlign(HorizontalTextAlign.RIGHT);
		headerVariables.setHorizontalImageAlign(HorizontalImageAlign.RIGHT);		
		headerVariables.setStretchType(StretchTypeEnum.NO_STRETCH);
		headerVariables.setVerticalTextAlign(VerticalTextAlign.TOP);
		headerVariables.setVerticalImageAlign(VerticalImageAlign.TOP);

		Style titleStyle = new Style();
		titleStyle.setFont(new Font(18, Font._FONT_VERDANA, true));
		Style importeStyle = new Style();
		importeStyle.setHorizontalTextAlign(HorizontalTextAlign.RIGHT);
		importeStyle.setHorizontalImageAlign(HorizontalImageAlign.RIGHT);
		Style oddRowStyle = new Style();
		oddRowStyle.setBorder(Border.NO_BORDER());
		oddRowStyle.setBackgroundColor(Color.LIGHT_GRAY);
		oddRowStyle.setTransparency(Transparency.OPAQUE);

		DynamicReportBuilder drb = new DynamicReportBuilder();
		Integer margin = 20;
		drb
			.setTitleStyle(titleStyle)
			.setTitle("November " + getYear() +" sales report")					//defines the title of the report
			.setSubtitle("The items in this report correspond "
					+"to the main products: DVDs, Books, Foods and Magazines")
			.setDetailHeight(15).setLeftMargin(margin)
			.setRightMargin(margin).setTopMargin(margin).setBottomMargin(margin)
			.setPrintBackgroundOnOddRows(false)
			.setGrandTotalLegend("Grand Total")
			.setGrandTotalLegendStyle(headerVariables)
			.setDefaultStyles(titleStyle, null, headerStyle, detailStyle)
			.setPrintColumnNames(true)
			.addImageBanner(System.getProperty("user.dir") +"/target/test-classes/images/logo_fdv_solutions_60.png", 100, 30, ImageBanner.Alignment.Right)
			.setOddRowBackgroundStyle(oddRowStyle);


		AbstractColumn columnState = ColumnBuilder.getNew()
				.setColumnProperty("state", String.class.getName())
				.setTitle("State").setWidth(new Integer(85))
				.setStyle(titleStyle).setHeaderStyle(titleStyle)
				.build();

		AbstractColumn columnBranch = ColumnBuilder.getNew()
				.setColumnProperty("branch", String.class.getName())
				.setTitle("Branch").setWidth(new Integer(85))
				.setStyle(detailStyle).setHeaderStyle(headerStyle)
				.build();

		AbstractColumn columnaProductLine = ColumnBuilder.getNew()
				.setColumnProperty("productLine", String.class.getName())
				.setTitle("Product Line").setWidth(new Integer(85))
				.setStyle(detailStyle).setHeaderStyle(headerStyle)
				.build();

		AbstractColumn columnaQuantity = ColumnBuilder.getNew()
				.setColumnProperty("quantity", Long.class.getName())
				.setTitle("Quantity").setWidth(new Integer(80))
				.setStyle(importeStyle).setHeaderStyle(headerStyle)
				.build();

		AbstractColumn columnAmount = ColumnBuilder.getNew()
				.setColumnProperty("amount", Float.class.getName())
				.setTitle("Amount").setWidth(new Integer(90)).setPattern("$ 0.00")
				.setStyle(importeStyle).setHeaderStyle(headerStyle)
				.build();


		GroupBuilder gb1 = new GroupBuilder();

		Style glabelStyle = new StyleBuilder(false).setFont(Font.ARIAL_SMALL)
			.setHorizontalTextAlign(HorizontalTextAlign.RIGHT)
			.setHorizontalImageAlign(HorizontalImageAlign.RIGHT)
			.setBorderTop(Border.THIN())
			.setTextAdjust(TextAdjustEnum.CUT_TEXT)
			.build();
		
		DJGroupLabel glabel1 = new DJGroupLabel("Total amount",glabelStyle);
		DJGroupLabel glabel2 = new DJGroupLabel("Total quantity",glabelStyle);
		
		//		 define the criteria column to group by (columnState)
		DJGroup g1 = gb1.setCriteriaColumn((PropertyColumn) columnState)
				.setHeaderHeight(30)
				.addHeaderVariable(columnAmount,DJCalculation.SUM,headerVariables, null, glabel1) // tell the group place a variable footer of the column "columnAmount" with the SUM of all values of the columnAmount in this group.
				.addHeaderVariable(columnaQuantity,DJCalculation.SUM,headerVariables, null, glabel2) // idem for the columnaQuantity column
				.setGroupLayout(GroupLayout.VALUE_IN_HEADER) // tells the group how to be shown, there are manyposibilities, see the GroupLayout for more.
				.build();


		drb.addColumn(columnState);
		drb.addColumn(columnBranch);
		drb.addColumn(columnaProductLine);
		drb.addColumn(columnaQuantity);
		drb.addColumn(columnAmount);
		
		drb.addGlobalFooterVariable(columnAmount, DJCalculation.SUM, headerVariables, null);
		drb.addGlobalFooterVariable(columnaQuantity, DJCalculation.SUM, headerVariables, null);

		drb.addGroup(g1); // add group g1

		drb.setUseFullPageWidth(true);
		drb.addAutoText(AutoText.AUTOTEXT_PAGE_X_SLASH_Y, AutoText.POSITION_FOOTER, AutoText.ALIGNMENT_RIGHT);

		DynamicReport dr = drb.build();
		return dr;
	}

	public static void main(String[] args) throws Exception {
		GroupLabelTest1b test = new GroupLabelTest1b();
		test.testReport();
		test.exportToJRXML();
		JasperViewer.viewReport(test.jp);
		JasperDesignViewer.viewReportDesign(test.jr);
	}

}