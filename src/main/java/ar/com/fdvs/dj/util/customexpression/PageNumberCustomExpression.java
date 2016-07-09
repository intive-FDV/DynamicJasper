package ar.com.fdvs.dj.util.customexpression;

import ar.com.fdvs.dj.domain.CustomExpression;
import ar.com.fdvs.dj.domain.entities.Entity;

import java.util.Map;

/**
 * Convenient CustomExpression that returns page number 
 * @author mamana
 *
 */
public class PageNumberCustomExpression implements CustomExpression {
	private static final long serialVersionUID = Entity.SERIAL_VERSION_UID;
	
	public Object evaluate(Map fields, Map variables, Map parameters) {
		Integer count = (Integer) variables.get("PAGE_NUMBER");
		return count;
	}

	public String getClassName() {
		return Integer.class.getName();
	}

}
