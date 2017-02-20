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
import java.util.List;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.view.JasperDesignViewer;
import net.sf.jasperreports.view.JasperViewer;
import ar.com.fdvs.dj.core.BarcodeTypes;
import ar.com.fdvs.dj.domain.DynamicReport;
import ar.com.fdvs.dj.domain.Style;
import ar.com.fdvs.dj.domain.builders.FastReportBuilder;
import ar.com.fdvs.dj.domain.builders.StyleBuilder;
import ar.com.fdvs.dj.domain.constants.Border;
import ar.com.fdvs.dj.domain.constants.HorizontalAlign;
import ar.com.fdvs.dj.domain.constants.ImageScaleMode;
import ar.com.fdvs.dj.domain.constants.Stretching;
import ar.com.fdvs.dj.test.domain.Product;
import ar.com.fdvs.dj.util.SortUtils;

public class BarcodeColumnReportTest2 extends BaseDjReportTest {


	public DynamicReport buildReport() throws Exception {


		Style style = new StyleBuilder(true).setHorizontalAlign(HorizontalAlign.CENTER).build();
		Style defStyle = new StyleBuilder(true).
			setBorderBottom(Border.THIN())
			.setStretching(Stretching.RELATIVE_TO_TALLEST_OBJECT)
			.setPaddingBottom(3)
			.setPaddingTop(3)
			.build();
		/*
		  Creates the DynamicReportBuilder and sets the basic options for
		  the report
		 */
		FastReportBuilder drb = new FastReportBuilder();
		Style title = null;
		Style subtitle = null;
		Style columnHeader = null;
		drb.addColumn("State", "state", String.class.getName(),20,defStyle)
			.addColumn("Branch", "branch", String.class.getName(),30,defStyle)
			.addColumn("Quantity", "quantity", Long.class.getName(),60,defStyle,null,true)
			.addColumn("Amount", "amount", Float.class.getName(),70,defStyle,null,true)
			.addBarcodeColumn("Bar-Code", "amount", Long.class.getName(), BarcodeTypes.USD3, true, false,null, 100, true, ImageScaleMode.FILL, defStyle)
			.addGroups(1)
			.setDetailHeight(30)
			.addField("productLine",  String.class.getName())
			.setTitle("November " + getYear() +" sales report")
			.setSubtitle("This report was generated at " + new Date())
			.setDefaultStyles(title, subtitle, columnHeader, defStyle)
			.setUseFullPageWidth(true);

		return drb.build();
	}
	
	protected JRDataSource getDataSource() {
		List<Product> dummyCollection = TestRepositoryProducts.getDummyCollection();
		dummyCollection.add(new Product( new Long("1"),
				"book",
				"Harry Potter 7",
				"Florida",
				"Main Street, Main Street, Main Street, Main Street, Main Street, Main Street, " +
				"Main Street, Main Street, Main Street, Main Street, Main Street, Main Street, " +
				"Main Street, Main Street, Main Street, Main Street, Main Street (end)",
				new Long("2500"), new Float("10000")));
		dummyCollection = SortUtils.sortCollection(dummyCollection,dr.getColumns());

		//here contains dummy hardcoded objects...
		return new JRBeanCollectionDataSource(dummyCollection);
	}	

	public static void main(String[] args) throws Exception {
		BarcodeColumnReportTest2 test = new BarcodeColumnReportTest2();
		test.testReport();
		JasperViewer.viewReport(test.jp);	//finally display the report report
		JasperDesignViewer.viewReportDesign(test.jr);
	}

}
