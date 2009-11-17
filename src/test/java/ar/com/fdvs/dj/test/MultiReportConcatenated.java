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

 	  	 
