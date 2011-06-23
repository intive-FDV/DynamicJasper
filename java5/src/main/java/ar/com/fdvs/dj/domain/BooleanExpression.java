package ar.com.fdvs.dj.domain;

import ar.com.fdvs.dj.domain.entities.Entity;


public abstract class BooleanExpression implements CustomExpression {
	private static final long serialVersionUID = Entity.SERIAL_VERSION_UID;
	
	public String getClassName() {
		return Boolean.class.getName();
	}

}
