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

import java.util.HashMap;
import java.util.Map;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;
import ar.com.fdvs.dj.core.DJConstants;
import ar.com.fdvs.dj.core.DynamicJasperHelper;
import ar.com.fdvs.dj.core.layout.ClassicLayoutManager;
import ar.com.fdvs.dj.domain.builders.DynamicReportBuilder;
import ar.com.fdvs.dj.domain.builders.SubReportBuilder;
import ar.com.fdvs.dj.domain.entities.Subreport;

/**
 * This report shows how to add concatenated subreports that start in a new page
 * @author Anis BEN RHOUMA 
 *
 */
public class MultiReportConcatenated {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		Map params = new HashMap();
		
		ClassicLayoutManager layoutManager = new ClassicLayoutManager();
		
		DynamicReportBuilder drb = new DynamicReportBuilder();
		drb.setTitle("TEST");
		drb.setSubtitle("This is a report with many concatenated subreports which should start in a new page");
		drb.setWhenNoDataAllSectionNoDetail();
		for (int i = 0; i < 3; i++) {
			FastReportTest report = new FastReportTest();

			try {
				report.dr = report.buildReport();
				report.dr.setTitle("REPORT N°: " + i);
				report.dr.setWhenNoDataType(DJConstants.WHEN_NO_DATA_TYPE_ALL_SECTIONS_NO_DETAIL);
				
				String dataSourcePath = "DataSource" + i;
				JRDataSource dataSource = report.getDataSource();
				params.put(dataSourcePath, dataSource);

				Subreport subreport = new SubReportBuilder()
				.setStartInNewPage(true)
				.setDataSource(DJConstants.DATA_SOURCE_ORIGIN_PARAMETER, DJConstants.DATA_SOURCE_TYPE_JRDATASOURCE, dataSourcePath)
				.setDynamicReport(report.dr,  new ClassicLayoutManager())
				.build();

				drb.addConcatenatedReport(subreport);

			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}

		try {
			JasperPrint jasperPrint = DynamicJasperHelper.generateJasperPrint(drb.build(), layoutManager, params);
			JasperViewer.viewReport(jasperPrint);
			
		} catch (JRException e) {
			e.printStackTrace();
		}
		
	}

}

 	  	 
