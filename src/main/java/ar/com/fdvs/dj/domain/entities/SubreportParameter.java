package ar.com.fdvs.dj.domain.entities;

import ar.com.fdvs.dj.core.DJConstants;
import ar.com.fdvs.dj.domain.DJBaseElement;

public class SubreportParameter extends DJBaseElement {

	private static final long serialVersionUID = Entity.SERIAL_VERSION_UID;
	
	private String name;
	private String expression;
	private String className;

	/**
	 * Subreport parameters are objects from the main report. In the main report
	 * they can come from field values, parameters or variables.<br>
	 * Use {@link DJConstants}.SUBREPORT_PARAM_ORIGIN_FIELD,<br>
	 * {@link DJConstants}.SUBREPORT_PARAM_ORIGIN_PARAMETER or <br>
	 * {@link DJConstants}.SUBREPORT_PARAM_ORIGIN_VARIABLE  to specify it.
	 */
	private int parameterOrigin = DJConstants.SUBREPORT_PARAM_ORIGIN_FIELD;
	
	public int getParameterOrigin() {
		return parameterOrigin;
	}

	public void setParameterOrigin(int parameterOrigin) {
		this.parameterOrigin = parameterOrigin;
	}

	public SubreportParameter() {
		super();
	}
	
	
	
	public SubreportParameter(String name, String expression, String className, int parameterOrigin) {
		super();
		this.name = name;
		this.expression = expression;
		this.className = className;
		this.parameterOrigin = parameterOrigin;
	}

	public SubreportParameter(String name, String expression, String className) {
		super();
		this.name = name;
		this.expression = expression;
		this.className = className;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getExpression() {
		return expression;
	}
	public void setExpression(String expression) {
		this.expression = expression;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	
}
