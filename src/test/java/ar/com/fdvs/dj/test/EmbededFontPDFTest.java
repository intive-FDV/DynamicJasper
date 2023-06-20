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


import ar.com.fdvs.dj.domain.DynamicReport;
import ar.com.fdvs.dj.domain.Style;
import ar.com.fdvs.dj.domain.builders.FastReportBuilder;
import ar.com.fdvs.dj.domain.builders.StyleBuilder;
import ar.com.fdvs.dj.domain.constants.Font;
import ar.com.fdvs.dj.domain.constants.Page;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.view.JasperDesignViewer;
import net.sf.jasperreports.view.JasperViewer;

import java.util.*;

import ar.com.fdvs.dj.domain.AutoText;

public class EmbededFontPDFTest extends BaseDjReportTest {

	public DynamicReport buildReport() throws Exception {

		Style style = new StyleBuilder(false).
				setFont(Font.TIMES_NEW_ROMAN_MEDIUM).build();

		String footerText = " dj هذا تذييل بحروف عربية";
//		String footerText = " dj";
		FastReportBuilder reportBuilder = new FastReportBuilder();
//		Page page = Page.Page();
		reportBuilder.setTitle("table title")
				.setUseFullPageWidth(true)
				.setDefaultStyles(style, style,style,style)
				.addAutoText(footerText,  AutoText.POSITION_FOOTER, AutoText.ALIGNMENT_RIGHT, 200, style )
				.addAutoText(footerText,  AutoText.POSITION_HEADER, AutoText.ALIGMENT_LEFT, 200, style )
				.setReportName("report name ");
		List<String> columns = Arrays.asList("description");
		reportBuilder.addColumn( "description","description" , String.class.getName(), 30);

		reportBuilder.setReportLocale(new Locale("AR","sa"));

		DynamicReport dr = reportBuilder.build();

		return dr;
	}

	@Override
	protected JRDataSource getDataSource() {
		List rowsDataList = new ArrayList();
		for (int row = 0; row < 5; row++) {
			HashMap<String, String> rowHashMap = new HashMap<>();
			rowHashMap.put("description", "تجربه" );
			rowsDataList.add(rowHashMap);
		}
		return  new JRBeanCollectionDataSource(rowsDataList);
	}

	public static void main(String[] args) throws Exception {
		EmbededFontPDFTest test = new EmbededFontPDFTest();
		test.testReport();
		JasperViewer.viewReport(test.jp);	//finally display the report report
			JasperDesignViewer.viewReportDesign(test.jr);
	}

}
