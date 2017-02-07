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


import ar.com.fdvs.dj.domain.DJCalculation;
import ar.com.fdvs.dj.domain.DynamicReport;
import ar.com.fdvs.dj.domain.Style;
import ar.com.fdvs.dj.domain.builders.FastReportBuilder;
import ar.com.fdvs.dj.domain.builders.StyleBuilder;
import ar.com.fdvs.dj.domain.constants.Border;
import ar.com.fdvs.dj.domain.constants.Font;
import ar.com.fdvs.dj.domain.constants.GroupLayout;
import ar.com.fdvs.dj.domain.constants.HorizontalAlign;
import ar.com.fdvs.dj.domain.entities.DJGroupVariable;
import net.sf.jasperreports.view.JasperDesignViewer;
import net.sf.jasperreports.view.JasperViewer;

import java.util.Date;

public class TotalingReportTest extends BaseDjReportTest {

	public DynamicReport buildReport() throws Exception {


		/*
		  Creates the DynamicReportBuilder and sets the basic options for
		  the report
		 */
				
		Style headerStyle1 = new StyleBuilder(false).setFont(Font.ARIAL_BIG).setBorderBottom(Border.THIN()).setPaddingTop(15) .build();
		Style headerStyle2 = new StyleBuilder(false).setFont(Font.ARIAL_SMALL).setPaddingLeft(20).build();
		
		FastReportBuilder drb = new FastReportBuilder();
		drb.addColumn("State", "state", String.class.getName(),70, headerStyle1, headerStyle1)
			.addColumn("Branch", "branch", String.class.getName(),70, headerStyle2, headerStyle2)
			.addColumn("Amount", "amount", Float.class.getName(),70)
			.addGroups(2)
			.setTitle("November " + getYear() +" sales report")
			.setSubtitle("This report was generated at " + new Date())
			.setPrintBackgroundOnOddRows(false)
			.setPrintColumnNames(false)
			.setShowDetailBand(false)
			.setUseFullPageWidth(true);

					
			Style hstyle1 = new StyleBuilder(false).setHorizontalAlign(HorizontalAlign.RIGHT).setBorderBottom(Border.THIN()).build();
			Style hstyle2 = new StyleBuilder(false).setHorizontalAlign(HorizontalAlign.RIGHT).setFont(Font.ARIAL_SMALL).build();
			drb.getGroup(0).addHeaderVariable(new DJGroupVariable(drb.getColumn(2),DJCalculation.SUM,hstyle1));
			drb.getGroup(1).addHeaderVariable(new DJGroupVariable(drb.getColumn(2),DJCalculation.SUM, hstyle2));
			
			drb.getGroup(0).setLayout(GroupLayout.VALUE_IN_HEADER);

		drb.getGroup(0).setHeaderVariablesHeight(20);
		drb.getGroup(1).setHeaderVariablesHeight(15);
			
		DynamicReport dr = drb.build();

		return dr;
	}

	public static void main(String[] args) throws Exception {
		TotalingReportTest test = new TotalingReportTest();
		test.testReport();
		test.exportToJRXML();
		JasperViewer.viewReport(test.jp);	//finally display the report report
		JasperDesignViewer.viewReportDesign(test.jr);
	}

}
