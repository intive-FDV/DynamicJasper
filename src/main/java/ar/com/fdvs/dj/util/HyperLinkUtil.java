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

package ar.com.fdvs.dj.util;

import net.sf.jasperreports.engine.JRHyperlink;
import net.sf.jasperreports.engine.design.JRDesignChart;
import net.sf.jasperreports.engine.design.JRDesignExpression;
import net.sf.jasperreports.engine.design.JRDesignImage;
import net.sf.jasperreports.engine.design.JRDesignTextField;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import ar.com.fdvs.dj.domain.DJHyperLink;
import ar.com.fdvs.dj.domain.DynamicJasperDesign;
import ar.com.fdvs.dj.domain.StringExpression;

public class HyperLinkUtil {
	
	static final Log log = LogFactory.getLog(HyperLinkUtil.class);
	
	/**
	 * 
	 * @param design
	 * @param djHyperLink 
	 * @param image
	 * @param column
	 */
	public static void applyHyperLinkToElement(DynamicJasperDesign design, DJHyperLink djlink, JRDesignImage image, String name) {
		StringExpression hce = djlink.getExpression();
		
		String text = ExpressionUtils.createCustomExpressionInvocationText(djlink.getExpression(), name);
		LayoutUtils.registerCustomExpressionParameter(design, name,hce);
		JRDesignExpression hlpe = new JRDesignExpression();
		hlpe.setValueClassName(hce.getClassName());
		
		hlpe.setText(text);
		image.setHyperlinkReferenceExpression(hlpe);
		image.setHyperlinkType(JRHyperlink.HYPERLINK_TYPE_REFERENCE); //FIXME Should this be a parameter in the future?

		if (djlink.getTooltip() != null){
			StringExpression sExp = djlink.getTooltip();
			String tooltipParameterName = "hyperlink_tooltip_" +name;
			String tooltipText = ExpressionUtils.createCustomExpressionInvocationText(djlink.getExpression(), tooltipParameterName);
			LayoutUtils.registerCustomExpressionParameter(design, tooltipParameterName,sExp);
			JRDesignExpression tooltipExp = new JRDesignExpression();
			tooltipExp.setValueClassName(sExp.getClassName());
			tooltipExp.setText(tooltipText);
			
			image.setHyperlinkTooltipExpression(tooltipExp);
		}	
		
	}

	/**
	 *  Creates necessary objects to make a textfield an hyperlink
	 * @param design
	 * @param textField
	 * @param column
	 */
	public static void applyHyperLinkToElement(DynamicJasperDesign design, DJHyperLink djlink, JRDesignTextField textField, String name) {
//		DJHyperLink djlink = column.getLink();
		StringExpression hce = djlink.getExpression();
		
//		String hceParameterName = "hyperlink_column_" +column.getTextForExpression().replaceAll("[\\$\\{\\}]", "_");
		String text = ExpressionUtils.createCustomExpressionInvocationText2(name);
		LayoutUtils.registerCustomExpressionParameter(design,name,hce);
		JRDesignExpression hlpe = new JRDesignExpression();
		hlpe.setValueClassName(hce.getClassName());
		
		hlpe.setText(text);
		textField.setHyperlinkReferenceExpression(hlpe);
		textField.setHyperlinkType(JRHyperlink.HYPERLINK_TYPE_REFERENCE); //FIXME Should this be a parameter in the future?
		
		
		if (djlink.getTooltip() != null){
			StringExpression sExp = djlink.getTooltip();
//			String tooltipParameterName = "hyperlink_tooltip_column_" +column.getTextForExpression().replaceAll("[\\$\\{\\}]", "_");
			String tooltipParameterName = "tooltip_" + name;
			String tooltipText = ExpressionUtils.createCustomExpressionInvocationText2(tooltipParameterName);
			LayoutUtils.registerCustomExpressionParameter(design,tooltipParameterName,sExp);
			JRDesignExpression tooltipExp = new JRDesignExpression();
			tooltipExp.setValueClassName(sExp.getClassName());
			tooltipExp.setText(tooltipText);
			
			textField.setHyperlinkTooltipExpression(tooltipExp);
		}
	}

	/**
	 *  Creates necessary objects to make a chart an hyperlink
	 * @param design
	 * @param chart
	 * @param column
	 */
	public static void applyHyperLinkToElement(DynamicJasperDesign design, DJHyperLink djlink, JRDesignChart chart, String name) {
		JRDesignExpression hlpe = ExpressionUtils.createAndRegisterExpression(design, name, djlink.getExpression());
		chart.setHyperlinkReferenceExpression(hlpe);
		chart.setHyperlinkType(JRHyperlink.HYPERLINK_TYPE_REFERENCE); //FIXME Should this be a parameter in the future?
				
		if (djlink.getTooltip() != null){			
			JRDesignExpression tooltipExp = ExpressionUtils.createAndRegisterExpression(design, "tooltip_" + name, djlink.getTooltip());
			chart.setHyperlinkTooltipExpression(tooltipExp);
		}
	}
}
