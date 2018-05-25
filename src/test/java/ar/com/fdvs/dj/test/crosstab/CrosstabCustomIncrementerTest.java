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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ar.com.fdvs.dj.core.DJConstants;
import ar.com.fdvs.dj.core.layout.CrossTabColorShema;
import ar.com.fdvs.dj.domain.DJCalculation;
import ar.com.fdvs.dj.domain.DJCrosstab;
import ar.com.fdvs.dj.domain.DJCrosstabMeasure;
import ar.com.fdvs.dj.domain.DJCrosstabTest;
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
import ar.com.fdvs.dj.test.domain.Product;
import junit.framework.Assert;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.fill.AbstractValueProvider;
import net.sf.jasperreports.engine.fill.JRAbstractExtendedIncrementer;
import net.sf.jasperreports.engine.fill.JRAbstractExtendedIncrementerFactory;
import net.sf.jasperreports.engine.fill.JRCalculable;
import net.sf.jasperreports.engine.fill.JRExtendedIncrementer;
import net.sf.jasperreports.engine.fill.JRTemplatePrintFrame;
import net.sf.jasperreports.engine.fill.JRTemplatePrintText;
import net.sf.jasperreports.engine.type.CalculationEnum;
import net.sf.jasperreports.view.JasperDesignViewer;
import net.sf.jasperreports.view.JasperViewer;

/**
 * This uses the main datasource instead of one passed as parameter
 * 
 * @author mamana
 *
 */
public class CrosstabCustomIncrementerTest extends BaseDjReportTest {

	public static class TestIncrementerFactory extends JRAbstractExtendedIncrementerFactory {

		@Override
		public JRExtendedIncrementer getExtendedIncrementer(CalculationEnum calculation) {
			return new JRAbstractExtendedIncrementer() {

				@Override
				public Object initialValue() {
					return null;
				}

				/**
				 * Keep null as null, not zero
				 * 
				 */
				@Override
				public Object increment(JRCalculable calculable, Object expressionValue,
						AbstractValueProvider valueProvider) throws JRException {

					Float initial = (Float) calculable.getIncrementedValue();
					Float value = (Float) expressionValue;

					if (value == null && initial == null) {
						return null;
					}

					return Float.valueOf(
							(initial == null ? 0 : initial.floatValue()) + (value == null ? 0 : value.floatValue()));
				}
			};
		}

	}

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
		
		DJCrosstabMeasure measure = new DJCrosstabMeasure("amount", Float.class.getName(), DJCalculation.SUM , "Amount");
		measure.setStyle(measureStyle2);
		measure.setIncrementerFactoryClassName(TestIncrementerFactory.class.getName());
		
		DJCrosstab djcross = new CrosstabBuilder()
			.setHeight(400)
			.setWidth(500)
			.setHeaderStyle(mainHeaderStyle)
			.setDatasource("sr",DJConstants.DATA_SOURCE_ORIGIN_PARAMETER, DJConstants.DATA_SOURCE_TYPE_COLLECTION)
			.setUseFullWidth(true)
			.setColorScheme(colorScheme)
			.setAutomaticTitle(true)
			.setCellBorder(Border.PEN_1_POINT())
			.addRow("State","state",String.class.getName(),true)
			.addRow("Branch","branch",String.class.getName(),true)
			.addColumn("Product Line", "productLine", String.class.getName(),true)
			.addColumn("Item", "item", String.class.getName(),true)
			.addMeasure(measure)
			.setRowStyles(rowHeaderStyle, totalStyle, totalHeaderStyle)
			.setColumnStyles(colHeaderStyle, totalStyle, totalHeaderStyle)
			.setCellDimension(34, 60)
			.setColumnHeaderHeight(30)
			.setRowHeaderWidth(80)
			.build();

		drb.addFooterCrosstab(djcross); //add the crosstab in the summary band of the report

		DynamicReport dr = drb.build();

		//put a collection in the parameters map to be used by the crosstab
		params.put("sr", getData());

		return dr;
	}

	/**
	 *
	 */
	private void initStyles() {
		titleStyle = new StyleBuilder(false).setFont(Font.ARIAL_BIG_BOLD).setHorizontalAlign(HorizontalAlign.LEFT)
				.setVerticalAlign(VerticalAlign.MIDDLE).setTransparency(Transparency.OPAQUE)
				.setBorderBottom(Border.PEN_2_POINT()).build();

		totalHeaderStyle = new StyleBuilder(false).setHorizontalAlign(HorizontalAlign.CENTER)
				.setVerticalAlign(VerticalAlign.MIDDLE).setFont(Font.ARIAL_MEDIUM_BOLD)
				.setTransparency(Transparency.OPAQUE).setTextColor(Color.BLUE).setBackgroundColor(Color.GREEN).build();
		rowHeaderStyle = new StyleBuilder(false).setHorizontalAlign(HorizontalAlign.LEFT)
				.setVerticalAlign(VerticalAlign.TOP).setFont(Font.ARIAL_MEDIUM_BOLD)
				.setBackgroundColor(new Color(240, 248, 255)).setBackgroundColor(Color.BLUE).build();
		colHeaderStyle = new StyleBuilder(false).setHorizontalAlign(HorizontalAlign.LEFT)
				.setVerticalAlign(VerticalAlign.TOP).setFont(Font.ARIAL_MEDIUM_BOLD)
				.setBackgroundColor(new Color(255, 240, 248)).setBackgroundColor(Color.RED).build();
		mainHeaderStyle = new StyleBuilder(false).setHorizontalAlign(HorizontalAlign.CENTER)
				.setVerticalAlign(VerticalAlign.MIDDLE).setFont(Font.ARIAL_BIG_BOLD).setTextColor(Color.BLACK).build();
		totalStyle = new StyleBuilder(false).setPattern("#,###.##").setHorizontalAlign(HorizontalAlign.RIGHT)
				.setFont(Font.ARIAL_MEDIUM_BOLD).build();
		Style measureStyle = new StyleBuilder(false).setPattern("#,###.##").setHorizontalAlign(HorizontalAlign.RIGHT)
				.setFont(Font.ARIAL_MEDIUM).setBackgroundColor(Color.WHITE).build();

		measureStyle2 = new StyleBuilder(false).setPattern("#,###.##").setHorizontalAlign(HorizontalAlign.RIGHT)
				.setFont(new Font(Font.MEDIUM, Font._FONT_ARIAL, false, true, false)).setTextColor(Color.RED).build();
	}

	protected List<Product> getData() {
		// 1 entry
		List<Product> col = new ArrayList<Product>(2);

		col.add(new Product(Long.valueOf(1), "book", "Harry Potter 7", "Florida", "Main Street", Long.valueOf(2500),
				null));
		col.add(new Product(Long.valueOf(1), "book", "Harry Potter 7", "Florida", "Railway Station", Long.valueOf(1400),
				Float.valueOf(2831.32f)));

		return col;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ar.com.fdvs.dj.test.BaseDjReportTest#testReport()
	 */
	@Override
	public void testReport() throws Exception {
		super.testReport();

		JRTemplatePrintFrame crosstab = (JRTemplatePrintFrame) jp.getPages().get(0).getElements().get(3);

		// | 0 |___1___| 2 |
		// |_______|_3_|_4_|___|
		// | 5 |_6_|_7_|_8_|_9_|
		// | |_10|_11|_12|_13|
		// |___|_14|_15|_16|_17|
		// |___18__|_19|_20|_21|

		// main street is empty, not "0"

		int i = 0;
		Assert.assertEquals("Product Line, Item\nvs.\nState, Branch", getCellText(crosstab, i++));
		Assert.assertEquals("book", getCellText(crosstab, i++));
		Assert.assertEquals("Total Product Line", getCellText(crosstab, i++));

		Assert.assertEquals("Harry Potter 7", getCellText(crosstab, i++));
		Assert.assertEquals("Total Item", getCellText(crosstab, i++));

		Assert.assertEquals("Florida", getCellText(crosstab, i++));
		Assert.assertEquals("Main Street", getCellText(crosstab, i++));
		Assert.assertEquals("", getCellText(crosstab, i++));
		Assert.assertEquals("", getCellText(crosstab, i++));
		Assert.assertEquals("", getCellText(crosstab, i++));

		Assert.assertEquals("Railway Station", getCellText(crosstab, i++));
		Assert.assertEquals("2,831.32", getCellText(crosstab, i++));
		Assert.assertEquals("2,831.32", getCellText(crosstab, i++));
		Assert.assertEquals("2,831.32", getCellText(crosstab, i++));

		Assert.assertEquals("Total Branch", getCellText(crosstab, i++));
		Assert.assertEquals("2,831.32", getCellText(crosstab, i++));
		Assert.assertEquals("2,831.32", getCellText(crosstab, i++));
		Assert.assertEquals("2,831.32", getCellText(crosstab, i++));

		Assert.assertEquals("Total State", getCellText(crosstab, i++));
		Assert.assertEquals("2,831.32", getCellText(crosstab, i++));
		Assert.assertEquals("2,831.32", getCellText(crosstab, i++));
		Assert.assertEquals("2,831.32", getCellText(crosstab, i++));

	}

	/**
	 * Get crosstab cell by index, numbered LtR/TtB
	 * 
	 * @param crosstab
	 *            the crosstab frame
	 * @param cellIndex
	 *            the cell index
	 * @return the text found
	 */
	public String getCellText(JRTemplatePrintFrame crosstab, int cellIndex) {
		JRTemplatePrintFrame cell8 = (JRTemplatePrintFrame) crosstab.getElements().get(cellIndex + 1);
		String cellText = ((JRTemplatePrintText) cell8.getElements().get(0)).getFullText();
		return cellText;
	}

	public static void main(String[] args) throws Exception {
		CrosstabCustomIncrementerTest test = new CrosstabCustomIncrementerTest();
		test.testReport();
		JasperViewer.viewReport(test.jp); // finally display the report report
		JasperDesignViewer.viewReportDesign(test.jr);
	}

}
