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
