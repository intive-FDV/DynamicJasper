package ar.com.fdvs.dj.core;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRHtmlExporterParameter;
import net.sf.jasperreports.j2ee.servlets.ImageServlet;
import ar.com.fdvs.dj.core.layout.LayoutManager;
import ar.com.fdvs.dj.domain.DynamicReport;
import ar.com.fdvs.dj.output.ReportWriter;
import ar.com.fdvs.dj.output.ReportWriterFactory;

public class DJServletHelper {

    /**
     * Generates the report as HTML and setups everything for a clean response (serving images as well).
     * You have to declare JasperReport servlet in web.xml (net.sf.jasperreports.j2ee.servlets.ImageServlet)
     * <br/><br/>
     * Web XML must be configured somehow like this:
     * <code><br/><br/>
     * &lt;servlet&gt;<br/>
     * &nbsp;	&lt;servlet-name&gt;image&lt;/servlet-name&gt;<br/>
     * &nbsp; &lt;servlet-class&gt;net.sf.jasperreports.j2ee.servlets.ImageServlet&lt;/servlet-class&gt;<br/>
     * &lt;/servlet&gt;<br/>
     * <p/>
     * &lt;servlet-mapping&gt;<br/>
     * &nbsp;	&lt;servlet-name&gt;image&lt;/servlet-name&gt;<br/>
     * &nbsp;	&lt;url-pattern&gt;/reports/image&lt;/url-pattern&gt;<br/>
     * &lt;/servlet-mapping&gt;<br/>
     * </code>
     *
     * @param request
     * @param response
     * @param imageServletUrl the URI to reach net.sf.jasperreports.j2ee.servlets.ImageServlet servlet (in example it would be "reports/image")
     * @param dynamicReport
     * @param layoutManager
     * @param ds
     * @param parameters      Parameters for the DynamicReport
     * @param exporterParams  Extra parameters for JasperReport's HTML exporter (HTMLJRHtmlExporter)
     * @throws JRException
     * @throws IOException
     */
    public static void exportToHtml(HttpServletRequest request,
                                    HttpServletResponse response,
                                    String imageServletUrl,
                                    DynamicReport dynamicReport,
                                    LayoutManager layoutManager,
                                    JRDataSource ds,
                                    Map parameters,
                                    Map exporterParams) throws JRException, IOException {
        if (parameters == null)
            parameters = new HashMap();
        if (exporterParams == null)
            exporterParams = new HashMap();

        JasperPrint _jasperPrint = DynamicJasperHelper.generateJasperPrint(dynamicReport, layoutManager, ds, parameters);
        final ReportWriter reportWriter = ReportWriterFactory.getInstance().getReportWriter(_jasperPrint, DJConstants.FORMAT_HTML, parameters);

        exportToHtml(request,response,imageServletUrl,_jasperPrint,exporterParams);

    }



    public static void exportToHtml(HttpServletRequest request,
                                    HttpServletResponse response,
                                    String imageServletUrl,
                                    JasperPrint jasperPrint,
                                    Map exporterParams) throws JRException, IOException {
        if (exporterParams == null)
            exporterParams = new HashMap();

        exporterParams.put(JRHtmlExporterParameter.IMAGES_URI, request.getContextPath() + imageServletUrl);

        final ReportWriter reportWriter = ReportWriterFactory.getInstance().getReportWriter(jasperPrint, DJConstants.FORMAT_HTML, exporterParams);

        Map imagesMap = new HashMap();
        JRExporter exporter = reportWriter.getExporter();
        exporter.setParameters(exporterParams);

        setupParameters(request, imageServletUrl, jasperPrint, imagesMap, exporter);

        //write generated HTML to the http-response (the one you got from the helper)
        reportWriter.writeTo(response);
    }

    public static InputStream exportToHtml(HttpServletRequest request,
                                           String imageServletUrl,
                                           DynamicReport dynamicReport,
                                           LayoutManager layoutManager,
                                           JRDataSource ds,
                                           Map parameters,
                                           Map exporterParams) throws JRException, IOException {
        if (parameters == null)
            parameters = new HashMap();
        if (exporterParams == null)
            exporterParams = new HashMap();

        JasperPrint _jasperPrint = DynamicJasperHelper.generateJasperPrint(dynamicReport, layoutManager, ds, parameters);

        return exportToHtml(request, imageServletUrl,_jasperPrint,exporterParams);

    }

    public static InputStream exportToHtml(HttpServletRequest request,
                                    String imageServletUrl,
                                    JasperPrint jasperPrint,
                                    Map exporterParams) throws JRException, IOException {
        if (exporterParams == null)
            exporterParams = new HashMap();

        exporterParams.put(JRHtmlExporterParameter.IMAGES_URI, request.getContextPath() + imageServletUrl);

        final ReportWriter reportWriter = ReportWriterFactory.getInstance().getReportWriter(jasperPrint, DJConstants.FORMAT_HTML, exporterParams);

        Map imagesMap = new HashMap();
        JRExporter exporter = reportWriter.getExporter();
        exporter.setParameters(exporterParams);

        setupParameters(request, imageServletUrl, jasperPrint, imagesMap, exporter);

        //write generated HTML to the http-response (the one you got from the helper)
        return reportWriter.write();

    }

    private static void setupParameters(HttpServletRequest request, String imageServletUrl, JasperPrint jasperPrint, Map imagesMap, JRExporter exporter) {
        exporter.setParameter(JRHtmlExporterParameter.IMAGES_MAP, imagesMap);
        exporter.setParameter(JRHtmlExporterParameter.IMAGES_URI, request.getContextPath() + "/" + imageServletUrl + "?image=");
        // Needed to support chart images:
        exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
        HttpSession session = request.getSession();
        session.setAttribute(ImageServlet.DEFAULT_JASPER_PRINT_SESSION_ATTRIBUTE, jasperPrint);
        session.setAttribute("net.sf.jasperreports.j2ee.jasper_print", jasperPrint);
    }


}
