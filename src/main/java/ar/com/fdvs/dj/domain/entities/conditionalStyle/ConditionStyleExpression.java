package ar.com.fdvs.dj.domain.entities.conditionalStyle;

import java.util.Map;

import ar.com.fdvs.dj.domain.CustomExpression;

public abstract class ConditionStyleExpression implements CustomExpression {


	private Object currentValue;

	public Object evaluate(Map fields, Map variables, Map parameters, Object value) {
		this.currentValue = value;
		return evaluate(fields, variables, parameters);
	}

	public Object getCurrentValue() {
		return currentValue;
	}


}
