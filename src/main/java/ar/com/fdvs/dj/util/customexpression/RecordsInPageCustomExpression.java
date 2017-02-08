package ar.com.fdvs.dj.util.customexpression;

import ar.com.fdvs.dj.domain.CustomExpression;
import ar.com.fdvs.dj.domain.entities.Entity;

import java.util.Map;

/**
 * Convenient CustomExpression that returns the record number for the current page 
 * @author mamana
 *
 */
public class RecordsInPageCustomExpression implements CustomExpression {
	private static final long serialVersionUID = Entity.SERIAL_VERSION_UID;
	
	public Object evaluate(Map fields, Map variables, Map parameters) {
        return variables.get("PAGE_COUNT");
	}

	public String getClassName() {
		return Integer.class.getName();
	}

}
