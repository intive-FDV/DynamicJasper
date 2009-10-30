package ar.com.fdvs.dj.domain;


public abstract class BooleanExpression implements CustomExpression {

	public String getClassName() {
		return Boolean.class.getName();
	}

}
