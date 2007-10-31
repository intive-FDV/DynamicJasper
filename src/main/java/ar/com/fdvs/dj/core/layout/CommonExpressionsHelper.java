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
		
		pageNumber.setExpression(ExpressionUtils.getPageNumberExpression(KEY_autotext_page, KEY_autotext_of,true));
		pageNumber.setHeight(height);
		pageNumber.setWidth(80);
		pageNumber.setY(yOffset);
		pageNumber.setPositionType(JRDesignTextField.POSITION_TYPE_FLOAT);
		
		JRDesignTextField pageCounter = new JRDesignTextField();

		pageCounter.setExpression(ExpressionUtils.getPageNumberExpression("", "",false));
		pageCounter.setHeight(height);
		pageCounter.setWidth(20);
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
		pageNumber.setExpression(ExpressionUtils.getPageNumberExpression("", "",false));
		pageNumber.setHeight(height);
		pageNumber.setWidth(20);
		pageNumber.setY(yOffset);
		
		JRDesignTextField pageCounter = new JRDesignTextField();
		pageCounter.setExpression(ExpressionUtils.getPageNumberExpression("/", "",false));
		pageCounter.setHeight(height);
		pageCounter.setWidth(20);
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
		pageNumber.setExpression(ExpressionUtils.getPageNumberExpression("", "",false));
		pageNumber.setHeight(height);
		pageNumber.setWidth(20);
		pageNumber.setY(yOffset);
		
		autoText.getAlignment().align(report.getOptions().getPrintableWidth(), 0, band, pageNumber);
		
		band.setHeight(band.getHeight() + height);
		
	}
	
	public static void addCreationDate(int yOffset, final JasperDesign design, final DynamicReport report, JRDesignBand band,  AutoText autoText) {
		int height = autoText.getHeight().intValue();
		
		JRDesignTextField dateTf = new JRDesignTextField();

		dateTf.setExpression(ExpressionUtils.getDateExpression(KEY_autotext_created_on, "", report.getReportLocale(),autoText.getPattern()));
		dateTf.setHeight(height);
		dateTf.setWidth(report.getOptions().getColumnWidth());
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
		textfield.setWidth(report.getOptions().getPrintableWidth());
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
