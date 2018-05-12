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

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.HtmlExporter;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.export.OutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleHtmlExporterConfiguration;
import net.sf.jasperreports.export.SimpleHtmlExporterOutput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleXlsReportConfiguration;
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

    /**
     * The path to the file must exist.
     *
     * @param jp
     * @param path
     * @throws JRException
     * @throws FileNotFoundException
     */
    public static void exportReport(JasperPrint jp, String path) throws JRException, FileNotFoundException {
        logger.debug("Exporing report to: " + path);
        JRPdfExporter exporter = new JRPdfExporter();

        File outputFile = new File(path);
        File parentFile = outputFile.getParentFile();
        if (parentFile != null)
            parentFile.mkdirs();
        FileOutputStream fos = new FileOutputStream(outputFile);

        SimpleExporterInput simpleExporterInput = new SimpleExporterInput(jp);
        OutputStreamExporterOutput simpleOutputStreamExporterOutput = new SimpleOutputStreamExporterOutput(fos);

        exporter.setExporterInput(simpleExporterInput);
        exporter.setExporterOutput(simpleOutputStreamExporterOutput);

        exporter.exportReport();

        logger.debug("Report exported: " + path);
    }

    public static void exportReportXls(JasperPrint jp, String path, SimpleXlsReportConfiguration configuration) throws JRException, FileNotFoundException {
        JRXlsExporter exporter = new JRXlsExporter();

        File outputFile = new File(path);
        File parentFile = outputFile.getParentFile();
        if (parentFile != null)
            parentFile.mkdirs();
        FileOutputStream fos = new FileOutputStream(outputFile);

        exporter.setConfiguration(configuration);

        SimpleExporterInput simpleExporterInput = new SimpleExporterInput(jp);
        OutputStreamExporterOutput simpleOutputStreamExporterOutput = new SimpleOutputStreamExporterOutput(fos);

        exporter.setExporterInput(simpleExporterInput);
        exporter.setExporterOutput(simpleOutputStreamExporterOutput);

        exporter.exportReport();

        logger.debug("Xlsx Report exported: " + path);
    }

    public static void exportReportXls(JasperPrint jp, String path) throws JRException, FileNotFoundException {
        SimpleXlsReportConfiguration configuration = new SimpleXlsReportConfiguration();
        configuration.setDetectCellType(true);
        configuration.setWhitePageBackground(false);
        configuration.setIgnoreGraphics(false);
        configuration.setIgnorePageMargins(true);

        exportReportXls(jp, path, configuration);
    }

    public static void exportReportHtml(JasperPrint jp, String path) throws JRException, FileNotFoundException {
        HtmlExporter exporter = new HtmlExporter();

        File outputFile = new File(path);
        File parentFile = outputFile.getParentFile();
        if (parentFile != null)
            parentFile.mkdirs();
        FileOutputStream fos = new FileOutputStream(outputFile);

        SimpleExporterInput simpleExporterInput = new SimpleExporterInput(jp);
        SimpleHtmlExporterOutput simpleOutputStreamExporterOutput = new SimpleHtmlExporterOutput(fos);

        exporter.setExporterInput(simpleExporterInput);
        exporter.setExporterOutput(simpleOutputStreamExporterOutput);
        SimpleHtmlExporterConfiguration configuration = new SimpleHtmlExporterConfiguration();

        exporter.setConfiguration(configuration);

        exporter.exportReport();

        logger.debug("HTML Report exported: " + path);
    }

}
