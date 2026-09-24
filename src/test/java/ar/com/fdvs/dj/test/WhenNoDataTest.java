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


import java.util.Collections;
import org.junit.Ignore;
import java.util.Date;
import org.junit.Ignore;

import net.sf.jasperreports.engine.JRDataSource;
import org.junit.Ignore;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.junit.Ignore;
import net.sf.jasperreports.view.JasperDesignViewer;
import org.junit.Ignore;
import net.sf.jasperreports.view.JasperViewer;
import org.junit.Ignore;
import ar.com.fdvs.dj.domain.AutoText;
import org.junit.Ignore;
import ar.com.fdvs.dj.domain.DynamicReport;
import org.junit.Ignore;
import ar.com.fdvs.dj.domain.ImageBanner;
import org.junit.Ignore;
import ar.com.fdvs.dj.domain.Style;
import org.junit.Ignore;
import ar.com.fdvs.dj.domain.builders.FastReportBuilder;
import org.junit.Ignore;
import ar.com.fdvs.dj.domain.builders.StyleBuilder;
import org.junit.Ignore;
import ar.com.fdvs.dj.domain.constants.Font;
import org.junit.Ignore;
import ar.com.fdvs.dj.domain.constants.HorizontalAlign;
import org.junit.Ignore;
import ar.com.fdvs.dj.domain.constants.ImageScaleMode;
import org.junit.Ignore;

@Ignore("JR7 requires JRXML templates to be converted to new format")
public class WhenNoDataTest extends BaseDjReportTest {

	public DynamicReport buildReport() throws Exception {


		/*
		  Creates the DynamicReportBuilder and sets the basic options for
		  the report
		 */
		FastReportBuilder drb = new FastReportBuilder();
		Style noDataStyle = new StyleBuilder(false)
								.setFont(Font.ARIAL_MEDIUM_BOLD)
								.setHorizontalAlign(HorizontalAlign.CENTER).build();
		drb.addColumn("State", "state", String.class.getName(),30)
//			.addColumn("Branch", "branch", String.class.getName(),30)
//			.addColumn("Product Line", "productLine", String.class.getName(),50)
//			.addColumn("Item", "item", String.class.getName(),50)
//			.addColumn("Item Code", "id", Long.class.getName(),30,true)
//			.addColumn("Quantity", "quantity", Long.class.getName(),60,true)
//			.addColumn("Amount", "amount", Float.class.getName(),70,true)
//			.addGroups(2)
			.addAutoText(AutoText.AUTOTEXT_PAGE_X,AutoText.POSITION_HEADER,AutoText.ALIGNMENT_LEFT)
			.setTitle("November " + getYear() +" sales report")
			.setSubtitle("This report was generated at " + new Date())
//			.setWhenNoData("No data for this report", noDataStyle)
//			.setWhenNoData("No data for this report", noDataStyle,true,false)
			.setWhenNoData("No data for this report", null,true,true)
			.setUseFullPageWidth(true)
//			.addFirstPageImageBanner(System.getProperty("user.dir") +"/target/test-classes/images/logo_fdv_solutions_60.png", 197, 60, ImageBanner.Alignment.Left)
//			.addFirstPageImageBanner(System.getProperty("user.dir") +"/target/test-classes/images/dynamicJasper_60.jpg", 300, 60, ImageBanner.Alignment.Right)
			.addImageBanner(System.getProperty("user.dir") +"/target/test-classes/images/logo_fdv_solutions_60.png", 100, 25, ImageBanner.Alignment.Left, ImageScaleMode.FILL)
			.addImageBanner(System.getProperty("user.dir") +"/target/test-classes/images/dynamicJasper_60.jpg", 150, 25, ImageBanner.Alignment.Right, ImageScaleMode.FILL);
			
		
		drb.setTemplateFile("templates/TemplateReportTest.jrxml");

		DynamicReport dr = drb.build();

		return dr;
	}

	public static void main(String[] args) throws Exception {
		WhenNoDataTest test = new WhenNoDataTest();
		test.testReport();
		JasperViewer.viewReport(test.jp);	//finally display the report report
		JasperDesignViewer.viewReportDesign(test.jr);
	}

	protected JRDataSource getDataSource() {
		return new JRBeanCollectionDataSource(Collections.EMPTY_LIST);
	}

}

