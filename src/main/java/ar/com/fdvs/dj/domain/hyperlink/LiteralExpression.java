package ar.com.fdvs.dj.domain.hyperlink;

import ar.com.fdvs.dj.domain.StringExpression;

import java.util.Map;

public class LiteralExpression extends StringExpression {
	
	private static final long serialVersionUID = 1L;
	
	private String text;

	public LiteralExpression(String text){
		this.text = text;
	}

	public Object evaluate(Map fields, Map variables, Map parameters) {
		return text;
	}

}
