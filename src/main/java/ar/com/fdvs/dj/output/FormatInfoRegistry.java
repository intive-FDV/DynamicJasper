/*
 * Copyright (c) 2012, FDV Solutions S.A.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *     * Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 *     * Neither the name of the FDV Solutions S.A. nor the
 *       names of its contributors may be used to endorse or promote products
 *       derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL FDV Solutions S.A. BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
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

	
    private static final Map FORMAT_INFO = new HashMap();
    static {
        FORMAT_INFO.put(DJConstants.FORMAT_CSV, new FormatInfo("text/plain", EXPORTER_CLASS_CSV, ClassicLayoutManager.class.getName()));
        FORMAT_INFO.put(DJConstants.FORMAT_HTML, new FormatInfo("text/html", EXPORTER_CLASS_HTML, ClassicLayoutManager.class.getName()));
        FORMAT_INFO.put(DJConstants.FORMAT_PDF, new FormatInfo("application/pdf", EXPORTER_CLASS_PDF, ClassicLayoutManager.class.getName()));
        FORMAT_INFO.put(DJConstants.FORMAT_XLS, new FormatInfo("application/vnd.ms-excel", EXPORTER_CLASS_XLS, ListLayoutManager.class.getName()));
        FORMAT_INFO.put(DJConstants.FORMAT_XML, new FormatInfo("text/xml", EXPORTER_CLASS_XML, ClassicLayoutManager.class.getName()));
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
