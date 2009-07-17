package ar.com.fdvs.dj.domain;

/**
 * HyperLinkExpression are CustomsExpression that returns a String value which consist in the
 * url we want the hyperlink to have
 * @author mamana
 *
 */
public abstract class HyperLinkExpression implements CustomExpression {

	public String getClassName() {
		return String.class.getName();
	}

}
