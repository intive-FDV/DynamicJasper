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

import net.sf.jasperreports.view.JasperDesignViewer;
import net.sf.jasperreports.view.JasperViewer;
import ar.com.fdvs.dj.domain.DJCalculation;
import ar.com.fdvs.dj.domain.DynamicReport;
import ar.com.fdvs.dj.domain.Style;
import ar.com.fdvs.dj.domain.builders.FastReportBuilder;
import ar.com.fdvs.dj.domain.constants.HorizontalAlign;
import ar.com.fdvs.dj.domain.customexpression.DJSimpleExpression;
import ar.com.fdvs.dj.domain.entities.DJVariable;

public class ReportWithVariablesTest extends BaseDjReportTest {

	public DynamicReport buildReport() throws Exception {
		Style style = new Style("currency");
		style.setHorizontalAlign(HorizontalAlign.RIGHT);
		style.setPattern("$ #,###.00");

		/*
		  Creates the DynamicReportBuilder and sets the basic options for
		  the report
		 */
		FastReportBuilder drb = new FastReportBuilder();
		drb.addColumn("State", "state", String.class.getName(),30)
			.addColumn("Branch", "branch", String.class.getName(),30)
			.addColumn("Product Line", "productLine", String.class.getName(),50)
			.addColumn("Item", "item", String.class.getName(),50)
			.addColumn("Item Code", "id", Long.class.getName(),30,true)
			.addColumn("Quantity", "quantity", Long.class.getName(),60,true)
			.addColumn("Amount", "amount", Float.class.getName(),70,true)
			.addColumn("Balance",new DJSimpleExpression(DJSimpleExpression.TYPE_VARIABLE, "acum", Float.class.getName()),80,false,null,style)
			.addVariable("acum", DJCalculation.SUM, new DJSimpleExpression(DJSimpleExpression.TYPE_FIELD, "amount", Float.class.getName()))
			.addGroups(2)
			.setTitle("November " + getYear() + " sales report")
			.setSubtitle("This report was generated at " + new Date())
			.setPrintBackgroundOnOddRows(true)			
			.setUseFullPageWidth(true);


//		AbstractColumn columnWithVar = ColumnBuilder.getNew().setTitle("Acumn")
//			.setCustomExpression(new DJSimpleExpression(DJSimpleExpression.TYPE_VARIABLE, "acum", Float.class.getName())).setWidth(80).setStyle(style).build();
//		
//		drb.addColumn(columnWithVar);
		
		DynamicReport dr = drb.build();
		
//		DJVariable var = createVariable();
//		
//		dr.getVariables().add(var);
		
		

		return dr;
	}

	private DJVariable createVariable() {
		DJSimpleExpression expression = new DJSimpleExpression(DJSimpleExpression.TYPE_FIELD, "amount", Float.class.getName());
		DJVariable var = new DJVariable("acum",Float.class.getName(),DJCalculation.SUM,expression);
		return var;
	}

	public static void main(String[] args) throws Exception {
		ReportWithVariablesTest test = new ReportWithVariablesTest();
		test.testReport();
		test.exportToJRXML();
		JasperViewer.viewReport(test.jp);	//finally display the report report
		JasperDesignViewer.viewReportDesign(test.jr);
	}

}
