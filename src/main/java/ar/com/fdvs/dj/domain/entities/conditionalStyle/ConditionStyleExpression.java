package ar.com.fdvs.dj.domain.entities.conditionalStyle;

import java.io.Serializable;
import java.util.Map;

import ar.com.fdvs.dj.domain.CustomExpression;
import ar.com.fdvs.dj.domain.entities.Entity;

public abstract class ConditionStyleExpression  implements CustomExpression, Serializable {

	private static final long serialVersionUID = Entity.SERIAL_VERSION_UID;
	
	private Object currentValue;

	public Object evaluate(Map fields, Map variables, Map parameters, Object value) {
		this.currentValue = value;
		return evaluate(fields, variables, parameters);
	}

	public Object getCurrentValue() {
		return currentValue;
	}


}
