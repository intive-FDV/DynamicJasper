/*
 * Dynamic Jasper: A library for creating reports dynamically by specifying
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

import ar.com.fdvs.dj.core.DynamicJasperHelper;
import ar.com.fdvs.dj.core.layout.ClassicLayoutManager;
import ar.com.fdvs.dj.domain.ColumnsGroupVariableOperation;
import ar.com.fdvs.dj.domain.DynamicReport;
import ar.com.fdvs.dj.domain.Style;
import ar.com.fdvs.dj.domain.builders.ColumnBuilder;
import ar.com.fdvs.dj.domain.builders.DynamicReportBuilder;
import ar.com.fdvs.dj.domain.builders.GroupBuilder;
import ar.com.fdvs.dj.domain.constants.Border;
import ar.com.fdvs.dj.domain.constants.Font;
import ar.com.fdvs.dj.domain.constants.HorizontalAlign;
import ar.com.fdvs.dj.domain.constants.Rotation;
import ar.com.fdvs.dj.domain.constants.Transparency;
import ar.com.fdvs.dj.domain.constants.VerticalAlign;
import ar.com.fdvs.dj.domain.entities.ColumnsGroup;
import ar.com.fdvs.dj.domain.entities.columns.AbstractColumn;
import ar.com.fdvs.dj.domain.entities.columns.PropertyColumn;
import ar.com.fdvs.dj.util.SortUtils;
import junit.framework.TestCase;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.view.JasperViewer;
import org.apache.commons.beanutils.BeanUtils;

import java.awt.Color;
import java.util.Collection;

public class StylesReport2Test extends TestCase {

	public DynamicReport buildReport() throws Exception {

		Style detailStyle = new Style();

		Style headerStyle = new Style();
		headerStyle.setFont(Font.ARIAL_MEDIUM_BOLD);
		headerStyle.getFont().setItalic(true);
		headerStyle.setBorderTop(Border.PEN_2_POINT);
		headerStyle.setBorderBottom(Border.THIN);
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
		amountStyle.setPaddingBotton(new Integer(5));
		Style oddRowStyle = new Style();
		oddRowStyle.setBorder(Border.NO_BORDER);
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
		Integer margin = new Integer(20);
		drb.setTitle("November 2006 sales report")					//defines the title of the report
			.setSubtitle("The items in this report correspond "
					+"to the main products: DVDs, Books, Foods and Magazines")
			.setTitleStyle(titleStyle).setTitleHeight(new Integer(30))
			.setSubtitleHeight(new Integer(20))
			.setDetailHeight(new Integer(15))
			.setLeftMargin(margin)
			.setRightMargin(margin)
			.setTopMargin(margin)
			.setBottomMargin(margin)
			.setPrintBackgroundOnOddRows(true)
			.setOddRowBackgroundStyle(oddRowStyle)
			.setColumnsPerPage(new Integer(1))
			.setColumnSpace(new Integer(5));

		AbstractColumn columnState = ColumnBuilder.getInstance().setColumnProperty("state", String.class.getName())
			.setTitle("State").setWidth(new Integer(85))
			.setStyle(detailStyle).setHeaderStyle(headerStyle).build();

		AbstractColumn columnBranch = ColumnBuilder.getInstance().setColumnProperty("branch", String.class.getName())
			.setTitle("Branch").setWidth(new Integer(85))
			.setStyle(detailStyle).setHeaderStyle(headerStyle).build();

		AbstractColumn columnaProductLine = ColumnBuilder.getInstance().setColumnProperty("productLine", String.class.getName())
			.setTitle("Product Line").setWidth(new Integer(85))
			.setStyle(detailStyle).setHeaderStyle(headerStyle).build();

		AbstractColumn columnaItem = ColumnBuilder.getInstance().setColumnProperty("item", String.class.getName())
			.setTitle("item").setWidth(new Integer(85))
			.setStyle(detailStyle).setHeaderStyle(headerStyle).build();

		AbstractColumn columnCode = ColumnBuilder.getInstance().setColumnProperty("id", Long.class.getName())
			.setTitle("ID").setWidth(new Integer(40))
			.setStyle(numberStyle).setHeaderStyle(headerStyle).build();

		AbstractColumn columnaCantidad = ColumnBuilder.getInstance().setColumnProperty("quantity", Long.class.getName())
			.setTitle("Quantity").setWidth(new Integer(80))
			.setStyle(numberStyle).setHeaderStyle(headerStyle).build();

		AbstractColumn columnAmount = ColumnBuilder.getInstance().setColumnProperty("amount", Float.class.getName())
			.setTitle("Amount").setWidth(new Integer(90)).setPattern("$ 0.00")
			.setStyle(amountStyle).setHeaderStyle(headerStyle).build();

		drb.addColumn(columnState);
		drb.addColumn(columnBranch);
		drb.addColumn(columnaProductLine);
		drb.addColumn(columnaItem);
		drb.addColumn(columnCode);
		drb.addColumn(columnaCantidad);
		drb.addColumn(columnAmount);

		ColumnsGroup group = new GroupBuilder()
			.setCriteriaColumn((PropertyColumn) columnState)
			.addFooterVariable(columnAmount, ColumnsGroupVariableOperation.SUM,variableStyle).build();
		drb.addGroup(group);

		ColumnsGroup group2 = new GroupBuilder()
		.setCriteriaColumn((PropertyColumn) columnBranch)
		.addFooterVariable(columnAmount, ColumnsGroupVariableOperation.SUM).build();
		drb.addGroup(group2);

		group2.setDefaulFooterStyle(variableStyle2);

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

	public void testReport() {
		try {
			DynamicReport dr = buildReport();
			Collection dummyCollection = TestRepositoryProducts.getDummyCollection();
			dummyCollection = SortUtils.sortCollection(dummyCollection,dr.getColumns());

//			saveXML(dummyCollection,"reportData");
			JRDataSource ds = new JRBeanCollectionDataSource(dummyCollection);
			JasperPrint jp = DynamicJasperHelper.generateJasperPrint(dr, new ClassicLayoutManager(), ds);
			ReportExporter.exportReport(jp, System.getProperty("user.dir")+ "/target/StylesReport2Test.pdf");
			JasperViewer.viewReport(jp);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		StylesReport2Test test = new StylesReport2Test();
		test.testReport();
	}

}
