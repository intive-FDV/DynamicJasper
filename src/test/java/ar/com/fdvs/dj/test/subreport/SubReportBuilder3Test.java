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

package ar.com.fdvs.dj.test.subreport;

import ar.com.fdvs.dj.core.DJConstants;
import ar.com.fdvs.dj.core.layout.ClassicLayoutManager;
import ar.com.fdvs.dj.domain.CustomExpression;
import ar.com.fdvs.dj.domain.DynamicReport;
import ar.com.fdvs.dj.domain.builders.FastReportBuilder;
import ar.com.fdvs.dj.domain.builders.SubReportBuilder;
import ar.com.fdvs.dj.domain.entities.Subreport;
import ar.com.fdvs.dj.test.BaseDjReportTest;
import ar.com.fdvs.dj.test.domain.Product;
import net.sf.jasperreports.view.JasperDesignViewer;
import net.sf.jasperreports.view.JasperViewer;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * This tests makes the subreport to use it's own parameters map (which a map stored in the parent parameters map)
 * The subreport also uses custom expressions.
 * The idea is test that custom expression does not clash even when using parent report parameters map
 * @author mamana
 *
 */
public class SubReportBuilder3Test extends BaseDjReportTest {

	public DynamicReport buildReport() throws Exception {

		FastReportBuilder drb = new FastReportBuilder();
		drb.addColumn("State", "state", String.class.getName(), 30)
			.addColumn("Branch", "branch", String.class.getName(), 30)
			.addColumn("Product Line", "productLine", String.class.getName(), 50)
			.addColumn("Item", "item", String.class.getName(), 50)
//			.addColumn("Item Code", "id", Long.class.getName(),30,true)
			.addColumn("Item Code", new CustomExpression() {
                public Object evaluate(Map fields, Map variables, Map parameters) {
                    return "XXXX";
                }

                public String getClassName() {
                    return String.class.getName();
                }
            },50,false,null,null)
                    .addColumn("Quantity", "quantity", Long.class.getName(), 60, true)
                    .addColumn("Amount", "amount", Float.class.getName(), 70, true)
                    .addGroups(1)
                    .setMargins(5, 5, 20, 20)
                    .setTitle("November " + getYear() +" sales report")
                    .setSubtitle("This report was generated at " + new Date())
                    .setUseFullPageWidth(true);

		/*
		  Create the subreport. Note that the "subreport" object is then passed
		  as parameter to the GroupBuilder
		 */
		Subreport subreport = new SubReportBuilder()
						.setDataSource(	DJConstants.DATA_SOURCE_ORIGIN_PARAMETER,
										DJConstants.DATA_SOURCE_TYPE_COLLECTION,
										"statistics")
						.setDynamicReport(createFooterSubreport(), new ClassicLayoutManager())
						.setParameterMapPath("subreportParameterMap")
						.setSplitAllowed(false)						
						.setStartInNewPage(false)
						.build();

		drb.addSubreportInGroupFooter(1, subreport);

		/*
		  add in a map the paramter with the data source to use in the subreport.
		  The "params" map is later passed to the DynamicJasperHelper.generateJasperPrint(...)
		 */
		params.put("statistics", Product.statistics_  ); // the 2nd param is a static Collection

		Map subreportParameterMap = new HashMap();
		subreportParameterMap.put("rightHeader", "Sub report right header");

		params.put("subreportParameterMap", subreportParameterMap  ); // the 2nd param is a static Collection

		/*
		  Create the group and add the subreport (as a Fotter subreport)
		 */
		drb.setUseFullPageWidth(true);

		DynamicReport dr = drb.build();

		return dr;
	}

	/**
	 * Created and compiles dynamically a report to be used as subreportr
	 * @return
	 * @throws Exception
	 */
	private DynamicReport createFooterSubreport() throws Exception {
		FastReportBuilder rb = new FastReportBuilder();
		DynamicReport dr = rb
		.addColumn("Area", "name", String.class.getName(), 100)
		.addColumn("Average", "average", Float.class.getName(), 50)
		.addColumn("%", "percentage", Float.class.getName(), 50)
		.addColumn("Amount", "amount", Float.class.getName(), 50)
        .addColumn("CustomExp", new CustomExpression() {
            public Object evaluate(Map fields, Map variables, Map parameters) {
                return "Soy la C.E.";
            }

            public String getClassName() {
                return String.class.getName();
            }
        },50,false,null,null)
		.addGroups(1)
//		.setMargins(5, 5, 20, 20)
//		.setTemplateFile("templates/TemplateReportTest.jrxml")
		.setUseFullPageWidth(true)
		.setTitle("Subreport for this group")
		.build();
		return dr;
	}


	public static void main(String[] args) throws Exception {
		SubReportBuilder3Test test = new SubReportBuilder3Test();
		test.testReport();
		JasperViewer.viewReport(test.jp);
		JasperDesignViewer.viewReportDesign(test.jr);
	}

}