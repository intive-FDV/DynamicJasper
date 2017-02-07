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
import ar.com.fdvs.dj.domain.builders.GroupBuilder;
import ar.com.fdvs.dj.domain.constants.Font;
import ar.com.fdvs.dj.domain.constants.GroupLayout;
import ar.com.fdvs.dj.domain.constants.HorizontalAlign;
import ar.com.fdvs.dj.domain.entities.DJGroup;
import ar.com.fdvs.dj.domain.entities.columns.AbstractColumn;
import ar.com.fdvs.dj.domain.entities.columns.PropertyColumn;

/**
 * This test aims to generate a report with almost no styles, ensuring that there are no errors
 * as result of the lack of style or fonts
 * 
 * See bug 2817859 at SF
 * 
 * @author mamana
 *
 */
public class StylesReportTest3 extends BaseDjReportTest {

	public DynamicReport buildReport() throws Exception {
		
		DynamicReportBuilder drb = new DynamicReportBuilder();
		Integer margin = 20;
		drb.setTitle("November " + getYear() +" sales report")					//defines the title of the report
			.setSubtitle("The items in this report correspond "
					+"to the main products: DVDs, Books, Foods and Magazines")
			.setTitleHeight(30)
			.setSubtitleHeight(20)
			.setDetailHeight(15)
			.setLeftMargin(margin)
			.setRightMargin(margin)
			.setTopMargin(margin)
			.setBottomMargin(margin)
			.setColumnsPerPage(1)
			.setColumnSpace(5);

		Style style1 = new Style("style1");
		style1.setFont(Font.ARIAL_MEDIUM_BOLD);
		style1.setHorizontalAlign(HorizontalAlign.CENTER);
		drb.addStyle(style1);

		Style style2 = Style.createBlankStyle("style2", "style1");
		style2.setTextColor(Color.BLUE);
		drb.addStyle(style2);
		
		AbstractColumn columnState = ColumnBuilder.getNew().setColumnProperty("state", String.class.getName())
			.setTitle("State").setWidth(new Integer(85))
			.setStyle(style1).build();

		AbstractColumn columnBranch = ColumnBuilder.getNew().setColumnProperty("branch", String.class.getName())
			.setTitle("Branch").setWidth(new Integer(85))
			.setStyle(style2).build();

		drb.addColumn(columnBranch);
		drb.addColumn(columnState);		

		DJGroup g1 = new GroupBuilder()
		.setCriteriaColumn((PropertyColumn) columnBranch)

//		.setGroupLayout(GroupLayout.DEFAULT)
		.setGroupLayout(GroupLayout.DEFAULT_WITH_HEADER)
		.build();
		drb.addGroup(g1);
		
		drb.setUseFullPageWidth(true);

		DynamicReport dr = drb.build();
		return dr;
	}

	public static void main(String[] args) throws Exception {
		StylesReportTest3 test = new StylesReportTest3();
		test.testReport();
		JasperViewer.viewReport(test.jp);
	}

}
