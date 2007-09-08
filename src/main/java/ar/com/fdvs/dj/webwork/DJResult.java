package ar.com.fdvs.dj.webwork;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRHtmlExporterParameter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import ar.com.fdvs.dj.core.DynamicJasperHelper;
import ar.com.fdvs.dj.core.layout.ClassicLayoutManager;
import ar.com.fdvs.dj.domain.DynamicReport;
import ar.com.fdvs.dj.output.FormatInfoRegistry;
import ar.com.fdvs.dj.output.ReportWriter;
import ar.com.fdvs.dj.output.ReportWriterFactory;

import com.opensymphony.util.TextUtils;
import com.opensymphony.webwork.WebWorkException;
import com.opensymphony.webwork.WebWorkStatics;
import com.opensymphony.webwork.views.jasperreports.JasperReportsResult;
import com.opensymphony.xwork.ActionInvocation;
import com.opensymphony.xwork.util.TextParseUtil;

/**
 * @author Alejandro Gomez
 *         Date: Feb 22, 2007
 *         Time: 4:32:34 PM
 */
public class DJResult extends JasperReportsResult {

    private static final Log LOG = LogFactory.getLog(DJResult.class);

    private String dynamicReport;

    private String documentFormat;
    private static final long serialVersionUID = 8272276796810374927L;

    public void setDynamicReport(final String _dynamicReport) {
        dynamicReport = _dynamicReport;
    }

    /**
     * Executes the result given a final location (jsp page, action, etc) and the action invocation
     * (the state in which the action was executed). Subclasses must implement this class to handle
     * custom logic for result handling.
     *
     * @param _finalLocation the location (jsp page, action, etc) to go to.
     * @param _invocation    the execution state of the action.
     * @throws Exception if an error occurs while executing the result.
     */
    protected void doExecute(final String _finalLocation, final ActionInvocation _invocation) throws Exception {
        checkParams();
        documentFormat = getFormat(_invocation);
        if (LOG.isDebugEnabled()) {
            LOG.debug("Creating JasperReport for dynamicReport, format = " + documentFormat);
        }


        //construct the dynamic report
        //final OgnlValueStack stack = _invocation.getStack();
        final JRDataSource ds = (JRDataSource)conditionalParse(dataSource, _invocation, JRDataSource.class);
        //final OgnlValueStackDataSource stackDataSource = new OgnlValueStackDataSource(stack, dataSource);

        // (Map) ActionContext.getContext().getSession().get("IMAGES_MAP");

        final HttpServletRequest request = (HttpServletRequest)_invocation.getInvocationContext().get(WebWorkStatics.HTTP_REQUEST);
        final HttpServletResponse response = (HttpServletResponse)_invocation.getInvocationContext().get(WebWorkStatics.HTTP_RESPONSE);
        if ("contype".equals(request.getHeader("User-Agent"))) {
            // Code to handle "contype" request from IE
            handleConTypeRequest(response);
        } else {
            //final JasperPrint jasperPrint = DynamicJasperHelper.generateJasperPrint(getDynamicReport(_invocation), new ClassicLayoutManager(), stackDataSource);
            final HashMap parameters = new HashMap();
            //TODO set the locale
            parameters.put(JRParameter.REPORT_LOCALE, _invocation.getInvocationContext().getLocale());
            final JasperPrint jasperPrint = DynamicJasperHelper.generateJasperPrint(getDynamicReport(_invocation), new ClassicLayoutManager(), ds, parameters);

            // Export the print object to the desired output format
            writeReponse(request, response, jasperPrint, _invocation);
        }
    }

    private void handleConTypeRequest(final HttpServletResponse _response) throws ServletException {
        try {
            _response.setContentType("application/pdf");
            _response.setContentLength(0);
            _response.getOutputStream().close();
        } catch (IOException ex) {
            LOG.error("Error writing report output", ex);
            throw new ServletException(ex.getMessage(), ex);
        }
    }

    private void checkParams() {
        if (dynamicReport == null) {
            final String message = "No dynamicReport specified...";
            LOG.error(message);
            throw new WebWorkException(message);
        }
        if (dataSource == null) {
            final String message = "No dataSource specified...";
            LOG.error(message);
            throw new WebWorkException(message);
        }
    }

    private void setResponseHeaders(final HttpServletResponse _response, final ActionInvocation _invocation) {
        if (contentDisposition != null || documentName != null) {
            final StringBuffer buffer = new StringBuffer();
            buffer.append(getContentDisposition(_invocation));
            if (documentName != null) {
                buffer.append("; filename=");
                buffer.append(getDocumentName(_invocation));
                buffer.append(".");
                buffer.append(documentFormat.toLowerCase());
            }
            _response.setHeader("Content-disposition", buffer.toString());
        }
        _response.setContentType(FormatInfoRegistry.getInstance().getContentType(documentFormat));
    }

    private void writeReponse(final HttpServletRequest _request, final HttpServletResponse _response, final JasperPrint _jasperPrint, final ActionInvocation _invocation) throws JRException, IOException {
        setResponseHeaders(_response, _invocation);
        final HashMap parameters = new HashMap();
        parameters.put(JRHtmlExporterParameter.IMAGES_URI, _request.getContextPath() + imageServletUrl);
        final ReportWriter reportWriter = ReportWriterFactory.getInstance().getReportWriter(_jasperPrint, documentFormat, parameters);
        reportWriter.writeTo(_response);
    }

    private DynamicReport getDynamicReport(final ActionInvocation _invocation) {
        return (DynamicReport)conditionalParse(dynamicReport, _invocation, DynamicReport.class);
    }

    private String getDataSource(final ActionInvocation _invocation) {
        return conditionalParse(dataSource, _invocation);
    }

    private String getFormat(final ActionInvocation _invocation) {
        final String parsedFormat = conditionalParse(format == null ? FORMAT_PDF : format, _invocation);
        return TextUtils.stringSet(parsedFormat) ? parsedFormat : FORMAT_PDF;
    }

    private String getDocumentName(final ActionInvocation _invocation) {
        return conditionalParse(documentName, _invocation);
    }

    private String getContentDisposition(final ActionInvocation _invocation) {
        final String parsedContentDisposition = conditionalParse(contentDisposition, _invocation);
        return parsedContentDisposition == null ? "inline" : parsedContentDisposition;
    }

    private String getDelimiter(final ActionInvocation _invocation) {
        return conditionalParse(delimiter, _invocation);
    }

    private String getImageServletUrl(final ActionInvocation _invocation) {
        return conditionalParse(imageServletUrl, _invocation);
    }

    private Object conditionalParse(final String _param, final ActionInvocation _invocation, final Class _type) {
        if (parse && _param != null && _invocation != null) {
            return TextParseUtil.translateVariables('$', _param, _invocation.getStack(), _type, null);
        } else {
            return _param;
        }
    }
}
