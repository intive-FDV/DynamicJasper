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

package ar.com.fdvs.dj.test;

import java.awt.Color;

import net.sf.jasperreports.view.JasperViewer;

import org.apache.commons.beanutils.BeanUtils;

import ar.com.fdvs.dj.domain.DJCalculation;
import ar.com.fdvs.dj.domain.DynamicReport;
import ar.com.fdvs.dj.domain.Style;
import ar.com.fdvs.dj.domain.builders.ColumnBuilder;
import ar.com.fdvs.dj.domain.builders.DynamicReportBuilder;
import ar.com.fdvs.dj.domain.builders.GroupBuilder;
import ar.com.fdvs.dj.domain.builders.StyleBuilder;
import ar.com.fdvs.dj.domain.constants.Border;
import ar.com.fdvs.dj.domain.constants.Font;
import ar.com.fdvs.dj.domain.constants.HorizontalAlign;
import ar.com.fdvs.dj.domain.constants.Rotation;
import ar.com.fdvs.dj.domain.constants.Transparency;
import ar.com.fdvs.dj.domain.constants.VerticalAlign;
import ar.com.fdvs.dj.domain.entities.DJGroup;
import ar.com.fdvs.dj.domain.entities.columns.AbstractColumn;
import ar.com.fdvs.dj.domain.entities.columns.PropertyColumn;

public class StylesReport2Test extends BaseDjReportTest {

	public DynamicReport buildReport() throws Exception {

//		Style detailStyle = new Style();
		Style detailStyle = new StyleBuilder(false).setTransparency(Transparency.OPAQUE).setBackgroundColor(new Color(200,200,230)).build();

		Style headerStyle = new Style();
		headerStyle.setFont(Font.ARIAL_MEDIUM_BOLD);
		headerStyle.getFont().setItalic(true);
		headerStyle.setBorderTop(Border.PEN_2_POINT());
		headerStyle.setBorderBottom(Border.THIN());
		headerStyle.setBackgroundColor(Color.blue);
		headerStyle.setTransparency(Transparency.OPAQUE);
		headerStyle.setTextColor(Color.white);
		headerStyle.setHorizontalAlign(HorizontalAlign.CENTER);
		headerStyle.setVerticalAlign(VerticalAlign.MIDDLE);
		headerStyle.setRotation(Rotation.LEFT);

		Style titleStyle = new Style();
		titleStyle.setFont(new Font(10,Font._FONT_VERDANA,true));
		Style numberStyle = new Style();
		numberStyle.setHorizontalAlign(HorizontalAlign.RIGHT);
		Style amountStyle = new Style();
		amountStyle.setHorizontalAlign(HorizontalAlign.RIGHT);
		amountStyle.setBackgroundColor(Color.cyan);
		amountStyle.setTransparency(Transparency.OPAQUE);
		amountStyle.setFont(Font.ARIAL_MEDIUM_BOLD);
		amountStyle.getFont().setUnderline(true);
		amountStyle.setPaddingBottom(5);
		Style oddRowStyle = new Style();
		oddRowStyle.setBorder(Border.NO_BORDER());
		Color veryLightGrey = new Color(230,230,230);
		oddRowStyle.setBackgroundColor(veryLightGrey);oddRowStyle.setTransparency(Transparency.OPAQUE);

		Style variableStyle = new Style();
		BeanUtils.copyProperties(variableStyle, amountStyle);
		variableStyle.setFont(Font.ARIAL_MEDIUM_BOLD);
		variableStyle.setBackgroundColor(Color.PINK);

		Style variableStyle2 = new Style();
		BeanUtils.copyProperties(variableStyle2, amountStyle);
		variableStyle2.setFont(Font.ARIAL_MEDIUM_BOLD);
		variableStyle2.setBackgroundColor(Color.ORANGE);


		DynamicReportBuilder drb = new DynamicReportBuilder();
		Integer margin = 20;
		
		drb.setTitle("November " + getYear() +" sales report")					//defines the title of the report
			.setSubtitle("The items in this report correspond "
					+"to the main products: DVDs, Books, Foods and Magazines")
			.setTitleStyle(titleStyle).setTitleHeight(30)
			.setDefaultStyles(null, null, null, detailStyle)
			.setSubtitleHeight(20)
			.setDetailHeight(15)
//			.setLeftMargin(margin)
//			.setRightMargin(margin)
//			.setTopMargin(margin)
//			.setBottomMargin(margin)
			.setPrintBackgroundOnOddRows(true)
			.setOddRowBackgroundStyle(oddRowStyle)
			.setColumnsPerPage(1)
			.setColumnSpace(5);

		AbstractColumn columnState = ColumnBuilder.getNew().setColumnProperty("state", String.class.getName())
			.setTitle("State").setWidth(new Integer(85))
			.setStyle(detailStyle).setHeaderStyle(headerStyle).build();

		AbstractColumn columnBranch = ColumnBuilder.getNew().setColumnProperty("branch", String.class.getName())
			.setTitle("Branch").setWidth(new Integer(85))
			.setStyle(detailStyle).setHeaderStyle(headerStyle).build();

		AbstractColumn columnaProductLine = ColumnBuilder.getNew().setColumnProperty("productLine", String.class.getName())
			.setTitle("Product Line").setWidth(new Integer(85))
			.setStyle(detailStyle).setHeaderStyle(headerStyle).build();

		AbstractColumn columnaItem = ColumnBuilder.getNew().setColumnProperty("item", String.class.getName())
			.setTitle("item").setWidth(new Integer(85))
			.setStyle(detailStyle).setHeaderStyle(headerStyle).build();

		AbstractColumn columnCode = ColumnBuilder.getNew().setColumnProperty("id", Long.class.getName())
			.setTitle("ID").setWidth(new Integer(40))
			.setStyle(numberStyle).setHeaderStyle(headerStyle).build();

		AbstractColumn columnaCantidad = ColumnBuilder.getNew().setColumnProperty("quantity", Long.class.getName())
			.setTitle("Quantity").setWidth(new Integer(80))
			.setStyle(numberStyle).setHeaderStyle(headerStyle).build();

		AbstractColumn columnAmount = ColumnBuilder.getNew().setColumnProperty("amount", Float.class.getName())
			.setTitle("Amount").setWidth(new Integer(90)).setPattern("$ 0.00")
			.setStyle(amountStyle).setHeaderStyle(headerStyle).build();

		drb.addColumn(columnState);
		drb.addColumn(columnaItem);
		drb.addColumn(columnBranch);
		drb.addColumn(columnaProductLine);
		drb.addColumn(columnCode);
		drb.addColumn(columnaCantidad);
		drb.addColumn(columnAmount);

		DJGroup group = new GroupBuilder()
			.setCriteriaColumn((PropertyColumn) columnState)
			.addFooterVariable(columnAmount, DJCalculation.SUM,variableStyle).build();
		drb.addGroup(group);

		DJGroup group2 = new GroupBuilder()
		.setCriteriaColumn((PropertyColumn) columnaItem)
		.addFooterVariable(columnAmount, DJCalculation.SUM).build();
		drb.addGroup(group2);

		group2.setDefaulFooterVariableStyle(variableStyle2);

		drb.setUseFullPageWidth(true);

		DynamicReport dr = drb.build();
//		saveXML(dr,"dynamicReport");
		return dr;
	}

//	private static void saveXML(Object object, String filename) throws Exception {
//
//		OutputStream out = new FileOutputStream(System.getProperty("user.dir")+ "/target/" + filename +".xml");
//		XMLEncoder enc = new XMLEncoder(out);
//		enc.writeObject(object);
//		enc.close();
//		out.close();
//
//	}

	public static void main(String[] args) throws Exception {
		StylesReport2Test test = new StylesReport2Test();
		test.testReport();
		JasperViewer.viewReport(test.jp);
	}

}
