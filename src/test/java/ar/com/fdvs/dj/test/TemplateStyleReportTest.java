/*
 * Copyright (c) 2012, FDV Solutions S.A.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *     * Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 *     * Neither the name of the FDV Solutions S.A. nor the
 *       names of its contributors may be used to endorse or promote products
 *       derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL FDV Solutions S.A. BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
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

public class TemplateStyleReportTest extends BaseDjReportTest {

	public DynamicReport buildReport() throws Exception {

//		Style detailStyle = new Style();
		Style headerStyle = new Style();

		headerStyle.setBackgroundColor(new Color(230,230,230));
		headerStyle.setBorderBottom(Border.THIN);
		headerStyle.setBorderColor(Color.black);
		headerStyle.setHorizontalAlign(HorizontalAlign.CENTER);
		headerStyle.setTransparency(Transparency.OPAQUE);

		/**
		 * "titleStyle" exists in the template .jrxml file
		 * The title should be seen in a big font size, violet foreground and light green background
		 */
		Style titleStyle = new Style("titleStyle");

		/**
		 * "subtitleStyleParent" is meant to be used as a parent style, while
		 * "subtitleStyle" is the child.
		 */
		Style subtitleStyleParent = new Style("subtitleParent");
		subtitleStyleParent.setBackgroundColor(Color.CYAN);
		subtitleStyleParent.setTransparency(Transparency.OPAQUE);

		Style subtitleStyle = Style.createBlankStyle("subtitleStyle","subtitleParent");
		subtitleStyle.setFont(Font.GEORGIA_SMALL_BOLD);

		Style amountStyle = new Style(); amountStyle.setHorizontalAlign(HorizontalAlign.RIGHT);

		/**
		 * Creates the DynamicReportBuilder and sets the basic options for
		 * the report
		 */
		DynamicReportBuilder drb = new DynamicReportBuilder();
		drb.setTitle("November 2006 sales report")
			.setSubtitle("The items in this report correspond "
					+"to the main products: DVDs, Books, Foods and Magazines")
			.setDetailHeight(15)
			.setMargins(30, 20, 30, 15)
			.setDefaultStyles(titleStyle, subtitleStyle, headerStyle, null)
			.addStyle(subtitleStyleParent); //register the parent style


		/**
		 * Note that we didn't call the build() method yet
		 */

		/**
		 * Column definitions. We use a new ColumnBuilder instance for each
		 * column, the ColumnBuilder.getNew() method returns a new instance
		 * of the builder
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

		/**
		 * We add the columns to the report (through the builder) in the order
		 * we want them to appear
		 */
		drb.addColumn(columnState);
		drb.addColumn(columnBranch);
		drb.addColumn(columnaProductLine);
		drb.addColumn(columnaItem);
		drb.addColumn(columnCode);
		drb.addColumn(columnaCantidad);
		drb.addColumn(columnAmount);

		/**
		 * add some more options to the report (through the builder)
		 */
		drb.setUseFullPageWidth(true);	//we tell the report to use the full width of the page. this rezises
										//the columns width proportionally to meet the page width.

		//This look for the resource in the classpath
		//drb.setTemplateFile("templates/TemplateReportTestWithStyles2.jrxml");

		DynamicReport dr = drb.build();	//Finally build the report!

		params.put("leftHeader", "My Company - My Area");
		params.put("rightHeader", "User: Jack Sparrow");
		return dr;
	}

    public static void main(String[] args) throws Exception {
		TemplateStyleReportTest test = new TemplateStyleReportTest();
		test.testReport();
		JasperViewer.viewReport(test.jp);	//finally display the report report
	}

}
