/*
 * Dynamic Jasper: A library for creating reports dynamically by specifying
 * columns, groups, styles, etc. at runtime. It also saves a lot of development
 * time in many cases! (http://sourceforge.net/projects/dynamicjasper)
 *
 * Copyright (C) 2007  FDV Solutions (http://www.fdvsolutions.com)
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

import java.awt.Color;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import junit.framework.TestCase;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.view.JasperDesignViewer;
import net.sf.jasperreports.view.JasperViewer;
import ar.com.fdvs.dj.core.DJConstants;
import ar.com.fdvs.dj.core.DynamicJasperHelper;
import ar.com.fdvs.dj.core.layout.ClassicLayoutManager;
import ar.com.fdvs.dj.domain.ColumnsGroupVariableOperation;
import ar.com.fdvs.dj.domain.DynamicReport;
import ar.com.fdvs.dj.domain.Style;
import ar.com.fdvs.dj.domain.builders.ColumnBuilder;
import ar.com.fdvs.dj.domain.builders.DynamicReportBuilder;
import ar.com.fdvs.dj.domain.builders.FastReportBuilder;
import ar.com.fdvs.dj.domain.builders.GroupBuilder;
import ar.com.fdvs.dj.domain.constants.Border;
import ar.com.fdvs.dj.domain.constants.Font;
import ar.com.fdvs.dj.domain.constants.GroupLayout;
import ar.com.fdvs.dj.domain.constants.HorizontalAlign;
import ar.com.fdvs.dj.domain.constants.Stretching;
import ar.com.fdvs.dj.domain.constants.Transparency;
import ar.com.fdvs.dj.domain.constants.VerticalAlign;
import ar.com.fdvs.dj.domain.entities.ColumnsGroup;
import ar.com.fdvs.dj.domain.entities.Subreport;
import ar.com.fdvs.dj.domain.entities.columns.AbstractColumn;
import ar.com.fdvs.dj.domain.entities.columns.PropertyColumn;
import ar.com.fdvs.dj.test.ReportExporter;
import ar.com.fdvs.dj.test.TestRepositoryProducts;
import ar.com.fdvs.dj.util.SortUtils;

public class SubReportLevel3Test extends TestCase {

	public DynamicReport buildReport() throws Exception {
		
		

		FastReportBuilder drb = new FastReportBuilder();
		drb.addColumn("State", "state", String.class.getName(),30)
			.addColumn("Branch", "branch", String.class.getName(),30)
//			.addColumn("Product Line", "productLine", String.class.getName(),50)
//			.addColumn("Item", "item", String.class.getName(),50)
//			.addColumn("Item Code", "id", Long.class.getName(),30,true)
//			.addColumn("Quantity", "quantity", Long.class.getName(),60,true)
//			.addColumn("Amount", "amount", Float.class.getName(),70,true)
			.addGroups(2)
			.addField("statistics", Collection.class.getName())
			.addTitle("November 2006 sales report")
			.addSubtitle("This report was generated at " + new Date())
			.addUseFullPageWidth(true);	

		DynamicReport mainReport = drb.build();	
		

//		//Create de deepest subreport (level 3)
		DynamicReport drLevel3 = createLevel3Subreport();
		JasperReport srLevel3 = DynamicJasperHelper.generateJasperReport(drLevel3, new ClassicLayoutManager());
//		
//		
//		//Create level 2 subreport
		DynamicReport drLevel2 = createLevel2Subreport();
		ColumnsGroup srl2group1 = (ColumnsGroup) drLevel2.getColumnsGroups().get(0);
//		
		Subreport level3Sr = new Subreport();
		level3Sr.setReport(srLevel3);
		level3Sr.setDataSourceExpression("dummy3");
		level3Sr.setDataSourceOrigin(DJConstants.SUBREPORT_DATA_SOURCE_ORIGIN_FIELD);
		level3Sr.setDataSourceType(DJConstants.DATA_SOURCE_TYPE_COLLECTION);
		srl2group1.getHeaderSubreports().add(level3Sr); //put level 3 subreport inside level 2 subreport
		JasperReport jrLevel2 = DynamicJasperHelper.generateJasperReport(drLevel2, new ClassicLayoutManager());
//		
		//now create and put level2 subreport in the main subreport
		Subreport headerSubreport = new Subreport();
		headerSubreport.setReport(jrLevel2);
		headerSubreport.setDataSourceExpression("statistics");
		headerSubreport.setDataSourceOrigin(DJConstants.SUBREPORT_DATA_SOURCE_ORIGIN_FIELD);
		headerSubreport.setDataSourceType(DJConstants.DATA_SOURCE_TYPE_COLLECTION);
		ColumnsGroup mainReportGroup1 = (ColumnsGroup) mainReport.getColumnsGroups().get(0);	
		mainReportGroup1.getHeaderSubreports().add(headerSubreport);
		

		return mainReport;
	}

	private DynamicReport createLevel2Subreport() throws Exception {
		FastReportBuilder rb = new FastReportBuilder();
		DynamicReport dr = rb
			.addColumn("Date", "date", Date.class.getName(), 100)
			.addColumn("Average", "average", Float.class.getName(), 50)
			.addColumn("%", "percentage", Float.class.getName(), 50)
			.addColumn("Amount", "amount", Float.class.getName(), 50)
			.addGroups(1)
			.addField("dummy3", Collection.class.getName())
			.addMargins(5, 5, 20, 20)
			.addUseFullPageWidth(true)
			.addTitle("Level 2 Subreport")
			.build();
		return dr;
	}

	private DynamicReport createLevel3Subreport() throws Exception {
		FastReportBuilder rb = new FastReportBuilder();
		DynamicReport dr = rb
		.addColumn("Name", "name", String.class.getName(), 100)
		.addColumn("Number", "number", Long.class.getName(), 50)
		.addMargins(5, 5, 20, 20)
		.addUseFullPageWidth(false)
		.addTitle("Level 3 Subreport")
		.build();
		return dr;
	}

	public void testReport() {
	try {
		DynamicReport dr = buildReport();
		Collection dummyCollection = TestRepositoryProducts.getDummyCollection();
		dummyCollection = SortUtils.sortCollection(dummyCollection,dr.getColumns());
		
		JRDataSource ds = new JRBeanCollectionDataSource(dummyCollection);
		JasperPrint jp = DynamicJasperHelper.generateJasperPrint(dr, new ClassicLayoutManager(), ds);
		ReportExporter.exportReport(jp, System.getProperty("user.dir")+ "/target/SubReportLevel3Test.pdf");
		JasperViewer.viewReport(jp);
//		JasperDesignViewer.viewReportDesign(DynamicJasperHelper.generateJasperReport(dr, new ClassicLayoutManager()));
	} catch (Exception e) {
		e.printStackTrace();
	}
}

	public static void main(String[] args) {
		SubReportLevel3Test test = new SubReportLevel3Test();
		test.testReport();
	}

}