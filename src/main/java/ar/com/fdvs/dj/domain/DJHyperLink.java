package ar.com.fdvs.dj.domain;

import java.io.Serializable;

public class DJHyperLink implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/*
	 * This expression will return in runtime the URL (as a String) to add to the report element
	 * whose this link will be bounded.
	 */
	private StringExpression expression;
	
	private StringExpression tooltip;

	public StringExpression getExpression() {
		return expression;
	}

	public void setExpression(StringExpression expression) {
		this.expression = expression;
	}

	public StringExpression getTooltip() {
		return tooltip;
	}

	public void setTooltip(StringExpression tooltip) {
		this.tooltip = tooltip;
	}



}
