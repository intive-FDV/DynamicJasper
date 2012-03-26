/*
 * Copyright (c) 2012, FDV Solutions S.A.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *     * Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 *     * Neither the name of the FDV Solutions S.A. nor the
 *       names of its contributors may be used to endorse or promote products
 *       derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL FDV Solutions S.A. BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

/**
 *
 */
package ar.com.fdvs.dj.core.layout;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import net.sf.jasperreports.engine.design.JRDesignExpression;
import ar.com.fdvs.dj.domain.AutoText;

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
