package ar.com.fdvs.dj.domain.hyperlink;

import java.util.Map;

import ar.com.fdvs.dj.domain.StringExpression;

public class LiteralExpression extends StringExpression {
	
	private String text;

	public LiteralExpression(String text){
		this.text = text;
	}

	public Object evaluate(Map fields, Map variables, Map parameters) {
		return text;
	}

}
