/**
 * 
 */
package ar.com.fdvs.dj.core.layout;

import java.util.Date;

import net.sf.jasperreports.engine.design.JRDesignExpression;

/**
 * @author msimone
 *
 */
public abstract class ExpressionUtils {

	private static final String PAGE_NUMBER_VAR = "$V{PAGE_NUMBER}";

	public static JRDesignExpression getPageNumberExpression(String before, String after) {
		JRDesignExpression expression = new JRDesignExpression();
		expression.setText( "\"" + before + "\" + " + PAGE_NUMBER_VAR + " + \"" + after + "\"");
		expression.setValueClass(String.class);
		return expression;
	}

	public static JRDesignExpression getDateExpression(String before, String after) {
		JRDesignExpression expression = new JRDesignExpression();
		expression.setText( "\"" + before + "\" + " + "\"" + new Date() + "\" + \"" + after + "\"");
		expression.setValueClass(String.class);
		return expression;
	}
}
