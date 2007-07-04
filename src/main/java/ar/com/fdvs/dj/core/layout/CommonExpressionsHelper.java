package ar.com.fdvs.dj.core.layout;

import net.sf.jasperreports.engine.JRExpression;
import net.sf.jasperreports.engine.design.JRDesignBand;
import net.sf.jasperreports.engine.design.JRDesignExpression;
import net.sf.jasperreports.engine.design.JRDesignTextField;
import net.sf.jasperreports.engine.design.JasperDesign;
import ar.com.fdvs.dj.domain.AutoText;
import ar.com.fdvs.dj.domain.DynamicReport;

//TODO: Design not being used
/**
 * @author msimone
 *
 */
public class CommonExpressionsHelper {

	/**
	 * @param report 
	 * @param design 
	 * @param footerband
	 */
	public static void addPageXofY(final JasperDesign design, final DynamicReport report, JRDesignBand band, HorizontalBandAlignment alignment) {

		int detailHeight = report.getOptions().getDetailHeight().intValue();

		JRDesignTextField pageNumber = new JRDesignTextField();
		pageNumber.setHorizontalAlignment(JRDesignTextField.HORIZONTAL_ALIGN_RIGHT);
		//TODO: l10n
		pageNumber.setExpression(ExpressionUtils.getPageNumberExpression("Page ", " of "));
		pageNumber.setHeight(detailHeight);
		pageNumber.setWidth(80);
		
		JRDesignTextField pageCounter = new JRDesignTextField();
		//TODO: l10n
		pageCounter.setExpression(ExpressionUtils.getPageNumberExpression("", ""));
		pageCounter.setHeight(detailHeight);
		pageCounter.setWidth(20);
		pageCounter.setEvaluationTime(JRExpression.EVALUATION_TIME_REPORT);
		pageCounter.setHorizontalAlignment(JRDesignTextField.HORIZONTAL_ALIGN_LEFT);
		band.addElement(pageCounter);

		int pageNumberOffset = 0;
		if (alignment == HorizontalBandAlignment.RIGHT) pageNumberOffset = pageCounter.getWidth();
		else if (alignment == HorizontalBandAlignment.CENTER) pageNumberOffset = -pageCounter.getWidth()/2;
		
		int pageCounterOffset = 0;
		if (alignment == HorizontalBandAlignment.LEFT) pageCounterOffset = pageNumber.getWidth();
		else if (alignment == HorizontalBandAlignment.CENTER) pageCounterOffset = pageNumber.getWidth()/2;

		alignment.align(report.getOptions().getPrintableWidth(), pageNumberOffset, band, pageNumber);
		alignment.align(report.getOptions().getPrintableWidth(), pageCounterOffset, band, pageCounter);

		band.setHeight(band.getHeight() + detailHeight);
		
	}
	
	public static void addPageXSlashY(final JasperDesign design, final DynamicReport report, JRDesignBand band, HorizontalBandAlignment alignment) {
		
		int detailHeight = report.getOptions().getDetailHeight().intValue();
		
		JRDesignTextField pageNumber = new JRDesignTextField();
		pageNumber.setHorizontalAlignment(JRDesignTextField.HORIZONTAL_ALIGN_RIGHT);
		pageNumber.setExpression(ExpressionUtils.getPageNumberExpression("", ""));
		pageNumber.setHeight(detailHeight);
		pageNumber.setWidth(20);
		
		JRDesignTextField pageCounter = new JRDesignTextField();
		pageCounter.setExpression(ExpressionUtils.getPageNumberExpression("/", ""));
		pageCounter.setHeight(detailHeight);
		pageCounter.setWidth(20);
		pageCounter.setHorizontalAlignment(JRDesignTextField.HORIZONTAL_ALIGN_LEFT);
		pageCounter.setEvaluationTime(JRExpression.EVALUATION_TIME_REPORT);
		pageCounter.setX(pageNumber.getX() + pageNumber.getWidth());

		int pageNumberOffset = 0;
		if (alignment == HorizontalBandAlignment.RIGHT) pageNumberOffset = pageCounter.getWidth();
		else if (alignment == HorizontalBandAlignment.CENTER) pageNumberOffset = -pageCounter.getWidth()/2;
		
		int pageCounterOffset = 0;
		if (alignment == HorizontalBandAlignment.LEFT) pageCounterOffset = pageNumber.getWidth();
		else if (alignment == HorizontalBandAlignment.CENTER) pageCounterOffset = pageNumber.getWidth()/2;
		
		alignment.align(report.getOptions().getPrintableWidth(), pageNumberOffset, band, pageNumber);
		alignment.align(report.getOptions().getPrintableWidth(), pageCounterOffset, band, pageCounter);
		
		band.setHeight(band.getHeight() + detailHeight);
		
	}
	
	/**
	 * @param design
	 * @param report
	 * @param footerband
	 * @param alignment
	 */
	public static void addPageX(JasperDesign design, DynamicReport report, JRDesignBand band, HorizontalBandAlignment alignment) {
		int detailHeight = report.getOptions().getDetailHeight().intValue();
		
		JRDesignTextField pageNumber = new JRDesignTextField();
		pageNumber.setHorizontalAlignment(JRDesignTextField.HORIZONTAL_ALIGN_RIGHT);
		pageNumber.setExpression(ExpressionUtils.getPageNumberExpression("", ""));
		pageNumber.setHeight(detailHeight);
		pageNumber.setWidth(20);
		
		alignment.align(report.getOptions().getPrintableWidth(), 0, band, pageNumber);
		
		band.setHeight(band.getHeight() + detailHeight);
		
	}
	
	public static void addCreationDate(final JasperDesign design, final DynamicReport report, JRDesignBand band, HorizontalBandAlignment alignment) {
		int detailHeight = report.getOptions().getDetailHeight().intValue();
		
		JRDesignTextField date = new JRDesignTextField();
		date.setHorizontalAlignment(JRDesignTextField.HORIZONTAL_ALIGN_RIGHT);
		//TODO: l10n
		date.setExpression(ExpressionUtils.getDateExpression("Created on", ""));
		date.setHeight(detailHeight);
		date.setWidth(200);
		alignment.align(report.getOptions().getPrintableWidth(), 0, band, date);
		band.setHeight(band.getHeight() + detailHeight);
		
	}

	/**
	 * @param design
	 * @param report
	 * @param footerband
	 * @param left
	 */
	public static void addMessage(JasperDesign design, DynamicReport report, JRDesignBand band, AutoText autoText) {
		int detailHeight = report.getOptions().getDetailHeight().intValue();
		
		JRDesignTextField textfield = new JRDesignTextField();
		JRDesignExpression expression = new JRDesignExpression();
		expression.setValueClass(String.class);
		expression.setText( "\"" + autoText.getMessage() + "\"");
		textfield.setExpression(expression);
		textfield.setHeight(detailHeight);
		textfield.setStyledText(true);
		textfield.setWidth(report.getOptions().getPrintableWidth());
		autoText.getAlignment().align(report.getOptions().getPrintableWidth(), 0, band, textfield);
		
		if (autoText.getAlignment() == HorizontalBandAlignment.CENTER) textfield.setHorizontalAlignment(JRDesignTextField.HORIZONTAL_ALIGN_CENTER);
		else if (autoText.getAlignment() == HorizontalBandAlignment.RIGHT) textfield.setHorizontalAlignment(JRDesignTextField.HORIZONTAL_ALIGN_RIGHT);
		band.setHeight(band.getHeight() + detailHeight);
		
	}

	/**
	 * @param design
	 * @param report
	 * @param text
	 */
	public static void add(JasperDesign design, DynamicReport report, JRDesignBand band , AutoText text) {
		switch (text.getType()) {
		case AutoText.PAGE_X_OF_Y: CommonExpressionsHelper.addPageXofY(design, report, band, text.getAlignment()); break;
		case AutoText.PAGE_X_SLASH_Y: CommonExpressionsHelper.addPageXSlashY(design, report, band, text.getAlignment()); break;
		case AutoText.PAGE_X: CommonExpressionsHelper.addPageX(design, report, band, text.getAlignment()); break;
		case AutoText.CREATED_ON: CommonExpressionsHelper.addCreationDate(design, report, band,text.getAlignment()); break;
		case AutoText.CUSTOM_MESSAGE: CommonExpressionsHelper.addMessage(design, report, band, text); break;
		}
	}

}
