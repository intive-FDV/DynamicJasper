package ar.com.fdvs.dj.util.customexpression;

import java.util.Map;

import ar.com.fdvs.dj.domain.CustomExpression;

/**
 * Convenient CustomExpression that returns page number 
 * @author mamana
 *
 */
public class PageNumberCustomExpression implements CustomExpression {

	public Object evaluate(Map fields, Map variables, Map parameters) {
		Integer count = (Integer) variables.get("PAGE_NUMBER");
		return count;
	}

	public String getClassName() {
		return Integer.class.getName();
	}

}
