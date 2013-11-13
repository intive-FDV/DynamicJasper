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

import java.io.*;

import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperPrint;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author Alejandro Gomez
 *         Date: Feb 23, 2007
 *         Time: 5:48:02 PM
 */
public class MemoryReportWriter extends ReportWriter {

    private static final Log LOGGER = LogFactory.getLog(MemoryReportWriter.class);

    public MemoryReportWriter(final JasperPrint _jasperPrint, final JRExporter _exporter) {
        super(_jasperPrint, _exporter);
    }

    public void writeTo(final HttpServletResponse _response) throws IOException, JRException {
        LOGGER.info("entering MemoryReportWriter.writeTo(HttpServletResponse)");
        final ByteArrayOutputStream stream = new ByteArrayOutputStream();
        exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, stream);
        exporter.exportReport();
        _response.setContentLength(stream.size());
        copyStreams(new ByteArrayInputStream(stream.toByteArray()), _response.getOutputStream());
    }

    @Override
    public InputStream write() throws IOException, JRException {
        LOGGER.info("entering MemoryReportWriter.write()");
        final ByteArrayOutputStream stream = new ByteArrayOutputStream();
        exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, stream);
        exporter.exportReport();

        return new ByteArrayInputStream(stream.toByteArray());
    }
}
