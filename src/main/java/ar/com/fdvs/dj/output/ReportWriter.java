package ar.com.fdvs.dj.output;

import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JRException;

import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.IOException;

/**
 * @author Alejandro Gomez
 *         Date: Feb 23, 2007
 *         Time: 5:37:13 PM
 */
public abstract class ReportWriter {

    private static final int BUFFER_SIZE = 10 * 1024;

    protected JasperPrint jasperPrint;
    protected JRExporter exporter;

    protected ReportWriter(final JasperPrint _jasperPrint, final JRExporter _exporter) {
        jasperPrint = _jasperPrint;
        exporter = _exporter;
        exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
    }

    public abstract void writeTo(HttpServletResponse _response) throws IOException, JRException;

    protected void copyStreams(final InputStream _inputStream, final OutputStream _ouputStream) throws IOException {
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
