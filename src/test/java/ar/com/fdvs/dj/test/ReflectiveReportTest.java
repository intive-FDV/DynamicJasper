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

import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import junit.framework.TestCase;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperPrint;
import ar.com.fdvs.dj.core.DynamicJasperHelper;
import ar.com.fdvs.dj.core.layout.ClassicLayoutManager;
import ar.com.fdvs.dj.domain.DynamicReport;
import ar.com.fdvs.dj.domain.builders.ReflectiveReportBuilder;
import ar.com.fdvs.dj.util.SortUtils;

public class ReflectiveReportTest extends TestCase {

//	private DynamicReport buildOrderedReport(final Collection _data, final String[] _properties) {
//		return new ReflectiveReportBuilder(_data, _properties).addGroups(3).build();
//	}

	/**
	 * Test N� 1. With only the collection, the ReflectiveReportBuilder make some guesses
	 */
	public void testReport() {
        final Collection data = TestRepositoryProducts.getDummyCollection();
        DynamicReport dynamicReport = new ReflectiveReportBuilder(data).build();
		doReport(dynamicReport, data, "");
    }

	/**
	 * Test N�2, the same but we tell the builder the order of the columns, we also add 3 groups
	 */
	public void testOrderedReport() {
        final Collection data = TestRepositoryProducts.getDummyCollection();
        final List items = SortUtils.sortCollection(data, Arrays.asList(new String[]{"productLine", "item", "state"}));
        String[] columOrders = new String[]{"productLine", "item", "state", "id", "branch", "quantity", "amount"};
		DynamicReport dynamicReport = new ReflectiveReportBuilder(items, columOrders).addGroups(3).build();
		doReport(dynamicReport, items, "ordered");
    }

	public void doReport(final DynamicReport _report, final Collection _data, String name) {
        try {
        	final JasperPrint jasperPrint = DynamicJasperHelper.generateJasperPrint(_report, new ClassicLayoutManager(), _data);
//        	JasperViewer.viewReport(jasperPrint);
			ReportExporter.exportReport(jasperPrint, System.getProperty("user.dir")+ "/target/ReflectiveReportTest "+ name + ".pdf");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (JRException e) {
			e.printStackTrace();
		}
	}

	public static void main(final String[] _args) {
        final ReflectiveReportTest reportTest = new ReflectiveReportTest();
        reportTest.testReport();
        reportTest.testOrderedReport();
	}
}
