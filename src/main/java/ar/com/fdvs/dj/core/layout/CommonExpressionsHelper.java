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

package ar.com.fdvs.dj.core.layout;

import java.util.Random;

import net.sf.jasperreports.engine.JRExpression;
import net.sf.jasperreports.engine.design.JRDesignBand;
import net.sf.jasperreports.engine.design.JRDesignExpression;
import net.sf.jasperreports.engine.design.JRDesignTextField;
import ar.com.fdvs.dj.domain.AutoText;
import ar.com.fdvs.dj.domain.DynamicJasperDesign;
import ar.com.fdvs.dj.domain.DynamicReport;
import ar.com.fdvs.dj.util.ExpressionUtils;

/**
 * @author msimone
 *
 */
public class CommonExpressionsHelper {

	private static String KEY_autotext_page = "autotext.page";
	private static String KEY_autotext_of = "autotext.of";
	private static String KEY_autotext_created_on = "autotext.created_on";
	private static Random random = new Random();

	/**
	 * @param yOffset
	 * @param report
	 * @param design
	 * @param footerband
	 */
	public static void addPageXofY(int yOffset, final DynamicJasperDesign design, final DynamicReport report, AbstractLayoutManager lm, JRDesignBand band, AutoText autoText) {

		int height = autoText.getHeight().intValue();

		JRDesignTextField pageNumber = new JRDesignTextField();
		pageNumber.setHorizontalAlignment(JRDesignTextField.HORIZONTAL_ALIGN_RIGHT);

		boolean hasStyle = autoText.getStyle() != null;
		if (hasStyle) {
			lm.applyStyleToElement(autoText.getStyle(), pageNumber);
		}

		pageNumber.setExpression(AutoTextExpressionUtils.getPageNumberExpression(KEY_autotext_page, KEY_autotext_of,true));
		pageNumber.setHeight(height);
		pageNumber.setWidth(autoText.getWidth().intValue());
		pageNumber.setY(yOffset);
		pageNumber.setPositionType(JRDesignTextField.POSITION_TYPE_FLOAT);

		JRDesignTextField pageCounter = new JRDesignTextField();
		
		if (hasStyle) {
			lm.applyStyleToElement(autoText.getStyle(), pageCounter);
		}
		

		pageCounter.setExpression(AutoTextExpressionUtils.getPageNumberExpression(" ", "",false));
		pageCounter.setHeight(height);
		pageCounter.setWidth(autoText.getWidth2().intValue());
		pageCounter.setY(yOffset);
		pageCounter.setEvaluationTime(JRExpression.EVALUATION_TIME_REPORT);
		pageCounter.setHorizontalAlignment(JRDesignTextField.HORIZONTAL_ALIGN_LEFT);
		pageCounter.setPositionType(JRDesignTextField.POSITION_TYPE_FLOAT);
		band.addElement(pageCounter);

		int pageNumberOffset = 0;
		HorizontalBandAlignment alignment = autoText.getAlignment();
		if (alignment == HorizontalBandAlignment.RIGHT) pageNumberOffset = pageCounter.getWidth();
		else if (alignment == HorizontalBandAlignment.CENTER) pageNumberOffset = -pageCounter.getWidth()/2;

		int pageCounterOffset = 0;
		if (alignment == HorizontalBandAlignment.LEFT) pageCounterOffset = pageNumber.getWidth();
		else if (alignment == HorizontalBandAlignment.CENTER) pageCounterOffset = pageNumber.getWidth()/2;

		alignment.align(report.getOptions().getPrintableWidth(), pageNumberOffset, band, pageNumber);
		alignment.align(report.getOptions().getPrintableWidth(), pageCounterOffset, band, pageCounter);

		if (autoText.getPrintWhenExpression() != null) {
			JRDesignExpression printWhenExpression = getPrintWhenExpression(design, autoText);
			pageNumber.setPrintWhenExpression(printWhenExpression);
			pageCounter.setPrintWhenExpression(printWhenExpression);
		}
		
		band.setHeight(band.getHeight() + height);

	}

	public static void addPageXSlashY(int yOffset, final DynamicJasperDesign design, final DynamicReport report, AbstractLayoutManager lm, JRDesignBand band, AutoText autoText) {

		int height = autoText.getHeight().intValue();

		JRDesignTextField pageNumber = new JRDesignTextField();
		if (autoText.getStyle() != null) {
			lm.applyStyleToElement(autoText.getStyle(), pageNumber);
		}

		pageNumber.setHorizontalAlignment(JRDesignTextField.HORIZONTAL_ALIGN_RIGHT);
		pageNumber.setExpression(AutoTextExpressionUtils.getPageNumberExpression("", "",false));
		pageNumber.setHeight(height);
		pageNumber.setWidth(autoText.getWidth().intValue());
		pageNumber.setY(yOffset);

		JRDesignTextField pageCounter = new JRDesignTextField();
		if (autoText.getStyle() != null) { //page counter shares font attrs, color, etc.
			lm.applyStyleToElement(autoText.getStyle(), pageCounter);
		}		
		pageCounter.setExpression(AutoTextExpressionUtils.getPageNumberExpression("/", "",false));
		pageCounter.setHeight(height);
		pageCounter.setWidth(autoText.getWidth().intValue());
		pageCounter.setHorizontalAlignment(JRDesignTextField.HORIZONTAL_ALIGN_LEFT);
		pageCounter.setEvaluationTime(JRExpression.EVALUATION_TIME_REPORT);
		pageCounter.setX(pageNumber.getX() + pageNumber.getWidth());
		pageCounter.setY(yOffset);

		int pageNumberOffset = 0;
		HorizontalBandAlignment alignment = autoText.getAlignment();
		if (alignment  == HorizontalBandAlignment.RIGHT) pageNumberOffset = pageCounter.getWidth();
		else if (alignment == HorizontalBandAlignment.CENTER) pageNumberOffset = -pageCounter.getWidth()/2;

		int pageCounterOffset = 0;
		if (alignment == HorizontalBandAlignment.LEFT) pageCounterOffset = pageNumber.getWidth();
		else if (alignment == HorizontalBandAlignment.CENTER) pageCounterOffset = pageNumber.getWidth()/2;

		alignment.align(report.getOptions().getPrintableWidth(), pageNumberOffset, band, pageNumber);
		alignment.align(report.getOptions().getPrintableWidth(), pageCounterOffset, band, pageCounter);

		if (autoText.getPrintWhenExpression() != null) {
			JRDesignExpression printWhenExpression = getPrintWhenExpression(design, autoText);
			pageNumber.setPrintWhenExpression(printWhenExpression);
			pageCounter.setPrintWhenExpression(printWhenExpression);
		}
		
		band.setHeight(band.getHeight() + height);

	}

	/**
	 * @param yOffset
	 * @param design
	 * @param report
	 * @param footerband
	 * @param alignment
	 */
	public static void addPageX(int yOffset, DynamicJasperDesign design, DynamicReport report, AbstractLayoutManager lm, JRDesignBand band,  AutoText autoText) {
		int height = autoText.getHeight().intValue();

		JRDesignTextField pageNumber = new JRDesignTextField();
		if (autoText.getStyle() != null)
		{
			lm.applyStyleToElement(autoText.getStyle(), pageNumber);
		}

		pageNumber.setHorizontalAlignment(JRDesignTextField.HORIZONTAL_ALIGN_RIGHT);
		pageNumber.setExpression(AutoTextExpressionUtils.getPageNumberExpression("", "",false));
		pageNumber.setHeight(height);
		if (AutoText.WIDTH_NOT_SET.equals(autoText.getWidth())){
			pageNumber.setWidth(report.getOptions().getPrintableWidth());
		} else {
			pageNumber.setWidth(autoText.getWidth().intValue());
		}		
		pageNumber.setY(yOffset);

		autoText.getAlignment().align(report.getOptions().getPrintableWidth(), 0, band, pageNumber);

		if (autoText.getPrintWhenExpression() != null) {
			JRDesignExpression printWhenExpression = getPrintWhenExpression(design, autoText);
			pageNumber.setPrintWhenExpression(printWhenExpression);
		}
		
		band.setHeight(band.getHeight() + height);

	}

	public static void addCreationDate(int yOffset, final DynamicJasperDesign design, final DynamicReport report, AbstractLayoutManager lm, JRDesignBand band,  AutoText autoText) {
		int height = autoText.getHeight().intValue();

		JRDesignTextField dateTf = new JRDesignTextField();
		if (autoText.getStyle() != null)
		{
			lm.applyStyleToElement(autoText.getStyle(), dateTf);
		}


		dateTf.setExpression(AutoTextExpressionUtils.getDateExpression(KEY_autotext_created_on, "", report.getReportLocale(),autoText.getPattern()));
		dateTf.setHeight(height);
		if (AutoText.WIDTH_NOT_SET.equals(autoText.getWidth())){
			dateTf.setWidth(report.getOptions().getPrintableWidth());
		} else {
			dateTf.setWidth(autoText.getWidth().intValue());
		}		
		dateTf.setHorizontalAlignment(autoText.getAlignment().getAlignment());
		dateTf.setY(yOffset);
		dateTf.setPositionType(JRDesignTextField.POSITION_TYPE_FLOAT);

		autoText.getAlignment().align(report.getOptions().getPrintableWidth(), 0, band, dateTf);
		
		if (autoText.getPrintWhenExpression() != null) {
			JRDesignExpression printWhenExpression = getPrintWhenExpression(design, autoText);
			dateTf.setPrintWhenExpression(printWhenExpression);
		}
		
		band.setHeight(band.getHeight() + height);

	}

	/**
	 * @param offset
	 * @param design
	 * @param report
	 * @param footerband
	 * @param left
	 */
	public static void addMessage(int yOffset, DynamicJasperDesign design, DynamicReport report, AbstractLayoutManager lm, JRDesignBand band, AutoText autoText) {
		int height = autoText.getHeight().intValue();

		JRDesignTextField textfield = new JRDesignTextField();
		if (autoText.getStyle() != null)
		{
			lm.applyStyleToElement(autoText.getStyle(), textfield);
		}
		JRDesignExpression expression = new JRDesignExpression();
		expression.setValueClass(String.class);
		expression.setText( "\"" + autoText.getMessageKey() + "\"");
		textfield.setExpression(expression);
		textfield.setHeight(autoText.getHeight().intValue());
		textfield.setStyledText(true);
		if (AutoText.WIDTH_NOT_SET.equals(autoText.getWidth())){
			textfield.setWidth(report.getOptions().getPrintableWidth());
		} else {
			textfield.setWidth(autoText.getWidth().intValue());
		}
		textfield.setY(yOffset);
		autoText.getAlignment().align(report.getOptions().getPrintableWidth(), 0, band, textfield);

		textfield.setHorizontalAlignment(autoText.getAlignment().getAlignment());

		if (autoText.getPrintWhenExpression() != null) {
			JRDesignExpression printWhenExpression = getPrintWhenExpression(design, autoText);
			textfield.setPrintWhenExpression(printWhenExpression);
		}
		
		band.setHeight(band.getHeight() + height);

	}

	/**
	 * @param design
	 * @param autoText
	 */
	private static JRDesignExpression getPrintWhenExpression(DynamicJasperDesign design, AutoText autoText) {
		long random_ = Math.abs(random.nextLong());
		String name = "autotext_" + random_ + "_printWhenExpression";
		return ExpressionUtils.createAndRegisterExpression(design, name , autoText.getPrintWhenExpression());
	}
	
	/**
	 * @param offset
	 * @param design
	 * @param report
	 * @param text
	 */
	public static void add(int yOffset, DynamicJasperDesign design, AbstractLayoutManager lm, JRDesignBand band , AutoText text) {
		DynamicReport report = lm.getReport();
		switch (text.getType()) {
		case AutoText.AUTOTEXT_PAGE_X_OF_Y: CommonExpressionsHelper.addPageXofY(yOffset,design, report, lm, band, text); break;
		case AutoText.AUTOTEXT_PAGE_X_SLASH_Y: CommonExpressionsHelper.addPageXSlashY(yOffset,design, report, lm, band, text); break;
		case AutoText.AUTOTEXT_PAGE_X: CommonExpressionsHelper.addPageX(yOffset,design, report, lm, band, text); break;
		case AutoText.AUTOTEXT_CREATED_ON: CommonExpressionsHelper.addCreationDate(yOffset,design, report, lm, band,text); break;
		case AutoText.AUTOTEXT_CUSTOM_MESSAGE: CommonExpressionsHelper.addMessage(yOffset,design, report, lm, band, text); break;
		}
	}

}
