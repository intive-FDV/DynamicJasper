package ar.com.fdvs.dj.output;

import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JRException;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

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
        LOGGER.info("entering MemoryReportWriter.writeTo()");
        final ByteArrayOutputStream stream = new ByteArrayOutputStream();
        exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, stream);
        exporter.exportReport();
        _response.setContentLength(stream.size());
        copyStreams(new ByteArrayInputStream(stream.toByteArray()), _response.getOutputStream());
    }
}
