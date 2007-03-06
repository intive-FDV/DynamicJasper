package ar.com.fdvs.dj.output;

import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JasperPrint;

import java.util.Map;

import ar.com.fdvs.dj.output.FormatInfoRegistry;

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
