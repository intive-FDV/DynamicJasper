package ar.com.fdvs.dj.core;

import ar.com.fdvs.dj.core.layout.LayoutManager;
import ar.com.fdvs.dj.domain.DynamicReport;
import ar.com.fdvs.dj.output.ReportWriter;
import ar.com.fdvs.dj.output.ReportWriterFactory;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.export.Exporter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * @deprecated JasperReports 7.x removed the j2ee.servlets package and JRHtmlExporterParameter.
 * This class no longer functions correctly for HTML export with image handling.
 * Use the new JasperReports 7.x HTML export API with HtmlResourceHandler instead.
 */
@Deprecated
public class DJServletHelper {

    private static final ThreadLocal<Integer> pageTreshold = new ThreadLocal<Integer>();

    static {
        pageTreshold.set(5);
    }

    /**
     * Sets the number of pages to keep the report in memory, if the report surpases such limit, a file will
     * be used.
     * @param treshold
     */
    public static void setPageTreshold(int treshold){
        if (treshold >= 0)
            pageTreshold.set(treshold);
    }

    /**
     * Generates the report as HTML and setups everything for a clean response (serving images as well).
     * You have to declare JasperReport servlet in web.xml (net.sf.jasperreports.j2ee.servlets.ImageServlet)
     * <br><br>
     * Web XML must be configured somehow like this:
     * <code><br><br>
     * &lt;servlet&gt;<br>
     * &nbsp;	&lt;servlet-name&gt;image&lt;/servlet-name&gt;<br>
     * &nbsp; &lt;servlet-class&gt;net.sf.jasperreports.j2ee.servlets.ImageServlet&lt;/servlet-class&gt;<br>
     * &lt;/servlet&gt;<br>
     * <p/>
     * &lt;servlet-mapping&gt;<br>
     * &nbsp;	&lt;servlet-name&gt;image&lt;/servlet-name&gt;<br>
     * &nbsp;	&lt;url-pattern&gt;/reports/image&lt;/url-pattern&gt;<br>
     * &lt;/servlet-mapping&gt;<br>
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
                                    Map<String, Object> parameters,
                                    Map<String,Object> exporterParams) throws JRException, IOException {
        if (parameters == null)
            parameters = new HashMap<String, Object>();
        if (exporterParams == null)
            exporterParams = new HashMap<String,Object>();

        JasperPrint _jasperPrint = DynamicJasperHelper.generateJasperPrint(dynamicReport, layoutManager, ds, parameters);
        exportToHtml(request,response,imageServletUrl,_jasperPrint,exporterParams);

    }



    public static void exportToHtml(HttpServletRequest request,
                                    HttpServletResponse response,
                                    String imageServletUrl,
                                    JasperPrint jasperPrint,
                                    Map<String,Object>  exporterParams) throws JRException, IOException {
        if (exporterParams == null)
            exporterParams = new HashMap<String,Object>();

        // Note: HTML image handling changed in JR 7.x - this method may not work correctly
        final ReportWriter reportWriter = ReportWriterFactory.build(pageTreshold.get()).getReportWriter(jasperPrint, DJConstants.FORMAT_HTML, exporterParams);

        Map imagesMap = new HashMap();
        Exporter exporter = reportWriter.getExporter();

        setupParameters(request, imageServletUrl, jasperPrint, imagesMap, exporter);

        //write generated HTML to the http-response (the one you got from the helper)
        reportWriter.writeTo(response);
    }

    public static InputStream exportToHtml(HttpServletRequest request,
                                           String imageServletUrl,
                                           DynamicReport dynamicReport,
                                           LayoutManager layoutManager,
                                           JRDataSource ds,
                                           Map<String, Object> parameters,
                                           Map<String,Object>  exporterParams) throws JRException, IOException {
        if (parameters == null)
            parameters = new HashMap<String, Object>();
        if (exporterParams == null)
            exporterParams = new HashMap<String,Object>();

        JasperPrint _jasperPrint = DynamicJasperHelper.generateJasperPrint(dynamicReport, layoutManager, ds, parameters);

        return exportToHtml(request, imageServletUrl,_jasperPrint,exporterParams);

    }

    public static InputStream exportToHtml(HttpServletRequest request,
                                    String imageServletUrl,
                                    JasperPrint jasperPrint,
                                    Map<String,Object> exporterParams) throws JRException, IOException {
        if (exporterParams == null)
            exporterParams = new HashMap<String,Object>();

        // Note: HTML image handling changed in JR 7.x - this method may not work correctly
        final ReportWriter reportWriter = ReportWriterFactory.build(pageTreshold.get()).getReportWriter(jasperPrint, DJConstants.FORMAT_HTML, exporterParams);

        Map imagesMap = new HashMap();
        Exporter exporter = reportWriter.getExporter();

        setupParameters(request, imageServletUrl, jasperPrint, imagesMap, exporter);

        //write generated HTML to the http-response (the one you got from the helper)
        return reportWriter.write();

    }

    private static void setupParameters(HttpServletRequest request, String imageServletUrl, JasperPrint jasperPrint, Map imagesMap, Exporter exporter) {
        // Note: JRHtmlExporterParameter and ImageServlet removed in JR 7.x
        // This method no longer works - HTML image handling requires new JR 7 HtmlResourceHandler API

        HttpSession session = request.getSession();
        // Keep session attributes for backward compatibility (may not work in JR 7)
        session.setAttribute("net.sf.jasperreports.j2ee.jasper_print", jasperPrint);
    }


}
