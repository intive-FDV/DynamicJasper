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
			.setTitle("November " + getYear() +" sales report")
			.setSubtitle("This report was generated at " + new Date())
			.setUseFullPageWidth(true);


		Style atStyle = new StyleBuilder(true).setFont(Font.COMIC_SANS_SMALL).setTextColor(Color.red).build();
		Style atStyle2 = new StyleBuilder(true).setFont(new Font(9, Font._FONT_TIMES_NEW_ROMAN, false, true, false)).setTextColor(Color.BLUE).build();
		
		/*
		  Adding many autotexts in the same position (header/footer and aligment) makes them to be one on top of the other
		 */
		//First add in the FOOTER
		drb.addAutoText(AutoText.AUTOTEXT_PAGE_X, AutoText.POSITION_HEADER, AutoText.ALIGNMENT_LEFT,200,40, atStyle);
		drb.addAutoText("Autotext below Page counter", AutoText.POSITION_FOOTER, AutoText.ALIGNMENT_LEFT);

		//Note the styled text: <b>msimone</b>, valid tags are: <b>, <i> and <u>
		drb.addAutoText("Created by <b>msimone</b>", AutoText.POSITION_FOOTER, AutoText.ALIGNMENT_RIGHT,200);
		drb.addAutoText(AutoText.AUTOTEXT_PAGE_X_OF_Y, AutoText.POSITION_FOOTER, AutoText.ALIGNMENT_RIGHT,30,30,atStyle2);

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