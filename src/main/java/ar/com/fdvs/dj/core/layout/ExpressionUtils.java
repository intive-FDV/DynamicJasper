/**
 * 
 */
package ar.com.fdvs.dj.core.layout;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import ar.com.fdvs.dj.domain.AutoText;

import net.sf.jasperreports.engine.design.JRDesignExpression;

/**
 * @author msimone
 *
 */
public abstract class ExpressionUtils {

	private static final String PAGE_NUMBER_VAR = "$V{PAGE_NUMBER}";

	public static JRDesignExpression getPageNumberExpression(String before, String after, boolean useI18n) {
		JRDesignExpression expression = new JRDesignExpression();
		String text = null;
		if (useI18n) {
			if (!emptyString(before)){
				before = "$R{" + before + "}";
			} else {before = "\"" + before + "\"";}
			if (!emptyString(after)){
				after = "$R{" + after + "}";
			} else {after = "\"" + after + "\"";}
			 text = before + "+\" \" + " + PAGE_NUMBER_VAR + "+\" \" + " + after;
		} else {
			if (emptyString(before)){
				before = "\"\"";
			} else {before = "\"" + before + "\"";}
			if (emptyString(after)){
				after = "\"\"";
			} else {after = "\"" + after + "\"";}
			text = before + "+\" \" + " + PAGE_NUMBER_VAR + "+\" \" + " + after;
			
		}
		expression.setText( text );
		expression.setValueClass(String.class);
		return expression;
	}

    //TODO: use string utils
    private static boolean emptyString(String str) {
		if (str == null)
			return true;

		if ("".equals(str.trim()))
			return true;
		
		return false;
	}

	public static JRDesignExpression getDateExpression(String before, String after, Locale locale, byte pattern) {
		if (!emptyString(before)){
			before = "$R{" + before + "}";
		} else {before = "\"" + before + "\"";}
		if (!emptyString(after)){
			after = "$R{" + after + "}";
		} else {after = "\"" + after + "\"";}		
		DateFormat dateFormatter = null;
		if (AutoText.PATTERN_DATE_DATE_ONLY ==  pattern)
		 dateFormatter = DateFormat.getDateInstance(DateFormat.DEFAULT,locale);
		else if (AutoText.PATTERN_DATE_TIME_ONLY ==  pattern)
			 dateFormatter = DateFormat.getTimeInstance(DateFormat.DEFAULT,locale);
		else 
			dateFormatter = DateFormat.getDateTimeInstance(DateFormat.DEFAULT,DateFormat.DEFAULT,locale);
		JRDesignExpression expression = new JRDesignExpression();
		String text = before + "+\" \" + \"" + dateFormatter.format(new Date()) + "\" +\" \" + " + after;
//		expression.setText( "\"" + before + "\" + " + "\"" + new Date() + "\" + \"" + after + "\"");
		expression.setText( text );
		expression.setValueClass(String.class);
		return expression;
	}
}
