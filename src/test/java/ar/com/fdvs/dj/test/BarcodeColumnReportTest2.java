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


import java.util.Collection;
import java.util.Date;

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
			setBorderBottom(Border.THIN)
			.setStretching(Stretching.RELATIVE_TO_TALLEST_OBJECT)
			.setPaddingBottom(3)
			.setPaddingTop(3)
			.build();
		/**
		 * Creates the DynamicReportBuilder and sets the basic options for
		 * the report
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
			.setTitle("November 2006 sales report")
			.setSubtitle("This report was generated at " + new Date())
			.setDefaultStyles(title, subtitle, columnHeader, defStyle)
			.setUseFullPageWidth(true);

		DynamicReport dr = drb.build();

		return dr;
	}
	
	protected JRDataSource getDataSource() {
		Collection dummyCollection = TestRepositoryProducts.getDummyCollection();
		dummyCollection.add(new Product( new Long("1"),
				"book",
				"Harry Potter 7",
				"Florida",
				"Main Street, Main Street, Main Street, Main Street, Main Street, Main Street, " +
				"Main Street, Main Street, Main Street, Main Street, Main Street, Main Street, " +
				"Main Street, Main Street, Main Street, Main Street, Main Street (end)",
				new Long("2500"), new Float("10000")));
		dummyCollection = SortUtils.sortCollection(dummyCollection,dr.getColumns());

		JRDataSource ds = new JRBeanCollectionDataSource(dummyCollection);		//Create a JRDataSource, the Collection used
																										//here contains dummy hardcoded objects...
		return ds;
	}	

	public static void main(String[] args) throws Exception {
		BarcodeColumnReportTest2 test = new BarcodeColumnReportTest2();
		test.testReport();
		JasperViewer.viewReport(test.jp);	//finally display the report report
		JasperDesignViewer.viewReportDesign(test.jr);
	}

}
