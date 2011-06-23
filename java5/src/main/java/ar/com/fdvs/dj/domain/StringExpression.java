package ar.com.fdvs.dj.domain;

import java.io.Serializable;

/**
 * HyperLinkExpression are CustomsExpression that returns a String value which consist in the
 * url we want the hyperlink to have
 * @author mamana
 *
 */
public abstract class StringExpression implements CustomExpression, Serializable {

	private static final long serialVersionUID = 1L;

	public String getClassName() {
		return String.class.getName();
	}

}
