package ar.com.fdvs.dj.core;

import java.util.Map;

import com.opensymphony.webwork.views.jasperreports.JasperReportConstants;
import net.sf.jasperreports.engine.JRDefaultScriptlet;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JRScriptletException;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.fill.JRFillGroup;

import net.sf.jasperreports.engine.fill.JRFillParameter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * This class handles parameter passing to custom expressions in runtime (during report fill)
 * @author mamana
 *
 */
public class DJDefaultScriptlet extends JRDefaultScriptlet  {
	
	int veces = 0;
	public DJDefaultScriptlet(){
		super();
//		logger.debug("Im a new Scrptlet  " + this);
	}
	
	/**
	 * Logger for this class
	 */
	private static final Log logger = LogFactory.getLog(DJDefaultScriptlet.class);
	
	protected static final String VARS_KEY = "vars";
	protected static final String PARAMS_KEY = "params";
	protected static final String FIELDS_KEY = "fields";
	
	protected FieldMapWrapper fieldMapWrapper = new FieldMapWrapper();
	protected ParameterMapWrapper parameterMapWrapper = new ParameterMapWrapper();
	protected VariableMapWrapper variableMapWrapper = new VariableMapWrapper();

	public void setData(Map parsm, Map fldsm, Map varsm, JRFillGroup[] grps) {
		super.setData(parsm, fldsm, varsm, grps);
		putValuesInMap();
	}
	
	protected void putValuesInMap() {
		fieldMapWrapper.setMap(this.fieldsMap);
		parameterMapWrapper.setMap(this.parametersMap);
		variableMapWrapper.setMap(this.variablesMap);
	}

	public Map getCurrentFiels() {
		return fieldMapWrapper; 
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
