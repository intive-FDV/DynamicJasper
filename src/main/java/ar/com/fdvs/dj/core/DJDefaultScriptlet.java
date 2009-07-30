package ar.com.fdvs.dj.core;

import java.util.HashMap;
import java.util.Map;

import net.sf.jasperreports.engine.JRDefaultScriptlet;
import net.sf.jasperreports.engine.fill.JRFillGroup;

public class DJDefaultScriptlet extends JRDefaultScriptlet {
	
	protected static final String VARS_KEY = "vars";
	protected static final String PARAMS_KEY = "params";
	protected static final String FIELDS_KEY = "fields";
	
	private ThreadLocal currentValues = new ThreadLocal();

	public void setData(Map parsm, Map fldsm, Map varsm, JRFillGroup[] grps) {
		super.setData(parsm, fldsm, varsm, grps);
		Map map = new HashMap(3);
		map.put(FIELDS_KEY, new FieldMapWrapper(fldsm));
		map.put(PARAMS_KEY, new ParameterMapWrapper(parsm));
		map.put(VARS_KEY, new VariableMapWrapper(varsm));
		currentValues.set(map);
	}
	
	public Map getCurrentFiels() {
		return (Map) ((Map) currentValues.get()).get(FIELDS_KEY);
	}

	public Map getCurrentParams() {
		return (Map) ((Map) currentValues.get()).get(PARAMS_KEY);
	}

	public Map getCurrentVariables() {
		return (Map) ((Map) currentValues.get()).get(VARS_KEY);
	}

}
