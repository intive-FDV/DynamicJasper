package ar.com.fdvs.dj.test;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class ReportExporter {
	/**
	 * Logger for this class
	 */
	private static final Log logger = LogFactory.getLog(ReportExporter.class);

	public static void exportReport(JasperPrint jp, String path) throws JRException, FileNotFoundException{
		JRPdfExporter exporter = new JRPdfExporter();

		File outputFile = new File(path);
		FileOutputStream fos = new FileOutputStream(outputFile);

		exporter.setParameter(JRExporterParameter.JASPER_PRINT, jp);
		exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, fos);

		exporter.exportReport();

		logger.debug("Report exported: " + path);

	}

}
