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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JExcelApiExporter;
import net.sf.jasperreports.engine.export.JRHtmlExporter;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ReportExporter {
	/**
	 * Logger for this class
	 */
	private static final Log logger = LogFactory.getLog(ReportExporter.class);

	/**
	 * The path to the file must exist.
	 * @param jp
	 * @param path
	 * @throws JRException
	 * @throws FileNotFoundException
	 */
	public static void exportReport(JasperPrint jp, String path) throws JRException, FileNotFoundException{
		logger.debug("Exporing report to: " + path);
		JRPdfExporter exporter = new JRPdfExporter();

		File outputFile = new File(path);
		File parentFile = outputFile.getParentFile();
		if (parentFile != null)
			parentFile.mkdirs();
		FileOutputStream fos = new FileOutputStream(outputFile);

		exporter.setParameter(JRExporterParameter.JASPER_PRINT, jp);
		exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, fos);

		exporter.exportReport();

		logger.debug("Report exported: " + path);
	}

	public static void exportReportXls(JasperPrint jp, String path) throws JRException, FileNotFoundException{
		JRXlsExporter exporter = new JRXlsExporter();

		File outputFile = new File(path);
		File parentFile = outputFile.getParentFile();
		if (parentFile != null)
			parentFile.mkdirs();
		FileOutputStream fos = new FileOutputStream(outputFile);

		exporter.setParameter(JRExporterParameter.JASPER_PRINT, jp);
		exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, fos);
		exporter.setParameter(JRXlsExporterParameter.IS_DETECT_CELL_TYPE,Boolean.TRUE);
		exporter.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);
		exporter.setParameter(JRXlsExporterParameter.IS_IGNORE_GRAPHICS, Boolean.FALSE);

		exporter.exportReport();

		logger.debug("XLS Report exported: " + path);
	}

	public static void exportReportHtml(JasperPrint jp, String path) throws JRException, FileNotFoundException{
		JRHtmlExporter exporter = new JRHtmlExporter();
		
		File outputFile = new File(path);
		File parentFile = outputFile.getParentFile();
		if (parentFile != null)
			parentFile.mkdirs();
		FileOutputStream fos = new FileOutputStream(outputFile);
		
		exporter.setParameter(JRExporterParameter.JASPER_PRINT, jp);
		exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, fos);
		exporter.exportReport();
		
		logger.debug("HTML Report exported: " + path);
	}

	public static void exportReportPlainXls(JasperPrint jp, String path) throws JRException, FileNotFoundException{
//		JRXlsExporter exporter = new JRXlsExporter();
		JExcelApiExporter exporter = new JExcelApiExporter();

		File outputFile = new File(path);
		File parentFile = outputFile.getParentFile();
		if (parentFile != null)
			parentFile.mkdirs();
		FileOutputStream fos = new FileOutputStream(outputFile);

		exporter.setParameter(JRExporterParameter.JASPER_PRINT, jp);
		exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, fos);
		exporter.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE);
		exporter.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
		exporter.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);
		exporter.setParameter(JRXlsExporterParameter.IS_DETECT_CELL_TYPE, Boolean.TRUE);


		exporter.exportReport();

		logger.debug("Report exported: " + path);

	}

}
