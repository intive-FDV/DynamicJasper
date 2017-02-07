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
import ar.com.fdvs.dj.domain.DynamicReport;
import ar.com.fdvs.dj.domain.Style;
import ar.com.fdvs.dj.domain.builders.ColumnBuilder;
import ar.com.fdvs.dj.domain.builders.DynamicReportBuilder;
import ar.com.fdvs.dj.domain.constants.Border;
import ar.com.fdvs.dj.domain.constants.Font;
import ar.com.fdvs.dj.domain.constants.HorizontalAlign;
import ar.com.fdvs.dj.domain.constants.Transparency;
import ar.com.fdvs.dj.domain.entities.columns.AbstractColumn;

public class TemplateFileReportTest extends BaseDjReportTest {

	public DynamicReport buildReport() throws Exception {

		Style detailStyle = new Style();
		Style headerStyle = new Style();

		headerStyle.setBackgroundColor(new Color(230,230,230));
		headerStyle.setBorderBottom(Border.THIN());
		headerStyle.getBorderBottom().setColor(Color.black);
		headerStyle.setHorizontalAlign(HorizontalAlign.CENTER);
		headerStyle.setTransparency(Transparency.OPAQUE);

		Style titleStyle = new Style();
		titleStyle.setHorizontalAlign(HorizontalAlign.CENTER);
		titleStyle.setFont(Font.ARIAL_BIG_BOLD);
		Style subtitleStyle = new Style();
		Style amountStyle = new Style(); amountStyle.setHorizontalAlign(HorizontalAlign.RIGHT);

		/*
		  Creates the DynamicReportBuilder and sets the basic options for
		  the report
		 */
		DynamicReportBuilder drb = new DynamicReportBuilder();
		drb.setTitle("November " + getYear() +" sales report")					//defines the title of the report
			.setSubtitle("The items in this report correspond "
					+"to the main products: DVDs, Books, Foods and Magazines")
			.setDetailHeight(15)						//defines the height for each record of the report
			.setMargins(30, 20, 30, 15)							//define the margin space for each side (top, bottom, left and right)
			.setDefaultStyles(titleStyle, subtitleStyle, headerStyle, detailStyle)
			.setColumnsPerPage(1);						//defines columns per page (like in the telephone guide)

		/*
		  Note that we didn't call the build() method yet
		 */

		/*
		  Column definitions. We use a new ColumnBuilder instance for each
		  column, the ColumnBuilder.getNew() method returns a new instance
		  of the builder
		 */
		AbstractColumn columnState = ColumnBuilder.getNew()		//creates a new instance of a ColumnBuilder
			.setColumnProperty("state", String.class.getName())			//defines the field of the data source that this column will show, also its type
			.setTitle("State")											//the title for the column
			.setWidth(85)									//the width of the column
			.build();													//builds and return a new AbstractColumn

		//Create more columns
		AbstractColumn columnBranch = ColumnBuilder.getNew()
			.setColumnProperty("branch", String.class.getName())
			.setTitle("Branch").setWidth(85)
			.build();

		AbstractColumn columnaProductLine = ColumnBuilder.getNew()
			.setColumnProperty("productLine", String.class.getName())
			.setTitle("Product Line").setWidth(85)
			.build();

		AbstractColumn columnaItem = ColumnBuilder.getNew()
			.setColumnProperty("item", String.class.getName())
			.setTitle("Item").setWidth(85)
			.build();

		AbstractColumn columnCode = ColumnBuilder.getNew()
			.setColumnProperty("id", Long.class.getName())
			.setTitle("ID").setWidth(40)
			.build();

		AbstractColumn columnaCantidad = ColumnBuilder.getNew()
			.setColumnProperty("quantity", Long.class.getName())
			.setTitle("Quantity").setWidth(80)
			.build();

		AbstractColumn columnAmount = ColumnBuilder.getNew()
			.setColumnProperty("amount", Float.class.getName())
			.setTitle("Amount").setWidth(90)
			.setPattern("$ 0.00")		//defines a pattern to apply to the values swhown (uses TextFormat)
			.setStyle(amountStyle)		//special style for this column (align right)
			.build();

		/*
		  We add the columns to the report (through the builder) in the order
		  we want them to appear
		 */
		drb.addColumn(columnState);
		drb.addColumn(columnBranch);
		drb.addColumn(columnaProductLine);
		drb.addColumn(columnaItem);
		drb.addColumn(columnCode);
		drb.addColumn(columnaCantidad);
		drb.addColumn(columnAmount);

		/*
		  add some more options to the report (through the builder)
		 */
		drb.setUseFullPageWidth(true);	//we tell the report to use the full width of the page. this rezises
										//the columns width proportionally to meet the page width.

		//This look for the resource in the classpath
		drb.setTemplateFile("templates/TemplateReportTest.jrxml");

		//Portrait (looks the resource as a file in the filesystem)
//		drb.setTemplateFile(System.getProperty("user.dir") + "/target/test-classes/templates/TemplateReportTest.jrxml");

		//Landscape  (looks the resource as a file in the filesystem)
//		drb.setTemplateFile(System.getProperty("user.dir") + "/target/test-classes/templates/TemplateReportTestPortLandscape.jrxml");

		DynamicReport dr = drb.build();	//Finally build the report!

		params.put("leftHeader", "DynamicJasper is the Best!!!");
		params.put("rightHeader", "This is the right header");
		
		return dr;
	}

    public static void main(String[] args) throws Exception {
		TemplateFileReportTest test = new TemplateFileReportTest();
		test.testReport();
		JasperViewer.viewReport(test.jp);	//finally display the report report
	}

}
