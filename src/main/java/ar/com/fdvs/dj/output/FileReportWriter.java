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

package ar.com.fdvs.dj.output;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperPrint;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author Alejandro Gomez
 *         Date: Feb 23, 2007
 *         Time: 5:48:17 PM
 */
public class FileReportWriter extends ReportWriter {

    private static final Log LOGGER = LogFactory.getLog(FileReportWriter.class);

    public FileReportWriter(final JasperPrint _jasperPrint, final JRExporter _exporter) {
        super(_jasperPrint, _exporter);
    }

    public void writeTo(final HttpServletResponse _response) throws IOException, JRException {
        LOGGER.info("entering FileReportWriter.writeTo()");
        final File file = File.createTempFile("djreport", ".tmp");
        try {
            exporter.setParameter(JRExporterParameter.OUTPUT_FILE, file);
            exporter.exportReport();
            _response.setContentLength((int)file.length());
            copyStreams(new FileInputStream(file), _response.getOutputStream());
        } finally {
            LOGGER.info("deleting " + file.getAbsolutePath());
            file.delete();
        }
    }

    @Override
    public InputStream write() throws IOException, JRException {
        LOGGER.info("entering FileReportWriter.writeTo()");
        final File file = File.createTempFile("djreport", ".tmp");

        file.deleteOnExit();
        exporter.setParameter(JRExporterParameter.OUTPUT_FILE, file);
        exporter.exportReport();

        return new FileInputStream(file);
    }


}
