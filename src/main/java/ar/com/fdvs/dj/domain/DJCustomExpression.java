package ar.com.fdvs.dj.domain;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import ar.com.fdvs.dj.core.DJConstants;


public abstract class DJCustomExpression implements CustomExpression {

	public Object evaluate(Object object) {
		Map map = (Map) object;
		Map fields = new HashMap();
		Map parameters = new HashMap();
		Map variables = new HashMap();
		for (Iterator iterator = map.keySet().iterator(); iterator.hasNext();) {
			String key = (String) iterator.next();
			if (key.startsWith("v_")){
				variables.put(key.substring(2), map.get(key));
			} else if (DJConstants.CUSTOM_EXPRESSION__PARAMETERS_MAP.equals(key)) {
				parameters.putAll((Map) map.get(key));
			} else { //its a field
				fields.put(key, map.get(key));
			}
			
		}
		return innerEvaluate(fields,variables,parameters);
	}

	public abstract Object innerEvaluate(Map fields, Map variables, Map parameters);

}
