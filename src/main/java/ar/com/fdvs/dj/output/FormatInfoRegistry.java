/*
 * Dynamic Jasper: A library for creating reports dynamically by specifying
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

import ar.com.fdvs.dj.core.layout.ClassicLayoutManager;
import ar.com.fdvs.dj.core.layout.LayoutManager;
import ar.com.fdvs.dj.core.layout.ListLayoutManager;
import com.opensymphony.webwork.views.jasperreports.JasperReportConstants;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.export.JRCsvExporter;
import net.sf.jasperreports.engine.export.JRHtmlExporter;
import net.sf.jasperreports.engine.export.JRHtmlExporterParameter;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.engine.export.JRXmlExporter;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Alejandro Gomez
 *         Date: Feb 22, 2007
 *         Time: 4:44:50 PM
 */
public class FormatInfoRegistry {

    private static final Map FORMAT_INFO = new HashMap();
    static {
        FORMAT_INFO.put(JasperReportConstants.FORMAT_CSV, new FormatInfo("text/plain", JRCsvExporter.class, ClassicLayoutManager.class));
        FORMAT_INFO.put(JasperReportConstants.FORMAT_HTML, new FormatInfo("text/html", JRHtmlExporter.class, ClassicLayoutManager.class));
        FORMAT_INFO.put(JasperReportConstants.FORMAT_PDF, new FormatInfo("application/pdf", JRPdfExporter.class, ClassicLayoutManager.class));
        FORMAT_INFO.put(JasperReportConstants.FORMAT_XLS, new FormatInfo("application/vnd.ms-excel", JRXlsExporter.class, ListLayoutManager.class));
        FORMAT_INFO.put(JasperReportConstants.FORMAT_XML, new FormatInfo("text/xml", JRXmlExporter.class, ClassicLayoutManager.class));
    }

    private static final FormatInfoRegistry INSTANCE = new FormatInfoRegistry();

    public String getContentType(final String _format) {
        checkFormat(_format);
        return ((FormatInfo)FORMAT_INFO.get(_format)).getContentType();
    }

    public JRExporter getExporter(final String _format) {
        checkFormat(_format);
        final JRExporter exporter = ((FormatInfo)FORMAT_INFO.get(_format)).getExporterInstance();
        exporter.setParameter(JRHtmlExporterParameter.IS_USING_IMAGES_TO_ALIGN, Boolean.FALSE);
        exporter.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);
        exporter.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
        exporter.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);
        exporter.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
        return exporter;
    }

    public LayoutManager getLayoutManager(final String _format) {
        checkFormat(_format);
        return ((FormatInfo)FORMAT_INFO.get(_format)).getLayoutManagerInstance();
    }

    private static void checkFormat(final String _format) {
        if (!FORMAT_INFO.containsKey(_format)) {
            throw new IllegalArgumentException("Unsupported format: " + _format);
        }
    }

    public static FormatInfoRegistry getInstance() {
        return INSTANCE;
    }

    private static class FormatInfo {

        private String contentType;
        private Class exporterClass;
        private Class layoutManagerClass;

        private FormatInfo(final String _contentType, final Class _exporterClass, final Class _layoutManagerClass) {
            contentType = _contentType;
            exporterClass = _exporterClass;
            layoutManagerClass = _layoutManagerClass;
        }

        public String getContentType() {
            return contentType;
        }

        public JRExporter getExporterInstance() {
            try {
                return (JRExporter)exporterClass.newInstance();
            } catch (Exception ex) {
                return null;
            }
        }

        public LayoutManager getLayoutManagerInstance() {
            try {
                return (LayoutManager)layoutManagerClass.newInstance();
            } catch (Exception ex) {
                return null;
            }
        }
    }
}
