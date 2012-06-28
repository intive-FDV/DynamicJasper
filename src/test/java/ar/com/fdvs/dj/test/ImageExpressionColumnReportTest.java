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
import java.util.Map;

import net.sf.jasperreports.view.JasperViewer;
import ar.com.fdvs.dj.domain.CustomExpression;
import ar.com.fdvs.dj.domain.DynamicReport;
import ar.com.fdvs.dj.domain.Style;
import ar.com.fdvs.dj.domain.builders.FastReportBuilder;
import ar.com.fdvs.dj.domain.builders.StyleBuilder;
import ar.com.fdvs.dj.domain.constants.Border;
import ar.com.fdvs.dj.domain.constants.HorizontalAlign;
import ar.com.fdvs.dj.domain.constants.ImageScaleMode;
import ar.com.fdvs.dj.domain.constants.Stretching;

public class ImageExpressionColumnReportTest extends BaseDjReportTest {

	public DynamicReport buildReport() throws Exception {


		Style style = new StyleBuilder(false).setHorizontalAlign(HorizontalAlign.CENTER)
		.setStretching(Stretching.RELATIVE_TO_TALLEST_OBJECT)
		.setBorderColor(Color.BLACK).setBorder(Border.THIN).build();
		/**
		 * Creates the DynamicReportBuilder and sets the basic options for
		 * the report
		 */
		CustomExpression imgExpr = new CustomExpression() {
			
			String basePath = this.getClass().getResource("/images/emoticons/confused.gif").toString();
			
			public String getClassName() {
				return String.class.getName();
			}
			
			public Object evaluate(Map fields, Map variables, Map parameters) {				
				return basePath;
			}
		};
		
		FastReportBuilder drb = new FastReportBuilder();
		drb.addColumn("State", "state", String.class.getName(),30)
			.addColumn("Branch", "branch", String.class.getName(),30)
			.addColumn("Product Line", "productLine", String.class.getName(),50)
			.addImageColumn("IMG", imgExpr, 50, true,ImageScaleMode.FILL ,style)
			.addColumn("Item", "item", String.class.getName(),20)
			.addColumn("Item Code", "id", Long.class.getName(),30,true)
			.addColumn("Quantity", "quantity", Long.class.getName(),60,true)
			.addColumn("Amount", "amount", Float.class.getName(),70,true)
			.addGroups(2)
			.setDetailHeight(17)
			.setTitle("November 2006 sales report")
			.setSubtitle("This report was generated at " + new Date())
			.setUseFullPageWidth(true);


		DynamicReport dr = drb.build();

		return dr;
	}

	public static void main(String[] args) throws Exception {
		ImageExpressionColumnReportTest test = new ImageExpressionColumnReportTest();
		test.testReport();
		JasperViewer.viewReport(test.jp);	//finally display the report report
	}

}
