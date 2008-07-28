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

import java.util.Map;

import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JasperPrint;

/**
 * @author Alejandro Gomez
 *         Date: Feb 23, 2007
 *         Time: 5:35:37 PM
 */
public class ReportWriterFactory {

    private static final int PAGES_THRESHHOLD = 2;

    private static final ReportWriterFactory INSTANCE = new ReportWriterFactory();

    public ReportWriter getReportWriter(final JasperPrint _jasperPrint, final String _format, final Map _parameters) {
        final JRExporter exporter = FormatInfoRegistry.getInstance().getExporter(_format);
        exporter.setParameters(_parameters);
        if (_jasperPrint.getPages().size() > PAGES_THRESHHOLD) {
            return new FileReportWriter(_jasperPrint, exporter);
        } else {
            return new MemoryReportWriter(_jasperPrint, exporter);
        }
    }

    public static ReportWriterFactory getInstance() {
        return INSTANCE;
    }
}
