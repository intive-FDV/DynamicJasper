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
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import net.sf.jasperreports.view.JasperDesignViewer;
import net.sf.jasperreports.view.JasperViewer;
import ar.com.fdvs.dj.core.DynamicJasperHelper;
import ar.com.fdvs.dj.domain.AutoText;
import ar.com.fdvs.dj.domain.DynamicReport;
import ar.com.fdvs.dj.domain.Style;
import ar.com.fdvs.dj.domain.builders.FastReportBuilder;
import ar.com.fdvs.dj.domain.builders.StyleBuilder;
import ar.com.fdvs.dj.domain.constants.Font;

public class AutotextReportTest extends BaseDjReportTest {

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
			.setTitle("November 2006 sales report")
			.setSubtitle("This report was generated at " + new Date())
			.setUseFullPageWidth(true);


		Style atStyle = new StyleBuilder(true).setFont(Font.COMIC_SANS_SMALL).setTextColor(Color.red).build();
		Style atStyle2 = new StyleBuilder(true).setFont(new Font(9, Font._FONT_TIMES_NEW_ROMAN, false, true, false)).setTextColor(Color.BLUE).build();
		
		/**
		 * Adding many autotexts in the same position (header/footer and aligment) makes them to be one on top of the other
		 */
		//First add in the FOOTER
		drb.addAutoText(AutoText.AUTOTEXT_PAGE_X, AutoText.POSITION_HEADER, AutoText.ALIGNMENT_LEFT,200,40, atStyle);
		drb.addAutoText("Autotext below Page counter", AutoText.POSITION_FOOTER, AutoText.ALIGNMENT_LEFT);

		//Note the styled text: <b>msimone</b>, valid tags are: <b>, <i> and <u>
		drb.addAutoText("Created by <b>msimone</b>", AutoText.POSITION_FOOTER, AutoText.ALIGNMENT_RIGHT,200);
		drb.addAutoText(AutoText.AUTOTEXT_PAGE_X_SLASH_Y, AutoText.POSITION_FOOTER, AutoText.ALIGNMENT_RIGHT,30,30,atStyle2);

		drb.addAutoText(AutoText.AUTOTEXT_CREATED_ON, AutoText.POSITION_FOOTER, AutoText.ALIGNMENT_LEFT,AutoText.PATTERN_DATE_DATE_TIME);

		//Now in HEADER
		drb.addAutoText(AutoText.AUTOTEXT_PAGE_X_OF_Y, AutoText.POSITION_HEADER, AutoText.ALIGNMENT_LEFT,100,40);
		drb.addAutoText("Autotext at top-left", AutoText.POSITION_HEADER, AutoText.ALIGNMENT_LEFT,200);
		
		drb.addAutoText("Autotext at top-left (2)", AutoText.POSITION_HEADER, AutoText.ALIGNMENT_LEFT,200);
		drb.addAutoText("Autotext at top-center", AutoText.POSITION_HEADER, AutoText.ALIGNMENT_CENTER,200,atStyle);
		DynamicReport dr = drb.build();

		//i18N, you can set a Locale, different than the default in the VM
		drb.setReportLocale(new Locale("es","AR"));
//		drb.setReportLocale(new Locale("pt","BR"));
//		drb.setReportLocale(new Locale("fr","FR"));

		return dr;
	}

	public static void main(String[] args) throws Exception {
		AutotextReportTest test = new AutotextReportTest();
		test.testReport();
		JasperViewer.viewReport(test.jp);
		JasperDesignViewer.viewReportDesign(DynamicJasperHelper.generateJasperReport(test.dr, test.getLayoutManager(),new HashMap()));
	}

}