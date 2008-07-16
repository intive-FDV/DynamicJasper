/*
 * Dynamic Jasper: A library for creating reports dynamically by specifying
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

package ar.com.fdvs.dj.core.layout;

import net.sf.jasperreports.engine.JRExpression;
import net.sf.jasperreports.engine.design.JRDesignBand;
import net.sf.jasperreports.engine.design.JRDesignExpression;
import net.sf.jasperreports.engine.design.JRDesignTextField;
import net.sf.jasperreports.engine.design.JasperDesign;
import ar.com.fdvs.dj.domain.AutoText;
import ar.com.fdvs.dj.domain.DynamicReport;

/**
 * @author msimone
 *
 */
public class CommonExpressionsHelper {

	private static String KEY_autotext_page = "autotext.page";
	private static String KEY_autotext_of = "autotext.of";
	private static String KEY_autotext_created_on = "autotext.created_on";

	/**
	 * @param yOffset
	 * @param report
	 * @param design
	 * @param footerband
	 */
	public static void addPageXofY(int yOffset, final JasperDesign design, final DynamicReport report, JRDesignBand band, AutoText autoText) {

		int height = autoText.getHeight().intValue();

		JRDesignTextField pageNumber = new JRDesignTextField();
		pageNumber.setHorizontalAlignment(JRDesignTextField.HORIZONTAL_ALIGN_RIGHT);

		pageNumber.setExpression(AutoTextExpressionUtils.getPageNumberExpression(KEY_autotext_page, KEY_autotext_of,true));
		pageNumber.setHeight(height);
		pageNumber.setWidth(autoText.getWidth().intValue());
		pageNumber.setY(yOffset);
		pageNumber.setPositionType(JRDesignTextField.POSITION_TYPE_FLOAT);

		JRDesignTextField pageCounter = new JRDesignTextField();

		pageCounter.setExpression(AutoTextExpressionUtils.getPageNumberExpression("", "",false));
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

		band.setHeight(band.getHeight() + height);

	}

	public static void addPageXSlashY(int yOffset, final JasperDesign design, final DynamicReport report, JRDesignBand band, AutoText autoText) {

		int height = autoText.getHeight().intValue();

		JRDesignTextField pageNumber = new JRDesignTextField();
		pageNumber.setHorizontalAlignment(JRDesignTextField.HORIZONTAL_ALIGN_RIGHT);
		pageNumber.setExpression(AutoTextExpressionUtils.getPageNumberExpression("", "",false));
		pageNumber.setHeight(height);
		pageNumber.setWidth(autoText.getWidth().intValue());
		pageNumber.setY(yOffset);

		JRDesignTextField pageCounter = new JRDesignTextField();
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

		band.setHeight(band.getHeight() + height);

	}

	/**
	 * @param yOffset
	 * @param design
	 * @param report
	 * @param footerband
	 * @param alignment
	 */
	public static void addPageX(int yOffset, JasperDesign design, DynamicReport report, JRDesignBand band,  AutoText autoText) {
		int height = autoText.getHeight().intValue();

		JRDesignTextField pageNumber = new JRDesignTextField();
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

		band.setHeight(band.getHeight() + height);

	}

	public static void addCreationDate(int yOffset, final JasperDesign design, final DynamicReport report, JRDesignBand band,  AutoText autoText) {
		int height = autoText.getHeight().intValue();

		JRDesignTextField dateTf = new JRDesignTextField();

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
		band.setHeight(band.getHeight() + height);

	}

	/**
	 * @param offset
	 * @param design
	 * @param report
	 * @param footerband
	 * @param left
	 */
	public static void addMessage(int yOffset, JasperDesign design, DynamicReport report, JRDesignBand band, AutoText autoText) {
		int height = autoText.getHeight().intValue();

		JRDesignTextField textfield = new JRDesignTextField();
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

		band.setHeight(band.getHeight() + height);

	}

	/**
	 * @param offset
	 * @param design
	 * @param report
	 * @param text
	 */
	public static void add(int yOffset, JasperDesign design, DynamicReport report, JRDesignBand band , AutoText text) {
		switch (text.getType()) {
		case AutoText.AUTOTEXT_PAGE_X_OF_Y: CommonExpressionsHelper.addPageXofY(yOffset,design, report, band, text); break;
		case AutoText.AUTOTEXT_PAGE_X_SLASH_Y: CommonExpressionsHelper.addPageXSlashY(yOffset,design, report, band, text); break;
		case AutoText.AUTOTEXT_PAGE_X: CommonExpressionsHelper.addPageX(yOffset,design, report, band, text); break;
		case AutoText.AUTOTEXT_CREATED_ON: CommonExpressionsHelper.addCreationDate(yOffset,design, report, band,text); break;
		case AutoText.AUTOTEXT_CUSTOM_MESSAGE: CommonExpressionsHelper.addMessage(yOffset,design, report, band, text); break;
		}
	}

}
