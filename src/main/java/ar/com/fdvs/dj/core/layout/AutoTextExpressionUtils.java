/*
 * DynamicJasper: A library for creating reports dynamically by specifying
 * columns, groups, styles, etc. at runtime. It also saves a lot of development
 * time in many cases! (http://sourceforge.net/projects/dynamicjasper)
 *
 * Copyright (C) 2008  FDV Solutions (http://www.fdvsolutions.com)
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 *
 * License as published by the Free Software Foundation; either
 *
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 *
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 *
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 *
 *
 */

/**
 *
 */
package ar.com.fdvs.dj.core.layout;

import ar.com.fdvs.dj.domain.AutoText;
import net.sf.jasperreports.engine.design.JRDesignExpression;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * @author msimone
 *
 */
public abstract class AutoTextExpressionUtils {

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
