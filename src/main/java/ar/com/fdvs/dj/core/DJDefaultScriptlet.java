package ar.com.fdvs.dj.core;

import java.util.Map;

import net.sf.jasperreports.engine.JRDefaultScriptlet;
import net.sf.jasperreports.engine.fill.JRFillGroup;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

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
		
		
//		if (logger.isDebugEnabled()) {
//			veces++;
//			logger.debug("setData(...) Me llamaron " +veces +" veces, " + this); //$NON-NLS-1$
//		}
		
		fieldMapWrapper.setMap(fldsm);
		parameterMapWrapper.setMap(parsm);
		variableMapWrapper.setMap(varsm);
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

}
