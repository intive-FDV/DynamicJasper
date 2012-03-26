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


import java.util.Date;

import net.sf.jasperreports.view.JasperDesignViewer;
import net.sf.jasperreports.view.JasperViewer;
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

public class TotalingReportTest extends BaseDjReportTest {

	public DynamicReport buildReport() throws Exception {


		/**
		 * Creates the DynamicReportBuilder and sets the basic options for
		 * the report
		 */
				
		Style headerStyle1 = new StyleBuilder(false).setFont(Font.ARIAL_BIG).setBorderBottom(Border.THIN).setPaddingTop(new Integer(15)) .build();
		Style headerStyle2 = new StyleBuilder(false).setFont(Font.ARIAL_SMALL).setPaddingLeft(new Integer(20)).build();
		
		FastReportBuilder drb = new FastReportBuilder();
		drb.addColumn("State", "state", String.class.getName(),70, headerStyle1, headerStyle1)
			.addColumn("Branch", "branch", String.class.getName(),70, headerStyle2, headerStyle2)
			.addColumn("Amount", "amount", Float.class.getName(),70)
			.addGroups(2)
			.setTitle("November 2006 sales report")
			.setSubtitle("This report was generated at " + new Date())
			.setPrintBackgroundOnOddRows(false)
			.setPrintColumnNames(false)
			.setShowDetailBand(false)
			.setUseFullPageWidth(true);

					
			Style hstyle1 = new StyleBuilder(false).setHorizontalAlign(HorizontalAlign.RIGHT).setBorderBottom(Border.THIN).build();
			Style hstyle2 = new StyleBuilder(false).setHorizontalAlign(HorizontalAlign.RIGHT).setFont(Font.ARIAL_SMALL).build();
			drb.getGroup(0).addHeaderVariable(new DJGroupVariable(drb.getColumn(2),DJCalculation.SUM,hstyle1));
			drb.getGroup(1).addHeaderVariable(new DJGroupVariable(drb.getColumn(2),DJCalculation.SUM, hstyle2));
			
			drb.getGroup(0).setLayout(GroupLayout.VALUE_IN_HEADER);

			drb.getGroup(0).setHeaderVariablesHeight(new Integer(20));
			drb.getGroup(1).setHeaderVariablesHeight(new Integer(15));
			
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
