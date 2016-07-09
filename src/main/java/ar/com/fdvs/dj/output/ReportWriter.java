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

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author Alejandro Gomez
 *         Date: Feb 23, 2007
 *         Time: 5:37:13 PM
 */
public abstract class ReportWriter {

    public JRExporter getExporter() {
		return exporter;
	}

	private static final int BUFFER_SIZE = 10 * 1024;

    protected JasperPrint jasperPrint;
    protected JRExporter exporter;

    protected ReportWriter(final JasperPrint _jasperPrint, final JRExporter _exporter) {
        jasperPrint = _jasperPrint;
        exporter = _exporter;
        exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
    }

    public abstract void writeTo(HttpServletResponse _response) throws IOException, JRException;

    public abstract InputStream write() throws IOException, JRException;

    public static final void copyStreams(final InputStream _inputStream, final OutputStream _ouputStream) throws IOException {
        final byte[] buffer = new byte[BUFFER_SIZE];
        int c;
        try {
            while ((c = _inputStream.read(buffer)) != -1) {
                _ouputStream.write(buffer, 0, c);
            }
        } finally {
            _ouputStream.flush();
            _ouputStream.close();
            _inputStream.close();
        }
    }
}
