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

package ar.com.fdvs.dj.test.subreport;

import ar.com.fdvs.dj.core.DJConstants;
import ar.com.fdvs.dj.core.layout.ClassicLayoutManager;
import ar.com.fdvs.dj.domain.CustomExpression;
import ar.com.fdvs.dj.domain.DJHyperLink;
import ar.com.fdvs.dj.domain.DynamicReport;
import ar.com.fdvs.dj.domain.StringExpression;
import ar.com.fdvs.dj.domain.builders.ColumnBuilder;
import ar.com.fdvs.dj.domain.builders.FastReportBuilder;
import ar.com.fdvs.dj.domain.entities.columns.AbstractColumn;
import ar.com.fdvs.dj.test.BaseDjReportTest;
import net.sf.jasperreports.view.JasperViewer;

import java.util.Collection;
import java.util.Date;
import java.util.Map;

public class SubReportRecursive2Test extends BaseDjReportTest {

	public DynamicReport buildReport() throws Exception {



		FastReportBuilder drb = new FastReportBuilder();
		drb.addColumn("State", "state", String.class.getName(),30)
			.addColumn("Branch", "branch", String.class.getName(),30)
			.addGroups(2)
			.setMargins(5, 5, 20, 20)
			.addField("statistics", Collection.class.getName())
			.setTitle("November " + getYear() +" sales report")
			.setSubtitle("This report was generated at " + new Date())
			.setUseFullPageWidth(true);

        DJHyperLink djlink = new DJHyperLink();
        djlink.setExpression(new StringExpression() {
            public Object evaluate(Map fields, Map variables, Map parameters) {
                return "http://linkInImage.com?param=" + variables.get("REPORT_COUNT");
            }
        });

        drb.getColumn(1).setLink(djlink);

		//Create level 2 sub-report
		DynamicReport drLevel2 = createLevel2Subreport();

		//now create and put level2 subreport in the main subreport
		drb.addSubreportInGroupFooter(2, drLevel2, new ClassicLayoutManager(),
				"statistics", DJConstants.DATA_SOURCE_ORIGIN_FIELD, DJConstants.DATA_SOURCE_TYPE_COLLECTION);

		DynamicReport mainReport = drb.build();


		return mainReport;
	}

	private DynamicReport createLevel2Subreport() throws Exception {
		FastReportBuilder rb = new FastReportBuilder();
		rb
			.addColumn("Date", "date", Date.class.getName(), 100)
			.addColumn("Average", "average", Float.class.getName(), 50)
			.addColumn("%", "percentage", Float.class.getName(), 50)
			.addColumn("Amount", "amount", Float.class.getName(), 50)
            .addColumn("ExpCol-Sr1",new CustomExpression() {
                    @Override
                    public Object evaluate(Map fields, Map variables, Map parameters) {
                        return "fixed value!";
                    }

                    @Override
                    public String getClassName() {
                        return String.class.getName();
                    }
                },20,false,null,null)
			.addGroups(1)
			.addField("dummy3", Collection.class.getName())
			.setMargins(5, 5, 20, 20)
			.setUseFullPageWidth(true)
			.setTitle("Level 2 Subreport")
			.addSubreportInGroupFooter(1, createLevel3Subreport(), new ClassicLayoutManager(),
				"dummy3", DJConstants.DATA_SOURCE_ORIGIN_FIELD, DJConstants.DATA_SOURCE_TYPE_COLLECTION);

        DJHyperLink djlink = new DJHyperLink();
        djlink.setExpression(new StringExpression() {
            public Object evaluate(Map fields, Map variables, Map parameters) {
                return "http://linkInImage.com?param=" + variables.get("REPORT_COUNT");
            }
        });

        rb.getColumn(1).setLink(djlink);

        DynamicReport dr = rb.build();

		return dr;
	}

	private DynamicReport createLevel3Subreport() throws Exception {
		FastReportBuilder rb = new FastReportBuilder();

		AbstractColumn customExpCol = ColumnBuilder.getNew().setTitle("Sub-Sub Expression").setCustomExpression(
				new CustomExpression() {
					public Object evaluate(Map fields, Map variables, Map parameters) {
						return "Sub-Sub-report";
					}

					public String getClassName() {
						return String.class.getName();
					}
				}
		).setWidth(150).build();
		rb.addColumn(customExpCol); // Comment out this line to see this report work. Seemingly, DJ cannot handle a sub-sub report with a CustomExpression.

		rb
		.addColumn("Name", "name", String.class.getName(), 100)
		.addColumn("Number", "number", Long.class.getName(), 50)
		.setMargins(5, 5, 20, 20)
		.setUseFullPageWidth(false)
		.setTitle("Level 3 Subreport");


        DJHyperLink djlink = new DJHyperLink();
        djlink.setExpression(new StringExpression() {
            public Object evaluate(Map fields, Map variables, Map parameters) {
                return "http://linkInImage.com?param=" + variables.get("REPORT_COUNT");
            }
        });

        rb.getColumn(1).setLink(djlink);

        DynamicReport dr = rb.build();

		return dr;
	}

	public static void main(String[] args) throws Exception {
		SubReportRecursive2Test test = new SubReportRecursive2Test();
		test.testReport();
		JasperViewer.viewReport(test.jp);
	}

}