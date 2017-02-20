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

import ar.com.fdvs.dj.core.DynamicJasperHelper;
import ar.com.fdvs.dj.domain.AutoText;
import ar.com.fdvs.dj.domain.DynamicReport;
import ar.com.fdvs.dj.domain.Style;
import ar.com.fdvs.dj.domain.builders.FastReportBuilder;
import ar.com.fdvs.dj.domain.builders.StyleBuilder;
import ar.com.fdvs.dj.domain.constants.Font;
import net.sf.jasperreports.view.JasperDesignViewer;
import net.sf.jasperreports.view.JasperViewer;

import java.util.Date;
import java.util.HashMap;

public class BuiltinFontsReportTest extends BaseDjReportTest {

	public DynamicReport buildReport() throws Exception {

		FastReportBuilder drb = new FastReportBuilder();
		drb.addColumn("State", "state", String.class.getName(),30)
			.addColumn("Branch", "branch", String.class.getName(),30)
			.addColumn("Product Line", "productLine", String.class.getName(),50)
			.addColumn("Item", "item", String.class.getName(),50)
			.addColumn("Item Code", "id", Long.class.getName(),30,true)
			.addColumn("Quantity", "quantity", Long.class.getName(),60,true)
			.addColumn("Amount", "amount", Float.class.getName(),70,true)
			.addGroups(2)
			.setTitle("November " + getYear() +" sales report")
			.setSubtitle("This report was generated at " + new Date())
			.setUseFullPageWidth(true);


		Style style1 = new StyleBuilder(true).setFont(Font.ARIAL_MEDIUM).build();
		Style style2 = new StyleBuilder(true).setFont(Font.COMIC_SANS_MEDIUM).build();
		Style style3 = new StyleBuilder(true).setFont(Font.COURIER_NEW_MEDIUM).build();
		Style style4 = new StyleBuilder(true).setFont(Font.GEORGIA_MEDIUM).build();
		Style style5 = new StyleBuilder(true).setFont(Font.MONOSPACED_MEDIUM).build();
		Style style6 = new StyleBuilder(true).setFont(Font.TIMES_NEW_ROMAN_MEDIUM).build();
		Style style7 = new StyleBuilder(true).setFont(Font.VERDANA_MEDIUM).build();


		drb.addAutoText("Testing the font Arial", AutoText.POSITION_HEADER, AutoText.ALIGNMENT_LEFT,300, style1);
		drb.addAutoText("Testing the font Comic Sans MS", AutoText.POSITION_HEADER, AutoText.ALIGNMENT_LEFT,300, style2);
		drb.addAutoText("Testing the font Courrier New", AutoText.POSITION_HEADER, AutoText.ALIGNMENT_LEFT,300, style3);
		drb.addAutoText("Testing the font Georgia", AutoText.POSITION_HEADER, AutoText.ALIGNMENT_LEFT,300, style4);
		drb.addAutoText("Testing the font Monospaced", AutoText.POSITION_HEADER, AutoText.ALIGNMENT_LEFT,300, style5);
		drb.addAutoText("Testing the font Times New Roman", AutoText.POSITION_HEADER, AutoText.ALIGNMENT_LEFT,300, style6);
		drb.addAutoText("Testing the font Verdana", AutoText.POSITION_HEADER, AutoText.ALIGNMENT_LEFT,300, style7);


		DynamicReport dr = drb.build();


		return dr;
	}

	public static void main(String[] args) throws Exception {
		BuiltinFontsReportTest test = new BuiltinFontsReportTest();
		test.testReport();
		JasperViewer.viewReport(test.jp);
		JasperDesignViewer.viewReportDesign(DynamicJasperHelper.generateJasperReport(test.dr, test.getLayoutManager(),new HashMap()));
	}

}