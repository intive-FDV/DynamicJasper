package ar.com.fdvs.dj.core;

import net.sf.jasperreports.engine.JRDefaultScriptlet;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JRScriptletException;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.fill.JRFillField;
import net.sf.jasperreports.engine.fill.JRFillGroup;
import net.sf.jasperreports.engine.fill.JRFillParameter;
import net.sf.jasperreports.engine.fill.JRFillVariable;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Map;

/**
 * This class handles parameter passing to custom expressions in runtime (during report fill)
 *
 * @author mamana
 */
public class DJDefaultScriptlet extends JRDefaultScriptlet {

    public DJDefaultScriptlet() {
        super();
    }

    private static final Log logger = LogFactory.getLog(DJDefaultScriptlet.class);


    protected FieldMapWrapper fieldMapWrapper = new FieldMapWrapper();
    protected ParameterMapWrapper parameterMapWrapper = new ParameterMapWrapper();
    protected VariableMapWrapper variableMapWrapper = new VariableMapWrapper();

    public void setData(Map<String, JRFillParameter> parsm, Map<String,JRFillField> fldsm, Map<String,JRFillVariable> varsm, JRFillGroup[] grps) {
        super.setData(parsm, fldsm, varsm, grps);
        putValuesInMap();
    }

    protected void putValuesInMap() {
        fieldMapWrapper.setMap(this.fieldsMap);
        parameterMapWrapper.setMap(this.parametersMap);
        variableMapWrapper.setMap(this.variablesMap);
    }

    public Map getCurrentFields() {
        return fieldMapWrapper;
    }

    public Map getPreviousFields() {
        return fieldMapWrapper.getPreviousValues();
    }

    public Map getCurrentParams() {
        return parameterMapWrapper;
    }

    public Map getCurrentVariables() {
        return variableMapWrapper;
    }

    @Override
    public void beforeReportInit() throws JRScriptletException {
        super.beforeReportInit();
        JasperReport jr = (JasperReport) getParameterValue(JRParameter.JASPER_REPORT);
        variableMapWrapper.setReportName(jr.getName());
        parameterMapWrapper.setReportName(jr.getName());
    }
}
