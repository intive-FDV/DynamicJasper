package ar.com.fdvs.dj.domain.entities;

import ar.com.fdvs.dj.domain.DJBaseElement;

public class Parameter extends DJBaseElement {
	
	private static final long serialVersionUID = Entity.SERIAL_VERSION_UID;
	
	private String name;
	private String className;
	private String defaultValueExpression;
	
	public Parameter() {
		super();
	}
	
	public Parameter(String name, String className) {
		super();
		this.name = name;
		this.className = className;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public String getDefaultValueExpression() {
		return defaultValueExpression;
	}
	public void setDefaultValueExpression(String defaultValueExpression) {
		this.defaultValueExpression = defaultValueExpression;
	}
}
