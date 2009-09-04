package ar.com.fdvs.dj.util.customexpression;

import java.util.Map;

import ar.com.fdvs.dj.domain.CustomExpression;

/**
 * Convenient CustomExpression that returns the record number for the current page 
 * @author mamana
 *
 */
public class RecordsInPageCustomExpression implements CustomExpression {

	public Object evaluate(Map fields, Map variables, Map parameters) {
		Integer count = (Integer) variables.get("PAGE_COUNT");
		return count;
	}

	public String getClassName() {
		return Integer.class.getName();
	}

}
