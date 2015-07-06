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

import java.util.HashMap;
import java.util.Map;

import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.export.JRHtmlExporterParameter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import ar.com.fdvs.dj.core.DJConstants;
import ar.com.fdvs.dj.core.layout.ClassicLayoutManager;
import ar.com.fdvs.dj.core.layout.LayoutManager;
import ar.com.fdvs.dj.core.layout.ListLayoutManager;

/**
 * @author Alejandro Gomez
 *         Date: Feb 22, 2007
 *         Time: 4:44:50 PM
 */
public class FormatInfoRegistry {

	public static final String EXPORTER_CLASS_XLS = "net.sf.jasperreports.engine.export.JRXlsExporter";
	public static final String EXPORTER_CLASS_CSV = "net.sf.jasperreports.engine.export.JRCsvExporter";
	public static final String EXPORTER_CLASS_HTML = "net.sf.jasperreports.engine.export.JRHtmlExporter";
	public static final String EXPORTER_CLASS_PDF = "net.sf.jasperreports.engine.export.JRPdfExporter";
	public static final String EXPORTER_CLASS_XML = "net.sf.jasperreports.engine.export.JRXmlExporter";
	public static final String EXPORTER_CLASS_RTF = "net.sf.jasperreports.engine.export.JRRtfExporter";

	
    private static final Map FORMAT_INFO = new HashMap();
    static {
        FORMAT_INFO.put(DJConstants.FORMAT_CSV, new FormatInfo("text/plain", EXPORTER_CLASS_CSV, ClassicLayoutManager.class.getName()));
        FORMAT_INFO.put(DJConstants.FORMAT_HTML, new FormatInfo("text/html", EXPORTER_CLASS_HTML, ClassicLayoutManager.class.getName()));
        FORMAT_INFO.put(DJConstants.FORMAT_PDF, new FormatInfo("application/pdf", EXPORTER_CLASS_PDF, ClassicLayoutManager.class.getName()));
        FORMAT_INFO.put(DJConstants.FORMAT_XLS, new FormatInfo("application/vnd.ms-excel", EXPORTER_CLASS_XLS, ListLayoutManager.class.getName()));
        FORMAT_INFO.put(DJConstants.FORMAT_XML, new FormatInfo("text/xml", EXPORTER_CLASS_XML, ClassicLayoutManager.class.getName()));
        FORMAT_INFO.put(DJConstants.FORMAT_RTF, new FormatInfo("application/rtf", EXPORTER_CLASS_RTF, ClassicLayoutManager.class.getName()));
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
        private String exporterClass;
        private String layoutManagerClass;

        private FormatInfo(final String _contentType, final String _exporterClass, final String _layoutManagerClass) {
            contentType = _contentType;
            exporterClass = _exporterClass;
            layoutManagerClass = _layoutManagerClass;
        }

        public String getContentType() {
            return contentType;
        }

        public JRExporter getExporterInstance() {
            try {
                return (JRExporter)Class.forName(exporterClass).newInstance();
            } catch (Exception ex) {
                return null;
            }
        }

        public LayoutManager getLayoutManagerInstance() {
            try {
                return (LayoutManager)Class.forName(layoutManagerClass).newInstance();
            } catch (Exception ex) {
                return null;
            }
        }
    }
}
