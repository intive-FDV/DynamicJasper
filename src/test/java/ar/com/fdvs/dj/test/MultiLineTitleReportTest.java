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


import java.util.Date;

import net.sf.jasperreports.view.JasperDesignViewer;
import net.sf.jasperreports.view.JasperViewer;
import ar.com.fdvs.dj.domain.DynamicReport;
import ar.com.fdvs.dj.domain.Style;
import ar.com.fdvs.dj.domain.builders.FastReportBuilder;
import ar.com.fdvs.dj.domain.constants.Stretching;
import ar.com.fdvs.dj.domain.constants.VerticalAlign;

/**
 * This test intends to show how to create multi line title and sub title
 * @author mamana
 *
 */
public class MultiLineTitleReportTest extends BaseDjReportTest {

	public DynamicReport buildReport() throws Exception {


		/*
		  Creates the DynamicReportBuilder and sets the basic options for
		  the report
		 */
		FastReportBuilder drb = new FastReportBuilder();
		Style subtitleStyle = new Style("subtitle_style");
		subtitleStyle.setStretchWithOverflow(true);
		subtitleStyle.setStreching(Stretching.NO_STRETCH);
		subtitleStyle.setVerticalAlign(VerticalAlign.TOP);
		
		drb.addColumn("State", "state", String.class.getName(),30)
			.addColumn("Branch", "branch", String.class.getName(),30)
			.addColumn("Product Line", "productLine", String.class.getName(),50)
			.addColumn("Item", "item", String.class.getName(),50)
			.addColumn("Item Code", "id", Long.class.getName(),30,true)
			.addColumn("Quantity", "quantity", Long.class.getName(),60,true)
			.addColumn("Amount", "amount", Float.class.getName(),70,true)
			.addGroups(2)
			.setTitle("November " + getYear() +"\\nsales report")
			.setSubtitle("This report was generated at" + new Date() + "\\n*** This information is confidential ***")
			.setSubtitleHeight(20)
			.setSubtitleStyle(subtitleStyle)
			.setUseFullPageWidth(true);

		DynamicReport dr = drb.build();

		return dr;
	}

	public static void main(String[] args) throws Exception {
		MultiLineTitleReportTest test = new MultiLineTitleReportTest();
		test.testReport();
		test.exportToJRXML();
		JasperViewer.viewReport(test.jp);	//finally display the report report
		JasperDesignViewer.viewReportDesign(test.jr);
	}

}
