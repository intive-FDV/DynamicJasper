package ar.com.fdvs.dj.output;

import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JRException;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

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
}
